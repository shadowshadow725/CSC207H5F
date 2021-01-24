package ca.utoronto.utm.paint;

import java.io.PrintWriter;
import java.util.ArrayList;

public class PaintVisitorImpl implements PaintVisitor{
    /* (non-Javadoc)
     * @see ca.utoronto.utm.paint.PaintVisitor#visit(ca.utoronto.utm.paint.PaintModel, java.io.PrintWriter)
     */
   
    public void visit(PaintModel pm, PrintWriter writer){
        // save
        ArrayList<PaintCommand> command = pm.getCommands();

        ArrayList<String> output = new ArrayList<>();
        for(int i = 0;i<command.size();i++) {
            if (command.get(i).getClass().equals(CircleCommand.class)) {
                CircleCommand current = (CircleCommand) command.get(i);
                output.add("Circle");
                output.add(current.toString().substring(0, current.toString().length() - 1));
                output.add("\tcenter:" + current.getCentre().toString());
                output.add("\tradius:" + current.getRadius());
                output.add("EndCircle");
            } else if (command.get(i).getClass().equals(RectangleCommand.class)) {

                RectangleCommand current = (RectangleCommand) command.get(i);
                output.add("Rectangle");
                output.add(current.toString().substring(0, current.toString().length() - 1));
                output.add("\tp1:" + current.getP1().toString());
                output.add("\tp2:" + current.getP2().toString());
                output.add("End Rectangle");

            } else if (command.get(i).getClass().equals((SquiggleCommand.class))) {

                SquiggleCommand current = (SquiggleCommand) command.get(i);
                output.add("Squiggle");
                output.add(current.toString().substring(0, current.toString().length() - 1));
                output.add("\tpoints");
                ArrayList<Point> tpoints = current.getPoints();
                for (int j = 0; j < tpoints.size(); j++) {
                    output.add("\t\tpoint:" + tpoints.get(j).toString());
                }
                output.add("\tend points");
                output.add("End Squiggle");

            } else if (command.get(i).getClass().equals((PolygonCommand.class))) {

                PolygonCommand current = (PolygonCommand) command.get(i);
                output.add("Polyline");
                output.add(current.toString().substring(0, current.toString().length() - 1));
                output.add("\tpoints");
                ArrayList<Point> tpoints = current.getPoints();
                for (int j = 0; j < tpoints.size(); j++) {
                    output.add("\t\tpoint:" + tpoints.get(j).toString());
                }
                output.add("\tend points");
                output.add("End Polyline");
            }
        }

        for(int i =0;i<output.size();i++){
            try {
                writer.write(output.get(i)+"\n");
            }
            catch (Exception e){
                System.out.println("Write Error");
            }

            System.out.println(output.get(i));
        }
    }
    /* (non-Javadoc)
     * @see ca.utoronto.utm.paint.PaintVisitor#visit(ca.utoronto.utm.paint.PaintModel, ca.utoronto.utm.paint.PaintCommand)
     */
    public void visit(PaintModel pm, PaintCommand command){
        pm.getCommands().add(command);
        command.addObserver(pm);
    }
}
