package model;

import javafx.scene.control.Button;

public abstract class Peg extends Button {
    //2 == empty
    //0 == O
    //1 == X
    public int pegState;
    int x; //row
    int z; //column


    public Peg(int x, int z) {
        this.x = x;
        this.z = z;
        pegState = 2;
        setDisable(false);
    }
    //set the state of the peg
    public int getPegState() {
        return pegState;
    }
    //get the the row of the peg
    public int getXPosition() {
        return x;
    }
    //get the column of the peg
    public int getZPosition() {
        return z;
    }
    //reset the peg
    public void reset() {
        setGraphic(null);
        setDisable(false);
        pegState = 2;
    }
    //fill the peg with an image
    public abstract void setTile(int i);
}
