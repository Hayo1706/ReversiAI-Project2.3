package games.reversi.model;

import model.Peg;

import java.io.*;
import java.util.ArrayList;

import static java.lang.Math.*;

public class ReversiAI implements ai.AI, Serializable {
    ReversiModel model;
    private int[][] boardAI = new int[8][8];
    int side = 1;
    int minMaxAmount = 6;

    public void setSide(int side) {
        this.side = side;
    }

    public void setModel(ReversiModel model){
        this.model = model;
    }

    public int chooseMove() {
        pegs_to_board(model.get_pegs());
        if(evaluateBoardPegsEmpty(boardAI) <= 10){
            minMaxAmount = 10;
        }
        AIMove o = miniMax(new AIMove(boardAI),minMaxAmount,true, evaluateBoardPegsEmpty(boardAI) <= minMaxAmount,-1000,1000);

        return o.getPos();

    }


    public void pegs_to_board(Peg[][] pegs) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                boardAI[row][col] = pegs[row][col].pegState;
            }
        }
    }
    public AIMove miniMax(AIMove position,int depth,boolean maximize,boolean finalMoves,int alpha, int beta){
        if(depth == 0)
            return position;
        if(maximize)
            return miniMaxMaximumTrue(position,depth,finalMoves, alpha,  beta);
        else
            return miniMaxMaximumFalse(position,depth,finalMoves, alpha,  beta);


    }
    //ai
    public AIMove miniMaxMaximumTrue(AIMove position,int depth,boolean finalMoves,int alpha, int beta){
        AIMove maxEval = null;
        int maxValueWhite = -1000;
        int maxValueBlack = 1000;

        int evaluationSide;
        int evaluationNotSide;

        for (AIMove var: getAllValidMoves(side,position.getBoard())) {
            AIMove eval = miniMax(var,depth-1,false,finalMoves, alpha, beta);

            evaluationSide = finalMoves ? evaluateBoardPegs(true,eval,side):evaluateBoardPegs(false,eval,side);
            evaluationNotSide = finalMoves ? evaluateBoardPegs(true,eval,abs(side-1)): evaluateBoardPegs(false,eval,abs(side-1));

            if(evaluationSide >= maxValueWhite && evaluationNotSide <= maxValueBlack){
                maxEval = var;
                maxValueWhite = evaluationSide;
                maxValueBlack = evaluationNotSide;
            }
//            alpha = max(alpha,evaluationSide);
//            if(beta >= alpha)
//                break;
        }
        if(maxEval == null)
            return miniMax(position,depth-1,false,finalMoves, alpha,  beta);
        return maxEval;
    }
    //opponent
    public AIMove miniMaxMaximumFalse(AIMove position,int depth,boolean finalMoves,int alpha, int beta){
        AIMove minEval = null;

        int maxValueWhite = -1000;
        int maxValueBlack = 1000;

        int evaluationSide;
        int evaluationNotSide;

        for (AIMove var: getAllValidMoves(abs(side-1),position.getBoard())) {
            AIMove eval = miniMax(var, depth - 1, true, finalMoves, alpha,  beta);

            evaluationSide = finalMoves ? evaluateBoardPegs(true,eval,side):evaluateBoardPegs(false,eval,side) ;
            evaluationNotSide = finalMoves ? evaluateBoardPegs(true,eval,abs(side-1)): evaluateBoardPegs(false,eval,abs(side-1));

            if(evaluationNotSide >= maxValueBlack && evaluationSide <= maxValueWhite){
                minEval = var;
                maxValueWhite = evaluationSide;
                maxValueBlack = evaluationNotSide;
            }
//            beta = max(beta,evaluationNotSide);
//            if(beta <= alpha)
//                break;
        }
        if(minEval == null)
            return miniMax(position, depth - 1, true, finalMoves, alpha,  beta);
        return minEval;
    }

    public int evaluateBoardPegs(boolean countAmountOfPegs,AIMove eval, int whichSide){
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
                    {99, -8, 8, 6, 6, 8, -8,99},
                    {-8,-24,-4,-3,-3,-4,-24,-8},
                    { 8, -4, 100, 4, 4, 100, -4, 8},
                    { 6, -3, 4, 0, 0, 4, -3, 6},
                    { 6, -3, 4, 0, 0, 4, -3, 6},
                    { 8, -4, 100, 4, 4, 100, -4, 8},
                    {-8,-24,-4,-3,-3,-4,-24,-8},
                    {99, -8, 8, 6, 6, 8, -8,99}
            };

//            if(toCount[2][1] != 2 || toCount[1][2] != 2){
//                values[2][2] = 0;
//            }
//            if(toCount[5][1] != 2|| toCount[6][2] != 2){
//                values[5][2] = 0;
//            }
//            if(toCount[1][5] != 2 || toCount[2][6] != 2){
//                values[2][5] = 0;
//            }
//            if(toCount[6][5] != 2 || toCount[5][6] != 2){
//                values[5][5] = 0;
//            }
//
//            if(toCount[0][2] != 2){
//                values[0][2] = 0;
//                values[1][2] = 4;
//            }
//            if(toCount[0][5] != 2){
//                values[0][5] = 0;
//                values[1][5] = 4;
//            }
//            if(toCount[2][0] != 2){
//                values[2][0] = 0;
//                values[2][1] = 4;
//            }
//            if(toCount[2][7] != 2){
//                values[2][7] = 0;
//                values[2][6] = 4;
//            }
//
//            if(toCount[5][0] != 2){
//                values[5][0] = 0;
//                values[5][1] = 4;
//            }
//            if(toCount[5][7] != 2){
//                values[5][7] = 0;
//                values[5][6] = 4;
//            }
//            if(toCount[7][2] != 2){
//                values[7][2] = 0;
//                values[6][2] = 4;
//            }
//            if(toCount[7][5] != 2){
//                values[7][5] = 0;
//                values[6][5] = 4;
//            }
//
//            if(toCount[0][7] != 2){
//                values[0][6] = 8;
//                values[1][7] = 8;
//                values[1][6] = 10;
//            }
//            if(toCount[0][0] != 2){
//                values[0][1] = 8;
//                values[1][0] = 8;
//                values[1][1] = 10;
//            }
//            if(toCount[7][0] != 2){
//                values[7][1] = 8;
//                values[6][0] = 8;
//                values[6][1] = 10;
//            }
//            if(toCount[7][7] != 2){
//                values[7][6] = 8;
//                values[6][7] = 8;
//                values[6][6] = 10;
//            }

            int amPointsWhite = 0;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if(toCount[row][col] == abs(whichSide-1) && (boardAI[row][col] == 2)){
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
