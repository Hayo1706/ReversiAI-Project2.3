package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChooseGameView extends SceneView {
    public ChooseGameView(GameClient client) {
        super(client);
    }

    @Override
    public void CreateScene() {
        super.CreateScene();

        Button RButton = CreateButton("Play Othello (reversi)");
        RButton.setOnMouseClicked((e) -> {
//            client.StartGame(0);
        });

        Button TTTButton = CreateButton("Play Tic Tac Toe");
        TTTButton.setOnMouseClicked((e) -> {
//            client.StartGame(1);
        });

        RButton.setWrapText(true);
        RButton.setMinSize(50, 50);
        RButton.setMaxSize(200, 50);

        TTTButton.setWrapText(true);
        TTTButton.setMinSize(50, 50);
        TTTButton.setMaxSize(200, 50);


        Label title = new Label("Choose your game!");
        title.getStyleClass().add("start-game-title");
        title.setMinSize(50, 50);
        title.setMaxSize(200, 50);

        VBox alignBox = new VBox(title, TTTButton, RButton);
        alignBox.setSpacing(10);
        alignBox.setMaxWidth(200);

        rootVBox.getChildren().add(alignBox);
    }

//    private void StartGame(int GameToPlay, MatchStarted event) {
//        if (GameToPlay == 0) {
//            client.getBoardView().SetUpGame(8, new ReversiController(new ReversiModel(8, this, new ReversiAI(),event)));
//            stage.setTitle("Reversi");
//        } else if (GameToPlay == 1) {
//            SetUpGame(3, new TicTacToeController(new TicTacToeModel(3, this, new TicTacToeAI(),event)));
//            stage.setTitle("TicTacToe");
//        }
//
//        stage.setScene(gameScene);
//        stage.sizeToScene();
//    }
}
