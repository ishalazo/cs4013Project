import java.util.ArrayList;

public class Owner {

	private String ownerid;
	private String password;
	private String ownername;
	private ArrayList<Object> properties = new ArrayList<>();

	/** Owner class 
	  @author Tito Etimiri 19248547 */
	/*
	 *  
	 * 
	 */

	public Owner(String ownerid,String password,String ownername,boolean writeToCSV) {
		this.ownername = ownername;
		this.ownerid = ownerid;
		this.password = password;

		if(writeToCSV)
		{
			String[] login = {ownerid,password,ownername};
			Utilities.writeToFile("systemLogins.csv", login);
		}
		else
		{
			properties = Utilities.fileToArrayList("properties.csv", "Owner_id", ownerid);
		}
	}

	public String getOwnerid(){
		return ownerid;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getFullname() {
		return ownername;
	}
	
	//might be a little redundant tho
	/* Owner can add properties */
	public void addProperty(Object property) {
		if(property instanceof Property)
		{
			properties.add(property);
		}

	}   

	/*returns a list of properties*/
	public ArrayList<Object> getProperties() {
		return properties;
	}

	/*returns a list of transactions for a particular year*/
	public void getBalanceStatement(int year){
		Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Year", year);
    }

	/*pays outstanding property tax*/
	public void payPropertyTax(String[] property){
		Utilities.writeToCell("taxPayments.csv", true, property, "Paid");
	}


}
