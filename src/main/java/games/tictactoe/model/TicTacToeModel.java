package games.tictactoe.model;

import ai.AI;
import communication.StrategicGameClient;
import communication.events.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import model.Model;
import model.Peg;
import player.ExternalPlayer;
import player.LocalPlayer;
import view.BoardView;
import view.View;

/**
 * Created by Singh van Offeren
 */

public class TicTacToeModel extends Model
        //The games.tictactoe logic
{

    public TicTacToeModel(int boardsize, View view, AI ai, MatchStarted matchStarted) {
        super(boardsize, view, ai, matchStarted);
    }

    public void initSide() {
        if (is_mode(HUMAN_VS_AI)) {

            player1 = new LocalPlayer("Player");
            player2 = new LocalPlayer("Computer");


            side = random.nextInt(2);
            if (side == PLAYER2) {
                player1.setSymbol(getSecondSymbol());
                player2.setSymbol(getFirstSymbol());

                setText(player2.getName() + "'s turn!");

                int best = calculateBest();
                Platform.runLater(()-> {
                    playMove(best);
                });


            } else {
                player1.setSymbol(getFirstSymbol());
                player2.setSymbol(getSecondSymbol());
                setText(player1.getName() + "'s turn!");
            }


        } else if (is_mode(HUMAN_VS_HUMAN)) {
            side = 0;
            player1 = new LocalPlayer(username);
            player2 = new LocalPlayer(username2);
            if (side == PLAYER2) {
                player1.setSymbol(getSecondSymbol());
                player2.setSymbol(getFirstSymbol());

                setText(player2.getName() + " 's turn!");
            } else {
                player1.setSymbol(getFirstSymbol());
                player2.setSymbol(getSecondSymbol());

                setText(player1.getName() + " 's turn!");

            }
        }
        //online multiplayer
        else {
            //init players when playing online
            if(matchStarted!=null) {
                player1 = new LocalPlayer(Model.username);
                player2 = new ExternalPlayer(matchStarted.getOpponent());

                if (matchStarted.getPlayerToMove().equals(Model.username)) {
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

        TicTactToePeg peg = (TicTactToePeg) pegs[move / boardsize][move % boardsize];

        if (side == PLAYER2) {

            peg.setTile(1,player2.getSymbol());

        } else {
            peg.setTile(0,player1.getSymbol());

        }

        change_side();
    }




    public int calculateBest() {

        AI.pegs_to_board(pegs);

        int best = AI.chooseMove();

        return best;
    }



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

    //check if gameover, if so update the text above the board and disables it
    @Override
    public boolean gameOver() {
        this.position = positionValue();
        if (position != UNCLEAR) {
            ((BoardView) view).SetBackToMainMenu();
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

    public Image getFirstSymbol() {
        return new Image("x.png");
    }


    public Image getSecondSymbol() {
        return new Image("o.png");
    }

    @Override
    public void update(Event event) {

        if (mode == Model.HUMAN_VS_SERVER || mode == Model.AI_VS_SERVER) {


            if (event instanceof Move) {
                Move move = (Move) event;
                if (move.getPlayer().equals(player2.getName())) {
                    //
                    Platform.runLater(() -> {
                        try {
                            int opponentmove = Integer.parseInt(move.getMove());

                            if (moveOk(opponentmove)) {
                                playMove(opponentmove);
                            }
                        } catch (NumberFormatException e) {
                        }

                    });

                    if (mode != AI_VS_SERVER) {
                        enable_pegs();
                    }
                }
            } else if (event instanceof Win) {

                Win win = (Win) event;
                if (win.getComment().equals("Player forfeited match")) {
                    Platform.runLater(() -> {
                        setText(player1.getName() + " wins! " + player2.getName() + " gave up!");
                    });
                } else if (win.getComment().equals("Client disconnected")) {
                    Platform.runLater(() -> {
                        setText(player1.getName() + " wins! " + player2.getName() + " lost connection!");
                    });
                } else if (win.getComment().equals("Turn timelimit reached")) {
                    Platform.runLater(() -> {
                        setText(player1.getName() + " wins! " + player2.getName() + " took too long!");
                    });
                } else if (win.getComment().equals("Illegal move")) {
                    Platform.runLater(() -> {
                        setText(player1.getName() + " wins! " + player2.getName() + " played an illegal move!");
                    });
                } else {
                    Platform.runLater(() -> {
                        setText(player1.getName() + " wins!");
                    });
                }
                Platform.runLater(() -> {
                    ((BoardView) view).SetBackToMainMenu();
                });
                disable_pegs();
            } else if (event instanceof Loss) {
                Loss loss = (Loss) event;
                if (loss.getComment().equals("Turn timelimit reached")) {
                    Platform.runLater(() -> {
                        setText(player2.getName() + " wins! " + player1.getName() + " took too long!");
                    });
                } else if (loss.getComment().equals("Player forfeited match")) {
                    Platform.runLater(() -> {
                        setText(player2.getName() + " wins! " + player1.getName() + " gave up!");
                    });
                } else {
                    Platform.runLater(() -> {
                        setText(player2.getName() + " wins!");
                    });
                }
                Platform.runLater(() -> {
                    ((BoardView) view).SetBackToMainMenu();
                });
                disable_pegs();
            } else if (event instanceof Draw) {
                Platform.runLater(() -> {
                    setText("Nobody" + " wins! It's a draw!");
                });
                Platform.runLater(() -> {
                    ((BoardView) view).SetBackToMainMenu();
                });
                disable_pegs();
            } else if (event instanceof YourTurn) {
                if (mode == AI_VS_SERVER) {

                    Platform.runLater(() -> {
                        int best = calculateBest();
                        playMove(best);
                        StrategicGameClient.getInstance().doMove(best);
                    });
                }
            }
        }
    }
}