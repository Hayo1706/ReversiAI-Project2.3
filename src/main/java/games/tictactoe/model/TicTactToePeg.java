package games.tictactoe.model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Peg;

public class TicTactToePeg extends Peg {


    public TicTactToePeg(int x, int z) {
        super(x, z);
    }


    public void setTile(int i) {
            // set o
            if (i == 0) {
                Image image = new Image("white.png");
                ImageView imageView = new ImageView(image);
                imageView.smoothProperty().set(true);
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
                setGraphic(imageView);
                setDisable(true);
                pegState = 0;
            }
            //set x
            else {
                Image image = new Image("black.png");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);
                setGraphic(imageView);
                setDisable(true);
                pegState = 1;
            }

    }
    public void setTile(int i,Image symbol) {
        ImageView imageView = new ImageView(symbol);
        imageView.smoothProperty().set(true);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        setGraphic(imageView);
        setDisable(true);
        pegState = i;

    }
}
