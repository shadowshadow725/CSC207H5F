package ca.utoronto.utm.othello.model;
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
public class PlayerGreedy extends Player {
	public PlayerGreedy(Othello othello, char player) {
		super(othello, player);
	}
	
	@Override
	public Move getMove() {
		Othello othelloCopy = othello.copy();
		Move bestMove=new Move(0,0);
		int bestMoveCount=othelloCopy.getCount(this.player);;
		for(int row=0;row<Othello.DIMENSION;row++) {
			for(int col=0;col<Othello.DIMENSION;col++) {
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

