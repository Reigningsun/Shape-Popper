package shapePopper;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bubble implements Shape{
	
	// ========================= Variables and Constants ===================================================================================================================
	private Circle circle;
	Color color;
	// =====================================================================================================================================================================

	
	
	
	// ======================== Constructor ================================================================================================================================
	Bubble (Point2D p, Color color, Boolean movable){
		circle = new Circle();
		circle.setCenterX( p.getX());
		circle.setCenterY( p.getY());
		circle.setRadius(10);
		circle.setFill(color);
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(3);
		this.color = color;
	}
	// =====================================================================================================================================================================

	
	
	
	// ======================== Functionality ==============================================================================================================================

	@Override
	public void move(double dx, double dy) {									// Moves this Bubble
		circle.setCenterY(circle.getCenterY()+dy);
		circle.setCenterX(circle.getCenterX()+dx);	
	}

	
	
	
	@Override
	public boolean ContainsPoint(Point2D point) {								// Determines if cursor is in this Shape
		return this.circle.contains(point);
	}

	
	
	
	@Override
	public void setColor(Color color) {											// Sets Bubbles fill color
		circle.setFill(color);
	}
	// =====================================================================================================================================================================


	
	
	// ============ Getters and Setters ====================================================================================================================================

	public Circle getCircle() {
		return circle;
	}



	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	// =====================================================================================================================================================================

}
