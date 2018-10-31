package org.a2lpo.bank.notownbank.payload;

import java.time.LocalDate;

/**
 * DTO json ответа при создании новой карты
 */
public class CardResponse {
    private long cardNumber;
    private LocalDate expiredDate;

}
