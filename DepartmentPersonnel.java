package cs4013Project;
import java.util.ArrayList;

public class DepartmentPersonnel {
	
	private ArrayList<Property> property;
	private String eircode;
	private String ownerid;
	
	/** DepartmentPersonnel
	 * @author Tito Etimiri 19248547
	 */
	/**
	 * @param ownerdata
	 * @param propertydata
	 * 
	 */
	
	public DepartmentPersonnel() {

	}
	
	/*return payments made by a particular owner*/
	public String getOwnerPayments(String ownerid) {
		property = new ArrayList<Property>();
		Utilities.filter(properties, "Ownerid", ownerid);
		 Utilities.readFromFile("taxPayments.csv");
		
		for(int i = 0; i < property.size(); i++) {
			if(ownerid.equals((property.get(i).getownerID()))){
				return(property.get(i).toString());
			}
		}
		return "Incorrect id";
	}
	
	/*return payments mad on a particular property*/
	public String getPropertyPayments(String eircode) {
		property = new ArrayList<Property>();
		Utilities.filter(properties, "Eircode", eircode);
		 Utilities.readFromFile("taxPayments.csv");
		
		for(int i = 0; i < property.size(); i++) {
			if(eircode.equals((property.get(i).getEircode()))) {
				return(property.get(i).toString());
			}
		}
		return "Incorrect eircode";
	}
	
	/*return all unpaid taxpayments for a particular year*/
	public String getYearOverdues(int year) {
		Utilities.filter(properties, "Year", year);
		Utilities.readFromColumn("taxPayments.csv", 4)

		 return "Overdue payments for the year: ";
	}
	
	//just return all the tax details for an area depending on the imputed routing key
	public String getEircodeStats(String eircode) {
		System.out.println("Enter the routing key");
		Utilities.filter(properties, "Eircode", eircode);
		 Utilities.readFromFile("taxPayments.csv");
		//total payments/amount of properties
		return "\nAverage for area: ";
	}
	
   
