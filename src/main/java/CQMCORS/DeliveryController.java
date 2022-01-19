package CQMCORS;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static CQMCORS.App.deliveryBatchList;
import static CQMCORS.App.dairyList;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Joshua Turner - s0258441
 * file: DeliveryBatch.java
 * purpose: Assessment 2 - COIT11134
 * class for controlling the delivery window
 */

public class DeliveryController 
{
    
    @FXML
    private Button addButton;

    @FXML
    private DatePicker filterDatePicker;

    @FXML
    private ComboBox<String> dairyFilterComboBox;
    
    @FXML
    private TextField availQuantTextField;
    

    @FXML
    private Button filterButton;

    @FXML
    private Button menuBotton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField averageQuantityTextField;

    @FXML
    private TextField totalQuantityTextField;
    
    @FXML
    private TextField lowestQuantityTextField;

    @FXML
    private TextField highestQuantityTextField;

    @FXML
    private TableView<DeliveryBatch> outputTable;

    @FXML
    private TableColumn<DeliveryBatch, LocalDate> dateColumn;

    @FXML
    private TableColumn<DeliveryBatch, String> dairyColumn;

    @FXML
    private TableColumn<DeliveryBatch, Double> quantityColumn;

    @FXML
    private TableColumn<DeliveryBatch, String> tankerRegoColumn;

    @FXML
    private Button clearButton;

    private ObservableList<String> dairyOptionsList;    // list of dairy options
    
    private ObservableList<DeliveryBatch> deliveryList; // list of all delivery objects
    
    private ObservableList<DeliveryBatch> filteredList; // list of filered delivery objects
    
    private boolean filterDateUnchanged = true;   // used to track if the filter date picker has been changed
    
    @FXML
    void initialize() 
    { 
        // set up the display table to output the data
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        
        dairyColumn.setCellValueFactory(
                new PropertyValueFactory<>("dairy"));
        
        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
        
        tankerRegoColumn.setCellValueFactory(
                new PropertyValueFactory<>("tankerRegNum"));
        
        // create the list to store the delivery objects locally to be displayed
        deliveryList = FXCollections.observableArrayList(); 
        
        // create the list to store the filtered delivery objects locally to be displayed
        filteredList = FXCollections.observableArrayList(); 
        
        // fill  dairies list with options
        dairyOptionsList = FXCollections.observableArrayList(); // create list of strings
        
        // check if dairies have been entered
        if(App.dairyList.isEmpty())
        {   // if none have been entered add below text to dropdown
            dairyOptionsList.add("No dairies entered");
        }
        else
        { // if the array has dairies the add dairies to drop down
            // loop through the main array
            for(int i = 0; i < App.dairyList.size(); ++i)
            {
                //get dairy name and add in to the dairy options list
                dairyOptionsList.add(App.dairyList.get(i).getName());
            }
        }
        
        dairyFilterComboBox.setItems(dairyOptionsList);   // set dairy list in filter  
        
        initialiseOutputFields();
    }
    
    // actions taken when the add button is pressed
    @FXML
    public void addButtonPressed() throws IOException
    {
        // check that a 
        if (App.dairyList.isEmpty())// if none have been entered
        {
            // display an error
            App.showErrorMessage("No Dairies Entered", 
                    "Please enter dairy details before creating a delivery");
        }
        else // if a dairy has been entered
        {
            showInputDialog();            // display input window
            initialiseOutputFields();     // refresh display fields
        }
    }
        
    // actions taken when the filter button is pressed
    @FXML
    public void filterButtonPressed() throws IOException
    {
        // confirm input have been made
        
        // check date has been entered
        if(filterDateUnchanged)
        {
            //configure error message
            String error = "Please select a date to be filtered.";
            
            // display error alert
            App.showErrorMessage("Invalid Filter Input", error);
            
            // return focus to the input field
            filterDatePicker.requestFocus();
            return;
        }
        
        // check that dairy has been selected
        if(dairyFilterComboBox.getSelectionModel().getSelectedItem() == null)
        {
            //configure error message
            String error = "Please select the Dairy to be filtered.";
            
            // display error alert
            App.showErrorMessage("Invalid Filter Input", error);
            
            // return focus to the input field
            dairyFilterComboBox.requestFocus();
            return;
        }
        
        // declare the filtered total quantity variable
        double filteredQuantity = 0.0;
        double filteredAverage;
        double filteredHighest = 0.0;
        double filteredLowest = 1000000000.0;        
        int filterItems = 0;
        
        // clear the filter list to remove objects from previous filters
        filteredList.clear();
        
        // loop through the main array
        for(int i = 0; i < App.deliveryBatchList.size(); ++i)
        {
            // if a delivery object matches the date and dairy
            if(App.deliveryBatchList.get(i).getDairy().
                    equals(dairyFilterComboBox.getSelectionModel().getSelectedItem())
                    && App.deliveryBatchList.get(i).getDate().
                    equals(filterDatePicker.getValue()))
            {
                // add the delivery object to the list
                filteredList.add(App.deliveryBatchList.get(i));
                // add the objects quantity to the total filter quantity
                filteredQuantity += App.deliveryBatchList.get(i).getQuantity();
                filterItems += 1;
                
                if(App.deliveryBatchList.get(i).getQuantity() < filteredLowest) // test if each value is lower than the lowest
                    filteredLowest = App.deliveryBatchList.get(i).getQuantity();	// if it is, make is the lowest value
                
                if(App.deliveryBatchList.get(i).getQuantity() > filteredHighest)     // test if each value is highest than the highest
                    filteredHighest = App.deliveryBatchList.get(i).getQuantity();	// if it is, make is the lowest value
            }
        }
        
        // display the results of the fiter in the table
        outputTable.setItems(filteredList);
        
        filteredAverage = filteredQuantity / filterItems;
        
        // diplay the filtered total quantity
        totalQuantityTextField.setText(String.format("%.3f kL",  // set total quantity
                filteredQuantity));
        // display the average quantity
        averageQuantityTextField.setText(String.format("%.3f kL", 
                filteredAverage));
        // display the lowest quantity
        lowestQuantityTextField.setText(String.format("%.3f kL", 
                filteredLowest));
        // display the highest quantity
        highestQuantityTextField.setText(String.format("%.3f kL", 
                filteredHighest));
    }
    
    // action taken when filter button is pressed
    @FXML
    public void clearButtonPressed()
    {
        // reset the display table to show all delivery objects
        initialiseOutputFields();
        
        // clear filter input fields
        dairyFilterComboBox.getSelectionModel().clearSelection();
        filterDatePicker.getEditor().clear();
        dairyFilterComboBox.setPromptText("Select Dairy");
        filterDateUnchanged = true;
        
        // return focus to the delivery creation
        addButton.requestFocus();
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
    
    // method for calculating the totally quantity delivered
    private static double calculateTotalQuantity()
    {
        // declare the total varible
        double totalQuantity = 0.0;
        
        // loop through the main delivery array list
        for(int i = 0; i < App.deliveryBatchList.size(); ++i)
        {
            // add the quantity from each delivery object in the arraylist
            totalQuantity += App.deliveryBatchList.get(i).getQuantity();
        }
        
        // return the calculated total
        return totalQuantity;
    }
    
    // method for calcuating the total quantity
    public static double calculateAverageQuantity()
    {
        // declare the total variable
        double averageQuantity = 0.0;
        
        averageQuantity = calculateTotalQuantity() / App.deliveryBatchList.size();
        
        return averageQuantity;
    }
   
    // method for displaying all delivery objects in the main array
    private void initialiseTable()
    {
        // clear the current list to prevent double ups
        deliveryList.clear();
        
        // sort the array list
        Collections.sort(App.deliveryBatchList);
        
        // loop through the main delivery array
        for(int i = 0; i < App.deliveryBatchList.size(); ++i)
        {
            // add each delivery object to the local list
            deliveryList.add(App.deliveryBatchList.get(i));
        }
        
        // display the list in the table
        outputTable.setItems(deliveryList);
    }
    
    // actioned taken with filter date picker is changed
    @FXML
    public void filterDateChanged()
    {
        filterDateUnchanged = false;
    }
    
    /**
     * display input dialog for adding a delivery object
     */
    private void showInputDialog()
    {
        DeliveryBatch delivery;      // create local object
        
        Dialog dialog = new Dialog<>();     // new dialog pane
        
        dialog.setTitle(App.WINDOW_TITLE);      // add title
        dialog.setHeaderText("Enter new delivery");     // add header
        
        ButtonType addInputButton = new         // initialise add button
                ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        
        dialog.getDialogPane().getButtonTypes().        // Set the button types
                addAll(addInputButton, ButtonType.CANCEL);

        // create the grid padding and set the sizes
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 30, 30, 30));

        // create the input fields
        DatePicker batchDatePicker = new DatePicker();
        batchDatePicker.setPromptText("Select date");
        
        ComboBox<String> dairyComboBox = new ComboBox<>();
        dairyComboBox.setPromptText("Select Dairy");
        dairyComboBox.setItems(dairyOptionsList);  // set dairy list in delivery creation
        
        TextField quantityTextField = new TextField();
        quantityTextField.setPromptText("Enter Quantity");
        
        TextField tankerRegoTextField = new TextField();
        tankerRegoTextField.setPromptText("Enter tanker registration number");
        
        // add labels and textfields to the gridpane
        grid.add(new Label("Delivery Date"), 0, 0);
        grid.add(batchDatePicker, 1, 0);
        grid.add(new Label("*"), 2, 0);
        
        grid.add(new Label("Dairy"), 0, 1);
        grid.add(dairyComboBox, 1, 1);
        grid.add(new Label("*"), 2, 1);        
        
        grid.add(new Label("Quantity"), 0, 2);
        grid.add(quantityTextField, 1, 2);
        grid.add(new Label("*"), 2, 2);
        grid.add(new Label(String.format("Available: %.3f kL", getAvailableQuantity())), 3, 2);
        
        grid.add(new Label("Tanker Registration"), 0, 3);
        grid.add(tankerRegoTextField, 1, 3);
        grid.add(new Label("*"), 2, 3);
        
        grid.add(new Label("* Required fields"), 1, 6);

        dialog.getDialogPane().setContent(grid);    // add the grid to the dialog
        
        // disable add button if all required inputs have not been entered
        Button add = (Button) dialog.getDialogPane().
                                lookupButton(addInputButton);

        add.disableProperty().bind(batchDatePicker.valueProperty().isNull().
                or(dairyComboBox.valueProperty().isNull()).
                or(quantityTextField.textProperty().isEmpty()).
                or(tankerRegoTextField.textProperty().isEmpty()));
        
        // validate inputs
        add.addEventFilter(ActionEvent.ACTION, event -> 
        {
            try
            {
                //validate quantity is not negative
                if(Double.parseDouble(quantityTextField.getText()) < 0)
                {
                    //configure error message
                    String error = "Batch quantity cannot be less than zero.";

                    // display error alert
                    App.showErrorMessage("Invalid Input", error);

                    // return focus to the input field
                    quantityTextField.requestFocus();
                    event.consume();                        
                }

                //validate quantity is not more the the available
                if(Double.parseDouble(quantityTextField.getText()) > 
                        getAvailableQuantity())
                {
                    //configure error message
                    String error = "Batch quantity cannot be greater than the available quantity";

                    // display error alert
                    App.showErrorMessage("Invalid Input", error);

                    // return focus to the input field
                    quantityTextField.requestFocus();
                    event.consume();                        
                }
            }
            catch(NumberFormatException ex)
            {
                // create error message
                String message = "You have entered an invalid character " + 
                        "in the quantity field" +
                        "\n\nPlease enter numeric characters only.";

                // display the exception error window
                App.showExceptionDialog(ex, message);

                event.consume();
            }
        });
        
        // Request focus on the date field by default.
        Platform.runLater(() -> batchDatePicker.requestFocus());
        
        // display the dialog and wait for a button to be pressed
        Optional<ButtonType> result = dialog.showAndWait();
        
        // if the add button is pressed
        if(result.isPresent() && result.get() == addInputButton)
        {
            // attempt to create object
            try{
                // instantiate temporary object
                delivery = new DeliveryBatch(tankerRegoTextField.getText(),
                        dairyComboBox.getSelectionModel().getSelectedItem(),
                        batchDatePicker.getValue(),
                        Double.parseDouble(quantityTextField.getText()));

                // add object to dairy list
                App.deliveryBatchList.add(delivery);
            } // if incorrect data type has been entered an exception will be thrown
            catch(NumberFormatException ex)
            {
                // create error message
                String message = "You have entered an invalid character " + 
                        "in the quantity field" +
                        "\n\nPlease enter numeric characters only.";

                // display the exception error window
                App.showExceptionDialog(ex, message);
            }
        }
    }
    
    public static Double getAvailableQuantity()
    {
        double available;
        
        available = BatchController.calculateTotalQuantity() - calculateTotalQuantity();
        
        return available;
    }
    
    private void initialiseOutputFields()
    {
        if(App.supplyBatchList.isEmpty())
        {
            // display that not data has been entered
            availQuantTextField.setText("No content");
        }
        else
        {
            // display the current milk in storage
            availQuantTextField.setText(String.format("%.3f kL", getAvailableQuantity()));
        }
        
        if(App.deliveryBatchList.isEmpty())
        {
            // display the to quantity of milk delivered
            totalQuantityTextField.setText("No content");        
            // display the average quantity
            averageQuantityTextField.setText("No content");
            // display the lowest quantity
            lowestQuantityTextField.setText("No content");
            // display the highest quantity
            highestQuantityTextField.setText("No content");
        }
        else
        {        
            // display the to quantity of milk delivered
            totalQuantityTextField.setText(String.format("%.3f kL",  // set total quantity
                    calculateTotalQuantity()));        
            // display the average quantity
            averageQuantityTextField.setText(String.format("%.3f kL", 
                    calculateAverageQuantity()));
            // display the lowest quantity
            lowestQuantityTextField.setText(String.format("%.3f kL", 
                    App.findLowestDelivery()));
            // display the highest quantity
            highestQuantityTextField.setText(String.format("%.3f kL", 
                    App.findHighestDelivery()));

            // display all current deliveries in the display table
            initialiseTable();
        }
    }
}
