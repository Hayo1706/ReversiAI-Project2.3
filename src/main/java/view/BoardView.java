package view;

import communication.StrategicGameClient;
import communication.events.Event;
import communication.events.GameOverEvent;
import controller.Controller;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Model;
import model.Peg;
import communication.events.Loss;

import java.util.Iterator;

public class BoardView extends SceneView {
    private Label GameLabel= new Label("Game");
    private GridPane GridPane = new GridPane();

    private Button endGameButton;

    public BoardView(GameClient client) {
        super(client);
    }

    @Override
    public void CreateScene() {
        super.CreateScene();

        endGameButton = CreateButton("forfeit");
        endGameButton.setOnMouseClicked((e) -> forfeit());

        rootVBox.getChildren().add(GameLabel);
        rootVBox.getChildren().add(GridPane);
        rootVBox.getChildren().add(endGameButton);
    }

    public void SetUpGame(String gameName, int size, Controller controller) {
        title.setText(gameName);

        GridPane.getChildren().clear();

        for (int i = 0; i < size; i++) {
            for (int o = 0; o < size; o++) {
                Peg peg = controller.get_pegs()[i][o];
                peg.setOnAction(
                        actionEvent ->  {
                            controller.nextTurn(peg);
                        });
                GridPane.add(peg, peg.getZPosition(), peg.getXPosition());
            }
        }
    }

    private void forfeit(){
        if(!(Model.mode == Model.HUMAN_VS_HUMAN || Model.mode==Model.HUMAN_VS_AI)) {
            //only forfeit if game not ended
            if (!endGameButton.getText().equals("Back te Main Menu")) {
                StrategicGameClient.getInstance().forfeit();
                endGameButton.setText("Back te Main Menu");
                endGameButton.setOnMouseClicked((e) ->
                        {
                            endGameButton.setText("forfeit");
                            endGameButton.setOnMouseClicked((ex) -> forfeit());
                            client.SwitchScene(GameClient.Scenes.GAMES);

                        }
                );

            } else {
                client.SwitchScene(GameClient.Scenes.GAMES);
            }
        } else {
            client.SwitchScene(GameClient.Scenes.LOGIN);
        }
    }



    @Override
    public void setText(String s) {
        GameLabel.setText(s);
    }


    public void BackTomainMenu() {
        Platform.runLater(()-> {
            endGameButton.setText("Back te Main Menu");
        });
    }
}
