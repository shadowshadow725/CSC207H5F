package ca.utoronto.utm.util;

/**
 * Implement a modification of the Observer/Observable 
 * Design Pattern. See https://www.oodesign.com/observer-pattern.html 
 *
 * @author arnold
 *
 */
public interface Observer {
	public void update(Observable o);
}
