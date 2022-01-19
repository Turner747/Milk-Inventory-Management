
package CQMCORS;

/**
 * @author Joshua Turner - s0258441
 * file: Dairy.java
 * purpose: Assessment 1 - COIT11134
 * class for storing dairy details, sub-class of the partner class
 */

public class Dairy extends Partner
{
    private int accountRefNum;  // dairy's account reference number for tracking payments

    // default constructor
    public Dairy(int accountRefNum)
    {
        super();
        this.accountRefNum = 0;
    }

    /**
     * parameterised constructor
     * @param accountRefNum holds the dairy's account reference number
     * @param name holds full business name
     * @param address holds full address
     * @param postcode holds poscode
     * @param contactPhone holds contact phone number
     * @param email holds email
     * @param ABN holds australian business number 
     */
    public Dairy(int accountRefNum, String name, String address, 
            String postcode, String contactPhone, String email, String ABN)
    {
        super(name, address, postcode, contactPhone, email, ABN);
        this.accountRefNum = accountRefNum;
    }

    // returns the account number as an integer
    public int getAccountRefNum()
    {
        return accountRefNum;
    }

    /**
     * set the dairy's account reference number
     * @param accountRefNum holds the dairy's reference number as an integer
     */
    public void setAccountRefNum(int accountRefNum)
    {
        this.accountRefNum = accountRefNum;
    }

    // toString to be used to display the dairy's details in output field
    @Override
    public String toString()
    {
        return super.toString() + 
                "Account reference Number: " + accountRefNum + "\n";
    }
    
    @Override
    public String printToFile()
    {
        return super.printToFile() + "|" + accountRefNum;
    }
}
