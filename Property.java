/** Property Class
 * @author Lakeisha Lazo 19277997
 */
import java.time.LocalDate;
import java.util.*;

public class Property {
	private String address, eircode, location,ownerID;
	private double marketValue, tax;
	private boolean principalResidence;
//	private String[] propVals;

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
		/*propVals = new String[] {
				ownerID, 
				address,
				eircode, 
				location,
				Double.toString(marketValue),
				Boolean.toString(principalResidence)
		};*/
		
		ArrayList<String[]> payments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
		payments.remove(0);
		tax = Double.parseDouble(payments.get(payments.size()-1)[4]);
		
		if(writeToCSV) { 
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
		String[] info = {ownerID, address, eircode, location, Double.toString(this.marketValue), Boolean.toString(principalResidence)};
		Utilities.writeToCell("properties.csv", marketValue, info, "Estimated Market Value");
		this.marketValue = marketValue;
//		propVals[4] = Double.toString(marketValue);
	}

	public boolean isprincipalResidence() {
		return principalResidence;
	}

	public void setprincipalResidence(boolean principalResidence) {
		String[] info = {ownerID, address, eircode, location, Double.toString(marketValue), Boolean.toString(this.principalResidence)};
		this.principalResidence = principalResidence;
		Utilities.writeToCell("properties.csv", principalResidence, info, "Principal Residence");
		
//		propVals[5] = Boolean.toString(principalResidence);
	}

	public String getAddress() {
		return address;
	}
	
	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

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
	
	public String propBalStatement() {
		ArrayList<String> data = Utilities.readFromColumn("taxPayments.csv",3);
		data.remove(0);
		int currTaxYr = Integer.parseInt(Collections.max(data));
		int initialTaxYr = Integer.parseInt(Collections.min(data));
		return propBalStatement(initialTaxYr,currTaxYr);
	}
	
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

	public String toString() {
		return String.format("Owner ID: %s\nAddress: %s \nEircode: %s\nLocation:  %s\n"
				+ "Estimated Market Value: €%.2f\nPrincipal Private Residence: %b\nTax due: €%.2f\n",
				ownerID, address, eircode, location, marketValue, principalResidence,tax);
	}
}