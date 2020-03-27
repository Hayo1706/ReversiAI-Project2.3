package games.tictactoe.controller;



import controller.Controller;
import games.tictactoe.model.TicTactToePeg;
import games.tictactoe.model.TicTacToeModel;


public class TicTacToeController implements Controller {


    TicTacToeModel model;


    public TicTacToeController(TicTacToeModel model)

    {

        this.model = model;
        //At startup no square can be filled
        //model.switch_gamemode(Model.IDLE);
        //human vs human
        //model.switch_gamemode(Model.HUMAN_VS_HUMAN);
        //human vs ai
        model.switch_gamemode(TicTacToeModel.HUMAN_VS_AI);

    }





    public void nextTurn(model.Peg peg){
        if(model.human_vs_ai()){

            model.playMove(peg.getXPosition()*3+peg.getZPosition());
            if(!gameOver()) {
                model.playMove(getBest());
            }

        }
        else if(model.human_vs_human()){
            model.playMove(peg.getXPosition()*3+peg.getZPosition());
        }
        else if(model.human_vs_server()){

            //play on the server
            //update model
            model.playMove(peg.getXPosition()*3+peg.getZPosition());
            //receive opponents result and play the same move on the model
        }
        //game is idle and cannot reach this whole method
        else{

        }


        if(gameOver()){

            disable_pegs();

        }
    }

    public TicTactToePeg[][] get_pegs(){

        return model.get_pegs();
    }
    public boolean gameOver(){
        return model.gameOver();
    }
    public void disable_pegs(){
        model.disable_pegs();
    }
    public void enable_pegs(){
        model.enable_pegs();
    }
    public void clear_board(){
        model.clearBoard();
    }
    public int getBest(){
        return model.calculateBest();
    }
}
