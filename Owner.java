
package cs4013Project;

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
	
	/*returns a list of transactions for a particular year*/
	public void getBalanceStatement(int year){
		 Utilities.readFromFile("taxPayments.csv");
		//Utilities.readFromColumn("taxPayments.csv", year);
    }

	/*pays outstanding property tax*/
   public void payPropertyTax(Property property){
    	 for (String[] properties : Utilities.filter(properties, "Eircode", property.getEircode())) {
		         Utilities.readFromColumn("taxPayments.csv", 1)
			 Utilities.writeToCell("taxPayments.csv", true, null, "Paid");
	 }
    }
    
    
}
