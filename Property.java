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
			
			//generate the current years tax cause it's registered in 2020
			String[] c = {
				eircode, 
				ownerID, 
				Integer.toString(LocalDate.now().getYear()), 
				Double.toString(TaxCalculator.calculateTax(this)), 
				Boolean.toString(false)};
			Utilities.writeToFile("taxPayments.csv", c);
		}
	}

//	private boolean insideCSV(String[] info) {
//		ArrayList<String[]> CSVprops = Utilities.readFromFile("properties.csv");
//		boolean inside = false;
//		for(int i = 1; i < CSVprops.size(); i++) {
//			String[] row = CSVprops.get(i);
//			if(ownerID.equals(row[0]) && address.equalsIgnoreCase(row[1]) && eircode.equalsIgnoreCase(row[2]) && location.equalsIgnoreCase(row[3]) 
//					&& marketValue == Double.parseDouble(row[4]) && principalResidence == Boolean.parseBoolean(row[5])) {
//				inside = true;
//				break;
//			}
//		}
//		return inside;
//	}


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


	public void calculateCurrentTax(boolean isPaid) {
		ArrayList<String[]> payments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
//		the code below sucks cause it assumes that there would always be a payment for this property in the
//		csv but that's not always the case, specially if we're calling this inside the constructor
//		that's why i have this take in a boolean as an argument so we can set it to true at the construction
//		boolean isPaid = Boolean.parseBoolean(payments.get(payments.size()-1)[Utilities.indexCol(payments.get(0),"Paid")]);
		if(!isPaid) {
			double prev = Double.parseDouble(payments.get(payments.size()-1)[3/*Utilities.indexCol(payments.get(0),"Tax")*/]);
			String[] content = {
					eircode, 
					ownerID, 
					Integer.toString(LocalDate.now().getYear()), 
					Double.toString(TaxCalculator.compoundTax(this,prev)), 
					Boolean.toString(false)};
			Utilities.writeToFile("taxPayments.csv", content);
		} else {
			String[] c = {
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
			output += p[2] + " Tax: €" + p[3]+ ((Boolean.parseBoolean(p[4]))? ", paid\n":", overdue\n");
			if(Integer.parseInt(p[2]) == LocalDate.now().getYear() && !Boolean.parseBoolean(p[4])) {
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
