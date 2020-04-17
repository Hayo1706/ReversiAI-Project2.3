package games.reversi.model;
/**
 * Created by Hayo Riem
 */
import java.util.Arrays;

public class AIMove {
    int[][] boardAfterMove;
    int pos = 0;
    public AIMove(int[][] board,int pos){
        int[][] toCount = new int[8][];

        for(int i = 0; i < 8; i++)
        {
            int[] aMatrix = board[i];
            toCount[i] = new int[8];
            System.arraycopy(aMatrix, 0, toCount[i], 0, 8);
        }

        this.boardAfterMove = toCount;
        this.pos = pos;

    }

    public AIMove(int[][] board){
        this.boardAfterMove = board;
    }

    public int getPos() {
        return pos;
    }

    public int[][] getBoard() {
        return boardAfterMove;
    }
}
