package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.payload.ClientRequest;
import org.a2lpo.bank.notownbank.repos.ClientRepo;
import org.a2lpo.bank.notownbank.repos.RoleRepo;
import org.a2lpo.bank.notownbank.repos.UserRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.ClientsService;
import org.a2lpo.bank.notownbank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRepo clientRepo;
    private final ClientsService clientsService;
    private final UserService userService;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    public ClientController(ClientRepo clientRepo,
                            ClientsService clientsService,
                            UserService userService,
                            RoleRepo roleRepo,
                            UserRepo userRepo) {
        this.clientRepo = clientRepo;
        this.clientsService = clientsService;
        this.userService = userService;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }


    @PostMapping("/add")
    public ResponseEntity<?> registration(@CurrentUser UserPrincipal userPrincipal,
                                          @Valid @RequestBody ClientRequest clientRequest) {

        Optional<User> userExtract = userService.extractUser(userPrincipal);

        if (!clientRepo.findByUser(userExtract.get()).isPresent()) {
            Client newClient = new Client(clientRequest.getFirstName(),
                    clientRequest.getLastName(),
                    clientRequest.getPhone(),
                    clientRequest.getDateOfBirth(),
                    userExtract.get());
            try {
                clientRepo.save(newClient);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(false, "Something shit!!!"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "This user is already client."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(new ApiResponse(true, "New client is added"));
    }
}
