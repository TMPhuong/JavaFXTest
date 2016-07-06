package sample;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import javafx.util.StringConverter;
import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {

    @FXML private ComboBox<String> currencyComboBox;
    @FXML private Label notificationText;
    @FXML private TextFlow buyPrice, sellPrice;
    @FXML private Spinner<BigDecimal> quantitySpinner;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private void initializeComboBox() {
        currencyComboBox.getItems().addAll("EUR/USD", "SGD/USD", "GBP/USD", "JPY/USD");
        currencyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Change in comboBox from " + oldValue + " to " + newValue);
        });
        currencyComboBox.getSelectionModel().selectFirst();
    }

    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBox();
        initializeSpinner();
        initializePriceLabel();
        initializeNotificationText();
    }

    private void initializeSpinner() {
        quantitySpinner.setValueFactory(new SpinnerValueFactory<BigDecimal>() {
            @Override
            public void decrement(int steps) {
                BigDecimal newValue = getValue().subtract(getStepValue(steps));

                setValue(newValue.signum() > 0 ? newValue : BigDecimal.ZERO);
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().add(getStepValue(steps)));
            }

            private BigDecimal getStepValue(int steps) {
                return BigDecimal.valueOf(100000);
            }


        });
        quantitySpinner.getValueFactory().setValue(BigDecimal.valueOf(100000));
        quantitySpinner.getValueFactory().setConverter(new StringConverter<BigDecimal>() {
            @Override
            public String toString(BigDecimal object) {
                return object.toPlainString();
            }

            @Override
            public BigDecimal fromString(String string) {
                return new BigDecimal(string);
            }
        });
    }

    private void initializeNotificationText() {
        notificationText.setVisible(false);
    }

    private void initializePriceLabel() {
        scheduledExecutorService.schedule(new NewTickRunner(buyPrice, scheduledExecutorService), 500, TimeUnit.MILLISECONDS);
        scheduledExecutorService.schedule(new NewTickRunner(sellPrice, scheduledExecutorService), 500, TimeUnit.MILLISECONDS);

        buyPrice.getChildren().addListener(new PriceChangeListener(buyPrice));
        sellPrice.getChildren().addListener(new PriceChangeListener(sellPrice));

        buyPrice.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                String currencyPair = currencyComboBox.getSelectionModel().getSelectedItem();
                BigDecimal quantity = quantitySpinner.getValue();
                PrepareOrder prepareOrder = new PrepareOrder(currencyPair, quantity);

            }
        });
    }


    private class NewTickRunner implements Runnable {
        private final TextFlow priceNode;
        private final ScheduledExecutorService scheduledExecutorService;

        public NewTickRunner(TextFlow priceNode, ScheduledExecutorService scheduledExecutorService) {
            this.priceNode = priceNode;
            this.scheduledExecutorService = scheduledExecutorService;
        }

        public void run() {
            Platform.runLater(() -> {
                priceNode.getChildren().clear();
                priceNode.getChildren().addAll(getRandomPriceText());
            });
            scheduledExecutorService.schedule(this, RandomUtils.nextInt(500, 1500), TimeUnit.MILLISECONDS);
        }

        private Node[] getRandomPriceText() {
            Text upperValue = new Text(new BigDecimal(RandomUtils.nextDouble(1.00, 1.20)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "\n");
            upperValue.setStyle("-fx-font-size: 24;");
            Text mainValue = new Text(String.valueOf(RandomUtils.nextInt(10, 99)));
            mainValue.setStyle("-fx-font-size: 40;");
            Text subscriptValue = new Text(String.valueOf(RandomUtils.nextInt(0, 9)));
            subscriptValue.setStyle("-fx-font-size: 18;");
            return new Node[] {upperValue, mainValue, subscriptValue};
        }
    }

    private class PriceChangeListener implements ListChangeListener<Node> {
        private final TextFlow priceNode;
        public PriceChangeListener(TextFlow priceNode) {
            this.priceNode = priceNode;
        }

        public void onChanged(Change<? extends Node> c) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(0), e -> priceNode.setStyle("-fx-background-color: yellow")),
                    new KeyFrame(Duration.millis(400), e -> priceNode.setStyle("-fx-background-color: transparent"))
            );
            timeline.play();
        }
    }
}
