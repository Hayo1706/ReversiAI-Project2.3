package model;

import games.reversi.model.ReversiPeg;
import javafx.application.Platform;

public interface Model {
    int PLAYER1        = 0;
    int PLAYER2     = 1;
    int EMPTY        = 2;
    int PLAYER1_WIN    = 0;
    int DRAW         = 1;
    int UNCLEAR      = 2;
    int PLAYER2_WIN = 3;
    //state vor mode
    int IDLE  = -1;
    int HUMAN_VS_HUMAN   = 0;
    int HUMAN_VS_AI         = 1;
    int AI_VS_SERVER      = 2;
    int HUMAN_VS_SERVER         = 3;

    public  void fill_pegs();
    public void initSide();
    public void play_ai_vs_server();
    public void switch_gamemode(int gamemode);
    public Peg[][] get_pegs();
    public boolean idle();
    //return true if human plays vs (local) ai
    public boolean human_vs_ai();
    //return true if human plays vs human
    public boolean human_vs_human();
    //return true if ai plays vs server
    public boolean ai_vs_server();
    //return true if human plays vs server
    public boolean human_vs_server();
    public int calculateBest();

    public void playMove(int move);

    // Simple supporting routines
    public void clearBoard( );

    public boolean pegsIsFull( );

    // Returns whether 'side' has won in this position
    public boolean isAWin( int side );

    // Play a move, possibly clearing a square
    public void place( int row, int column, int piece );

    public boolean squareIsEmpty( int row, int column );

    // Compute static value of current position (win, draw, etc.)
    public int positionValue( );
    public void setText(String text);

    public void disable_pegs();
    public void enable_pegs();
    public boolean gameOver();
}
