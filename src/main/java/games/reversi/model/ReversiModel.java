package games.reversi.model;

import communication.events.MatchStarted;
import javafx.scene.image.Image;
import model.Model;
import model.Peg;
import player.LocalPlayer;
import view.View;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;


public class ReversiModel extends Model {
    Set<Integer> validMoves = new HashSet<>();
    int amountBlack;
    int amountWhite;

    //Model
    public ReversiModel(int boardsize, View view, ai.AI AI, MatchStarted matchStarted) {
        super(boardsize, view, AI,matchStarted);
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
                setText("Black's turn!" + " " + "Black - "+ amountBlack + "| " + "White - "+ amountWhite);
            } else {

                peg.setTile(1);
                checkAndSet(peg.getXPosition(),peg.getZPosition());
                this.side = PLAYER2;
                setText("White's turn!" + " " + "Black - "+ amountBlack + "| " + "White - "+ amountWhite);
            }
    }

    /**
     * @author Maurice Wijker
     * @param x check and set peg on x (vertical)
     * @param z check and set peg on z (horizontal)
     */

    public void checkAndSet(int x,int z){
        if(checkHorizontalL(x,z))
            setHorizontalL(x, z);
        if(checkHorizontalR(x,z))
            setHorizontalR(x,z);
        if (checkVerticalL(x,z))
            setVerticalL(x,z);
        if (checkVerticalR(x,z))
            setVerticalR(x,z);
        if (checkDiagonalUR(x,z))
            setDiagonalUR(x,z);
        if (checkDiagonalUL(x,z))
            setDiagonalUL(x,z);
        if (checkDiagonalDR(x,z))
            setDiagonalDR(x,z);
        if (checkDiagonalDL(x,z))
            setDiagonalDL(x,z);
    }

    /**
     * @author Maurice Wijker
     * sets moves that are available
     */

    public void setValidMoves() {
        validMoves.clear();
        disable_pegs();
        Peg[][] pegs = get_pegs();


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
//                if(pegs[row][col].pegState == 0)
//                    amountWhite++;
//                else if(pegs[row][col].pegState == 1)
//                    amountBlack++;
                if ((pegs[abs(row)][abs(col)].getPegState() == 2) && (checkHorizontalL(row,col) || checkHorizontalR(row,col)||
                        checkVerticalL(row,col)||checkVerticalR(row,col)||checkDiagonalDL(row,col)||checkDiagonalDR(row,col)||checkDiagonalUL(row,col)||checkDiagonalUR(row,col))) {
                    pegs[abs(row)][abs(col)].setDisable(false);
                    pegs[abs(row)][abs(col)].setStyle("-fx-background-color: #3c8e55");
                    validMoves.add(row*8 + col);
                }
            }
        }
    }

    /**
     * @author Maurice Wijker
     * @param posX init row
     * @param posZ init col
     */

    private boolean checkHorizontalL(int posX, int posZ) {
        int side = getSide();
        if (posZ - 1 >= 0 && get_pegs()[posX][posZ - 1].getPegState() == side) {
            for (int col = posZ - 2; col >= 0; col--) {
                if (get_pegs()[posX][col].getPegState() == abs(side - 1)) {
                    return true;
                } else if (get_pegs()[posX][col].getPegState() == 2) {
                    break;
                }
            }
        }
        return false;
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
    private boolean checkHorizontalR(int posX, int posZ) {
        int side = getSide();
        if (posZ + 1 < 8 && get_pegs()[posX][posZ + 1].getPegState() == side) {
            for (int col = posZ + 2; col < 8; col++) {
                if (get_pegs()[posX][col].getPegState() == abs(side - 1)) {
                    return true;
                } else if (get_pegs()[posX][col].getPegState() == 2) {
                    break;
                }
            }
        }
        return false;
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
    private boolean checkVerticalL(int posX, int posZ) {
        int side = getSide();
        if (posX - 1 >= 0 && get_pegs()[posX - 1][posZ].getPegState() == side) {

            for (int row = posX - 2; row >= -0; row--) {
                if (get_pegs()[row][posZ].getPegState() == abs(side - 1)) {
                    return true;
                } else if (get_pegs()[row][posZ].getPegState() == 2) {
                    break;
                }
            }
        }
        return false;
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
    private boolean checkVerticalR(int posX, int posZ) {
        int side = getSide();
        if (posX + 1 < 8 && get_pegs()[posX + 1][posZ].getPegState() == side) {
            for (int row = posX + 2; row < 8; row++) {
                if (get_pegs()[row][posZ].getPegState() == abs(side - 1)) {
                    return true;
                } else if (get_pegs()[row][posZ].getPegState() == 2) {
                    break;
                }
            }
        }
        return false;
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
    private boolean checkDiagonalDR(int posX, int posZ) {
        int side = getSide();
        if (posX + 1 < 8 && posZ + 1 < 8 && get_pegs()[posX + 1][posZ + 1].getPegState() == side) {
            for (int i = posX + 2, o = posZ + 2; i < 8 && o < 8; i++, o++) {
                if (get_pegs()[i][o].getPegState() == abs(side - 1)) {
                    return true;
                } else if (get_pegs()[i][o].getPegState() == 2) {
                    break;
                }
            }
        }
        return false;
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
    private boolean checkDiagonalUL(int posX, int posZ) {
        int side = getSide();
        if (posX - 1 >= 0 && posZ - 1 >= 0 && get_pegs()[posX - 1][posZ - 1].getPegState() == side) {
            for (int i = posX - 2, o = posZ - 2; i >= 0 && o >= 0; i--, o--) {
                if (get_pegs()[i][o].getPegState() == abs(side - 1)) {
                    return true;
                } else if (get_pegs()[i][o].getPegState() == 2) {
                    break;
                }
            }
        }
        return false;
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
    private boolean checkDiagonalUR(int posX, int posZ) {
        int side = getSide();
        if (posX + 1 < 8 && posZ - 1 >= 0 && get_pegs()[posX + 1][posZ - 1].getPegState() == side) {
            for (int i = posX + 2, o = posZ - 2; i < 8 && o >= 0; i++, o--) {
                if (get_pegs()[i][o].getPegState() == abs(side - 1)) {
                    return true;
                } else if (get_pegs()[i][o].getPegState() == 2) {
                    break;
                }
            }
        }
        return false;
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
    private boolean checkDiagonalDL(int posX,int posZ){
        int side = getSide();
        if(posX-1 >= 0 && posZ+1 < 8 && get_pegs()[posX - 1][posZ + 1].getPegState() == side) {
            for (int i = posX-2,o = posZ+2; i >= 0 && o < 8; i--,o++) {
                if (get_pegs()[i][o].getPegState() == abs(side-1)) {
                    return true;
                } else if (get_pegs()[i][o].getPegState() == 2) {
                    break;
                }
            }
        }
        return false;
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
    public Set<Integer> getValidMoves() {
        return validMoves;
    }

    public int calculateBest() {
        // TODO: 28/03/2020
        return 0;
    }


    // Returns whether 'side' has won in this position

    /**
     * @author Maurice Wijker
     *
     * @return is it a win for side?
     */

    public boolean isAWin(int side) {

        amountBlack = 0;
        amountWhite = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(get_pegs()[i][j].getPegState() == 0){
                    amountWhite++;
                } else if(get_pegs()[i][j].getPegState() == 1){
                    amountBlack++;
                }
            }
        }
        //check if board has empty space
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(get_pegs()[i][j].getPegState() == 2){
                    return false;
                }
            }
        }
        if(side == 0 && amountWhite < amountBlack){
            return true;
        } else if (side == 1 && amountBlack < amountWhite){
            return true;
        } else {
            return false;
        }
    }
}
