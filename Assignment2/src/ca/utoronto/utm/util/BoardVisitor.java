package ca.utoronto.utm.util;

import ca.utoronto.utm.othello.model.OthelloBoard;



public interface BoardVisitor {
    public abstract OthelloBoard visit (OthelloBoard v);
    public abstract char visit (OthelloBoard v , int row, int col);
    public abstract int visit(OthelloBoard v, char player);
    public abstract String visit (OthelloBoard v, String s );



}
