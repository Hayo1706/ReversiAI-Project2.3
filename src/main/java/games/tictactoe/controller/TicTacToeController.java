package games.tictactoe.controller;



import controller.Controller;
import games.tictactoe.model.TicTactToePeg;
import games.tictactoe.model.TicTacToeModel;
import model.Peg;


public class TicTacToeController extends Controller {


    public TicTacToeController(TicTacToeModel model)

    {

        super(model);
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
    }


    public boolean gameOver(){
        return model.gameOver();
    }
    public int getBest(){
        return model.calculateBest();
    }
}
