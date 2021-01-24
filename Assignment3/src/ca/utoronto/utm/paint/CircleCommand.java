package ca.utoronto.utm.paint;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Andy-Mi
 *
 */

public class CircleCommand extends PaintCommand {
	private Point centre;
	private int radius;
	/**
	 * 
	 * @param centre
	 * @param radius
	 */
	
	public CircleCommand(Point centre, int radius){
		
		this.centre = centre;
		this.radius = radius;
	}
	
	public Point getCentre() { return centre; }
	public void setCentre(Point centre) { 
		this.centre = centre; 
		this.setChanged();
		this.notifyObservers();
	}
	/**
	 * @return
	 */
	public int getRadius() { return radius; }
	/**
	 * @param radius
	 */
	public void setRadius(int radius) { 
		this.radius = radius; 
		this.setChanged();
		this.notifyObservers();
	}
	/* (non-Javadoc)
	 * @see ca.utoronto.utm.paint.PaintCommand#execute(javafx.scene.canvas.GraphicsContext)
	 */
	public void execute(GraphicsContext g){
		int x = this.getCentre().x;
		int y = this.getCentre().y;
		int radius = this.getRadius();
		if(this.isFill()){
			g.setFill(this.getColor());
			g.fillOval(x-radius, y-radius, 2*radius, 2*radius);
		} else {
			g.setStroke(this.getColor());
			g.strokeOval(x-radius, y-radius, 2*radius, 2*radius);
		}
	}
}
