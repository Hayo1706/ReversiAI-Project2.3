package communication;

import communication.events.Event;
import communication.events.MatchStarted;
import communication.events.ReceivedChallenge;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by Dylan Hiemstra
 * <p>
 * FOR TESTING PURPOSES!
 */
public class DebugBar extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Button button1 = new Button("Challenge Piet for Reversi");

        button1.setOnAction((event) -> {
            Platform.runLater(() -> {
                StrategicGameClient.getInstance().challenge("Piet", "Reversi");
            });
        });

        Button button2 = new Button("Subscribe Reversi");

        button2.setOnAction((event) -> {
            Platform.runLater(() -> {
                StrategicGameClient.getInstance().subscribe("Reversi");
            });
        });

        stage.setOnCloseRequest(e -> {
            StrategicGameClient.getInstance().logout();
            Platform.exit();
            System.exit(0);
        });


        StrategicGameClient.getInstance().getEventBus().addObserver(event -> {
            if(event instanceof ReceivedChallenge) {
                ReceivedChallenge receivedChallenge = (ReceivedChallenge) event;
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setContentText("Accept challenge from " + receivedChallenge.getChallenger() + " to play " + receivedChallenge.getGameType());

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        receivedChallenge.acceptChallenge();
                    } else {
                        receivedChallenge.denyChallenge();
                    }
                });
            } else if(event instanceof MatchStarted) {
                MatchStarted matchStarted = (MatchStarted) event;

                if(matchStarted.getGameType().equals("Reversi")) {

                } else if(matchStarted.getGameType().equals("Tic-tac-toe")) {

                }
            }
        });

        VBox vbox = new VBox(button1, button2);

        Scene scene = new Scene(vbox, 200, 100);
        stage.setScene(scene);
        stage.show();
    }
}
