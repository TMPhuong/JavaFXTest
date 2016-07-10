package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main extends Application {

    public static Injector injector = Guice.createInjector(new SampleModule());

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Start application");
        Guice.createInjector(new SampleModule());
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Simple PricePod");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            log.info("Main app is closing now");
            System.exit(0);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
