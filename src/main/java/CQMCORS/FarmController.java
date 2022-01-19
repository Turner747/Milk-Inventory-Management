
package CQMCORS;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import static CQMCORS.App.farmList;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Joshua Turner - s0258441
 * file: FarmController.java
 * purpose: Assessment 2 - COIT11134
 * class for controlling the farms window
 */

public class FarmController 
{
    @FXML
    private ComboBox<String> farmComboBox;

    @FXML
    private Button filterButton;
    
    @FXML
    private Button clearButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button addFarmButton;

     @FXML
    private TableView<Farm> farmTable;

    @FXML
    private TableColumn<Farm, String> nameCol;

    @FXML
    private TableColumn<Farm, String> addressCol;

    @FXML
    private TableColumn<Farm, String> postcodeCol;

    @FXML
    private TableColumn<Farm, String> abnCol;

    @FXML
    private TableColumn<Farm, String> phoneCol;

    @FXML
    private TableColumn<Farm, String> emailCol;

    @FXML
    private TableColumn<Farm, Integer> bsbCol;

    @FXML
    private TableColumn<Farm, Integer> accountCol;
    
    private ObservableList<String> farmOptionsList; // used to fill combobox
    
    private ObservableList<Farm> farmTableList;     // used to fill display table
    
    private ObservableList<Farm> farmFilteredList;  // used to display filtered results
    
    

    @FXML
    void initialize() 
    {
        // initailise the display table
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
        bsbCol.setCellValueFactory(
                new PropertyValueFactory<>("bankBSBNum"));        
        accountCol.setCellValueFactory(
                new PropertyValueFactory<>("bankAccountNum"));        
        
        // create the farm name list to used in the filter combo box
        farmOptionsList = FXCollections.observableArrayList();
        
        // create the farm object list that will display all objects in the display table
        farmTableList = FXCollections.observableArrayList();
        
        // create the farm object list that will dusplay filtered objects in the display table
        farmFilteredList = FXCollections.observableArrayList();
       
        // fill the diplay table all current objects
        initialiseTable();
        
        // fill the filter combo box with current farm names
        initialiseFarmFilterList();        
    }
    
    // action taken when the add button is pressed
    @FXML
    public void addButtonPressed() throws IOException
    {
       showInputDialog();
       initialiseTable();
    }
    
    // action taken when filter button is pressed
    @FXML
    public void filterButtonPressed() throws IOException
    {
        // confirm farm selection has been made
        if(farmComboBox.getSelectionModel().getSelectedItem() == null)
        {
            //configure error message
            String error = "Please select a Farm to filter.";
            
            // display error alert
            App.showErrorMessage("Invalid Filter Input", error);
            
            // return focus to the input field
            farmComboBox.requestFocus();
            return;
        }
        
        // clear the list of any previous added objects
        farmFilteredList.clear();
        
        // loop through the main array
        for(int i = 0; i < App.farmList.size(); ++i)
        {
            // when farm with matching name is found
            if(App.farmList.get(i).getName().
                        equals(farmComboBox.getSelectionModel().getSelectedItem()))
                {
                    // print the farm's to string                      
                    farmFilteredList.add(App.farmList.get(i));
                }
        }
        
        // display the filtered list in the display table
        farmTable.setItems(farmFilteredList);
    }
    
    // actions taken when the clear button is pressed
    @FXML
    public void clearButtonPressed()
    {
        // reset the table to show all
        initialiseTable();
        
        // clear farm filter combo box selection
        farmComboBox.getSelectionModel().clearSelection();
        
        // return focus to the name field
        addFarmButton.requestFocus();
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
    
    // populate the farm combo box in the filter section
    private void initialiseFarmFilterList()
    {
        // determine is farms have been entered
        if(App.farmList.isEmpty())
        { // if not farms have been entered add below text
            farmOptionsList.add("No farms entered");
        }
        else
        { // if farms have been enter populate list with farms
            // clear the list to prevent double ups
            farmOptionsList.clear();

            // loop trhouhg the main array list
            for(int i = 0; i < App.farmList.size(); ++i)
            {
                //get farm name and add in to the farm options list
                farmOptionsList.add(App.farmList.get(i).getName());
            }
        }
            // add the list to the farm combo box
            farmComboBox.setItems(farmOptionsList);   // set farm list in batch creation
    }
    
    // populate the display table with all farm objects
    private void initialiseTable()
    {
        // th eexisting list to prevent double ups
        farmTableList.clear();
        
        // sort the array list
        Collections.sort(App.farmList);
        
        // loop through the main array list
        for(int i = 0; i < App.farmList.size(); ++i)
        {
            // add the farm object to the local list
            farmTableList.add(App.farmList.get(i));
        }
        
        // display the list in the table
        farmTable.setItems(farmTableList);
    }
    
    /**
     * display input dialog for adding a farm object
     */
    private void showInputDialog()
    {
        Farm farm;      // create local object
        
        Dialog dialog = new Dialog<>();     // new dialog pane
        
        dialog.setTitle(App.WINDOW_TITLE);      // add title
        dialog.setHeaderText("Enter new farm");     // add header
        
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
        TextField farmNameTextField = new TextField();
        farmNameTextField.setPromptText("Farm name");
        TextField addressTextField = new TextField();
        addressTextField.setPromptText("Address");
        TextField postcodeTextField = new TextField();
        postcodeTextField.setPromptText("Postcode");
        TextField phoneTextField = new TextField();
        phoneTextField.setPromptText("Phone number");
        TextField bsbTextField = new TextField();
        bsbTextField.setPromptText("BSB number");
        TextField accountTextField = new TextField();
        accountTextField.setPromptText("Account number");
        TextField abnTextField = new TextField();
        abnTextField.setPromptText("ABN");
        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Email");

        // add labels and textfields to the gridpane
        grid.add(new Label("Farm name"), 0, 0);
        grid.add(farmNameTextField, 1, 0);
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
        grid.add(new Label("Bank Details"), 0, 5);
        grid.add(bsbTextField, 1, 5);
        grid.add(new Label("*"), 2, 5);
        grid.add(accountTextField, 3, 5);
        grid.add(new Label("*"), 4, 5);
        grid.add(new Label("* Required fields"), 1, 6);

        dialog.getDialogPane().setContent(grid);    // add the grid to the dialog
        
        // disable add button if all required inputs have not been entered
        Button add = (Button) dialog.getDialogPane().
                                lookupButton(addInputButton);

        add.disableProperty().bind(farmNameTextField.textProperty().isEmpty().
                or(addressTextField.textProperty().isEmpty()).
                or(postcodeTextField.textProperty().isEmpty()).
                or(abnTextField.textProperty().isEmpty()).
                or(phoneTextField.textProperty().isEmpty()).
                or(emailTextField.textProperty().isEmpty()).
                or(bsbTextField.textProperty().isEmpty()).
                or(accountTextField.textProperty().isEmpty()));
        
        // Request focus on the farm name field by default.
        Platform.runLater(() -> farmNameTextField.requestFocus());
        
        // validate inputs
        add.addEventFilter(ActionEvent.ACTION, event -> 
        {
            try
            {
                //validate account reference number is not negative
                if(Double.parseDouble(accountTextField.getText()) < 0 ||
                        Double.parseDouble(bsbTextField.getText()) < 0 )
                {
                    //configure error message
                    String error = "Bank details cannot be negative.";

                    // display error alert
                    App.showErrorMessage("Input Error",error);

                    // return focus to the input field
                    accountTextField.requestFocus();
                    event.consume();                        
                }
            }
            catch(NumberFormatException ex)
            {
                // create error message
                String message = "You have entered an invalid character " + 
                        "in the BSB or bank account number field" +
                        "\n\nPlease enter numeric characters only.";

                // display the exception error window
                App.showExceptionDialog(ex, message);

                event.consume();
            }
        });
        
        // display the dialog and wait for a button to be pressed
        Optional<ButtonType> result = dialog.showAndWait();
        
        // if the add button is pressed
        if(result.isPresent() && result.get() == addInputButton)
        {
            // attempt to create object
            try{
                // instantiate temporary object
                farm = new Farm(Integer.parseInt(accountTextField.getText()),
                        Integer.parseInt(bsbTextField.getText()),
                        farmNameTextField.getText(),
                        addressTextField.getText(),
                        postcodeTextField.getText(),
                        phoneTextField.getText(),
                        emailTextField.getText(),
                        abnTextField.getText());

                // add object to farm list
                App.farmList.add(farm);
            } // if incorrect data type has been entered an exception will be thrown
            catch(NumberFormatException ex)
            {
                // create error message
                String message = "You have entered an invalid character " + 
                        "in the BSB or bank account number field" +
                        "\n\nPlease enter numeric characters only.";

                // display the exception error window
                App.showExceptionDialog(ex, message);
            }
        }
    }  
}

