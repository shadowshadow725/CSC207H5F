package ca.utoronto.utm.assignment1.othello;
/**
 * 
 * @author arnold
 *
 */
// TODO: Javadoc this class
public class Move {
	private int row, col;

	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}
	/**
	 *
	 * @return row
	 *
	 */
	public int getRow() {
		return row;
	}

	/**
	 *
	 * @return col
	 *
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information.
	 */
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
