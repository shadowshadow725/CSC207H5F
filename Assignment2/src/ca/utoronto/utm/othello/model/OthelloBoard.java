package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.BoardVisitorImplementation;
import ca.utoronto.utm.util.BoardVisitable;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 *
 * Othello makes use of the OthelloBoard.
 *
 * @author arnold
 *
 */


public class OthelloBoard implements BoardVisitable {
    public BoardVisitorImplementation visitor = new BoardVisitorImplementation();
    public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
    private int dim = 8;
    private char[][] board;

    public OthelloBoard(int dim) {
        this.dim = dim;
        board = new char[this.dim][this.dim];
        for (int row = 0; row < this.dim; row++) {
            for (int col = 0; col < this.dim; col++) {
                this.board[row][col] = EMPTY;
            }
        }
        int mid = this.dim / 2;
        this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
        this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
    }

    /**
     *
     * @return a copy of this
     */
    public OthelloBoard copy() {
        OthelloBoard ob = new OthelloBoard(this.dim);
        for (int row = 0; row < this.dim; row++) {
            for (int col = 0; col < this.dim; col++) {
                ob.board[row][col] = this.board[row][col];
            }
        }
        return ob;
    }



    /**
     *
     * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
     * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
     * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
     */
    public char get(int row, int col) {
        if(this.validCoordinate(row, col))return this.board[row][col];
        else return EMPTY;
    }

    public int getDimension() {
        return this.dim;
    }

    /**
     *
     * @param player either P1 or P2
     * @return P2 or P1, the opposite of player
     */
    public static char otherPlayer(char player) {
        if (player == P1)
            return P2;
        if (player == P2)
            return P1;
        return EMPTY;
    }


    /**
     *
     * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
     * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
     * @return whether (row,col) is a position on the board. Example: (6,12) is not
     *         a position on the board with dim 8x8.
     */
    private boolean validCoordinate(int row, int col) {
        return 0 <= row && row < this.dim && 0 <= col && col < this.dim;
    }
    /**
     * flip all other player tokens to player, starting at (row,col) in direction
     * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
     * result in a flip to OOOO
     *
     * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
     * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
     * @param drow   the row direction, in {-1,0,1}
     * @param dcol   the col direction, in {-1,0,1}
     * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
     *               flip to.
     * @return the number of other player tokens actually flipped, -1 if this is not
     *         a valid move in this one direction, that is, EMPTY or the end of the
     *         board is reached before seeing a player token.
     */
    private int flip(int row, int col, int drow, int dcol, char player) {
        if (!validCoordinate(row, col))
            return -1;
        if (this.board[row][col] == EMPTY)
            return -1;
        if (this.board[row][col] == player)
            return 0;
        if (this.board[row][col] == this.otherPlayer(player)) {
            int numChanged = this.flip(row + drow, col + dcol, drow, dcol, player);
            if (numChanged >= 0) {
                this.board[row][col] = player;
                return numChanged + 1;
            } else {
                return numChanged;
            }
        }
        return -1; // Should not get here!
    }

    /**
     * Check if there is an alternation of P1 next to P2, starting at (row,col) in
     * direction (drow,dcol). That is, starting at (row,col) and heading in
     * direction (drow,dcol), you encounter a sequence of at least one P1 followed
     * by a P2, or at least one P2 followed by a P1. The board is not modified by
     * this method. Why is this method important? If
     * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
     * assuming that square is EMPTY, is a valid move, resulting in a collection of
     * P2 being flipped.
     *
     * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
     * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
     * @param drow the row direction, in {-1,0,1}
     * @param dcol the col direction, in {-1,0,1}
     * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
     *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
     *         alternation
     */
    private char alternation(int row, int col, int drow, int dcol) {
        if (drow == 0 && dcol == 0) return EMPTY;
        char firstToken = this.get(row,col);
        while (true) {
            char nextToken = this.get(row,col);
            if(nextToken != P1 && nextToken !=P2) return EMPTY;
            if(nextToken == this.otherPlayer(firstToken)) return nextToken;
            row += drow; col += dcol;
        }
    }

    /**
     * Return which player has a move (row,col) in direction (drow,dcol).
     *
     * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
     * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
     * @param drow the row direction, in {-1,0,1}
     * @param dcol the col direction, in {-1,0,1}
     * @return P1,P2,EMPTY
     */
    private char hasMove(int row, int col, int drow, int dcol) {
        if (!this.validCoordinate(row, col) || this.board[row][col] != this.EMPTY)
            return this.EMPTY;
        return this.alternation(row + drow, col + dcol, drow, dcol);
    }

    /**
     *
     * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
     *         neither do.
     */
    public char hasMove() {
        char retVal = EMPTY;
        for (int row = 0; row < this.dim; row++) {
            for (int col = 0; col < this.dim; col++) {
                for (int drow = -1; drow <= 1; drow++) {
                    for (int dcol = -1; dcol <= 1; dcol++) {
                        if (drow == 0 && dcol == 0)
                            continue;
                        char p = this.hasMove(row, col, drow, dcol);
                        if (p == P1 && retVal == P2)
                            return BOTH;
                        if (p == P2 && retVal == P1)
                            return BOTH;
                        if (retVal == EMPTY)
                            retVal = p;
                    }
                }
            }
        }
        return retVal;
    }

    /**
     * Make a move for player at position (row,col) according to Othello rules,
     * making appropriate modifications to the board. Nothing is changed if this is
     * not a valid move.
     *
     * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
     * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
     * @param player P1 or P2
     * @return true if player moved successfully at (row,col), false otherwise
     */
    public boolean move(int row, int col, char player) {
        if (!validCoordinate(row, col))
            return false;
        if (this.board[row][col] != EMPTY)
            return false;

        int numChangedTotal = 0;

        for (int drow = -1; drow <= 1; drow++) {
            for (int dcol = -1; dcol <= 1; dcol++) {
                if (drow == 0 && dcol == 0)
                    continue;
                int numChanged = flip(row + drow, col + dcol, drow, dcol, player);
                if (numChanged >= 0)
                    numChangedTotal += numChanged;
            }
        }
        if (numChangedTotal > 0) {
            this.board[row][col] = player;
            return true;
        }
        return false;
    }


    // Completely unnecessary code

    @Override
    public OthelloBoard accept (OthelloBoard v){
        return this.visitor.visit(v);
    }
    @Override
    public char accept (OthelloBoard v , int row, int col){
        return this.visitor.visit(v, row, col);

    }
    @Override
    public int accept (OthelloBoard v, char player){
        return this.visitor.visit(v, player);
    }
    @Override
    public String accept (OthelloBoard v, String s ){
        return this.visitor.visit(v, s);
    }


}


