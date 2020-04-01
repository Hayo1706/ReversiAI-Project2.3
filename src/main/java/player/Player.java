package player;

import javafx.scene.image.Image;

/**
 * Created by Singh van Offeren
 */
public abstract class Player {
    protected String name;
    protected Image symbol;
    public Player(String name) {
        this.name = name;
    }
    public void  setSymbol(Image symbol){
        this.symbol=symbol;
    }
    public Image getSymbol(){
        return symbol;
    }
    public String getName() {
        return name;
    }
}
