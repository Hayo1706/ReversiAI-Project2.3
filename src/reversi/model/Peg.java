package reversi.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.scene.control.Button;

public class Peg extends Button {
    //0 == empty
    //1 == black
    //2 == white
    public int pegState;

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
        Image black = new Image("reversi/view/style/black.png");
        ImageView imageView = new ImageView(black);
        imageView.smoothProperty().set(true);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        setGraphic(imageView);
        setDisable(true);
        pegState = 1;
    }


    public void setWhite(){
        Image white= new Image("reversi/view/style/white.png");
        ImageView imageView = new ImageView(white);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        setGraphic(imageView);
        setDisable(true);
        pegState = 2;
    }
}
