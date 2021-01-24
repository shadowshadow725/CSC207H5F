package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.util.Observable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Stopwatch model that will count down from 300s to 0s when one player starts to play
 *
 * @author team RankedFlexofFour
 *
 */
public class ClausesStopwatch extends Observable {
    public static final int INITIAL_COUNT = 300;
    private int secondsLeft = INITIAL_COUNT;

    protected void secondPass (){
        this.secondsLeft--;
    }

    Timeline countdownTimeline = new Timeline();

    private ClausesStopwatchEventHandler countdownEventHandler = new ClausesStopwatchEventHandler(this.secondsLeft, this, this.countdownTimeline) ;

    public ClausesStopwatch() {
        countdownTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame perSecondKeyFrame = new KeyFrame(Duration.seconds(1), countdownEventHandler);
        countdownTimeline.getKeyFrames().add(perSecondKeyFrame);
    }

    public void startTimer() {

        countdownTimeline.play();
    }

    public void pauseTimer() {

        countdownTimeline.pause();
    }

    public void resetTimer() {

        countdownTimeline.stop();
        secondsLeft = INITIAL_COUNT;
        notifyObservers();
    }
    public void changeSecondsLeft(int i) {
    	this.secondsLeft = i;
    	
    }
    public int getSecondsLeft() {
        return secondsLeft;
    }
}
