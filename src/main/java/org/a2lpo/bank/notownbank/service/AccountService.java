package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.accounts.BankAccount;
import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.model.message.logging.Status;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.payload.CurrentCurseCurrency;
import org.a2lpo.bank.notownbank.repos.AccountRepo;
import org.a2lpo.bank.notownbank.repos.BankAccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;

/**
 * Сервис работы со счетами
 */
@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${app.currency.sell.commission}")   //коммиссия банка за работы с валютными операциями.
    private Double commission;
    private final AccountRepo accountRepo;
    private final HistoryService historyService;
    private final CurrencyService currencyService;
    private final BankAccountRepo bankAccountRepo;
    private final LoggingService loggingService;

    public AccountService(AccountRepo accountRepo,
                          HistoryService historyService,
                          CurrencyService currencyService,
                          BankAccountRepo bankAccountRepo,
                          LoggingService loggingService) {
        this.accountRepo = accountRepo;
        this.historyService = historyService;
        this.currencyService = currencyService;
        this.bankAccountRepo = bankAccountRepo;
        this.loggingService = loggingService;
    }

    /**
     * <b>Метод осуществления перевода.</b><br>
     * Метод выполняет перевод со счета from на счёт to суммы в размере sum.
     * предварительно проверяет совпадение типов валют отправителя и получателя
     * и проверяет наличие суммы перевода на балансе отправителя.
     * если проверка прошла успешно то у отправителя с баланса снимается сумма перевода и
     * зачисляется получателю, после делается запись в историю переводов вызывая метод <code>saveCurrentTransfer</code>
     * Результат операции сохраняется в историю переводов которая доступна пользователю, а также делается сохранение логов
     * доступных администратору
     *
     * @param from счёт отправителя
     * @param to   счёт получателя
     * @param sum  сумма перевода
     * @return возвращает ApiResponse с состоянием status = true | false в зависимости от успеха выполненной операции,
     * а также сообщением о деталях операции.
     */
    public ApiResponse makeTransfer(PersonalAccount from,
                                    PersonalAccount to,
                                    BigDecimal sum) {
        MathContext mc = new MathContext(2);
        //проверка верификации счетов.
        boolean verifyPayment = to.getCurrency() == from.getCurrency() &&
                from.getSum().doubleValue() >= sum.doubleValue();
        if (verifyPayment) {
            try {
                from.setSum(from.getSum().subtract(sum, mc));
                to.setSum(to.getSum().add(sum, mc));
                accountRepo.save(from);
                accountRepo.save(to);
                historyService.saveCurrentTransfer(from, to, sum, BigDecimal.valueOf(0.00));
                loggingService.createLog(String.format("The transfer from: %s to %s was successfully.",
                        from.toString(),
                        to.toString()),
                        Status.INFO);
            } catch (Exception e) {
                loggingService.createLog(e.toString(), Status.ERROR);
                return new ApiResponse(false, String.format("INTERNAL SERVER ERROR. %s", e));

            }
            return new ApiResponse(true,
                    String.format("The transfer of %s %s to the client of the bank from the " +
                                    "%s account to the %s account in the amount of %s was completed successfully.",
                            to.getClient().getLastName(),
                            to.getClient().getFirstName(),
                            from.getUniqCheckId(),
                            to.getUniqCheckId(),
                            sum));
        } else {
            loggingService.createLog(String
                    .format("Data verification error when making a money transfer." +
                                    " Account transfer from: %s. Account transfer to: %s",
                            from.toString(),
                            to.toString()), Status.WARNING);
            return new ApiResponse(false, "It is not possible to make a transfer. " +
                    "Perhaps the transfer amount exceeds your account balance, " +
                    "or the beneficiary’s account has a different currency type.");
        }
    }

    /**
     * <b>Метод валютных операций ПОКУПКА/ПРОДАЖА/ОБМЕН</b><br>
     * Методом <code>currencyService</code> получаем список валют. При получении валют
     * может вылететь IOException перехватываем его, в этом случае, пишем об ошибке в лог файлб в таблицу логов
     * и выходим из метода с apiResponse содержащего информацию об ошибке
     * Методом <code>currencyService</code> получаем из списка объект котировки валюты
     * <code>CurrentCurseCurrency</code> которой расплачиваемся/продаем <code>currencySold</code>.
     * Делаем то-же самое для получения котировки валюты которую получаем/покупаем <code>currencyBuy</code>.
     * Далее находим номер банковского счёта на который будет зачисляться коммиссия. Коммиссия закидывается на счёт того-же
     * валютного типа, что и счёт <code>PersonalAccount to</code>
     * Далее делается расчёт по формуле <b>sumMultiple = sumToSold*(currencySold/currencyBuy)</b>
     * Общая сумма = Сумма продажи * (Курс Продаваемой валюты к ЦБРФ / Курс покупаемой валюты к ЦБРФ)
     * Далее выщитывается коммиссия по формуле <b>sumMiltiple - sumMultiple * commission</b>
     * <b>Cумма к зачислению = Общая сумма - Общая сумма * коммиссию</b>
     * Далее проверяем наличие суммы продажи на счету, и если такая сумма имеется то вычетаем со счета <b>from</b>
     * сумму <b>sumToSold</b>, добавляем к к счёту <b>to</b> сумму <b>sumToAccount</b>
     * и зачисляем на счёт банка коммиссию.
     *
     * @param from счёт с которого списываются денежные средства
     * @param to счёт на который зачисляются конвертериванные средства
     * @param sumToSold сумма списания
     * @return возвращает ApiResponse с состоянием status = true | false в зависимости от успеха выполненной операции,
     * а также сообщением о деталях операции.
     */
    public ApiResponse currencyOperation(PersonalAccount from,
                                         PersonalAccount to,
                                         BigDecimal sumToSold) {
        List<CurrentCurseCurrency> currencyList = null;
        try {
            currencyList = currencyService.getCurrentCurrency();
        } catch (IOException e) {
            loggingService.createLog(e.toString(), Status.ERROR);
            return new ApiResponse(false,
                    String.format("Get currency curse not possible. INTERNAL SERVER ERROR. %s", e));
        }
        CurrentCurseCurrency currencySold = currencyService.getCurse(
                currencyList, from.getCurrency().getName()
        );
        CurrentCurseCurrency currencyBuy = currencyService.getCurse(
                currencyList, to.getCurrency().getName()
        );
        Optional<BankAccount> optionalBankAccount = bankAccountRepo // номер банковского счёта на который будет зачисляться комиссия.
                .findCurrencyBankAcount(to.getCurrency().getId());
        //расчёт основной суммы по курсу
        MathContext mc = new MathContext(6);
        BigDecimal sumMultiplyCurse = sumToSold
                .multiply(
                        currencySold.getValue()
                                .divide(
                                        currencyBuy.getValue(),
                                        mc
                                )
                );
        BigDecimal bankCommission = sumMultiplyCurse.multiply(BigDecimal.valueOf(commission));
        //расчёт суммы к зачислению с учётом вычета комиссии
        BigDecimal sumToAccount = sumMultiplyCurse.subtract(bankCommission,mc);
        if (from.getSum().doubleValue() >= sumToSold.doubleValue() && optionalBankAccount.isPresent()) {

            from.setSum(from.getSum().subtract(sumToSold, mc));
            to.setSum(to.getSum().add(sumToAccount));
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.setSum(bankAccount.getSum().add(bankCommission));
            try {
                accountRepo.save(from);
                accountRepo.save(to);
                bankAccountRepo.save(bankAccount);
            } catch (Exception e) {
                logger.error("ERROR", e);
                loggingService.createLog(e.toString(), Status.ERROR);
                return new ApiResponse(false,
                        String.format("INTERNAL SERVER ERROR. %s", e));
            }
            historyService.saveCurrencyOperation(from, to, sumToSold, sumToAccount, bankCommission);
            loggingService.createLog("Currency operation",Status.INFO);
            return new ApiResponse(true,
                    String.format("Currency operation successful. " +
                            "%s account has been credited for %s. " +
                            "The commission amounted to 4 percents of the amount in the amount of %s",
                    to.getUniqCheckId(),
                    sumToAccount,
                    bankCommission));
        }
        return new ApiResponse(false,
                "Internal Server Error. Make sure that the payment account has a sufficient amount.");
    }

    /**
     * Метод вычисления соотношения суммы для покупки валюты.
     * @param from счёт списания денег
     * @param to  счёт зачисления
     * @param sum сумма зачисления
     * @return вызывает метод <code>currencyOperation</code>
     */
    public ApiResponse buyCurrency(PersonalAccount from,
                                   PersonalAccount to,
                                   BigDecimal sum) {
        BigDecimal value;
        try {
            value = currencyService
                    .getCurse(currencyService.getCurrentCurrency(),
                            to.getCurrency().getName()).getValue().multiply(sum);
        } catch (IOException e) {
            logger.error("ERROR", e);
            loggingService.createLog(e.toString(), Status.ERROR);
            return new ApiResponse(false,
                    String.format("Get currency curse not possible. INTERNAL SERVER ERROR. %s", e));
        }
        return currencyOperation(from,to,value);
    }
}


