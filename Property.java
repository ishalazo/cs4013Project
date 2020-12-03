/** Property Class
 * @author Lakeisha Lazo 19277997*/

import java.util.ArrayList;
public class Property {
	private String address, eircode, location,owner;
	private double marketValue/*, tax*/;
	private boolean principalRes;
	private ArrayList<Payment> propertyPayments;
	
	
	/**Constructs a Property Object
	 * @param address address of property
	 * @param eircode routing key of the eircode
	 * @param location type of location property is situated in
	 * @param owner property owner
	 * @param marketValue estimated market value of property
	 * @param principalRes true if the property is the principal private property of the owner
	 */
	public Property(String address, String eircode, String location, String owner, double marketValue, boolean principalRes) {
		this.address = address;
		this.eircode = eircode;
		this.location = location;
		this.owner = owner;
		this.marketValue = marketValue;
		this.principalRes = principalRes;
//		tax = 100;
		propertyPayments = new ArrayList<Payment>();
	}

	public String getEircode() {
		return eircode;
	}

	public void setEircode(String eircode) {
		this.eircode = eircode;
	}

	public String getLocation() {
		return location;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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
	public boolean isPrincipalRes() {
		return principalRes;
	}

	public void setPrincipalRes(boolean principalRes) {
		this.principalRes = principalRes;
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
		return "Property [address=" + address + ", eircode=" + eircode + ", location=" + location + ", owner=" + owner
				+ ", marketValue=" + marketValue + ", principalRes=" + principalRes + ", propertyPayments="
				+ propertyPayments + "]";
	}
	
	
	//balancing statement??
	
}
