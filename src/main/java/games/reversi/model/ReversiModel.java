package games.reversi.model;


import model.Model;
import view.BoardSetup;

public class ReversiModel extends Model {
    private int turn = 1;

    public int nextTurn(){
        int t = turn;
        turn += 1;
        return t;
    }
    public ReversiModel(int sizeX,int sizeY,BoardSetup view){
        super(sizeX,sizeY,view);

    }
}
