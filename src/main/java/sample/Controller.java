package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class Controller implements Initializable {

    @FXML private Label notificationText;

    public void initialize(URL location, ResourceBundle resources) {
        initializeSpinner();
        initializeNotificationText();
    }

    private void initializeSpinner() {
//        quantitySpinner.setValueFactory(new SpinnerValueFactory<BigDecimal>() {
//            @Override
//            public void decrement(int steps) {
//                BigDecimal newValue = getValue().subtract(getStepValue(steps));
//
//                setValue(newValue.signum() > 0 ? newValue : BigDecimal.ZERO);
//            }
//
//            @Override
//            public void increment(int steps) {
//                setValue(getValue().add(getStepValue(steps)));
//            }
//
//            private BigDecimal getStepValue(int steps) {
//                return BigDecimal.valueOf(100000);
//            }
//
//
//        });
//        quantitySpinner.getValueFactory().setValue(BigDecimal.valueOf(100000));
//        quantitySpinner.getValueFactory().setConverter(new StringConverter<BigDecimal>() {
//            @Override
//            public String toString(BigDecimal object) {
//                return object.toPlainString();
//            }
//
//            @Override
//            public BigDecimal fromString(String string) {
//                return new BigDecimal(string);
//            }
//        });
    }

    private void initializeNotificationText() {
        notificationText.setVisible(false);
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
