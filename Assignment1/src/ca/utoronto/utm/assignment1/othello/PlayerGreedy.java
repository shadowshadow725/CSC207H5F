package ca.utoronto.utm.assignment1.othello;

import javax.swing.*;
import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 * 
 * @author arnold
 *
 */

public class PlayerGreedy {
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";

	private Othello othello;
	private char player;

	public PlayerGreedy(Othello othello, char player){
	    this.player = player;
	    this.othello = othello;
    }
    /**
     * @return returns the most greedy move
     *
     */
	public Move getMove() {

        int [] a = getMoveCord();
        return new Move(a[0], a[1]);
	}
    /**
     * @return where the algorithm decides to move, array contains {row, col}
     *
     */
	private int [] getMoveCord(){
        int [] cord = new int [2];
        int max = -1;

        for (int i = 0;i<othello.board.getDimension();i++){
            for (int j = 0;j<othello.board.getDimension();j++){

                int t = numFlipPersq(i, j);
                if (t > max && othello.board.get(i, j) == ' '){
                    cord = new int [] {i, j};
                    max = t;
                }
            }
        }
        return cord;
	}

    /**
     * @param row
     * @param col
     * @param drow
     * @param dcol
     * @return returns the number of flips in the given direction
     *
     */
	protected int numFlip (int row, int col, int drow, int dcol){
        int sum = 0;
        row+=drow;col+=dcol;
        if(drow == 0 && dcol == 0){
            return 0;
        }
        if (row > othello.board.getDimension()-1 || col > othello.board.getDimension()-1 ||col < 0 || row < 0){
            return 0;
        }
        if (othello.board.get(row, col)  == ' '){
            return 0;
        }
        while(othello.board.get(row,col) != player && othello.board.get(row, col) != ' ') {
            row += drow;
            col += dcol;
            sum++;

            if (row > othello.board.getDimension()-1 || col > othello.board.getDimension()-1 ||col < 0 || row < 0){
                return 0;
            }
        }
        if (othello.board.get(row, col) == ' '){
            return 0;
        }
        return sum;
    }
    /**
     * @param row
     * @param col
     * @return returns the total amounts of flips if the players move their move at {row, col}
     *
     */
    private int numFlipPersq(int row, int col){
        int max = 0;
        for(int i = -1;i<2;i++){
            for (int j = -1;j<2;j++){
                max += numFlip(row, col, i, j);
            }
        }
        return max;

    }


}
