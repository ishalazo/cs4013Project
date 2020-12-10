
package cs4013Project;

import java.io.*;
import java.util.ArrayList;

public class Owner {

	  private String ownerid;
	 private String password;
	 private String ownername;
	 private ArrayList<Property> properties = new ArrayList<>();
	
	 /** Owner class 
	  @author Tito Etimiri 19248547 */
	 /*
	  *  
	  * 
	  */
	 
	 public Owner(String ownername, String ownerid, String password) {
	        this.ownername = ownername;
	        this.ownerid = ownerid;
	        this.password = password;
	    }
	 
	 private String getOwnerid(){
		 return ownerid;
	 }
	
	private void setPassword(String password){
		this.password = password;
	}
	
	 public String getFullname() {
		return ownername;
	}
	 
	/* Owner can add properties */
	public void addProperty(Property property) {
		properties.add(property);
    }   
	
	/*returns a list of properties*/
	public ArrayList<Property> getProperties() {
		return properties;
	}
	
	/*returns a list of transactions for a property*/
	public void getBalanceStatement(){
		properties = Utilities.readFromFile(ownerid);
		for(int i = 0; i<properties.size(); i++){
			System.out.println(properties.get(i).length);
        }
		   
    }

	/*pays outstanding property tax*/
	public void payPropertyTax(Property property, boolean payment){
    	//read and write into binary file using ./ 
	    if (payment == false)
    		property.calculatePropertyTax(payment);
    
    }
    
}
