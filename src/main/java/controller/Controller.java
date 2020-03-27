package controller;

import model.Model;
import model.Peg;

public abstract class Controller {
    protected Model model;
    public Peg[][] get_pegs(){
        return model.get_pegs();
    }
    public Controller(Model model){
        this.model = model;
    }

    public abstract void nextTurn(Peg peg);
}
