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

    public TicTacToeController(Model model, MatchStarted start) {
        this.model = model;
        //At startup no square can be filled
        //model.switch_gamemode(Model.IDLE);
        //human vs human
        //model.switch_gamemode(Model.HUMAN_VS_HUMAN);
        //human vs ai
        model.switch_gamemode(GameClient.gameMode);

        // start -> determine who starts, name opponent

        StrategicGameClient.getInstance().getEventBus().addObserver(event -> {
            if(event instanceof YourTurn) {
                // event.doMove or  StrategicGameClient.getInstance().doMove(index);
            } else if(event instanceof Move) {
                // event.getPlayer and event.getMove()
            } else if(event instanceof Win) {
                // we won!
            } else if(event instanceof Loss) {
                // We lost
            } else if(event instanceof Draw) {
                // Draw!
            }
        });
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
