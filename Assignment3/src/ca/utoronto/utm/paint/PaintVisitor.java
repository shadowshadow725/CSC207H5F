package ca.utoronto.utm.paint;

import java.io.PrintWriter;

/**
 * @author Andy-Mi
 *
 */
public interface PaintVisitor {
    public void visit(PaintModel pm, PrintWriter writer);
    public void visit(PaintModel pm, PaintCommand command );

}
