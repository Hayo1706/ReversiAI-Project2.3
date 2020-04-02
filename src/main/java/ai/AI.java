package ai;

import model.Peg;

/**
 * Created by Singh van Offeren
 */
public interface AI {
    //pegstate
    public static final int PLAYER = 0;
    public static final int AI = 1;
    public static final int EMPTY = 2;
    //endstate
    public static final int PLAYER_WIN = 0;
    public static final int DRAW = 1;
    public static final int UNCLEAR = 2;
    public static final int AI_WIN = 3;

    //choose the best move in the current position
    public int chooseMove();
    //Method to convert the GUI pegs to a board array
    public void pegs_to_board(Peg[][] pegs);
}
