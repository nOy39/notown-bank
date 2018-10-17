package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.exceptions.NoEntityException;
import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.model.accounts.Currency;
import org.a2lpo.bank.notownbank.model.accounts.CurrencyName;
import org.a2lpo.bank.notownbank.model.accounts.TypeAccountName;
import org.a2lpo.bank.notownbank.payload.*;
import org.a2lpo.bank.notownbank.repos.AccountRepo;
import org.a2lpo.bank.notownbank.repos.ClientRepo;
import org.a2lpo.bank.notownbank.repos.CurrencyRepo;
import org.a2lpo.bank.notownbank.repos.TypeRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.AccountService;
import org.a2lpo.bank.notownbank.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Рест контроллер для работы с персональными счетами клиентов
 */
@RestController
@RequestMapping("/api/account")
public class AccountsController {


    private final ClientRepo clientRepo;
    private final CurrencyRepo currencyRepo;
    private final AccountRepo accountRepo;
    private final TypeRepo typeRepo;
    private final AccountService accountService;
    private final ClientsService clientsService;

    @Autowired
    public AccountsController(ClientRepo clientRepo,
                              CurrencyRepo currencyRepo,
                              AccountRepo accountRepo,
                              TypeRepo typeRepo,
                              AccountService accountService,
                              ClientsService clientsService) {
        this.clientRepo = clientRepo;
        this.currencyRepo = currencyRepo;
        this.accountRepo = accountRepo;
        this.typeRepo = typeRepo;
        this.accountService = accountService;
        this.clientsService = clientsService;
    }

    /**
     * TODO расписать ошибки нормально, задокументировать
     * POST-метод создания нового счета, доступен только для пользователей с ролью CLIENT.
     * Предварительно выполняются следующие проверки.
     * 1. Проверяется тип счёта который хочет создать клиент, если счёт кредитный то счёт не создается.
     * 2. Проверка пользователя на регистрацию как клиента и установленного статуса isActive.
     * 3. проверка на наличие счёта подобного типа используемого
     * по-умолчанию. Если такого счета нет то при создании счета ему выставляется значение "default"
     *
     * @param createRequest запрос на создания счёта может содержать одно из трех параметров тип
     *                      счета(DEBIT, ACUMM, CREDIT) и тип валюты (RUB, USD, EUR)
     *                      клиент не может самостоятельно открыть себе кредитовый счет.
     * @param userPrincipal авторизированный пользователь который создает себе счёт
     * @return возвращает <code>ApiResponse</code> содержащий в себе 2 значение
     * <code>boolean status</code> - состояние выполненной операции и <code>String message</code> - текстовое сообщение
     * о результатах выполненной операции
     */
    @PostMapping
    @RequestMapping("new")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse> createAccount(@Valid @RequestBody CreateAccountRequest createRequest,
                                                     @CurrentUser UserPrincipal userPrincipal) {

        if (createRequest.getTypeAccountName() == TypeAccountName.CREDIT)
            return new ResponseEntity<>(
                    new ApiResponse(false,
                            "In order to open a credit account you must fill out a credit form, go to a special section for this."),
                    HttpStatus.BAD_REQUEST);
        //поиск указанной валюты.
        Optional<Currency> optionalCurrency = currencyRepo.findByName(createRequest.getCurrencyName());
        optionalCurrency.orElseThrow(() -> new NoEntityException("Currency", "ererer"));

        Optional<Client> optionalClient = clientRepo.findByUserId(userPrincipal.getId());
        boolean checkedClients = optionalClient.isPresent() && optionalClient.get().isActive();
        if (checkedClients) {
            PersonalAccount personalAccount = new PersonalAccount(optionalClient.get(),
                    optionalCurrency.get(),
                    typeRepo.findByType(createRequest.getTypeAccountName()).get());
            if (!accountRepo.findDefaultAccounts(optionalClient.get().getId(), optionalCurrency.get().getId()).isPresent()) {
                personalAccount.setDefault(true);
            }
            accountRepo.save(personalAccount);


            return ResponseEntity.ok(new ApiResponse(true, "piu-piu..."));

        } else {
            return new ResponseEntity<>(new ApiResponse(false, "no user or user no active calling manager"), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Метод выводит список счетов пользователя.
     * todo задокументировать
     *
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
                                account.getCurrency().getName(),
                                account.getTypeAccount().getType())
                        )
                );
        return accountList;
    }

    /**
     * Перевод денег между одинаковыми счетами от одного клиента к другому
     * Сначало выполняется проверка на то, что счет
     * todo задокументировать
     *
     * @param userPrincipal
     * @param paymentRequest
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Object> sendMoney(@CurrentUser UserPrincipal userPrincipal,
                                            @Valid @RequestBody PaymentRequest paymentRequest) {
        Optional<PersonalAccount> toAccount = accountRepo.findCheckByUniqID(paymentRequest.getToAccountId());
        if (!toAccount.isPresent())
            return new ResponseEntity<>(new ApiResponse(false, "Uniq account not found"), HttpStatus.BAD_REQUEST);
        Optional<PersonalAccount> fromAccount = accountRepo.findCheckByUniqID(paymentRequest.getFromAccountId());
        boolean securePaymentDone = fromAccount.isPresent() &&
                fromAccount.get().getClient().getUser().getId().equals(userPrincipal.getId()) &&
                !fromAccount.get().isBlocked();
        if (!securePaymentDone)
            return new ResponseEntity<>(new ApiResponse(false, "Some security error"), HttpStatus.INTERNAL_SERVER_ERROR);


        ApiResponse apiResponse = accountService.makeTransfer(fromAccount.get(), toAccount.get(), paymentRequest.getSum());
        if (apiResponse.getSuccess()) {
            return ResponseEntity.ok(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обмен валюты между своими счетами валюты.
     * todo задокументировать, отрефакторить сделать правильную проверку на корректность операции с нормальными выводами по ситуативным ошибкам
     * todo сделать красиво в requeste `sum` можно запутаться с тем, что, куда, переводим...
     * todo проработать исключительные ситуации
     *
     * @param userPrincipal
     * @param changeRequest
     * @return
     * @throws IOException
     */
    @PostMapping
    @RequestMapping("change")
    public ResponseEntity<ApiResponse> buyCurrency(
            @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody ChangeCurrencyRequest changeRequest) throws IOException {

        Optional<PersonalAccount> optionalFrom = accountRepo.findCheckByUniqID(changeRequest.getFromAccountId());
        Optional<PersonalAccount> optionalTo = accountRepo.findCheckByUniqID(changeRequest.getToAccountId());

        clientsService.checkSourceData(changeRequest, optionalFrom, optionalTo, userPrincipal);
        boolean isCorrectAccounts = optionalFrom.isPresent() && optionalTo.isPresent() &&
                !optionalFrom.get().isBlocked() && !optionalTo.get().isBlocked() &&
                optionalFrom.get().getClient().getId().equals(optionalTo.get().getClient().getId());

        if (!isCorrectAccounts) return new ResponseEntity<>(new ApiResponse(
                false, "Some account not correct..."), HttpStatus.BAD_REQUEST);
        boolean isCorrectSellRequest = userPrincipal.getId().equals(optionalFrom.get().getClient().getUser().getId());
        if (!isCorrectSellRequest) return new ResponseEntity<>(new ApiResponse(
                false, "Request not correct"), HttpStatus.BAD_REQUEST);

        ApiResponse apiResponse = accountService.changeCurrency(optionalFrom.get(), optionalTo.get(), changeRequest.getSum());
        if (apiResponse.getSuccess()) return ResponseEntity.ok(apiResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * todo задокументировать, отрефакторить сделать правильную проверку на корректность операции с нормальными выводами по ситуативным ошибкам
     * todo сделать красиво в requeste `sum` можно запутаться с тем, что, куда, переводим...
     * todo проработать исключительные ситуации
     *
     * @param userPrincipal
     * @param sellRequest
     * @return
     * @throws IOException
     */
    @PostMapping
    @RequestMapping("sold")
    public ResponseEntity<ApiResponse> soldCurrency(
            @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody SellCurrencyRequest sellRequest) throws IOException {
        Optional<PersonalAccount> optionalFrom = accountRepo.findCheckByUniqID(sellRequest.getFromAccountId());
        Optional<PersonalAccount> optionalTo = accountRepo.findDefaultAccounts(
                optionalFrom.get().getClient().getId(),
                currencyRepo.findByName(CurrencyName.RUB).get().getId()
        );
        boolean isCorrectAccount = optionalFrom.isPresent() && optionalTo.isPresent() &&
                !optionalFrom.get().isBlocked() &&
                userPrincipal.getId().equals(optionalFrom.get().getClient().getUser().getId()) &&
                optionalTo.get().getCurrency().getName() == CurrencyName.RUB;
        if (!isCorrectAccount) return new ResponseEntity<>(new ApiResponse(
                false, "Some account not correct..."), HttpStatus.BAD_REQUEST);
        ApiResponse apiResponse = accountService.changeCurrency(optionalFrom.get(), optionalTo.get(), sellRequest.getSum());
        if (apiResponse.getSuccess()) return ResponseEntity.ok(apiResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * todo задокументировать, отрефакторить сделать правильную проверку на корректность операции с нормальными выводами по ситуативным ошибкам
     * todo сделать красиво в requeste `sum` можно запутаться с тем, что, куда, переводим...
     * todo проработать исключительные ситуации
     *
     * @param userPrincipal
     * @param buyRequest
     * @return
     * @throws IOException
     */
    @PostMapping
    @RequestMapping("buy")
    public ResponseEntity<ApiResponse> buyCurrency(
            @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody BuyCurrencyRequest buyRequest) throws IOException {

        Optional<PersonalAccount> optionalTo = accountRepo.findCheckByUniqID(buyRequest.getToAccountId());
        Optional<PersonalAccount> optionalFrom = accountRepo.findDefaultAccounts(
                optionalTo.get().getClient().getId(),
                currencyRepo.findByName(CurrencyName.RUB).get().getId()
        );
//        boolean isCorrectAccount = optionalFrom.isPresent() && optionalTo.isPresent() &&
//                optionalFrom.get().isBlocked() && optionalTo.get().isBlocked() &&
//                userPrincipal.getId().equals(optionalFrom.get().getClient().getUser().getId()) &&
//                userPrincipal.getId().equals(optionalTo.get().getClient().getUser().getId()) &&
//                optionalFrom.get().getCurrency().getName() == CurrencyName.RUB;
//        if (!isCorrectAccount) return new ResponseEntity<>(new ApiResponse(
//                false, "Some account not correct..."), HttpStatus.BAD_REQUEST);
        ApiResponse apiResponse = accountService.changeCurrency(optionalFrom.get(), optionalTo.get(), buyRequest.getSum());
        if (apiResponse.getSuccess()) return ResponseEntity.ok(apiResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
