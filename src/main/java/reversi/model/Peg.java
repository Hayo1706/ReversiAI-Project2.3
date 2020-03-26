package reversi.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Button;
/**
 * Created by Hayo Riem
 */
public class Peg extends Button {
    //0 == empty
    //1 == black
    //2 == white
    public int pegState;
    Image image;
    int x;
    int z;

    public Peg(int x, int z){
        this.x = x;
        this.z = z;
        pegState = 0;
    }
    private void setPegState(int pegState) { this.pegState = pegState; }
    public int getPegState() { return pegState; }
    public int getXPosition() {
        return x;
    }

    public int getZPosition() {
        return z;
    }

    public void setBlack(){
        image = new Image("black.png");
        ImageView imageView = new ImageView(image);
        imageView.smoothProperty().set(true);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        setGraphic(imageView);
        setDisable(true);
        pegState = 1;
    }


    public void setWhite(){
        image = new Image("white.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        setGraphic(imageView);
        setDisable(true);
        pegState = 2;
    }
    public void reset(){
        setGraphic(null);
        setDisable(false);
        pegState = 0;
    }
}
