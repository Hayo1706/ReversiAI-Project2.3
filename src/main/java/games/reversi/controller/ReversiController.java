package games.reversi.controller;

import com.sun.webkit.Timer;
import communication.StrategicGameClient;
import controller.Controller;
import games.reversi.model.ReversiModel;
import games.reversi.view.Animation;
import model.Model;
import model.Peg;


public class ReversiController extends Controller {



    public ReversiController(ReversiModel model) {
        super(model);

        setupBoard();
        Animation animation = new Animation(model.get_pegs());
        model.switch_gamemode(model.getMode());
        if(Model.mode!= Model.AI_VS_SERVER || Model.mode!=Model.HUMAN_VS_SERVER) {
            //animation.start();
        }
        model.addToValidMoves();
        model.setValidMoves();
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
            ((ReversiModel)model).addToValidMoves();
            if (!model.gameOver()) {
                if(checkIfValidMoves())
                    ((ReversiModel)model).playMove(model.calculateBest());
                    model.gameOver();
            }




        } else if (model.is_mode(Model.HUMAN_VS_HUMAN)) {
            model.playMove(peg.getXPosition() * 8 + peg.getZPosition());
            if (model.gameOver()) {
                disable_pegs();
            }


        } else if (model.is_mode(Model.HUMAN_VS_SERVER)) {

                if(((ReversiModel)model ).getYourTurn()) {
                    int move=peg.getXPosition()*8+peg.getZPosition();
                    StrategicGameClient.getInstance().doMove(move);
                    model.playMove(move);
                    ((ReversiModel)model ).setYourTurn(false);
                }

        }

        ((ReversiModel)model).addToValidMoves();
        if(checkIfValidMoves())
            ((ReversiModel)model).setValidMoves();


    }

    public boolean checkIfValidMoves(){
        return ((ReversiModel)model).checkIfValidMoves();
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







}
