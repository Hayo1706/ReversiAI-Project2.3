package reversi.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import reversi.controller.Controller;
import reversi.model.Model;
import reversi.model.Peg;

/**
 * Created by Hayo Riem
 */

public class BoardSetup extends Application {
    private GridPane gridPane=new GridPane();
    private Label text=new Label();
    private VBox vBox=new VBox(text,gridPane);
    private Controller controller;


    public void start(Stage primaryStage){


        controller=new Controller(new Model(this));



        for(int i = 0;i < 8;i++) {
            for (int o = 0; o < 8; o++) {
                Peg peg=controller.get_pegs()[i][o];
                peg.setOnAction(
                        actionEvent -> Platform.runLater( ()-> {
                            controller.nextTurn(peg);
                        } ));
                    gridPane.add(peg, peg.getXPosition(), peg.getZPosition());
            }
        }

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Reversi");
        primaryStage.sizeToScene();
        primaryStage.show();

    }
    public void setText(String s){
        this.text.setText(s);
    }


}


