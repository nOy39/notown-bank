package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.Manager;
import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.a2lpo.bank.notownbank.payload.*;
import org.a2lpo.bank.notownbank.repos.ManagerRepo;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//todo сделать блокировку менеджера,
//todo отрефакторить класс,
//todo задокументировать оставшиеся методы в этом классе и написать коментарии
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/control")
public class AdminController {

    private final UserRepo userRepo;
    private final ManagerRepo managerRepo;
    private final RoleService roleService;
    private final MessageService messageService;

    public AdminController(UserRepo userRepo,
                           ManagerRepo managerRepo,
                           RoleService roleService,
                           MessageService messageService) {
        this.userRepo = userRepo;
        this.managerRepo = managerRepo;
        this.roleService = roleService;
        this.messageService = messageService;
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
    @PostMapping("/managers/add")
    public ResponseEntity<ApiResponse> newManager(
            @Valid @RequestBody ManagerAddRequest addRequest,
            @CurrentUser UserPrincipal userPrincipal) {

        Optional<User> userById = userRepo.findById(addRequest.getUserId());

        boolean isManager = userById.isPresent() && userById.get().getRoles()
                .stream()
                .map(Role::getName)
                .anyMatch(n -> n == RoleName.ROLE_MANAGER);

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
            manager.setPersonalPage(ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/control/managers/" + manager.getUniqId())
                    .buildAndExpand()
                    .toUri());
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
            //todo рефакторинг сообщения
            messageService.createServiceMessage("Registration manager",
                    manager.getPersonalPage(),
                    manager);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "User is manager"),
                    HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(new ApiResponse(true, "Manager added"));
    }

    /**
     * Персональная  карточка Менеджера, метод делает поиск по uuid менеджера,
     * в базе, если менеджера нет, кидает пользователю ошибку
     *
     * @param uuid Уникальный id менджера, получает из пути
     * @return возвращает ResponseEntity
     */
    @GetMapping("/managers/{uuid}")
    public ResponseEntity<Object> loadManager(@PathVariable("uuid") String uuid) {

        Optional<Manager> manager = managerRepo.findByUniqId(uuid);
        if (manager.isPresent()) {
            return ResponseEntity.ok(
                    new ManagerDetailsResponse(manager.get(),
                            new CreatedByResponse(manager.get().getCreatedBy()),
                            new BlockedAtResponse(manager.get().getBlockedAt())));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Uniq Id not found"),
                    HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Список всех менеджеров в формате json
     *
     * @return
     */
    @GetMapping("/managers/list")
    public ResponseEntity<Object> getManagersList() {

        List<ManagerListResponse> managerList = new ArrayList<>();
        managerRepo.findAll().forEach(m ->
            managerList.add(new ManagerListResponse(
                    m.getId(),
                    m.getFirstName(),
                    m.getLastName(),
                    m.getUniqId(),
                    m.getPersonalPage(),
                    m.getBlocked() == null))
        );
        return ResponseEntity.ok(managerList);
    }

    //TODO раскидать ексепшены, подключить логи
    @PostMapping("/managers/{uuid}")
    public ResponseEntity<Object> modifyManager(@PathVariable("uuid") String uuid,
                                                @Valid @RequestBody ManagerEditRequest managerRequest,
                                                @CurrentUser UserPrincipal currentUser) {
        Optional<Manager> byUniqId = managerRepo.findByUniqId(uuid);
        if (byUniqId.isPresent()) {
            Manager currentManager = byUniqId.get();
            if (!managerRequest.getFirstName().isEmpty()) {
                currentManager.setFirstName(managerRequest.getFirstName());
            }
            if (!managerRequest.getLastName().isEmpty()) {
                currentManager.setLastName(managerRequest.getLastName());
            }
            if (!managerRequest.isActive() && currentManager.getBlocked() == null) {
                currentManager.setActive(false);
                currentManager.setBlocked(LocalDateTime.now());
                currentManager.setBlockedAt(userRepo.findById(currentUser.getId()).get());
            }
            managerRepo.save(currentManager);
        }
        return ResponseEntity.ok(new ApiResponse(true, "Manager was modified"));
    }

}
