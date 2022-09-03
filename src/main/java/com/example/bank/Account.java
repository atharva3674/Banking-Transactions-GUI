package com.example.bank;
import java.text.DecimalFormat;
/**
 * Stores the account information for each account in the bank.
 * Uses the Profile object to store client information, closed to see if the account is open or not, and the balance in each account.
 * Implements different methods to manipulate each account object.
 * @author Akshat Mehta and Atharva Patil
 */
public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;
    protected static final int NUMMONTHSINYEAR = 12;
    protected static final double NOFEE = 0;
    /**
     * Take in the profile object, whether the account is closed or open, and the balance in the account to make an account object.
     * @param profile profile of client
     * @param close is the account closed or open
     * @param bal the balance in the account
     */
    public Account(Profile profile, boolean close, double bal){
        this.holder = profile;
        this.closed = close;
        this.balance = bal;
    }
    /**
     * Checks to see if two account objects are the equal.
     * @param obj object
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Account)
            if(((Account) obj).holder.equals(this.holder)){
                return true;
            }
        return false;
    }

    /**
     * Textually represent the account object.
     * @return String account represented as a string.
     */
    @Override
    public String toString(){
        DecimalFormat formatter = new DecimalFormat("0.00");
        return this.holder.toString() + "::Balance $" + formatter.format(balance);
    }

    /**
     * Withdraws specified amount from account, but also must check for sufficient funds.
     * @param amount the amount of money to be withdrawn.
     */
    public void withdraw(double amount) {
        balance = balance - amount;
    }

    /**
     * Deposits specified amount to account.
     * @param amount the amount of money to be deposited.
     */
    public void deposit(double amount){
        balance = balance + amount;
    }

    /**
     * Returns the monthly interest of the account.
     * @return monthly interest
     */
    public abstract double monthlyInterest();

    /**
     * Returns the monthly fee.
     * @return monthly fee
     */
    public abstract double fee();

    /**
     * Returns the account type (class name)
     * @return account type
     */
    public abstract String getType();


}
