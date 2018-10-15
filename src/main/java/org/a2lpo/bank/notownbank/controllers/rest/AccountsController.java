package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.accounts.Account;
import org.a2lpo.bank.notownbank.model.accounts.TypeAccountName;
import org.a2lpo.bank.notownbank.payload.AccountListResponse;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.payload.CreateAccountRequest;
import org.a2lpo.bank.notownbank.payload.PaymentResponse;
import org.a2lpo.bank.notownbank.repos.AccountRepo;
import org.a2lpo.bank.notownbank.repos.ClientRepo;
import org.a2lpo.bank.notownbank.repos.CurrencyRepo;
import org.a2lpo.bank.notownbank.repos.TypeRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountsController {


    private final ClientRepo clientRepo;
    private final CurrencyRepo currencyRepo;
    private final AccountRepo accountRepo;
    private final TypeRepo typeRepo;
    private final AccountService accountService;

    @Autowired
    public AccountsController(ClientRepo clientRepo,
                              CurrencyRepo currencyRepo,
                              AccountRepo accountRepo,
                              TypeRepo typeRepo,
                              AccountService accountService) {
        this.clientRepo = clientRepo;
        this.currencyRepo = currencyRepo;
        this.accountRepo = accountRepo;
        this.typeRepo = typeRepo;
        this.accountService = accountService;
    }

    /**
     * TODO расписать ошибки нормально, задокументировать
     * Открытие счета
     *
     * @param createRequest
     * @param userPrincipal
     * @return
     */
    @PostMapping
    @RequestMapping("new")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody CreateAccountRequest createRequest,
                                                @CurrentUser UserPrincipal userPrincipal) {
        if (createRequest.getTypeAccountName() != TypeAccountName.CREDIT) {
            Optional<Client> optionalClient = clientRepo.findByUserId(userPrincipal.getId());
            if (optionalClient.isPresent() && optionalClient.get().isActive()) {

                Account account = new Account(optionalClient.get(),
                        currencyRepo.findByName(createRequest.getCurrencyName()).get(),
                        typeRepo.findByType(createRequest.getTypeAccountName()).get());
                accountRepo.save(account);
                return ResponseEntity.ok(new ApiResponse(true, "piu-piu..."));

            } else {
                return new ResponseEntity<>(new ApiResponse(false, "no user or user no active calling manager"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new ApiResponse(false, "no credit"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод выводит список счетов пользователя.
     * @param userPrincipal
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public List<AccountListResponse> accountList(@CurrentUser UserPrincipal userPrincipal) {
        List<AccountListResponse> accountList = new ArrayList<>();
        accountRepo.findAllByClient_User_Id(userPrincipal.getId())
                .forEach(account ->
                        accountList.add(new AccountListResponse(
                                account.getUniqCheckId(),
                                account.getSum(),
                                account.getUpdatedAt(),
                                account.getCurrencyName().getName(),
                                account.getTypeAccount().getType())
                        )
                );
        return accountList;
    }

    /**
     * Перевод денег между одинаковыми счетами от одного клиента к другому
 * Сначало выполняется проверка на то, что счет
     * @param userPrincipal
     * @param paymentResponse
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Object> sendMoney(@CurrentUser UserPrincipal userPrincipal,
                                            @Valid @RequestBody PaymentResponse paymentResponse) {
        Optional<Account> toAccount = accountRepo.findCheckByUniqID(paymentResponse.getToAccountId());
        if (!toAccount.isPresent()) return new ResponseEntity<>(new ApiResponse(false, "Uniq account not found"), HttpStatus.BAD_REQUEST);
        Optional<Account> fromAccount = accountRepo.findCheckByUniqID(paymentResponse.getFromAccountId());
        boolean securePaymentDone = fromAccount.isPresent() &&
                fromAccount.get().getClient().getUser().getId().equals(userPrincipal.getId()) &&
                !fromAccount.get().isBlocked();
        if (!securePaymentDone) return new ResponseEntity<>(new ApiResponse(false, "Some security error"), HttpStatus.INTERNAL_SERVER_ERROR);


        ApiResponse apiResponse = accountService.makeTransfer(fromAccount.get(), toAccount.get(), paymentResponse.getSum());
        if (apiResponse.getSuccess()) {
            return ResponseEntity.ok(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
