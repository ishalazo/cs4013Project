package cs4013Project;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class DepartmentPersonnel {
	// pretty sure there us no need for any of these data fields
	private ArrayList<Property> property;
	private String eircode;
	private String ownerid;
	private ArrayList<String[]> properties;
	
	
	/** DepartmentPersonnel
	 * @author Tito Etimiri 19248547
	 */
	/**
	 * @param eircode
	 * @param ownerid
	 * 
	 */
	
	public DepartmentPersonnel() {
	}
	
	//some of the methods are a lil redundant now but i don't want to delete anything yet
	
	private ArrayList<String[]> getOwnerProperties(String ownerid) { 
        return Utilities.filter(Utilities.readFromFile("properties.csv"), "Owner_id",ownerid);
    }
	
	//seems? kinda? idk, not gonna delete ~for review ig~
	/*return payments made by a particular owner*/
	public String getOwnerPayments(String ownerid) {
		property = new ArrayList<Property>();
		Utilities.filter(properties, "Ownerid", ownerid);
		 Utilities.readFromFile("taxPayments.csv");
		
		for(int i = 0; i < property.size(); i++) {
			if(ownerid.equals((property.get(i).getOwnerID()))){
				return(property.get(i).toString());
			}
		}
		return "Incorrect id";
	}
	
	//seems? kinda? idk, not gonna delete ~for review ig~
	/*return payments made on a particular property*/
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
	
	/*return all unpaid tax payments for a particular owner, on a particular year*/
	private ArrayList<String> getOverdues(String ownerid, String eircode){
        ArrayList<String[]> properties = Utilities.filter(Utilities.filter(Utilities.readFromFile("taxPayments.csv"),"Year",ownerid), "Eircode", eircode);
        properties = Utilities.filter(properties, "Year", LocalDate.now().getYear());
        ArrayList<String[]> unpaid = Utilities.filter(properties,"Paid",false);
        return Utilities.readFromColumn(unpaid,0);
    }
	
	public String getRoutingKey(String eircode){        
        return eircode.substring(0, 2);
    }
	
	private int sumRows(ArrayList<String[]> properties, int tax) {
		int sum = 0;
        for (String[] line: properties) {
            sum = sum + Integer.parseInt(line[tax]);
        }
        return sum;
	}
	
	public String getPropertStats(String eircode, String ownerid) {
	   
		String RKey = getRoutingKey(eircode);
		
		if(eircode.length() > 3) {
			System.out.println("Incorrect input, routing key too long");
		}
	
        ArrayList<String[]> tax = Utilities.filter(tax, RKey, Utilities.readFromColumn("Tax",5));
        int allprops = sumRows(getOwnerProperties(null), 5 );
        System.out.println("The sum of column "+ tax +" is: " + allprops); 
        return "Eircode: " + eircode + "\nTotal Tax Paid: " + String.format( "%.2f", allprops) + "\nAverage Tax Paid: " + String.format( "%.2f", (allprops / tax));
		
    }

}
