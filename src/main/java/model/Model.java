package model;

import games.reversi.model.ReversiPeg;
import games.tictactoe.model.TicTactToePeg;
import javafx.application.Platform;
import view.View;

import java.util.Random;

public abstract class Model {

    public Model(int boardsize,View view){
        pegs=new Peg[boardsize][boardsize];
        this.boardsize=boardsize;
        this.view=view;
        fill_pegs();
    }


    //gui board
    protected Peg[][] pegs;
    protected int boardsize;

    protected int mode=IDLE;
    protected int side=0;
    protected Random random=new Random();
    protected View view;
    //boardstate
    protected static int PLAYER1        = 0;
    protected static int PLAYER2     = 1;
    protected static int EMPTY        = 2;

    //winstate
    protected static int PLAYER1_WIN    = 0;
    protected static int DRAW         = 1;
    protected static int UNCLEAR      = 2;
    protected static int PLAYER2_WIN = 3;
    //state vor mode
    public static int IDLE  = -1;
    public static int HUMAN_VS_HUMAN   = 0;
    public static int HUMAN_VS_AI         = 1;
    public static int AI_VS_SERVER      = 2;
    public static int HUMAN_VS_SERVER         = 3;



    protected abstract void fill_pegs();

    public abstract void initSide();
    public abstract void play_ai_vs_server();
    public abstract void switch_gamemode(int gamemode);
    public abstract Peg[][] get_pegs();
    public boolean is_mode(int gamemode){ return gamemode==mode;}

    public abstract int calculateBest();

    public abstract void playMove(int move);

    // Simple supporting routines
    public abstract void clearBoard( );

    public abstract boolean pegsIsFull( );

    // Returns whether 'side' has won in this position
    public abstract boolean isAWin( int side );

    // Play a move, possibly clearing a square
    public abstract void place( int row, int column, int piece );

    public abstract boolean squareIsEmpty( int row, int column );

    // Compute static value of current position (win, draw, etc.)
    public abstract int positionValue( );
    public abstract void setText(String text);

    public abstract void disable_pegs();
    public abstract void enable_pegs();
    public abstract boolean gameOver();
}
