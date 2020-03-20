package reversi.model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Model  {
    private int turn = 1;
    private Peg[][] pegs=new Peg[8][8];
    public void fill_pegs(){
        for(int i = 0;i < 8;i++){
            for(int o = 0;o < 8;o++) {
                Peg peg = new Peg(i,o);
                peg.setMinSize(100,100);
                pegs[i][o]=peg;

            }
        }
    }
    public int nextTurn(){
        int t = turn;
        turn += 1;
        return t;
    }
    public Model(){
        fill_pegs();
    }

    public Peg[][] get_pegs(){
        return pegs;
    }
}
