package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Model;

public class OfflineGamesView extends SceneView {
    public OfflineGamesView(GameClient client) {
        super(client);
    }


    @Override
    public void CreateScene() {
        super.CreateScene();

        title.setText("What game do you want to play?");

        VBox ticTacToeBox = CreateGame("Tic Tac Toe", GameClient.Games.TICTACTOE);
        VBox reversiBox = CreateGame("reversi", GameClient.Games.REVERSI);

        Button backButton = CreateButton("Back to login screen");
        backButton.setOnMouseClicked((e) -> client.SwitchScene(GameClient.Scenes.LOGIN));

        rootVBox.getChildren().add(ticTacToeBox);
        rootVBox.getChildren().add(reversiBox);
        rootVBox.getChildren().add(backButton);
    }

    private VBox CreateGame(String gameName, GameClient.Games game) {
        Button playLocal = CreateButton("Play " + gameName + " local");
        playLocal.setOnMouseClicked((e) -> {
            Model.mode = Model.HUMAN_VS_HUMAN;
            client.StartGame(game);
        });

        Button playVsAI = CreateButton("Play " + gameName + " vs AI");
        playVsAI.setOnMouseClicked((e) -> {
            System.out.println("vs ai");
            Model.mode = Model.HUMAN_VS_AI;
            client.StartGame(game);
        });

        VBox box = new VBox(playLocal, playVsAI);
        box.setAlignment(Pos.TOP_CENTER);
        return box;
    }
}
