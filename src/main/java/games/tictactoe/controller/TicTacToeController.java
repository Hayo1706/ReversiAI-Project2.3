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


        if (model.is_mode(Model.HUMAN_VS_AI)) {

            model.playMove(peg.getXPosition() * 3 + peg.getZPosition());
            if (!gameOver()) {
                model.playMove(getBest());
            }

        } else if (model.is_mode(Model.HUMAN_VS_HUMAN)) {
            model.playMove(peg.getXPosition() * 3 + peg.getZPosition());
        } else if (model.is_mode(Model.HUMAN_VS_SERVER)) {



                model.playMove(peg.getXPosition() * 3 + peg.getZPosition());
            StrategicGameClient.getInstance().doMove(peg.getXPosition()*3+peg.getZPosition());

            //wait for move

            StrategicGameClient.getInstance().getEventBus().addObserver(event -> {
                while (!(event instanceof Move)){};
                if(event instanceof Move) {
                    Move moveEvent = (Move) event;
                    model.playMove(Integer.parseInt(moveEvent.getMove()));


                    StrategicGameClient.getInstance().getEventBus().removeAllObservers();


                }
            });

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
