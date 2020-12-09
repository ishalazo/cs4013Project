/** Property Class
 * @author Lakeisha Lazo 19277997
 */

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
			double marketValue, boolean principalResidence) {
		this.ownerID = ownerID;
		this.address = address;
		this.eircode = eircode;
		this.location = location;
		this.marketValue = marketValue;
		this.principalResidence = principalResidence;
		calculatePropertyTax();
		String[] info = {ownerID,/*Owner.getName(ownerID),*/ address, eircode, location, Double.toString(marketValue), Boolean.toString(principalResidence)};
		CSV.writeToFile("properties.csv", info);
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

	public String toString() {
		return "ownerID:" + ownerID + "\nAddress:\n" + address + "\n" + eircode 
				+ "\nLocation: " + location 
				+ "\nEstimated Market Value: €" + String.format("%.2f", marketValue) 
				+ "\nPrincipal Private Residence: " + principalResidence;
	}

	public void calculatePropertyTax() {
		//if to see which one to call compound/normal
		//filter by eircode first & then just read & write into code 
		//		if(!taxHistory.get(taxHistory.size()-1).isPaid()) {
		//			TaxRecord compoundTax = new TaxRecord(TaxCalculator.compoundTax(this), LocalDate.now(), false);
		//			taxHistory.add(compoundTax);
		//		} else {
		//			TaxRecord currentTax = new TaxRecord(TaxCalculator.calculateTax(this), LocalDate.now(), false);
		//			taxHistory.add(currentTax);
		//		}
	}

	//	balancing statement payment calculations


}
