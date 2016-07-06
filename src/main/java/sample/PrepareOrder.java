package sample;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
public class PrepareOrder {
    @Getter private String currencyPair;
    @Getter private BigDecimal quantity;
}
