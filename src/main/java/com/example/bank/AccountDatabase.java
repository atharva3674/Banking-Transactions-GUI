package com.example.bank;

import java.text.DecimalFormat;

/**
 * Keeps track of all the accounts in the bank.
 * Contains an array of all the accounts at the bank and the number of accounts there are.
 * Implements different methods to manipulate the accounts array depending on actions taken by clients.
 * @author Akshat Mehta and Atharva Patil
 */
public class AccountDatabase {
    private Account [] accounts;
    private int numAcct;
    private static final int GROWARRAYBYCONSTANT = 4;
    private static final int MINMONEYMARKETBALANCE = 2500;


    /**
     * Constructor to create an account database object and set the original length to 4 and number of accounts in the database to 0.
     * @param length initial length of array.
     */
    public AccountDatabase(int length){
        accounts = new Account[length];
        numAcct = 0;
    }

    /**
     * Looks for a particular account in the account array.
     * @param account account object
     * @return the index of the account in the array if found, otherwise -1
     */
    private int find(Account account){
        if(numAcct == 0) return -1;
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].getType().equals(account.getType())
                    || (accounts[i].getType().equals("Checking") && account.getType().equals("College Checking"))
            || (accounts[i].getType().equals("College Checking") && account.getType().equals("Checking"))) {
                if (accounts[i].equals(account))
                    return i;
            }
        }
        return -1;
    }

    /**
     * Grows the accounts array by 4.
     */
    private void grow(){
        Account [] accounts_temp = accounts;
        accounts = new Account[accounts_temp.length + GROWARRAYBYCONSTANT];
        for(int i = 0; i < numAcct; i++){
            accounts[i] = accounts_temp[i];
        }
    }

    /**
     * Opens an account and adds it to the account array, if it doesn't exist already.
     * @param account account object
     * @return true if the account was opened and added to the array, otherwise false
     */
    public boolean open(Account account){
        int a = find(account);
        if(numAcct >= accounts.length) grow();

        if(a == -1){
            accounts[numAcct] = account;
            numAcct++;
            return true;
        }
        return false;
    }

    /**
     * Method checks if the account is closed or not
     * @param account account object
     * @return true if account if we repoen account, otherwise return false
     */
    public boolean reopenAccountIfClosed(Account account){
        int a = findWithoutCheckingAndCollegeChecking(account);
        if(a != -1){
            if(accounts[a].closed == true){
                accounts[a].closed = false;
                accounts[a].balance = account.balance;
                return true;
            }
        }
        return false;
    }

    /**
     * Closes and account if it exists in the account array.
     * @param account account object
     * @return true if the account was closed, false otherwise
     */
    public boolean close(Account account){
        int a = find(account);
        if(a == -1) return false;

        else if(!accounts[a].closed) {
            accounts[a].closed = true;
            accounts[a].balance = 0.00;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Find the account for deposit in the accounts array if it exists.
     * @param account Account to find
     * @return return true if it exists, false otherwise
     */
    public int findWithoutCheckingAndCollegeChecking(Account account){
        if(numAcct == 0) return -1;

        for(int i = 0; i < numAcct; i++){
            if(accounts[i].getType().equals(account.getType())) {
                if (accounts[i].equals(account))
                    return i;
            }
        }
        return -1;
    }

    /**
     * Deposits money into an account.
     * @param account account object
     */
    public void deposit(Account account){
        int a = findWithoutCheckingAndCollegeChecking(account);
        accounts[a].deposit(account.balance);
    }

    /**
     * Withdraw money from an account if there is sufficient balance.
     * @param account account object
     * @return return true if the money was withdrawn successfully, false otherwise
     */
    public boolean withdraw(Account account){
        int a = findWithoutCheckingAndCollegeChecking(account);
        if(accounts[a].balance < account.balance){
            return false;
        }
        else{
            accounts[a].withdraw(account.balance);
            if(accounts[a].getType().equals("Money Market Savings")){
                ((MoneyMarket)(accounts[a])).incrementWithdraws();
                if(accounts[a].balance < MINMONEYMARKETBALANCE){
                    ((MoneyMarket)(accounts[a])).setLoyalCustomer();
                }
            }
            return true;
        }
    }

    /**
     * Stores all contents to be printed in a String array.
     * @return String array containing the accounts to print.
     */
    public String[] print(){
        String[] temp_accounts = new String[numAcct];
        for(int i = 0; i < numAcct; i++){
            String type = accounts[i].getType();
            if(type.equals("College Checking")) {
                String code = ((CollegeChecking) (accounts[i])).getCampusCode();
                if(accounts[i].closed)
                    temp_accounts[i] = (type + "::" + accounts[i].toString() + "::CLOSED::" + code);
                else
                    temp_accounts[i] = (type + "::" + accounts[i].toString() + "::" + code);
            }
            else if(type.equals("Checking")) {
                if(accounts[i].closed)
                    temp_accounts[i] = (type + "::" + accounts[i].toString() + "::CLOSED");
                else
                    temp_accounts[i] = (type + "::" + accounts[i].toString());
            }
            else if(type.equals("Savings")) {
                String loyalty = ((Savings) (accounts[i])).getLoyalty();
                if(accounts[i].closed)
                    temp_accounts[i] = (type + "::" + accounts[i].toString() + "::CLOSED");
                else if (loyalty.equals("::Loyal"))
                    temp_accounts[i] = (type + "::" + accounts[i].toString() + loyalty);
                else
                    temp_accounts[i] = (type + "::" + accounts[i].toString());
            }
            else if(type.equals("Money Market Savings")) {
                String loyalty = ((MoneyMarket) (accounts[i])).getLoyalty();
                int withdraws = ((MoneyMarket) (accounts[i])).getWithdraws();
                if(accounts[i].closed)
                    temp_accounts[i] = (type + "::" + accounts[i].toString() + "::" + "CLOSED::withdrawal: " + withdraws);
                else
                    temp_accounts[i] = (type + "::" + accounts[i].toString() + loyalty + "withdrawal: " + withdraws);
            }
        }

        return temp_accounts;
    }

    /**
     * Sorts accounts array by the type of account. Calls print to print the array.
     * @return String array returned by method call to print which contains the accounts to be printed.
     */
    public String[] printByAccountType(){
        int indexer = 0;
        Account[] sortedArray = new Account[accounts.length];
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].getType().equals("Checking")){
                sortedArray[indexer] = accounts[i];
                indexer++;
            }
        }
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].getType().equals("College Checking")){
                sortedArray[indexer] = accounts[i];
                indexer++;
            }
        }
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].getType().equals("Money Market Savings")){
                sortedArray[indexer] = accounts[i];
                indexer++;
            }
        }
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].getType().equals("Savings")){
                sortedArray[indexer] = accounts[i];
                indexer++;
            }
        }
        accounts = sortedArray;
        return print();
    }

    /**
     * Calculates the fees and monthly interests and stores the accounts to be printed in a String array.
     * @return String array that contains the accounts to be printed.
     */
    public String[] printFeeAndInterest(){
        String[] temp_accounts = new String[numAcct];
        for(int i = 0; i < numAcct; i++){
            double interest = accounts[i].monthlyInterest();
            double fee = accounts[i].fee();
            if(accounts[i].closed == true) fee = 0;
            DecimalFormat formatter = new DecimalFormat("0.00");
            if(accounts[i].getType().equals("Checking")){
                if(accounts[i].closed)
                    temp_accounts[i] = (accounts[i].getType() + "::" + accounts[i].toString() +
                            "::CLOSED" + "::fee $" + formatter.format(fee) + "::monthly interest $" + formatter.format(interest));
                else
                    temp_accounts[i] = (accounts[i].getType() + "::" + accounts[i].toString() +
                                "::fee $" + formatter.format(fee) + "::monthly interest $" + formatter.format(interest));
            }
            else if(accounts[i].getType().equals("College Checking")){
                String code = ((CollegeChecking)(accounts[i])).getCampusCode();
                if(accounts[i].closed)
                    temp_accounts[i] = (accounts[i].getType() + "::" + accounts[i].toString() +
                            "::CLOSED::" + code + "::fee $" + formatter.format(fee) + "::monthly interest $" + formatter.format(interest));
                else
                    temp_accounts[i] = (accounts[i].getType() + "::" + accounts[i].toString() +
                            "::" + code + "::fee $" + formatter.format(fee) + "::monthly interest $" + formatter.format(interest));
            }
            else if(accounts[i].getType().equals("Savings")){
                String loyal = ((Savings) (accounts[i])).getLoyalty();
                if(accounts[i].closed)
                    temp_accounts[i] = (accounts[i].getType() + "::" + accounts[i].toString() + "::CLOSED" +
                        "::fee $" + formatter.format(fee) + "::monthly interest $" + formatter.format(interest));
                else
                    if(loyal.equals("::"))
                        temp_accounts[i] = (accounts[i].getType() + "::" + accounts[i].toString() + loyal +
                                "fee $" + formatter.format(fee) + "::monthly interest $" + formatter.format(interest));
                    else
                        temp_accounts[i] = (accounts[i].getType() + "::" + accounts[i].toString() + loyal +
                                "::fee $" + formatter.format(fee) + "::monthly interest $" + formatter.format(interest));
            }
            else if(accounts[i].getType().equals("Money Market Savings")){
                String loyal = ((MoneyMarket) (accounts[i])).getLoyalty();
                int withdraws = ((MoneyMarket) (accounts[i])).getWithdraws();
                if(accounts[i].closed)
                    temp_accounts[i] = (accounts[i].getType() + "::" + accounts[i].toString() +
                            "::CLOSED::" + "withdrawal: " + withdraws + "::fee $" + formatter.format(fee) + "::monthly interest $" + formatter.format(interest));
                else
                    temp_accounts[i] = (accounts[i].getType() + "::" + accounts[i].toString() +
                            loyal + "withdrawal: " + withdraws + "::fee $" + formatter.format(fee) + "::monthly interest $" + formatter.format(interest));
            }
        }
        return temp_accounts;
    }

    /**
     * Update all balances in the accounts array by subtracting their fees and adding their monthly interests.
     * Calls print to get the accounts to be printed.
     * @return String array that contains the accounts to be printed.
     */
    public String[] updateAllBalances(){
        for(int i = 0; i < numAcct; i++){
            double interest = accounts[i].monthlyInterest();
            double fee = accounts[i].fee();
            if(accounts[i].closed == true) fee = 0;
            accounts[i].balance += interest;
            accounts[i].balance -= fee;
        }
        return print();
    }

    /**
     * Returns the account database array.
     * @return account database array.
     */
    public Account[] getAccounts(){
        return accounts;
    }

    /**
     * Returns the number of accounts in the database
     * @return integer value of the number of accounts
     */
    public int getNumAcct(){
        return numAcct;
    }
}
