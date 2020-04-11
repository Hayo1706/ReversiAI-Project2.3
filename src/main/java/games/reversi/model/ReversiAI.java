package games.reversi.model;

import ai.AI;
import model.Peg;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Math.*;

public class ReversiAI implements ai.AI, Serializable {
    ReversiModel model;
    private int[][] boardAI = new int[8][8];
    int side = 1;
    int depth = 6;

    public void setSide(int side) {
        this.side = side;
    }

    public void setModel(ReversiModel model){
        this.model = model;
    }

    public int chooseMove() {
        pegs_to_board(model.get_pegs());
        int temp = evaluateBoardPegsEmpty(boardAI);
        if(temp <= 9)
            depth = temp;
        AIMove o = negaMax(new AIMove(boardAI),depth,side, temp <= depth);
        return o.getPos();

    }


    public void pegs_to_board(Peg[][] pegs) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                boardAI[row][col] = pegs[row][col].pegState;
            }
        }
    }
    public AIMove negaMax(AIMove position,int depth, int color,boolean lastPegs){
        if(depth == 0){
            return position;
        }
        AIMove maxAIMove = null;
        int maxValue = -1000;
        int eval;
        int evalOther;
        AIMove temp;
        for (AIMove move:getAllValidMoves(color,position.getBoard())) {

            temp =negaMax(move, depth-1 , abs(color-1),lastPegs);
            eval = evaluateBoardPegs(lastPegs, temp ,color);
            evalOther = evaluateBoardPegs(lastPegs, temp ,abs(color-1));

            if(maxValue <= eval-evalOther) {
                maxValue = eval;
                maxAIMove = move;
            }
        }
        if(maxAIMove == null)
            return negaMax(position,depth-1,abs(color-1),lastPegs);
        return maxAIMove;
    }


    public int evaluateBoardPegs(boolean countAmountOfPegs,AIMove eval,int whichSide){
        int[][] toCount = eval.getBoard();
        if(countAmountOfPegs){

            int amWhite = 0;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if(toCount[row][col] == abs(whichSide-1))
                        amWhite++;
                }
            }
            return amWhite;
        }
        else{
            int[][] values = {
                    {99,-25,24,6,6,24,-25,99},
                    {-25,-50,-14,-3,-3,-14,-50,-25},
                    {24,-14,14,4,4,14,-14,24},
                    {6,-3,4,0,0,4,-3,6},
                    {6,-3,4,0,0,4,-3,6},
                    {24,-14,14,4,4,14,-14,24},
                    {-25,-50,-14,-3,-3,-14,-50,-25},
                    {99,-25,24,6,6,24,-25,99}
            };
            int amPointsWhite = 0;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if(toCount[row][col] == abs(whichSide-1) && boardAI[row][col] == 2){
                        amPointsWhite += values[row][col];
                    }
                }
            }
            return amPointsWhite;
        }
    }

    public int evaluateBoardPegsEmpty(int[][] toCount){
        int amEmpty = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                if(toCount[row][col] == 2)
                    amEmpty++;
            }
        }
        return amEmpty;
    }
    public ArrayList<AIMove> getAllValidMoves(int side, int[][] boardCount) {
        ArrayList<AIMove> returnArray = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                int[][] toCount = new int[8][];

                for(int i = 0; i < 8; i++)
                {
                    int[] aMatrix = boardCount[i];
                    toCount[i] = new int[8];
                    System.arraycopy(aMatrix, 0, toCount[i], 0, 8);
                }

                if(toCount[row][col] == 2) {
                    boolean hasChanged = false;

                    if (checkHorizontalL(row, col, side, toCount)) {
                        setHorizontalL(row, col, side, toCount);
                        hasChanged = true;
                    }
                    if (checkHorizontalR(row, col, side, toCount)) {
                        setHorizontalR(row, col, side, toCount);
                        hasChanged = true;
                    }
                    if (checkVerticalL(row, col, side, toCount)) {
                        setVerticalL(row, col, side, toCount);
                        hasChanged = true;
                    }
                    if (checkVerticalR(row, col, side, toCount)) {
                        setVerticalR(row, col, side, toCount);
                        hasChanged = true;
                    }
                    if (checkDiagonalDR(row, col, side, toCount)) {
                        setDiagonalDR(row, col, side, toCount);
                        hasChanged = true;
                    }
                    if (checkDiagonalUL(row, col, side, toCount)) {
                        setDiagonalUL(row, col, side, toCount);
                        hasChanged = true;
                    }
                    if (checkDiagonalUR(row, col, side, toCount)) {
                        setDiagonalUR(row, col, side, toCount);
                        hasChanged = true;
                    }
                    if (checkDiagonalDL(row, col, side, toCount)) {
                        setDiagonalDL(row, col, side, toCount);
                        hasChanged = true;
                    }
                    if (hasChanged) {
                        returnArray.add(new AIMove(toCount, row * 8 + col));
                    }
                }
            }
        }
        return returnArray;
    }

    private boolean checkHorizontalL(int posX, int posZ,int side,int[][] toCount) {
        if (posZ - 1 >= 0 && toCount[posX][posZ - 1] == side) {
            for (int col = posZ - 2; col >= 0; col--) {
                if (toCount[posX][col] == abs(side - 1)) {
                    return true;
                } else if (toCount[posX][col] == 2) {
                    break;
                }
            }
        }
        return false;
    }
    private void setHorizontalL(int posX, int posZ, int side, int[][] toCount) {
        toCount[posX][posZ] = abs(side-1);
        for(int col = posZ - 1; col >= 0; col--) {
            if (toCount[posX][col] == side) {
                toCount[posX][col] = abs(side - 1);
            } else{
                return;
            }
        }
    }
    private boolean checkHorizontalR(int posX, int posZ,int side,int[][] toCount) {
        if (posZ + 1 < 8 && toCount[posX][posZ + 1] == side) {
            for (int col = posZ + 2; col < 8; col++) {
                if (toCount[posX][col] == abs(side - 1)) {
                    return true;
                } else if (toCount[posX][col] == 2) {
                    break;
                }
            }
        }
        return false;
    }
    private void setHorizontalR(int posX, int posZ, int side, int[][] toCount) {
        toCount[posX][posZ] = abs(side-1);
        for (int col = posZ + 1; col < 8; col++) {
            if (toCount[posX][col] == side) {
                toCount[posX][col] = abs(side - 1);
            } else{
                return;
            }
        }
    }

    private boolean checkVerticalL(int posX, int posZ,int side,int[][] toCount) {
        if (posX - 1 >= 0 && toCount[posX - 1][posZ] == side) {
            for (int row = posX - 2; row >= -0; row--) {
                if (toCount[row][posZ] == abs(side - 1)) {
                    return true;
                } else if (toCount[row][posZ] == 2) {
                    break;
                }
            }
        }
        return false;
    }
    private void setVerticalL(int posX, int posZ, int side, int[][] toCount) {
        toCount[posX][posZ] = abs(side-1);
        for (int row = posX - 1; row >= -0; row--) {
            if (toCount[row][posZ] == side)
                toCount[row][posZ] = abs(side - 1);
            else
                return;
        }
    }
    private boolean checkVerticalR(int posX, int posZ,int side,int[][] toCount) {
        if (posX + 1 < 8 && toCount[posX + 1][posZ] == side) {
            for (int row = posX + 2; row < 8; row++) {
                if (toCount[row][posZ] == abs(side - 1)) {
                    return true;
                } else if (toCount[row][posZ] == 2) {
                    break;
                }
            }
        }
        return false;
    }
    private void setVerticalR(int posX, int posZ, int side, int[][] toCount) {
        toCount[posX][posZ] = abs(side-1);
        for (int row = posX + 1; row < 8; row++) {
            if (toCount[row][posZ] == side)
                toCount[row][posZ] = abs(side - 1);
            else
                return;
        }
    }

    private boolean checkDiagonalDR(int posX, int posZ,int side,int[][] toCount) {
        if (posX + 1 < 8 && posZ + 1 < 8 && toCount[posX + 1][posZ + 1] == side) {
            for (int i = posX + 2, o = posZ + 2; i < 8 && o < 8; i++, o++) {
                if (toCount[i][o] == abs(side - 1)) {
                    return true;
                } else if (toCount[i][o] == 2) {
                    break;
                }
            }
        }
        return false;
    }
    private void setDiagonalDR(int posX, int posZ, int side, int[][] toCount) {
        toCount[posX][posZ] = abs(side-1);
        for (int i = posX + 1, o = posZ + 1; i < 8 && o < 8; i++, o++) {
            if (toCount[i][o] == side)
                toCount[i][o] = abs(side - 1);
            else
                return;
        }
    }
    private boolean checkDiagonalUL(int posX, int posZ,int side,int[][] toCount) {
        if (posX - 1 >= 0 && posZ - 1 >= 0 && toCount[posX - 1][posZ - 1] == side) {
            for (int i = posX - 2, o = posZ - 2; i >= 0 && o >= 0; i--, o--) {
                if (toCount[i][o] == abs(side - 1)) {
                    return true;
                } else if (toCount[i][o] == 2) {
                    break;
                }
            }
        }
        return false;
    }
    private void setDiagonalUL(int posX, int posZ, int side, int[][] toCount) {
        toCount[posX][posZ] = abs(side-1);
        for (int i = posX - 1, o = posZ - 1; i >= 0 && o >= 0; i--, o--) {
            if (toCount[i][o] == side) {
                toCount[i][o] = abs(side - 1);
            } else {
                return;
            }
        }
    }
    private boolean checkDiagonalUR(int posX, int posZ,int side,int[][] toCount) {
        if (posX + 1 < 8 && posZ - 1 >= 0 && toCount[posX + 1][posZ - 1] == side) {
            for (int i = posX + 2, o = posZ - 2; i < 8 && o >= 0; i++, o--) {
                if (toCount[i][o] == abs(side - 1)) {
                    return true;
                } else if (toCount[i][o] == 2) {
                    break;
                }
            }
        }
        return false;
    }
    private void setDiagonalUR(int posX, int posZ, int side, int[][] toCount) {
        toCount[posX][posZ] = abs(side-1);
        for (int i = posX + 1, o = posZ - 1; i < 8 && o >= 0; i++, o--) {
            if (toCount[i][o] == side)
                toCount[i][o] = abs(side - 1);
            else
                return;
        }
    }
    private boolean checkDiagonalDL(int posX,int posZ,int side,int[][] toCount){
        if(posX-1 >= 0 && posZ+1 < 8 && toCount[posX - 1][posZ + 1] == side) {
            for (int i = posX-2,o = posZ+2; i >= 0 && o < 8; i--,o++) {
                if (toCount[i][o] == abs(side-1)) {
                    return true;
                } else if (toCount[i][o] == 2) {
                    break;
                }
            }
        }
        return false;
    }
    private void setDiagonalDL(int posX, int posZ, int side, int[][] toCount){
        toCount[posX][posZ] = abs(side-1);
        for (int i = posX-1,o = posZ+1; i >= 0 && o < 8; i--,o++) {
            if (toCount[i][o] == side)
                toCount[i][o] = abs(side - 1);
            else
                return;
        }
    }
}
