package games.tictactoe.model;

import ai.AI;
import model.Model;
import model.Peg;
import view.GameClient;

/**
 * Created by Singh van Offeren
 */
public class TicTacToeAI implements AI {

    //the board
    private int[][] board = new int[3][3];


    // Constructor
    public TicTacToeAI() {


    }



    public void pegs_to_board(Peg[][] pegs) {

        clearBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                board[row][col] = pegs[row][col].pegState;
            }
        }


    }

    //return the optimal move depending on the gamemode
    public int chooseMove() {
        Best best;
        if (GameClient.gameMode== Model.AI_VS_SERVER){
            best= chooseMove(PLAYER);
        } else {
             best = chooseMove(AI);
        }
        return best.row * 3 + best.column;


    }

    // Find optimal move
    //returns:
    //012
    //345
    //678
    private Best chooseMove(int side) {
        int opp;              // The other side
        Best reply;           // Opponent's best reply
        int simpleEval;       // Result of an immediate evaluation
        int bestRow = 0;
        int bestColumn = 0;
        int value;

        if ((simpleEval = positionValue()) != UNCLEAR)
            return new Best(simpleEval);

        if (side == AI) {
            opp = PLAYER;
            value = -1000;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (squareIsEmpty(row, col)) {
                        place(row, col, side);
                        reply = chooseMove(opp);
                        place(row, col, EMPTY);
                        if (reply.val > value) {
                            value = reply.val;
                            bestRow = row;
                            bestColumn = col;
                        }
                    }
                }
            }
            return new Best(value, bestRow, bestColumn);
        } else {
            opp = AI;
            value = 1000;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (squareIsEmpty(row, col)) {
                        place(row, col, side);
                        reply = chooseMove(opp);
                        place(row, col, EMPTY);
                        if (reply.val < value) {
                            value = reply.val;
                            bestRow = row;
                            bestColumn = col;
                        }
                    }
                }
            }
            return new Best(value, bestRow, bestColumn);
        }

    }


    //check if move ok
    public boolean moveOk(int move) {
        return (move >= 0 && move <= 8 && board[move / 3][move % 3] == EMPTY);

    }
    //print the board for debugging
    public void printBoard() {
        String s = "";

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                s += board[row][col];
            }
            s += "\r\n";
        }

    }


    // Simple supporting routines
    private void clearBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                place(row, col, EMPTY);
            }
        }
    }

    //check if board is full
    private boolean boardIsFull() {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (squareIsEmpty(row, col))
                    return false;
            }
        }
        return true;
    }

    // Returns whether 'side' has won in this position
    private boolean isAWin(int side) {
        //sides:
        //top
        if ((side == board[0][0]) && (side == board[0][1]) && (side == board[0][2])) {
            return true;

        }
        //bottom
        if ((side == board[2][0]) && (side == board[2][1]) && (side == board[2][2])) {
            return true;

        }
        //left
        if ((side == board[0][0]) && (side == board[1][0]) && (side == board[2][0])) {
            return true;

        }
        //right
        if ((side == board[0][2]) && (side == board[1][2]) && (side == board[2][2])) {
            return true;

        }


        //middle:
        //horizontal
        if ((side == board[1][0]) && (side == board[1][1]) && (side == board[1][2])) {
            return true;

        }
        //vertical
        if ((side == board[0][1]) && (side == board[1][1]) && (side == board[2][1])) {
            return true;

        }


        //diagonal bottom left corner to top right
        if ((side == board[2][0]) && (side == board[1][1]) && (side == board[0][2])) {
            return true;

        }
        //diagonal bottom right corner to top left
        if ((side == board[2][2]) && (side == board[1][1]) && (side == board[0][0])) {

            return true;
        }

        return false;
    }

    // Play a move, possibly clearing a square
    private void place(int row, int column, int piece) {
        board[row][column] = piece;
    }

    private boolean squareIsEmpty(int row, int column) {
        return board[row][column] == EMPTY;
    }

    // Compute static value of current position (win, draw, etc.)
    private int positionValue() {

        boolean player_win = isAWin(PLAYER);
        boolean computer_win = isAWin(AI);
        boolean is_full = boardIsFull();
        if ((is_full && !computer_win) && (!player_win)) {
            return DRAW;
        } else if (computer_win) {
            return AI_WIN;
        } else if (player_win) {
            return PLAYER_WIN;
        } else {
            return UNCLEAR;
        }


    }
    //class to hold the best move
    private class Best {
        int row;
        int column;
        int val;

        public Best(int v) {
            this(v, 0, 0);
        }

        public Best(int v, int r, int c) {
            val = v;
            row = r;
            column = c;
        }
    }

}
