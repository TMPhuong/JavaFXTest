package sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.PriceSide;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class LevelData {
    String currencyPair;
    PriceSide priceSide;
    BigDecimal price;
    BigDecimal quantity;
}
