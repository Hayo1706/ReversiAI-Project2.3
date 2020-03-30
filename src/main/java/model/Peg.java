package model;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

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
        setDisable(true);
    }

    public int getPegState() {
        return pegState;
    }

    public int getXPosition() {
        return x;
    }

    public int getZPosition() {
        return z;
    }

    public void reset() {
        setGraphic(null);
        setDisable(true);
        pegState = 2;
    }
    public abstract void setTile(int i);
}
