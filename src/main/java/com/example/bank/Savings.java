package com.example.bank;

/**
 * The type of account is a Savings account.
 * Different variables define the characteristics of the class such as interest rate, monthly fees, and loyal customer rates
 * Implement different methods to calculate interest and fees of the account.
 * @author Akshat Mehta and Atharva Patil
 */
public class Savings extends Account{
    public static final double INTERESTRATE = 0.003;
    public static final int MONTHLYFEE = 6;
    public static final double LOYALCUSTOMERRATE = 0.0045;
    protected int loyalCustomer = 0;
    private static final int MINIMUMBALANCE = 300;
    protected static final int NOTLOYALCUSTOMER = 0;
    protected static final int ISLOYALCUSTOMER = 1;

    /**
     * Take in the profile of client, automatically set closed to false, and take in balance in the account.
     * Call the super class to make Savings object.
     * And set loyalty of client based on loyalCust parameter.
     * @param profile profile of client.
     * @param balance balance in the account.
     * @param loyalCust loyalty of customer.
     */
    public Savings(Profile profile, double balance, int loyalCust){
        super(profile, false, balance);
        this.loyalCustomer = loyalCust;
    }

    /**
     * Calculate the monthly interest of Savings account.
     * @return double, monthly interest.
     */
    @Override
    public double monthlyInterest() {
        if(loyalCustomer == ISLOYALCUSTOMER)
            return (LOYALCUSTOMERRATE / NUMMONTHSINYEAR) * balance;
        else
            return (INTERESTRATE / NUMMONTHSINYEAR) * balance;
    }

    /**
     * Calculate fee incurred by account based on the balance in the Savings account.
     * @return double, fee
     */
    @Override
    public double fee() {
        if(balance >= MINIMUMBALANCE)
            return NOFEE;
        else
            return MONTHLYFEE;
    }

    /**
     * Return the type of account: Savings
     * @return String "Savings"
     */
    @Override
    public String getType() {
        return "Savings";
    }

    /**
     * Return the loyalty of the customer as a String.
     * @return String, empty string if not loyal and "Loyal" string if loyal.
     */
    public String getLoyalty(){
        if(loyalCustomer == NOTLOYALCUSTOMER)
            return "::";
        else
            return "::Loyal";
    }

    /**
     * Set the loyalty of the customer.
     */
    public void setLoyalCustomer(){
        if(loyalCustomer == NOTLOYALCUSTOMER) loyalCustomer = ISLOYALCUSTOMER;
        else loyalCustomer = NOTLOYALCUSTOMER;
    }

}
