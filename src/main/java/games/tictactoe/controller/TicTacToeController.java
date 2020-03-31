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



    }

    @Override
    public Peg[][] board_to_pegs() {
        return model.board_to_pegs();
    }

    public boolean gameOver() {
        return model.gameOver();
    }


    public int getBest() {
        return model.calculateBest();
    }
}
