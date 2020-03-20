package reversi.model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import reversi.view.Peg;

import java.util.ArrayList;

public class Model  {
    private Peg[][] pegs=new Peg[8][8];
    public void fill_pegs(){
        for(int i = 0;i < 8;i++){
            for(int o = 0;o < 8;o++) {
                Peg peg = new Peg(i,o);
                peg.setMinHeight(100);
                peg.setMinWidth(100);
                pegs[i][o]=peg;

            }
        }
    }
    public Model(){
        fill_pegs();


    }
    public void change_peg(Peg peg){
        Image black = new Image("reversi/view/style/black.png");
        Image white= new Image("reversi/view/style/white.png");
        ImageView imageView = new ImageView(black);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
            //temporary set it black
            peg.setGraphic(imageView);




    }
    public Peg[][] get_pegs(){
        return pegs;
    }
}
