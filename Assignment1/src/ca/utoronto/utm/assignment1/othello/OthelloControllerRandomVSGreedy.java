package ca.utoronto.utm.assignment1.othello;

import java.util.EnumMap;

/**
 * The goal here is to print out the probability that Random wins and Greedy
 * wins as a result of playing 10000 games against each other with P1=Random and
 * P2=Greedy. What is your conclusion, which is the better strategy?
 * @author arnold
 *
 */
public class OthelloControllerRandomVSGreedy extends OthelloControllerHumanVSGreedy {
	protected Othello othello;
	PlayerRandom player1;
	PlayerGreedy player2;

	public OthelloControllerRandomVSGreedy(){
		this.othello = new Othello();
		this.player1 = new PlayerRandom(this.othello, OthelloBoard.P1);
		this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
	}
	/**
	 * plays a game of othello
	 *
	 */
	public void play() {

		while (!othello.isGameOver()) {
			this.report();

			Move move = null;
			char whosTurn = othello.getWhosTurn();

			if (whosTurn == OthelloBoard.P1)
				move = player1.getMove();
			if (whosTurn == OthelloBoard.P2)
				move = player2.getMove();

			this.reportMove(whosTurn, move);
			othello.move(move.getRow(), move.getCol());
		}
		this.reportFinal();
	}
	/**
	 * Prints which player's turn and what their move is
	 * @param whosTurn
	 * @param move
	 *
	 *
	 */
	private void reportMove(char whosTurn, Move move) {
		System.out.println(whosTurn + " makes move " + move + "\n");
	}
	/**
	 * prints out a string representation of the game board and whos turn is it
	 *
	 */
	private void report() {

		String s = othello.getBoardString() + OthelloBoard.P1 + ":"
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
				+ othello.getWhosTurn() + " moves next";
		System.out.println(s);
	}
	/**
	 * prints the final states of the game and the winner
	 *
	 */
	private void reportFinal() {

		String s = othello.getBoardString() + OthelloBoard.P1 + ":"
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2)
				+ "  " + othello.getWinner() + " won\n";
		System.out.println(s);
	}

	/**
	 * Run main to execute the simulation and print out the two line results.
	 * Output looks like: 
	 * Probability P1 wins=.75 
	 * Probability P2 wins=.20
	 * @param args
	 */

	public static void main(String[] args) {
		int p1wins = 0, p2wins = 0, numGames = 10000;
		for (int i = 0;i < numGames; i++){
			OthelloControllerRandomVSGreedy oc = new OthelloControllerRandomVSGreedy();
			oc.play();
			if (oc.othello.getWinner() == 'X'){
				p1wins++;
			}
			else if (oc.othello.getWinner() == 'O'){
				p2wins++;
			}

		}

		System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
		System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
	}
}
