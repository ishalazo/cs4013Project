
package cs4013Project;

import java.util.ArrayList;

public class Owner {

	 private ArrayList<Property> properties = new ArrayList<Property>();
	 private int ownerid;
	 private double taxpaid;
	 private ArrayList<Property> payments;
	
	 /** Owner class 
	  @author Tito Etimiri 19248547 */
	 /*
	  * @param ownerid
	  * @param property
	  */
	 
	 public Owner() {
	        properties = new ArrayList<>();
	        ArrayList payments = new ArrayList<>();
	    }
	 
	 private void setOwnerid(int ownerid) {
			this.ownerid = ownerid;

	    }
		
		private int getOwnerid() {
			return this.ownerid;
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
	
	//fix
	public double getBalance(int year) {
        for (int i = payments.size()-1; i >= 0; i--) {
            if (payments.get(i).getYear() == year ) {
                return payments.get(i).getAmount();
            }
        }
        return -1;
    }

    //fix 
    public double getBalance(Property property) {
        return property.getAmont();
    }
    
    //fix
    public double getPaymentAmount(){
        TaxCalculator tax = new TaxCalculator();
        double amount = tax.getCharge();
        return amount;
    }
    
    public void payPropertyTax(double payment){
        taxpaid = payment;
        double tax = toPay();
        
    }
    
}
