package games.tictactoe.model;

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
            Image image = new Image("o.png");
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
            Image image = new Image("x.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            setGraphic(imageView);
            setDisable(true);
            pegState = 1;
        }

    }
}
