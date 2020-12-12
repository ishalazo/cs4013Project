import java.util.*;

public class CLI {
	private Scanner scan;
	
	/**
	 * Constructs the Command Line Interface
	 */
	public CLI() {
		scan = new Scanner(System.in);
		TaxCalculator.setCalculator();
//		ArrayList<Object> properties = fileToArrayList(Utilities.readFromFile("properties.csv"));
//		ArrayList<Object> users = fileToArrayList(Utilities.readFromFile("systemLogins.csv"));
	}
	
	//idk if i even need this 
	private ArrayList<Object> fileToArrayList(ArrayList<String[]> file) {
		String lastHead = file.get(0)[file.get(0).length-1];
		ArrayList<Object> output = new ArrayList<Object>();		
		if(lastHead.equalsIgnoreCase("principal residence")) {
			for(String[] row : file) {
				Property p = new Property(row[0], row[1], row[2], row[3], 
						Double.parseDouble(row[4]), Boolean.parseBoolean(row[5]));
				output.add(p);
			}
		} else if(lastHead.equalsIgnoreCase("full name")) {
			for(String[] row : file) {
				Owner o = new Owner(row[2], row[0], row[1]);
				output.add(o);
			}
		}
		return output;
	}
	
	public void run() {
		boolean more = true;
		
		while(more) {
			System.out.println("O)wner\tD)epartment Personnel\tQ)uit");
			String command = scan.nextLine().toUpperCase();
			
			if(command.equals("O")) {
				System.out.println("L)ogin\tR)egister\tQ)uit");
				String in = scan.nextLine().toUpperCase();
				if(in.equals("L")) { //login
					boolean successfulLogin = login();
					if(!successfulLogin) {
						more = false;
					} else {
						System.out.println("Successful login");
						//owner screen
					}
				}
				else if(in.equals("R")) { //register owner
					System.out.println("Are you eligible to register as an owner? Y/N");
					String in1 = scan.nextLine().toUpperCase();
					if(in1.equals("N")) {
						more = false;
					} else {
						registerOwner(); //!!!!!!! bro are we redirecting them or asking for a restart
					}
				} 
				else if(in.equals("Q")) { //quit
					more = false;
				}
			}
			else if(command.equals("D")) { //dept personell
				boolean successfulLogin = login();
				if(!successfulLogin) {
					more = false;
				} else {
					System.out.println("Successful login");
					//employee screen
				}
			}
			else if(command.equals("Q")) { //quit
				more = false;
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
		System.out.println("Provide your full name:");
		String name = scan.nextLine();
		System.out.println("Provide your password:");
		String pw = scan.nextLine();
		Utilities.writeToFile("systemLogins.csv", new String[] {Integer.toString(ownerID), pw, name});
		System.out.println("You have successfully registered.\nPlease note your Owner ID: "+ownerID
				+"\nPlease login with your new account");
	}
}
