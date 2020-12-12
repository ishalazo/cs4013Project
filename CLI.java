import java.util.*;

public class CLI {
	private Scanner scan;
	boolean more;
	/**
	 * Constructs the Command Line Interface
	 */
	public CLI() {
		more = true;
		scan = new Scanner(System.in);
		TaxCalculator.setCalculator();
		//		ArrayList<Object> properties = fileToArrayList(Utilities.readFromFile("properties.csv"));
		//		ArrayList<Object> users = fileToArrayList(Utilities.readFromFile("systemLogins.csv"));
	}

	//idk if i even need this 
	public ArrayList<Object> fileToArrayList(String fileName) {
		ArrayList<String[]> file = Utilities.readFromFile(fileName);
		ArrayList<Object> output = new ArrayList<Object>();		
		if(fileName.equals("properties.csv")) {
			for(int i = 1; i < file.size(); i++) {
				String[] row = file.get(i);
				Property p = new Property(row[0], row[1], row[2], row[3], 
						Double.parseDouble(row[4]), Boolean.parseBoolean(row[5]),false);
				output.add(p);
			}
		} else if(fileName.equals("systemLogins.csv")) {
			for(int i = 1; i < file.size(); i++) {
				String[] row = file.get(i);
				Owner o = new Owner(row[0], row[1], row[2]);
				output.add(o);
			}
		}
		return output;
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
						boolean successfulLogin = login();
						if(!successfulLogin) {
							more = false;
						} else {
							System.out.println("Successful login");
							//owner screen
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
				boolean successfulLogin = login();
				if(!successfulLogin) {
					System.out.println("Unsuccessful login. Please try again.");
				} else {
					System.out.println("Successful login");
					deptMenu();
				}
				break;

			case "Q": //Quit
				more = false;
				break;
			}


		}
	}

	private boolean login() {
		boolean success = false;
		System.out.println("ID: ");
		String id = scan.nextLine();
		System.out.println("Password: ");
		String pw = scan.nextLine();
		String[] userData = Utilities.filter(Utilities.readFromFile("systemLogins.csv"),"ID",id).get(1);
		if(pw.equals(userData[1])) {
			success = true;
		}
		return success;
	}

	private void registerOwner() {
		ArrayList<String> currentOwners = Utilities.readFromColumn("systemLogins.csv", 0);
		int ownerID = Integer.parseInt(currentOwners.get(currentOwners.size() -1)) + 1;
		System.out.println("Enter your full name:");
		String name = scan.nextLine();
		System.out.println("Enter your password:");
		String pw = scan.nextLine();
		Utilities.writeToFile("systemLogins.csv", new String[] {Integer.toString(ownerID), pw, name});
		System.out.println("You have successfully registered.\nPlease note your Owner ID: "+ownerID
				+"\nPlease login with your new account.");
	}
	
	private void ownerMenu() {
		boolean more = true;
		while(more) {
			System.out.println("R)V)iew properties");
		}
	}
	
	private void deptMenu() {
		boolean more = true;
		while(more) {
			System.out.println("P)roperty tax payments    "
					+ "Ow)ner tax payments    "
					+ "Ov)erdue property taxes    "
					+ "T)ax statistics    "
					+ "S)et tax calculator    "
					+ "Q)uit");
			String in = scan.nextLine().toUpperCase();
			
			switch(in) {
				case "P":
					System.out.println("Enter the eircode of the property");
					String eircode = scan.nextLine();
					String[] pInfo = Utilities.filter(Utilities.readFromFile("properties.csv"), "Eircode", eircode).get(1);
					Property p = new Property(pInfo[0],pInfo[1],pInfo[2],pInfo[3],Double.parseDouble(pInfo[4]),Boolean.parseBoolean(pInfo[5]), false);
					System.out.println("Owner ID: " + p.getOwnerID());
					System.out.println(p.propBalStatement());
					break;
					
				case "OW":
					System.out.println("Enter Owner ID");
					break;
					
				case "OV":
					break;
					
				case "T":
					break;
				
				case "S":
					break;
					
				case "Q":
					this.more = false;
					more = false;
					break;
			}
		}
		
	}
}
