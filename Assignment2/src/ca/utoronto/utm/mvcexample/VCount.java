package ca.utoronto.utm.mvcexample;

import ca.utoronto.utm.util.*;


import javafx.scene.control.Label;

public class VCount extends Label implements Observer {
	
	@Override
	public void update(Observable o) {
		MCounter mcounter = (MCounter)o;
		this.setText(""+mcounter.getCount());
	}
	
}
