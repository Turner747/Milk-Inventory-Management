
package CQMCORS;

import static CQMCORS.App.WINDOW_TITLE;
import static CQMCORS.DeliveryController.getAvailableQuantity;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Joshua Turner - s0258441
 * file: PrimaryController.java
 * purpose: Assessment 1 - COIT11134
 * Controller for primary FXML window
 */

public class PrimaryController {

    @FXML
    private Button batchesButton;

    @FXML
    private Button deliveriesButton;

    @FXML
    private Button farmsButton;

    @FXML
    private Button dairiesButton;

    @FXML
    private Button exitButton;
    
    @FXML
    private Button saveButton;    
    
    @FXML
    private Button aboutButton;
    

    @FXML
    void initialize() 
    {
       

    }
    
    // action taken when the batches button is pressed
    @FXML
    public void batchButtonPressed() throws IOException
    {
        // call the set root method from the app class
        App.setRoot("Batch");
    }
    
    // action taken when the deliveries button is pressed
    @FXML
    public void deliveryButtonPressed() throws IOException
    {
        // call the set root method from the app class
        App.setRoot("Delivery");
    }
    
    // action taken when the farms button is pressed
    @FXML
    public void farmButtonPressed() throws IOException
    {
        // call the set root method from the app class
        App.setRoot("Farm");
    }
    
    // action taken when the dairies button is pressed
    @FXML
    public void dairyButtonPressed() throws IOException
    {
        // call the set root method from the app class
        App.setRoot("Dairy");
    }
    
    // action taken when the exit button is pressed
    @FXML
    public void saveButtonPressed() throws IOException
    {
        // call the close window method from the app class
        App.saveAllData();
        
        App.showInfoMessage("Save successful","All data has been saved successfully.");
    }
    
    // action taken when the exit button is pressed
    @FXML
    public void exitButtonPressed() throws IOException
    {
        // call the close window method from the app class
        App.showCloseDialog();
    }
    
   // action taken when the about button is pressed
    @FXML
    public void aboutButtonPressed() throws IOException
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        
        a.setTitle(WINDOW_TITLE);
        a.setHeaderText("About");
        
        // create the grid padding and set the sizes
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 30, 20, 30));
        
        // add labels and textfields to the gridpane
        grid.add(new Label("Developed by:"), 0, 0);
        grid.add(new Label("Joshua Turner"), 1, 0);
        
        grid.add(new Label("Student Number:"), 0, 1);
        grid.add(new Label("s0258441"), 1, 1);        
        
        grid.add(new Label("Version Number:"), 0, 2);
        grid.add(new Label("2"), 1, 2);
        
        grid.add(new Label("Version Date:"), 0, 3);
        grid.add(new Label("1 October 2021"), 1, 3);
        
        a.getDialogPane().setContent(grid);    // add the grid to the dialog
        
        a.show();
    }
}
