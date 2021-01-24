package ca.utoronto.utm.assignment1.othello;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 * 
 * @author arnold
 *
 */
public class PlayerRandom extends PlayerGreedy{
	private char player;
	private Othello othello;

	public PlayerRandom(Othello othello, char player){
		super(othello, player);
	}

	private Random rand = new Random();

	@Override
	/**
	 * @return returns the move
	 *
	 */
	public Move getMove() {
		int [] a = choseSquare();
		return new Move(a[0], a[1]);
	}
	/**
	 * @return where the algorithm decides to move, array contains {row, col}
	 *
	 */
	private int [] choseSquare(){
		int [] a = new int [2];
		int dim = othello.DIMENSION;
		while(true) {
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					a[0] = rand.nextInt(dim);
					a[1] = rand.nextInt(dim);
					if (super.numFlip(a[0], a[1], i, j) > 0) {
						return a;
					}
				}
			}
		}

	}




}
