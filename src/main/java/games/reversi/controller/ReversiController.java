package games.reversi.controller;

import model.Peg;
import games.reversi.model.ReversiModel;
import games.reversi.model.ReversiPeg;
import games.reversi.view.Animation;


public class ReversiController implements controller.Controller{
    ReversiModel model;

    public ReversiController(ReversiModel model){
        this.model=model;
        startupAnimation();
    }
    public void startupAnimation(){
        Animation animation = new Animation(model.get_pegs());
        animation.start();
        setupBoard();
    }


    private void setupBoard(){
            model.get_pegs()[3][3].setTile(1);
            model.get_pegs()[4][4].setTile(1);
            model.get_pegs()[3][4].setTile(0);
            model.get_pegs()[4][3].setTile(0);

    }


    public void nextTurn(Peg peg){
        if(model.nextTurn() % 2 == 0){
            peg.setTile(0);
        }
        else {
            peg.setTile(1);
        }
    }

    public ReversiPeg[][] get_pegs(){

        return model.get_pegs();
    }

}
