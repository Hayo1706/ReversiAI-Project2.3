package games.tictactoe.controller;

import communication.StrategicGameClient;
import controller.Controller;
import model.Model;


/**
 * Created by Singh van Offeren
 */
public class TicTacToeController extends Controller {



    public TicTacToeController(Model model) {
        super(model);
        model.switch_gamemode(model.getMode());
    }




    public void setupBoard() {

    }


    public void nextTurn(model.Peg peg) {



            if (model.getMode()== Model.HUMAN_VS_HUMAN) {


                model.playMove(peg.getXPosition()*3+peg.getZPosition());
                model.gameOver(false);

            } else if (model.getMode()==Model.HUMAN_VS_AI) {

                model.playMove(peg.getXPosition()*3+peg.getZPosition());
                int best = model.calculateBest();

                    if (!model.gameOver(false)) {
                        model.playMove(best);
                        model.gameOver(false);
                    }

            } else if (model.getMode()==Model.HUMAN_VS_SERVER) {
                int move=peg.getXPosition()*3+peg.getZPosition();
                model.playMove(move);
                StrategicGameClient.getInstance().doMove(move);
                disable_pegs();
            }



    }






}
