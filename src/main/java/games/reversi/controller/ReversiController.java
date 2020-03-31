package games.reversi.controller;

import games.reversi.view.Animation;
import model.Model;
import model.Peg;
import view.GameClient;

import static java.lang.Math.abs;


public class ReversiController implements controller.Controller {
    Model model;

    public ReversiController(Model model) {
        this.model = model;
        setupBoard();
        startupAnimation();
        model.switch_gamemode(GameClient.gameMode);

        checkMovesCloseby();


    }

    public void startupAnimation() {
        Animation animation = new Animation(model.get_pegs());
        animation.start();
    }


    public void setupBoard() {
        model.get_pegs()[3][3].setTile(0);
        model.get_pegs()[4][4].setTile(0);
        model.get_pegs()[3][4].setTile(1);
        model.get_pegs()[4][3].setTile(1);

    }


    public void nextTurn(Peg peg) {

        if (model.is_mode(Model.HUMAN_VS_AI)) {


        } else if (model.is_mode(Model.HUMAN_VS_HUMAN)) {
//            if(calcValidMove(peg)){

                model.playMove(peg.getXPosition() * 8 + peg.getZPosition());
//            }
            printBoardToConsole();

        } else if (model.is_mode(Model.HUMAN_VS_SERVER)) {

        }

        if (gameOver()) {
            disable_pegs();
        }
        checkMovesCloseby();
    }


    /**
     * @author Maurice Wijker.
     * @printBoardToConsole Prints current layout to console
     */
    public void printBoardToConsole() {
        String row = "";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                row += model.get_pegs()[i][j].getPegState() + " ";
            }
            System.out.println(row);
            row = "";
        }
        System.out.println("\n");
    }


    /**
     * @return Is legal move to make
     * @author Maurice Wijker
     */
//    public boolean calcValidMove(Peg peg) {
//
//        int side = model.getSide(); // who's turn
//
//        for(int row = 0; row < 8; row++){
//            for(int col = 0; col < 8; col++){
//                if(model.get_pegs()[row][col].getPegState() == 2){
//                    var nw = isValidMove(side, -1, -1, row, col);
//                    var nn = isValidMove(side, -1, 0, row, col);
//                    var ne = isValidMove(side, -1, 1, row, col);
//
//                    var ww = isValidMove(side, 0, -1, row, col);
//                    var ee = isValidMove(side, 0, 1, row, col);
//
//                    var sw = isValidMove(side, 1, -1, row, col);
//                    var sn = isValidMove(side, 1, 0, row, col);
//                    var se = isValidMove(side, 1, 1, row, col);
//
//                    if(nw || nn || ne || ww || ee || sw || sn || se){
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    /**
     * @author Maurice Wijker
     * @return is legal to make move or not
     * @side who's turn
     * @dr delta row
     * @dc delta colomn
     * @r init row
     * @c init column
     */
//    public boolean isValidMove(int side, int dr, int dc, int r, int  c){
//
//        int other = -1;
//
//        if(side == 0){
//            other = 1;
//        }
//        else if(side == 1){
//            other = 0;
//        }
//        else {
//            System.out.println("Something went wrong with side ..." + side);
//        }
//
//        if((r + dr < 0) || (r + dr > 7 )){
//            return false;
//        }
//        if((c + dc < 0) || (c + dc > 7 )){
//            return false;
//        }
//        if(model.get_pegs()[r+dr][c+dc].getPegState() != other){
//            return false;
//        }
//        if((r+dr+dr <0) || (r+dr+dr > 7)){
//            return false;
//        }
//        if((c+dc+dc < 0) || (c+dc+dc > 7)){
//            return false;
//        }
//        return checkLineMatch(side, dr, dc, r+dr+dr, c+dc+dc);
//    }
//
//    private boolean checkLineMatch(int side, int dr, int dc, int r, int c){
//        if(model.get_pegs()[r][c].getPegState() == side){
//            return true;
//        }
//        if((r + dr < 0) || (r + dr > 7 )){
//            return false;
//        }
//        if((c + dc < 0) || (c + dc > 7 )){
//            return false;
//        }
//        return checkLineMatch(side, dr, dc, r + dr, c + dc);
//    }





    //player1 == black
    //player1 == 0 = side
    //black == 1 = pegstate

    //player2 == white
    //player2 == 1 = side
    //white == 0 = pegstate


    /**
     * @author hayo & Maurice Wijker
     * row 4 col 2 X && row 3 col 5 X
     */
    public void checkMovesCloseby() {

        int side = model.getSide();
        System.out.println(side);
        model.disable_pegs();
        Peg[][] pegs = model.get_pegs();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((pegs[row][col].getPegState() == 0 && side == 0) || (side == 1 && pegs[row][col].getPegState() == 1)) {
                    for (int q= -1; q <= 1 && q+row < 8; q++) {
                        for (int w= -1; w <= 1 && w+col < 8; w++) {
                            if((pegs[abs(q + row)][abs(w + col)].getPegState() == 2) && (checkHorizontal(abs(q + row), abs(w + col)) || checkVertical(abs(q + row), abs(w + col)) || checkDiagonal(abs(q + row), abs(w + col)))) {
                                pegs[abs(q + row)][abs(w + col)].setDisable(false);
                                pegs[abs(q + row)][abs(w + col)].setStyle("-fx-background-color: #3c8e55");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @author Maurice Wijker
     * @param posZ the initial horizontal position
     * @param posX the initial vertical position
     * @return is move legal to make horizontal?
     */


    private boolean checkHorizontal(int posX,int posZ){
        int side = model.getSide();

        int other = -1;

        if(side == 0){
            other = 1;
        }
        else if(side == 1){
            other = 0;
        }
        if(posZ-1 >= 0 && model.get_pegs()[posX][posZ-1].getPegState() == side) {
                for (int col = posZ - 2; col >= 0; col--) {
                    if (model.get_pegs()[posX][col].getPegState() == other) {
                        return true;
                    } else if (model.get_pegs()[posX][col].getPegState() == 2) {
                        break;
                    }
                }
        }
        if(posZ+1 < 8 && model.get_pegs()[posX][posZ+1].getPegState() == side) {
                for (int col = posZ + 2; col < 8; col++) {
                    if (model.get_pegs()[posX][col].getPegState() == other) {
                        return true;
                    } else if (model.get_pegs()[posX][col].getPegState() == 2) {
                        break;
                    }
                }
        }
        return false;
    }


    /**
     * @author Maurice Wijker
     * @param posZ the initial horizontal position
     * @param posX the initial vertical position
     * @return is move legal to make vertically?
     */
    private boolean checkVertical(int posX,int posZ){
        int side = model.getSide();

        int other = -1;

        if(side == 0)
            other = 1;
        else
            other = 0;

        if(posX-1 >= 0 && model.get_pegs()[posX -1][posZ].getPegState() == side) {

            for (int row = posX - 2; row >= -0; row--) {
                if (model.get_pegs()[row][posZ].getPegState() == other) {
                    return true;
                } else if (model.get_pegs()[row][posZ].getPegState() == 2) {
                    break;
                }
            }
        }
        if(posX+1 < 8 && model.get_pegs()[posX +1][posZ].getPegState() == side) {
            for (int row = posX + 2; row < 8; row++) {
                if (model.get_pegs()[row][posZ].getPegState() == other) {
                    return true;
                } else if (model.get_pegs()[row][posZ].getPegState() == 2) {
                    break;
                }
            }
        }
        return false;
    }


    /**
     * @author Maurice Wijker
     * @param posZ the initial horizontal position
     * @param posX the initial vertical position
     * @return is move legal to make diagonaly?
     */

    private boolean checkDiagonal(int posX,int posZ){
        int side = model.getSide();

        int other = -1;

        if(side == 0){
            other = 1;
        }
        else if(side == 1){
            other = 0;
        }

        if(posX+1 < 8 && posZ+1 < 8 &&  model.get_pegs()[posX + 1][posZ + 1].getPegState() == side) {
            for (int i = posX + 2; posX + i < 8 && posZ + i < 8; i++) {
                if (model.get_pegs()[posX + i][posZ + i].getPegState() == other) {
                    return true;
                } else if (model.get_pegs()[posX + i][posZ + i].getPegState() == 2) {
                    break;
                }
            }
        }

        if(posX-1 >= 0 && posZ-1 >= 0 && model.get_pegs()[posX - 1][posZ - 1].getPegState() == side) {
            for (int i = posX -2; posX - i >= 0 && posZ-i >=0; i--) {
                if (model.get_pegs()[posX-i][posZ-i].getPegState() == other) {
                    return true;
                } else if (model.get_pegs()[posX-i][posZ-i].getPegState() == 2) {
                    break;
                }
            }
        }
       return false;
    }





    public Peg[][] get_pegs() {
        return model.get_pegs();
    }

    public boolean gameOver() {
        return model.gameOver();
    }

    public void disable_pegs() {
        model.disable_pegs();
    }

    public int getBest() {
        return model.calculateBest();
    }
}
