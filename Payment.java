import java.time.LocalDate;
public class Payment {
	private double amount;
	private LocalDate date;
	private String payer;
	
	/** Payment Class for documenting payment made by owners
	 * @author Clodagh Walsh 19230737*/
	
	// Payment pay = new Payment(1000,LocalDate.of(2020, 10, 17),"clodagh");
	
	/** Constructs a Payment object
	 * @param double amount paid
	 * @param LocalDate date payment made
	 * @param String payer
	 * */
	public Payment(double amount,LocalDate date,String payer) {
		this.amount = amount;
		this.date = date;
		this.payer = payer;
	}
	
	/** Set the amount of the payment*/
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/** Set the date the payment is made*/
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/** Set the name of the payer*/
	public void setPayer(String payer) {
		this.payer = payer;
	}

	/** Returns the amount paid*/
	public double getAmount() {
		return amount;
	}

	/** Returns the date the payment was made*/
	public LocalDate getDate() {
		return date;
	}
	
	/** Returns the year in which the payment was made*/
	public int yearOfPayment() {
		return date.getYear();
	}

	/** Returns the name of the payer*/
	public String getPayer() {
		return payer;
	}
	
	/** Returns a string representation of a Payment object*/
	public String toString() {
		return "€" + amount + " paid by " + payer + " on " + date;
	}
}