package ca.utoronto.utm.othello.model;

import java.util.ArrayList;
import java.util.Random;

public class PlayerNewStrategy extends Player {
	
	private Random rand = new Random();

	
	public PlayerNewStrategy(Othello othello, char player) {
			super(othello, player);
		}
	
	public Move getMove() {
		Othello othelloCopy = othello.copy();
		
		ArrayList<Move> moves = new ArrayList<Move>();
		
		Move bestMove = new Move(0,0);
		int bestMoveCount = othelloCopy.getCount(this.player);;
		
		
		// Choose a corner if available,
		if (othelloCopy.move(0, 0))return new Move(0,0);
		else if (othelloCopy.move(0, Othello.DIMENSION-1))return new Move(0, Othello.DIMENSION-1);
		else if (othelloCopy.move(Othello.DIMENSION-1, 0))return new Move(Othello.DIMENSION-1, 0);
		else if (othelloCopy.move(Othello.DIMENSION-1, Othello.DIMENSION-1))return new Move(Othello.DIMENSION-1, Othello.DIMENSION-1);
		
		// otherwise choose a side if available, 
		for (int row = 0;row < Othello.DIMENSION; row++) {
			if(othelloCopy.move(row, 0))moves.add(new Move(row,0));
			if(othelloCopy.move(row, Othello.DIMENSION-1))moves.add(new Move(row,Othello.DIMENSION-1));
		}
		for(int col = 0; col < Othello.DIMENSION; col++) {
			if(othelloCopy.move(0, col))moves.add(new Move(0,col));
			if(othelloCopy.move(Othello.DIMENSION-1, col))moves.add(new Move(Othello.DIMENSION-1,col));
		}
		if (!moves.isEmpty()) {
			return moves.get(this.rand.nextInt(moves.size()));
		}
		
		// otherwise choose a space that 
		// maximizes the players presence in the middle 4 by 4 square of the board,
		int mid = Othello.DIMENSION / 2;
		int count = 0;
		
		if (othelloCopy.getToken(mid-1, mid-1) == this.player) count += 1;
		if (othelloCopy.getToken(mid, mid-1) == this.player) count += 1;
		if (othelloCopy.getToken(mid-1, mid) == this.player) count += 1;
		if (othelloCopy.getToken(mid, mid) == this.player) count += 1;
		
		int maxi = count;
		if (count != 4) {
			for(int row = 0; row<Othello.DIMENSION; row++) {
				for(int col = 0; col<Othello.DIMENSION; col++) {
					int c = 0;
					othelloCopy = othello.copy();
					if(othelloCopy.move(row, col)) {
						if (othelloCopy.getToken(mid-1, mid-1) == this.player) c += 1;
						if (othelloCopy.getToken(mid, mid-1) == this.player) c += 1;
						if (othelloCopy.getToken(mid-1, mid) == this.player) c += 1;
						if (othelloCopy.getToken(mid, mid) == this.player) c += 1;
						if (c>= maxi) {
							maxi = c;
						}
					}
				}
			}
			for(int row = 0; row<Othello.DIMENSION; row++) {
				for(int col = 0; col<Othello.DIMENSION; col++) {
					int c = 0;
					othelloCopy = othello.copy();
					if(othelloCopy.move(row, col)) {
						if (othelloCopy.getToken(mid-1, mid-1) == this.player) c += 1;
						if (othelloCopy.getToken(mid, mid-1) == this.player) c += 1;
						if (othelloCopy.getToken(mid-1, mid) == this.player) c += 1;
						if (othelloCopy.getToken(mid, mid) == this.player) c += 1;
						if (c == maxi) {
							moves.add(new Move(row,col));
						}
					}
				}
			}	
			return moves.get(this.rand.nextInt(moves.size()));
		}
		
		else {
			// otherwise choose the location maximizing the movers presence on the board.
			for(int row = 0; row<Othello.DIMENSION; row++) {
				for(int col = 0; col < Othello.DIMENSION; col++) {
					othelloCopy = othello.copy();
					if(othelloCopy.move(row, col) && othelloCopy.getCount(this.player)>bestMoveCount) {
						bestMoveCount = othelloCopy.getCount(this.player);
						bestMove = new Move(row,col);
					}
				}
			}
			return bestMove;
		}
	}

}
		