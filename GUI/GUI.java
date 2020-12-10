import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Insets defaultPadding = new Insets(15,15,15,15); 
			
		Button logout1 = new Button("Logout");
		Button logout2 = new Button("Logout");
		Button back = new Button("Back");
		
		BorderPane homePane = new BorderPane();
		GridPane registrationForm = new GridPane();
		BorderPane ownerMenu = new BorderPane();
		BorderPane adminMenu = new BorderPane();
		GridPane propertyDetails = new GridPane();
		BorderPane propertyList = new BorderPane();
		GridPane updateForm = new GridPane();
				
		Scene home = new Scene(homePane, 700, 700);
		Scene registration = new Scene(registrationForm,700,700);
		Scene main = new Scene(ownerMenu, 700, 700);
		Scene admin = new Scene(adminMenu, 700, 700);
		Scene addProperty = new Scene(propertyDetails,700,700);
		Scene propertyView = new Scene(propertyList,700,700);
		Scene update = new Scene(updateForm,700,700);
		
		Button newUser = new Button("Click here to register");
		
		Button login = new Button("Login");
		Button register = new Button("Register");
		GridPane form = new GridPane();
		
		PasswordField password = new PasswordField(); 
		TextField userInput = new TextField(); 
		form.add(new Label("ID"), 0, 0);
		form.add(new Label("Password"), 0, 1);
		form.add(userInput, 1, 0);
		form.add(password, 1, 1);
		form.add(login, 0, 2);
		form.setAlignment(Pos.CENTER);
		form.setHgap(5);
		form.setVgap(5);
		
		homePane.setCenter(form);
		homePane.setBottom(newUser);
		homePane.setPadding(new Insets(200,15,200,15));
		BorderPane.setAlignment(newUser, Pos.CENTER);

		
		PasswordField newpassword = new PasswordField(); 
		TextField name = new TextField(); 
		registrationForm.add(new Label("Full Name"), 0, 0);
		registrationForm.add(new Label("Password"), 0, 1);
		registrationForm.add(name, 1, 0);
		registrationForm.add(newpassword, 1, 1);
		registrationForm.add(register, 0, 2);
		registrationForm.setAlignment(Pos.CENTER);
		registrationForm.setHgap(5);
		registrationForm.setVgap(5);
		
		homePane.setCenter(form);
		homePane.setBottom(newUser);
		homePane.setPadding(new Insets(200,15,200,15));
		BorderPane.setAlignment(newUser, Pos.CENTER);
		
			
		Button add = new Button("Add Property");
		TextField ownerid = new TextField();
		TextArea address = new TextArea();
		address.setPrefHeight(150);
		address.setPrefWidth(200);
		address.setWrapText(true);
		TextField eircode = new TextField();
		ComboBox<String> locations = new ComboBox<String>();
		String[] locationCategories = {"City","Large Town","Small Town","Village","Countryside"};
		ObservableList<String> options = FXCollections.observableArrayList(locationCategories);
		locations.setItems(options);
		TextField price = new TextField();
		RadioButton primRes = new RadioButton("Yes");
		RadioButton notPrimRes = new RadioButton("No");
		ToggleGroup radioButtons = new ToggleGroup();
		primRes.setToggleGroup(radioButtons);
		notPrimRes.setToggleGroup(radioButtons);
		HBox residentStatus = new HBox(10);
		residentStatus.getChildren().addAll(primRes,notPrimRes);
		
		propertyDetails.add(new Label("Owner id"),0 ,0); // what is have Owners ???
		propertyDetails.add(ownerid,1 ,0);
		propertyDetails.add(new Label("Address"),0 ,1);
		propertyDetails.add(address,1 ,1);
		propertyDetails.add(new Label("Eircode"),0 ,2);
		propertyDetails.add(eircode,1 ,2);
		propertyDetails.add(new Label("Location"),0 ,3);
		propertyDetails.add(locations,1 ,3);
		propertyDetails.add(new Label("Estimated Market Value €"),0 ,4);
		propertyDetails.add(price,1 ,4);
		propertyDetails.add(new Label("Principal Private Residence"),0 ,5);
		propertyDetails.add(residentStatus,1 ,5);
		propertyDetails.add(add,2 ,6);
	
		propertyDetails.setVgap(12);
		propertyDetails.setHgap(12);
		propertyDetails.setPadding(defaultPadding);
		
		/* needs to be finished off
		 * ComboBox<String> properties = new ComboBox<String>();
		ArrayList<String[]> currentProperties = getUserProperties(userInput.getText());
		currentProperties.remove(0);
		//only want the address
		ObservableList<String> dropdown = FXCollections.observableList(currentProperties);
		properties.setItems(dropdown);
		updateForm.add();
		*/
		
		
		add.setOnAction(e -> {
			String estimatedValue = price.getText().replace(",", "");
			new Property(ownerid.getText(),address.getText().replace("\n", " "),eircode.getText(),locations.getValue(),Double.parseDouble(estimatedValue),primRes.isSelected());
			primaryStage.setScene(main);
		});
		
		Label stats = new Label("Generate statistics \nBy Year");
		TextField year = new TextField();
		Label optional = new Label("Additionally specify area using Eircode routing key");
		TextField area = new TextField();
		Button getStats = new Button("Search");
		Button getOwnerData = new Button("Search");
		Button getPropertyData = new Button("Search");
		Button getOverDues = new Button("Search");
		
		Label propertyLabel = new Label("Enter the properties Eircode to view its payment history");
		TextField property = new TextField();
	
		Label selectOwner = new Label("Enter an owner id to view their payment history");
		TextField ownerHistory = new TextField();
		
		Label overdues = new Label("Enter a year to view all its outstanding balances");
		TextField overdueYear = new TextField();
		
		GridPane adminActions = new GridPane();
		adminActions.add(stats, 0, 0);
		adminActions.add(year, 1, 0);
		adminActions.add(optional, 0, 1);
		adminActions.add(area, 1, 1);
		adminActions.add(getStats, 2, 1);
		adminActions.add(propertyLabel, 0, 2);
		adminActions.add(property, 1, 2);
		adminActions.add(getPropertyData, 2, 2);
		adminActions.add(selectOwner, 0, 3);
		adminActions.add(ownerHistory, 1, 3);
		adminActions.add(getOwnerData, 2, 3);
		adminActions.add(overdues, 0, 4);
		adminActions.add(overdueYear, 1, 4);
		adminActions.add(getOverDues, 2, 4);
	
		adminActions.setVgap(12);
		adminActions.setHgap(5);
		adminActions.setAlignment(Pos.CENTER);
		
		adminMenu.setCenter(adminActions);
		adminMenu.setTop(logout2);
		adminMenu.setPadding(defaultPadding);
		BorderPane.setAlignment(logout2, Pos.TOP_RIGHT);
		
		Button newProperty = new Button("Register Property");
		Button viewProperties = new Button("View Properties");
		Button pay = new Button("Pay tax");
		Button viewStatement = new Button("View Balancing \nStatement");
		Button updateDetails = new Button("Update Property \nDetails");
		
				
		VBox ownerActions = new VBox(12);
		ownerActions.getChildren().addAll(newProperty,viewProperties,pay,viewStatement,updateDetails);
		ownerActions.setPrefWidth(120);
		ownerMenu.setTop(logout1);
		ownerMenu.setCenter(ownerActions);
		ownerMenu.setPadding(defaultPadding);
		BorderPane.setAlignment(logout1, Pos.TOP_RIGHT);
		
		newProperty.setMinWidth(ownerActions.getPrefWidth());
		viewProperties.setMinWidth(ownerActions.getPrefWidth());
		pay.setMinWidth(ownerActions.getPrefWidth());
		viewStatement.setMinWidth(ownerActions.getPrefWidth());
		updateDetails.setMinWidth(ownerActions.getPrefWidth()); 
		
		PropertyPane propertyPane = new PropertyPane();
		propertyList.setCenter(propertyPane);
		propertyList.setTop(back);
		propertyList.setPadding(defaultPadding);
		BorderPane.setAlignment(back, Pos.TOP_RIGHT);
		
		login.setOnAction(e -> { 
			System.out.println("Login was pressed");
			String[] userData = Utilities.filter(Utilities.readFromFile("systemLogins.csv"), "ID",userInput.getText()).get(1);
			if(password.getText().equals(userData[1]))
			{
				if(userInput.getText().equals("dept.employee"))
				{
					System.out.println("Successful login as department personnel");
					primaryStage.setScene(admin);
				}
				else
				{
					System.out.println("Successful login as property owner" + userInput.getText());
					primaryStage.setScene(main);
				}
			}
		});

		newUser.setOnAction(e -> {
			Alert alert = new Alert(AlertType.WARNING,"Please confirm your status as a property owner to register" ,ButtonType.YES);
			alert.showAndWait();
			primaryStage.setScene(registration);
		});
		
		register.setOnAction(e -> { 
				System.out.println("New owner regitered " + name.getText());
				ArrayList<String> currentOwners = Utilities.readFromColumn("systemLogins.csv", 0);
				int ownerID = Integer.parseInt(currentOwners.get(currentOwners.size() -1)) + 1;
				Alert alert = new Alert(AlertType.INFORMATION,"You have successfully registered \nPlease note your owner id :" + ownerID + "\nYou will now be redirected to log in with your new account",ButtonType.NEXT);
				alert.showAndWait();
				String[] newOwner= {Integer.toString(ownerID), password.getText(),name.getText()};
				Utilities.writeToFile("systemLogins.csv",newOwner);
				primaryStage.setScene(home);	
		});
		
		viewProperties.setOnAction(e -> {
			System.out.println("Logged in as owner " + userInput.getText());
			propertyPane.insertProperties(getUserProperties(userInput.getText()));
			primaryStage.setScene(propertyView);
			});
				
		newProperty.setOnAction(e -> primaryStage.setScene(addProperty));
		updateDetails.setOnAction(e -> primaryStage.setScene(update));
		logout1.setOnAction(e -> primaryStage.setScene(home));
		logout2.setOnAction(e -> primaryStage.setScene(home));
		back.setOnAction(e -> {
			propertyPane.clearGrid();
			primaryStage.setScene(main);
			});
		 
		
		primaryStage.setTitle("Tax Management System");
		primaryStage.setScene(main); //home
		primaryStage.show();
	}


	private ArrayList<String[]> getUserProperties(String id) { 
		return Utilities.filter(Utilities.readFromFile("properties.csv"), "Owner_id",id);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}



