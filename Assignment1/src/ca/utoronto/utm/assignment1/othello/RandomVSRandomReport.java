package ca.utoronto.utm.assignment1.othello;


import javax.swing.*;
import java.awt.*;

public class RandomVSRandomReport {
    protected Othello othello;
    PlayerGreedy player2;
    PlayerGreedy player1;
    PlayerRandom player3;
    PlayerRandom player4;
    
    public RandomVSRandomReport(){
        this.othello = new Othello();
        this.player1 = new PlayerGreedy(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
        this.player3 = new PlayerRandom(this.othello, OthelloBoard.P1);
        this.player4 = new PlayerRandom(this.othello, OthelloBoard.P2);

    }
    /**
     * plays a game of othello for player greedy1 and greedy2
     *
     */
    public void play() {

        while (!othello.isGameOver()) {
            this.report();

            Move move = null;
            char whosTurn = othello.getWhosTurn();

            if (whosTurn == OthelloBoard.P1)
                move = player1.getMove();
            if (whosTurn == OthelloBoard.P2)
                move = player2.getMove();

            this.reportMove(whosTurn, move);
            othello.move(move.getRow(), move.getCol());
        }
        this.reportFinal();
    }
    /**
     * plays a game of othello for player random1 and random2
     *
     */
    public void play34() {

        while (!othello.isGameOver()) {
            this.report();

            Move move = null;
            char whosTurn = othello.getWhosTurn();

            if (whosTurn == OthelloBoard.P1)
                move = player3.getMove();
            if (whosTurn == OthelloBoard.P2)
                move = player4.getMove();

            this.reportMove(whosTurn, move);
            othello.move(move.getRow(), move.getCol());
        }
        this.reportFinal();
    }
    /**
     * Prints which player's turn and what their move is
     * @param whosTurn
     * @param move
     *
     *
     */
    private void reportMove(char whosTurn, Move move) {
        System.out.println(whosTurn + " makes move " + move + "\n");
    }
    /**
     * prints out a string representation of the game board and whos turn is it
     *
     */
    private void report() {

        String s = othello.getBoardString() + OthelloBoard.P1 + ":"
                + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
                + othello.getWhosTurn() + " moves next";
        System.out.println(s);
    }
    /**
     * prints the final states of the game and the winner
     *
     */
    private void reportFinal() {

        String s = othello.getBoardString() + OthelloBoard.P1 + ":"
                + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2)
                + "  " + othello.getWinner() + " won\n";
        System.out.println(s);
    }
    /**
     * tests if the experiment support Ha or H0
     *
     */
    public static void main (String[] args){
        int p1wins = 0, p2wins = 0, numGames = 10000;
        for (int i = 0;i < numGames; i++){
            RandomVSRandomReport oc = new RandomVSRandomReport();
            oc.play();
            if (oc.othello.getWinner() == 'X'){
                p1wins++;
            }
            else if (oc.othello.getWinner() == 'O'){
                p2wins++;
            }

        }


        int p3wins = 0, p4wins = 0;


        for (int i = 0;i < numGames; i++){
            RandomVSRandomReport oc = new RandomVSRandomReport();
            oc.play34();
            if (oc.othello.getWinner() == 'X'){
                p3wins++;
            }
            else if (oc.othello.getWinner() == 'O'){
                p4wins++;
            }

        }

        System.out.println("Probability P1 Greedy wins=" + (float) p1wins / numGames);
        System.out.println("Probability P2 Greedy wins=" + (float) p2wins / numGames);

        System.out.println("Probability P3 Random wins=" + (float) p3wins / numGames);
        System.out.println("Probability P4 Random wins=" + (float) p4wins / numGames);
        System.out.println("Since Greedy always run on the same algorithm with no randomness");
        System.out.println("the outcome would always be the same");
        System.out.println("The random vs random results show that there are variance between going");
        System.out.println("first vs going second.");
        System.out.println("Therefore my experiment supports Ha instead of H0");

    }

}
