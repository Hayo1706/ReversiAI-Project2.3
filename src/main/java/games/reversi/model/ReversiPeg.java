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


    public void setTile(int i) {
        //white
        if (i == 0) {
            Image image = new Image("white.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            setGraphic(imageView);
            setDisable(true);
            pegState = 0;
        }
        //black
        else if(i == 1){
            Image image = new Image("black.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            setGraphic(imageView);
            setDisable(true);
            pegState = 1;
        }
    }
}
