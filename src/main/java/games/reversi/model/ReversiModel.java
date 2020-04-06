package games.reversi.model;

import communication.StrategicGameClient;
import communication.events.MatchStarted;
import javafx.application.Platform;
import model.Model;
import model.Peg;
import player.LocalPlayer;
import view.BoardView;
import view.View;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class ReversiModel extends Model {
    ArrayList<Integer> validMoves = new ArrayList<>();
    int amountBlack = 0;
    int amountWhite = 0;

    //Model
    public ReversiModel(int boardsize, View view, ReversiAI AI, MatchStarted matchStarted) {
        super(boardsize, view, AI,matchStarted);
        StrategicGameClient.getInstance().getEventBus().addObserver(this);
        AI.setModel(this);

    }
    //Model


    public void fill_pegs() {
        for (int i = 0; i < boardsize; i++) {
            for (int o = 0; o < boardsize; o++) {
                Peg peg = new ReversiPeg(i, o);
                peg.setMinSize(60, 60);
                pegs[i][o] = peg;
                //i = row
                //o=column

            }
        }
    }
    public void initSide() {
        side = PLAYER1;

        player1 = new LocalPlayer("Black");
        player2 = new LocalPlayer("White");


        if (side == PLAYER1) {
            setText(player1.getName() + "'s turn!");
        } else {
            setText(player2.getName() + "'s turn!");
        }

    }

    public void playMove(int move) {
        Peg peg = pegs[move / boardsize][move % boardsize];
            if (side == PLAYER2) {

                peg.setTile(0);
                checkAndSet(peg.getXPosition(),peg.getZPosition());
                this.side = PLAYER1;
                updateAmountPegsBoard();
                setText("Black's turn!" + "  Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);

            } else {

                peg.setTile(1);
                checkAndSet(peg.getXPosition(),peg.getZPosition());
                this.side = PLAYER2;
                updateAmountPegsBoard();
                setText("White's turn!" + "  Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);
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
        if (checkVerticalL(x,z)>0)
            setVerticalL(x,z);
        if (checkVerticalR(x,z)>0)
            setVerticalR(x,z);
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
                        checkVerticalL(row,col)>0||checkVerticalR(row,col) > 0||checkDiagonalDL(row,col)>0||checkDiagonalDR(row,col)>0||checkDiagonalUL(row,col)>0||checkDiagonalUR(row,col)>0)) {
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
     */
    private int checkVerticalL(int posX, int posZ) {
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
     */
    private void setVerticalL(int posX, int posZ) {
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
     */
    private int checkVerticalR(int posX, int posZ) {
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
     */
    private void setVerticalR(int posX, int posZ) {
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

        if(!getValidMoves().isEmpty()){
            return false;
        }

        if(side == 0 && amountWhite < amountBlack){
            return true;
        } else if (side == 1 && amountBlack < amountWhite){
            return true;
        } else {
            return false;
        }
    }

    //check if gameover, if so update the text above the board and disables it
    public boolean gameOver() {
        this.position = positionValue();
        if (position != UNCLEAR) {
            ((BoardView) view).SetBackToMainMenu();
            Platform.runLater(() -> {
                disable_pegs();
                if (position == DRAW) {

                    setText(" It's a draw, " + winner() + " wins!" + "  Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);
                } else {
                    setText(" Match over, " + winner() + " wins!" + "  Black - " + this.amountBlack + " | " + "White - "+ this.amountWhite);
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
}
