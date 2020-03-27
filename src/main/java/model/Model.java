package model;

import games.tictactoe.model.TicTactToePeg;
import view.BoardSetup;

public abstract class Model {
    private int sizeX;
    private int sizeY;
    protected BoardSetup view;
    protected Peg[][] pegs = new Peg[3][3];

    public Model(int sizeX,int sizeY,BoardSetup view){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.view = view;
        fill_pegs();
    }

    public void fill_pegs() {
        for (int i = 0; i < sizeX; i++) {
            for (int o = 0; o < sizeY; o++) {
                TicTactToePeg peg = new TicTactToePeg(i, o);
                peg.setMinSize(100, 100);
                pegs[i][o] = peg;


            }
        }
    }
    public  static final int HUMAN_VS_HUMAN   = 0;
    public  static final int HUMAN_VS_AI         = 1;
    public  static final int AI_VS_SERVER      = 2;
    public  static final int HUMAN_VS_SERVER         = 3;
    //state vor mode
    public  static final int IDLE  = -1;
    public int mode=IDLE;


    //return true if there will be no match
    public boolean idle(){
        return mode==IDLE;
    }
    public Peg[][] get_pegs(){
        return pegs;
    }
    //return true if human plays vs (local) ai
    public boolean human_vs_ai(){
        return mode==HUMAN_VS_AI;
    }
    //return true if human plays vs human
    public boolean human_vs_human(){
        return mode==HUMAN_VS_HUMAN;
    }
    //return true if ai plays vs server
    public boolean ai_vs_server(){
        return mode==AI_VS_SERVER;
    }
    //return true if human plays vs server
    public boolean human_vs_server(){
        return mode==HUMAN_VS_SERVER;
    }

}
