import java.io.IOException;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;

import controller.MainController;

// create javafx main class
public class Main extends Application {
    // create javafx scene
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/view/application.css");

        primaryStage.setTitle("City Map");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // create main method
    public static void main(String[] args) {
        // launch javafx application
        launch(args);
    }
}
