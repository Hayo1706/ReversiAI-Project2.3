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

/**
 * Created by Singh van Offeren
 */
public class TicTacToeController implements Controller {


    public Model model;


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



            if (model.mode== Model.HUMAN_VS_HUMAN) {
                model.playMove(peg.getXPosition()*3+peg.getZPosition());

            } else if (model.mode==Model.HUMAN_VS_AI) {

                model.playMove(peg.getXPosition()*3+peg.getZPosition());


                int best = model.calculateBest();

                    if (!gameOver()) {
                        model.playMove(best);
                        gameOver();
                    }

            } else if (model.mode==Model.HUMAN_VS_SERVER) {
                Runnable run=()-> {
                    int move = peg.getXPosition() * 3 + peg.getZPosition();
                    Platform.runLater(()-> {
                        model.playMove(move);
                        gameOver();
                        disable_pegs();

                    });



                    StrategicGameClient.getInstance().doMove(move);

                    try {
                        StrategicGameClient.getInstance().getMoveQueue().take();
                    } catch (InterruptedException e) {
                    }


                    Move playermove = null;
                    try {
                        playermove = StrategicGameClient.getInstance().getMoveQueue().take();
                    } catch (InterruptedException e) {
                    }

                    int opponentmove = Integer.parseInt(playermove.getMove());

                    Platform.runLater(()-> {
                        model.playMove(opponentmove);
                        gameOver();
                        enable_pegs();

                    });

                };
                new Thread(run).start();

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
    public void enable_pegs() {
        model.enable_pegs();
    }
    public int getBest() {
        return model.calculateBest();
    }
}
