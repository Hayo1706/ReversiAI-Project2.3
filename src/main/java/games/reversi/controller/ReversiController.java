package games.reversi.controller;

import model.Model;
import model.Peg;
import games.reversi.model.ReversiModel;
import games.reversi.model.ReversiPeg;
import games.reversi.view.Animation;


public class ReversiController implements controller.Controller{
    ReversiModel model;

    public ReversiController(ReversiModel model){
        this.model=model;
        startupAnimation();
        model.switch_gamemode(Model.HUMAN_VS_HUMAN);
    }
    public void startupAnimation(){
        Animation animation = new Animation((ReversiPeg[][]) model.get_pegs());
        animation.start();
        setupBoard();
    }


    public void setupBoard(){
            model.get_pegs()[3][3].setTile(0);
            model.get_pegs()[4][4].setTile(0);
            model.get_pegs()[3][4].setTile(1);
            model.get_pegs()[4][3].setTile(1);

    }


    public void nextTurn(Peg peg){

        if(model.human_vs_ai()){



        }
        else if(model.human_vs_human()){

            model.playMove(peg.getXPosition()*8+peg.getZPosition());
        }
        else if(model.human_vs_server()){


        }
        //game is idle and cannot reach this whole method
        else{

        }


        if(gameOver()){

            disable_pegs();

        }
    }

    public ReversiPeg[][] get_pegs(){

        return (ReversiPeg[][]) model.get_pegs();

    }
    public boolean gameOver(){
        return model.gameOver();
    }
    public void disable_pegs(){
        model.disable_pegs();
    }
    public int getBest(){
        return model.calculateBest();
    }
}
