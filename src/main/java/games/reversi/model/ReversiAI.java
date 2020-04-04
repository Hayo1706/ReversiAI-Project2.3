package games.reversi.model;

import model.Peg;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class ReversiAI implements ai.AI, Serializable {
    ReversiModel model;
    Random random = new Random();
    private int[][] board = new int[8][8];
    private int[][] tempBoard = new int[8][8];
    private String[] boardString = new String[64];
    int[][] values = {{99,-8,8,6,6,8,-8,99},{-8,-24,-4,-3,-3,-4,-24,-8},{8,-4,7,4,4,7,-4,8},{6,-3,4,0,0,4,-3,6},{6,-3,4,0,0,4,-3,6},{8,-4,7,4,4,7,-4,8},{-8,-24,-4,-3,-3,-4,-24,-8},{99,-8,8,6,6,8,-8,99}};
    public void setModel(ReversiModel model){
        this.model = model;
    }

    public int chooseMove() {
        pegs_to_board(model.get_pegs());
        tempBoard = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);
        int temp = -100;
        int move = 0;
        for (Integer var:model.getValidMoves()) {
            if(values[var/8][var%8] > temp){
                temp = values[var/8][var%8];
                move = var;

            }
            System.out.println(temp);
            System.out.println(var);
            System.out.println("-");

        }
        System.out.println(" ");
        return move;

    }


    public void pegs_to_board(Peg[][] pegs) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                board[row][col] = pegs[row][col].pegState;
                boardString[row*8+col] = String.valueOf(board[row][col]);
            }
        }
        String hoi = String.join("",boardString);

    }
    public String[] rotate90()
    {
        String[] temp = new String[64];
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                temp[j*8+7-i] = boardString[i*8+j];
            }
        }
        return temp;
    }

    void printArray(String[] arr){
        for (String var:arr) {
            System.out.print(var);
        }
    }

    static void printMatrix(int arr[][])
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
                System.out.print( arr[i][j] + " ");
            System.out.println();
        }
    }
}
