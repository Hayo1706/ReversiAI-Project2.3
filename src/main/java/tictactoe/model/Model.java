package tictactoe.model;
import javafx.application.Platform;
import tictactoe.view.BoardSetup;

import java.util.Random;

public class Model
        //The tictactoe logic
{
    private TicTacToeAI AI=new TicTacToeAI();
    private BoardSetup view;
    private Random random=new Random();

    private int side=0;

    //gui board
    private Peg[][] pegs = new Peg[3][3];

    public void fill_pegs() {
        for (int i = 0; i < 3; i++) {
            for (int o = 0; o < 3; o++) {
                Peg peg = new Peg(i, o);
                peg.setMinSize(100, 100);
                pegs[i][o] = peg;


            }
        }
    }

    public void initSide(){

        if(mode==HUMAN_VS_AI){
            side=random.nextInt(2);
            player1_name="Player";
            player2_name="Computer";
            if(side==PLAYER2){
                setText(player2_name+ "'s turn!");

                int best=calculateBest();
                Platform.runLater( ()-> {
                    playMove(best);
                });


            }else{
                setText(player1_name+ "'s turn!");
            }
        }
        else if(mode==HUMAN_VS_SERVER){
            //check who begins
            //update names
            //update side
            //if side==opponent(player2): play the move on the model
        }
        else if(mode==AI_VS_SERVER){
           play_ai_vs_server();

        }
        else if(mode==HUMAN_VS_HUMAN){
            side=random.nextInt(2);
            player1_name="Player";
            player2_name="Guest";
            if(side==PLAYER1){
                setText(player1_name+ " 's turn!");
            }
            else {
                setText(player2_name+ " 's turn!");
            }
        }
        //nothing: game is idle
        else{

        }

    }
    public void play_ai_vs_server(){
        //update the names
        //check who begins and update side
        //while !gameover():
        /*
        if(side==PLAYER1)
        //calculate best move
        //play move on the server
        //play move on model
        playMove()
        else
        //receive move
        play received move on model
         */
        //if game over: update winner label


    }

    //Model
    public Model(BoardSetup view) {
        this.view=view;
        fill_pegs();
        clearBoard( );

    }

    public void switch_gamemode(int gamemode){

        mode=gamemode;
        clearBoard();
        //check if board can be enabled
        if(mode==IDLE || mode==AI_VS_SERVER){
            disable_pegs();
        }
        else {
            enable_pegs();
        }
        initSide();


    }

    public Peg[][] get_pegs() {
        return pegs;
    }



    private static final int PLAYER1        = 0;
    private static final int PLAYER2     = 1;
    public  static final int EMPTY        = 2;


    //name to be logged in with
    String player1_name="";
    String player2_name="";

    public  static final int PLAYER1_WIN    = 0;
    public  static final int DRAW         = 1;
    public  static final int UNCLEAR      = 2;
    public  static final int PLAYER2_WIN = 3;

    //state vor mode
    public  static final int IDLE  = -1;
    public  static final int HUMAN_VS_HUMAN   = 0;
    public  static final int HUMAN_VS_AI         = 1;
    public  static final int AI_VS_SERVER      = 2;
    public  static final int HUMAN_VS_SERVER         = 3;

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

        AI.pegs_to_board(pegs);
        int best=AI.chooseMove();

        return best;
    }

    public void playMove(int move){

        Peg peg=pegs[move/3 ][ move%3 ];

        if(side==PLAYER2){

                peg.setX();


        }
        else {

            peg.setO();
        }

        if (side==PLAYER1){
            this.side=PLAYER2;
            setText(player2_name+ "'s turn!");
        }
        else {this.side=PLAYER1;
            setText(player1_name+ "'s turn!");
        }
    }






    // Simple supporting routines
    public void clearBoard( )
    {
        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                place(row,col,EMPTY);
                pegs[row][col].setDisable(false);
            }
        }
    }

    private boolean pegsIsFull( )
    {

        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                if(squareIsEmpty(row,col))
                    return false;
            }
        }
        return true;
    }

    // Returns whether 'side' has won in this position
    private boolean isAWin( int side )
    {
        //sides:
        //top
        if ((side == pegs[0][0].pegState) && (side == pegs[0][1].pegState)&& (side == pegs[0][2].pegState)) {
            return true;

        }
        //bottom
        if ((side == pegs[2][0].pegState) && (side == pegs[2][1].pegState)&& (side == pegs[2][2].pegState)) {
            return true;

        }
        //left
        if ((side == pegs[0][0].pegState) && (side == pegs[1][0].pegState)&& (side == pegs[2][0].pegState)) {
            return true;

        }
        //right
        if ((side == pegs[0][2].pegState) && (side == pegs[1][2].pegState)&& (side == pegs[2][2].pegState)) {
            return true;

        }


        //middle:
        //horizontal
        if ((side == pegs[1][0].pegState) && (side == pegs[1][1].pegState)&& (side == pegs[1][2].pegState)) {
            return true;

        }
        //vertical
        if ((side == pegs[0][1].pegState) && (side == pegs[1][1].pegState)&& (side == pegs[2][1].pegState)) {
            return true;

        }


        //diagonal bottom left corner to top right
        if ((side == pegs[2][0].pegState) && (side == pegs[1][1].pegState)&& (side == pegs[0][2].pegState)) {
            return true;

        }
        //diagonal bottom right corner to top left
        if ((side == pegs[2][2].pegState) && (side == pegs[1][1].pegState)&& (side == pegs[0][0].pegState)) {

            return true;
        }

        return false;
    }

    // Play a move, possibly clearing a square
    private void place( int row, int column, int piece )
    {
        Platform.runLater(()-> {
                    pegs[row][column].pegState = piece;
                }
        );
    }

    private boolean squareIsEmpty( int row, int column )
    {
        return pegs[ row ][ column ].pegState == EMPTY;
    }

    // Compute static value of current position (win, draw, etc.)
    private int positionValue( )
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
        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                pegs[row][col].setDisable(true);
            }
        }
    }
    public void enable_pegs(){
        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                pegs[row][col].setDisable(false);
            }
        }
    }
    public void setText(String text){
        view.setText(text);
    }


    public boolean gameOver()
    {
        this.position=positionValue();
        if(position!=UNCLEAR){
            Platform.runLater(()-> {
                if (position == DRAW) {

                    setText(" It's a draw, " + winner() + " wins!");
                } else {
                    setText(" Match over, " + winner() + " wins!");
                }
            } );
        }
        return this.position!=UNCLEAR;
    }

    public String winner()
    {
        if      (this.position==PLAYER1_WIN) return player1_name;
        else if (this.position==PLAYER2_WIN   ) return player2_name;
        else                                  return "nobody";
    }





}











