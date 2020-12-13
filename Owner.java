import java.time.LocalDate;
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
	
	public void updateProperties() {
		properties = Utilities.fileToArrayList("properties.csv", "Owner_id", ownerid);
	}

	/*returns a list of properties*/
	public ArrayList<Object> getProperties() {
		return properties;
	}

	/*returns a list of transactions for a particular year*/ // PLEASE DO NOT CHANGE THIS
	public ArrayList<String> getBalanceStatement(int firstYr, int secondYr){
		ArrayList<String> statements = new ArrayList<String>();
		for(int i=0;i<properties.size();i++)
		{
			if(properties.get(i) instanceof Property)
			{
				statements.add(((Property)properties.get(i)).propBalStatement(firstYr,secondYr));
			}
		}
		return statements;
	}

	/*pays outstanding property tax*/
	public void payPropertyTax(String[] property){
		Utilities.writeToCell("taxPayments.csv", true, property, "Paid");
	}


}
