package tictactoe.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Button;

public class Peg extends Button {
    //2 == empty
    //0 == O
    //1 == X
    public int pegState;
    Image image;
    int x;
    int z;

    public Peg(int x, int z){
        this.x = x;
        this.z = z;
        pegState = 2;
    }
    public void setPegState(int pegState) { this.pegState = pegState; }
    public int getPegState() { return pegState; }
    public int getXPosition() {
        return x;
    }

    public int getZPosition() {
        return z;
    }

    public void setO(){
        image = new Image("tictactoe/view/style/o.gif");
        ImageView imageView = new ImageView(image);
        imageView.smoothProperty().set(true);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        setGraphic(imageView);
        setDisable(true);
        pegState=0;

    }


    public void setX(){
        image = new Image("tictactoe/view/style/x.gif");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        setGraphic(imageView);
        setDisable(true);
        pegState=1;
    }
    public void reset(){
        setGraphic(null);
        setDisable(false);
        pegState = 2;
    }
}
