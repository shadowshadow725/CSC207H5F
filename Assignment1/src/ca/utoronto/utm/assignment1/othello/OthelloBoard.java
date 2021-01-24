package ca.utoronto.utm.assignment1.othello;

import java.util.EmptyStackException;
import java.util.EventListener;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * 
 * @author arnold
 *
 */
public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;

	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	public int getDimension() {
		return this.dim;
	}

	/**
	 * 
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
		if (player == P1){
			return P2;
		}
		else if (player == P2){
			return P1;
		}
		return EMPTY;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		if (!validCoordinate(row, col)) {
			return EMPTY;
		}
		return board[row][col];
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) {
		if (row > getDimension()-1 || col > getDimension()-1 ||col < 0 || row < 0){
			return false;
		}
		return true;
	}

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol),a you encounter a sequence of t least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */
	private char alternation(int row, int col, int drow, int dcol) {

		if (!validCoordinate(row+drow, col+dcol) || get(row, col) == EMPTY){
			return EMPTY;
		}
		if (dcol == 0 && drow == 0){
			return EMPTY;
		}

		char temp = get(row, col);
		while (get(row, col) == temp){
			row+=drow;
			col+=dcol;
			if (!validCoordinate(row,col)){
				return EMPTY;
			}
		}
		return get(row, col);
	}

	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOOO
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) {
		int sum = 0;
		if (!validCoordinate(row+drow, col+dcol)){
			return -1;
		}
		row+=drow;col+=dcol;
		while(get(row,col)!= player){
			board[row][col] = player;
			row+=drow;
			col+=dcol;
			sum++;
			if (!validCoordinate(row+drow, col+dcol)){
				return -1;
			}
		}
		if (sum == 0){
			return -1;
		}
		return sum;

	}

	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) {

		if (board[row][col] == EMPTY && validCoordinate(row+drow, col+ dcol)){
			if (board[row+drow][col+dcol]  == EMPTY ){
				return EMPTY;
			}
			return alternation(row+drow, col+dcol, drow, dcol);
//			return otherPlayer(board[row+drow][col+dcol] );
		}
		return EMPTY;
// EX
	}

	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {

		boolean m1 = false, m2 = false;
		for (int i = 0;i<dim;i++){
			for (int j = 0;j<dim;j++){
				if (!m1){
					if (hasMove(i, j, 1, 0  ) == P1 || hasMove(i, j, 1, 1) == P1 || hasMove(i, j, 1, -1) == P1){
						m1 = true;
					}
					else if (hasMove(i, j, 0, -1) == P1 || hasMove(i, j, 0, 1) == P1){
						m1 = true;
					}
					else if (hasMove(i, j, -1, -1) == P1 || hasMove(i, j, -1, 1) == P1 || hasMove(i, j, -1, 0) == P1){
						m1 = true;
					}
				}
				if (!m2){
					if (hasMove(i, j, 1, 0  ) == P2 || hasMove(i, j, 1, 1) == P2 || hasMove(i, j, 1, -1) == P2){
						m2 = true;
					}
					else if (hasMove(i, j, 0, -1) == P2 || hasMove(i, j, 0, 1) == P2){
						m2 = true;
					}
					else if (hasMove(i, j, -1, -1) == P2 || hasMove(i, j, -1, 1) == P2 || hasMove(i, j, -1, 0) == P2){
						m2 = true;
					}
				}
			}
		}
		if (m1 && m2){
			return BOTH;
		}
		else if (m1){
			return P1;
		}
		else if (m2){
			return P2;
		}
		return EMPTY;
	}

	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * 	 * making appropriate modifications to the board. Nothing is changed if this is
	 * 	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {

		if (get(row, col) != EMPTY){
			return false;
		}
		boolean moveable = false;
		for (int i = -1;i<2;i++){
			for (int j = -1;j<2;j++){
				if (alternation(row+i, col+j, i, j) == player){
					moveable = true;
				}
			}
		}
		if (moveable){
			board[row][col] = player;
			for (int i = -1;i<2;i++){
				for (int j = -1;j<2;j++){
					if (alternation(row+i,col+j,i,j) == player){
						flip(row, col, i, j, player);
					}

				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
		int count = 0;
		for (int i = 0;i<getDimension();i++){
			for (int j = 0;j<getDimension();j++){
				if (get(i, j) == player){
					count++;

				}
			}
		}
		return count;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.board[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.board[row][col] = P2;
				}
			}
		}
		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.board[4][4] = EMPTY;
		ob.board[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());

	}
}
