
package CQMCORS;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import static CQMCORS.App.dairyList;
import java.util.Collections;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Joshua Turner - s0258441
 * file: FarmController.java
 * purpose: Assessment 2 - COIT11134
 * class for controlling the dairies window
 */

public class DairyController {

    @FXML
    private ComboBox<String> dairyComboBox;

    @FXML
    private Button filterButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button addDairyButton;

    @FXML
    private TableView<Dairy> dairyTable;

    @FXML
    private TableColumn<Dairy, String> nameCol;

    @FXML
    private TableColumn<Dairy, String> addressCol;

    @FXML
    private TableColumn<Dairy, String> postcodeCol;

    @FXML
    private TableColumn<Dairy, String> abnCol;

    @FXML
    private TableColumn<Dairy, String> phoneCol;

    @FXML
    private TableColumn<Dairy, String> emailCol;

    @FXML
    private TableColumn<Dairy, Integer> accountRefCol;
    
    @FXML
    private Button clearButton;

    // list used to store strings for the dairy combo box
    private ObservableList<String> dairyOptionsList;
    
    // list used to display all dairy objects in the display table
    private ObservableList<Dairy> dairyTableList;
    
    // list used to display filtered resuls in the display table
    private ObservableList<Dairy> dairyFilteredList;

    @FXML
    void initialize() 
    {        
        // initialise the display table
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(
                new PropertyValueFactory<>("address"));        
        postcodeCol.setCellValueFactory(
                new PropertyValueFactory<>("postcode"));        
        abnCol.setCellValueFactory(
                new PropertyValueFactory<>("ABN"));        
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<>("contactPhone"));        
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("email"));        
        accountRefCol.setCellValueFactory(
                new PropertyValueFactory<>("accountRefNum"));        
       
        // instantiate the dairies options list
        dairyOptionsList = FXCollections.observableArrayList();
        
        // instantiate the list for all objects to be displayed
        dairyTableList = FXCollections.observableArrayList();
        
        // instantiate the list for filtered objects to be displayed
        dairyFilteredList = FXCollections.observableArrayList();
       
        // display all current dairy objects
        initialiseTable();
        
        // populate the dairy combo box in the filter section
        initialiseDairyFilterList();        
    }
    
    // action taken when the add button is pressed
    @FXML
    public void addButtonPressed() throws IOException
    {
        showInputDialog();
        initialize();
    }
    
    // action taken when filter button is pressed
    @FXML
    public void filterButtonPressed() throws IOException
    {
        // confirm dairy selection has been made
        if(dairyComboBox.getSelectionModel().getSelectedItem() == null)
        {
            //configure error message
            String error = "Please select a Dairy to filter.";
            
            // display error alert
            App.showErrorMessage("Invalid Filter Input", error);
            
            // return focus to the input field
            dairyComboBox.requestFocus();
            return;
        }
       
        // clear the curent list to prevent double ups or incorrect results
        dairyFilteredList.clear();
        
        // loop through the main array list
        for(int i = 0; i < App.dairyList.size(); ++i)
        {
            // when dairy with matching name is found
            if(App.dairyList.get(i).getName().
                        equals(dairyComboBox.getSelectionModel().getSelectedItem()))
                {
                    // add the dairy to the filtered list                    
                    dairyFilteredList.add(App.dairyList.get(i));
                }
        }
        
        // display the results in the display table
        dairyTable.setItems(dairyFilteredList);
    }
    
    // action taken when the clear button is pressed
    @FXML
    public void clearButtonPressed()
    {
        // reset the table to show all
        initialiseTable();
        
        // clear dairy filter selection
        dairyComboBox.getSelectionModel().clearSelection();        
        
        // return focus to the name field
        addDairyButton.requestFocus();
    }
    
    
    // action taken when the menu button is pressed
    @FXML
    public void menuButtonPressed() throws IOException
    {
        // call the set root method from the app class
        App.setRoot("Primary");
    }
    
    // action taken when the exit button is pressed
    @FXML
    public void exitButtonPressed() throws IOException
    {
        // call the close window method from the app class
        App.showCloseDialog();
    }
    
    // initialise the dairy names that appear in the filer combo box
    private void initialiseDairyFilterList()
    {
        // check if dairies have been entered
        if(App.dairyList.isEmpty())
        {   // if none have been entered add below text to dropdown
            dairyOptionsList.add("No dairies entered");
        }
        else
        { // if the array has dairies the add dairies to drop down
            // clear the current list
            dairyOptionsList.clear();

            // loop through the array list that stores all the objects
            for(int i = 0; i < App.dairyList.size(); ++i)
            {
                //get dairy name from the object and add it to the list
                dairyOptionsList.add(App.dairyList.get(i).getName());
            }
        }
            // re-populate the combo box
            dairyComboBox.setItems(dairyOptionsList);
    }
    
    // display all Dairy objects in the table
    private void initialiseTable()
    {
        // clear the table
        dairyTableList.clear();
        
        // sort the array list
        Collections.sort(App.dairyList);
        
        // loop through the Dairy array list
        for(int i = 0; i < App.dairyList.size(); ++i)
        {
            //add each object to the local observable list
            dairyTableList.add(App.dairyList.get(i));
        }
        
        // display list in the table
        dairyTable.setItems(dairyTableList);
    }
    
    /**
     * display input dialog for adding a dairy object
     */
    private void showInputDialog()
    {
        Dairy dairy;      // create local object
        
        Dialog dialog = new Dialog<>();     // new dialog pane
        
        dialog.setTitle(App.WINDOW_TITLE);      // add title
        dialog.setHeaderText("Enter new dairy");     // add header
        
        ButtonType addInputButton = new         // initialise add button
                ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        
        dialog.getDialogPane().getButtonTypes().        // Set the button types
                addAll(addInputButton, ButtonType.CANCEL);

        // create the grid padding and set the sizes
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 30, 30, 30));

        // create the text fields
        TextField dairyNameTextField = new TextField();
        dairyNameTextField.setPromptText("Dairy name");
        TextField addressTextField = new TextField();
        addressTextField.setPromptText("Address");
        TextField postcodeTextField = new TextField();
        postcodeTextField.setPromptText("Postcode");
        TextField phoneTextField = new TextField();
        phoneTextField.setPromptText("Phone number");        
        TextField accountRefTextField = new TextField();
        accountRefTextField.setPromptText("Account reference number");
        TextField abnTextField = new TextField();
        abnTextField.setPromptText("ABN");
        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Email");

        // add labels and textfields to the gridpane
        grid.add(new Label("Farm name"), 0, 0);
        grid.add(dairyNameTextField, 1, 0);
        grid.add(new Label("*"), 2, 0);
        grid.add(new Label("Address"), 0, 1);
        grid.add(addressTextField, 1, 1);
        grid.add(new Label("*"), 2, 1);
        grid.add(postcodeTextField, 3, 1);
        grid.add(new Label("*"), 4, 1);
        grid.add(new Label("ABN"), 0, 2);
        grid.add(abnTextField, 1, 2);
        grid.add(new Label("*"), 2, 2);
        grid.add(new Label("Phone"), 0, 3);
        grid.add(phoneTextField, 1, 3);
        grid.add(new Label("*"), 2, 3);
        grid.add(new Label("Email"), 0, 4);
        grid.add(emailTextField, 1, 4);
        grid.add(new Label("*"), 2, 4);
        grid.add(new Label("Account reference"), 0, 5);
        grid.add(accountRefTextField, 1, 5);
        grid.add(new Label("*"), 2, 5);
        grid.add(new Label("* Required fields"), 1, 6);

        dialog.getDialogPane().setContent(grid);    // add the grid to the dialog
        
        // disable add button if all required inputs have not been entered
        Button add = (Button) dialog.getDialogPane().
                                lookupButton(addInputButton);

        add.disableProperty().bind(dairyNameTextField.textProperty().isEmpty().
                or(addressTextField.textProperty().isEmpty()).
                or(postcodeTextField.textProperty().isEmpty()).
                or(abnTextField.textProperty().isEmpty()).
                or(phoneTextField.textProperty().isEmpty()).
                or(emailTextField.textProperty().isEmpty()).       
                or(accountRefTextField.textProperty().isEmpty()));
        
        // validate inputs
        add.addEventFilter(ActionEvent.ACTION, event -> 
        {
            try
            {
                //validate account reference number is not negative
                if(Double.parseDouble(accountRefTextField.getText()) < 0)
                {
                    //configure error message
                    String error = "Account reference number cannot be negative.";

                    // display error alert
                    App.showErrorMessage("Input Error",error);

                    // return focus to the input field
                    accountRefTextField.requestFocus();
                    event.consume();                        
                }
            }
            catch(NumberFormatException ex)
            {
                // create error message
                String message = "You have entered an invalid character " + 
                        "in the account reference number field" +
                        "\n\nPlease enter numeric characters only.";

                // display the exception error window
                App.showExceptionDialog(ex, message);

                event.consume();
            }
        });
        
        // Request focus on the dairy name field by default.
        Platform.runLater(() -> dairyNameTextField.requestFocus());
        
        // display the dialog and wait for a button to be pressed
        Optional<ButtonType> result = dialog.showAndWait();
        
        // if the add button is pressed
        if(result.isPresent() && result.get() == addInputButton)
        {
            // attempt to create object
            try{
                // instantiate temporary object
                dairy = new Dairy(Integer.parseInt(accountRefTextField.getText()),
                        dairyNameTextField.getText(),
                        addressTextField.getText(),
                        postcodeTextField.getText(),
                        phoneTextField.getText(),
                        emailTextField.getText(),
                        abnTextField.getText());

                // add object to dairy list
                App.dairyList.add(dairy);
            } // if incorrect data type has been entered an exception will be thrown
            catch(NumberFormatException ex)
            {
                // create error message
                String message = "You have entered an invalid character " + 
                        "in the account reference number field" +
                        "\n\nPlease enter numeric characters only.";

                // display the exception error window
                App.showExceptionDialog(ex, message);
            }
        }
    }
}