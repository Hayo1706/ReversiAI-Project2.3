package view;

import controller.Controller;
import games.reversi.model.ReversiAI;
import games.tictactoe.model.TicTacToeAI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Peg;
import games.reversi.controller.ReversiController;
import games.reversi.model.ReversiModel;
import games.tictactoe.controller.TicTacToeController;
import games.tictactoe.model.TicTacToeModel;


public class BoardSetup extends Application implements View {
    private GridPane gridPane=new GridPane();
    private Label text=new Label();
    private VBox vBox=new VBox(text,gridPane);
    private Controller controller;
    private int size;
    static int whatGame;

    public void start(Stage primaryStage){
        whatGame = 1;
        //0 = Reversi
        //1 = TictacToe
        if(whatGame == 0) {
            this.size = 8;
            controller=new ReversiController(new ReversiModel(size,this,new ReversiAI()));
            primaryStage.setTitle("Reversi");
        }
        else if(whatGame == 1) {
            this.size = 3;
            controller = new TicTacToeController(new TicTacToeModel(size,this,new TicTacToeAI()));
            primaryStage.setTitle("TicTacToe");
        }

        for(int i = 0;i < size;i++) {
            for (int o = 0; o < size; o++) {
                Peg peg=controller.get_pegs()[i][o];
                peg.setOnAction(
                        actionEvent -> Platform.runLater( ()-> {
                            controller.nextTurn(peg);
                        } ));
                    gridPane.add(peg, peg.getZPosition(), peg.getXPosition());
            }
        }

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

    }
    public void setText(String s){
        this.text.setText(s);
    }
}
