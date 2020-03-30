package games.tictactoe.model;

import ai.AI;
import communication.StrategicGameClient;
import model.Model;
import model.Peg;
import view.View;

/**
 * Created by Singh van Offeren
 */

public class TicTacToeModel extends Model
        //The games.tictactoe logic
{
    public TicTacToeModel(int boardsize, View view, AI ai) {
        super(boardsize, view, ai);
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

        Peg peg = pegs[move / boardsize][move % boardsize];

        if (side == PLAYER2) {

            peg.setTile(1);

        } else {
            peg.setTile(0);
        }

        if (side == PLAYER1) {
            this.side = PLAYER2;
            setText(player2.getName() + "'s turn!");

        } else {
            this.side = PLAYER1;
            setText(player1.getName() + "'s turn!");
        }
    }

    public void play_ai_vs_server() {
        // TODO: 28/03/2020
        //update the names
        //check who begins and update side
        //while !gameover():
        /*
        if(side==PLAYER1)
        //calculate best move
        //play move on the server
        //play move on model
        playMove()
        else
        //receive move
        play received move on model
         */
        //if game over: update winner label

    }


    public int calculateBest() {

        AI.pegs_to_board(pegs);
        int best = AI.chooseMove();

        return best;
    }


    // Returns whether 'side' has won in this position
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


}











