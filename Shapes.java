import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Shapes extends Application

{	//attribute for side number is declared as int type
	private int sideNumber;
	
	// method checks validity of the input, returns boolean 
	public static boolean isValid(int sides) 
	{
		return (9 > sides && sides > 3);
	}
	
	// generates Cartesian points based on given side numbers.
	public static Double[] setPolygonSides(int sides) 
	{
		double radius = 70; //arbitrary number to determine the size of the shape
		Double[] sidePoints = new Double[sides*2];
	    final double angleStep = Math.PI * 2 / sides;
	    double angle = 0; // point starts directly beneath the center point
	    for (int i = 0; i < sides; i++, angle += angleStep) {
	    	sidePoints[i*2] = Math.sin(angle) * radius; //X-coordinate of a given point
	    	sidePoints[i*2+1] = Math.cos(angle) * radius; //Y-coordinate of a given point
	    }
		return sidePoints;
	}
	
	// method sets the side number of a shape after the input is validated with isValid method.
	public void setSide(int inputSideNumber) 
	{
		sideNumber = inputSideNumber;
	}
	
	// method returns the current side number of a shape.
	public int getSide() 
	{
		return sideNumber;
	}
	
	@Override // Not mandatory here but included as a good practice rule.
	public void start (Stage stage)
	{
		// create and configure text field for an input, valid inputs integers between 4 and 8         
		TextField sideField = new TextField();         
		sideField.setMaxWidth(50);
		sideField.setMaxHeight(50);
		sideField.setFont(Font.font("Arial", FontWeight.BOLD,20));
		
		// create and configure Labels for the text fields         
		Label shapeLabel = new Label("Enter side number of shape: ");         
		shapeLabel.setTextFill(Color.BLACK);         
		shapeLabel.setFont(Font.font("Arial", FontWeight.BOLD,16));
		
		// label shows number of sides in the currently displayed shape
		Label shapeNumber = new Label();         
		shapeNumber.setTextFill(Color.GREEN);         
		shapeNumber.setFont(Font.font("Arial", 16));
		
		
		// create generate, decrement and increment buttons
		Button generateButton = new Button("Generate\n  shape");
		generateButton.setTextFill(Color.RED);
		generateButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		
		Button incrementButton = new Button();         
		incrementButton.setText("Increment by 1");
		incrementButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		Button decrementButton = new Button();         
		decrementButton.setText("Decrement by 1");
		decrementButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		// create and configure a Polygon
		Polygon shape = new Polygon();
		
		// this button generates a shape if the in put is valid, if not displays informative error message
		generateButton.setOnAction( e ->
		{   
			String entered = new String(sideField.getText());
			
			// try to convert the input into integer if not possible then input is wrong, so throw error via catch.
			try 
			{
			
			// As input is given as a string try to parse it into integer, if fails it means entry has non-numeric charters
			int enteredValue = Integer.parseInt(entered);
			
			//check if the input is valid else pop up self explanatory error messages
			if(!isValid(enteredValue))
			{
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Your entered " + enteredValue +", which is an invalid entry :(");
				if (enteredValue < 4)
				{
					errorAlert.setContentText("Reason:\nBecause it is smaller that the lowest acceptible number 4. \n\n"
							+ "Action: \nPlease only enter numbers between 4 and 8, inclusive.");
				}
				else if (enteredValue > 8)
				{
					errorAlert.setContentText("Reason:\nBecause it is larger that the largest acceptible number 8. \n\n"
							+ "Action: \nPlease only enter numbers between 4 and 8, inclusive.");
				}
				
				errorAlert.showAndWait();
			}
			else
			{
				// means input is valid, so clear the old shape. 
				shape.getPoints().clear();
				
				//and update the side number
				setSide(enteredValue);
				
				// get the shape's x, y coordinates
				shape.getPoints().addAll(setPolygonSides(enteredValue));
				
				// for clarity color the outer of the shape into black
				shape.setStroke(Color.BLACK);
				
				// display the number of sides along with the shape
				shapeNumber.setText("Shape has " + getSide() +  " sides");	
				
				// if side number is odd green else yellow
				if (enteredValue%2 ==1) 
				{
					shape.setFill(Color.GREEN);
				}
				else
				{
					shape.setFill(Color.YELLOW);
				}
			}
			}
			// if the parsing of the input to the integer fails the following catch code is executed
			catch(Exception Invalid_Entry) 
			{
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Your entery \"" + entered + "\" is invalid :(");
				if (entered.isEmpty())
				{
					errorAlert.setContentText("Reason:\nBecause you did not make any entry. \n\n"
							+ "Action: \nPlease enter numbers between 4 and 8, inclusive.");
				}
				else
				{
					errorAlert.setContentText("Reason:\nYour entry contains non-Numeric character(s). \n\n"
							+ "Action: \nPlease only enter numbers between 4 and 8, inclusive.");
				}
				errorAlert.showAndWait();
			}
			}                                     
		);
		// this button increments shape number and shows new shape while it is valid, if not displays informative error message
		incrementButton.setOnAction( e ->
		{   
			// Increment the value by 1 
			int enteredValue = getSide()+1;
			
			//check if the new value is valid
			if(!isValid(enteredValue))
			{
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("You incremented too much. " + enteredValue +" is an invalid :(");
	
					errorAlert.setContentText("Reason:\nBecause it is larger that the largest acceptiple number 8. \n\n"
							+ "Action: \nPlease do not increment above 8.");
				errorAlert.showAndWait();
			}
			else 
			{   
				// means input is valid, so clear the old shape. 
				shape.getPoints().clear();
				
				//and update the side number
				setSide(enteredValue);
				
				// get the new shape's x, y coordinates
				shape.getPoints().addAll(setPolygonSides(enteredValue));
				// for clarity color the outer of the shape into black
				shape.setStroke(Color.BLACK);
				
				// display the number of sides along with the shape
				shapeNumber.setText("Shape has " + getSide() +  " sides");
				
				// if the side number is odd, set the shape color to green else to yellow
				if (enteredValue%2 ==1) 
				{
					shape.setFill(Color.GREEN);
				}
				else
				{
					shape.setFill(Color.YELLOW);
				}
			}
		}
		);
		// this button decrements shape number and shows new shape while it is valid, if not displays informative error message
		decrementButton.setOnAction( e ->
		{   
			// Decrement the value by 1 
			int enteredValue = getSide()-1;
			
			//check if the new value is valid
			if(!isValid(enteredValue))
			{
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("You decremented too much. " + enteredValue +" is an invalid :(");
				errorAlert.setContentText("Reason:\nBecause it is smaller that the lowest acceptible number 4. \n\n"
						+ "Action: \nPlease do not decrement below 4.");
				
				errorAlert.showAndWait();
			}
			else
			{
				// means input is valid, so clear the old shape.
				shape.getPoints().clear();
				
				//and update the side number
				setSide(enteredValue);
				
				// get the new shape's x, y coordinates
				shape.getPoints().addAll(setPolygonSides(enteredValue));
				
				// for clarity color the outer of the shape into black
				shape.setStroke(Color.BLACK);
				
				// display the number of sides along with the shape
				shapeNumber.setText("Shape has " + getSide() +  " sides");
				
				// if side number is odd green else yellow
				if (enteredValue%2 ==1) 
				{
					shape.setFill(Color.GREEN);
				}
				else
				{
					shape.setFill(Color.YELLOW);
				}
			}
		}
		);
		
		// create and configure an HBox for the shapeLabel, sideField and generateButton
		HBox topInputComponents = new HBox(10);
		topInputComponents.setAlignment(Pos.CENTER);        
		topInputComponents.getChildren().addAll(shapeLabel, sideField, generateButton);  

		// create and configure an HBox for the increment and decrement buttons
		HBox buttonGroup = new HBox(10);
		buttonGroup.setAlignment(Pos.CENTER);        
		buttonGroup.getChildren().addAll(incrementButton, decrementButton);  
		
		// create and configure a vertical container to hold all the components         
		VBox root = new VBox(25);         
		root.setAlignment(Pos.CENTER);        
		root.getChildren().addAll(topInputComponents, shape, shapeNumber, buttonGroup); 
		
		// create and configure a  scene         
		Scene scene = new Scene(root, 450, 350, Color.ORANGE); 
	
		// add the scene to the stage and set the title         
		stage.setScene(scene);         
		stage.setTitle("Shape maker"); 
		
		// finally show the stage         
		stage.show();  
		} 

	public static void main(String[] args)     
	{         
		launch(args);
		System.out.println("Launched successfully!"); // To prove the command line launch
	}  
	
}
