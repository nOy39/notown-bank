package org.a2lpo.bank.notownbank.payload;

import com.google.common.base.Objects;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrentCurseCurrency {
    private String ID;
    private String NumCode;
    private String CharCode;
    private double Nominal;
    private String Name;
    private BigDecimal Value;
    private BigDecimal Previous;

    public CurrentCurseCurrency() {
    }

    public CurrentCurseCurrency(String numCode,
                                String charCode,
                                double nominal,
                                String name,
                                double value,
                                double previous) {
        NumCode = numCode;
        CharCode = charCode;
        Nominal = nominal;
        Name = name;
        Value = BigDecimal.valueOf(value);
        Previous = BigDecimal.valueOf(previous);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentCurseCurrency currentCurseCurrency = (CurrentCurseCurrency) o;
        return Double.compare(currentCurseCurrency.Nominal, Nominal) == 0 &&
                Objects.equal(ID, currentCurseCurrency.ID) &&
                Objects.equal(NumCode, currentCurseCurrency.NumCode) &&
                Objects.equal(CharCode, currentCurseCurrency.CharCode) &&
                Objects.equal(Name, currentCurseCurrency.Name) &&
                Objects.equal(Value, currentCurseCurrency.Value) &&
                Objects.equal(Previous, currentCurseCurrency.Previous);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID, NumCode, CharCode, Nominal, Name, Value, Previous);
    }
}
