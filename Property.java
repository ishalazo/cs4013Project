/** Property Class
 * @author Lakeisha Lazo 19277997
 */
import java.time.LocalDate;
import java.util.*;

public class Property {
	private String address, eircode, location,ownerID;
	private double marketValue;
	private boolean principalResidence;

	/**Constructs a Property Object
	 * @param address address of property
	 * @param eircode routing key of the eircode
	 * @param location type of location property is situated in
	 * @param ownerID property ownerID
	 * @param marketValue estimated market value of property
	 * @param principalResidence true if the property is the principal private property of the ownerID
	 */
	public Property(String ownerID, String address, String eircode, String location, 
			double marketValue, boolean principalResidence, boolean writeToCSV) {
		this.ownerID = ownerID;
		this.address = address;
		this.eircode = eircode;
		this.location = location;
		this.marketValue = marketValue;
		this.principalResidence = principalResidence;
		
		
		if(writeToCSV) { 
			String[] info = {ownerID, address, eircode, location, Double.toString(marketValue), Boolean.toString(principalResidence)};
			Utilities.writeToFile("properties.csv", info);
			String[] c = {
					eircode, 
					ownerID, 
					Integer.toString(LocalDate.now().getYear()), 
					Double.toString(TaxCalculator.calculateTax(this)), 
					Boolean.toString(false)};
			Utilities.writeToFile("taxPayments.csv", c);
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
	 * Changes the ownerID of the property to the new ownerID.	
	 * @param ownerID Name of the new ownerID
	 */
	public void setownerID(String ownerID) {
		this.ownerID = ownerID;
	}

	/**
	 * Gets the eircode of the property.
	 * @return Returns the 
	 */
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

	public boolean isprincipalResidence() {
		return principalResidence;
	}

	public void setprincipalResidence(boolean principalResidence) {
		this.principalResidence = principalResidence;
	}

	public String getAddress() {
		return address;
	}


	public void calculateCurrentTax() {
		ArrayList<String[]> payments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
		payments.remove(0);
		boolean isPaid = Boolean.parseBoolean(payments.get(payments.size()-1)[5]);
		if(!isPaid) {
			double prev = Double.parseDouble(payments.get(payments.size()-1)[3]);
			String[] content = {
					address,
					eircode, 
					ownerID, 
					Integer.toString(LocalDate.now().getYear()), 
					Double.toString(TaxCalculator.compoundTax(this,prev)), 
					Boolean.toString(false)};
			Utilities.writeToFile("taxPayments.csv", content);
		} else {
			String[] c = {
					address,
					eircode, 
					ownerID, 
					Integer.toString(LocalDate.now().getYear()), 
					Double.toString(TaxCalculator.calculateTax(this)), 
					Boolean.toString(false)};
			Utilities.writeToFile("taxPayments.csv", c);
		}
	}
	
	public String propBalStatement() {
		ArrayList<String[]> propPayments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
		String output = "";
		for(int i = 1; i < propPayments.size(); i++) {
			String[] p = propPayments.get(i);
			if(Boolean.parseBoolean(p[5])) {
				output += ", paid";
			} else if (Integer.parseInt(p[3]) == LocalDate.now().getYear() && !Boolean.parseBoolean(p[5])) {
				output += ", outstanding";
			} else {
				output += ", overdue";
			}
		}
		return output;
	}

	public String toString() {
		return "Owner ID:" + ownerID + "\nAddress:" + address + "\nEircode: " + eircode 
				+ "\nLocation: " + location 
				+ "\nEstimated Market Value: €" + String.format("%.2f", marketValue) 
				+ "\nPrincipal Private Residence: " + principalResidence;
	}
}
