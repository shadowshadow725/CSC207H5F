package ca.utoronto.utm.othello.model;

public abstract class OthelloControllerVerbose extends OthelloController {

	protected void reportMove(char whosTurn, Move move) {
		System.out.println(whosTurn + " makes move " + move + "\n");
	}

	protected void report() {
		
		String s = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  " 
				+ othello.getWhosTurn() + " moves next";
		System.out.println(s);
	}

	protected void reportFinal() {
		
		String s = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) 
				+ "  " + othello.getWinner() + " won\n";
		System.out.println(s);
	}

}
