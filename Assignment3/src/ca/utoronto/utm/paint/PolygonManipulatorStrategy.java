package ca.utoronto.utm.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class PolygonManipulatorStrategy extends ShapeManipulatorStrategy{
    private PolygonCommand polygonCommand;

    /**
     * @param paintModel
     */
    PolygonManipulatorStrategy(PaintModel paintModel) {
        super(paintModel);
        this.polygonCommand = new PolygonCommand();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.polygonCommand.removeLast();
        Point p = new Point((int)e.getX(), (int)e.getY());
        this.polygonCommand.add(p);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton().toString() == "PRIMARY"){
            this.polygonCommand.add(new Point((int)e.getX(), (int)e.getY()));
            this.addCommand(polygonCommand);
        }
        else {
            this.polygonCommand = new PolygonCommand();
        }


    }


}
