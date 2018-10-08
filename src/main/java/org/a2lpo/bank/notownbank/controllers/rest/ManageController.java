package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.Manager;
import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.payload.CreatedByResponse;
import org.a2lpo.bank.notownbank.payload.ManagerAddRequest;
import org.a2lpo.bank.notownbank.payload.ManagerDetailsResponse;
import org.a2lpo.bank.notownbank.repos.ManagerRepo;
import org.a2lpo.bank.notownbank.repos.RoleRepo;
import org.a2lpo.bank.notownbank.repos.UserRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.MessageService;
import org.a2lpo.bank.notownbank.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/control")
public class ManageController {

    private final UserRepo userRepo;
    private final ManagerRepo managerRepo;
    private final RoleRepo roleRepo;
    private final RoleService roleService;
    private final MessageService messageService;

    public ManageController(UserRepo userRepo,
                            ManagerRepo managerRepo,
                            RoleRepo roleRepo,
                            RoleService roleService,
                            MessageService messageService) {
        this.userRepo = userRepo;
        this.managerRepo = managerRepo;
        this.roleRepo = roleRepo;
        this.roleService = roleService;
        this.messageService = messageService;
    }

    @GetMapping
    private void checkClient() {
//        boolean isFind =
//                user.getRoles()
//                        .stream()
//                        .map(Role::getName)
//                        .anyMatch(n -> n == RoleName.ROLE_CLIENT);
    }

    /**
     * Метод создания менеджера, принимает на вход addRequest существующего пользователя
     * проверяет наличие пользователя в базе, если пользователь не найден выкидывает
     * Exception и сообщение пользователю `User not found`, затем выполняется проверка
     * на отсутствие у данного пользователя Role_Manager если условие истинна, то создает
     * менеджера в таблице Manager в БД, сохраняет его, вызывает метод
     *
     * @param addRequest    payload ManagerAddRequest от пользователя приходит в виде JSON, содержит
     *                      user_id, Manager_FirstName, Manager_LastName.
     * @param userPrincipal авторизированный пользователь.
     * @return возвращает Response содержащий payload ApiResponse который содержит
     * статус выполнения операции с сообщением в формате JSON.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> newManager(
            @Valid @RequestBody ManagerAddRequest addRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        boolean isManager;
        Optional<User> userById = userRepo.findById(addRequest.getUserId());

        /* Проверка есть ли у `Optional<User> userById` ROLE_MANAGER, юзера с указанным id
        не существует то по exception выкинет пользователю сообщение об отсутствии такого юзера
         */
        try {
            isManager = userById.get().getRoles()
                    .stream()
                    .map(Role::getName)
                    .anyMatch(n -> n == RoleName.ROLE_MANAGER);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ApiResponse(false, "User not found"),
                    HttpStatus.NO_CONTENT);
        }
        /*
        Если user не имеет ROLE_MANAGER то создаем менеджера в таблице, сохраняем его и добавляем к его ролям
        ROLE_MANAGER в случае ошибки сохранения менеджера кидаем ексепшн пользователю, после сохранения
        создаем сообщение для админа с урлом на данного менеджера.
        если у user`а есть данная роль, то кидаем пользователю сообщение о том что этот юзер уже менеджер
         */
        if (!isManager) {
            Manager manager = new Manager(addRequest.getFirstName(),
                    addRequest.getLastName(),
                    userById.get(),
                    userRepo.findByUsername(userPrincipal.getUsername()).get());
            try {
                managerRepo.save(manager);
                userRepo.save(roleService.addRole(
                        userById.get(),
                        RoleName.ROLE_MANAGER)
                );
            } catch (Exception e) {
                //todo вставить сюда логирование
                return new ResponseEntity<>(new ApiResponse(false, "Something Shit"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/control/" + manager.getUniqId())
                    .buildAndExpand(userById.get().getUsername()).toUri();
            messageService.createServiceMessage("Registration manager", location, manager);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "User is manager"),
                    HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(new ApiResponse(true, "Manager added"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{uuid}")
    public ResponseEntity<?> loadManager(@PathVariable("uuid") String uuid) {
        ManagerDetailsResponse managerDetails;

        Optional<Manager> manager = managerRepo.findByUniqId(uuid);
        if (manager.isPresent()) {
            return ResponseEntity.ok(
                    new ManagerDetailsResponse(manager.get(),
                    new CreatedByResponse(manager.get().getCreatedBy())));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Uniq Id not found"),
                    HttpStatus.NOT_FOUND);
        }
    }
}
