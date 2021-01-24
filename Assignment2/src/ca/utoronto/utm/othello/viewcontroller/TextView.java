package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Othello;
import ca.utoronto.utm.util.*;

/**
 * Gives the string representation of the board.(Displayed only once)
 *
 * @author team RankedFlexofFour
 */
public class TextView implements Observer {
	
	@Override
	public void update(Observable o) {
		Othello game = (Othello) o;
		System.out.println(game.getBoardString());
	}
}

	
	


