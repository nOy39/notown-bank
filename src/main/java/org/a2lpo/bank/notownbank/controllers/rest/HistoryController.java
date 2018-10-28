package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.message.logging.History;
import org.a2lpo.bank.notownbank.payload.ApiResponse;
import org.a2lpo.bank.notownbank.payload.HistoryListResponse;
import org.a2lpo.bank.notownbank.payload.HistoryRequest;
import org.a2lpo.bank.notownbank.repos.HistoryRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
public class HistoryController {

    private final HistoryRepo historyRepo;

    public HistoryController(HistoryRepo historyRepo) {
        this.historyRepo = historyRepo;
    }

    /**
     * Список операций по указанному счёту
     *
     * @param uuid уникальные счёта
     * @return возвращает список HistoryListResponse
     */
    @GetMapping()
    @RequestMapping(value = "{uuid}")
    public List<?> historyTest(@PathVariable("uuid") String uuid) {
        List<History> accountHistory = historyRepo.findAccountHistory(uuid);
        return accountHistory.stream()
                .map(HistoryListResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping()
    @RequestMapping(value = "/period")
    public ResponseEntity<?> periodHistory(@CurrentUser UserPrincipal userPrincipal,
                                           @RequestBody HistoryRequest historyRequest) {

        List<History> historyList = historyRepo.findAccountHistoryByPeriod(userPrincipal.getId(),
                historyRequest.getUniqCheckId(),
                historyRequest.getFirstDate(),
                historyRequest.getLastDate());

        return historyList.isEmpty() ?
                ResponseEntity.ok(new ApiResponse(true, "History is empty...")) :
                ResponseEntity.ok(historyList.stream()
                        .map(HistoryListResponse::new)
                        .collect(Collectors.toList()));
    }

}
