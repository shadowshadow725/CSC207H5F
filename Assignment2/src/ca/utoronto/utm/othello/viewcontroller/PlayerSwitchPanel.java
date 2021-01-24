package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.PlayerGreedy;
import ca.utoronto.utm.othello.model.PlayerHuman;
import ca.utoronto.utm.othello.model.PlayerRandom;
import ca.utoronto.utm.othello.model.PlayerNewStrategy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Player Switch Panel. User can press the button to switch player between human, random, greedy and new Strategy
 *
 * @author team RankedFlexofFour
 */
public class PlayerSwitchPanel extends GridPane implements EventHandler<ActionEvent> {
    private Button Human, Greedy, Random, NewStrategy;
    private Text text1, text2;
    private OthelloApplicationController game;
    private char player ;
    public PlayerSwitchPanel(char t, OthelloApplicationController game) {

    	this.Human = new Button("Human");
        this.Greedy = new Button("Greedy");
        this.Random = new Button("Random");
        this.NewStrategy = new Button("NewStrategy");

        this.Human.setOnAction(this);
        this.Greedy.setOnAction(this);
        this.Random.setOnAction(this);
        this.NewStrategy.setOnAction(this);

        this.text1 = new Text("Player " + t);
        this.text2 = new Text("Currently: Human");
        this.game = game ;
        this.player = t;
        
        
        this.add(this.text1, 0, 0);
        this.add(this.text2, 1, 0);
        this.add(this.Human, 0, 1);
        this.add(this.Greedy, 0, 2);
        this.add(this.Random, 1, 1);
        this.add(this.NewStrategy, 1, 2);

    }

    public void reset(OthelloApplicationController g){
        this.game = g;
        this.text1.setText("Player " + this.player);
        this.text2.setText("Currently: " + this.Human.getText());

    }

    public void handle(ActionEvent event) {
        if (event.getSource() == this.Human){
            if (this.player == 'X'){
                this.game.changePlayer1(new PlayerHuman(this.game.othello, 'X'));
            }
            else if (this.player =='O'){
                this.game.changePlayer2(new PlayerHuman(this.game.othello, 'O'));
            }
            this.text1.setText("Player " + this.player);
            this.text2.setText("Currently: " + this.Human.getText());
        }
        else if (event.getSource() == this.Greedy){
            if (this.player == 'X'){
                this.game.changePlayer1(new PlayerGreedy(this.game.othello, 'X'));
            }
            else if (this.player =='O'){
                this.game.changePlayer2(new PlayerGreedy(this.game.othello, 'O'));
            }
            this.text1.setText("Player " + this.player);
            this.text2.setText("Currently: " + this.Greedy.getText());
        }
        else if (event.getSource() == this.Random){
            if (this.player == 'X'){
                this.game.changePlayer1(new PlayerRandom(this.game.othello, 'X'));
            }
            else if (this.player =='O'){
                this.game.changePlayer2(new PlayerRandom(this.game.othello, 'O'));
            }
            this.text1.setText("Player " + this.player);
            this.text2.setText("Currently: " + this.Random.getText());
        }
        else if (event.getSource() == this.NewStrategy){
            if (this.player == 'X'){
                this.game.changePlayer1(new PlayerNewStrategy(this.game.othello, 'X'));
            }
            else if (this.player =='O'){
                this.game.changePlayer2(new PlayerNewStrategy(this.game.othello, 'O'));
            }
            this.text1.setText("Player " + this.player);
            this.text2.setText("Currently: " + this.NewStrategy.getText());
        }


    }
}
