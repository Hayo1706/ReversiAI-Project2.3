package ai;

import model.Peg;

/**
 * Created by Singh van Offeren
 */
public interface AI {
    //pegstate
    int PLAYER = 0;
    int AI = 1;
    int EMPTY = 2;
    //endstate
    int PLAYER_WIN = 0;
    int DRAW = 1;
    int UNCLEAR = 2;
    int AI_WIN = 3;

    //choose the best move in the current position
    int chooseMove();
    //Method to convert the GUI pegs to a board array
    void pegs_to_board(Peg[][] pegs);
}
