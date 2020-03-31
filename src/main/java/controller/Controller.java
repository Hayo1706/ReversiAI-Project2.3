package controller;

import model.Model;
import model.Peg;

/**
 * Created by Singh van Offeren
 */
public interface Controller {
    public boolean gameOver();


    public int getBest();

    public Peg[][] board_to_pegs();



}
