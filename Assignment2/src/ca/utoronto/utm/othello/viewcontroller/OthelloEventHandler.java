package ca.utoronto.utm.othello.viewcontroller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Chess button EventHandler. When a player clicked a button, the player makes a move on the board.
 */
public class OthelloEventHandler implements EventHandler<ActionEvent> {
	private OthelloApplicationController game ;
    
    public OthelloEventHandler(OthelloApplicationController game){
        this.game = game;
    }

	public void handle(ActionEvent event) {

		Button source = (Button)event.getSource();
		if (!this.game.gameover) {
			GridPane grid = (GridPane) source.getParent();

			for (int i = 0;i<64;i++){
				if (grid.getChildren().get(i) == source){

					this.game.getMove((int)Math.floor(i/8),+ i%8);

				}
			}
			this.game.play();	
    	}
    }
}


