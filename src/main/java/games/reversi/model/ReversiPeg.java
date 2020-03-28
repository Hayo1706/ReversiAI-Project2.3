package games.reversi.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import model.Peg;

/**
 * Created by Hayo Riem
 */
public class ReversiPeg extends Peg {


    public ReversiPeg(int x, int z) {
        super(x, z);
    }


    public void setTile(int i){
        //white
        if(i == 0) {
            Image image = new Image("white.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            setGraphic(imageView);
            setDisable(true);
            pegState = 2;
        }
        //black
        else{
            Image image = new Image("black.png");
            ImageView imageView = new ImageView(image);
            imageView.smoothProperty().set(true);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            setGraphic(imageView);
            setDisable(true);
            pegState = 1;
        }
    }
}
