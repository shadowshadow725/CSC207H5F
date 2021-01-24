package ca.utoronto.utm.util;
import ca.utoronto.utm.othello.model.OthelloBoard;



public interface BoardVisitable {
    public abstract OthelloBoard accept (OthelloBoard v);
    public abstract char accept (OthelloBoard v , int row, int col);
    public abstract int accept (OthelloBoard v, char player);
    public abstract String accept (OthelloBoard v, String s );
}
