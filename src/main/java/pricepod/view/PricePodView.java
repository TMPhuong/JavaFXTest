package pricepod.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PricePodView extends Pane {

    public PricePodView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pricepod.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
