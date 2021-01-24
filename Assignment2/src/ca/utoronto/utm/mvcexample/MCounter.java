package ca.utoronto.utm.mvcexample;

import ca.utoronto.utm.util.*;

/**
 * Model a counter, with increment and decrement.
 * @author student
 *
 */
public class MCounter extends Observable  {
	private int count=0;
	public void increment() { 
		this.count++; 
		this.notifyObservers();
	}
	public void decrement() {
		this.count--; 
		this.notifyObservers();
	}
	public int getCount() { return this.count; }
}
