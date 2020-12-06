
package cs4013Project;

import java.util.ArrayList;

public class Owner {

	 private ArrayList<Property> properties = new ArrayList<Property>();
	 private String username;
	 private String password;
	 private double taxpaid;
	 private ArrayList<Property> propertyPayments;
	 private Payment payment;
	
	 /** Owner class 
	  @author Tito Etimiri 19248547 */
	 /*
	  * @param ownerid
	  * @param property
	  */
	 
	 public Owner(String username, String password) {
	        properties = new ArrayList<>();
	        ArrayList propertyPayments = new ArrayList<>();
	    }
	 
	 private String getUsername(){
		 return username;
	 }
	
	private void setPassword(String password){
		this.password = password;
	}
	
	
	public void setTaxPaid(double taxpaid) {
		this.taxpaid = taxpaid;
	}
	
	public double getTaxPaid() {
		return taxpaid;
	}
	
	 /* Owner can add properties */
	public void addProperty(Property property){
        properties.add(property);
    }   
	
	public ArrayList<Property> getProperties() {
		return properties;
	}
	
	public ArrayList<Payment> getBalance(int year) {
        for (int i = propertyPayments.size()-1; i >= 0; i--) {
            if (propertyPayments.get(i).getYear() == year ) {
                return propertyPayments.get(i).getPropertyPayments();
            }
        }
        return null;
    }

    public ArrayList<Payment> getBalance(Property property) {
        return property.getPropertyPayments();
    }

    public double getPaymentAmount(){
        TaxCalculator tax = new TaxCalculator();
        double amount = tax.getCharge(null);
        return amount;
    }
    
    public void payPropertyTax(Payment payment){
        this.payment = payment;
        double tax = getPaymentAmount();
        
    }
    
}
