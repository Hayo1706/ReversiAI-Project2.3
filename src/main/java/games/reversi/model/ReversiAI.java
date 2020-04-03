package games.reversi.model;

import model.Peg;

import java.util.Random;

public class ReversiAI implements ai.AI {
    ReversiModel model;
    Random random;

    public void setModel(ReversiModel model){
        this.model = model;
    }

    public int chooseMove() {
       return 0;
    }


    public void pegs_to_board(Peg[][] pegs) {

    }
}
