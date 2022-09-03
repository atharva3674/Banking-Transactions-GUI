package com.example.bank;

/**
 * The type of account is a Regular Checking account.
 * Different variables define the characteristics of the class such as interest rate and monthly fees.
 * Implement different methods to calculate interest and fees of the account.
 * @author Akshat Mehta and Atharva Patil
 */
public class Checking extends Account{
    private static final double INTERESTRATE = 0.001;
    private static final double MONTHLYFEE = 25.00;
    private static final int MINIMUMBALANCE = 1000;

    /**
     * Take in the client profile, automatically set it to open, and take in the balance. Call the super class to create object.
     * @param profile profile of client.
     * @param balance balance in the account.
     */
    public Checking(Profile profile, double balance) {
        super(profile, false, balance);
    }

    /**
     * Calculate the monthly interest for the Checking account.
     * @return double which is the calculated interest.
     */
    @Override
    public double monthlyInterest() {
        return (INTERESTRATE / NUMMONTHSINYEAR) * balance;
    }

    /**
     * Calculate the fee for the Checking account based on the balance in the account.
     * @return double which represents the fee.
     */
    @Override
    public double fee() {
        if(balance >= MINIMUMBALANCE)
            return NOFEE;
        else
            return MONTHLYFEE;
    }

    /**
     * Represent the name of account as a string
     * @return string "Checking"
     */
    @Override
    public String getType() {
        return "Checking";
    }
}
