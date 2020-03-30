package games.reversi.model;

import model.Model;
import model.Peg;
import view.View;


public class ReversiModel extends Model {

    //Model
    public ReversiModel(int boardsize, View view, ai.AI AI) {
        super(boardsize, view, AI);

    }

    public void fill_pegs() {
        for (int i = 0; i < boardsize; i++) {
            for (int o = 0; o < boardsize; o++) {
                Peg peg = new ReversiPeg(i, o);
                peg.setMinSize(60, 60);
                pegs[i][o] = peg;
                //i = row
                //o=column

            }
        }
    }


    public void play_ai_vs_server() {


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

    public int calculateBest() {
        // TODO: 28/03/2020
        return 0;
    }


    // Returns whether 'side' has won in this position
    public boolean isAWin(int side) {
        // TODO: 28/03/2020
        return false;
    }


}
