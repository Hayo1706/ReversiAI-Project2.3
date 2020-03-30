package games.reversi.controller;

import games.reversi.view.Animation;
import javafx.application.Platform;
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
        setValidMoves();


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
            model.playMove(peg.getXPosition() * 8 + peg.getZPosition());

        } else if (model.is_mode(Model.HUMAN_VS_HUMAN)) {

        } else if (model.is_mode(Model.HUMAN_VS_SERVER)) {

        }

        if (gameOver()) {
            disable_pegs();
        }
        setValidMoves();
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
    public boolean isValidMove(Peg peg) {
        int z = peg.getZPosition(); // -> horizontal
        int x = peg.getXPosition(); // -> vertical
        int side = model.getSide();


//
//
//
//        //scan vertically down
//        for (int i = x; i < 8; i++) {
//            if (peg.getPegState() == model.get_pegs()[i][x].getPegState()) {
//                return true;
//            }

        return false;

    }

    // player1 == black
    //black == 1
    //player1 = 0

    public void setValidMoves() {
        int side = model.getSide();
        System.out.println(side);
        model.disable_pegs();
        Peg[][] pegs = model.get_pegs();
            for (int i = 0; i < 8; i++) {
                for (int o = 0; o < 8; o++) {

                    if ((pegs[i][o].getPegState() == 0 && side == 0) || (side == 1 && pegs[i][o].getPegState() == 1)) {
                        for (int q= -1; q <= 1 && q+i < 8; q++) {
                            for (int w= -1; w <= 1 && w+o < 8; w++) {
                                if((pegs[abs(q + i)][abs(w + o)].getPegState() == 2)) {
                                    pegs[abs(q + i)][abs(w + o)].setDisable(false);
                                    pegs[abs(q + i)][abs(w + o)].setStyle("-fx-background-color: #3c8e55");

                                }
                            }
                        }
                    }
                }
            }
    }
    private boolean checkHorizontal(int posZ,int posX){
        return false;
    }

    private boolean checkVertical(int posX,int posZ){
        return false;
    }
    private boolean checkDiagonal(int posX,int posZ){
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
