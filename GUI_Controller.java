package shapePopper;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI_Controller extends Application {

	// ========================= Variables and Constants ===================================================================================================================
	AnchorPane root;													// Anchor pane
	Point2D clickPoint;													// Current cursor position
	Point2D lastPosition = null;												// Tracks previous position of cursor
	Shape activeComponent;													// Object beneath cursor 
	Shape objAtRelease;													// Object beneath cursor and beneath activeComponent
	Boolean inDragMode = false;												// Stores current state of dragging
	ArrayList<Shape> components = new ArrayList<Shape>();									// Stores all Shapes currently in scene
	// =====================================================================================================================================================================
	
	
	
	// ========================= Mouse Handling ============================================================================================================================
	
	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>(){
		@Override
		public void handle(MouseEvent mouseEvent){
			clickPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
			String eventName = mouseEvent.getEventType().getName();
			
			if(!inDragMode){											// While not in drag mode the active component is 
				activeComponent = getCurrentShape();								// whatever is under the cursor
			}
			
			if (inDragMode){											// Stores object under cursor that is not the 
				objAtRelease = getObjAtRelease();								// activeComponent during drag
			}
			
			
			
			
			switch (eventName){
			
			case("MOUSE_DRAGGED"):											// Calculates and initiates Shape movement during 
				if(lastPosition != null){									// mouse drag event
					double deltaX = clickPoint.getX()-lastPosition.getX();
					double deltaY = clickPoint.getY()-lastPosition.getY();
					activeComponent.move(deltaX, deltaY);						
				}
				inDragMode = true;
				break;
			
				
			case("MOUSE_RELEASED"):																 
				if (inDragMode){										// Determine what to do with dragged Shape
					objAtRelease = getObjAtRelease();							// Determine what Shape is beneath the activeComponent
					addToContainerIfApplicable(objAtRelease, activeComponent);				// Adds Shape to a container if applicable
					
					objAtRelease = getObjAtRelease(); 							// Required to select objects that are layered
					removeFromContainerIfApplicable(objAtRelease, activeComponent);				// Removes Shape from containers when it is applicable
					
				} else {											// Creates new Shapes based on mouse button pressed
					if (getCurrentShape() == null){
						if (mouseEvent.getButton() == MouseButton.PRIMARY){				// Creates a black Bubble when left mouse button pressed
							Bubble bubble = new Bubble(clickPoint, Color.BLACK, true);
							addBubble(bubble);							// Adds the bubble to scene and starts mouse handling
						} else {
							Container container = new Container(clickPoint, randomColor(), true);	// Creates a randomly colored Container
							addContainer(container);						// Adds the Container to scene and starts mouse handling
						}
					}
					activeComponent = null;									// Clears activeComponent variable
				}
			
				inDragMode = false;										// Indicates drag event has ended
				break;
			
				
			case("MOUSE_PRESSED"):
				inDragMode = false;										// Indicates drag event has ended
				break;
				

			}
			lastPosition = clickPoint;										// Updates lastPosition for future movement
		}
	};
	// ===================================================================================================================================================================================
	
	
	
	
	// =========================== Return Shape ==========================================================================================================================================

	private Shape getCurrentShape(){											// Returns the object under the mouse cursor
		activeComponent = null;
		for(Shape thing : components){
			if (thing.ContainsPoint(clickPoint) && thing != activeComponent){					// Returns the first non-null Shape in components
				activeComponent = thing;									// that is at click point
				break;
			}
		}
		return activeComponent;
	}
	


	
	private Shape getObjAtRelease(){											// Returns object under the currently activeComponent
		objAtRelease = null;												// Does not set activeComponent to null, allowing it 
		for (Shape thing : components){											// Find Shape at this click point that isn't the activeComponent
			if (thing.ContainsPoint(clickPoint) && thing != activeComponent){
				objAtRelease = thing;
				break;
			}
		}
		return objAtRelease;
	}
	// =====================================================================================================================================================================
	
	
	
	
	// ========================= Generates a random color for shapes =======================================================================================================
	
	private Color randomColor(){												// returns slightly transparent random color
		Random rand = new Random();
		int red = rand.nextInt(255);
		int green = rand.nextInt(255);
		int blue = rand.nextInt(255);
		
		return Color.rgb(red, green, blue, 0.5);													 
	}
	// =====================================================================================================================================================================
	
	
	
	
	// ======================== Add to or Remove from Containers ===========================================================================================================
	
	private void addToContainerIfApplicable(Shape objAtRelease, Shape activeComponent){					// If activeComponent is dropped onto a container add it
		if (objAtRelease instanceof Container && objAtRelease != activeComponent){					// Prevents infinite loop of adding a container to itself
			((Container) objAtRelease).addTo(activeComponent);
		}
	}
	
	
	
	
	private void removeFromContainerIfApplicable(Shape objAtRelease, Shape activeComponent) {				// Removes a shape from a container if it is dragged out
		if (objAtRelease == null && activeComponent != null){								// If a Shape is dragged into empty space remove it 
			for (Shape thing : components){										// from all containers
				if (thing instanceof Container){
					((Container) thing).removeFrom(activeComponent);
				}
			}
			if (activeComponent instanceof Bubble){									// Removed Bubbles revert to Black color
				activeComponent.setColor(Color.BLACK);
			}
			if (activeComponent instanceof Container){								// Removed Containers and contents revert to original color
				activeComponent.setColor(((Container) activeComponent).originalColor);
			}
		}
	}
	
	// =====================================================================================================================================================================
	
	
	
	
	// ========================= Add Shape to Scene and adds mouseHandling =================================================================================================
	
	private Bubble addBubble(Bubble bubble){
		components.add(bubble);
		root.getChildren().add(bubble.getCircle());
		
		bubble.getCircle().setOnMousePressed(mouseHandler);
		bubble.getCircle().setOnMouseDragged(mouseHandler);
		bubble.getCircle().setOnMouseReleased(mouseHandler);
		bubble.getCircle().setOnMouseClicked(mouseHandler);
		
		return bubble;
	}
	
	private Container addContainer(Container container){
		components.add(container);
		root.getChildren().add(container.getRect());
		
		container.getRect().setOnMousePressed(mouseHandler);
		container.getRect().setOnMouseDragged(mouseHandler);
		container.getRect().setOnMouseReleased(mouseHandler);
		container.getRect().setOnMouseReleased(mouseHandler);
		
		return container;
	}
	// =====================================================================================================================================================================

	
	
	
	// ========================= Create and Display Scene ==================================================================================================================
	@Override
	public void start(Stage primaryStage) throws Exception {
		root  = new AnchorPane();
		Scene scene = new Scene(root, 1500, 900);
		scene.setOnMouseReleased(mouseHandler);										// Needed to handle mouse events in empty space
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	public static void main (String[] args){
		launch(args);
	}
		

}
