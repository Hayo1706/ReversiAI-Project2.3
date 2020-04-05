package controller;

import model.Model;
import model.Peg;

/**
 * Created by Singh van Offeren
 */
public interface Controller {
    //see Model for description
    void disable_pegs();
    //see Model for description
    Peg[][] get_pegs();
    //Set the begin state of the board
    void setupBoard();
    //handle a move on the gui
    void nextTurn(Peg peg);
    //get the side form model
    int getSide();
}
