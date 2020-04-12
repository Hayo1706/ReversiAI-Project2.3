package games.reversi.model;

import communication.StrategicGameClient;
import communication.events.*;
import games.reversi.view.Animation;
import javafx.application.Platform;
import model.Model;
import model.Peg;
import player.ExternalPlayer;
import player.LocalPlayer;
import player.Player;
import view.BoardView;
import view.View;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class ReversiModel extends Model {
    ArrayList<Integer> validMoves = new ArrayList<>();
    int amountBlack = 2;
    int amountWhite = 2;
    Player you;
    Player opponent;
    ReversiAI AI;
    //ensures that the first move is always in initSide because YourTurn is unreliable
    private boolean firstmove=false;
    private boolean yourturn=false;

    //Model
    public ReversiModel(int boardsize, View view, ReversiAI AI, MatchStarted matchStarted) {
        super(boardsize, view, AI,matchStarted);
        StrategicGameClient.getInstance().getEventBus().addObserver(this);
        AI.setModel(this);
        this.AI = AI;

    }
    //Model


    public void fill_pegs() {
        for (int i = 0; i < boardsize; i++) {
            for (int o = 0; o < boardsize; o++) {
                Peg peg = new ReversiPeg(i, o);
                //for testing: peg.setText(String.valueOf(i*8+o));
                peg.setMinSize(60, 60);
                pegs[i][o] = peg;
                //i = row
                //o = column
            }
        }
    }
    public void initSide() {
        side = PLAYER1;
        if (is_mode(HUMAN_VS_AI)) {

            player1 = new LocalPlayer("Black");
            player2 = new LocalPlayer("White");

                setText(player1.getName() + "'s turn!" + "  Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);

            Animation animation = new Animation(get_pegs());
            animation.start();
        } else if (is_mode(HUMAN_VS_HUMAN)) {
            side = PLAYER1;

            player1 = new LocalPlayer("Black");
            player2 = new LocalPlayer("White");



            setText(player1.getName() + "'s turn!" + "  Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);
            Animation animation = new Animation(get_pegs());
            animation.start();
        }
        //online multiplayer
        else {
            //init players when playing online
            if(matchStarted!=null) {


                if (matchStarted.getPlayerToMove().equals(Model.username)) {
                    yourturn=true;
                    player1 = new LocalPlayer(Model.username);
                    player2 = new ExternalPlayer(matchStarted.getOpponent());
                    you=player1;
                    opponent=player2;
                    AI.setSide(PLAYER1);
                    if (mode == AI_VS_SERVER ) {

                        Platform.runLater(() -> {

                            int move=calculateBest();
                            StrategicGameClient.getInstance().doMove(move);
                            playMove(move);
                            yourturn=false;
                            addToValidMoves();
                            if(checkIfValidMoves())
                                setValidMoves();
                            firstmove=true;
                        });
                    }

                } else {
                    AI.setSide(PLAYER2);
                    yourturn=false;
                    player1 = new ExternalPlayer(matchStarted.getOpponent());
                    player2 = new LocalPlayer(Model.username);
                    you=player2;
                    opponent=player1;



                }

                setText(player1.getName() + "'s turn!" + "  Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);
            }
        }
    }

    public void playMove(int move) {
        Peg peg = pegs[move / boardsize][move % boardsize];
            if (side == PLAYER2) {
                if(!validMoves.contains(move))
                    System.out.println("NEE ZIT ER NIET IN");
                peg.setTile(0);
                checkAndSet(peg.getXPosition(),peg.getZPosition());
                this.side = PLAYER1;
                updateAmountPegsBoard();
                setText(player1.getName() + "'s turn!" + "  Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);
            } else {
                if(!validMoves.contains(move))
                    System.out.println("NEE ZIT ER NIET IN");
                peg.setTile(1);
                checkAndSet(peg.getXPosition(),peg.getZPosition());
                this.side = PLAYER2;
                updateAmountPegsBoard();
                setText(player2.getName() + "'s turn!" + "  Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);
            }
    }

    /**
     * @author Maurice Wijker
     * @param x check and set peg on x (vertical)
     * @param z check and set peg on z (horizontal)
     */

    public void checkAndSet(int x,int z){
        if(checkHorizontalL(x,z)>0)
            setHorizontalL(x, z);
        if(checkHorizontalR(x,z)>0)
            setHorizontalR(x,z);
        if (checkVerticalU(x,z)>0)
            setVerticalU(x,z);
        if (checkVerticalD(x,z)>0)
            setVerticalD(x,z);
        if (checkDiagonalUR(x,z)>0)
            setDiagonalUR(x,z);
        if (checkDiagonalUL(x,z)>0)
            setDiagonalUL(x,z);
        if (checkDiagonalDR(x,z)>0)
            setDiagonalDR(x,z);
        if (checkDiagonalDL(x,z)>0)
            setDiagonalDL(x,z);
    }

    /**
     * @author Maurice Wijker
     * sets moves that are available
     */

    public void addToValidMoves() {
        validMoves.clear();
        disable_pegs();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((pegs[abs(row)][abs(col)].getPegState() == 2) && (checkHorizontalL(row,col)>0 || checkHorizontalR(row,col)>0||
                        checkVerticalU(row,col)>0||checkVerticalD(row,col) > 0||checkDiagonalDL(row,col)>0||checkDiagonalDR(row,col)>0||checkDiagonalUL(row,col)>0||checkDiagonalUR(row,col)>0)) {
                    validMoves.add(row*8 + col);
                }
            }
        }
    }
    public void setValidMoves(){
        for (Integer var:validMoves) {
            pegs[var/boardsize][var%boardsize].setDisable(false);
            pegs[var/boardsize][var%boardsize].setStyle("-fx-background-color: #3c8e55");
        }
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method Checks to the west whether a colour of the opponent occurs
     */

    private int checkHorizontalL(int posX, int posZ) {
        int side = getSide();
        int temp = 0;
        if (posZ - 1 >= 0 && get_pegs()[posX][posZ - 1].getPegState() == side) {
            for (int col = posZ - 2; col >= 0; col--) {
                if (get_pegs()[posX][col].getPegState() == abs(side - 1)) {
                    temp++;
                } else if (get_pegs()[posX][col].getPegState() == 2) {
                    break;
                }
            }
        }
        return temp;
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method sets pegs to the west to current players pegs if a colour of the opponent occurs
     */
    private void setHorizontalL(int posX, int posZ) {
        int side = getSide();
        for (int col = posZ - 1; col >= 0; col--) {
            if (get_pegs()[posX][col].getPegState() == side) {
                get_pegs()[posX][col].setTile(abs(side - 1));
            } else{
                return;
            }
        }
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method Checks to the east whether a colour of the opponent occurs
     */
    private int checkHorizontalR(int posX, int posZ) {
        int side = getSide();
        int temp = 0;
        if (posZ + 1 < 8 && get_pegs()[posX][posZ + 1].getPegState() == side) {
            for (int col = posZ + 2; col < 8; col++) {
                if (get_pegs()[posX][col].getPegState() == abs(side - 1)) {
                    temp++;
                } else if (get_pegs()[posX][col].getPegState() == 2) {
                    break;
                }
            }
        }
        return temp;
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method sets pegs to the east to current players pegs if a colour of the opponent occurs
     */
    private void setHorizontalR(int posX, int posZ) {
        int side = getSide();
        for (int col = posZ + 1; col < 8; col++) {
            if (get_pegs()[posX][col].getPegState() == side)
                get_pegs()[posX][col].setTile(abs(side - 1));
            else
                return;
        }
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method Checks to the north whether a colour of the opponent occurs
     */
    private int checkVerticalU(int posX, int posZ) {
        int side = getSide();
        int temp = 0;
        if (posX - 1 >= 0 && get_pegs()[posX - 1][posZ].getPegState() == side) {

            for (int row = posX - 2; row >= -0; row--) {
                if (get_pegs()[row][posZ].getPegState() == abs(side - 1)) {
                    temp++;
                } else if (get_pegs()[row][posZ].getPegState() == 2) {
                    break;
                }
            }
        }
        return temp;
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method sets pegs to the north to current players pegs if a colour of the opponent occurs
     */
    private void setVerticalU(int posX, int posZ) {
        int side = getSide();
        for (int row = posX - 1; row >= -0; row--) {
            if (get_pegs()[row][posZ].getPegState() == side)
                get_pegs()[row][posZ].setTile(abs(side - 1));
            else
                return;
        }
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method sets pegs to the  to current players pegs if a colour of the opponent occurs
     */
    private int checkVerticalD(int posX, int posZ) {
        int side = getSide();
        int temp = 0;
        if (posX + 1 < 8 && get_pegs()[posX + 1][posZ].getPegState() == side) {
            for (int row = posX + 2; row < 8; row++) {
                if (get_pegs()[row][posZ].getPegState() == abs(side - 1)) {
                    temp++;
                } else if (get_pegs()[row][posZ].getPegState() == 2) {
                    break;
                }
            }
        }
        return temp;
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method Checks to the south whether a colour of the opponent occurs
     */
    private void setVerticalD(int posX, int posZ) {
        int side = getSide();
        for (int row = posX + 1; row < 8; row++) {
            if (get_pegs()[row][posZ].getPegState() == side)
                get_pegs()[row][posZ].setTile(abs(side - 1));
            else
                return;
        }
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method Checks to the south-east whether a colour of the opponent occurs
     */
    private int checkDiagonalDR(int posX, int posZ) {
        int side = getSide();
        int temp = 0;
        if (posX + 1 < 8 && posZ + 1 < 8 && get_pegs()[posX + 1][posZ + 1].getPegState() == side) {
            for (int i = posX + 2, o = posZ + 2; i < 8 && o < 8; i++, o++) {
                if (get_pegs()[i][o].getPegState() == abs(side - 1)) {
                    temp++;
                } else if (get_pegs()[i][o].getPegState() == 2) {
                    break;
                }
            }
        }
        return temp;
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method sets pegs to the south-east to current players pegs if a colour of the opponent occurs
     */
    private void setDiagonalDR(int posX, int posZ) {
        int side = getSide();
        for (int i = posX + 1, o = posZ + 1; i < 8 && o < 8; i++, o++) {
            if (get_pegs()[i][o].getPegState() == side)
                get_pegs()[i][o].setTile(abs(side - 1));
            else
                return;
        }
    }


    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method Checks to the north-west whether a colour of the opponent occurs
     */
    private int checkDiagonalUL(int posX, int posZ) {
        int side = getSide();
        int temp = 0;
        if (posX - 1 >= 0 && posZ - 1 >= 0 && get_pegs()[posX - 1][posZ - 1].getPegState() == side) {
            for (int i = posX - 2, o = posZ - 2; i >= 0 && o >= 0; i--, o--) {
                if (get_pegs()[i][o].getPegState() == abs(side - 1)) {
                    temp++;
                } else if (get_pegs()[i][o].getPegState() == 2) {
                    break;
                }
            }
        }
        return temp;
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method sets pegs to the north-west to current players pegs if a colour of the opponent occurs
     */
    private void setDiagonalUL(int posX, int posZ) {
        int side = getSide();
        for (int i = posX - 1, o = posZ - 1; i >= 0 && o >= 0; i--, o--) {
            if (get_pegs()[i][o].getPegState() == side)
                get_pegs()[i][o].setTile(abs(side - 1));
            else
                return;
        }
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method Checks to the north-east whether a colour of the opponent occurs
     */
    private int checkDiagonalUR(int posX, int posZ) {
        int side = getSide();
        int temp = 0;
        if (posX + 1 < 8 && posZ - 1 >= 0 && get_pegs()[posX + 1][posZ - 1].getPegState() == side) {
            for (int i = posX + 2, o = posZ - 2; i < 8 && o >= 0; i++, o--) {
                if (get_pegs()[i][o].getPegState() == abs(side - 1)) {
                    temp++;
                } else if (get_pegs()[i][o].getPegState() == 2) {
                    break;
                }
            }
        }
        return temp;
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method sets pegs to the north-east to current players pegs if a colour of the opponent occurs
     */
    private void setDiagonalUR(int posX, int posZ) {
        int side = getSide();
        for (int i = posX + 1, o = posZ - 1; i < 8 && o >= 0; i++, o--) {
            if (get_pegs()[i][o].getPegState() == side)
                get_pegs()[i][o].setTile(abs(side - 1));
            else
                return;
        }
    }


    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method Checks to the south-west whether a colour of the opponent occurs
     */
    private int checkDiagonalDL(int posX,int posZ){
        int side = getSide();
        int temp = 0;
        if(posX-1 >= 0 && posZ+1 < 8 && get_pegs()[posX - 1][posZ + 1].getPegState() == side) {
            for (int i = posX-2,o = posZ+2; i >= 0 && o < 8; i--,o++) {
                if (get_pegs()[i][o].getPegState() == abs(side-1)) {
                    temp++;
                } else if (get_pegs()[i][o].getPegState() == 2) {
                    break;
                }
            }
        }
        return temp;
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     * @method sets pegs to the south-west to current players pegs if a colour of the opponent occurs
     */
    private void setDiagonalDL(int posX,int posZ){
        int side = getSide();
        for (int i = posX-1,o = posZ+1; i >= 0 && o < 8; i--,o++) {
            if (get_pegs()[i][o].getPegState() == side)
                get_pegs()[i][o].setTile(abs(side - 1));
            else
                return;
        }
    }

    /**
     * @author Maurice Wijker
     * @return get validMoves in a Set
     */
    public ArrayList<Integer> getValidMoves() {
        return validMoves;
    }

    /**
     * @author Maurice Wijker
     * @return cur amountofblacks on board
     */
    public int getAmountBlack() {
        return amountBlack;
    }

    /**
     * @author Maurice Wijker
     * @return cur amountofwhites on board
     */
    public int getAmountWhite() {
        return amountWhite;
    }

    public int calculateBest() {
        return AI.chooseMove();
    }


    // Returns whether 'side' has won in this position

    /**
     * @author Maurice Wijker
     *
     * @return is it a win for side?
     */

    public boolean isAWin(int side) {

        if(!pegsIsFull()){
            return false;
        }

        if(side == 0 && amountWhite < amountBlack){
            return true;
        } else return side == 1 && amountBlack < amountWhite;

    }



    //check if gameover, if so update the text above the board and disables it
    @Override
    public boolean gameOver(boolean force) {
        this.position = positionValue();
        if (position != UNCLEAR || force) {
            ((BoardView) view).SetBackToMainMenu();
            Platform.runLater(() -> {
                disable_pegs();
                    if(position==DRAW){
                        setText(" Match over, it's a draw!" + "  Black - " + this.amountBlack + " | " + "White - " + this.amountWhite);
                    } else {
                        setText(" Match over, " + winner() + " wins!" + "  Black - " + this.amountBlack + " | " + "White - " + this.amountWhite);
                    }
            });
        }
        return this.position != UNCLEAR;
    }


    /**
     * @author Maurice Wijker
     *
     * updates amount (white or black) of pegs on board
     */
    public void updateAmountPegsBoard(){

        this.amountBlack = 0;
        this.amountWhite = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(get_pegs()[i][j].getPegState() == 0){
                    this.amountWhite++;
                } else if(get_pegs()[i][j].getPegState() == 1){
                    this.amountBlack++;
                }
            }
        }
    }

    //If no available moves are present, other player gets the turn.
    public boolean checkIfValidMoves() {
        if (getValidMoves().isEmpty()) {
            setSide(StrictMath.abs(getSide() - 1));
            addToValidMoves();
            if(validMoves.isEmpty())
                gameOver(true);
            setValidMoves();
            if (getSide() == 0)
                setText("White has no moves, Black's turn!");
            else
                setText("Black has no moves, White's turn!");
            return false;
        }
        return true;
    }
    public boolean getYourTurn(){
        return yourturn;
    }
    public void setYourTurn(boolean condition){
        yourturn=condition;
    }
    public void update(Event event) {
        if (mode == Model.HUMAN_VS_SERVER || mode == Model.AI_VS_SERVER) {

            if (event instanceof Move) {
                Move move = (Move) event;
                if (move.getPlayer().equals(opponent.getName())) {
                    //
                    Platform.runLater(() -> {
                        try {
                            int opponentmove = Integer.parseInt(move.getMove());

                            if (moveOk(opponentmove)) {
                                playMove(opponentmove);
                            }
                        } catch (NumberFormatException e) {
                        }
                        addToValidMoves();
                        if(checkIfValidMoves())
                           setValidMoves();
                    });

                    if (mode != AI_VS_SERVER) {
                        enable_pegs();
                    }
                    firstmove=true;
                }
            } else if (event instanceof Win) {

                Win win = (Win) event;
                switch (win.getComment()) {
                    case "Player forfeited match":
                        Platform.runLater(() -> {
                            setText(you.getName() + " wins! " + opponent.getName() + " gave up!");
                        });
                        break;
                    case "Client disconnected":
                        Platform.runLater(() -> {
                            setText(you.getName() + " wins! " + opponent.getName() + " lost connection!");
                        });
                        break;
                    case "Turn timelimit reached":
                        Platform.runLater(() -> {
                            setText(you.getName() + " wins! " + opponent.getName() + " took too long!");
                        });
                        break;
                    case "Illegal move":
                        Platform.runLater(() -> {
                            setText(you.getName() + " wins! " + opponent.getName() + " played an illegal move!");
                        });
                        break;
                    default:
                        Platform.runLater(() -> {
                            setText(you.getName() + " wins! " + "Black - " + this.amountBlack + " | " + "White - " + this.amountWhite);
                        });
                        break;
                }
                Platform.runLater(() -> {
                    ((BoardView) view).SetBackToMainMenu();
                });
                disable_pegs();
                if(Model.mode==AI_VS_SERVER) {
                    Platform.runLater(()-> {
                        ((BoardView) view).goBackToList();
                    });
                }
            } else if (event instanceof Loss) {
                Loss loss = (Loss) event;
                if (loss.getComment().equals("Turn timelimit reached")) {
                    Platform.runLater(() -> {
                        setText(opponent.getName() + " wins! " + you.getName() + " took too long!");
                    });
                } else if (loss.getComment().equals("Player forfeited match")) {
                    Platform.runLater(() -> {
                        setText(opponent.getName() + " wins! " + you.getName() + " gave up!");
                    });
                } else {
                    Platform.runLater(() -> {
                        setText(opponent.getName() + " wins! "+"Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);
                    });
                }
                Platform.runLater(() -> {
                    ((BoardView) view).SetBackToMainMenu();
                });
                disable_pegs();
                if(Model.mode==AI_VS_SERVER) {
                    Platform.runLater(()-> {
                        ((BoardView) view).goBackToList();
                    });
                }


            }  else if (event instanceof Draw) {
                Platform.runLater(() -> {
                    setText("Nobody" + " wins! It's a draw!");
                });
                Platform.runLater(() -> {
                    ((BoardView) view).SetBackToMainMenu();
                });
                disable_pegs();

                if(Model.mode==AI_VS_SERVER) {
                    Platform.runLater(()-> {
                        ((BoardView) view).goBackToList();
                    });
                }
            }else if (event instanceof YourTurn) {
                yourturn=true;

                if (mode == AI_VS_SERVER && firstmove ) {

                    Platform.runLater(() -> {

                            int move=calculateBest();
                            StrategicGameClient.getInstance().doMove(move);
                            playMove(move);
                            yourturn=false;
                        addToValidMoves();
                        if(checkIfValidMoves())
                           setValidMoves();
                    });
                }


            }
        }
    }
}
