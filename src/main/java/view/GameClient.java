package view;

import communication.StrategicGameClient;
import communication.events.*;
import games.reversi.controller.ReversiController;
import games.reversi.model.ReversiAI;
import games.reversi.model.ReversiModel;
import games.tictactoe.controller.TicTacToeController;
import games.tictactoe.model.TicTacToeAI;
import games.tictactoe.model.TicTacToeModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Model;

import java.util.Iterator;
import java.util.Optional;

public class GameClient extends Application {

    private Stage stage;

    private SceneView loginView, OnlineGamesView, boardView, OfflineGamesView;

    @Override
    public void start(Stage stage) {
        SetEvents();

        this.stage = stage;

        loginView = new LoginView(this);
        loginView.CreateScene();

        boardView = new BoardView(this);
        boardView.CreateScene();

        OnlineGamesView = new GamesView(this);
        OnlineGamesView.CreateScene();

        OfflineGamesView = new OfflineGamesView(this);
        OfflineGamesView.CreateScene();


        stage.setResizable(false);
        stage.setTitle("!!!GAMESONLINE!!!");
        stage.initStyle(StageStyle.DECORATED);

        stage.show();

        SwitchScene(Scenes.LOGIN);
    }

    private void StartGame(Games gameToPlay, MatchStarted event) {
        switch (gameToPlay) {
            case TICTACTOE:
                ((BoardView) boardView).SetUpGame("Tic Tac Toe", 3, new TicTacToeController(new TicTacToeModel(3, boardView, new TicTacToeAI(),event)));
                break;
            case REVERSI:
                ((BoardView) boardView).SetUpGame("Reversi", 8, new ReversiController(new ReversiModel(8, boardView, new ReversiAI(),event)));
                break;
        }

        SwitchScene(Scenes.GAME);
    }
    //local start game, no connection
    public void StartGame(Games GameToPlay){
        StartGame(GameToPlay,null);
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
                    Platform.runLater(() -> StartGame(Games.REVERSI, matchStarted));
                } else if (matchStarted.getGameType().equals("Tic-tac-toe")) {
                    Platform.runLater(() -> StartGame(Games.TICTACTOE, matchStarted));
                }
            }
        });
    }

    public void SwitchScene(Scenes scene) {
        switch (scene) {
            case LOGIN:
                stage.setScene(loginView.getScene());
                break;
            case GAMESONLINE:
                //unregister model as observer
                Iterator iterator= StrategicGameClient.getInstance().getEventBus().getObservers().iterator();
                while (iterator.hasNext()){
                    if (iterator.next() instanceof Model){
                        iterator.remove();
                    }
                }
                stage.setScene(OnlineGamesView.getScene());
                break;
            case GAMESOFFLINE:
                stage.setScene(OfflineGamesView.getScene());
                break;
            case GAME:
                stage.setScene(boardView.getScene());
                break;
        }
    }

    public enum Scenes {
        LOGIN,
        GAMESONLINE,
        GAMESOFFLINE,
        GAME,
    }

    public enum Games{
        TICTACTOE,
        REVERSI
    }

}
