package view;

import communication.StrategicGameClient;
import communication.events.MatchStarted;
import communication.events.ReceivedChallenge;
import controller.Controller;
import games.reversi.controller.ReversiController;
import games.reversi.model.ReversiAI;
import games.reversi.model.ReversiModel;
import games.tictactoe.controller.TicTacToeController;
import games.tictactoe.model.TicTacToeAI;
import games.tictactoe.model.TicTacToeModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Model;
import model.Peg;

import java.util.Optional;

public class GameClient extends Application implements View {

    
    public static int gameMode = Model.HUMAN_VS_HUMAN;

    public static String username = "Dylan";
    private Stage stage;
    private GridPane gridPane = new GridPane();
    private Label gameLabel = new Label();
    private Scene StartScene;
    private Scene GameScene;
    Button backButton;
    Model model;
    @Override
    public void start(Stage stage) {

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
            }
        });


        this.stage = stage;


        GridPane gridPane = new GridPane();

        StartScene = CreateStartScene();
        GameScene = CreateGameScene();


        stage.setResizable(false);
        stage.setTitle("!!!Games!!!");
        stage.initStyle(StageStyle.DECORATED);

        stage.show();

        LoadMainMenu();

    }

    private Scene CreateStartScene() {
        Button RButton = CreateButton("Play Othello (reversi)");
        RButton.setOnMouseClicked((e) -> {
            StartGame(0);
        });

        Button TTTButton = CreateButton("Play Tic Tac Toe");
        TTTButton.setOnMouseClicked((e) -> {
            StartGame(1);
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

        VBox vBox = new VBox(alignBox);
        vBox.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(vBox, 500, 500);
        scene.getStylesheets().add("style.css");

        return scene;
    }

    private Scene CreateGameScene() {
        backButton = CreateButton("Give up!");
        backButton.setOnMouseClicked((e) -> {
            backButton.setText("Give up!");
            LoadMainMenu();
            StrategicGameClient.getInstance().forfeit();



        });

        VBox vBox = new VBox(gameLabel, gridPane, backButton);
        vBox.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("style.css");

        return scene;
    }

    private Button CreateButton(String text) {
        var button = new Button(text);

        button.getStyleClass().clear();
        button.getStyleClass().add("Client-button");

        return button;
    }

    private void StartGame(int GameToPlay) {
        gridPane.getChildren().clear();

        if (GameToPlay == 0) {
            model=new ReversiModel(8, this, new ReversiAI());
            SetUpGame(8, new ReversiController(model));
            stage.setTitle("Reversi");
        } else if (GameToPlay == 1) {
            model=new TicTacToeModel(3, this, new TicTacToeAI());
            SetUpGame(3, new TicTacToeController(model));
            stage.setTitle("TicTacToe");
        }
        //add the model as an observer
        StrategicGameClient.getInstance().getEventBus().addObserver(model);

        stage.setScene(GameScene);
        stage.sizeToScene();
    }



    private void SetUpGame(int size, Controller controller) {
        for (int i = 0; i < size; i++) {
            for (int o = 0; o < size; o++) {
                Peg peg = controller.get_pegs()[i][o];
                peg.setOnAction(
                        actionEvent ->  {
                            controller.nextTurn(peg);
                        });
                gridPane.add(peg, peg.getZPosition(), peg.getXPosition());
            }
        }
    }

    private void LoadMainMenu() {
        stage.setScene(StartScene);
        setText("");
        //remove model as observer
        StrategicGameClient.getInstance().getEventBus().removeObserver(model);
    }

    public void setText(String s) {
        Platform.runLater(()-> {
            this.gameLabel.setText(s);
        });

    }
    public void BackTomainMenu() {
        Platform.runLater(()-> {
            backButton.setText("Go back to main menu!");
        });
    }
}
