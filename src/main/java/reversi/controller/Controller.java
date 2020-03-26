package reversi.controller;


import javafx.application.Platform;
import reversi.model.Model;
import reversi.model.Peg;
import reversi.view.Animation;

public class Controller {
    Model model;

    public Controller(Model model){

        this.model=model;
        startupAnimation();
    }
    public void startupAnimation(){
        Animation animation = new Animation(model.get_pegs());
        animation.start();
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
