package ca.utoronto.utm.paint;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;


public class PaintModel extends Observable implements Observer {

	public void reset(){
		for(PaintCommand c: this.commands){
			c.deleteObserver(this);
		}
		this.commands.clear();
		this.setChanged();
		this.notifyObservers();
	}

	public ArrayList<PaintCommand> getCommands(){
		return this.commands;
	}
	private ArrayList<PaintCommand> commands = new ArrayList<PaintCommand>();

	public void executeAll(GraphicsContext g) {
		for(PaintCommand c: this.commands){
			c.execute(g);
		}
	}
	
	/**
	 * We Observe our model components, the PaintCommands
	 */

	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * @param pv
	 * @param writer
	 */
	public void accept(PaintVisitorImpl pv, PrintWriter writer){
		pv.visit(this, writer);
	}
	/**
	 * @param pv
	 * @param command
	 */
	public void accept (PaintVisitorImpl pv, PaintCommand command){
		pv.visit(this, command);
		this.setChanged();
		this.notifyObservers();
	}
}
