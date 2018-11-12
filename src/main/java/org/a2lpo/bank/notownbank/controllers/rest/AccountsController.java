package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.exceptions.NoEntityException;
import org.a2lpo.bank.notownbank.exceptions.VerificationNoEntityException;
import org.a2lpo.bank.notownbank.model.accounts.eav.Account;
import org.a2lpo.bank.notownbank.model.message.logging.Status;
import org.a2lpo.bank.notownbank.payload.AccountInfoResponse;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.payload.CreateAccountRequest;
import org.a2lpo.bank.notownbank.payload.PaymentRequest;
import org.a2lpo.bank.notownbank.repos.AccountRepo;
import org.a2lpo.bank.notownbank.repos.ClientRepo;
import org.a2lpo.bank.notownbank.repos.accounts.CurrencyRepo;
import org.a2lpo.bank.notownbank.repos.accounts.SubTypeRepo;
import org.a2lpo.bank.notownbank.repos.accounts.TypeRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.AccountService;
import org.a2lpo.bank.notownbank.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

/**
 * Рест контроллер для работы с персональными счетами клиентов
 */
@RestController
@PreAuthorize("hasRole('CLIENT')")
@RequestMapping("/api/account")
public class AccountsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ClientRepo clientRepo;
    private final CurrencyRepo currencyRepo;
    private final AccountRepo accountRepo;
    private final TypeRepo typeRepo;
    private final AccountService accountService;
    private final LoggingService loggingService;
    private final SubTypeRepo subTypeRepo;


    @Autowired
    public AccountsController(ClientRepo clientRepo,
                              CurrencyRepo currencyRepo,
                              AccountRepo accountRepo,
                              TypeRepo typeRepo,
                              AccountService accountService,
                              LoggingService loggingService,
                              SubTypeRepo subTypeRepo) {
        this.clientRepo = clientRepo;
        this.currencyRepo = currencyRepo;
        this.accountRepo = accountRepo;
        this.typeRepo = typeRepo;
        this.accountService = accountService;
        this.loggingService = loggingService;
        this.subTypeRepo = subTypeRepo;
    }

    /**
     * <b>Метод доступен только пользователям с Ролью Client</b>
     * GET Метод выводит в формате JSON список всех счетов клиента.
     * Метод находит все счета клиента в таблице, затем методом forEach() сериализует нужные значения в DTO
     * <code>AccountListResponse</code> и помещает его в список ArrayList.
     *
     * @param userPrincipal авторизированный пользователь в Spring Security Context
     * @return возвращает список счетов в <code>List<AccountListResponse></code>
     */
//    @GetMapping
//    @PreAuthorize("hasRole('CLIENT')")
//    public List<AccountListResponse> accountList(@CurrentUser UserPrincipal userPrincipal) {
//        List<AccountListResponse> accountList = new ArrayList<>();
//        accountRepo.findAllByAccountHolder_User_Id(userPrincipal.getId())
//                .forEach(account ->
//                        accountList.add(new AccountListResponse(
//                                account.getAccountNumber(),
//                                account.getSum(),
//                                account.getUpdatedAt(),
//                                account.getCurrency().getCurrencyName(),
//                                account.getType().getTypeName()
//                        )
//                ));
//        return accountList;
//    }

    /**
     * @param userPrincipal
     * @return
     */
    @GetMapping
    @RequestMapping("{UUID}")
    public ResponseEntity<Object> getAccountInfo(@CurrentUser UserPrincipal userPrincipal,
                                                 @PathVariable("UUID") String uuid) {
        Account account;
        try {
            account = accountRepo.findAccountByUUID(uuid).orElseThrow(
                    () -> new VerificationNoEntityException("Account %s not found.", uuid)
            );
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        boolean verificationUser = account
                .getAccountHolder()
                .getUser()
                .getId()
                .equals(userPrincipal.getId());
        if (verificationUser) {
            return ResponseEntity.ok(new AccountInfoResponse(account));
        }
        return new ResponseEntity<>(new ApiResponse(
                false,
                String.format("User %s not master account %s.", userPrincipal.getUsername(), uuid)),
                HttpStatus.BAD_REQUEST);
    }


    /**
     * <b>Метод доступен только пользователям с Ролью Client</b>
     * POST-метод создания нового счета.
     * Предварительно, перед созданием счета выполняются следующие проверки.
     * 1. Проверяется тип счёта который хочет создать клиент, если счёт кредитный то счёт не создается.
     * 2. Проверка пользователя на регистрацию в клиентской таблице и установленного статуса isActive.
     * 3. Поиск дефолтного счета той-же валюты которой пользователь желает создать,
     * если такого счета нет то при создании счета ему выставляется значение "default == true"
     *
     * @param createRequest запрос на создания счёта может содержать одно из трех параметров тип
     *                      счета(DEBIT, ACCUM, CREDIT) и тип валюты (RUB, USD, EUR).
     *                      Запрос приходит в JSON формате.
     * @param userPrincipal авторизированный пользователь который создает себе счёт
     * @return возвращает <code>ApiResponse</code> содержащий в себе 2 значение
     * <code>boolean status</code> - состояние выполненной операции и <code>String message</code> - текстовое сообщение
     * о результатах выполненной операции в JSON формате.
     */
    @PostMapping
    @RequestMapping("new")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse> createAccount(@Valid @RequestBody CreateAccountRequest createRequest,
                                                     @CurrentUser UserPrincipal userPrincipal) {
        //Проверка приходящего DTO на тип счёта, и валюты
        boolean checkedAccountRequest = createRequest.getTypeAccount().equalsIgnoreCase("debit") ||
                createRequest.getTypeAccount().equalsIgnoreCase("accum");
        if (checkedAccountRequest) {
            //noinspection OptionalGetWithoutIsPresent
            ApiResponse apiResponse = accountService.createPhysicalAccount(createRequest,
                    clientRepo.findByUserId(userPrincipal.getId()).get());
            return apiResponse.getSuccess() ?
                    ResponseEntity.ok(apiResponse) : new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new ApiResponse(false,
                        "In order to open a credit account you must fill out a credit form, " +
                                "go to a special section for this."), HttpStatus.BAD_REQUEST);
    }


    //проверка клиента

//
//            Account account = new Account();
//            //поиск дефолтного счета клиента
//            boolean hasDefaultAccount = accountRepo.findDefaultAccounts(
//                    optionalClient.get().getId(),
//                    currency.get().getId()).isPresent();
//            account.setDefault(!hasDefaultAccount);
//            accountRepo.save(account);
//
//            return ResponseEntity.ok(new ApiResponse(true,
//                    String.format("Congratulations, %s you created a new account. ClientCabinet StatusLog %s, Currency %s, ClientCabinet Number %s",
//                            account.getAccountHolder().getFirstName(),
//                            account.getTypeAccount().getType().toString(),
//                            account.getCurrency().getName(),
//                            account.getAccountNumber())));
//        }


    /**
     * <b>Метод доступен только пользователям с Ролью Client</b>
     * POST метод перевода денег между счетами.
     * Метод принимает от пользователя JSON содержащий номер счета отправителя, номер счета получателя и сумму перевода.<br>
     * Выполняются проверки:<br>
     * 1. Сверка счёта отправителя с пользователем зарегистрированным в системе.<br>
     * 2. Провека правильности ввода счёта покупателя.<br>
     * 3. Проверяет не заблокирован ли счёт отправителя.<br>
     * Если все проверки пройдены то вызывается метод <code>makeTransfer</code> из AccountService в котором выполняется перевод
     * и результат выполнения метода сохраняется в <code>apiResponse</code> если метод отработал корректно то результат возвращения
     * будет ResponseEntity.ok
     *
     * @param userPrincipal  авторизированный пользователь в Spring Security Context
     * @param paymentRequest - запрос от клиента в формате json содержит 3 поля (<code>String accountId</code>,
     *                       <code>String toAccountId</code>,<code>BigDecimal sum</code>) (счёт отправителя, счёт получателя, сумма перевода)
     * @return возвращает <code>ApiResponse</code> содержащий в себе 2 значение
     * <code>boolean status</code> - состояние выполненной операции и <code>String message</code> - текстовое сообщение
     * о результатах выполненной операции в JSON формате.
     */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Object> sendMoney(@CurrentUser UserPrincipal userPrincipal,
                                            @Valid @RequestBody PaymentRequest paymentRequest) {
        Optional<Account> toAccount = accountRepo.findActiveAccountByUUID(paymentRequest.getToAccountId());
        Optional<Account> fromAccount = accountRepo.findActiveAccountByUUID(paymentRequest.getFromAccountId());

        //проверка автора запроса, в том что он хозяин счёта
        boolean isMasterAccountFrom = fromAccount.isPresent() &&
                fromAccount.get().getAccountHolder().getUser().getId().equals(userPrincipal.getId());
        if (!isMasterAccountFrom)
            return new ResponseEntity<>(new ApiResponse(false,
                    String.format("The sender %s is not the master of the account %s.",
                            userPrincipal.getUsername(),
                            paymentRequest.getFromAccountId())),
                    HttpStatus.FORBIDDEN);

        //проверка на наличие счёта получателя
        if (!toAccount.isPresent())
            return new ResponseEntity<>(new ApiResponse(false,
                    "The recipient's account is not found, check the entered data."), HttpStatus.BAD_REQUEST);
        //проверка счёта на блокировку.
        boolean securePaymentDone = !fromAccount.get().isBlocked();
        if (!securePaymentDone)
            return new ResponseEntity<>(new ApiResponse(false,
                    String.format("%s account is blocked, transfer is not possible.",
                            paymentRequest.getFromAccountId())),
                    HttpStatus.BAD_REQUEST);

        ApiResponse apiResponse = accountService.makeTransfer(
                fromAccount.get(),
                toAccount.get(),
                paymentRequest.getSum());
        //проверка результата выполнения метода.
        if (apiResponse.getSuccess()) {
            return ResponseEntity.ok(apiResponse);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * <b>Метод доступен только пользователям с Ролью Client</b><br>
     * POST метод обмена валют. От пользователя приходит json запрос <code>PaymentRequest changeRequest</code>.<br>
     * В методе проходит 3 проверки.<br>
     * 1. Счета ищуться в базе и в случае если один из них не найден кидает NoEntityException<br>
     * 2. Выполняется проверка на то, что найденные счета не заблокированы.<br>
     * 3. Проверяется является ли авторизированный пользователь собственником счетов.<br>
     * При не удачной проверке любого из пунктов выходит из метода и кидает пользователю сообщение об ошибке.<br>
     * Если все проверки пройдены то вызывает метод <code>currencyOperation</code> метод возвращает объект класса
     * <code>ApiResponse</code>, в зависимости от результата выполнения по состоянию поля status определяем какой
     * ResponseEntity вернуть пользователю.
     *
     * @param userPrincipal авторизированный пользователь в Spring Security Context
     * @param changeRequest DTO запрос от клиента в формате json содержит 3 поля (<code>String accountId</code>,
     *                      <code>String toAccountId</code>,<code>BigDecimal sum</code>
     *                      (счёт списания, счёт зачисления, сумма списания)
     * @return возвращает <code>ApiResponse</code> содержащий в себе 2 значение
     * <code>boolean status</code> - состояние выполненной операции и
     * <code>String message</code> - текстовое сообщение
     * о результатах выполненной операции в JSON формате.
     */
    @PostMapping
    @RequestMapping("change")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ApiResponse> changeCurrency(@CurrentUser UserPrincipal userPrincipal,
                                                      @Valid @RequestBody PaymentRequest changeRequest) {
        Account from;
        Account to;
        try {   //проверка на правильность ввода счетов.
            from = accountRepo
                    .findActiveAccountByUUID(changeRequest.getFromAccountId())
                    .orElseThrow(() -> new VerificationNoEntityException("Not found account or %s is inactive, for client.",
                            changeRequest.getFromAccountId()));
            to = accountRepo.findActiveAccountByUUID(changeRequest.getToAccountId())
                    .orElseThrow(() -> new VerificationNoEntityException("Not found account or %s is inactive, for client.",
                            changeRequest.getToAccountId()));
        } catch (NoEntityException e) {
            loggingService.createLog(e.toString(), Status.ERROR);
            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
        }
        //проверка на блокировку счетов
        if (to.isBlocked() || from.isBlocked()) return new ResponseEntity<>(new ApiResponse(
                false, String.format("Accounts %s is blocked, transfer not possible.",
                from.isBlocked() ? from.getAccountNumber() : to.getAccountNumber())),
                HttpStatus.BAD_REQUEST);

        //проверка является ли пользователь хозяином счётов.
        boolean isMasterAccount = from.getAccountHolder().getUser().getId().equals(userPrincipal.getId()) &&
                to.getAccountHolder().getUser().getId().equals(userPrincipal.getId());
        if (!isMasterAccount) return new ResponseEntity<>(new ApiResponse(
                false, String.format("User %s is not the owner of the account %s",
                userPrincipal.getUsername(),
                from.getAccountNumber())),
                HttpStatus.FORBIDDEN);

//        ApiResponse apiResponse = accountService.currencyOperation(from, to, changeRequest.getSum());
//
//        if (apiResponse.getSuccess()) return ResponseEntity.ok(apiResponse);
//        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        return null;
    }

    /**
     * <b>Метод доступен только пользователям с Ролью Client</b><br>
     * POST метод продажи валюты в рубли. Зачисление с продажи валюты происходит на  дефолтный рублёвый счёт клиента.
     * От пользователя приходит json запрос <code>TradingCurrencyRequest sellRequest</code>.
     * Со счётом с которого списывается валюта и суммой списания, после выполнения проверок вызывается метод
     * <code>currencyOperation</code> в который параметром счёта получателя передается предварительно найденный дефолтный рублёвый счет<br>
     * Проверки:<br>
     * 1. Поиск счетов из json запроса и дефолтного счёта в базе. Для поиска счета отправителя в строке запроса указывается,
     * что счет не должен быть заблокирован, тем самым делается пассивная проверка счета на статус блокировки.<br>
     * 2. Проверка пользователя авторизированного в системе на принадлежность к счету отправителя<br>
     * <p>
     * При не удачной проверке любого из пунктов выходит из метода и кидает пользователю сообщение об ошибке.<br>
     * Если все проверки пройдены то вызывает метод <code>currencyOperation</code> метод возвращает объект класса
     * <code>ApiResponse</code>, в зависимости от результата выполнения по состоянию поля status определяем какой
     * ResponseEntity вернуть пользователю.
     *
     * @param userPrincipal авторизированный пользователь в Spring Security Context
     * @param sellRequest   JSON запрос от клиента на продажу валюты состоит из двух полей.
     *                      <code>String accountId</code>, <code>BigDecimal sum</code>
     *                      (счёт списания, сумма списания)
     * @return возвращает <code>ApiResponse</code> содержащий в себе 2 значение
     * <code>boolean status</code> - состояние выполненной операции и
     * <code>String message</code> - текстовое сообщение
     * о результатах выполненной операции в JSON формате.
     */
//    @PostMapping
//    @RequestMapping("sold")
//    @PreAuthorize("hasRole('CLIENT')")
//    public ResponseEntity<ApiResponse> soldCurrency(@CurrentUser UserPrincipal userPrincipal,
//                                                    @Valid @RequestBody TradingCurrencyRequest sellRequest) {
//        Optional<Currency> curRuble = currencyRepo.findByName(CurrencyName.RUB);
//        Account from;
//        Account to;
//        try {
//            //поиск и проверка номера счета.
//            from = accountRepo.findActiveAccountByUUID(sellRequest.getAccountId())
//                    .orElseThrow(() -> new VerificationNoEntityException("Not found account or %s is inactive, for client.",
//                            sellRequest.getAccountId()));
//
//            //noinspection OptionalGetWithoutIsPresent because curRuble cant be null
//            to = accountRepo.findDefaultAccounts(from.getAccountHolder().getId(), curRuble.get().getId())
//                    .orElseThrow(() -> new VerificationNoEntityException("Not found active, %s defaults account, for client %s %s",
//                            sellRequest.getAccountId(),
//                            from.getAccountHolder().getLastName()));
//
//        } catch (Exception e) {
//            logger.error("ERROR", e);
//            loggingService.createLog(e.toString(), Status.ERROR);
//            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
//        }
//        //проверка пользователя на принадлежность к счету отправителя
//        boolean isCorrectAccount = userPrincipal.getId().equals(from.getAccountHolder().getUser().getId());
//        if (!isCorrectAccount) return new ResponseEntity<>(new ApiResponse(
//                false,
//                String.format("User %s is not the owner of the account %s",
//                        userPrincipal.getUsername(),
//                        from.getAccountNumber())),
//                HttpStatus.FORBIDDEN);
//
//        ApiResponse apiResponse = accountService.currencyOperation(from, to, sellRequest.getSum());
//        if (apiResponse.getSuccess()) return ResponseEntity.ok(apiResponse);
//        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    /**
     * <b>Метод доступен только пользователям с Ролью Client</b><br>
     * POST метод покупки валюты за рубли, со списанием суммы с дефолтного рублевого счёта.
     * От пользователя приходит json запрос с указанием какую валюту и в каком объеме покупает пользователь.
     * Выполняется проверка на валидность данной валюты и счетов клиента. Если при проверке отлавливается exception, то
     * пользователю выкидывается сообщение об ошибке, в случае если все данные были корректны то выполняется метод <code>buyCurrency</code>
     *
     * @param userPrincipal авторизированный пользователь в Spring Security Context
     * @param buyRequest    JSON запрос от клиента на покупку валюты, клиент указывает
     *                      какую валюту он хочет приобрести и в каком размере
     * @return
     * @throws IOException
     */
//    @PostMapping
//    @RequestMapping("buy")
//    @PreAuthorize("hasRole('CLIENT')")
//    public ResponseEntity<ApiResponse> buyCurrency(@CurrentUser UserPrincipal userPrincipal,
//                                                   @Valid @RequestBody BuyCurrencyRequest buyRequest) {
//
//        //noinspection OptionalGetWithoutIsPresent currency and client neve be to null in this method
//        Currency currencyPay = currencyRepo.findByName(CurrencyName.RUB).get();
//        //noinspection OptionalGetWithoutIsPresent
//        Client client = clientRepo.findByUserId(userPrincipal.getId()).get();
//        Currency currencyBuy;
//        Account from;
//        Account to;
//        try {
//            currencyBuy = currencyRepo.findByName(buyRequest.getCurrencyName())
//                    .orElseThrow(() -> new VerificationNoEntityException("Currency %s not valid value.",
//                            buyRequest.getCurrencyName().toString()));
//            from = accountRepo.findActiveDefaultAccounts(client.getId(), currencyPay.getId())
//                    .orElseThrow(() -> new VerificationNoEntityException("Not found active, %s defaults account, for client %s",
//                            currencyPay.getName().toString(), client.getLastName()));
//            to = accountRepo.findActiveDefaultAccounts(client.getId(), currencyBuy.getId())
//                    .orElseThrow(() -> new VerificationNoEntityException("Not found active, %s defaults account, for client %s",
//                            currencyBuy.getName().toString(), client.getLastName()));
//        } catch (Exception e) {
//            logger.error("ERROR", e);
//            loggingService.createLog(e.toString(), Status.ERROR);
//            return new ResponseEntity<>(new ApiResponse(false, e.toString()), HttpStatus.BAD_REQUEST);
//        }
//        ApiResponse apiResponse = accountService.buyCurrency(from, to, buyRequest.getSum());
//        if (apiResponse.getSuccess()) return ResponseEntity.ok(apiResponse);
//        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
}
