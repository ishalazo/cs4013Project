public class TaxRecord {
	private double amount;
	private int year;
	private boolean paidOnTime;
	
	/** Payment Class for documenting payment made by owners
	 * @author Lakeisha Lazo 19277997*/
	
	
	/** Constructs a Payment object
	 * @param double amount 
	 * @param int year 
	 * @param String paidOnTime
	 * */
	public TaxRecord(double amount,int year,boolean paidOnTime) {
		this.amount = amount;
		this.year = year;
		this.paidOnTime = paidOnTime;
	}
	
	/** Set the amount of the payment*/
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/** Returns the amount paidOnTime*/
	public double getAmount() {
		return amount;
	}
	
	/** Set the name of the paidOnTime*/
	public void setpaidOnTime(boolean paidOnTime) {
		this.paidOnTime = paidOnTime;
	}

	/** Returns the name of the paidOnTime*/
	public boolean isPaidOnTime() {
		return paidOnTime;
	}
	
	public int getYear() {
		return year;
	}

	/** Returns a string representation of a TaxRecord object*/
	public String toString() {
		return "Tax for year (" +year+")" + " €" + amount + " Tax paid on time: " + paidOnTime;
	}
}
