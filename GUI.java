import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Label username = new Label("Username");
		Label password = new Label("Password");
		TextField user = new TextField(); // can do a promptText for username maybe
		PasswordField pin = new PasswordField(); 
		
		BorderPane pane = new BorderPane();
		BorderPane p1 = new BorderPane();
		BorderPane p2 = new BorderPane();
		BorderPane p3 = new BorderPane();

		Scene home = new Scene(pane, 300, 300);
		Scene main = new Scene(p1, 300, 300);
		Scene admin = new Scene(p2, 300, 300);
		Scene addProperty = new Scene(p3,300,300); // newly reg user brought here and can reuse for adding prop later on
		
		pane.setTop(new Text("Welcome")); // get nicer font and perhaps color and even logo for home screen
		p1.setTop(new Text("owner mode"));
		p2.setTop(new Text("admin mode"));
		
		Button newUser = new Button("Click here to register");
		pane.setBottom(newUser);
		

		GridPane form = new GridPane();
						
		Button login = new Button("Login");
		Button register = new Button("Register");

		form.add(username, 0, 0);
		form.add(password, 0, 1);
		form.add(user, 1, 0);
		form.add(pin, 1, 1);
		form.add(login, 0, 2);
		form.setAlignment(Pos.CENTER);
		form.setHgap(5);
		
	
		pane.setCenter(form);
		BorderPane.setAlignment(newUser, Pos.CENTER); // maybe some padding after
		
		GridPane propertyDetails = new GridPane();
		Button add = new Button("Add Property");
		Label owner = new Label("Owner"); // what is have Owners ???
		Label address = new Label("Address"); 
		Label eircode = new Label("Eircode");
		Label location = new Label("Location"); // dropdown combo
		Label value = new Label("Estimated Market Value");
		Label primaryResidence = new Label("Principal Private Residence"); 
		
		TextField o = new TextField();
		TextArea addr = new TextArea();
		TextField ec = new TextField();
		//location combo thing
		TextField price = new TextField();
		RadioButton primRes = new RadioButton("Yes"); // use isSelected()
		RadioButton notPrimRes = new RadioButton("No");
		
		//propertyDetails.add(, , );
		// event Listener for add button that creates a new property object

		login.setOnAction(e -> {
			System.out.println("Login was pressed");
			if (userType(user.getText(), pin.getText()).equals("User")) 
			{
				System.out.println("User successfully logged in");
				primaryStage.setScene(main);
			} 
			else if (userType(user.getText(), pin.getText()).equals("Admin")) 
			{
				System.out.println("Logged in as admin");
				primaryStage.setScene(admin);
			}

		});

		newUser.setOnAction(e -> {
			form.add(register, 0, 2);  // is this the most appropriate method ???
			newUser.setVisible(false);
		});
		
		register.setOnAction(e -> {
			ArrayList<String> registeredUsers = CSV.readFromColumn("systemLogins.csv", 0);
			if(!registeredUsers.contains(user.getText()))
			{
				String[] account = {user.getText(),pin.getText()};
				CSV.writeToFile("systemLogins.csv", account);
				primaryStage.setScene(addProperty); 
			}
		});

		primaryStage.setTitle("Tax Management System");
		primaryStage.setScene(home);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	// could use the readFromColumn()
	private String userType(String username, String password) {
		ArrayList<String[]> logins = CSV.readFromFile("systemLogins.csv");
		String status = "Invalid";
		String[] combo = logins.get(0);

		if (combo[0].equals(username) && combo[1].equals(password)) {
			return "Admin";
		}

		for (int i = 1; i < logins.size(); i++) {
			combo = logins.get(i);
			String name = combo[0];
			String pin = combo[1];

			if (name.equals(username) && pin.equals(password)) {
				status = "User";
			}

		}
		return status;
	}
}
