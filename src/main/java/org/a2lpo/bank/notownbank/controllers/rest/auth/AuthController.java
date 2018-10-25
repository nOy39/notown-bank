package org.a2lpo.bank.notownbank.controllers.rest.auth;

import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.Manager;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.a2lpo.bank.notownbank.payload.*;
import org.a2lpo.bank.notownbank.repos.AccountRepo;
import org.a2lpo.bank.notownbank.repos.ClientRepo;
import org.a2lpo.bank.notownbank.repos.ManagerRepo;
import org.a2lpo.bank.notownbank.repos.UserRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.JwtTokenProvider;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final RoleService roleService;
    private final ClientRepo clientRepo;
    private final ManagerRepo managerRepo;
    private final AccountRepo accountRepo;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepo userRepo,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider tokenProvider,
                          RoleService roleService,
                          ClientRepo clientRepo,
                          ManagerRepo managerRepo,
                          AccountRepo accountRepo) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.roleService = roleService;
        this.clientRepo = clientRepo;
        this.managerRepo = managerRepo;
        this.accountRepo = accountRepo;
    }

    /**
     * Краткая информация об авторизированном клиенте
     *
     * @param currentUser
     * @return
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse detailsUser(@CurrentUser UserPrincipal currentUser) {

        return new UserResponse(
                currentUser.getUsername(),
                currentUser.getEmail(),
                new ArrayList<>(
                        currentUser.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())));
    }

    /**
     * Метод авторизации пользователя
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Optional<Client> optionalClientByUserId = clientRepo.findByUserId(userPrincipal.getId());
        if (optionalClientByUserId.isPresent()) {
            return ResponseEntity.ok(new AuthenticationClientResponse(jwt,
                    RoleName.ROLE_CLIENT,
                    optionalClientByUserId.get(),
                    accountRepo.findAllByClient_User_Id(userPrincipal.getId())));
        }
        Optional<Manager> optionalManagerByUserId = managerRepo.findByUser_Id(userPrincipal.getId());
        return optionalManagerByUserId.<ResponseEntity<?>>map(manager -> ResponseEntity.ok(new AuthenticationManagerResponse(jwt,
                RoleName.ROLE_MANAGER,
                manager) {
        })).orElseGet(() -> ResponseEntity.ok(new JwtAuthenticationResponse(jwt, (UserPrincipal) authentication.getPrincipal())));
    }

    /**
     * Регистрация
     *
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepo.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(roleService.addRole(user, RoleName.ROLE_USER));

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,
                "User registered successfully"));
    }

    /**
     * Возвращает userinfo
     * @param userPrincipal
     * @return
     */
    @GetMapping("/signin")
    public ResponseEntity<?> userInfo(@CurrentUser UserPrincipal userPrincipal) {
        Optional<Client> optionalClient = clientRepo.findByUserId(userPrincipal.getId());
        Optional<Manager> optionalManager = managerRepo.findByUser_Id(userPrincipal.getId());
        return optionalClient.<ResponseEntity<?>>map(
                client -> ResponseEntity.ok(
                        new AuthenticationClientResponse("",
                                RoleName.ROLE_CLIENT,
                                client, accountRepo.findAllByClient_User_Id(userPrincipal.getId()))))
                .orElseGet(
                        () -> ResponseEntity.ok(
                                new AuthenticationManagerResponse("",
                                        RoleName.ROLE_MANAGER,
                                        optionalManager.get()) {
                                }));
    }
}
