package reversi.model;

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
    }
    public void setPegState(int pegState) {
        this.pegState = pegState;
    }
    public int getXPosition() {
        return x;
    }

    public int getZPosition() {
        return z;
    }
}
