package shapePopper;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public interface Shape {
	public void move (double dx, double dy);
	public boolean ContainsPoint(Point2D point);
	public void setColor (Color color);
}
