package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO Запрос на историю по счету
 */
@Data
public class HistoryRequest {
    private String uniqCheckId;
    private LocalDateTime firstDate;
    private LocalDateTime lastDate;
}
