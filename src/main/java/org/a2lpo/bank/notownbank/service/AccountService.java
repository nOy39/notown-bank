package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.accounts.BankAccount;
import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.repos.AccountRepo;
import org.a2lpo.bank.notownbank.payload.CurrentCurseCurrency;
import org.a2lpo.bank.notownbank.repos.BankAccountRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Value("${app.currency.sell.commission}")
    private Double commission;

    private final AccountRepo accountRepo;
    private final HistoryService historyService;
    private final CurrencyService currencyService;
    private final BankAccountRepo bankAccountRepo;

    public AccountService(AccountRepo accountRepo,
                          HistoryService historyService,
                          CurrencyService currencyService,
                          BankAccountRepo bankAccountRepo) {
        this.accountRepo = accountRepo;
        this.historyService = historyService;
        this.currencyService = currencyService;
        this.bankAccountRepo = bankAccountRepo;
    }

    /**
     * перевод денег
     *
     * @param from
     * @param to
     * @param sum
     * @return
     */
    public ApiResponse makeTransfer(PersonalAccount from, PersonalAccount to, BigDecimal sum) {
        MathContext mc = new MathContext(2);

        boolean verifyPayment = to.getCurrency() == from.getCurrency() &&
                from.getSum().doubleValue() >= sum.doubleValue();
        if (verifyPayment) {
            from.setSum(from.getSum().subtract(sum, mc));
            to.setSum(to.getSum().add(sum, mc));
            accountRepo.save(from);
            accountRepo.save(to);
            historyService.saveCurrentTransfer(from, to, sum, BigDecimal.valueOf(0.00));
            return new ApiResponse(true, "Transfer was created to " + to.getClient().getFirstName() + " " + to.getClient().getLastName() + " account number " + to.getUniqCheckId());
        } else {
            return new ApiResponse(false, "Transfer was crushed");
        }
    }

    /**
     * Обмен валюты
     *  todo задокументировать, отрефакторить математику вынести в отдельный метод с указанием формылы в документации
     *  todo сделать правильные возвращаемые ApiResponse по ситуации
     *  todo переименовать метод под уневерсальность всем трем контроллерам
     * @param from
     * @param to
     * @param sumToSold
     * @return
     * @throws IOException //todo разобраться с пробрасываемыми исключениями
     */
    public ApiResponse changeCurrency(PersonalAccount from, PersonalAccount to, BigDecimal sumToSold) throws IOException {
        List<CurrentCurseCurrency> currencyList = currencyService.getCurrentCurrency();

        CurrentCurseCurrency currencyPay = currencyService.getCurrencyCurse(
                currencyList, from.getCurrency().getName()
        );
        CurrentCurseCurrency currencyBuy = currencyService.getCurrencyCurse(
                currencyList, to.getCurrency().getName()
        );
        Optional<BankAccount> optionalBankAccount = bankAccountRepo.findCurrencyBankAcount(to.getCurrency().getId());
        MathContext mc = new MathContext(6);
        BigDecimal curse = currencyPay.getValue().divide(currencyBuy.getValue(), mc);
        BigDecimal sumMultiplyCurse = sumToSold.multiply(curse);
        BigDecimal bankCommission = sumMultiplyCurse.multiply(BigDecimal.valueOf(commission));
        BigDecimal sumToAccount = sumMultiplyCurse.subtract(bankCommission);
        if (from.getSum().doubleValue() >= sumToSold.doubleValue() && optionalBankAccount.isPresent()) {
            from.setSum(from.getSum().subtract(sumToSold, mc));
            to.setSum(to.getSum().add(sumToAccount));
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.setSum(bankAccount.getSum().add(bankCommission));
            accountRepo.save(from);
            accountRepo.save(to);
            bankAccountRepo.save(bankAccount);
            return new ApiResponse(true, "Currency was buy.");
        }
        return new ApiResponse(false, "Not buy.");
    }
    /**
     * продажа валюты
     *  //todo удалить
     * @param from
     * @param to
     * @param sum
     * @return
     * @throws IOException
     */
    public ApiResponse sellCurrency(PersonalAccount from, PersonalAccount to, BigDecimal sum) throws IOException {
        MathContext mc = new MathContext(6);
        List<CurrentCurseCurrency> currencyList = currencyService.getCurrentCurrency();

        CurrentCurseCurrency currencySold = currencyService.getCurrencyCurse(
                currencyList, from.getCurrency().getName()
        );
        if (from.getSum().doubleValue() >= sum.doubleValue()) {
            BigDecimal finallySum = sum.multiply(currencySold.getValue())
                    .subtract(sum
                            .multiply(currencySold.getValue(), mc)
                            .multiply(BigDecimal.valueOf(commission), mc)
                    );
            to.setSum(to.getSum().add(finallySum));
            accountRepo.save(to);
            from.setSum(from.getSum().subtract(sum));
            accountRepo.save(from);
            return new ApiResponse(true, "Currency was sold.");
        }
        return new ApiResponse(false, "Not sold.");
    }

    //todo удалить
    public ApiResponse buyCarrency(PersonalAccount from, PersonalAccount to, BigDecimal sum) {
        MathContext mc = new MathContext(6);
        List<CurrentCurseCurrency> currencyList = null;
        try {
            currencyList = currencyService.getCurrentCurrency();
        } catch (IOException e) {
            return new ApiResponse(false, "Service curse currency not available.");
        }
        CurrentCurseCurrency currencySold = currencyService.getCurrencyCurse(
                currencyList, from.getCurrency().getName()
        );
        BigDecimal finallySum = currencySold.getValue().multiply(sum, mc);
        if (from.getSum().doubleValue() >= finallySum.doubleValue());
        to.setSum(finallySum);
        return new ApiResponse(false, "");
    }
}


