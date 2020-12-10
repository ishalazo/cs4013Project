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
			if(ownerid.equals((property.get(i).getownerID()))){
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
	
	
	public ArrayList<TaxRecord> getYearOverdues(int year) {
		ArrayList<TaxRecord> overdue = new ArrayList<>();
		
		for(int i = 0; i < property.size(); i++) {
			if(property.get(i).isPaid == false){
				return overdue;
				}
				
			}
		return overdue;
		}
		
	//just return all the tax details for an area depending on the imputed routing key
	public String getEircodeStats(String eircode) {
		System.out.println("Enter the routing key");
        
		return "";
	}
	
   
}
