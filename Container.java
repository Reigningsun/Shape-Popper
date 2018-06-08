package shapePopper;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Container implements Shape{

	// ========================= Variables and Constants ===================================================================================================================
	ArrayList<Shape> contents = new ArrayList<Shape>();							// Stores all Shapes currently contained in this object
	private Rectangle rect;											// Rectangle shape
	Color color;												// Current fill color of Rectangle
	Color originalColor;											// Original fill color of Rectangle
	// =====================================================================================================================================================================


	
	
	// ======================== Constructor ================================================================================================================================
	Container (Point2D p, Color color, Boolean moveable){
		rect = new Rectangle();
		rect.setX( p.getX() );
		rect.setY( p.getY() );
		rect.setHeight(100); 
		rect.setWidth(100);
		rect.setFill(color);
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(3);
		this.color = color;
		this.originalColor = color;
	}
	// =====================================================================================================================================================================

	
	
	
	// ======================== Functionality ==============================================================================================================================
	
	@Override
	public void move(double dx, double dy) {								// Moves Container and its contents
		rect.setX(rect.getX() + dx);
		rect.setY(rect.getY() + dy);
		
		Iterator<Shape> item = contents.iterator();						
		while (item.hasNext()){										// Moves all contents of Container along with it
			Shape content = (Shape) item.next();
			content.move(dx, dy);
		}
		
	}
	
	
	
	
	@Override
	public boolean ContainsPoint(Point2D point) {								// Determines if cursor is in this Shape
		return rect.contains(point);
	}

	
	
	
	@Override
	public void setColor(Color color) {									// Sets Rectangle fill color and changes the 
		rect.setFill(color);										// color of all contents to match
		this.color = color;
		Iterator<Shape> things = contents.iterator();
		while (things.hasNext()){
			Shape content = (Shape) things.next();
			content.setColor(color);
		}
	}

	
	
	
	public void addTo(Shape shape){										// Adds new shape to contents and sets its color 
		contents.add(shape);										// to match the containers current color
		shape.setColor(color);
	}
	
	
	
	
	public void removeFrom(Shape shape){									// Removes the Shape from this container
		contents.remove(shape);
	}
	// =====================================================================================================================================================================

	
	
	
	// ============ Getters and Setters ====================================================================================================================================
	public ArrayList<Shape> getContents() {
		return contents;
	}

	public void setContents(ArrayList<Shape> contents) {
		this.contents = contents;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	// =====================================================================================================================================================================
}
