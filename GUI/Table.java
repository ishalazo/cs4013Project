import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/* This class is a helper class for the GUI in an attempt to modularise the GUI design
 * This class essentially creates custom gripanes 
 * 
 */
public class Table extends GridPane{
	// Columns of interest for particular situations
	private int[] owner = {0,3,4,5};
	private int[] history = {0,1,2,3,4,5};
	private int[] overdue = {0,1,2,4};
	
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
	
	public void displayPropertyDetails(ArrayList<Object> properties) {
		add(new Label("Address"),0,0);
		add(new Label("Eircode"),1,0);
		add(new Label("Location"),2,0);
		add(new Label("Market Value"),3,0);
		
		for(int i=0;i<properties.size();i++)
		{
			if(properties.get(i) instanceof Property)
			{
				add(new Label(((Property)properties.get(i)).getAddress()),0,i+1);
				add(new Label(((Property)properties.get(i)).getEircode()),1,i+1);
				add(new Label(((Property)properties.get(i)).getLocation()),2,i+1);
				add(new Label(String.valueOf(((Property)properties.get(i)).getMarketValue())),3,i+1);
			}
		}
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
	
	public void displayStatement(ArrayList<String> statements) {
		for(int i=0;i<statements.size();i++)
		{
			add(new Label(statements.get(i)),0,i);
		}
	}
	
	public void displayStatement(String display) {
			add(new Label(display),0,0);
	}
	
	public void clearGrid() {
		getChildren().clear();
	}
}
