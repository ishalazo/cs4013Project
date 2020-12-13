package cs4013Project;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class DepartmentPersonnel {
	private ArrayList<Property> property;
	
	/** DepartmentPersonnel
	 * @author Tito Etimiri 19248547
	 */
	
	public DepartmentPersonnel() {
	}
		
	private ArrayList<String[]> getOwnerProperties(String ownerid) { 
        return Utilities.filter(Utilities.readFromFile("properties.csv"), "Owner_id",ownerid);
    }
	
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
	
	private double sumRows(ArrayList<String[]> properties, int tax) {
		double sum = 0;
		for (int i = 1; i < properties.size(); i++) {
			sum = sum + Double.parseDouble(properties.get(i)[tax]);
		}
		return sum;
	}
	
	/*returns tax statistics of properties in a particular area*/
	public String getPropertStats(String eircode, String ownerid) {
	   
		
		
		if(eircode.length() > 3) {
			System.out.println("Incorrect input, routing key too long");
		}
		ArrayList<String[]> properties = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode",
				eircode);
		double totalpaid = sumRows(properties, 4);

		return "Eircode: " + eircode + "\nTotal Tax Paid: " + String.format("%.2f", totalpaid) + "\nAverage Tax Paid: "
				+ String.format("%.2f", (totalpaid / properties.size()));
	            
		
    }

}
