package view;

import communication.StrategicGameClient;
import communication.events.Event;
import communication.events.GameOverEvent;
import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Peg;
import communication.events.Loss;

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
        StrategicGameClient.getInstance().forfeit();
        endGameButton.setText("Back te Main Menu");
        endGameButton.setOnMouseClicked((e)->client.SwitchScene(GameClient.Scenes.GAMES));
    }

    public void GameOver(GameOverEvent e){
        setText("Game Over" + e.getPlayerOneScore() + e.getPlayerTwoScore());
    }

    @Override
    public void setText(String s) {
        GameLabel.setText(s);
    }
}
