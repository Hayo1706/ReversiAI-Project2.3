package games.tictactoe.model;

import ai.AI;
import communication.Observer;
import communication.StrategicGameClient;
import communication.events.*;
import javafx.application.Platform;
import model.Model;
import model.Peg;
import player.ExternalPlayer;
import player.LocalPlayer;
import view.GameClient;
import view.View;

/**
 * Created by Singh van Offeren
 */

public class TicTacToeModel extends Model
        //The games.tictactoe logic
{


    public TicTacToeModel(int boardsize, View view, AI ai) {
        super(boardsize, view, ai);
    }

    public TicTacToeModel(int boardsize, View view, ai.AI AI, MatchStarted matchStarted) {
        super(boardsize, view, AI, matchStarted);


    }


    public void setup_board() {
        for (int i = 0; i < boardsize; i++) {
            for (int o = 0; o < boardsize; o++) {

                board[i][o] = EMPTY;

            }
        }
    }

    public  Peg[][] board_to_pegs(){
        Peg[][] pegs=new Peg[boardsize][boardsize];
        for(int row=0;row<boardsize;row++){
            for (int col=0;col<boardsize;col++){
                Peg peg=new TicTactToePeg(row,col);
                peg.pegState=board[row][col];
                peg.setMinSize(100,100);
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

    protected void initSide() {

        if (mode == HUMAN_VS_AI) {

            player1 = new LocalPlayer(GameClient.username);
            player2 = new LocalPlayer("Computer");


            side = random.nextInt(2);
            if (side == PLAYER2) {
                setText(player2.getName() + "'s turn!");

                int best = calculateBest();
                board[best / boardsize][best % boardsize]=side;
                changeSide();
                UpdateView();


            } else {
                setText(player1.getName() + "'s turn!");
            }
        } else if (mode == HUMAN_VS_SERVER) {
            player1=new LocalPlayer(GameClient.username);
            player2=new ExternalPlayer(matchStarted.getOpponent());
            if(matchStarted.getPlayerToMove().equals(GameClient.username)){
                side=PLAYER1;
                setText(player1.getName() + "'s turn!");
            } else {
                side=PLAYER2;
                setText(player2.getName() + "'s turn!");


                //get opponents move
                Runnable opponent=()->{

                    try {
                        Move playermove=StrategicGameClient.getInstance().getMoveQueue().take();
                        int move=Integer.parseInt(playermove.getMove());
                        board[move / boardsize][move % boardsize]=side;


                        changeSide();
                        UpdateView();


                    } catch (InterruptedException e){};
                };
                new Thread(opponent).start();

            }

        } else if (mode == AI_VS_SERVER) {
            view.disable_pegs(boardsize);
            player1=new LocalPlayer(GameClient.username);
            player2=new ExternalPlayer(matchStarted.getOpponent());
            if(matchStarted.getPlayerToMove().equals(GameClient.username)){
                side=PLAYER1;
                setText(player1.getName() + "'s turn!");
            } else {

                side=PLAYER2;
                setText(player2.getName() + "'s turn!");


                //get opponents move
                Runnable opponent=()->{

                    try {
                        Move playermove=StrategicGameClient.getInstance().getMoveQueue().take();
                        int move=Integer.parseInt(playermove.getMove());
                        board[move / boardsize][move % boardsize]=side;


                        changeSide();
                        UpdateView();


                    } catch (InterruptedException e){};
                };
                new Thread(opponent).start();

            }
            play_ai_vs_server();

        } else if (mode == HUMAN_VS_HUMAN) {
            side = random.nextInt(2);
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


    public void playMove(int move) {
        Runnable run=()-> {
            if (mode == Model.HUMAN_VS_HUMAN) {
                board[move / boardsize][move % boardsize] = side;
                changeSide();
                gameOver();
                UpdateView();
            } else if (mode == HUMAN_VS_AI) {
                board[move / boardsize][move % boardsize] = side;
                changeSide();
                UpdateView();


                int best = calculateBest();
                if (!gameOver()) {
                    board[best / boardsize][best % boardsize] = side;
                    changeSide();
                    UpdateView();
                } else {
                    view.disable_pegs(boardsize);
                }

            } else if (mode == HUMAN_VS_SERVER) {
                board[move / boardsize][move % boardsize] = PLAYER1;


                changeSide();
                UpdateView();
                if(gameOver()){return;}

                StrategicGameClient.getInstance().doMove(move);

                try {
                    StrategicGameClient.getInstance().getMoveQueue().take();
                } catch (InterruptedException e) {
                }
                ;


                Move playermove = null;
                try {
                    playermove = StrategicGameClient.getInstance().getMoveQueue().take();
                } catch (InterruptedException e) {
                }
                ;
                int opponentmove = Integer.parseInt(playermove.getMove());
                System.out.println(opponentmove);
                board[opponentmove / boardsize][opponentmove % boardsize] = PLAYER2;

                changeSide();
                UpdateView();





            }

            if (gameOver()) {
                view.disable_pegs(boardsize);
                return;

            }
        };
        new Thread(run).start();

    }
    public void play_ai_vs_server() {
        Runnable run=()-> {
            while (true) {
                int best = calculateBest();
                board[best / boardsize][best % boardsize] = PLAYER1;


                changeSide();
                UpdateView();
                if(gameOver()){break;}

                StrategicGameClient.getInstance().doMove(best);
                if(gameOver()){break;}
                try {
                    StrategicGameClient.getInstance().getMoveQueue().take();
                } catch (InterruptedException e) {
                }



                Move playermove = null;
                try {
                    playermove = StrategicGameClient.getInstance().getMoveQueue().take();
                } catch (InterruptedException e) {
                }
                ;
                int opponentmove = Integer.parseInt(playermove.getMove());
                System.out.println(opponentmove);
                board[opponentmove / boardsize][opponentmove % boardsize] = PLAYER2;

                changeSide();
                UpdateView();

                if(gameOver()){break;}

            }
        };
        new Thread(run).start();

    }


    public int calculateBest() {


        AI.load_board(board);
        int best = AI.chooseMove();

        return best;
    }


    // Returns whether 'side' has won in this position
    public boolean isAWin(int side) {
        //sides:
        //top
        if ((side == board[0][0]) && (side == board[0][1]) && (side == board[0][2])) {
            return true;

        }
        //bottom
        if ((side == board[2][0]) && (side == board[2][1]) && (side == board[2][2])) {
            return true;

        }
        //left
        if ((side == board[0][0]) && (side == board[1][0]) && (side == board[2][0])) {
            return true;

        }
        //right
        if ((side == board[0][2]) && (side == board[1][2]) && (side == board[2][2])) {
            return true;

        }


        //middle:
        //horizontal
        if ((side == board[1][0]) && (side == board[1][1]) && (side == board[1][2])) {
            return true;

        }
        //vertical
        if ((side == board[0][1]) && (side == board[1][1]) && (side == board[2][1])) {
            return true;

        }


        //diagonal bottom left corner to top right
        if ((side == board[2][0]) && (side == board[1][1]) && (side == board[0][2])) {
            return true;

        }
        //diagonal bottom right corner to top left
        if ((side == board[2][2]) && (side == board[1][1]) && (side == board[0][0])) {

            return true;
        }

        return false;
    }




}









