package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Othello;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.sql.SQLOutput;

/**
 * Stop watch EventHandler
 *
 * @author team RankedFlexofFour
 */
public class ClausesStopwatchEventHandler implements EventHandler<ActionEvent> {
    private int secondsLeft;
    private ClausesStopwatch cs ;
    private Timeline countdownTimeline;
    public ClausesStopwatchEventHandler(int sec, ClausesStopwatch cs, Timeline tl){
        this.secondsLeft = sec;
        this.cs = cs;
        this.countdownTimeline = tl;

    }

    @Override
    public void handle(ActionEvent event) {
        cs.secondPass();
        cs.notifyObservers();

        if (secondsLeft <= 0) {
            countdownTimeline.stop();
        }
    }
}
