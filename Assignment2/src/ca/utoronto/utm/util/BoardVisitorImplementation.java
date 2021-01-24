package ca.utoronto.utm.util;

import ca.utoronto.utm.othello.model.OthelloBoard;

public class BoardVisitorImplementation implements BoardVisitor {
    private OthelloBoard ob ;

    private void build(OthelloBoard v){
        this.ob = v.copy();
    }


    @Override
    public OthelloBoard visit (OthelloBoard v){
        build(v);
        return this.ob;
    }
    @Override
    public char visit (OthelloBoard v , int row, int col){
        build(v);
        return this.ob.get(row, col);
    }
    @Override
    public int visit(OthelloBoard v, char player){

        build(v);
        int count = 0;
        for (int row = 0; row < ob.getDimension(); row++) {
            for (int col = 0; col < ob.getDimension(); col++) {
                if (this.ob.get(row, col) == player)
                    count++;
            }
        }
        return count;

    }
    @Override
    public String visit (OthelloBoard v, String s1 ){
        build(v);
        String s = "";
        s += "  ";
        for (int col = 0; col < ob.getDimension(); col++) {
            s += col + " ";
        }
        s += '\n';

        s += " +";
        for (int col = 0; col < ob.getDimension(); col++) {
            s += "-+";
        }
        s += '\n';

        for (int row = 0; row < ob.getDimension(); row++) {
            s += row + "|";
            for (int col = 0; col < ob.getDimension(); col++) {
                s += this.ob.get(row,col) + "|";
            }
            s += row + "\n";

            s += " +";
            for (int col = 0; col < ob.getDimension(); col++) {
                s += "-+";
            }
            s += '\n';
        }
        s += "  ";
        for (int col = 0; col < ob.getDimension(); col++) {
            s += col + " ";
        }
        s += '\n';
        return s;

    }

}
