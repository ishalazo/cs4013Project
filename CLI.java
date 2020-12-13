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
//		TaxCalculator.setCalculator();
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
	
	private boolean validEircode(String eircode) {
		ArrayList<String[]> properties = Utilities.filter(Utilities.readFromFile("properties.csv"), "Eircode", eircode);
		properties.remove(0);
		boolean valid = true;
		for(String[] p : properties) {
			if(!eircode.equalsIgnoreCase(p[2])){
				valid = false;
				break;
			}
		}
		return valid;
	}
	
	private void ownerMenu() {
		boolean more = true;
		Owner currentO = (Owner) users.get(users.size()-1);
		ArrayList<Object> props = currentO.getProperties();
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
					currentO.addProperty(new Property(currentO.getOwnerid(), address, eircode, location, marketValue, principalResidence, true));
					System.out.println("Your property have been successfully registered.");
					break;
				
				case "V": //View properties
					for(Object p : props) {
						System.out.println(p.toString());
					}
					break;
				
				case "P": //Pay tax
					double total = 0;
					ArrayList<String[]> outstandingPayments = new ArrayList<String[]>(); 
					System.out.println("Enter the Eircode of the property you to pay tax for");
					for(int i = 0; i < props.size();i++) {
						Property p = (Property) props.get(i);
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
					
				case "B":
					System.out.println("Query (A)ll, a Specific (Y)ear or (P)roperty");
					String balIn = scan.nextLine().toUpperCase();
					switch(balIn) {
						case "A": // Query all
							for(Object o : props) {
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
							for(Object o : props) {
								Property p = (Property)o;
								System.out.print(p.propBalStatement(firstYr,secondYr));
							}
							break;
							
						case "P": //Query a specific property
							System.out.println("Enter the Eircode of the property you want to query");
							eircode = scan.nextLine();
							for(Object o : props) {
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
					
				case "U": // update property WHY THE FUCK DOESNT IT WORK
					System.out.println("Enter the Eircode of the property you want to update");
					eircode = scan.nextLine();
					if(validEircode(eircode)) {
						for(Object o : props) {
							Property p = (Property)o;
							if(eircode.equalsIgnoreCase(p.getEircode())) {
								System.out.println("Update M)arket value  P)rincipal private residence");
								String update = scan.nextLine().toUpperCase();
								switch(update) {
									case "M":
										System.out.printf("Currently: %.2f, update to \n",p.getMarketValue());
										double newVal = scan.nextDouble();
										p.setMarketValue(newVal);
										System.out.println("Successfully changed estimated market value");
										break;
									
									case "P":
										System.out.printf("Currently: %b, update to \n",p.isprincipalResidence());
										boolean newBool = scan.hasNext();
										p.setprincipalResidence(newBool);
										System.out.println("Successfully changed principal pricate residence");
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
					+ "Ov)erdue property taxes    T)ax statistics    "
					+ "S)et tax calculator    Q)uit");
			String in = scan.nextLine().toUpperCase();
			
			switch(in) {
				case "P":
					System.out.println("Enter the eircode of the property");
					String eircode = scan.nextLine();
					ArrayList<String[]> pInfo = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode", eircode);
//					Property p = new Property(pInfo[0],pInfo[1],pInfo[2],pInfo[3],Double.parseDouble(pInfo[4]),Boolean.parseBoolean(pInfo[5]), false);
//					System.out.println("Owner ID: " + p.getOwnerID());
//					System.out.println(p.propBalStatement());
					for(String[] s : pInfo) {
						Arrays.toString(s);
					}
					break;
					
				case "OW":
					System.out.println("Enter Owner ID");
					String ownerID = scan.nextLine();
					ArrayList<String[]> ownerPayments = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Owner_id", ownerID);
					for(String[] s : ownerPayments) {
						Arrays.toString(s);
					}
					break;
					
				case "OV":
					System.out.println("View (A)ll overdues or sort by Y)ear");
					String ovIn = scan.nextLine().toUpperCase();
					ArrayList<String[]> overdues = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Paid", "false");
					switch(ovIn) {
						case "A":
							for(String[] s : overdues) {
								Arrays.toString(s);
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
							for(String[] s : overdueYr) {
								Arrays.toString(s);
							}
							break;
					}
					break;
					
				case "T":
					break;
				
				case "S":
					break;
					
				case "Q":
					more = false;
					break;
			}
		}
		
	}
}
