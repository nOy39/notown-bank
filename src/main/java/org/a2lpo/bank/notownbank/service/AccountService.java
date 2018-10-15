package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.accounts.Account;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.repos.AccountRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class AccountService {
    private final AccountRepo accountRepo;
    private final HistoryService historyService;

    public AccountService(AccountRepo accountRepo,
                          HistoryService historyService) {
        this.accountRepo = accountRepo;
        this.historyService = historyService;
    }

    /**
     * перевод денег
     *
     * @param from
     * @param to
     * @param sum
     * @return
     */
    public ApiResponse makeTransfer(Account from, Account to, BigDecimal sum) {
        MathContext mc = new MathContext(2);

        boolean verifyPayment = to.getCurrencyName() == from.getCurrencyName() &&
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
}
