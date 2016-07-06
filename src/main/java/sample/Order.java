package sample;

import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.math.BigDecimal;

public class Order {
    @Getter private StringProperty orderId;
    @Getter private StringProperty currencyPair;
    @Getter private BigDecimal quantity;
    @Getter private BigDecimal price;
}
