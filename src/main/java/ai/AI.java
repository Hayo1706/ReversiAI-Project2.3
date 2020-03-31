package ai;

import model.Peg;

/**
 * Created by Singh van Offeren
 */
public interface AI {
    public static final int PLAYER = 0;
    public static final int AI = 1;
    public static final int EMPTY = 2;

    public static final int PLAYER_WIN = 0;
    public static final int DRAW = 1;
    public static final int UNCLEAR = 2;
    public static final int AI_WIN = 3;


    public int chooseMove();

    public void load_board(int[][] board);
}
