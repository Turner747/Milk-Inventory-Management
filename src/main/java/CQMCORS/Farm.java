
package CQMCORS;

/**
 * @author Joshua Turner - s0258441
 * file: Farm.java
 * purpose: Assessment 1 - COIT11134
 * class for storing farm details, sub-class of the partner class
 */

public class Farm extends Partner
{
    private int bankAccountNum;     // farm's bank account number
    private int bankBSBNum;         // farm's bank bsbs number

    // default constuctor
    public Farm()
    {
        super();
        this.bankAccountNum = 0;
        this.bankBSBNum = 0;
    }

    /**
     * 
     * @param bankAccountNum holds the farm's bank account number
     * @param bankBSBNum holds the farm's bank bsb number 
     * @param name holds full business name, passed to super constructor
     * @param address holds full address, passed to super constructor
     * @param postcode holds poscode, passed to super constructor
     * @param contactPhone holds contact phone number, passed to super constructor
     * @param email holds email, passed to super constructor
     * @param ABN holds australian business number, passed to super constructor
     */
    public Farm(int bankAccountNum, int bankBSBNum, String name, String address,
            String postcode, String contactPhone, String email, String ABN)
    {
        super(name, address, postcode, contactPhone, email, ABN);
        this.bankAccountNum = bankAccountNum;
        this.bankBSBNum = bankBSBNum;
    }
    
    // returns the farm's bank account number as a integer
    public int getBankAccountNum()
    {
        return bankAccountNum;
    }

    /**
     * set the farm's bank account number
     * @param bankAccountNum holds the farm's bank account number as a integer
     */
    public void setBankAccountNum(int bankAccountNum)
    {
        this.bankAccountNum = bankAccountNum;
    }

    // returns the farm's bank bsb number as a integer
    public int getBankBSBNum()
    {
        return bankBSBNum;
    }

    /**
     * set the farm's bank bsb number
     * @param bankBSBNum holds the bank account number as an integer
     */
    public void setBankBSBNum(int bankBSBNum)
    {
        this.bankBSBNum = bankBSBNum;
    }

    // toString to be used to dislay farm's details in the display test area
    @Override
    public String toString()
    {
        return super.toString() + 
                "\nBank Details:\nAccount number: " + bankAccountNum +
                "\n BSB number: " + bankBSBNum + "\n";
    }
    
    @Override
    public String printToFile()
    {
        return super.printToFile() + "|" + bankAccountNum + "|" + bankBSBNum;
    }
    
    
}
