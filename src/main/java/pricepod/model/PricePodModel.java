package pricepod.model;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import model.PriceSide;
import model.Security;
import sample.SubscriptionListener;
import sample.SubscriptionManager;
import service.SecurityService;

import java.math.BigDecimal;

public class PricePodModel {

    @Getter @Setter SimpleObjectProperty<BigDecimal> buyPrice = new SimpleObjectProperty<>(BigDecimal.ZERO);
    @Getter @Setter SimpleObjectProperty<BigDecimal> sellPrice = new SimpleObjectProperty<>(BigDecimal.ZERO);
    @Getter @Setter StringProperty currencyPair = new SimpleStringProperty();
    @Getter @Setter Security security;

    private final SecurityService securityService;
    private final SubscriptionListener subscriptionListener = levelData -> {
        if (levelData.getPriceSide() == PriceSide.BUY) {
            buyPrice.setValue(levelData.getPrice());
        } else {
            sellPrice.setValue(levelData.getPrice());
        }
    };

    @Inject
    public PricePodModel(SubscriptionManager subscriptionManager, SecurityService securityService) {
        this.securityService = securityService;

        currencyPair.addListener((observable, oldValue, newValue) -> {
            subscriptionManager.unsubsribe(oldValue, subscriptionListener);
            Optional<Security> securityOptional = securityService.loadSecurity(newValue);
            if (securityOptional.isPresent()) {
                security = securityOptional.get();
                subscriptionManager.subscribe(newValue, subscriptionListener);
            }
        });
    }

}
