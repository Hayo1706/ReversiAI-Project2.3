package games.reversi.model;


import javafx.application.Platform;
import model.Model;
import model.Peg;
import view.BoardSetup;
import view.View;

import java.util.Random;

public class ReversiModel implements Model {

    private View view;
    private Random random=new Random();

    private int side=0;

    //gui board
    private ReversiPeg[][] pegs = new ReversiPeg[8][8];

    public void fill_pegs() {
        for (int i = 0; i < 8; i++) {
            for (int o = 0; o < 8; o++) {
                ReversiPeg peg = new ReversiPeg(i, o);
                peg.setMinSize(100, 100);
                pegs[i][o] = peg;

                //i = row
                //o=column
            }
        }
    }

    public void initSide(){

        if(mode==HUMAN_VS_AI){


        }
        else if(mode==HUMAN_VS_SERVER){

        }
        else if(mode==AI_VS_SERVER){
            play_ai_vs_server();

        }
        else if(mode==HUMAN_VS_HUMAN){


        }
        //nothing: game is idle
        else{

        }

    }
    public void play_ai_vs_server(){



    }
    public void setText(String text){
        view.setText(text);
    }

    //Model
    public ReversiModel(BoardSetup view) {
        this.view=view;
        fill_pegs();
        clearBoard( );

    }

    public void switch_gamemode(int gamemode){
        mode=gamemode;
        initSide();
    }

    public Peg[][] get_pegs() {
        return pegs;
    }


    //name to be logged in with
    String player1_name="";
    String player2_name="";

    public int mode=IDLE;

    private int position=UNCLEAR;


    //return true if there will be no match
    public boolean idle(){
        return mode==IDLE;
    }
    //return true if human plays vs (local) ai
    public boolean human_vs_ai(){
        return mode==HUMAN_VS_AI;
    }
    //return true if human plays vs human
    public boolean human_vs_human(){
        return mode==HUMAN_VS_HUMAN;
    }
    //return true if ai plays vs server
    public boolean ai_vs_server(){
        return mode==AI_VS_SERVER;
    }
    //return true if human plays vs server
    public boolean human_vs_server(){
        return mode==HUMAN_VS_SERVER;
    }
    public int calculateBest(){
        //to do
    return 0;
    }

    public void playMove(int move){

        ReversiPeg peg=pegs[move/8 ][ move%8 ];

        if(side==PLAYER2){

            peg.setTile(1);

        }
        else {

            peg.setTile(0);
        }

        if (side==PLAYER1){
            this.side=PLAYER2;

        }
        else {this.side=PLAYER1;

        }
    }






    // Simple supporting routines
    public void clearBoard( )
    {
        for(int row=0;row<8;row++) {
            for(int col=0;col<8;col++) {
                place(row,col,EMPTY);
                pegs[row][col].setDisable(false);
            }
        }
    }

    public boolean pegsIsFull( )
    {

        for(int row=0;row<8;row++) {
            for(int col=0;col<8;col++) {
                if(squareIsEmpty(row,col))
                    return false;
            }
        }
        return true;
    }

    // Returns whether 'side' has won in this position
    public boolean isAWin( int side )
    {

        return false;
    }

    // Play a move, possibly clearing a square
    public void place( int row, int column, int piece )
    {
        Platform.runLater(()-> pegs[row][column].pegState = piece
        );
    }

    public boolean squareIsEmpty( int row, int column )
    {
        return pegs[ row ][ column ].pegState == EMPTY;
    }

    // Compute static value of current position (win, draw, etc.)
    public int positionValue( )
    {

        boolean player1_win=isAWin(PLAYER1);
        boolean player2_win=isAWin(PLAYER2);
        boolean is_full=pegsIsFull();
        if ((is_full && !player1_win) && (!player2_win)){
            return DRAW;
        }
        else if(player2_win){
            return PLAYER2_WIN;
        }
        else if(player1_win){
            return PLAYER1_WIN;
        } else {
            return UNCLEAR;
        }

    }



    public void disable_pegs(){
        for(int row=0;row<8;row++) {
            for(int col=0;col<8;col++) {
                pegs[row][col].setDisable(true);
            }
        }
    }
    public void enable_pegs(){
        for(int row=0;row<8;row++) {
            for(int col=0;col<8;col++) {
                pegs[row][col].setDisable(false);
            }
        }
    }


    public boolean gameOver()
    {
        this.position=positionValue();
        return this.position!=UNCLEAR;
    }

    public String winner()
    {
        if      (this.position==PLAYER1_WIN) return player1_name;
        else if (this.position==PLAYER2_WIN   ) return player2_name;
        else                                  return "nobody";
    }
}
