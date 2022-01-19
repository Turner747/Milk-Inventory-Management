
package CQMCORS;

import java.time.LocalDate;

/**
 * @author Joshua Turner - s0258441
 * file: SupplyBatch.java
 * purpose: Assessment 2 - COIT11134
 * class for storing details of a supply batch, sub-class of the MilkBatch class
 */

public class SupplyBatch extends MilkBatch
{
    private String testResult;      // test result (adulterated or un-adulterated)
    private String farm;            // name of the farm the batch is from

    // default constructor
    public SupplyBatch()
    {
        super();
        this.testResult = "Empty";
        this.farm = "Empty";
    }

    /**
     * parameterised constructor
     * @param testResult holds the test result as a string
     * @param farm hold the name of the farm as a string
     * @param date holds the date of the batch as a LocalDate object
     * @param quantity holds the quantity of milk(kL) as a double
     */
    public SupplyBatch(String testResult, String farm, LocalDate date, double quantity)
    {
        super(date, quantity);
        this.testResult = testResult;
        this.farm = farm;
    }

    // return the test result
    public String getTestResult()
    {
        return testResult;
    }
    
    /**
     * set the test result
     * @param testResult hold the test result as a string
     */
    public void setTestResult(String testResult)
    {
        this.testResult = testResult;
    }

    // returns the name of the farm the batch is from
    public String getFarm()
    {
        return farm;
    }

    /**
     * set the name of the farm
     * @param farm holds the name of the farm as a String
     */
    public void setFarm(String farm)
    {
        this.farm = farm;
    }

    // toString to be use to display the details of the supply batch
    @Override
    public String toString()
    {
         return super.toString() + 
                String.format("%75s%75s\n", testResult, farm);
    }
    
    // used to print to save file
    @Override
    public String printToFile()
    {
        return super.printToFile() + "|" + testResult + "|" + farm;
    }
}
