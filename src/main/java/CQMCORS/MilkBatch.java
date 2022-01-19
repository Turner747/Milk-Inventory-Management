
package CQMCORS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Joshua Turner - s0258441
 * file: MilkBatch.java
 * purpose: Assessment 2 - COIT11134
 * parent class of the SupplyBatch and DeliveryBatch classes
 */

public class MilkBatch implements Comparable<MilkBatch>
{
    private LocalDate date;     // date the batch was received
    private double quantity;    // quantity of milk in batch in kilo litres

    // default contructor
    public MilkBatch()
    {
        this.date = LocalDate.of(9999, 9, 9);
        this.quantity = 0;
    }

    /**
     * parameterised constructor
     * @param date holds the date of the batch as a LocalDate object
     * @param quantity holds the quantity of milk(kL) as a double
     */
    public MilkBatch(LocalDate date, double quantity)
    {
        this.date = date;
        this.quantity = quantity;
    }

    // returns the date of the batch as a LocalDate object
    public LocalDate getDate()
    {
        return date;
    }
    
    /**
     * set the date of the batch
     * @param date holds the date as a LocalDate object
     */
    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    // returns the milk quanity in kilo lites as a double
    public double getQuantity()
    {
        return quantity;
    }

    /**
     * set batch's mil quantity
     * @param quantity holds the quantity of milk(kL) as a double
     */
    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }

    // toString to be used to display batch details in diply area
    @Override
    public String toString()
    {
        return String.format("%-75s%.3f kL" , 
                date.format(DateTimeFormatter.ofPattern ("dd/MM/yyyy")), 
                quantity);
    }
    
    // used to print to save file
    public String printToFile()
    {
        return date + "|" + quantity;
    }

    @Override
    public int compareTo(MilkBatch b)
    {
        return this.date.compareTo(b.date);
    }
    
}
