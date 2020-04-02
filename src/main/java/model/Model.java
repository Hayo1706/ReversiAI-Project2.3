package model;

import ai.AI;
import communication.Observer;
import communication.StrategicGameClient;
import communication.events.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import player.ExternalPlayer;
import player.LocalPlayer;
import player.Player;
import view.GameClient;
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



    public Model(int boardsize, View view, AI AI ) {
        pegs = new Peg[boardsize][boardsize];
        this.boardsize = boardsize;
        this.view = view;
        fill_pegs();
        this.AI = AI;

    }


    @Override
    public void update(Event event) {
        if(mode==Model.HUMAN_VS_SERVER || mode==Model.AI_VS_SERVER) {

            if(event instanceof MatchStarted){
                MatchStarted matchStarted=(MatchStarted) event;
                if(matchStarted.getGameType().equals("Tic-tac-toe")) {

                    player1 = new LocalPlayer(GameClient.username);
                    player2 = new ExternalPlayer(matchStarted.getOpponent());

                    if (matchStarted.getPlayerToMove().equals(GameClient.username)) {
                        side = PLAYER1;
                        player1.setSymbol(getFirstSymbol());
                        player2.setSymbol(getSecondSymbol());
                        setText(player1.getName() + "'s turn!");


                    } else {
                        disable_pegs();
                        player1.setSymbol(getSecondSymbol());
                        player2.setSymbol(getFirstSymbol());
                        side = PLAYER2;

                        setText(player2.getName() + "'s turn!");



                    }
                }
            }
            else if(event instanceof Move){

                Move move=(Move) event;
                if(move.getPlayer().equals(player2.getName())) {
                    //
                    Platform.runLater(() -> {
                        try {
                            int opponentmove=Integer.parseInt(move.getMove());

                            if(moveOk(opponentmove)) {
                                playMove(opponentmove);
                                change_side();
                            }
                        } catch (NumberFormatException e){}

                    });

                    if(mode!=AI_VS_SERVER) {
                        enable_pegs();
                    }
                }
            }


            else if(event instanceof Win){

                Win win =(Win) event;
                if (win.getComment().equals("Player forfeited match")) {
                    Platform.runLater(()-> {
                        setText(player1.getName() + " wins! " + player2.getName() + " gave up!");
                    });
                } else if (win.getComment().equals("Client disconnected")) {
                    Platform.runLater(()-> {
                        setText(player1.getName() + " wins! " + player2.getName() + " lost connection!");
                    });
                } else  if(win.getComment().equals("Turn timelimit reached")){
                        Platform.runLater(()-> {
                            setText(player1.getName() + " wins! " + player2.getName() + " took too long!");
                        });
                } else if(win.getComment().equals("Illegal move")){
                    Platform.runLater(()-> {
                         setText(player1.getName() + " wins! " + player2.getName() + " played an illegal move!");
                    });
                } else{
                    Platform.runLater(()-> {
                        setText(player1.getName() + " wins!");
                    });
                }
                view.BackTomainMenu();
                disable_pegs();
            }
            else if(event instanceof Loss){
                Loss loss =(Loss) event;
                if (loss.getComment().equals("Turn timelimit reached")) {
                    Platform.runLater(()-> {
                    setText(player2.getName() + " wins! " + player1.getName() + " took too long!");
                    });
                } else {
                    Platform.runLater(()-> {
                    setText(player2.getName() + " wins! ");
                    });
                }

                view.BackTomainMenu();
                disable_pegs();
            }
            else if(event instanceof Draw){
                Platform.runLater(()-> {
                setText("Nobody" + " wins! It's a draw!");
                });
                view.BackTomainMenu();
                disable_pegs();
            }
            else if(event instanceof YourTurn){
                if(mode==AI_VS_SERVER){

                    Platform.runLater(() -> {
                        int best=calculateBest();
                        playMove(best);
                        change_side();
                        StrategicGameClient.getInstance().doMove(best);
                    });


                }
            }

        }

    }

    protected abstract void fill_pegs();

    //check if move ok
    public boolean moveOk(int move) {
        return (move >= 0 && move <= 8 && pegs[move / 3][move % 3].pegState == EMPTY);

    }


    public abstract void initSide();

    public void change_side(){
        if (side == PLAYER1) {
            this.side = PLAYER2;
            setText(player2.getName() + "'s turn!");

        } else {
            this.side = PLAYER1;
            setText(player1.getName() + "'s turn!");
        }

    }


    public void switch_gamemode(int gamemode) {

        mode = gamemode;
        //check if board can be enabled
        if (mode == IDLE || mode == AI_VS_SERVER) {
            disable_pegs();
            //wait for update
        }
        else if(mode==HUMAN_VS_SERVER){
            //wait for update
        } else {
            initSide();
        }




    }

    public Peg[][] get_pegs() {
        return pegs;
    }

    public boolean is_mode(int gamemode) {
        return gamemode == mode;
    }

    public abstract void playMove(int move);


    public abstract int calculateBest();

    public abstract Image getFirstSymbol();

    public abstract Image getSecondSymbol();
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
            view.setText(text);
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
    public abstract void setValidMoves();


}
