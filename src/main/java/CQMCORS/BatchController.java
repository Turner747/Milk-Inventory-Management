
package CQMCORS;

import static CQMCORS.App.supplyBatchList;
import static CQMCORS.App.farmList;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 * @author Joshua Turner - s0258441
 * file: BatchController.java
 * purpose: Assessment 2 - COIT11134
 * class for controlling the batches window
 */

public class BatchController 
{
    @FXML
    private Button addBatchButton;
    
    @FXML
    private TableView<SupplyBatch> outputTable;

    @FXML
    private TableColumn<SupplyBatch, LocalDate> dateColumn;

    @FXML
    private TableColumn<SupplyBatch, String> farmColumn;

    @FXML
    private TableColumn<SupplyBatch, Double> quantityColumn;

    @FXML
    private TableColumn<SupplyBatch, String> testResultColumn;

    @FXML
    private DatePicker filterDatePicker;

    @FXML
    private ComboBox<String> farmFilterComboBox;

    @FXML
    private Button filterButton;
    
    @FXML
    private TextField totalQuantityTextField;
    
    @FXML
    private TextField averageQuantityTextField;
    
    
    @FXML
    private TextField lowestQuantityTextField;

    @FXML
    private TextField highestQuantityTextField;
    
    @FXML
    private Button clearButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button exitButton;
    
    @FXML
    private final ObservableList<String> resultsOptions =     // test result options
            FXCollections.observableArrayList("Adultered", "Un-adultered");
    
    // list of farm names for the batch input and filter
    private ObservableList<String> farmOptionsList;
    
    // list to be used to display all batches
    private ObservableList<SupplyBatch> batchList;
    
    // list to be used to display filtered results
    private ObservableList<SupplyBatch> filteredList;
    
    private boolean filterDateUnchanged = true; // state of the filter date picker
    

    @FXML
    void initialize() 
    {        
        // initialise the batch display table
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        
        farmColumn.setCellValueFactory(
                new PropertyValueFactory<>("farm"));
        
        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));
        
        testResultColumn.setCellValueFactory(
                new PropertyValueFactory<>("testResult"));
        
        // instantiate the list for all batches
        batchList = FXCollections.observableArrayList(); 
        
        // instantiate the list of filtered batches
        filteredList = FXCollections.observableArrayList(); 
        
        // fill farms list with options
        farmOptionsList = FXCollections.observableArrayList(); // create list of strings
        
        // determine is farms have been entered
        if(App.farmList.isEmpty())
        { // if not farms have been entered add below text
            farmOptionsList.add("No farms entered");
        }
        else
        { // if farms have been enter populate list with farms
            // loop through the main array
            for(int i = 0; i < App.farmList.size(); ++i)
            {
                //get farm name and add in to the farm options list
                farmOptionsList.add(App.farmList.get(i).getName());
            }
        }
        farmFilterComboBox.setItems(farmOptionsList);   // set farm list in filter
        
        initialiseOutputFields();
    }
    
    // action taken when the add button is pressed
    @ FXML
    public void addButtonPressed() throws IOException
    {
        // check that a 
        if (App.farmList.isEmpty())// if none have been entered
        {
            // display an error
            App.showErrorMessage("No Farms Entered", 
                    "Please enter farm details before creating a batch");
        }
        else // if a farm has been entered
        {
            showInputDialog();            // display input window
            initialiseOutputFields();     // refresh display fields
        }
    }
    
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
        
        // check that farm has been selected
        if(farmFilterComboBox.getSelectionModel().getSelectedItem() == null)
        {
            //configure error message
            String error = "Please select the Farm to be filtered.";
            
            // display error alert
            App.showErrorMessage("Invalid Filter Input", error);
            
            // return focus to the input field
            farmFilterComboBox.requestFocus();
            return;
        }
        
        // declare the filtered total quantity
        double filteredQuantity = 0.0;
        double filteredAverage;
        double filteredHighest = 0.0;
        double filteredLowest = 10000000000.0;
        int filterItems = 0;
       
        // clear the filtered results list to prevent previous filters showing up
        filteredList.clear();
        
        // loop through the main array list
        for(int i = 0; i < App.supplyBatchList.size(); ++i)
        {
            // if the batch ojbect matches the requirements of the filter
            if(App.supplyBatchList.get(i).getFarm().
                    equals(farmFilterComboBox.getSelectionModel().getSelectedItem())
                    && App.supplyBatchList.get(i).getDate().
                    equals(filterDatePicker.getValue()))
            {
                // add the batch to the filter list
                filteredList.add(App.supplyBatchList.get(i));
                // get the quantity of the batch and add it to the filtered total
                filteredQuantity += App.supplyBatchList.get(i).getQuantity();
                filterItems += 1; // iterate for the average calculation
                
                if(App.supplyBatchList.get(i).getQuantity() < filteredLowest) // test if each value is lower than the lowest
                    filteredLowest = App.supplyBatchList.get(i).getQuantity();	// if it is, make is the lowest value
                
                if(supplyBatchList.get(i).getQuantity() > filteredHighest)     // test if each value is highest than the highest
                    filteredHighest = App.supplyBatchList.get(i).getQuantity();	// if it is, make is the lowest value
            }
        }
        
        // display the filtered list in the output table
        outputTable.setItems(filteredList);
        
        filteredAverage = filteredQuantity / filterItems;
        
        // display the filtered total in the total textfield
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
    
    // actions taken when the clear button is pressed
    @FXML
    public void clearButtonPressed()
    {
        // reset the display table to display all batches
        initialiseOutputFields();
        
        // clear filter input filters
        farmFilterComboBox.getSelectionModel().clearSelection();
        filterDatePicker.getEditor().clear();
        filterDateUnchanged = true;        
        
        // return focus to the top of the page
        addBatchButton.requestFocus();
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
    
    // method for calcuating the total quantity
    public static double calculateTotalQuantity()
    {
        // declare the total variable
        double totalQuantity = 0.0;
        
        // loop through the batch list
        for(int i = 0; i < App.supplyBatchList.size(); ++i)
        {
            // add the quantity from each supply batch to the total
            totalQuantity += App.supplyBatchList.get(i).getQuantity();
        }
        
        // return the total to the calling application
        return totalQuantity;
    }
    
    // method for calcuating the total quantity
    public static double calculateAverageQuantity()
    {
        // declare the total variable
        double averageQuantity = 0.0;
        
        averageQuantity = calculateTotalQuantity() / App.supplyBatchList.size();
        
        return averageQuantity;
    }
   
    // method use to initialised/reset the display table to display all batches
    private void initialiseTable()
    {
        // clear the current list of batches to prevent double ups
        batchList.clear();
        
        // sort the array list
        Collections.sort(App.supplyBatchList);
        
        // loop through the main array list 
        for(int i = 0; i < App.supplyBatchList.size(); ++i)
        {
            // add each batch to the list 
            batchList.add(App.supplyBatchList.get(i));
        }
        
        // display the list in the output display table
        outputTable.setItems(batchList);
    }
    
    // actioned taken with filter date picker is changed
    @FXML
    public void filterDateChanged()
    {
        filterDateUnchanged = false;
    }
    
    /**
     * display input dialog for adding a supply batch object
     */
    private void showInputDialog()
    {
        SupplyBatch batch;      // create local object
        
        Dialog dialog = new Dialog<>();     // new dialog pane
        
        dialog.setTitle(App.WINDOW_TITLE);      // add title
        dialog.setHeaderText("Enter new batch");     // add header
        
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
        
        ComboBox<String> farmComboBox = new ComboBox<>();
        farmComboBox.setPromptText("Select Farm");
        farmComboBox.setItems(farmOptionsList);  // set farm list in batch creation
        
        TextField quantityTextField = new TextField();
        quantityTextField.setPromptText("Enter Quantity");
        
        ComboBox<String> testResultComboBox = new ComboBox<>();
        testResultComboBox.setPromptText("Select result");
        testResultComboBox.setItems(resultsOptions);  // set test results options in batch creation
        
        // add labels and textfields to the gridpane
        grid.add(new Label("Batch Date"), 0, 0);
        grid.add(batchDatePicker, 1, 0);
        grid.add(new Label("*"), 2, 0);
        
        grid.add(new Label("Farm"), 0, 1);
        grid.add(farmComboBox, 1, 1);
        grid.add(new Label("*"), 2, 1);        
        
        grid.add(new Label("Quantity"), 0, 2);
        grid.add(quantityTextField, 1, 2);
        grid.add(new Label("*"), 2, 2);
        
        grid.add(new Label("Test Result"), 0, 3);
        grid.add(testResultComboBox, 1, 3);
        grid.add(new Label("*"), 2, 3);
        
        grid.add(new Label("* Required fields"), 1, 6);

        dialog.getDialogPane().setContent(grid);    // add the grid to the dialog
        
        // disable add button if all required inputs have not been entered
        Button add = (Button) dialog.getDialogPane().
                                lookupButton(addInputButton);

        add.disableProperty().bind(batchDatePicker.valueProperty().isNull().
                or(farmComboBox.valueProperty().isNull()).
                or(quantityTextField.textProperty().isEmpty()).
                or(testResultComboBox.valueProperty().isNull()));
        
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
                    App.showErrorMessage("Input Error",error);

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
                batch = new SupplyBatch(testResultComboBox.getSelectionModel().getSelectedItem(),
                        farmComboBox.getSelectionModel().getSelectedItem(),
                        batchDatePicker.getValue(),
                        Double.parseDouble(quantityTextField.getText()));

                // add object to dairy list
                App.supplyBatchList.add(batch);
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
    
    // populates all output fields
    private void initialiseOutputFields()
    {
        if(App.supplyBatchList.isEmpty())
        {
            // display the total quantity
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
            // display the total quantity
            totalQuantityTextField.setText(String.format("%.3f kL", 
                    calculateTotalQuantity()));         
            // display the average quantity
            averageQuantityTextField.setText(String.format("%.3f kL", 
                    calculateAverageQuantity()));        
            // display the lowest quantity
            lowestQuantityTextField.setText(String.format("%.3f kL", 
                    App.findLowestBatch()));
            // display the highest quantity
            highestQuantityTextField.setText(String.format("%.3f kL", 
                    App.findHighestBatch()));


            // display all current batch objects in the table
            initialiseTable();
        }
    }
    
}
