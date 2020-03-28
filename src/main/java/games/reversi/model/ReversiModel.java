package games.reversi.model;
import javafx.application.Platform;
import model.Model;
import model.Peg;
import view.View;



public class ReversiModel extends Model {

    //Model
    public ReversiModel(int boardsize,View view) {
        super(boardsize,view);

    }
    public void fill_pegs() {
        for (int i = 0; i < boardsize; i++) {
            for (int o = 0; o < boardsize; o++) {
                Peg peg = new ReversiPeg(i, o);
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



    private int position=UNCLEAR;



    public int calculateBest(){
        //to do
    return 0;
    }

    public void playMove(int move){

        Peg peg=pegs[move/8 ][ move%8 ];

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
