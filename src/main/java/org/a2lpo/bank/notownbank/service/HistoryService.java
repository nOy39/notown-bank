package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
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

    /**
     * Метод записывает в таблицу расходов и таблицу приходов информацию о переводе.
     * todo задокументировать нормально
     * @param from
     * @param to
     * @param sum
     * @param commission
     */
    public void saveCurrentTransfer(PersonalAccount from, PersonalAccount to, BigDecimal sum, BigDecimal commission) {
        outputRepo.save(new HistoryOutputPayment(from, to, sum, commission));
        inputRepo.save(new HistoryInputPayment(to, from, sum));
    }

    /**
     * Метод записи в таблицы расходов/приходов информации о валютных операциях
     * @param from
     * @param to
     * @param sumToSold
     * @param sumToAccount
     * @param bankCommission
     */
    public void saveCurrencyOperation(PersonalAccount from,
                                      PersonalAccount to,
                                      BigDecimal sumToSold,
                                      BigDecimal sumToAccount,
                                      BigDecimal bankCommission) {
        outputRepo.save(new HistoryOutputPayment(from, to, sumToSold, bankCommission));
        inputRepo.save(new HistoryInputPayment(to, from, sumToAccount));
    }
}
