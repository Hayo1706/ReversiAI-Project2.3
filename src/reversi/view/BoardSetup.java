package reversi.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import reversi.model.Peg;


public class BoardSetup extends Application {
    public void start(Stage primaryStage){
        GridPane gridPane = new GridPane();
        for(int i = 0;i < 8;i++){
            for(int o = 0;o < 8;o++) {
                Peg peg = new Peg(i,o);
                peg.setMinSize(100,100);
                Image image = new Image("reversi/view/style/black.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(90);
                imageView.setFitHeight(90);
                peg.setOnAction(actionEvent ->  {
                    peg.setGraphic(imageView);
                });
                gridPane.add(peg,i,o);
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
