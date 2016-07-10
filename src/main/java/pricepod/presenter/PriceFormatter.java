package pricepod.presenter;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.Getter;
import model.PriceSide;
import model.Security;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PriceFormatter {
    private final PriceSide priceSide;
    private final Security security;
    private NumberFormat numberFormat;
    private final SimpleObjectProperty<BigDecimal> priceProperty;

    @Getter private final StringProperty formattedPrice = new SimpleStringProperty();
    @Getter private final StringProperty upperText = new SimpleStringProperty();
    @Getter private final StringProperty mainText = new SimpleStringProperty();
    @Getter private final StringProperty subText = new SimpleStringProperty();

    public PriceFormatter(PriceSide priceSide, Security security, SimpleObjectProperty<BigDecimal> priceProperty) {
        this.priceSide = priceSide;
        this.security = security;
        this.priceProperty = priceProperty;
        this.numberFormat = new DecimalFormat();
        if (priceSide == PriceSide.BUY)
            numberFormat.setRoundingMode(RoundingMode.CEILING);
        else
            numberFormat.setRoundingMode(RoundingMode.FLOOR);
        if (security != null) {
            numberFormat.setMinimumFractionDigits(security.getPricePrecision());
            numberFormat.setMaximumFractionDigits(security.getPricePrecision());
        }

        priceProperty.addListener(new ChangeListener<BigDecimal>() {
            @Override
            public void changed(ObservableValue<? extends BigDecimal> observable, BigDecimal oldValue, BigDecimal newValue) {
                formatPrice(newValue);
            }
        });


    }

    private void formatPrice(BigDecimal newPrice) {
        String formattedPrice = numberFormat.format(newPrice);
        int pipPrecision = security.getPipPrecision();
        int upperIndex = pipPrecision - 2;
        upperText.setValue(formattedPrice.substring(0, upperIndex));
        mainText.setValue(formattedPrice.substring(upperIndex, pipPrecision));
        subText.setValue(formattedPrice.substring(pipPrecision));
    }
}
