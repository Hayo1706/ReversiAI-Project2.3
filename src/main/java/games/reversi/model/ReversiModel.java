package games.reversi.model;


import view.BoardSetup;

public class ReversiModel {
    private int turn = 1;
    private BoardSetup view;

    private ReversiPeg[][] pegs=new ReversiPeg[8][8];
    public void fill_pegs(){
        for(int i = 0;i < 8;i++){
            for(int o = 0;o < 8;o++) {
                ReversiPeg peg = new ReversiPeg(i,o);
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
    public ReversiModel(view.BoardSetup view){

        fill_pegs();
        this.view=view;
    }

    public ReversiPeg[][] get_pegs(){
        return pegs;
    }
}
