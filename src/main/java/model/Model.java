package model;

import ai.AI;
import communication.Observer;
import communication.StrategicGameClient;
import communication.events.*;
import javafx.application.Platform;
import player.Player;
import view.BoardView;
import view.View;

import java.util.Random;

/**
 * Created by Singh van Offeren
 */
public abstract class Model implements Observer<Event>{

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

    //winstate
    protected static int PLAYER2_WIN = 3;
    //gui board

    //gamemode and username of loggedin person
    public static String username = "";
    public static String username2 = "";
    public static int mode =IDLE;


    protected Peg[][] pegs;
    protected int boardsize;
    protected int side = 0;
    protected Random random = new Random();
    protected View view;
    protected AI AI;
    //current position of the game
    protected int position = UNCLEAR;
    //name to be logged in with
    protected Player player1;
    protected Player player2;
    protected MatchStarted matchStarted =null;


    public Model(int boardsize, View view, AI AI, MatchStarted matchStarted) {
        StrategicGameClient.getInstance().getEventBus().addObserver(this);
        this.matchStarted=matchStarted;
        pegs = new Peg[boardsize][boardsize];
        this.boardsize = boardsize;
        this.view = view;
        fill_pegs();
        this.AI = AI;

    }

    @Override
    //update method for server connections
    public abstract void update(Event event);

    //fill the board with it's initial pegs
    protected abstract void fill_pegs();

    //check if move ok
    public boolean moveOk(int move) {
        return (move >= 0 && move <= boardsize*boardsize-1 && pegs[move / boardsize][move % boardsize].pegState == EMPTY);

    }

    //initialize the beginning position
    public abstract void initSide();
    //change the side
    public void change_side(){
        if (side == PLAYER1) {
            this.side = PLAYER2;
            setText(player2.getName() + "'s turn!");

        } else {
            this.side = PLAYER1;
            setText(player1.getName() + "'s turn!");
        }

    }

    //set the gamemode
    public void switch_gamemode(int gamemode) {

        mode = gamemode;
        //check if board can be enabled
        if (mode == IDLE || mode == AI_VS_SERVER) {
            initSide();
            disable_pegs();
            //wait for update
        }
        else {
            initSide();
        }



    }
    //get all the pegs in the model
    public Peg[][] get_pegs() {
        return pegs;
    }

    //play a move on the board
    public abstract void playMove(int move);

    //calculate the best move in the current board position
    public abstract int calculateBest();

    public void setSide(int side) {
        this.side = side;
    }
    //check if board is full
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


    //check if a peg on the board is empty
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
    //set the text above the board
    public void setText(String text) {
        view.setText(text);
    }
    //disable all the pegs so that they are unclickable
    public void disable_pegs() {
        for (int row = 0; row < boardsize; row++) {
            for (int col = 0; col < boardsize; col++) {
                pegs[row][col].setDisable(true);
                pegs[row][col].setStyle("-fx-background-color: #3c8047;");
            }
        }
    }
    //disable all the pegs so that they are clickable
    public void enable_pegs() {
        for (int row = 0; row < boardsize; row++) {
            for (int col = 0; col < boardsize; col++) {
                pegs[row][col].setDisable(false);
            }
        }
    }

    //check if gameover, if so update the text above the board and disables it
    public abstract boolean gameOver();

    //get the winner in the endgame
    protected String winner() {
        if (this.position == PLAYER1_WIN) return player1.getName();
        else if (this.position == PLAYER2_WIN) return player2.getName();
        else return "nobody";
    }

    //get the side that must play in the current position
    public int getSide() {
        return side;
    }

    //get the gamemode
    public int getMode() {
        return mode;
    }

    public boolean is_mode(int mode){
        return this.mode==mode;
    }

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }
}
