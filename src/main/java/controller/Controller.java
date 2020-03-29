package controller;

import model.Peg;

/**
 * Created by Singh van Offeren
 */
public interface Controller {
    public boolean gameOver();

    public void disable_pegs();

    public int getBest();

    Peg[][] get_pegs();

    public void setupBoard();

    void nextTurn(Peg peg);

}
