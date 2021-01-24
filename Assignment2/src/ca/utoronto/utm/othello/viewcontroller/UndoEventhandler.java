package ca.utoronto.utm.othello.viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Undo EventHandler. Player can press the button to undo last step or steps.
 *
 * @author team RankedFlexofFour
 */
public class UndoEventhandler implements EventHandler<ActionEvent> {
    private OthelloApplicationController game ;
    private OthelloApplication g;
    private OthelloGUIView view;
    public UndoEventhandler(OthelloApplicationController game, OthelloGUIView view, OthelloApplication g){
        this.game = game;
        this.g = g;
        this.view = view;

    }

    public void handle(ActionEvent event) {
        if (this.game.gameover){
            return;
        }
        this.g.undo();
    }
}
