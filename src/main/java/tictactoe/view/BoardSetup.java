package tictactoe.view;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tictactoe.controller.Controller;
import tictactoe.model.Model;
import tictactoe.model.Peg;





public class BoardSetup extends Application {
    private GridPane gridPane=new GridPane();
    private Label text=new Label();
    private VBox vBox=new VBox(text,gridPane);
    private Controller controller;


    public void start(Stage primaryStage){


        controller=new Controller(new Model(this));



        for(int i = 0;i < 3;i++) {
            for (int o = 0; o < 3; o++) {
                Peg peg=controller.get_pegs()[i][o];



                peg.setOnAction(
                        actionEvent -> {
                            Platform.runLater( ()-> {

                                controller.nextTurn(peg);
                            } );
                        });
                    gridPane.add(peg, peg.getZPosition(), peg.getXPosition());
            }
        }

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("tictactoe/view/style/style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("TicTacToe");
        primaryStage.sizeToScene();
        primaryStage.show();

    }
    public void setText(String s){
        this.text.setText(s);
    }




}
