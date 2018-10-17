package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <b>DTO запрос на платеж со счёта fromAccountId на счёт toAccountId сумму sum</b>
 */
@Data
public class PaymentRequest {
    @NotBlank
    private String fromAccountId;
    @NotBlank
    private String toAccountId;
    @NotNull
    private BigDecimal sum;
}
