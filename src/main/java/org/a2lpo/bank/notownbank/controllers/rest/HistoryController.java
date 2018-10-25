package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.message.logging.History;
import org.a2lpo.bank.notownbank.payload.HistoryRequest;
import org.a2lpo.bank.notownbank.repos.HistoryRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class HistoryController {

    private final HistoryRepo historyRepo;

    public HistoryController(HistoryRepo historyRepo) {
        this.historyRepo = historyRepo;
    }

    /**
     * Список операций по указанному счёту
     * @param uuid уникальные счёта
     * @return возвращает список HistoryRequest
     */
    @GetMapping()
    @RequestMapping(value = "{uuid}")
    public List<?> historyTest(@PathVariable("uuid") String uuid) {
        List<History> accountHistory = historyRepo.findAccountHistory(uuid);
        List<HistoryRequest> historyRequest= new ArrayList<>();
        accountHistory.forEach(history -> {
            historyRequest.add(new HistoryRequest(history));
        });
        return historyRequest;
    }
}
