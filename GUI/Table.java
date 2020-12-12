import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Table extends GridPane{
	
	int[] property = {1,2,3,4,5};
	int[] owner = {0,3,4,5};
	int[] history = {0,1,2,3,4,5};
	int[] overdue = {0,1,2,4};
	
	public Table() {
		setVgap(12);
		setHgap(15);
		setPadding( new Insets(15,15,15,15));
		setAlignment(Pos.CENTER);
	}
	
	private void displayDetails(ArrayList<String[]> details,int[] columns) {
		for(int i=0;i<details.size();i++)
		{
			String[] data = details.get(i);
			for(int j=0;j<columns.length;j++)
			{
				add(new Label(data[columns[j]]),j,i);
			}
		}
	}
	
	public void displayPropertyDetails(ArrayList<String[]> properties) {
		displayDetails(properties,property);
	}
	
	public void displayOwnerHistory(ArrayList<String[]> ownerData) {
		displayDetails(ownerData,owner);
	}
	
	public void displayPropertyHistory(ArrayList<String[]> propertyData) {
		displayDetails(propertyData,history);
	}
		
	public void displayOverdues(ArrayList<String[]> overdues) {
		displayDetails(overdues,overdue);
	}
	
	public void clearGrid() {
		getChildren().clear();
	}
}
