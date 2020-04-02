package games.tictactoe.controller;

import communication.StrategicGameClient;
import communication.events.*;
import controller.Controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Model;
import model.Peg;
import view.GameClient;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by Singh van Offeren
 */
public class TicTacToeController implements Controller {


    public Model model;


    public TicTacToeController(Model model) {
        this.model = model;
        model.switch_gamemode(GameClient.gameMode);
    }




    public void setupBoard() {

    }


    public void nextTurn(model.Peg peg) {



            if (model.getMode()== Model.HUMAN_VS_HUMAN) {


                model.playMove(peg.getXPosition()*3+peg.getZPosition());
                gameOver();

            } else if (model.getMode()==Model.HUMAN_VS_AI) {

                model.playMove(peg.getXPosition()*3+peg.getZPosition());
                int best = model.calculateBest();

                    if (!gameOver()) {
                        model.playMove(best);
                        gameOver();
                    }

            } else if (model.getMode()==Model.HUMAN_VS_SERVER) {
                int move=peg.getXPosition()*3+peg.getZPosition();
                model.playMove(move);
                StrategicGameClient.getInstance().doMove(move);
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
