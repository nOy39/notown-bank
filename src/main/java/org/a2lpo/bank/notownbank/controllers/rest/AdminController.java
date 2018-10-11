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
import org.a2lpo.bank.notownbank.service.ManagerService;
import org.a2lpo.bank.notownbank.service.MessageService;
import org.a2lpo.bank.notownbank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO Попробовать responseEntity сделать с header`om, и body
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/control")
public class AdminController {
    @Value("${app.message.internal}")
    private String internalErrorMessage;

    private final UserRepo userRepo;
    private final ManagerRepo managerRepo;
    private final RoleService roleService;
    private final MessageService messageService;
    private final ManagerService managerService;

    @Autowired
    public AdminController(UserRepo userRepo,
                           ManagerRepo managerRepo,
                           RoleService roleService,
                           MessageService messageService,
                           ManagerService managerService) {
        this.userRepo = userRepo;
        this.managerRepo = managerRepo;
        this.roleService = roleService;
        this.messageService = messageService;
        this.managerService = managerService;
    }


    /**
     * Метод создания менеджера, принимает на вход addRequest существующего пользователя
     * проверяет наличие пользователя в базе, если пользователь не найден выкидывает
     * Exception и сообщение пользователю `User not found`, затем выполняется проверка
     * на отсутствие у данного пользователя Role_Manager если условие истинна, то создает
     * менеджера в таблице Manager в БД, сохраняет его, вызывает метод
     *
     * @param addRequest payload ManagerAddRequest от пользователя приходит в виде JSON, содержит
     *                   user_id, Manager_FirstName, Manager_LastName.
     * @return возвращает Response содержащий payload ApiResponse который содержит
     * статус выполнения операции с сообщением в формате JSON.
     */
    @PostMapping("/managers/add")
    public ResponseEntity<ApiResponse> newManager(
            @Valid @RequestBody ManagerAddRequest addRequest) {

        Optional<User> userById = userRepo.findById(addRequest.getUserId());
        boolean isVerifiedUser = userById.isPresent() &&
                userById.get().getRoles()
                        .stream()
                        .map(Role::getName)
                        .noneMatch(n -> n == RoleName.ROLE_MANAGER || n == RoleName.ROLE_CLIENT) &&
                !managerRepo.findByUser(userById.get()).isPresent();

        if (isVerifiedUser) {
            Manager manager = new Manager(addRequest.getFirstName(),
                    addRequest.getLastName(),
                    userById.get());
            manager.setPersonalPage(ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/control/managers/" + manager.getUniqId())
                    .buildAndExpand()
                    .toUri().toString());
            try {
                managerRepo.save(manager);
                userRepo.save(roleService.addRole(  //Сохранение манагера предварительно добавив ему роль ROLE_MANAGER
                        userById.get(),
                        RoleName.ROLE_MANAGER)
                );
            } catch (Exception e) {
                //todo вставить сюда логирование
                return new ResponseEntity<>(new ApiResponse(false, "Something Shit... 乁( ͡ಠ ʖ̯ ͡ಠ)ㄏ"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            messageService.createServiceMessage("Registration manager", manager.getPersonalPage());
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "This user can't complete an assignment. " +
                    "You can see personal page of that user, maybe user already has \"manager\" or \"clients\" roles," +
                    " or the user does not exist"),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse(true, "Manager was created. "
                + userById.get().getUsername() + " has been added to the role 'MANAGER'"));
    }

    /**
     * Персональная  карточка Менеджера, метод делает поиск по uuid менеджера,
     * в базе, если менеджера нет, кидает пользователю ошибку
     *
     * @param managerUUId Уникальный номер менеджера.
     * @return возвращает ManagerDetailsResponse
     */
    @GetMapping("/managers/{uuid}")
    public ResponseEntity<Object> loadManager(@PathVariable("uuid") String managerUUId) {
        Optional<Manager> manager = managerRepo.findByUniqId(managerUUId);
        if (manager.isPresent()) {
            return ResponseEntity.ok(
                    new ManagerDetailsResponse(manager.get(),
                            new UserAuditResponse(userRepo.findById(
                                    manager.get().getCreatedBy()).get()
                            ),
                            new UserAuditResponse(userRepo.findById(
                                    manager.get().getUpdateBy()).get()
                            )
                    ));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Uniq Id not found"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Список всех менеджеров в формате json
     *
     * @return возвращает список ManagerListResponse
     */
    @GetMapping("/managers/list")
    public ResponseEntity<List<ManagerListResponse>> getManagersList() {

        List<ManagerListResponse> managerList = new ArrayList<>();
        managerRepo.findAll().forEach(m ->
                managerList.add(new ManagerListResponse(m))
        );
        return ResponseEntity.ok(managerList);
    }

    /**
     * Редактирование данных менеджера, редактированию подлежат 3 поля firstName, lastName, isActive.
     * Поиском из репозитория ищем менеджера по UUID при условии, что его поле isActive == true,
     * если находится такой менеджер поочередно сравниваем поля manager(firstName, lastName, isActive)
     * с полями из запроса managerRequest(firstName, lastName, isActive) сравниваем на "если поле в запросе измененно"
     * то делаем замену, если поле isActive == false, то выполняем метод blockManager из сервиса ManagerService.
     * После того как менеджер заблокируется в таблице отправляем пользователю ответ с детальной информацией о текущем менеджере.
     * Если менеджер не был найден, то пользователю кидаем страницу с сообщением со статусом 400.
     *
     * @param managerUUId    Уникальный номер менеджера
     * @param managerRequest json запрос от пользователя содержащий 3 поля (firstName, lastName, isActive)
     * @param userPrincipal  текущий авторизированный пользователь который будет занесён в таблицу как пользователь
     *                       заблокировавший данного менеджера
     * @return - ответ пользователю в виде ResponseEntity содержащий json в теле и HTTP.status
     */
    @PutMapping("/managers/{uuid}")
    public ResponseEntity<Object> modifyManager(@PathVariable("uuid") String managerUUId,
                                                @RequestBody CurrentManagerRequest managerRequest,
                                                @CurrentUser UserPrincipal userPrincipal) {
        Long id;
        try {
            id = Long.valueOf(managerUUId);
        } catch (Exception e) {
            id = null;
        }
        Optional<Manager> byUniqId = managerRepo.searchActiveManagerByUniqId(managerUUId, id);
        if (byUniqId.isPresent()) {
            Manager manager = byUniqId.get();
            if (!manager.getFirstName().isEmpty() &&
                    !manager.getFirstName().equals(managerRequest.getFirstName()) &&
                    managerRequest.getFirstName() != null) {
                manager.setFirstName(managerRequest.getFirstName());
            }
            if (!manager.getLastName().isEmpty() &&
                    !manager.getLastName().equals(managerRequest.getLastName()) &&
                    managerRequest.getLastName() != null) {
                manager.setLastName(managerRequest.getLastName());
            }
            if (manager.isActive() != managerRequest.isActive() &&
                    manager.isActive()) {
                managerService.blockManager(manager, userRepo.findById(userPrincipal.getId()).get());
            }
            managerRepo.save(manager);

            return ResponseEntity.ok(new ManagerDetailsResponse(manager,
                    new UserAuditResponse(userRepo.findById(
                            manager.getCreatedBy()).get()
                    ),
                    new UserAuditResponse(userRepo.findById(
                            manager.getUpdateBy()).get()
                    )
            ));
        } else {
            return new ResponseEntity<>(new ApiResponse(true, "Manager not found."),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
