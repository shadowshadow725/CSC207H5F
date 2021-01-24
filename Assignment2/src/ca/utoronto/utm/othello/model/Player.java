package ca.utoronto.utm.othello.model;

public abstract class Player {
	protected Othello othello;
	protected char player;

	public Player(Othello othello, char player) {
		this.othello=othello;
		this.player=player;
	}
	public char getPlayer() {
		return this.player;
	}
	public abstract Move getMove();
}
