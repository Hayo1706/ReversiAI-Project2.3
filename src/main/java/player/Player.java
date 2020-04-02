package player;

import javafx.scene.image.Image;

/**
 * Created by Singh van Offeren
 */
public abstract class Player {
    protected String name;
    protected Image symbol;
    //initialize a player
    public Player(String name) {
        this.name = name;
    }
    //set the symbol that this player represents on the game board
    public void  setSymbol(Image symbol){
        this.symbol=symbol;
    }
    //get the symbol
    public Image getSymbol(){
        return symbol;
    }
    //get the name
    public String getName() {
        return name;
    }
}
