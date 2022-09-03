package com.example.bank;
/**
 * The type of account is a Money Market account.
 * Different variables define the characteristics of the class such as interest rate and monthly fees.
 * Implement different methods to calculate interest and fees of the account.
 * @author Akshat Mehta and Atharva Patil
 */
public class MoneyMarket extends Savings{
    public static final double INTERESTRATE = 0.008;
    public static final int MONTHLYFEE = 10;
    public static final double LOYALCUSTOMERRATE = 0.0095;
    private int withdrawCount = 0;
    private static final int MINIMUMBALANCE = 2500;
    private static final int WITHDRAWLIMIT = 3;


    /**
     * Take in profile of client, balance in the account, and automatically set loyalty of customer to 1.
     * Call super class to create Money Market object.
     * @param profile profile of client.
     * @param balance balance in the account.
     */
    public MoneyMarket(Profile profile, double balance){
        super(profile, balance, ISLOYALCUSTOMER);
    }

    /**
     * Calculate the monthly interest incurred by the Money Market account.
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
     * Calculate the fee of account based on the balance and number of withdraws.
     * @return double, fee.
     */
    @Override
    public double fee() {
        if(balance >= MINIMUMBALANCE && getWithdraws() <= WITHDRAWLIMIT)
            return NOFEE;
        else
            return MONTHLYFEE;
    }

    /**
     * Return the type of account.
     * @return String, "Money Market Savings"
     */
    @Override
    public String getType() {
        return "Money Market Savings";
    }

    /**
     * Get the loyalty of the account.
     * @return String, empty string if not loyal, and "::loyal::" if loyal.
     */
    public String getLoyalty(){
        if(loyalCustomer == NOTLOYALCUSTOMER)
            return "::";
        else
            return "::Loyal::";
    }

    /**
     * Get the number of withdraws in Money Market account.
     * @return int, withdraw count.
     */
    public int getWithdraws(){
        return withdrawCount;
    }

    /**
     * increment the number of withdraws in Money Market account by 1.
     */
    public void incrementWithdraws(){
        withdrawCount++;
    }


}
