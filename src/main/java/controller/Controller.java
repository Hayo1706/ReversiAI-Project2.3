package controller;

import model.Model;
import model.Peg;
import player.Player;

/**
 * Created by Singh van Offeren
 */
public abstract class Controller {
    protected Model model;
    public Controller(Model model){
        this.model=model;
    }
    //see Model for description
    public void disable_pegs() {
        model.disable_pegs();
    }
    //see Model for description
    public Peg[][] get_pegs() {
        return model.get_pegs();
    }
    //Set the begin state of the board
   protected abstract void setupBoard();
    //handle a move on the gui
    public abstract void nextTurn(Peg peg);
    //get the side form model
    public int getSide() {
        return  model.getSide();
    }
    //get player 1
    public Player getPlayer1(){
        return model.getPlayer1();
    }
    //get player 2
    public Player getPlayer2(){
        return model.getPlayer2();
    }

}
