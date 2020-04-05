package view;

import communication.StrategicGameClient;
import communication.events.GameOverEvent;
import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Model;
import model.Peg;

public class BoardView extends SceneView {
    private Label GameLabel = new Label("Game");
    private GridPane GridPane = new GridPane();

    private Button endGameButton;

    public BoardView(GameClient client) {
        super(client);
    }

    @Override
    public void CreateScene() {
        super.CreateScene();

//        GameLabel.setAlignment(Pos.CENTER);

//        SetEndGameButton();
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
                        actionEvent -> {
                            controller.nextTurn(peg);
                        });
                GridPane.add(peg, peg.getZPosition(), peg.getXPosition());
                GridPane.setHalignment(peg, HPos.CENTER);
            }
        }
    }

    public void forfeit() {
        StrategicGameClient.getInstance().forfeit();
        SetBackToMainMenu();
    }

    private void SetEndGameButton() {
        endGameButton.setText("forfeit");
        endGameButton.setOnMouseClicked((e) -> forfeit());
    }

    public void SetBackToMainMenu() {
        endGameButton.setText("Back te Main Menu");
        if(Model.mode==Model.HUMAN_VS_HUMAN || Model.mode==Model.HUMAN_VS_AI) {
            endGameButton.setOnMouseClicked((e) -> client.SwitchScene(GameClient.Scenes.GAMESOFFLINE));
        } else{
            endGameButton.setOnMouseClicked((e) -> client.SwitchScene(GameClient.Scenes.GAMESONLINE));
        }
    }

    @Override
    public Scene getScene() {
        SetEndGameButton();
        return super.getScene();
    }

    @Override
    public void setText(String s) {
        GameLabel.setText(s);
    }
}
