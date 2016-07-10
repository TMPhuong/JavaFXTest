package model;

import lombok.Data;

@Data
public class SpotSecurity implements Security {
    private final int pricePrecision;
    private final int pipPrecision;
    private final String currencyPair;

}
