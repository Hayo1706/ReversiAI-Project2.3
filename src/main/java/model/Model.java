package model;

import ai.AI;
import communication.StrategicGameClient;
import communication.events.*;
import javafx.application.Platform;
import player.ExternalPlayer;
import player.LocalPlayer;
import player.Player;
import view.GameClient;
import view.View;

import java.util.Random;

/**
 * Created by Singh van Offeren
 */
public abstract class Model {

    //state vor mode
    public static int IDLE = -1;
    public static int HUMAN_VS_HUMAN = 0;
    public static int HUMAN_VS_AI = 1;
    public static int AI_VS_SERVER = 2;
    public static int HUMAN_VS_SERVER = 3;
    //boardstate
    public static int PLAYER1 = 0;
    public static int PLAYER2 = 1;
    public static int EMPTY = 2;
    protected static int PLAYER1_WIN = 0;
    protected static int DRAW = 1;
    protected static int UNCLEAR = 2;

    //winstate
    protected static int PLAYER2_WIN = 3;
    //gui board

    protected int boardsize;
    //board
    protected int[][] board;

    protected int mode = IDLE;
    protected  int side = 0;
    protected Random random = new Random();
    protected View view;
    protected AI AI;
    //current position of the game
    protected int position = UNCLEAR;
    //name to be logged in with
    protected Player player1;
    protected Player player2;
    protected MatchStarted matchStarted;


    public Model(int boardsize, View view, AI AI ) {
        board=new int[boardsize][boardsize];
        this.boardsize = boardsize;
        this.view = view;
        setup_board();
        this.AI = AI;

    }
    public Model(int boardsize, View view, AI AI, MatchStarted matchStarted) {
        board=new int[boardsize][boardsize];
        this.boardsize = boardsize;
        this.view = view;
        setup_board();
        this.AI = AI;
        this.matchStarted=matchStarted;
    }


    protected abstract void setup_board();
    protected abstract void initSide();

    public void UpdateView(){
        Platform.runLater(()->{
            view.UpdateGame(boardsize, view.getController());
        });
    }
    public abstract void play_ai_vs_server();

    public void switch_gamemode(int gamemode) {

        mode = gamemode;

        initSide();


    }

    public abstract Peg[][] board_to_pegs();


    public boolean is_mode(int gamemode) {
        return gamemode == mode;
    }

    public abstract void playMove(int move);


    public abstract int calculateBest();


    protected boolean pegsIsFull() {

        for (int row = 0; row < boardsize; row++) {
            for (int col = 0; col < boardsize; col++) {
                if (squareIsEmpty(row, col))
                    return false;
            }
        }
        return true;
    }

    // Returns whether 'side' has won in this position
    public abstract boolean isAWin(int side);

    // Play a move, possibly clearing a square
    // Play a move, possibly clearing a square
    protected void place(int row, int column, int piece) {
        Platform.runLater(() -> board[row][column] = piece
        );
    }

    public boolean squareIsEmpty(int row, int column) {
        return board[row][column] == EMPTY;
    }

    // Compute static value of current position (win, draw, etc.)
    protected int positionValue() {

        boolean player1_win = isAWin(PLAYER1);
        boolean player2_win = isAWin(PLAYER2);
        boolean is_full = pegsIsFull();
        if ((is_full && !player1_win) && (!player2_win)) {
            return DRAW;
        } else if (player2_win) {
            return PLAYER2_WIN;
        } else if (player1_win) {
            return PLAYER1_WIN;
        } else {
            return UNCLEAR;
        }

    }

    protected void setText(String text) {
        view.setText(text);
    }


    public boolean gameOver() {
        this.position = positionValue();
        if (position != UNCLEAR) {
            Platform.runLater(() -> {
                if (position == DRAW) {

                    setText(" It's a draw, " + winner() + " wins!");
                } else {
                    setText(" Match over, " + winner() + " wins!");
                }
            });
        }
        return this.position != UNCLEAR;
    }

    protected String winner() {
        if (this.position == PLAYER1_WIN) return player1.getName();
        else if (this.position == PLAYER2_WIN) return player2.getName();
        else return "nobody";
    }

    public synchronized void changeSide() {
        if (side == PLAYER1) {
            this.side = PLAYER2;
            setText(player2.getName() + "'s turn!");

        } else {

            this.side = PLAYER1;
            setText(player1.getName() + "'s turn!");
        }

    }
}
