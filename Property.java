/** Property Class
 * @author Lakeisha Lazo 19277997
 */
import java.time.LocalDate;
import java.util.*;

public class Property {
	private String address, eircode, location,ownerID;
	private double marketValue, tax;
	private boolean principalResidence;
	String[] info;
//	private String[] propVals;

	/**Constructs a Property Object
	 * @param address address of property
	 * @param eircode eircode of t he property
	 * @param location type of location property is situated in
	 * @param ownerID owner's ID
	 * @param marketValue estimated market value of property
	 * @param principalResidence true if the property is the principal private property of the ownerID
	 * @param writeToCSV checks if you need to write to CSV or not
	 */
	public Property(String ownerID, String address, String eircode, String location, 
			double marketValue, boolean principalResidence, boolean writeToCSV) {
		this.ownerID = ownerID;
		this.address = address;
		this.eircode = eircode;
		this.location = location;
		this.marketValue = marketValue;
		this.principalResidence = principalResidence;
		
		ArrayList<String[]> payments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
		payments.remove(0);
		if(writeToCSV || payments.size() == 0) { 
			String[] info = {ownerID, address, eircode, location, Double.toString(marketValue), Boolean.toString(principalResidence)};
			Utilities.writeToFile("properties.csv", info);
			tax = TaxCalculator.calculateTax(this);
			String[] c = {
					address,
					eircode, 
					ownerID, 
					Integer.toString(LocalDate.now().getYear()), 
					Double.toString(tax), 
					Boolean.toString(false)};
			Utilities.writeToFile("taxPayments.csv", c);
		}else {
			tax = Double.parseDouble(payments.get(payments.size()-1)[4]);
		}
	}

	/**
	 * Gets the name of the ownerID.
	 * @return Name of the ownerID.
	 */
	public String getOwnerID() {
		return ownerID;
	}
	
	/**
	 * Changes owner of the propery
	 * @param ownerID the ownerID to set
	 */
	public void setOwnerID(String ownerID) {
		info = Utilities.filter(Utilities.readFromFile("properties.csv"), "Eircode", eircode).get(1);
		this.ownerID = ownerID;
		Utilities.writeToCell("properties.csv", ownerID, info, "Owner_id");
	}

	/**
	 * Gets the eircode of the property.
	 * @return Property eircode
	 */
	public String getEircode() {
		return eircode;
	}
	
	/**
	 * Gets the Location of the property
	 * @return Property's location category
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Gets the estimated market value of the property.
	 * @return Property estimated market value
	 */
	public double getMarketValue() {
		return marketValue;
	}
	
	/**
	 * Sets the estimated market value to a new price
	 * @param marketValue the market value to set
	 */
	public void setMarketValue(double marketValue) {
		info = Utilities.filter(Utilities.readFromFile("properties.csv"), "Eircode", eircode).get(1);
		Utilities.writeToCell("properties.csv", marketValue, info, "Estimated Market Value");
		this.marketValue = marketValue;
	}
	
	/**
	 * @return if the property is the principal private residence or not
	 */
	public boolean isprincipalResidence() {
		return principalResidence;
	}
	
	/**
	 * sets the principal private residence
	 * @param principalResidence the value to set
	 */
	public void setprincipalResidence(boolean principalResidence) {
		info = Utilities.filter(Utilities.readFromFile("properties.csv"), "Eircode", eircode).get(1);
		this.principalResidence = principalResidence;
		Utilities.writeToCell("properties.csv", principalResidence, info, "Principal Residence");
	}
	
	/**
	 * get address of property
	 * @return address of the property
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * get property's current tax due
	 * @return the tax ddue for the year
	 */
	public double getTax() {
		return tax;
	}
	
	/**
	 * Calculates the tax due for this property for the current year
	 */
	public void calculateCurrentTax() {
		ArrayList<String[]> payments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
		payments.remove(0);
		boolean isPaid = Boolean.parseBoolean(payments.get(payments.size()-1)[5]);
		if(!isPaid) {
			double prev = Double.parseDouble(payments.get(payments.size()-1)[4]);
			tax = TaxCalculator.compoundTax(this,prev);
			String[] content = {
					address,
					eircode, 
					ownerID, 
					Integer.toString(LocalDate.now().getYear()), 
					Double.toString(tax), 
					Boolean.toString(false)};
			Utilities.writeToFile("taxPayments.csv", content);
		} else {
			tax = TaxCalculator.calculateTax(this);
			String[] c = {
					address,
					eircode, 
					ownerID, 
					Integer.toString(LocalDate.now().getYear()), 
					Double.toString(tax), 
					Boolean.toString(false)};
			Utilities.writeToFile("taxPayments.csv", c);
		}
	}
	
	/**
	 * Get the property's entire balamcing statement
	 * @return
	 */
	public String propBalStatement() {
		ArrayList<String> data = Utilities.readFromColumn("taxPayments.csv",3);
		data.remove(0);
		int currTaxYr = Integer.parseInt(Collections.max(data));
		int initialTaxYr = Integer.parseInt(Collections.min(data));
		return propBalStatement(initialTaxYr,currTaxYr);
	}
	
	/**
	 * Get the property's balance between certain years
	 * @param firstYr one end of the year range 
	 * @param secondYr the other end of the range
	 * @return the balancing statement between these years
	 */
	public String propBalStatement(int firstYr, int secondYr) {
		if(firstYr>secondYr) {
			int temp = firstYr;
			firstYr = secondYr;
			secondYr = temp;
		}
		ArrayList<String[]> propPayments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
		String output = eircode + "\n";
		for(int i = 1; i < propPayments.size(); i++) {
			String[] p = propPayments.get(i);
			int propYr = Integer.parseInt(p[3]);
			if(firstYr <= propYr && propYr <= secondYr) {
				output += String.format("%s Tax: €%.2f",p[3],Double.parseDouble(p[4]));
				if(Boolean.parseBoolean(p[5])) {
					output += ", Paid\n";
				} else if (Integer.parseInt(p[3]) == LocalDate.now().getYear() && !Boolean.parseBoolean(p[5])) {
					output += ", Outstanding\n";
				} else {
					output += ", Overdue\n";
				}
			}
		}
		return output;
	}
	
	/**
	 * Converts property values to a string
	 * @return String of the property values
	 */
	public String toString() {
		return String.format("Owner ID: %s\nAddress: %s \nEircode: %s\nLocation:  %s\n"
				+ "Estimated Market Value: €%.2f\nPrincipal Private Residence: %b\nTax due: €%.2f\n",
				ownerID, address, eircode, location, marketValue, principalResidence,tax);
	}
}
