package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.Move;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * The real Othello Java game(application). Players basically play othello against other human player/computer player.
 * The one that has the most tokens wins.
 * The player can get hints and undo steps.
 *
 * @author  team RankedFlexofFour
 */
public class OthelloApplication extends Application  {
    private static final int BOARDSIZE = 8;

    private OthelloGUIView view = new OthelloGUIView(this);
    private OthelloApplicationController oc = new OthelloApplicationController();
    private OthelloEventHandler othellopresshandler = new OthelloEventHandler(oc);
    protected Button[] [] g = new Button[8][8];
    private GridPane grid = new GridPane();
    private Text tf = new Text(oc.othello.getWhosTurn()+" moves next");
    private Text pieceCounter = new Text();
    private Text gameState = new Text();
    private PlayerSwitchPanel p1 = new PlayerSwitchPanel('X', this.oc);
    private PlayerSwitchPanel p2 = new PlayerSwitchPanel('O', this.oc);
    private Button reset = new Button("reset");
    private Button undo = new Button("undo");
    private Button random = new Button("random hint");
    private Button greedy = new Button("greedy hint");
    private Button newstrategy = new Button("new Strategy hint");
    private HintEventhandler hinthandler = new HintEventhandler(oc,view, this);
    private UndoEventhandler undohandler = new UndoEventhandler(oc, view,this);
    private ResetEventhandler resethandler = new ResetEventhandler(oc, view, this);
    
    // Stopwatch
    private ClausesStopwatch sw1 = new ClausesStopwatch();
    private ClausesStopwatch sw2 = new ClausesStopwatch();
    private StasisTimeLabel s1 = new StasisTimeLabel(this.sw1, 'X');
    private StasisTimeLabel s2 = new StasisTimeLabel(this.sw2, 'O');
    private TextField timeInput1 = new TextField("x");
    private TextField timeInput2 = new TextField("o");
    private Button textButton1 = new Button("X ok");
    private Button textButton2 = new Button("O ok");

    /**
     * Fade animation when a button is clicked.
     */
    protected void fade(){
        if (this.oc.save_move != null){
            Move move = this.oc.save_move;
            int row = move.getRow();
            int col = move.getCol();
            Button b = (Button)this.grid.getChildren().get(row*8+col);
            FadeTransition fade = new FadeTransition(Duration.seconds(0.5),b);
            fade.setFromValue(0);
            fade.setToValue(1.0);
            fade.setCycleCount(2);
            fade.play();
        }

    }

    /**
     * Pause the stopwatch for the player not playing and start if for the next player.
     */
    protected void smashStopwatch(){
        if (this.oc.othello.getWhosTurn() == 'X'){
            sw2.pauseTimer();
            sw1.startTimer();
        }
        else if(this.oc.othello.getWhosTurn() == 'O'){
            sw1.pauseTimer();
            sw2.startTimer();
        }
    }

    /**
     * Reset timers.
     */
    private void resetTimers(){
        sw1.resetTimer();
        sw2.resetTimer();
    }

    /**
     * View layout.
     */
    private void addStasisTimeLabel(){
        this.grid.add(s1, 9, 6);
        this.grid.add(s2, 9, 7);
    }

    /**
     * Reset view, including a new controller, new Model-View hookup, new View-Controller hookup, new
     * Controller-Model hookup.
     * @return
     */
    protected OthelloGUIView resetVariables(){
        this.oc = new OthelloApplicationController();
        this.othellopresshandler = new OthelloEventHandler(this.oc);
        this.hinthandler = new HintEventhandler(oc,view, this);
        this.undohandler = new UndoEventhandler(oc, view,this);
        this.view = new OthelloGUIView(this);
        this.undo.setOnAction(undohandler);
        this.reset.setOnAction(resethandler);
        this.random.setOnAction(hinthandler);
        this.greedy.setOnAction(hinthandler);
        this.newstrategy.setOnAction(hinthandler);
        this.resetButton();
        this.resetTimers();
        this.reattachObservers();
        this.updateButtons();
        this.resetPlayer();


        return this.view;
    }

    /**
     * Undo the last step or steps.
     */
    protected void undo(){
            if (this.oc.undo_list.size() == 0) {
                this.oc.clean();
                this.oc.othello.attach(this.view);
                this.oc.othello.notifyObservers();
            }
            else {
                this.oc.othello = this.oc.undo_list.get(this.oc.undo_list.size() -1);
                this.oc.undo_list.remove(this.oc.undo_list.size() -1);
                this.oc.othello.attach(this.view);
                this.oc.othello.notifyObservers();

            }
    }

    /**
     * Model-View hookup.
     */
    protected void reattachObservers (){
        this.oc.othello.attach(this.view);
        this.sw1.attach(this.s1);
        this.sw2.attach(this.s2);

    }

    /**
     * View-Controller hookup.
     */
    private void resetButton(){
        for(int i = 0;i<8;i++){
            for (int j =0;j<8;j++){
                this.g[i][j].setOnAction(this.othellopresshandler);
            }
        }

    }

    /**
     * Reset Players
     */
    private void resetPlayer(){
        this.p1.reset(this.oc);
        this.p2.reset(this.oc);
    }

    /**
     * Text shows who is playing next.
     */
    protected void changeTurn(){
        this.tf.setText(oc.othello.getWhosTurn() + " moves next");
    }

    /**
     * Update tokens each player has on the board.
     */
    protected void updateTokens(){
        this.pieceCounter.setText("X = "+ this.oc.othello.getCount('X') + "\nO = "+this.oc.othello.getCount('O'));
    }

    /**
     * Get the othello game board.
     * @return the game board.
     */
    private char [] [] getBoard(){
        char [] [] board = new char[BOARDSIZE][BOARDSIZE];

        for( int i = 0;i< BOARDSIZE;i++){
            for (int j = 0;j< BOARDSIZE;j++){
                board[i][j] = this.oc.othello.getToken(i,j);
            }
        }
        return board;
    }

    /**
     * Tells the game state.
     */
    protected void setGameState(){
        this.gameState.setText("In progress");
        if (this.oc.othello.isGameOver()){
            this.tf.setText("");
            this.gameState.setText("GameOver\n"+this.oc.othello.getWinner()+" Won");
        }
    }

    /**
     * Set buttons and colors.
     */
    private void setButtons(){
        char boardData [] [] = getBoard();
        for (int row = 0;row<8;row++){
            for(int col = 0;col<8;col++){
                this.g[row][col] = new Button(boardData[row][col]+"");
                this.g[row][col].setOnAction(this.othellopresshandler);
                this.g[row][col].setPrefSize(50, 50);
                this.g[row][col].setShape(new Circle(30));
                this.g[row][col].setStyle("-fx-background-color: #CCCCCC");
                if (boardData[row][col] == 'X'){

                    this.g[row][col].setStyle("-fx-background-color: #000000; " +
                            "-fx-text-fill: white; ");
                }
                else if (boardData[row][col] == 'O') {
                    this.g[row][col].setStyle("-fx-background-color: #FFFFFF; ");
                }
                this.grid.add(this.g[row][col], col , row,1,1);
            }
        }
    }

    /**
     * Update buttons and colors.
     */
    protected void updateButtons(){
        char [] [] boardData = getBoard();
        for (int row = 0;row<8;row++){
            for(int col = 0;col<8;col++){
                this.g[row][col].setText(boardData[row][col]+"");
                if (boardData[row][col] == 'X'){

                    this.g[row][col].setStyle("-fx-background-color: #000000; " +
                            "-fx-text-fill: white; ");

                }
                else if (boardData[row][col] == 'O') {
                    this.g[row][col].setStyle("-fx-background-color: #FFFFFF; ");
                }
                else if (boardData[row][col] == ' ') {
                    this.g[row][col].setStyle("-fx-background-color: #CCCCCC; ");
                }
            }
        }
    }

    /**
     * Show available moves with a different color.
     */
    protected void showAvaliable() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int row = 0; row < 8 ; row++) {
			for(int col = 0; col < 8; col++) {
				OthelloApplicationController copy = this.oc.copy();
				if(copy.othello.move(row, col))moves.add(new Move(row,col));
			}
		}
		for (Move move: moves){
			this.g[move.getRow()][move.getCol()].setStyle("-fx-background-color: #ADD8E6; ");
		}
    }

    /**
     * Check value entered by the player to the timer.
     * @return value entered by the player if it's an integer, or 0 instead.
     */
    private int isInt1(String num){
        try{
            int time = Integer.parseInt(num);
            return time ;
        }
        catch (Exception e ){
            System.out.println("You must enter a number");
        }
        return 0;
    }
    
    @Override
    public void start(Stage stage) throws Exception {

        this.oc.othello.attach(this.view);
        this.sw1.attach(this.s1);
        this.sw2.attach(this.s2);

        //Create the Othello controller to control the Model

        this.grid.setPadding(new Insets(20));
        this.grid.setHgap(10.0);
        this.grid.setVgap(10.0);
        this.undo.setOnAction(undohandler);
        this.reset.setOnAction(resethandler);
        this.random.setOnAction(hinthandler);
        this.greedy.setOnAction(hinthandler);
        this.newstrategy.setOnAction(hinthandler);
        this.setButtons();
        this.updateTokens();
        this.setGameState();
        
        
        this.showAvaliable();
        
        //View components
        this.grid.add(this.textButton1,9,12);
        this.grid.add(this.textButton2, 10, 12);
        this.grid.add(this.timeInput1, 9, 11);
        this.grid.add(this.timeInput2, 9, 10);
        this.grid.add(this.tf, 9,1);
        this.grid.add(this.pieceCounter,9 ,2);
        this.grid.add(this.gameState, 9, 0);
        this.grid.add(this.p1, 9,3);
        this.grid.add(this.p2, 9, 4);
        this.grid.add(this.reset, 9, 5);
        this.grid.add(this.undo,10,5);
        this.grid.add(this.random, 10, 0);
        this.grid.add(this.greedy, 10, 1);
        this.grid.add(this.newstrategy, 10,2);
        // Stopwatch
        this.addStasisTimeLabel();
        this.sw1.startTimer();
        
        this.textButton1.setOnAction(e -> cleanupStopwatch1(e));
        this.textButton2.setOnAction(e -> cleanupStopwatch2(e));
        // SCENE
        Scene scene = new Scene(grid);
        stage.setTitle("Othello");
        stage.setScene(scene);

        // LAUNCH THE GUI
        this.oc.start(); //start the game, the board will print first.
        stage.show();
    }
    private void cleanupStopwatch1(ActionEvent e){
        if (isInt1(this.timeInput1.getText()) != 0){
            this.sw1.changeSecondsLeft(isInt1(this.timeInput1.getText()));
            this.grid.getChildren().remove(this.timeInput1);
            this.grid.getChildren().remove(e.getSource());
            this.sw1.notifyObservers();
        }
    }
    private void cleanupStopwatch2(ActionEvent e){
        if (isInt1(this.timeInput2.getText()) != 0){
            this.sw2.changeSecondsLeft(isInt1(this.timeInput2.getText()));
            this.grid.getChildren().remove(this.timeInput2);
            this.grid.getChildren().remove(e.getSource());
            this.sw2.notifyObservers();
        }
    }
    public static void main(String[] args) {
        launch(args);

    }
}
