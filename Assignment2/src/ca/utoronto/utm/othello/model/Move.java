package ca.utoronto.utm.othello.model;
/**
 * 
 * @author arnold
 *
 */
public class Move {
	private int row,col;
	public Move(int row, int col) {
		this.row=row; this.col=col;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public String toString() {
		return "("+this.row+","+this.col+")";
	}
}
