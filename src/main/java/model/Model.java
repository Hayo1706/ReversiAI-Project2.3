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
    protected static int EMPTY = 2;
    protected static int PLAYER1_WIN = 0;
    protected static int DRAW = 1;
    protected static int UNCLEAR = 2;
    //timelimit for server
    public static int TIMELIMIT=30;

    //winstate
    protected static int PLAYER2_WIN = 3;
    //gui board
    protected Peg[][] pegs;
    protected int boardsize;
    protected int mode = 0;
    protected int side = 0;
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
        pegs = new Peg[boardsize][boardsize];
        this.boardsize = boardsize;
        this.view = view;
        fill_pegs();
        this.AI = AI;

    }
    public Model(int boardsize, View view, AI AI, MatchStarted matchStarted) {
        pegs = new Peg[boardsize][boardsize];
        this.boardsize = boardsize;
        this.view = view;
        fill_pegs();
        this.AI = AI;
        this.matchStarted=matchStarted;
    }


    protected abstract void fill_pegs();

    public abstract void initSide();

    public abstract void play_ai_vs_server();

    public void switch_gamemode(int gamemode) {

        mode = gamemode;
        //check if board can be enabled
        if (mode == IDLE || mode == AI_VS_SERVER) {
            disable_pegs();
        }

        initSide();


    }

    public Peg[][] get_pegs() {
        return pegs;
    }

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
        Platform.runLater(() -> pegs[row][column].pegState = piece
        );
    }

    public boolean squareIsEmpty(int row, int column) {
        return pegs[row][column].pegState == EMPTY;
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

    public void setText(String text) {
        Platform.runLater(()-> {
            view.setText(text);
        });
    }

    public void disable_pegs() {
        for (int row = 0; row < boardsize; row++) {
            for (int col = 0; col < boardsize; col++) {
                pegs[row][col].setDisable(true);
                pegs[row][col].setStyle("-fx-background-color: #3c8047;");
            }
        }
    }

    public void enable_pegs() {
        for (int row = 0; row < boardsize; row++) {
            for (int col = 0; col < boardsize; col++) {
                pegs[row][col].setDisable(false);
            }
        }
    }

    public boolean gameOver() {
        this.position = positionValue();
        if (position != UNCLEAR) {
            view.BackTomainMenu();
            Platform.runLater(() -> {
                disable_pegs();
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

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public int getMode() {
        return mode;
    }
    public Player getPlayer1(){
        return player1;
    }
    public Player getPlayer2(){
        return player2;
    }
    public void backToMainMenu(){
        view.BackTomainMenu();
    }


}
