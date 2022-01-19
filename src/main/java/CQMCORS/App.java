
package CQMCORS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.WindowEvent;

/**
 * @author Joshua Turner - s0258441
 * file: App.java
 * purpose: Assessment 2 - COIT11134
 * main class for running the application
 */

public class App extends Application {

    private static Scene scene;
    
    // declare all the ArraysList so they are available to all controllers
    public static ArrayList<Farm> farmList;
    public static ArrayList<Dairy> dairyList;
    public static ArrayList<DeliveryBatch> deliveryBatchList;
    public static ArrayList<SupplyBatch> supplyBatchList;
    
    // file paths
    private static String farmFile;
    private static String dairyFile;
    private static String batchFile;
    private static String deliveryFile;
    private static final String SUB_PATH = "/src/main/java/cqmcors/data/";
    
    
    private static Formatter output; //used for writing to files
    
    public static final String WINDOW_TITLE = "CQMC - Operations Recording System";

    @Override
    public void start(Stage stage) throws IOException 
    {
        scene = new Scene(loadFXML("primary"), 1000, 700);
        stage.setScene(scene);
        stage.show();
        
        
         // initialise all the arraylists for the program
        farmList = new ArrayList<>();
        dairyList = new ArrayList<>();
        deliveryBatchList = new ArrayList<>();
        supplyBatchList = new ArrayList<>();
        
        // initialise file paths
        farmFile = new File("").getAbsolutePath() + SUB_PATH +
                "farm-data.txt";
        dairyFile = new File("").getAbsolutePath() + SUB_PATH +
                "dairy-data.txt";
        batchFile = new File("").getAbsolutePath() + SUB_PATH +
                "batch-data.txt";
        deliveryFile = new File("").getAbsolutePath() + SUB_PATH +
                "delivery-data.txt";
        
        readAllData();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                closeWindowEvent(e);
            }
        });
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args)
    {
        launch();
    }

    /**
     * method used to display prompt when exiting window
     */
    public static void showCloseDialog()
    {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Are you sure you want to quit?", 
                    ButtonType.YES,
                    ButtonType.NO);
        a.setTitle(WINDOW_TITLE);
        a.setContentText("Are you sure you want to quit?");
        
        ButtonType closeButton = new ButtonType("Exit without saving");
        ButtonType closeSaveButton = new ButtonType("Save and exit");
        //ButtonType cancelButton = new ButtonType("Cancel". ButtonData.CANCEL_CLOSE);
        
        a.getButtonTypes().setAll(closeButton, closeSaveButton, ButtonType.CANCEL);
        
        Optional<ButtonType> confirm = a.showAndWait();
        if (confirm.isPresent() && confirm.get() == closeButton) 
        {
            System.exit(0);
        }
        else if (confirm.isPresent() && confirm.get() == closeSaveButton) 
        {
            saveAllData();
            
            System.exit(0);
        }         
    }
    
    /**
     * close window as an event
     * @param event the calling event
     */
    public static void closeWindowEvent(WindowEvent event)
    {
        // show the close window dialog
        showCloseDialog();
 
        // if the cancel button is pressed consume the event
        event.consume();        
    }
    
    /**
     * display an error dialog for input errors
     * @param title contain the title for the type of error
     * @param message contains the string to be displayed in the dialog
     */
    public static void showErrorMessage(String title, String message)
    {
        Alert a = new Alert(Alert.AlertType.ERROR, 
                    message);
        
        a.setTitle("Input Error");
        a.setHeaderText("Error - " + title);
        a.show();
    }
    
    
    /**
     * display an error dialog for input errors
     * @param title contains title for the window
     * @param message contains the string to be displayed in the dialog
     */
    public static void showInfoMessage(String title, String message)
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION, 
                    message);
        
        a.setTitle(WINDOW_TITLE);
        a.setHeaderText(title);
        a.setContentText(message);
        a.show();
    }
    
    //method for activate text data in the program to allow for easy testing
    private void initialiseTestData()
    {
        // test farms
        farmList.add(new Farm(45678921, 123789, "Red Hill Farm Pty Ltd",
                "92 Gorge Road, Spring Valley", "2760", "0428 789 123", 
                "redhill@gmail.com", "89 466 213 213"));
        farmList.add(new Farm(45678921, 123789, "Dairy Valley Pty Ltd",
                "20 Valley Road, Spring Valley", "2760", "0454 687 493", 
                "dairy.valley@outlook.com", "20 467 845 329"));
        farmList.add(new Farm(45678921, 123789, "NL & JM Seymour",
                "502 Mountain View Rd, Meadowvale", "2840", "02 4567 8456", 
                "n.j.seymour3@bigpond.com", "12 847 613 258"));
        
        // test dairies
        dairyList.add(new Dairy(465384, "Big Break Milk Co.",
                "20 Watson St, Parramatta", "2150", "02 8146 1234", 
                "purchasing@bbmilkco.com", "89 484 125 547"));
        dairyList.add(new Dairy(464816, "Only Milk Pty Ltd",
                "20 Wooroona St, Newcastle", "2300", "1300 467 846", 
                "accounts@onlymilk.com", "90 846 819 236"));
        
        // test batches
        supplyBatchList.add(new SupplyBatch("Un-adultered", 
                "Red Hill Farm Pty Ltd", LocalDate.of(2021, 8, 19), 25.5));
        supplyBatchList.add(new SupplyBatch("Un-adultered", 
                "Red Hill Farm Pty Ltd", LocalDate.of(2021, 8, 19), 30.0));
        supplyBatchList.add(new SupplyBatch("Un-adultered", 
                "Red Hill Farm Pty Ltd", LocalDate.of(2021, 8, 20), 80.0));
        supplyBatchList.add(new SupplyBatch("Adultered", 
                "Dairy Valley Pty Ltd", LocalDate.of(2021, 8, 18), 45.0));
        supplyBatchList.add(new SupplyBatch("Un-adultered", 
                "Dairy Valley Pty Ltd", LocalDate.of(2021, 8, 20), 50.0));
        supplyBatchList.add(new SupplyBatch("Un-adultered", 
                "NL & JM Seymour", LocalDate.of(2021, 8, 16), 65.25));
        supplyBatchList.add(new SupplyBatch("Un-adultered", 
                "NL & JM Seymour", LocalDate.of(2021, 8, 16), 34.75));
        
        // test deliveries
        deliveryBatchList.add(new DeliveryBatch("WRT 781", 
                "Big Break Milk Co.", LocalDate.of(2021, 8, 18), 75.0));
        deliveryBatchList.add(new DeliveryBatch("BRE 496", 
                "Big Break Milk Co.", LocalDate.of(2021, 8, 18), 70.0));
        deliveryBatchList.add(new DeliveryBatch("185 NNM", 
                "Only Milk Pty Ltd", LocalDate.of(2021, 8, 19), 55.5));
        deliveryBatchList.add(new DeliveryBatch("147 OPT", 
                "Only Milk Pty Ltd", LocalDate.of(2021, 8, 20), 65.0));
        deliveryBatchList.add(new DeliveryBatch("789 WER", 
                "Only Milk Pty Ltd", LocalDate.of(2021, 8, 20), 65.0));
    }
    
    /**
     * Save data to respective files
     */
    public static void readAllData()
    {
        try
        {
            readFarmDataFile();
        } 
        catch (FileNotFoundException ex)
        {
            showExceptionDialog(ex, "Farm data file not found");
        } 
        catch (NoSuchElementException ex)
        {
            showExceptionDialog(ex, "Farm data file contain invalid data");
        }
        
        try
        {
            readDairyDataFile();
        } 
        catch (FileNotFoundException ex)
        {
            showExceptionDialog(ex, "Dairy data file not found");
        }
        catch (NoSuchElementException ex)
        {
            showExceptionDialog(ex, "Dairy data file contain invalid data");
        }
        
        try
        {
            readBatchDataFile();
        } 
        catch (FileNotFoundException ex)
        {
            showExceptionDialog(ex, "Batch data file not found");
        }
        catch (NoSuchElementException ex)
        {
            showExceptionDialog(ex, "Batch data file contain invalid data");
        }
        
        try
        {
            readDeliveryDataFile();
        } 
        catch (FileNotFoundException ex)
        {
            showExceptionDialog(ex, "Delivery data file not found");
        }
        catch (NoSuchElementException ex)
        {
            showExceptionDialog(ex, "Delivery data file contain invalid data");
        }
    }
    
    public static void readFarmDataFile() throws FileNotFoundException, NoSuchElementException
    {
        try
        {
            Scanner in = new Scanner(new FileReader(farmFile)); //open file
            
            String myEntry= "";
            
            // initialised local variables
            int bankAccountNum = 0;
            int bankBSBNum = 0;
            String name = "";
            String address = "";
            String postcode = "";
            String contactPhone = "";
            String email = "";
            String ABN = "";
            
            // loop through each line of the text file
            while(in.hasNextLine())
            {
                // create tokenizer for reading the text file
                myEntry = in.nextLine();
                StringTokenizer st = new StringTokenizer(myEntry,"|");
                
                // get the details from each line
                while(st.hasMoreTokens())
                {                 
                    name = st.nextToken();
                    address = st.nextToken();
                    postcode = st.nextToken();
                    contactPhone = st.nextToken();
                    email = st.nextToken();
                    ABN = st.nextToken();
                    bankAccountNum = Integer.parseInt(st.nextToken());
                    bankBSBNum = Integer.parseInt(st.nextToken());
                    
                }
                
                // add line to farm array list
                farmList.add(new Farm(bankAccountNum, bankBSBNum, name, address,
                                        postcode, contactPhone, email, ABN));
                
            }// end of while loop
            in.close();//close file
        } 
        catch(ArrayIndexOutOfBoundsException ex)  
        {
            App.showExceptionDialog(ex, "Farm ArrayList out of bounds exception");
        }  
        catch(IOException ex)   
        {
            App.showExceptionDialog(ex, "Error reading the farm data file");
        }
    }//end of readDataFarmFile method
    
    public static void readDairyDataFile() throws FileNotFoundException, NoSuchElementException
    {
        try
        {
            Scanner in = new Scanner(new FileReader(dairyFile)); //open file
            
            String myEntry= "";
            
            // initialised local variables
            int accountRefNum = 0;
            String name = "";
            String address = "";
            String postcode = "";
            String contactPhone = "";
            String email = "";
            String ABN = "";
            
            // loop through each line of the text file
            while(in.hasNextLine())
            {
                // create tokenizer for reading the text file
                myEntry = in.nextLine();
                StringTokenizer st = new StringTokenizer(myEntry,"|");
                
                // get the details from each line
                while(st.hasMoreTokens())
                {                 
                    name = st.nextToken();
                    address = st.nextToken();
                    postcode = st.nextToken();
                    contactPhone = st.nextToken();
                    email = st.nextToken();
                    ABN = st.nextToken();
                    accountRefNum = Integer.parseInt(st.nextToken());
                }
                
                // add line to farm array list
                dairyList.add(new Dairy(accountRefNum, name, address,
                                        postcode, contactPhone, email, ABN));
                
            }// end of while loop
            in.close();//close file
        } 
        catch(ArrayIndexOutOfBoundsException ex)  
        {
            App.showExceptionDialog(ex, "Dairy ArrayList out of bounds exception");
        }  
        catch(IOException ex)   
        {
            App.showExceptionDialog(ex, "Error reading the dairy data file");
        }
    }//end of readDairyDataFile method
    
    public static void readBatchDataFile() throws FileNotFoundException, NoSuchElementException
    {
        try
        {
            Scanner in = new Scanner(new FileReader(batchFile)); //open file
            
            String myEntry= "";
            
            // initialised local variables
            LocalDate date = LocalDate.now();
            double quantity = 0.0;
            String testResult = "";
            String farm = "";
            
            // loop through each line of the text file
            while(in.hasNextLine())
            {
                // create tokenizer for reading the text file
                myEntry = in.nextLine();
                StringTokenizer st = new StringTokenizer(myEntry,"|");
                
                // get the details from each line
                while(st.hasMoreTokens())
                {
                    date = LocalDate.parse(st.nextToken());
                    quantity = Double.parseDouble(st.nextToken());
                    testResult = st.nextToken();
                    farm = st.nextToken();
                }
                
                // add line to farm array list
                supplyBatchList.add(new SupplyBatch(testResult, farm, date, quantity));
                
            }// end of while loop
            in.close();//close file
        } 
        catch(ArrayIndexOutOfBoundsException ex)  
        {
            App.showExceptionDialog(ex, "Supply batch ArrayList out of bounds exception");
        }  
        catch(IOException ex)   
        {
            App.showExceptionDialog(ex, "Error reading the batch data file");
        }
    }//end of readBatchDataFile method
    
    public static void readDeliveryDataFile() throws FileNotFoundException, NoSuchElementException
    {
        try
        {
            Scanner in = new Scanner(new FileReader(deliveryFile)); //open file
            
            String myEntry= "";
            
            // initialised local variables
            LocalDate date = LocalDate.now();
            double quantity = 0.0;
            String tankerRegNum = "";
            String dairy = "";
            
            // loop through each line of the text file
            while(in.hasNextLine())
            {
                // create tokenizer for reading the text file
                myEntry = in.nextLine();
                StringTokenizer st = new StringTokenizer(myEntry,"|");
                
                // get the details from each line
                while(st.hasMoreTokens())
                {
                    date = LocalDate.parse(st.nextToken());
                    quantity = Double.parseDouble(st.nextToken());
                    tankerRegNum = st.nextToken();
                    dairy = st.nextToken();
                }
                
                // add line to farm array list
                deliveryBatchList.add(new DeliveryBatch(tankerRegNum, dairy, date, quantity));
                
            }// end of while loop
            in.close();//close file
        } 
        catch(ArrayIndexOutOfBoundsException ex)  
        {
            App.showExceptionDialog(ex, "Delivery batch ArrayList out of bounds exception");
        }  
        catch(IOException ex)   
        {
            App.showExceptionDialog(ex, "Error reading the delivery data file");
        }
    }//end of readBatchDataFile method
    
    
    /**
     * display stack trace exception dialog
     * @param throwable the exception thrown
     * @param message unique message for how the error occurred
     */
    public static void showExceptionDialog(Throwable throwable, String message) 
    {
        // print the stack trace to the console
        //throwable.printStackTrace();

        // create aleart window and set titles
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(WINDOW_TITLE);
        alert.setHeaderText("Exception Thrown");
        alert.setContentText(message);

        // create stack trace string
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Stacktrace details:");

        // create text area and add stacktrace string
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        
        // set size and behaviour of text area
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        // create new gridpane and add the label and text area
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // add the above gridpane to the alert window
        alert.getDialogPane().setExpandableContent(expContent);

        // display the alert
        alert.showAndWait();
    }
    
    /**
     * Save data to respective files
     */
    public static void saveAllData()
    {
        try
        {
            saveFarmData();
        } catch (FileNotFoundException ex)
        {
            showExceptionDialog(ex, "Farm data file not found");
        }
        try
        {
            saveDairyData();
        } catch (FileNotFoundException ex)
        {
            showExceptionDialog(ex, "Dairy data file not found");
        }
        try
        {
            saveBatchData();
        } catch (FileNotFoundException ex)
        {
            showExceptionDialog(ex, "Batch data file not found");
        }
        try
        {
            saveDeliveryData();
        } catch (FileNotFoundException ex)
        {
            showExceptionDialog(ex, "Delivery data file not found");
        }
    }
    
    
    /**
     * method use to save farm data
     * @throws FileNotFoundException 
     */
    public static void saveFarmData() throws FileNotFoundException
    {
        try{
            output = new Formatter(farmFile);
       
        }
        catch(IOException ex){
            App.showExceptionDialog(ex, "Error opening farm data file");
        }
        
        try{
            for(int i = 0; i < farmList.size(); i++)
            {
                output.format("%s\n", farmList.get(i).printToFile());
            }
            
        }
        catch(FormatterClosedException ex){
            App.showExceptionDialog(ex, "Error writing to farm data file");
        }
        catch(NoSuchElementException ex){
            App.showExceptionDialog(ex, "Invalid input please try again");
        }
        
        output.close();
    }
    
    /**
     * method use to save dairy data
     * @throws FileNotFoundException 
     */
    public static void saveDairyData() throws FileNotFoundException
    {
        try{
            output = new Formatter(dairyFile);
       
        }
        catch(IOException ex){
            App.showExceptionDialog(ex, "Error opening dairy data file");
        }
        
        try{
            for(int i = 0; i < dairyList.size(); i++)
            {
                output.format("%s\n", dairyList.get(i).printToFile());
            }
            
        }
        catch(FormatterClosedException ex){
            App.showExceptionDialog(ex, "Error writing to dairy data file");
        }
        catch(NoSuchElementException ex){
            App.showExceptionDialog(ex, "Invalid input please try again");
        }
        
        output.close();
    }
    
    /**
     * method use to save batch data
     * @throws FileNotFoundException 
     */
    public static void saveBatchData() throws FileNotFoundException
    {
        try{
            output = new Formatter(batchFile);
       
        }
        catch(IOException ex){
            App.showExceptionDialog(ex, "Error opening batch data file");
        }
        
        try{
            for(int i = 0; i < supplyBatchList.size(); i++)
            {
                output.format("%s\n", supplyBatchList.get(i).printToFile());
            }
            
        }
        catch(FormatterClosedException ex){
            App.showExceptionDialog(ex, "Error writing to batch data file");
        }
        catch(NoSuchElementException ex){
            App.showExceptionDialog(ex, "Invalid input please try again");
        }
        
        output.close();
    }
    
    /**
     * method use to save delivery batch data
     * @throws FileNotFoundException 
     */
    public static void saveDeliveryData() throws FileNotFoundException
    {
        try{
            output = new Formatter(deliveryFile);
       
        }
        catch(IOException ex){
            App.showExceptionDialog(ex, "Error opening delivery data file");
        }
        
        try{
            for(int i = 0; i < deliveryBatchList.size(); i++)
            {
                output.format("%s\n", deliveryBatchList.get(i).printToFile());
            }
            
        }
        catch(FormatterClosedException ex){
            App.showExceptionDialog(ex, "Error writing to delivery data file");
        }
        catch(NoSuchElementException ex){
            App.showExceptionDialog(ex, "Invalid input please try again");
        }
        
        output.close();
    }
    
    /**
     * find the lowest quantity in the passed list
     * @return the lowest quantity in the list
     */
    public static double findLowestBatch()
    {
        double lowest = supplyBatchList.get(0).getQuantity(); // set to high value

        for(int i = 1; i < supplyBatchList.size(); ++i)	// loop through the arraylist
        {
            if(supplyBatchList.get(i).getQuantity() < lowest) // test if each value is lower than the lowest
                lowest = supplyBatchList.get(i).getQuantity();	// if it is, make is the lowest value
        }

        return lowest;	// return the lowest value
    }
    
    /**
     * find the lowest quantity in the passed list
     * @return the lowest quantity in the list
     */
    public static double findLowestDelivery()
    {
        double lowest = deliveryBatchList.get(0).getQuantity(); // set to high value

        for(int i = 1; i < deliveryBatchList.size(); ++i)	// loop through the arraylist
        {
            if(deliveryBatchList.get(i).getQuantity() < lowest) // test if each value is lower than the lowest
                lowest = deliveryBatchList.get(i).getQuantity();	// if it is, make is the lowest value
        }

        return lowest;	// return the lowest value
    }

    /**
     * find the highest quantity in the passed list
     * @return the highest quantity in the list
     */
    public static double findHighestBatch()
    {
        double highest = supplyBatchList.get(0).getQuantity(); // set to high value

        for(int i = 1; i < supplyBatchList.size(); ++i)	// loop through the arraylist
        {
            if(supplyBatchList.get(i).getQuantity() > highest)     // test if each value is highest than the highest
                highest = supplyBatchList.get(i).getQuantity();	// if it is, make is the lowest value
        }

        return highest;	// return the highest value
    } 
    
    /**
     * find the highest quantity in the passed list
     * @return the highest quantity in the list
     */
    public static double findHighestDelivery()
    {
        double highest = deliveryBatchList.get(0).getQuantity(); // set to high value

        for(int i = 1; i < deliveryBatchList.size(); ++i)	// loop through the arraylist
        {
            if(deliveryBatchList.get(i).getQuantity() > highest)     // test if each value is highest than the highest
                highest = deliveryBatchList.get(i).getQuantity();	// if it is, make is the lowest value
        }

        return highest;	// return the highest value
    } 
}