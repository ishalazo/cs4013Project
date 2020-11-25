
package cs4013Project;

import java.util.ArrayList;

public class Owner {

	 private ArrayList<Property> properties = new ArrayList<Property>();
	 private int ownerid;
	 private String property;
	 private String balancestatement;
	
	/** Owner class 
	  @author Tito Etimiri 19248547 */
	 /*
	  * @param ownerid
	  * @param property
	  */
	
	private void setOwnerid(int ownerid) {
		this.ownerid = ownerid;

    }
	
	private int getOwnerid() {
		return this.ownerid;
	}
	
}
	 
	 /* Owner can add properties */
	public void addProperty(int ownerid, String property) {
		//add function to add property to the property arraylist
		 ArrayList<Property> properties = new ArrayList<Property>();
		 
		 properties.add("House");
		 properties.add("Mansion");
		 properties.add("Townhouse");
		 properties.add("Bungalo");
		
	}
	
	public static void PayTaxes() {
		
	}
	
	public ArrayList<Property> getProperties() {
		return properties;
	}
	
	//not too sure about this one
	public void setBalanceStatement() {
		String balancestatement = taxpaid + taxoverdue;
		 System.out.println("Balance statement: " + getTaxPaid() + getOverdue());
	}	
	public String getBalanceStatement() {
		return balancestatement;
	}
	
	
