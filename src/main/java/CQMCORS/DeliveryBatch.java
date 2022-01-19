
package CQMCORS;

import java.time.LocalDate;

/**
 * @author Joshua Turner - s0258441
 * file: DeliveryBatch.java
 * purpose: Assessment 2 - COIT11134
 * class for storing details of a delivery batch, sub-class of the MilkBatch class
 */

public class DeliveryBatch extends MilkBatch
{
    private String tankerRegNum;    // rego number of the truck delivery the batch
    private String dairy;           // name of the dairy the delivery is for

    // default constructor
    public DeliveryBatch(String tankerRegNum, String dairy)
    {
        super();
        this.tankerRegNum = "Empty";
        this.dairy = "Empty";
    }

    /**
     * parameterised constructor
     * @param tankerRegNum holds the truck rego number as a string
     * @param dairy hold the dairy's name as a string
     * @param date holds the date of the batch as a LocalDate object
     * @param quantity holds the quantity of milk(kL) as a double
     */
    public DeliveryBatch(String tankerRegNum, String dairy, LocalDate date, double quantity)
    {
        super(date, quantity);
        this.tankerRegNum = tankerRegNum;
        this.dairy = dairy;
    }

    // returns the rego number of the truck
    public String getTankerRegNum()
    {
        return tankerRegNum;
    }
    
    /**
     * set the rego number of the truck
     * @param tankerRegNum holds the rego number as a string
     */
    public void setTankerRegNum(String tankerRegNum)
    {
        this.tankerRegNum = tankerRegNum;
    }

    // return the name of the dairy
    public String getDairy()
    {
        return dairy;
    }

    /**
     * set the name of the dairy
     * @param dairy holds the dairy name as a string
     */
    public void setDairy(String dairy)
    {
        this.dairy = dairy;
    }

    // toString to be use to display the details of the delivery batch
    @Override
    public String toString()
    {
        return super.toString() + 
                String.format("%75s%75s\n", tankerRegNum, dairy);
    }
    
    // used to print to save file
    @Override
    public String printToFile()
    {
        return super.printToFile() + "|" + tankerRegNum + "|" + dairy;
    }
}
