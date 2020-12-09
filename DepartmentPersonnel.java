package cs4013Project;
import java.util.ArrayList;


//Do statistics in here
//Statistics = total houses that paid in a particular area and the total tax located in a certain area
//Maybe list all registered in area and find a % of those paid
//Eircode routing thing 
//Eircode is 7 characters long
//First 3 characters are the routing key to identify the area

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
		
		for(int i = 0; i < property.size(); i++) {
			if(ownerid.equals((property.get(i).getOwner()))){
				return(property.get(i).toString());
			}
		}
		return "Incorrect id";
	}
	
	/*return payments mad on a particular property*/
	public String getPropertyPayments(String eircode) {
		property = new ArrayList<Property>();
		
		for(int i = 0; i < property.size(); i++) {
			if(eircode.equals((property.get(i).getEircode()))) {
				return(property.get(i).toString());
			}
		}
		return "Incorrect eircode";
	}
	
	//This is vvvv wrong but essentially if isPaidOnTime = false, return overdues for that year
	//this doesnt make sense in code but im on to something
	public String getYearOverdues(TaxRecord taxes, int year) {
		for(Property property : property) {
				if(taxes.isPaidOnTime() == false) 
		
	}
	
	//just return all the tax details for an area depending on the imputed routing key
	public String getEircodeStats(String eircode) {
		return " ";
	}
	
   
}
