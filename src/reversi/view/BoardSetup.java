package reversi.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import reversi.controller.Controller;
import reversi.model.Model;


public class BoardSetup extends Application {
    private GridPane gridPane=new GridPane();
    private Controller controller;


    public void start(Stage primaryStage){


        controller=new Controller(new Model());



            for(int i = 0;i < 8;i++) {
                for (int o = 0; o < 8; o++) {
                    Peg peg=controller.get_pegs()[i][o];
                    peg.setOnAction(
                            actionEvent -> {
                                Platform.runLater( ()-> {
                                controller.change_peg(peg);
                                } );
                            }
                    );
                    gridPane.add(peg, peg.getXPosition(), peg.getZPosition());
                }
            }

        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add("reversi/view/style/style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Reversi");

        primaryStage.show();

    }




}
