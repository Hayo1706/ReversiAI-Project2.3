package games.reversi.model;

import java.util.Arrays;

public class AIMove {
    int[][] boardAfterMove;
    int pos = 0;
    String[] stringValue = new String[65];
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

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                stringValue[row*8+col] = String.valueOf(boardAfterMove[row][col]);
            }
        }
        stringValue[64] = String.valueOf(pos);

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

    public String[] getStringValue() {
        return stringValue;
    }
}
