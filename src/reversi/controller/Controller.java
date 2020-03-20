package reversi.controller;


import reversi.model.Model;
import reversi.view.Peg;

import java.util.ArrayList;

public class Controller {
    Model model;
    public Controller(Model model){
        this.model=model;
    }
    public void change_peg(Peg peg){
        model.change_peg(peg);
    }
    public Peg[][] get_pegs(){

        return model.get_pegs();
    }

}
