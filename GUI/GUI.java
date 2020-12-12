import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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
import javafx.stage.Stage;

public class GUI extends Application {
	//Owner owner;
	@Override
	public void start(Stage primaryStage) {
		Insets defaultPadding = new Insets(15,15,15,15); 
				
		Button logout1 = new Button("Logout");
		Button logout2 = new Button("Logout");
		Button ownerBack1 = new Button("Back");
		Button ownerBack2 = new Button("Back");
		Button ownerBack3 = new Button("Back");
		Button adminBack1 = new Button("Back");
		Button adminBack2 = new Button("Back");
		Button adminBack3 = new Button("Back");
		Button adminBack4 = new Button("Back");
		
		BorderPane homePane = new BorderPane();
		GridPane registrationForm = new GridPane();
		BorderPane ownerMenu = new BorderPane();
		BorderPane adminMenu = new BorderPane();
		GridPane propertyDetails = new GridPane();
		BorderPane propertyList = new BorderPane();
		BorderPane updateProperty = new BorderPane();
		BorderPane paymentList = new BorderPane();
		BorderPane ownerHistoryList = new BorderPane();
		BorderPane propertyHistoryList = new BorderPane();
		BorderPane overdueList = new BorderPane();
				
		Scene home = new Scene(homePane, 700, 700);
		Scene registration = new Scene(registrationForm,700,700);
		Scene main = new Scene(ownerMenu, 700, 700);
		Scene admin = new Scene(adminMenu, 700, 700);
		Scene addProperty = new Scene(propertyDetails,700,700);
		Scene propertyView = new Scene(propertyList,700,700);
		Scene update = new Scene(updateProperty,700,700);
		Scene payment = new Scene(paymentList,700,700);
		Scene ownerHistory = new Scene(ownerHistoryList,700,700);
		Scene propertyHistory = new Scene(propertyHistoryList,700,700);
		Scene overDues = new Scene(overdueList,700,700);
		
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
		
		add.setOnAction(e -> {
			new Property(ownerid.getText(),address.getText().replace("\n", " "),eircode.getText(),locations.getValue(),Double.parseDouble(formatPrice(price.getText())),primRes.isSelected());
			primaryStage.setScene(main);
		});
		
	
		TextField year = new TextField();
		TextField optionalArea = new TextField();
		TextField property = new TextField();
		TextField searchOwner = new TextField();
		TextField routingArea = new TextField();
		
		Button getAreaData = new Button("Search");
		Button getOwnerData = new Button("Search");
		Button getPropertyData = new Button("Search");
		Button getOverdues = new Button("Search");
		
		GridPane adminActions = new GridPane();
		adminActions.add(new Label("Enter a year to view all its outstanding balances"), 0, 0);
		adminActions.add(year, 1, 0);
		adminActions.add(new Label("Additionally specify area using Eircode routing key"), 0, 1);
		adminActions.add(optionalArea, 1, 1);
		adminActions.add(getOverdues, 2, 1);
		adminActions.add(new Label("Enter the properties Eircode to view its payment history"), 0, 2);
		adminActions.add(property, 1, 2);
		adminActions.add(getPropertyData, 2, 2);
		adminActions.add(new Label("Enter an owner id to view their payment history"), 0, 3);
		adminActions.add(searchOwner, 1, 3);
		adminActions.add(getOwnerData, 2, 3);
		adminActions.add(new Label("Generate stats for an area uing Eircode routing key"), 0, 4);
		adminActions.add(routingArea, 1, 4);
		adminActions.add(getAreaData, 2, 4);
	
	
		adminActions.setVgap(12);
		adminActions.setHgap(5);
		adminActions.setAlignment(Pos.CENTER);
		
		adminMenu.setCenter(adminActions);
		adminMenu.setTop(logout2);
		adminMenu.setPadding(defaultPadding);
		BorderPane.setAlignment(logout2, Pos.TOP_RIGHT);
		
		Button newProperty = new Button("Register Property");
		Button viewProperties = new Button("View Properties");
		Button viewPayments = new Button("Pay tax");
		Button viewStatement = new Button("View Balancing \nStatement");
		Button goToUpdate = new Button("Update Property \nDetails");
		Button pay = new Button("Pay");
		
		VBox ownerActions = new VBox(12);
		ownerActions.getChildren().addAll(newProperty,viewProperties,viewPayments,viewStatement,goToUpdate);
		ownerActions.setPrefWidth(120);
		ownerActions.setAlignment(Pos.CENTER);
		ownerMenu.setTop(logout1);
		ownerMenu.setCenter(ownerActions);
		ownerMenu.setPadding(defaultPadding);
		BorderPane.setAlignment(logout1, Pos.TOP_RIGHT);
				
		newProperty.setMinWidth(ownerActions.getPrefWidth());
		viewProperties.setMinWidth(ownerActions.getPrefWidth());
		viewPayments.setMinWidth(ownerActions.getPrefWidth());
		viewStatement.setMinWidth(ownerActions.getPrefWidth());
		goToUpdate.setMinWidth(ownerActions.getPrefWidth()); 
		
		Table propertyPane = new Table();
		propertyList.setCenter(propertyPane);
		propertyList.setTop(ownerBack1);
		propertyList.setPadding(defaultPadding);
		BorderPane.setAlignment(ownerBack1, Pos.TOP_RIGHT);
		
		ComboBox<String> taxesDue = new ComboBox<String>();
		paymentList.setTop(ownerBack2);
		paymentList.setBottom(pay);
		paymentList.setPadding(new Insets(15,15,200,15));
		BorderPane.setAlignment(ownerBack2, Pos.TOP_RIGHT);
		BorderPane.setAlignment(pay, Pos.CENTER);
		
		RadioButton updatePrimRes = new RadioButton("Yes");
		RadioButton updateNotPrimRes = new RadioButton("No");
		ToggleGroup updateRadioButtons = new ToggleGroup();
		updatePrimRes.setToggleGroup(updateRadioButtons);
		updateNotPrimRes.setToggleGroup(updateRadioButtons);
		HBox updateResidentStatus = new HBox(10);
		updateResidentStatus.getChildren().addAll(updatePrimRes,updateNotPrimRes);
		TextField updateValue = new TextField();
		Button updateDetails = new Button("Update");
		
		ComboBox<String> properties = new ComboBox<String>();
		GridPane updateForm = new GridPane();
		updateForm.add(properties, 0,0);
		updateForm.add(new Label("Is this property a principla private residence"), 0, 1);
		updateForm.add(updateResidentStatus,1, 1);
		updateForm.add(new Label("Enter current estimate market value"),0,2);
		updateForm.add(updateValue,1,2);		
		updateForm.add(updateDetails,2,3);
		
		updateProperty.setTop(ownerBack3);
		updateProperty.setCenter(updateForm);
		updateProperty.setPadding(defaultPadding);
		updateForm.setAlignment(Pos.CENTER);
		updateForm.setHgap(5);
		updateForm.setVgap(5);
		BorderPane.setAlignment(adminBack1, Pos.TOP_RIGHT);
		
		ownerHistoryList.setTop(adminBack1);
		ownerHistoryList.setPadding(defaultPadding);
		Table ownerData = new Table();
		ownerHistoryList.setCenter(ownerData);
		BorderPane.setAlignment(ownerBack3, Pos.TOP_RIGHT);
		
		propertyHistoryList.setTop(adminBack2);
		propertyHistoryList.setPadding(defaultPadding);
		Table propertyData = new Table();
		propertyHistoryList.setCenter(propertyData);
		BorderPane.setAlignment(adminBack2, Pos.TOP_RIGHT);
		
		overdueList.setTop(adminBack3);
		overdueList.setPadding(defaultPadding);
		Table overDueData = new Table();
		overdueList.setCenter(overDueData);
		BorderPane.setAlignment(adminBack3, Pos.TOP_RIGHT);
		
		login.setOnAction(e -> { 
			System.out.println("Login was pressed");
			String[] userData = Utilities.filter(Utilities.readFromFile("systemLogins.csv"),"ID",userInput.getText()).get(1);
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
			propertyPane.displayPropertyDetails(getOwnerProperties(userInput.getText()));
			primaryStage.setScene(propertyView);
			});
				
		viewPayments.setOnAction(e -> {
			ArrayList<String> dropdown = getPropertiesUnpaid(userInput.getText());
			dropdown.remove(0);
			ObservableList<String> selection = FXCollections.observableList(dropdown);
			taxesDue.setItems(selection);			
			paymentList.setCenter(taxesDue);
			primaryStage.setScene(payment);
		});
		
		pay.setOnAction(e -> {
			String[] data = getTaxDetails(taxesDue.getValue());
			System.out.println(taxesDue.getValue() + " has a tax of  " + taxPayable(data));
			Alert alert = new Alert(AlertType.CONFIRMATION,"Please confirm your payment of €" + taxPayable(data));
			Optional<ButtonType> decision = alert.showAndWait();
			if(decision.get() == ButtonType.OK)
			{
				Utilities.writeToCell("taxPayments.csv", true,data, "Paid");
			}
			
			primaryStage.setScene(main);
		});
		
		goToUpdate.setOnAction(e -> {
			ArrayList<String> list = Utilities.readFromColumn(getOwnerProperties(userInput.getText()),1);
			list.remove(0);
			ObservableList<String> selection = FXCollections.observableList(list);
			properties.setItems(selection);			
			primaryStage.setScene(update);
		});
		
		updateDetails.setOnAction(e -> {
			String[] row = getPropertyDetails(properties.getValue());
			Utilities.writeToCell("properties.csv", updatePrimRes.isSelected(), row, "Principal Residence");
			row = getPropertyDetails(properties.getValue());
			Utilities.writeToCell("properties.csv", formatPrice(updateValue.getText()), row, "Estimated Market Value");
			primaryStage.setScene(main);
		});
		
		getOwnerData.setOnAction(e -> {
			System.out.println("Getting payment for owner " + searchOwner.getText());
			ArrayList<String[]> data = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Owner_id", searchOwner.getText());
			for(String[]s:data)
			{
				System.out.println(Arrays.toString(s)); // remove after
			}
			
			ownerData.displayOwnerHistory(data);
			primaryStage.setScene(ownerHistory);
		});
		
		getPropertyData.setOnAction(e -> {
			System.out.println("Getting payment for property " + property.getText());
			ArrayList<String[]> data = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Eircode",property.getText());
			for(String[]s:data)
			{
				System.out.println(Arrays.toString(s)); // remove after
			}
			
			propertyData.displayPropertyHistory(data);
			primaryStage.setScene(propertyHistory);
		});
		
		getOverdues.setOnAction(e -> {
			System.out.println("Getting overdues for year " + year.getText());
			ArrayList<String[]> data = Utilities.filter(Utilities.readFromFile("taxPayments.csv"),"Paid",false);
			for(String[]s:data)
			{
				System.out.println(Arrays.toString(s)); // remove after
			}
			data = Utilities.filter(data,"Year",year.getText());
			for(String[]s:data)
			{
				System.out.println(Arrays.toString(s)); // remove after
			}
			if(!optionalArea.getText().isEmpty())
			{
				System.out.println("Getting overdues for year " + year.getText() + " and optional area" + optionalArea.getText());
				data = Utilities.filter(data, "Eircode",optionalArea.getText());
			}
			overDueData.displayOverdues(data);
			for(String[]s:data)
			{
				System.out.println(Arrays.toString(s)); // remove after
			}
			primaryStage.setScene(overDues);
		});
		
		adminBack1.setOnAction(e -> {
			ownerData.clearGrid();
			primaryStage.setScene(admin);
		});
		
		adminBack2.setOnAction(e -> {
			propertyData.clearGrid();
			primaryStage.setScene(admin);
		});
		
		adminBack3.setOnAction(e -> {
			overDueData.clearGrid();
			primaryStage.setScene(admin);
		});
		
		newProperty.setOnAction(e -> primaryStage.setScene(addProperty));
		
		logout1.setOnAction(e -> primaryStage.setScene(home));
		logout2.setOnAction(e -> primaryStage.setScene(home));
		ownerBack1.setOnAction(e -> {
			propertyPane.clearGrid();
			primaryStage.setScene(main);
			});
		
		 ownerBack2.setOnAction(e -> {
			 paymentList.setCenter(null);
		     primaryStage.setScene(main);
		 });
		 
		ownerBack3.setOnAction(e -> primaryStage.setScene(main));
				
		primaryStage.setTitle("Tax Management System");
		primaryStage.setScene(home);
		primaryStage.show();
	}

	
	
	
	
	
	
	
	// perhpas move later 
	private ArrayList<String[]> getOwnerProperties(String id) { 
		return Utilities.filter(Utilities.readFromFile("properties.csv"), "Owner_id",id);
	}
	
	private ArrayList<String> getPropertiesUnpaid(String id){
		ArrayList<String[]> properties = Utilities.filter(Utilities.readFromFile("taxPayments.csv"),"Owner_id",id);
		properties = Utilities.filter(properties, "Year", LocalDate.now().getYear());
		ArrayList<String[]> unpaid = Utilities.filter(properties,"Paid",false);
		return Utilities.readFromColumn(unpaid,0);
	}
	
	// this is a useless method
	private int taxPayable(String[] details) {
		return Integer.parseInt(details[4]);
	}
	
	private String[] getTaxDetails(String id) {
		ArrayList<String[]> data = Utilities.filter(Utilities.readFromFile("taxPayments.csv"), "Address", id);
		data = Utilities.filter(data, "Year", LocalDate.now().getYear());
		data.remove(0);
		return data.get(0);
	}
	
	private String[] getPropertyDetails(String address) {
		ArrayList<String[]> data = Utilities.filter(Utilities.readFromFile("properties.csv"), "Address", address);
		data.remove(0);
		return data.get(0);
	}
	
	private String formatPrice(String price) {
		return price.replace(",", "");
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}



