package communication;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Dylan Hiemstra
 */
public class DebugBar extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Button button1 = new Button("Challenge Piet for Reversi");

        button1.setOnAction((event) -> {
            Platform.runLater(() -> {
                StrategicGameClient.getInstance().getState().challenge("Piet", "Reversi");
            });
        });

        Button button2 = new Button("Subscribe Reversi");

        button2.setOnAction((event) -> {
            Platform.runLater(() -> {
                StrategicGameClient.getInstance().getState().subscribe("Reversi");
            });
        });


        VBox vbox = new VBox(button1, button2);

        Scene scene = new Scene(vbox, 200, 100);
        stage.setScene(scene);
        stage.show();
    }
}
