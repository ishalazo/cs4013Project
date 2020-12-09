
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
	 
	 private String getUsername(){
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
		return getProperties();
	}
	
	/*returns a list of transactions for a particular year*/
	public void getBalanceStatement(int year){
		//System.out.println((properties).getYear());
		   
    }

	/*pays outstanding property tax*/
	//major changes still needed because????? idk im likely dumb, but how do you pay 
    public void payPropertyTax(Property property, double payment){
    	//ArrayList<TaxRecord> record = this.getTaxRecord?????
    	//for(int i = 0; i < record.size(); i++){
    	property.getMarketValue();
    	
	
    }

	//read and write into binary file. - using .- ????
   
    
}
