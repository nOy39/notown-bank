package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.message.logging.OperationLog;
import org.a2lpo.bank.notownbank.model.message.logging.Status;
import org.a2lpo.bank.notownbank.repos.OperationLogRepo;
import org.a2lpo.bank.notownbank.repos.StatusLogRepo;
import org.springframework.stereotype.Service;

/**
 * Сервис логирования.
 */
@Service
public class LoggingService {
    private final OperationLogRepo operationLogRepo;
    private final StatusLogRepo statusLogRepo;

    public LoggingService(OperationLogRepo operationLogRepo,
                          StatusLogRepo statusLogRepo) {
        this.operationLogRepo = operationLogRepo;
        this.statusLogRepo = statusLogRepo;
    }

    /**<b>Метод записи в таблицу логов информацию о произошедшем событии<b/><br>
     * Существуют 3 события INFO - информативное событие,
     * WARNING - событие которые заблокировали выполнение операции по соображениям бизнес-логики программы
     * ERROR - событие которые были вызваны ошибками программы, ексепшены.
     * @param message сообщение о событии
     * @param status статус события
     */
    public void createLog(String message, Status status) {
        OperationLog log = new OperationLog(message,statusLogRepo.findByStatus(status).get());
        operationLogRepo.save(log);
    }
}
