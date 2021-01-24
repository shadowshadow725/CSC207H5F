package ca.utoronto.utm.mvcexample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CButtonPressEventHandler implements EventHandler<ActionEvent> {
	private MCounter mcounter;
	public CButtonPressEventHandler(MCounter mcounter) {
		this.mcounter=mcounter;
	}
	
	@Override
	public void handle(ActionEvent event) {
		Button source=(Button)event.getSource();
		if(source.getText()=="increment") {
			mcounter.increment();
		} else if(source.getText()=="decrement") {
			mcounter.decrement();
		}
	}
}
