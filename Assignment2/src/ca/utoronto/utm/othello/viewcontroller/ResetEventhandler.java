package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.PlayerHuman;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
/**
 * Reset EventHandler. Player can reset the game.
 *
 * @author team RankedFlexofFour
 */


public class ResetEventhandler implements EventHandler<ActionEvent> {
    private OthelloApplicationController game ;
    private OthelloGUIView view;
    private OthelloApplication g;
    public ResetEventhandler(OthelloApplicationController game, OthelloGUIView view, OthelloApplication g){
        this.game = game;
        this.view = view;
        this.g = g;
    }

    public void handle(ActionEvent event){
        this.view = g.resetVariables();
        this.game.changePlayer1(new PlayerHuman(this.game.othello,'X'));
        this.game.changePlayer2(new PlayerHuman(this.game.othello, 'O'));
        this.game.othello.notifyObservers();
    }
}
