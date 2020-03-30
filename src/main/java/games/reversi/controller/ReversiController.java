package games.reversi.controller;

import games.reversi.view.Animation;
import model.Model;
import model.Peg;
import view.GameClient;


public class ReversiController implements controller.Controller {
    Model model;

    public ReversiController(Model model) {
        this.model = model;
        startupAnimation();
        model.switch_gamemode(GameClient.gameMode);



    }

    public void startupAnimation() {
        Animation animation = new Animation(model.get_pegs());
        animation.start();
        setupBoard();
    }


    public void setupBoard() {
        model.get_pegs()[3][3].setTile(0);
        model.get_pegs()[4][4].setTile(0);
        model.get_pegs()[3][4].setTile(1);
        model.get_pegs()[4][3].setTile(1);

    }


    public void nextTurn(Peg peg) {
        if (model.is_mode(Model.HUMAN_VS_AI)) {
            if(isValidMove(peg)) {
                model.playMove(peg.getXPosition() * 8 + peg.getZPosition());
            }

        } else if (model.is_mode(Model.HUMAN_VS_HUMAN)) {

        } else if (model.is_mode(Model.HUMAN_VS_SERVER)) {


        }
        //game is idle and cannot reach this whole method
        else {

        }


        if (gameOver()) {
            disable_pegs();
        }

//        printBoardToConsole();

        //row 0 col 1 System.out.println(model.get_pegs()[row][col].getPegState());
    }


    /**
     * @author Maurice Wijker.
     * @printBoardToConsole Prints current layout to console
     */
    public void printBoardToConsole(){
        String row = "";

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                row += model.get_pegs()[i][j].getPegState() + " ";
            }
            System.out.println(row);
            row = "";
        }
        System.out.println("\n");
    }


    /**
     *
     *
     * @return Is legal move to make
     * @author Maurice Wijker
     */
    public boolean isValidMove(Peg peg) {
        int z = peg.getZPosition(); // -> horizontal
        int x = peg.getXPosition(); // -> vertical
        int side = model.getSide();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; i <= 1; i++) {
                if(side == 0 && model.get_pegs()[z+i][x+j].getPegState() == 0 || model.get_pegs()[z+i][x+j].getPegState() == 0){
                    System.out.println("hoi");

                }
            }
        }


//
//        //scan horizontal to the right
//        for (int j = z; j < 8; j++) {
//            if (peg.getPegState() == model.get_pegs()[j][z].getPegState()) {
//                return true;
//            }
//        }
//
//        //scan vertically down
//        for (int i = x; i < 8; i++) {
//            if (peg.getPegState() == model.get_pegs()[i][x].getPegState()) {
//                return true;
//            }
//        }
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
