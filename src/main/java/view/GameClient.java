package view;

import communication.StrategicGameClient;
import communication.commands.Login;
import communication.events.*;
import controller.Controller;
import games.reversi.controller.ReversiController;
import games.reversi.model.ReversiAI;
import games.reversi.model.ReversiModel;
import games.tictactoe.controller.TicTacToeController;
import games.tictactoe.model.TicTacToeAI;
import games.tictactoe.model.TicTacToeModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Model;

import java.util.Optional;

public class GameClient extends Application {

    public static int gameMode = Model.AI_VS_SERVER;
    public static String username = "Dylan";
    private Stage stage;
    private GridPane gridPane = new GridPane();
    private Label gameLabel = new Label();

    private SceneView loginView, gamesView, boardView;

    @Override
    public void start(Stage stage) {


        SetEvents();

        this.stage = stage;

        loginView = new LoginView(this);
        loginView.CreateScene();

        boardView = new BoardView(this);
        boardView.CreateScene();

        gamesView = new GamesView(this);
        gamesView.CreateScene();


        stage.setResizable(false);
        stage.setTitle("!!!Games!!!");
        stage.initStyle(StageStyle.DECORATED);

        stage.show();

        SwitchScene(Scenes.LOGIN);
    }

    private void StartGame(int GameToPlay, MatchStarted event) {
        if (GameToPlay == 0) {
            ((BoardView) boardView).SetUpGame("Reversi", 8, new ReversiController(new ReversiModel(8, boardView, new ReversiAI())));//, event)));
        } else if (GameToPlay == 1) {
            ((BoardView) boardView).SetUpGame("Tic Tac Toe", 3, new TicTacToeController(new TicTacToeModel(3, boardView, new TicTacToeAI())));//, event)));
        }

        SwitchScene(Scenes.GAME);
    }

    private void SetEvents() {
        StrategicGameClient.getInstance().getEventBus().addObserver(event -> {
            if (event instanceof ReceivedChallenge) {
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
            } else if (event instanceof MatchStarted) {
                MatchStarted matchStarted = (MatchStarted) event;

                if (matchStarted.getGameType().equals("Reversi")) {
                    Platform.runLater(() -> StartGame(0, matchStarted));
                } else if (matchStarted.getGameType().equals("Tic-tac-toe")) {
                    Platform.runLater(() -> StartGame(1, matchStarted));
                }
            } else if (event instanceof GameOverEvent) {
                Platform.runLater(() -> ((BoardView) boardView).GameOver((GameOverEvent) event));
            }
        });
    }

    public void SwitchScene(Scenes scene) {
        switch (scene) {
            case LOGIN:
                stage.setScene(loginView.getScene());
                break;
            case GAMES:
                stage.setScene(gamesView.getScene());
                break;
            case GAME:
                stage.setScene(boardView.getScene());
                break;
        }

    }

    public enum Scenes {
        LOGIN,
        GAMES,
        GAME,
    }
}
