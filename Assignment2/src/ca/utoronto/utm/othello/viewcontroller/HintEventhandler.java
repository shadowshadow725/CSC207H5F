package ca.utoronto.utm.othello.viewcontroller;


import ca.utoronto.utm.othello.model.Move;
import ca.utoronto.utm.othello.model.PlayerGreedy;
import ca.utoronto.utm.othello.model.PlayerNewStrategy;
import ca.utoronto.utm.othello.model.PlayerRandom;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Hint EventHandler. When a hint button is clicked, a hint will be highlighted on the board.
 *
 * The hint follows certain strategy:random, greedy, new Strategy
 *
 * @author team RankedFlexofFour
 */
public class HintEventhandler implements EventHandler<ActionEvent> {
    private OthelloApplicationController game;
    private OthelloApplication g;
    private OthelloGUIView view;

    public HintEventhandler(OthelloApplicationController game, OthelloGUIView view, OthelloApplication g) {
        this.game = game;
        this.g = g;
        this.view = view;

    }

    public void handle(ActionEvent event) {
        int[] hintmove = new int[2];
        Button source = (Button)event.getSource();
        if (!this.game.othello.isGameOver()){
            OthelloApplicationController gamecopy = this.game.copy();
            if (source.getText() == "greedy hint") {
                if (this.game.othello.getWhosTurn() == 'X') {
                    gamecopy.player1 = new PlayerGreedy(game.othello.copy(), 'X');
                    Move move = gamecopy.player1.getMove();
                    hintmove[0] = move.getRow();
                    hintmove[1] = move.getCol();
                }
                else {
                    gamecopy.player2 = new PlayerGreedy(game.othello.copy(), 'O');
                    Move move = gamecopy.player2.getMove();
                    hintmove[0] = move.getRow();
                    hintmove[1] = move.getCol();
                }
            }
            if (source.getText() == "random hint") {
                if (this.game.othello.getWhosTurn() == 'X') {
                    gamecopy.player1 = new PlayerRandom(game.othello.copy(), 'X');
                    Move move = gamecopy.player1.getMove();
                    hintmove[0] = move.getRow();
                    hintmove[1] = move.getCol();
                }
                else {
                    gamecopy.player2 = new PlayerRandom(game.othello.copy(), 'O');
                    Move move = gamecopy.player2.getMove();
                    hintmove[0] = move.getRow();
                    hintmove[1] = move.getCol();
                }
            }
            if (source.getText() == "new Strategy hint") {
                if (this.game.othello.getWhosTurn() == 'X') {
                    gamecopy.player1 = new PlayerNewStrategy(game.othello.copy(), 'X');
                    Move move = gamecopy.player1.getMove();
                    hintmove[0] = move.getRow();
                    hintmove[1] = move.getCol();
                }
                else {
                    gamecopy.player2 = new PlayerNewStrategy(game.othello.copy(), 'O');
                    Move move = gamecopy.player2.getMove();
                    hintmove[0] = move.getRow();
                    hintmove[1] = move.getCol();
                }
            }
            GridPane grid = (GridPane) source.getParent();
            Button button = (Button)grid.getChildren().get((hintmove[0])*8+(hintmove[1]));
            button.setStyle("-fx-background-color: #F5F5DC");
        }





    }
}


