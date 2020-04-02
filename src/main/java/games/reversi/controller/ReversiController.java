package games.reversi.controller;

import games.reversi.model.ReversiModel;
import games.reversi.view.Animation;
import model.Model;
import model.Peg;

import static java.lang.StrictMath.abs;


public class ReversiController implements controller.Controller {
   ReversiModel model;


    public ReversiController(ReversiModel model) {
        this.model = model;
        setupBoard();
        Animation animation = new Animation(model.get_pegs());
        animation.start();
        model.switch_gamemode(model.getMode());
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


        model.setValidMoves();

        //If no available moves are present, other player gets the turn.
        if(model.getValidMoves().isEmpty()){
            model.setSide(abs(model.getSide()-1));
            model.setValidMoves();
            if (model.getSide() == 0)
                model.setText("White has no moves, Black's turn!");
            else
                model.setText("Black has no moves, White's turn!");
        }
        if (model.gameOver()) {
            disable_pegs();
        }
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

    public void disable_pegs() {
        model.disable_pegs();
    }

}
