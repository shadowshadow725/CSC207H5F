package ca.utoronto.utm.paint;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * @author Andy-Mi
 *
 */
public class PolygonCommand extends PaintCommand {
    private ArrayList<Point> points=new ArrayList<Point>();

    /**
     * @param p
     */
    public void add(Point p){
        this.points.add(p);
        this.setChanged();
        this.notifyObservers();
    }
    /**
     * removes the last point in the array list
     */
    public void removeLast(){
        if (this.points.size() > 0){
            this.points.remove(this.points.size()-1);
            this.setChanged();
            this.notifyObservers();
        }

    }

    /**
     *
     * @return points
     */
    public ArrayList<Point> getPoints(){ return this.points; }

    @Override
    public void execute(GraphicsContext g) {
        ArrayList<Point> points = this.getPoints();
        g.setStroke(this.getColor());
        for(int i=0;i<points.size()-1;i++){
            Point p1 = points.get(i);
            Point p2 = points.get(i+1);
            g.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }


    }
}
