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

import java.util.concurrent.TimeUnit;

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
        super(boardsize, view, AI,matchStarted);


    }
    public void initSide() {
        enable_pegs();
        if (mode == HUMAN_VS_AI) {

                player1 = new LocalPlayer(GameClient.username);
                player2 = new LocalPlayer("Computer");


                side = random.nextInt(2);
                if (side == PLAYER2) {
                    setText(player2.getName() + "'s turn!");

                    int best = calculateBest();
                        Platform.runLater(()-> {
                            playMove(best);
                        });


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
                        disable_pegs();
                        Move playermove=StrategicGameClient.getInstance().getMoveQueue().poll(30, TimeUnit.SECONDS);
                        if(playermove==null){
                            Win win = StrategicGameClient.getInstance().getWinQueue().take();
                            if (win.getComment().equals("Player forfeited match")) {
                                setText(player1.getName() + " wins! " + player2.getName() + " gave up!");
                            } else if (win.getComment().equals("Client disconnected")) {
                                setText(player1.getName() + " wins! " + player2.getName() + " lost connection!");
                            } else {
                                setText(player1.getName() + " wins! " + player2.getName() + " took too long!");
                            }


                            view.BackTomainMenu();
                            return;
                        }

                        int move=Integer.parseInt(playermove.getMove());
                        Platform.runLater(()-> {
                            playMove(move);


                        });
                    } catch (InterruptedException e){};
                        enable_pegs();


                };
                new Thread(opponent).start();

            }

        } else if (mode == AI_VS_SERVER) {
            disable_pegs();
            player1=new LocalPlayer(GameClient.username);
            player2=new ExternalPlayer(matchStarted.getOpponent());
            if(matchStarted.getPlayerToMove().equals(GameClient.username)){
                side=PLAYER1;
                setText(player1.getName() + "'s turn!");
            } else {

                side=PLAYER2;
                setText(player2.getName() + "'s turn!");

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

    public void fill_pegs() {
        for (int i = 0; i < boardsize; i++) {
            for (int o = 0; o < boardsize; o++) {
                Peg peg = new TicTactToePeg(i, o);
                peg.setMinSize(100, 100);
                pegs[i][o] = peg;
                //i = row
                //o=column

            }
        }
    }
    public void playMove(int move) {

            Peg peg = pegs[move / boardsize][move % boardsize];

            if (side == PLAYER2) {

                peg.setTile(1);

            } else {
                peg.setTile(0);

            }

            if (side == PLAYER1) {
                this.side = PLAYER2;
                setText(player2.getName() + "'s turn!");

            } else {
                this.side = PLAYER1;
                setText(player1.getName() + "'s turn!");
            }

    }

    public void play_ai_vs_server() {
        Runnable run=()->{
            if(side==PLAYER2) {
                try {

                    Move playermove=StrategicGameClient.getInstance().getMoveQueue().poll(30, TimeUnit.SECONDS);
                    if(playermove==null){
                        Win win = StrategicGameClient.getInstance().getWinQueue().take();
                        if (win.getComment().equals("Player forfeited match")) {
                            setText(player1.getName() + " wins! " + player2.getName() + " gave up!");
                        } else if (win.getComment().equals("Client disconnected")) {
                            setText(player1.getName() + " wins! " + player2.getName() + " lost connection!");
                        } else {
                            setText(player1.getName() + " wins! " + player2.getName() + " took too long!");
                        }


                        view.BackTomainMenu();
                        return;
                    }

                    int move = Integer.parseInt(playermove.getMove());

                    Platform.runLater(()-> {

                        playMove(move);

                    });

                } catch (InterruptedException e) {
                }

            }

            while (!gameOver()){


                Platform.runLater(()-> {
                    if(!gameOver()) {
                        int best = calculateBest();


                        StrategicGameClient.getInstance().doMove(best);
                        playMove(best);
                        gameOver();
                    }

                });





                try {
                    StrategicGameClient.getInstance().getMoveQueue().take();
                } catch (InterruptedException e) {
                }


                Move playermove = null;
                try {
                    playermove = StrategicGameClient.getInstance().getMoveQueue().take();
                } catch (InterruptedException e) {
                }

                int opponentmove = Integer.parseInt(playermove.getMove());

                    Platform.runLater(() -> {
                        if(!gameOver()) {
                        playMove(opponentmove);
                        gameOver();
                        }


                    });



            }

        };
        new Thread(run).start();


    }


    public int calculateBest() {

        AI.pegs_to_board(pegs);
        int best = AI.chooseMove();

        return best;
    }


    // Returns whether 'side' has won in this position
    public boolean isAWin(int side) {
        //sides:
        //top
        if ((side == pegs[0][0].pegState) && (side == pegs[0][1].pegState) && (side == pegs[0][2].pegState)) {
            return true;

        }
        //bottom
        if ((side == pegs[2][0].pegState) && (side == pegs[2][1].pegState) && (side == pegs[2][2].pegState)) {
            return true;

        }
        //left
        if ((side == pegs[0][0].pegState) && (side == pegs[1][0].pegState) && (side == pegs[2][0].pegState)) {
            return true;

        }
        //right
        if ((side == pegs[0][2].pegState) && (side == pegs[1][2].pegState) && (side == pegs[2][2].pegState)) {
            return true;

        }


        //middle:
        //horizontal
        if ((side == pegs[1][0].pegState) && (side == pegs[1][1].pegState) && (side == pegs[1][2].pegState)) {
            return true;

        }
        //vertical
        if ((side == pegs[0][1].pegState) && (side == pegs[1][1].pegState) && (side == pegs[2][1].pegState)) {
            return true;

        }


        //diagonal bottom left corner to top right
        if ((side == pegs[2][0].pegState) && (side == pegs[1][1].pegState) && (side == pegs[0][2].pegState)) {
            return true;

        }
        //diagonal bottom right corner to top left
        if ((side == pegs[2][2].pegState) && (side == pegs[1][1].pegState) && (side == pegs[0][0].pegState)) {

            return true;
        }

        return false;
    }


}











