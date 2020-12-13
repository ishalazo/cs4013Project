/**
 * This runs the project on the command line.
 * @author Lakeisha Lazo 1927797
 */
import java.time.LocalDate;
import java.util.*;

public class CLI {
	private Scanner scan;
	private boolean more;
	ArrayList<Object> users;
	
	/**
	 * Constructs the Command Line Interface
	 */
	public CLI() {
		more = true;
		scan = new Scanner(System.in);
		users = new ArrayList<Object>();
	}

	public void run() {
		while(more) {
			System.out.println("O)wner  D)epartment Personnel  Q)uit");
			String command = scan.nextLine().toUpperCase();

			switch(command) {
			case "O": 
				System.out.println("L)ogin  R)egister  Q)uit");
				String OwnerIn = scan.nextLine().toUpperCase();
				switch(OwnerIn) { //login
					case "L":
						if(login()) {
							ownerMenu();
						} else {
							System.out.println("Unsuccessful login. Please try again.");
						}
						break;
						
					case "R":	//Register
						System.out.println("Are you eligible to register as an owner? Y/N");
						String in1 = scan.nextLine().toUpperCase();
						if(in1.equals("N")) {
							more = false;
						} else {
							registerOwner();
						}
						break;
						
					case "Q": //Quit
						more = false;
						break;
				}
				break;

			case "D": //Dept. Personnel
				if(login()) {
					deptMenu();
				} else {
					System.out.println("Unsuccessful login. Please try again.");
				}
				break;

			case "Q": //Quit
				more = false;
				break;
			}


		}
	}

	private boolean login() {
		boolean succ = false;
		System.out.println("ID: ");
		String id = scan.nextLine();
		System.out.println("Password: ");
		String pw = scan.nextLine();
		String[] userData = Utilities.filter(Utilities.readFromFile("systemLogins.csv"),"ID",id).get(1);
		if(pw.equals(userData[1])) {
			Utilities.taxRecalculation();
			succ = true;
			String name = userData[2];
			users.add(new Owner(id, pw, name, false));
		}
		return succ;
	}

	private void registerOwner() {
		ArrayList<String> currentOwners = Utilities.readFromColumn("systemLogins.csv", 0);
		int ownerID = Integer.parseInt(currentOwners.get(currentOwners.size() -1)) + 1;
		System.out.println("Enter your full name:");
		String name = scan.nextLine();
		System.out.println("Enter your password:");
		String pw = scan.nextLine();
		users.add(new Owner(Integer.toString(ownerID), pw, name, true));
		System.out.println("You have successfully registered.\nPlease note your Owner ID: "+ ownerID
				+"\nPlease login with your new account.");
	}
	
	private boolean validEircode(String in) {
		ArrayList<String[]> properties = Utilities.filter(Utilities.readFromFile("properties.csv"), "Eircode", in);
		properties.remove(0);
		boolean valid = true;
		for(String[] p : properties) {
			if(!in.equalsIgnoreCase(p[2])){
				valid = false;
				break;
			}
		}
		return valid;
	}
	
	private double[] convertToArray(String s) {
        String[] temp = s.split(",");
        double[] arr = new double[temp.length];
        for(int i=0;i<arr.length;i++)
        {
            arr[i] = Double.parseDouble(temp[i]);
        }
        return arr;
    }
	
	private void ownerMenu() {
		boolean more = true;
		Owner currentO = (Owner) users.get(users.size()-1);
		while(more) {
			System.out.println("R)egister property    V)iew properties    P)ay tax    "
					+ "B)alancing statement    U)pdate a property    Q)uit");
			String in = scan.nextLine().toUpperCase();
			switch(in) {
				case "R": //register property
					System.out.println("Enter property address");
					String address = scan.nextLine();
					System.out.println("Enter the Eircode of your property");
					String eircode = scan.nextLine();
					System.out.println("Choose one of the following location categories: Co)untryside, V)illage, S)mall town, L)arge town, C)ity");
					String locChoice = scan.nextLine().toUpperCase();
					String location = "";
					switch(locChoice) {
						case "CO": location = "Countryside"; break;
						case "V": location = "Village"; break;
						case "S": location = "Small town"; break;
						case "L": location = "Large town"; break;
						case "CI": location = "City"; break;
					}
					System.out.println("Enter the property's estimated market value");
					double marketValue = scan.nextDouble();
					System.out.println("Is the property your principal private residence? True/False");
					Boolean principalResidence = scan.nextBoolean();
					new Property(currentO.getOwnerid(), address, eircode, location, marketValue, principalResidence, true);
					currentO.updateProperties();
					System.out.println("Your property have been successfully registered.");
					break;
				
				case "V": //View properties
					for(Object p : currentO.getProperties()) {
						System.out.println(p.toString());
					}
					break;
				
				case "P": //Pay tax
					double total = 0;
					ArrayList<String[]> outstandingPayments = new ArrayList<String[]>(); 
					System.out.println("Enter the Eircode of the property you to pay tax for");
					for(int i = 0; i < currentO.getProperties().size();i++) {
						Property p = (Property) currentO.getProperties().get(i);
						ArrayList<String[]> propPayments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", p.getEircode());
						propPayments.remove(0);
						String[] recentPay = propPayments.get(propPayments.size()-1);
						if (Integer.parseInt(recentPay[3]) == LocalDate.now().getYear() && !Boolean.parseBoolean(recentPay[5])) {
							System.out.printf("%s due €%.2f\n", p.getEircode(), p.getTax());
							total += p.getTax();
							outstandingPayments.add(recentPay);
						}
					}
					System.out.printf("P)ay all tax, Total €%.2f\nC)ancel payment\n", total);
					String userPay = scan.nextLine().toUpperCase();
					if(userPay.equals("P")) { //pay all
						for(String[] payP : outstandingPayments) {
							currentO.payPropertyTax(payP);
						}
						System.out.println("Successfully paid the tax for all properties.");
					} else if(userPay.equals("C")) { //cancel
						break;
					} else if(validEircode(userPay)) { //specifically one 
						for(String[] payP : outstandingPayments) {
							if(userPay.equalsIgnoreCase(payP[1])) {
								currentO.payPropertyTax(payP);
								System.out.println("Successfully paid tax for property " + payP[1]);
								break;
							}
						}
					}
					break;
					
				case "B": // balancing statements
					System.out.println("Query (A)ll, a Specific (Y)ear or (P)roperty");
					String balIn = scan.nextLine().toUpperCase();
					switch(balIn) {
						case "A": // Query all
							for(Object o : currentO.getProperties()) {
								Property p = (Property)o;
								System.out.println(p.propBalStatement());
							}
							break;
							
						case "Y": //Query Years
							System.out.println("O)ne year   R)ange of years");
							String yearIn = scan.nextLine().toUpperCase();
							int firstYr = 0, secondYr = 0;
							if(yearIn.equals("O")){//one year
								System.out.println("Enter the year you want to query");
								firstYr = scan.nextInt();
								secondYr = firstYr;
							} else if(yearIn.equals("R")) {//range of years
								System.out.println("Enter the first year of your query");
								firstYr = scan.nextInt();
								System.out.println("Enter the second year of your query");
								secondYr = scan.nextInt();
							} else {
								break;
							}
							ArrayList<String> balSta = currentO.getBalanceStatement(firstYr, secondYr);
							for(String s : balSta) {
								System.out.println(s);
							}
							break;
							
						case "P": //Query a specific property
							System.out.println("Enter the Eircode of the property you want to query");
							eircode = scan.nextLine();
							for(Object o : currentO.getProperties()) {
								Property p = (Property)o;
								if(eircode.equalsIgnoreCase(p.getEircode())) {
									System.out.println(p.propBalStatement());
									break;
								}
								break;
							}
							break;
					}
					break;
					
				case "U": // update property
					System.out.println("Enter the Eircode of the property you want to update");
					eircode = scan.nextLine();
					if(validEircode(eircode)) {
						for(int i = 0; i < currentO.getProperties().size(); i++) {
							Property p = (Property) currentO.getProperties().get(i);
							if(eircode.equalsIgnoreCase(p.getEircode())) {
								System.out.println("Update M)arket value  P)rincipal private residence  O)wner");
								String update = scan.nextLine().toUpperCase();
								switch(update) {
									case "M": //Changing market value
										System.out.printf("Currently: €%.2f, update to €\n",p.getMarketValue());
										String newVal = scan.nextLine();
										p.setMarketValue(Double.parseDouble(newVal));
										System.out.println("Successfully changed estimated market value");
										break;
									
									case "P": //Changing PPR
										System.out.printf("Currently: %b, update to \n",p.isprincipalResidence());
										String newBool = scan.nextLine();
										p.setprincipalResidence(Boolean.parseBoolean(newBool));
										System.out.println("Successfully changed principal private residence");
										break;
									
									case "O": //Changing owner
										System.out.println("Enter the new owner's ID");
										String id = scan.nextLine();
										System.out.println("Warning: Once you change the owner, you won't have access to the property anymore\nWould you like to proceed? (Y/N)");
										String choice = scan.nextLine().toUpperCase();
										if(choice.equals("Y")) {
											for(Object o : currentO.getProperties()) {
												p =(Property)o;
												if(eircode.equalsIgnoreCase(p.getEircode())) {
													p.setOwnerID(id);
													currentO.updateProperties();
													break;
												}
											}
										}
										break;
								}
							}
						}
					}
					break;
					
				case "Q":
					more = false;
					break;
			}
		}
	}
	
	private void deptMenu() {
		boolean more = true;
		while(more) {
			System.out.println("P)roperty tax payments    Ow)ner tax payments    "
					+ "Ov)erdue property taxes    S)tatistics    "
					+ "T)ax calculator    Q)uit");
			String in = scan.nextLine().toUpperCase();
			
			switch(in) {
				case "P":
					System.out.println("Enter the eircode of the property");
					String eircode = scan.nextLine();
					ArrayList<String[]> pInfo = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
					for(String[] s : pInfo) {
						System.out.println(Arrays.toString(s));
					}
					break;
					
				case "OW":
					System.out.println("Enter Owner ID");
					String ownerID = scan.nextLine();
					ArrayList<String[]> ownerPayments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Owner_id", ownerID);
					for(String[] s : ownerPayments) {
						System.out.println(Arrays.toString(s));
					}
					break;
					
				case "OV": //Overdues
					System.out.println("View (A)ll overdues or sort by Y)ear");
					String ovIn = scan.nextLine().toUpperCase();
					ArrayList<String[]> overdues = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Paid", "false");
					switch(ovIn) {
						case "A":
							for(String[] s : overdues) {
								System.out.println(Arrays.toString(s));
							}
							break;
						
						case "Y":
							System.out.println("Would you like to select an area based on a Eircode routing key? Y/N");
							String keyOp = scan.nextLine().toUpperCase();
							ArrayList<String[]> overdueFil = new ArrayList<String[]>();
							if(keyOp.equals("Y")) {
								System.out.println("Enter the routing key");
								String key = scan.nextLine();
								overdueFil = Utilities.filter(overdues, "Eircode", key);
							} else if(keyOp.equals("N")) {
								overdueFil = overdues;
							}
							System.out.println("Enter the year");
							int year = scan.nextInt();
							ArrayList<String[]> overdueYr = Utilities.filter(overdueFil, "Year", year);
							if(overdueYr.size() == 1) {
								System.out.println("No overdues found");
								break;
							}
							for(String[] s : overdueYr) {
								System.out.println(Arrays.toString(s));
							}
							break;
					}
					break;
					
				case "S": //Statistics
					DepartmentPersonnel dp = new DepartmentPersonnel();
					System.out.println("Enter the routing key of the area you want statistics from");
					String key = scan.nextLine();
					System.out.println(dp.getPropertStats(key, null));
					break;
				
				case "T"://Tax Calculator
					System.out.println("Choose one of the following to set:\n"
							+ "1) Fixed cost of tax\n"
							+ "2) Principal Private Residence Charge\n"
							+ "3) Percentage penalty in decimal\n"
							+ "4) Property Value Boundaries\n"
							+ "5) Rates in decimal percentage in relation to the Property Values\n"
							+ "6) Location categories properties fall under\n"
							+ "7) Location charge based on the category\n"
							+ "8) Set all\n"
							+ "\n9) View Tax calculator values");
					int choice = scan.nextInt();
					switch(choice) {
						case 1:
							System.out.println("Enter new fixed cost of tax");
							double fixedCost = scan.nextDouble();
							TaxCalculator.setFixedCost(fixedCost);
							break;
							
						case 2:
							System.out.println("Enter new Principal Private Residence Charge");
							double flatCharge = scan.nextDouble();
							TaxCalculator.setFlatCharge(flatCharge);
							break;
							
						case 3:
							System.out.println("Enter new percentage penalty in decimal");
							double penalty = scan.nextDouble();
							TaxCalculator.setPenalty(penalty);
							break;
							
						case 4:
							System.out.println("Enter new Property Value Boundaries separated by commas");
							String propBounds = scan.nextLine();
							TaxCalculator.setPropBounds(convertToArray(propBounds));
							break;
							
						case 5:
							System.out.println("Enter new Rates in decimal percentage in relation to the Property Values separated by commas");
							String rateBounds = scan.nextLine();
							TaxCalculator.setRateBounds(convertToArray(rateBounds));
							break;
							
						case 6:
							System.out.println("Enter new location categories separated by commas");
							String s = scan.nextLine();
							String[] locType = s.split(",");
							TaxCalculator.setLocType(locType);
							break;
							
						case 7:
							System.out.println("Enter new location charge based on the category separated by commas");
							String locCharge = scan.nextLine();
							TaxCalculator.setLocCharge(convertToArray(locCharge));
							break;
							
						case 8:
							System.out.println("Enter new fixed cost of tax");
							fixedCost = scan.nextDouble();
							System.out.println("Enter new Principal Private Residence Charge");
							flatCharge = scan.nextDouble();
							System.out.println("Enter new percentage penalty in decimal");
							penalty = scan.nextDouble();
							System.out.println("Enter new Property Value Boundaries separated by commas");
							propBounds = scan.nextLine();
							System.out.println("Enter new Rates in decimal percentage in relation to the Property Values separated by commas");
							rateBounds = scan.nextLine();
							System.out.println("Enter new location categories separated by commas");
							s = scan.nextLine();
							locType = s.split(",");
							System.out.println("Enter new location charge based on the category separated by commas");
							locCharge = scan.nextLine();
							TaxCalculator.setCalculator(fixedCost, flatCharge, penalty, convertToArray(propBounds), convertToArray(rateBounds), convertToArray(locCharge), locType);
							break;
							
						case 9:
							System.out.println(TaxCalculator.viewCalc());
					}
					break;
					
				case "Q":
					more = false;
					break;
			}
		}
		
	}
	
	public static void main(String[] args) {
		CLI c = new CLI();
		c.run();
	}
}
