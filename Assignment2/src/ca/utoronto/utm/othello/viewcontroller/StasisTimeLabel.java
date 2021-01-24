package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.util.Observer;
import ca.utoronto.utm.util.Observable;
import javafx.scene.control.Label;

/**
 * Observer of the stopwatch.
 *
 * @author team RankedFlexofFour
 */
public class StasisTimeLabel extends Label implements Observer  {

    private ClausesStopwatch model;
    private char PLAYER;

    public StasisTimeLabel(ClausesStopwatch model, char player) {
        this.model = model;
        this.PLAYER = player;
        this.setText("Player: " + this.PLAYER + "\n" + ClausesStopwatch.INITIAL_COUNT + " s");
    }

    @Override
    public void update(Observable o) {
        int secondsLeft = this.model.getSecondsLeft();
        if (secondsLeft > 0) {
            setText("Player: " + this.PLAYER + "\n" + secondsLeft + " s");
        } else {
            setText("Countdown complete\n" + this.PLAYER+" has ran out of time");
        }
    }

}
