package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.payload.ClientRequest;
import org.a2lpo.bank.notownbank.repos.ClientRepo;
import org.a2lpo.bank.notownbank.repos.UserRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Value("${app.message.internal}")
    private String internalErrorMessage;

    private final ClientRepo clientRepo;
    private final UserRepo userRepo;
    private final RoleService roleService;

    @Autowired
    public ClientController(ClientRepo clientRepo,
                            UserRepo userRepo,
                            RoleService roleService) {
        this.clientRepo = clientRepo;
        this.userRepo = userRepo;
        this.roleService = roleService;
    }

    /**TODO подлежит нормальному документированию.
     * Саморегистрация пользователя как клиента банка, совершается проверка на совершеннолетие пользвателя,
     * на то не является ли пользователь уже клиентом банка и если не является то создает клиента
     * из запроса ClientRequest
     * @param userPrincipal
     * @param clientRequest
     * @return
     */
    @PostMapping("/add/self")
    public ResponseEntity<Object> registration(@CurrentUser UserPrincipal userPrincipal,
                                               @Valid @RequestBody ClientRequest clientRequest) {
        //проверка на совершеннолетие.
        boolean isAdult = ChronoUnit.YEARS.between(clientRequest.getDateOfBirth(), LocalDate.now()) > 16;
        if (!isAdult) {
            return new ResponseEntity<>(new ApiResponse(false,
                    "Too young to register in the customer database"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //проверка пользователя на то, что он новый клиент
        Optional<User> userOptional = userRepo.findByUsername(userPrincipal.getUsername());
        boolean isNewClient = userOptional.isPresent() &&
                userOptional.get().getRoles()
                        .stream()
                        .map(Role::getName)
                        .noneMatch(n -> n == RoleName.ROLE_CLIENT ||
                                        n == RoleName.ROLE_MANAGER ||
                                        n == RoleName.ROLE_ADMIN) &&
                !clientRepo.findByUserId(userPrincipal.getId()).isPresent();

        //Создание нового клиента
        if (isNewClient) {
            Client newClient = new Client(clientRequest.getFirstName(),
                    clientRequest.getLastName(),
                    clientRequest.getPhone(),
                    clientRequest.getDateOfBirth(),
                    userOptional.get());
            try {
                roleService.addRole(userOptional.get(), RoleName.ROLE_CLIENT);
                clientRepo.save(newClient);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, "Something Shit... 乁( ͡ಠ ʖ̯ ͡ಠ)ㄏ"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "TYou are already a customer of the Bank," +
                    " re-registration is not possible."), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse(true, "Congratulations, you are registered as a Bank customer," +
                " you can now fully operate the system."));
    }
}
