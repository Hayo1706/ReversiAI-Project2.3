package games.reversi.controller;

import games.reversi.model.ReversiModel;
import games.reversi.view.Animation;
import model.Model;
import model.Peg;
import view.GameClient;


public class ReversiController implements controller.Controller {
   Model model;


    public ReversiController(Model model) {
        this.model = model;
        setupBoard();
        Animation animation = new Animation(model.get_pegs());
        animation.start();
        model.switch_gamemode(GameClient.gameMode);
        model.setValidMoves();
    }

    public void setupBoard() {
        model.get_pegs()[3][3].setTile(1);
        model.get_pegs()[4][4].setTile(1);
        model.get_pegs()[3][4].setTile(0);
        model.get_pegs()[4][3].setTile(0);

    }


    public void nextTurn(Peg peg) {

        if (model.is_mode(Model.HUMAN_VS_AI)) {



        } else if (model.is_mode(Model.HUMAN_VS_HUMAN)) {
            model.playMove(peg.getXPosition() * 8 + peg.getZPosition());


        } else if (model.is_mode(Model.HUMAN_VS_SERVER)) {

        }

        if (gameOver()) {
            disable_pegs();
        }
        model.setValidMoves();
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

    public Peg[][] get_pegs() {
        return model.get_pegs();
    }

    public boolean gameOver() {
        return model.gameOver();
    }

    public void disable_pegs() {
        model.disable_pegs();
    }

}
