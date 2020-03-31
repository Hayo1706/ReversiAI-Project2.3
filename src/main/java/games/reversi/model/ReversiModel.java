package games.reversi.model;

import communication.StrategicGameClient;
import communication.events.MatchStarted;
import communication.events.Move;
import games.tictactoe.model.TicTactToePeg;
import javafx.application.Platform;
import model.Model;
import model.Peg;
import player.ExternalPlayer;
import player.LocalPlayer;
import view.GameClient;
import view.View;


public class ReversiModel extends Model {


    protected void initSide() {

        if (mode == HUMAN_VS_AI) {



        } else if (mode == HUMAN_VS_SERVER) {



        } else if (mode == AI_VS_SERVER) {

            play_ai_vs_server();

        } else if (mode == HUMAN_VS_HUMAN) {
            side = PLAYER2;
            player1 = new LocalPlayer(GameClient.username);
            player2 = new LocalPlayer("Guest");
            if (side == PLAYER1) {
                setText(player1.getName() + " 's turn!");
            } else {
                setText(player2.getName() + " 's turn!");
            }
        }
        //nothing: game is idle
        else {

        }

    }


    //Model
    public ReversiModel(int boardsize, View view, ai.AI AI) {
        super(boardsize, view, AI);

    }
    //Model
    public ReversiModel(int boardsize, View view, ai.AI AI,MatchStarted matchStarted) {
        super(boardsize, view, AI,matchStarted);

    }

    public void setup_board() {
        for (int i = 0; i < boardsize; i++) {
            for (int o = 0; o < boardsize; o++) {
                board[i][o]=EMPTY;
            }
        }
        board[3][3]=PLAYER1;
        board[4][4]=PLAYER1;
        board[3][4]=PLAYER2;
        board[4][3]=PLAYER2;
        Platform.runLater(()-> {
            view.UpdateGame(boardsize, view.getController());
        });
    }
    public  Peg[][] board_to_pegs(){
        Peg[][] pegs=new Peg[boardsize][boardsize];
        for(int row=0;row<boardsize;row++){
            for (int col=0;col<boardsize;col++){
                Peg peg=new ReversiPeg(row,col);
                peg.pegState=board[row][col];
                peg.setMinSize(60,60);
                peg.setOnAction(actionEvent -> {
                    playMove(peg.getXPosition()*boardsize+peg.getZPosition());
                    peg.setDisable(true);
                });
                if(peg.pegState!=EMPTY) {

                    peg.setTile(peg.pegState);
                }
                pegs[row][col]=peg;
            }
        }
        return pegs;
    }


    public void play_ai_vs_server() {


    }
    public void playMove(int move) {

        //Peg peg = pegs[move / boardsize][move % boardsize];

        Runnable run=()-> {
            board[move / boardsize][move % boardsize] = side;


            if (side == PLAYER1) {
                this.side = PLAYER2;
                setText(player2.getName() + "'s turn!");

            } else {
                this.side = PLAYER1;
                setText(player1.getName() + "'s turn!");
            }
            Platform.runLater(() -> {
                UpdateView();
            });
        };
        new Thread(run).start();
    }



    public int calculateBest() {
        // TODO: 28/03/2020
        return 0;
    }


    // Returns whether 'side' has won in this position
    public boolean isAWin(int side) {
        // TODO: 28/03/2020
        return false;
    }


}
