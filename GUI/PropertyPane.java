import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PropertyPane extends GridPane{
	public PropertyPane() {
		setVgap(12);
		setHgap(15);
		setPadding( new Insets(15,15,15,15));
	}
	
	public void insertProperties(ArrayList<String[]> properties) {
		for(int i=0;i<properties.size();i++)
		{
			String[] data = properties.get(i);
			add(new Label(data[1]),0,i+1);
			add(new Label(data[2]),1,i+1);
			add(new Label(data[3]),2,i+1);
			add(new Label(data[4]),3,i+1);
			add(new Label(data[5]),4,i+1);
		}
	}
	
	public void clearGrid() {
		getChildren().clear();
	}
}
