package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;

/**
 * Observer of the game
 *
 * @author  team RankedFlexofFour
 */
public class OthelloGUIView implements Observer {
    private OthelloApplication game;
    public OthelloGUIView(OthelloApplication game){
        this.game = game;
    }

    @Override
    public void update(Observable o){
        game.fade();
        game.smashStopwatch();
        game.changeTurn();
        game.updateButtons();
        game.updateTokens();
        game.setGameState();
        game.showAvaliable();
    }
}
