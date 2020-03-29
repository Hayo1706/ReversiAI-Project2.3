package player;
/**
 * Created by Singh van Offeren
 */
public abstract class Player {
    protected String name;
    public Player(String name){
        this.name=name;
    }


    public String getName(){
        return name;
    }
}
