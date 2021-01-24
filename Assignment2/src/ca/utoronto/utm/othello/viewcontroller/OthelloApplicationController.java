package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;

import java.util.ArrayList;

/**
 * The controller of the othello game. This will do appropriate play on the current othello games including play
 * a move, change players or open up a new othello game. The modifications will be updated to it's Observer
 *
 * @author team RankedFlexofFour. arnold.
 */
public class OthelloApplicationController {
	
	protected Othello othello;
	private Move move;
	public String Report;
	public boolean gameover;
	protected Player player1, player2;
	protected Othello othello_undo;
	protected ArrayList<Othello>  undo_list;
	protected Move save_move = null;

    /**
     * Make a copy of this controller. The copy will be used to give hints.
     * @return OthelloApplicationController
     */
	protected OthelloApplicationController copy() {
		OthelloApplicationController game = new OthelloApplicationController();
		game.othello = this.othello.copy();
		game.player1 = this.player1;
		game.player2 = this.player2;
		return game;
	}

    /**
     * Check if player is a computer.
     * @param player
     * @return whether player is a Computer.
     */
	private boolean isPlayerComputer(Player player){
	    return player.getClass().getName() != "ca.utoronto.utm.othello.model.PlayerHuman";
    }

    /**
     * Change player1 to given player type
     * @param p
     */
	protected void changePlayer1(Player p){
		this.player1 = p;
		if (this.othello.getWhosTurn() == 'X' && isPlayerComputer(p)){
			this.play();
		}
		this.othello.notifyObservers();
	}

    /**
     * Change player2 to given player type.
     * @param p
     */
	protected void changePlayer2(Player p){
		this.player2 = p;
		if(this.othello.getWhosTurn() == 'O' && isPlayerComputer(p)){
			this.play();
		}
		this.othello.notifyObservers();
	}

	public OthelloApplicationController() {
		this.othello = new Othello();
		this.othello_undo = new Othello();
		this.gameover = false;
		this.player1 = new PlayerHuman(this.othello, 'X');
		this.player2 = new PlayerHuman(this.othello, 'O');
		this.undo_list = new ArrayList<Othello>();
	}
	
	/**
	 * A string representation of the board, should be only shown once at the beginning.
	 */
	public void start() {
		System.out.println(othello.getBoardString() + othello.getWhosTurn() + " moves next");
	}

    /**
     * One play of the game.
     */
	public void play() {


		othello_undo = this.othello.copy();
		undo_list.add(othello_undo);


		Move move=null;
		char whosTurn = othello.getWhosTurn();
		move = this.move;
		if(whosTurn==OthelloBoard.P1 && isPlayerComputer(this.player1)) {
			move = player1.getMove();
		}
		if(whosTurn==OthelloBoard.P2 && isPlayerComputer(this.player2)) {
			move = player2.getMove();
		}

		this.reportMove(whosTurn, move);
		this.save_move = move;
		othello.move(move.getRow(), move.getCol());

		while ((othello.getWhosTurn() == 'X' && isPlayerComputer(this.player1)) || (othello.getWhosTurn() == 'O' && isPlayerComputer(this.player2))){
			if (othello.getWhosTurn() == 'X' && isPlayerComputer(this.player1)){
				Move m = player1.getMove();
				this.save_move = m;
				othello.move(m.getRow(), m.getCol());
				this.othello.notifyObservers();

			}
			if (othello.getWhosTurn() == 'O' && isPlayerComputer(this.player2)){
				Move m = player2.getMove();
                this.save_move = m;
				othello.move(m.getRow(), m.getCol());
				this.othello.notifyObservers();
			}
		}

		if(!othello.isGameOver()) {
			this.report();
		}
		else {
			this.gameover();
			this.reportFinal();
		}
		this.othello.notifyObservers();

	}

	/**
	 * Clean one othello game
	 */
	protected void clean(){
		this.othello= new Othello();
		this.othello_undo = new Othello();
		this.undo_list = new ArrayList<>();
	}
	
	
	/**
	 * This method is used by the EventHandler to set up the next move
	 * @param row
	 * @param col
	 */
	public void getMove(int row,int col) {
		this.move = new Move(row,col);

	}
	
	/**
	 * Change the status of the game.
	 */
	public void gameover() {
		this.gameover = true;
	}
	
	/**
	 * To help player to know what button(move) they just pressed.
	 * @param whosTurn
	 * @param move
	 */
	private void reportMove(char whosTurn, Move move) {
		if (move != null){
			System.out.println(whosTurn + " moves" + "(" + move.getRow() + ", " + move.getCol() + ")");
		}
	}
	
	
	/**
	 * Report of the game when it's not finished.
	 */
	protected void report() {	
		this.Report = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  " 
				+ othello.getWhosTurn() + " moves next";
	
	}


	/**
	 * Report of the game when it's finished.
	 */
	protected void reportFinal() {
		this.Report = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) 
				+ "  " + othello.getWinner() + " won\n";
	}

}
