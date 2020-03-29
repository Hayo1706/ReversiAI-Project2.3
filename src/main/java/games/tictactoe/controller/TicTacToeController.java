package games.tictactoe.controller;

import controller.Controller;
import model.Model;
import model.Peg;
import view.GameClient;

/**
 * Created by Singh van Offeren
 */
public class TicTacToeController implements Controller {


    Model model;


    public TicTacToeController(Model model) {

        this.model = model;
        //At startup no square can be filled
        //model.switch_gamemode(Model.IDLE);
        //human vs human
        //model.switch_gamemode(Model.HUMAN_VS_HUMAN);
        //human vs ai
        model.switch_gamemode(GameClient.gameMode);

    }

    public void setupBoard() {

    }


    public void nextTurn(model.Peg peg) {
        if (model.is_mode(Model.HUMAN_VS_AI)) {

            model.playMove(peg.getXPosition() * 3 + peg.getZPosition());
            if (!gameOver()) {
                model.playMove(getBest());
            }

        } else if (model.is_mode(Model.HUMAN_VS_HUMAN)) {
            model.playMove(peg.getXPosition() * 3 + peg.getZPosition());
        } else if (model.is_mode(Model.HUMAN_VS_SERVER)) {

            //play on the server
            //update model
            model.playMove(peg.getXPosition() * 3 + peg.getZPosition());
            //receive opponents result and play the same move on the model
        }
        //game is idle and cannot reach this whole method
        else {

        }


        if (gameOver()) {

            disable_pegs();

        }
    }

    public Peg[][] get_pegs() {

        return model.get_pegs();
    }

    public boolean gameOver() {
        return model.gameOver();
    }

    public void disable_pegs() {
        model.disable_pegs();
    }

    public int getBest() {
        return model.calculateBest();
    }
}
