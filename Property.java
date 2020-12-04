/** Property Class
 * @author Lakeisha Lazo 19277997*/

import java.util.ArrayList;
import java.util.Arrays;

public class Property {
	private String address, eircode, location,owner;
	private double marketValue/*, tax*/;
	private boolean principalResidence;
	private ArrayList<Payment> propertyPayments;
	private ArrayList<Payment> overdues; // -ve tax, year and owner 
	
	/**Constructs a Property Object
	 * @param address address of property
	 * @param eircode routing key of the eircode
	 * @param location type of location property is situated in
	 * @param owner property owner
	 * @param marketValue estimated market value of property
	 * @param principalResidence true if the property is the principal private property of the owner
	 */
	public Property(String owner, String address, String eircode, String location, double marketValue, boolean principalResidence) {
		this.owner = owner;
		this.address = address;
		this.eircode = eircode;
		this.location = location;
		this.marketValue = marketValue;
		this.principalResidence = principalResidence;
		propertyPayments = new ArrayList<Payment>();
		String[] info = {owner, address, eircode, location, Double.toString(marketValue), Boolean.toString(principalResidence)};
		CSV.writeToFile("owners.csv", info);
	}

	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getEircode() {
		return eircode;
	}


	public String getLocation() {
		return location;
	}


	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}
	
/*  kinda sus vvvv
	public double getTax() {
		return tax;
	}
*/
	public boolean isprincipalResidence() {
		return principalResidence;
	}

	public void setprincipalResidence(boolean principalResidence) {
		this.principalResidence = principalResidence;
	}

	public String getAddress() {
		return address;
	}

	public ArrayList<Payment> getPropertyPayments() {
		return propertyPayments;
	}

	public void setPropertyPayments(ArrayList<Payment> propertyPayments) {
		this.propertyPayments = propertyPayments;
	}

	public String toString() {
		return "Owner: " + owner + "\nAddress:\n" + address + "\n" + eircode 
				+ "\nLocation: " + location 
				+ "\nEstimated Market Value: €" + String.format("%.2f", marketValue) 
				+ "\nPrincipal Private Residence: " + principalResidence ;
	}
	
	public void overdueCheck() {
		//check if any year is missing inside propertyPayments, missing year - added to overdues, amount is -ve of compounded tax
	}

}
