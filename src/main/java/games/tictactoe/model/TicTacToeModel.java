package games.tictactoe.model;

import ai.AI;
import com.sun.webkit.Timer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import model.Model;
import model.Peg;
import player.LocalPlayer;
import view.GameClient;
import view.View;

import java.util.concurrent.TimeUnit;

/**
 * Created by Singh van Offeren
 */

public class TicTacToeModel extends Model
        //The games.tictactoe logic
{

    public void setValidMoves() { }

    public TicTacToeModel(int boardsize, View view, AI ai) {
        super(boardsize, view, ai);
    }

    public void initSide() {
        player1 = new LocalPlayer(Model.username);
        player2 = new LocalPlayer(is_mode(HUMAN_VS_AI) ? "Computer" : "Guest");
        side = 0;
        player1.setSymbol(new Image("black.png"));
        player2.setSymbol(new Image("white.png"));
        setText(player1.getName() + "'s turn!");
    }

    public void fill_pegs() {
        for (int i = 0; i < boardsize; i++) {
            for (int o = 0; o < boardsize; o++) {
                Peg peg = new TicTactToePeg(i, o);
                peg.setMinSize(100, 100);
                pegs[i][o] = peg;
                //i = row
                //o=column

            }
        }
    }
    public void playMove(int move) {

            TicTactToePeg peg = (TicTactToePeg) pegs[move / boardsize][move % boardsize];

            if (side == PLAYER2) {

                peg.setTile(1,player2.getSymbol());

            } else {
                peg.setTile(0,player1.getSymbol());

            }

            change_side();
    }




    public int calculateBest() {
        AI.pegs_to_board(pegs);
        return AI.chooseMove();
    }



    public boolean isAWin(int side) {
        //sides:
        //top
        if ((side == pegs[0][0].pegState) && (side == pegs[0][1].pegState) && (side == pegs[0][2].pegState)) {
            return true;

        }
        //bottom
        if ((side == pegs[2][0].pegState) && (side == pegs[2][1].pegState) && (side == pegs[2][2].pegState)) {
            return true;

        }
        //left
        if ((side == pegs[0][0].pegState) && (side == pegs[1][0].pegState) && (side == pegs[2][0].pegState)) {
            return true;

        }
        //right
        if ((side == pegs[0][2].pegState) && (side == pegs[1][2].pegState) && (side == pegs[2][2].pegState)) {
            return true;

        }


        //middle:
        //horizontal
        if ((side == pegs[1][0].pegState) && (side == pegs[1][1].pegState) && (side == pegs[1][2].pegState)) {
            return true;

        }
        //vertical
        if ((side == pegs[0][1].pegState) && (side == pegs[1][1].pegState) && (side == pegs[2][1].pegState)) {
            return true;

        }


        //diagonal bottom left corner to top right
        if ((side == pegs[2][0].pegState) && (side == pegs[1][1].pegState) && (side == pegs[0][2].pegState)) {
            return true;

        }
        //diagonal bottom right corner to top left
        if ((side == pegs[2][2].pegState) && (side == pegs[1][1].pegState) && (side == pegs[0][0].pegState)) {

            return true;
        }

        return false;
    }

    public Image getFirstSymbol() {
       return new Image("x.png");
    }


    public Image getSecondSymbol() {
        return new Image("o.png");
    }
}











