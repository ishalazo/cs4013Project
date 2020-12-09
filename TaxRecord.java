/** TaxRecord Class for documenting tax payments made by owners
	 * @author Lakeisha Lazo 19277997*/
	
public class TaxRecord {
	private double amount;
	private int year;
	private boolean paidOnTime;
	
	/** Constructs a Payment object
	 * @param double amount The tax due for that year.
	 * @param int year Year that the tax is due.
	 * @param boolean paidOnTime If the tax is paid on time or not.
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
	
	/** Gets the amount of tax due.
	 * @return Amount of tax due.
	 */
	public double getAmount() {
		return amount;
	}
	
	/** Change the value of paidOnTime.
	 * @return Nothing.*/
	public void setpaidOnTime(boolean paidOnTime) {
		this.paidOnTime = paidOnTime;
	}

	/**True if the tax has been paid on time. False otherwise. 
	 * @return Paid on time*/
	public boolean isPaidOnTime() {
		return paidOnTime;
	}
	
	/**Gets the yaer tax is due.
	 * @return Year the tax is due
	 */
	public int getYear() {
		return year;
	}

	/** Returns a string representation of a TaxRecord object*/
	public String toString() {
		return "Tax for year (" +year+")" + " €" + amount + ((paidOnTime)? ", paid":", outstanding");
	}
}
