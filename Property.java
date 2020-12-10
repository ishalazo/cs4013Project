/** Property Class
 * @author Lakeisha Lazo 19277997
 */
import java.time.LocalDate;
import java.util.*;

public class Property {
	private String address, eircode, location,ownerID;
	private double marketValue;
	private boolean principalResidence;
	private ArrayList<TaxRecord> taxes;

	/**Constructs a Property Object
	 * @param address address of property
	 * @param eircode routing key of the eircode
	 * @param location type of location property is situated in
	 * @param ownerID property ownerID
	 * @param marketValue estimated market value of property
	 * @param principalResidence true if the property is the principal private property of the ownerID
	 */
	public Property(String ownerID, String address, String eircode, String location, 
			double marketValue, boolean principalResidence) {
		this.ownerID = ownerID;
		this.address = address;
		this.eircode = eircode;
		this.location = location;
		this.marketValue = marketValue;
		this.principalResidence = principalResidence;
		taxes = new ArrayList<TaxRecord>();
		calculatePropertyTax(true);
		String[] info = {ownerID, address, eircode, location, Double.toString(marketValue), Boolean.toString(principalResidence)};
		Utilities.writeToFile("properties.csv", info);
	}

	/**
	 * Gets the name of the ownerID.
	 * @return Name of the ownerID.
	 */
	public String getownerID() {
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
	
	public ArrayList<TaxRecord> getTaxes() {
		return taxes;
	}

	public void calculatePropertyTax(boolean isPaid) {
		ArrayList<String[]> payments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
//		the code below sucks cause it assumes that there would always be a payment for this property in the
//		csv but that's not always the case, specially if we're calling this inside the constructor
//		that's why i have this take in a boolean as an argument so we can set it to true at the construction
//		boolean isPaid = Boolean.parseBoolean(payments.get(payments.size()-1)[Utilities.indexCol(payments.get(0),"Paid")]);
		if(!isPaid) {
			double prev = Double.parseDouble(payments.get(payments.size()-1)[Utilities.indexCol(payments.get(0),"Tax")]);
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

	public String toString() {
		return "ownerID:" + ownerID + "\nAddress:\n" + address + "\n" + eircode 
				+ "\nLocation: " + location 
				+ "\nEstimated Market Value: €" + String.format("%.2f", marketValue) 
				+ "\nPrincipal Private Residence: " + principalResidence;
	}
}
