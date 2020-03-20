package reversi.controller;


import reversi.model.Model;
import reversi.model.Peg;

public class Controller {
    Model model;

    public Controller(Model model){

        this.model=model;
        setupBoard();
    }


    private void setupBoard(){
        model.get_pegs()[3][3].setBlack();
        model.get_pegs()[4][4].setBlack();
        model.get_pegs()[3][4].setWhite();
        model.get_pegs()[4][3].setWhite();
    }


    public void nextTurn(Peg peg){

        if(model.nextTurn() % 2 == 0){
            peg.setWhite();
        }
        else {
            peg.setBlack();
        }
        
    }

    public Peg[][] get_pegs(){

        return model.get_pegs();
    }

}
