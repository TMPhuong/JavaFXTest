package pricepod.presenter;

import com.google.inject.Inject;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.extern.slf4j.Slf4j;
import model.PriceSide;
import model.Security;
import pricepod.model.PricePodModel;
import sample.Main;
import service.SecurityService;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class PricePodPresenter implements Initializable {

    @FXML ComboBox currencyPairComboBox;
    @FXML Button sellButton;
    @FXML Button buyButton;
    @FXML Spinner quantitySpinner;
    @FXML private TextFlow buyPriceField, sellPriceField;

    private final PricePodModel model;
    private final SecurityService securityService;

    public PricePodPresenter() {
        model = Main.injector.getInstance(PricePodModel.class);
        securityService = Main.injector.getInstance(SecurityService.class);
    }

    @Inject
    public PricePodPresenter(PricePodModel model, SecurityService securityService) {
        this.model = model;
        this.securityService = securityService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCurrencyPairComboBox();
        initializePriceButton(buyPriceField, model.getBuyPrice(), model.getSecurity(), PriceSide.BUY);
        initializePriceButton(sellPriceField, model.getSellPrice(), model.getSecurity(), PriceSide.SELL);
        initializeActionButton(buyButton, PriceSide.BUY);
        initializeActionButton(sellButton, PriceSide.SELL);

    }

    private void initializeActionButton(Button actionButton, PriceSide priceSide) {
        actionButton.setOnMouseClicked(event -> {
            if (event.isPrimaryButtonDown()) {
                log.info("Click {} on {}", priceSide, model);
            }
        });
    }


    private void initializeCurrencyPairComboBox() {
        currencyPairComboBox.getItems().addAll(securityService.loadAllCurrencyPairs());
        currencyPairComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            log.debug("comboBox value change from {} to {}", oldValue, newValue);
        });
        currencyPairComboBox.getSelectionModel().selectFirst();
        currencyPairComboBox.valueProperty().bindBidirectional(model.getCurrencyPair());
    }


    private void initializePriceButton(TextFlow priceField, SimpleObjectProperty<BigDecimal> priceProperty, Security security, PriceSide priceSide) {
        Text upperText = new Text(), mainText = new Text(), subText = new Text();
        upperText.setStyle("-fx-font-size: 24;");
        mainText.setStyle("-fx-font-size: 40;");
        subText.setStyle("-fx-font-size: 18;");
        PriceFormatter priceFormatter = new PriceFormatter(priceSide, security, priceProperty);
        priceFormatter.getFormattedPrice().addListener((observable, oldValue, newValue) -> {
            upperText.setText(priceFormatter.getUpperText().getValueSafe() + "\n");
            mainText.setText(priceFormatter.getMainText().getValueSafe());
            subText.setText(priceFormatter.getSubText().getValueSafe());
        });
        priceField.getChildren().addAll(upperText, mainText, subText);
    }
}
