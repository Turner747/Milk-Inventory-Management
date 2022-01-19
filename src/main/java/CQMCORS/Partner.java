
package CQMCORS;

/**
 * @author Joshua Turner - s0258441
 * file: Partner.java
 * purpose: Assessment 2 - COIT11134
 * Parent class of the Dairy and Farm class
 */

public class Partner implements Comparable<Partner>
{
    // class attributes - all strings to allow some freedom to the 
    // user for formating the field how they like
    private String name;            // full business name
    private String address;         // full address
    private String postcode;        // poscode
    private String contactPhone;    // contact phone number
    private String email;           // email
    private String ABN;             // australian business number

    // default constructor
    public Partner()
    {
        this.name = "Empty";
        this.address = "Empty";
        this.postcode = "Empty";
        this.contactPhone = "Empty";
        this.email = "Empty";
        this.ABN = "Empty";
    }

    /**
     * parameterised constructor
     * @param name holds full business name
     * @param address holds full address
     * @param postcode holds poscode
     * @param contactPhone holds contact phone number
     * @param email holds email
     * @param ABN holds australian business number
     */
    public Partner(String name, String address, String postcode, String contactPhone, String email, String ABN)
    {
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.contactPhone = contactPhone;
        this.email = email;
        this.ABN = ABN;
    }

    // return the business name as a string
    public String getName()
    {
        return name;
    }

    /**
     * set the business name
     * @param name holds the name as a string
     */
    public void setName(String name)
    {
        this.name = name;
    }

    // returns address as a string
    public String getAddress()
    {
        return address;
    }

    /**
     * set the address
     * @param address holds the address as a string
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    // returns the post code as a string
    public String getPostcode()
    {
        return postcode;
    }

    /**
     * sets the post code
     * @param postcode holds the postcode as a string
     */
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    // returns the contact phone number as a string
    public String getContactPhone()
    {
        return contactPhone;
    }

    /**
     * set the contact phone number
     * @param contactPhone holds the phone number as a string
     */
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    // returns the email as a String
    public String getEmail()
    {
        return email;
    }

    /**
     * sets the email
     * @param email holds the email address as a string
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    // returns the australian business number as a string
    public String getABN()
    {
        return ABN;
    }

    /**
     * set the australian business number
     * @param ABN holds the abn as a string
     */
    public void setABN(String ABN)
    {
        this.ABN = ABN;
    }

    // toString to be used to deisplay text in output fields
    @Override
    public String toString()
    {
        return "Business name: " + name +
                "\nAddress: " + address + 
                "\nPostcode: " + postcode + 
                "\nContact phone: " + contactPhone + 
                "\nEmail:"  + email + 
                "\nABN: " + ABN + "\n";
    }
    
    public String printToFile()
    {
        return name + "|" + address + "|" + postcode + "|" + contactPhone + 
                "|"  + email + "|" + ABN;
    }

    @Override
    public int compareTo(Partner p)
    {
        return this.name.compareTo(p.name);
    }
    
}
