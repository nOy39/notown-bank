package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.model.message.logging.History;
import org.a2lpo.bank.notownbank.repos.HistoryRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Сервис записи в БД информации о снятии и поступлении денежных средств со счетов клиентов
 */
@Service
public class HistoryService {
    private final HistoryRepo historyRepo;

    public HistoryService(HistoryRepo historyRepo) {
        this.historyRepo = historyRepo;
    }

    /**
     * Метод записывает в таблицу операций запись о списании со счета from и запись зачисления на счет to.
     * @param from счет с которого снялись денежные средства
     * @param to счет куда зачислились
     * @param outgoingSum сумма списания
     * @param incomingSum сумма зачисления
     */
    public void saveCurrentTransfer(PersonalAccount from,
                                    PersonalAccount to,
                                    BigDecimal outgoingSum,
                                    BigDecimal incomingSum) {
        outgoingOperation(from, to, outgoingSum);
        incomingOperation(from, to, incomingSum);
    }

    /**
     * todo задокументировать метод входящих операций
     * @param from
     * @param to
     * @param sum
     */
    private void incomingOperation(PersonalAccount from,
                                  PersonalAccount to,
                                  BigDecimal sum) {
        historyRepo.save(new History(to, from, BigDecimal.valueOf(0.00), sum));
    }

    /**
     * todo задокументировать метод осходящий операций
     * @param from
     * @param to
     * @param outgoingSum
     */
    private void outgoingOperation(PersonalAccount from,
                                   PersonalAccount to,
                                   BigDecimal outgoingSum) {
        historyRepo.save(new History(from, to, outgoingSum, BigDecimal.valueOf(0.00)));
    }
}
