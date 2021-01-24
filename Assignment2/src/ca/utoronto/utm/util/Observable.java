package ca.utoronto.utm.util;

import java.util.ArrayList;
/**
 * Implement a modification of the Observer/Observable 
 * Design Pattern. See https://www.oodesign.com/observer-pattern.html 
 *
 * @author arnold
 *
 */
public class Observable {
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	public void attach(Observer o) {
		observers.add(o);
	}
	public void detach(Observer o) {
		observers.remove(o);
	}
	public void notifyObservers() {
		for(Observer o:observers) {
			o.update(this);
		}
	}
	
}
