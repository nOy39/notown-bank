package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.accounts.Account;
import org.a2lpo.bank.notownbank.model.message.HistoryInputPayment;
import org.a2lpo.bank.notownbank.model.message.HistoryOutputPayment;
import org.a2lpo.bank.notownbank.repos.HistoryInputRepo;
import org.a2lpo.bank.notownbank.repos.HistoryOutputRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class HistoryService {
    private final HistoryOutputRepo outputRepo;
    private final HistoryInputRepo inputRepo;

    public HistoryService(HistoryOutputRepo outputRepo,
                          HistoryInputRepo inputRepo) {
        this.outputRepo = outputRepo;
        this.inputRepo = inputRepo;
    }

    public void saveCurrentTransfer(Account from, Account to, BigDecimal sum, BigDecimal commission) {
        outputRepo.save(new HistoryOutputPayment(from, to, sum, commission));
        inputRepo.save(new HistoryInputPayment(to, from, sum));
    }
}
