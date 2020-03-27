package controller;

import model.Peg;

public interface Controller {
    Peg[][] get_pegs();

    void nextTurn(Peg peg);
}
