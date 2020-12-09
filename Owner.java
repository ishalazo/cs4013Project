
package cs4013Project;

import java.util.ArrayList;

public class Owner {

	  private String ownerid;
	 private String password;
	 private String ownername;
	
	 /** Owner class 
	  @author Tito Etimiri 19248547 */
	 /*
	  *  
	  * 
	  */
	 
	 public Owner(String ownername, String username, String password) {
	        
	    }
	 
	 private String getUsername(){
		 return ownerid;
	 }
	
	private void setPassword(String password){
		this.password = password;
	}
	
	 public String getOwnername() {
		return ownername;
	}
	 
	/* Owner can add properties */
	public void addProperty(Property property){
		properties.add(property);
    }   
	
	/*returns a list of properties*/
	public ArrayList<Property> getProperties() {
		return getProperties();
	}
	
        public void getBalanceStatement(){
	   
        }
	
	public void payPropertyTax(Property property, double payment){
		
	}
	
	
}
