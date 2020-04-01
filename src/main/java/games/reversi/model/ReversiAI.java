package games.reversi.model;

import model.Peg;

import java.util.Random;

public class ReversiAI implements ai.AI {
    ReversiModel model;
    Random random;

//    public ReversiAI(ReversiModel model){
//        this.model = model;
//    }

    public int chooseMove() {
       return random.nextInt(model.getValidMoves().size());
    }


    public void pegs_to_board(Peg[][] pegs) {

    }
}
