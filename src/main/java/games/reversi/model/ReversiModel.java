package games.reversi.model;
import ai.AI;
import javafx.application.Platform;
import model.Model;
import model.Peg;
import view.View;



public class ReversiModel extends Model {

    //Model
    public ReversiModel(int boardsize, View view, ai.AI AI) {
        super(boardsize,view,AI);

    }
    public void fill_pegs() {
        for (int i = 0; i < boardsize; i++) {
            for (int o = 0; o < boardsize; o++) {
                Peg peg = new ReversiPeg(i, o);
                peg.setMinSize(100, 100);
                pegs[i][o] = peg;
                //i = row
                //o=column

            }
        }
    }



    public void play_ai_vs_server(){



    }





    public int calculateBest(){
        // TODO: 28/03/2020
    return 0;
    }




    // Returns whether 'side' has won in this position
    public boolean isAWin( int side )
    {
        // TODO: 28/03/2020
        return false;
    }


}
