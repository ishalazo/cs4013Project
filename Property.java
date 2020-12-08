/** Property Class
 * @author Lakeisha Lazo 19277997*/

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Property {
	private String address, eircode, location,owner;
	private double marketValue;
	private boolean principalResidence;
	private LocalDate dateRegistered; //?????????????
	
	/**Constructs a Property Object
	 * @param address address of property
	 * @param eircode routing key of the eircode
	 * @param location type of location property is situated in
	 * @param owner property owner
	 * @param marketValue estimated market value of property
	 * @param principalResidence true if the property is the principal private property of the owner
	 */
	public Property(String owner, String address, String eircode, String location, 
			double marketValue, boolean principalResidence, LocalDate dateRegistered) {
		this.owner = owner;
		this.address = address;
		this.eircode = eircode;
		this.location = location;
		this.marketValue = marketValue;
		this.principalResidence = principalResidence;
		this.dateRegistered = dateRegistered;
		calculatePropertyTax();
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
	
	public boolean isprincipalResidence() {
		return principalResidence;
	}

	public void setprincipalResidence(boolean principalResidence) {
		this.principalResidence = principalResidence;
	}

	public String getAddress() {
		return address;
	}
	
	public LocalDate getDateRegistered() {
		return dateRegistered;
	}

	public String toString() {
		return "Owner:" + owner + "\nAddress:\n" + address + "\n" + eircode 
				+ "\nLocation: " + location 
				+ "\nEstimated Market Value: €" + String.format("%.2f", marketValue) 
				+ "\nPrincipal Private Residence: " + principalResidence;
	}
	
	public void calculatePropertyTax() {
		//if to see which one to call compound/normal
		// in compound, update the previous year, merge(????) previous years 
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
