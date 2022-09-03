package com.example.bank;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;



/**
 * Controller file that relates the GUI to the functions that
 * excecute the program's tasks.
 * Implements methods to read user input and manipulate input to
 * carry out functions for designated output.
 * @author Akshat Mehta and Atharva Patil
 */
public class BankTellerController {

    private static final int INITIALSIZE = 4;
    private AccountDatabase accountDatabase = new AccountDatabase(INITIALSIZE);
    @FXML
    private RadioButton newBrunswick, newark, camden;
    @FXML
    private CheckBox loyalCustomer;
    @FXML
    private TextField first_name_open, last_name_open, dob_open, amount_open;
    @FXML
    private TextField first_name_close, last_name_close, dob_close;
    @FXML
    private TextField first_name_dw, last_name_dw, dob_dw, amount_dw;
    @FXML
    private TextArea message;
    @FXML
    private RadioButton checking_open, collegeChecking_open, savings_open, moneyMarket_open;
    @FXML
    private RadioButton checking_close, collegeChecking_close, savings_close, moneyMarket_close;
    @FXML
    private RadioButton checking_dw, collegeChecking_dw, savings_dw, moneyMarket_dw;

    private static final int MINMONEYMARKETBALANCE = 2500;
    private static final int NEWBRUNSWICK = 0;
    private static final int NEWARK = 1;
    private static final int CAMDEN = 2;
    private static final int INITIALVALUE = 0;
    private static final int ISLOYALCUSTOMER = 1;
    private static final int NOTLOYALCUSTOMER = 0;
    private static final int MINBALANCE = 0;
    private static final String MINAMOUNTVALUE = "0.01";

    /**
     * When College Checking button is clicked enable campus code buttons and disable and deselect loyalty checkbox.
     */
    @FXML
    public void onCCClick(){
        newBrunswick.setDisable(false);
        newark.setDisable(false);
        camden.setDisable(false);
        loyalCustomer.setDisable(true);
        loyalCustomer.setSelected(false);
    }

    /**
     * When Checking button is clicked disable campus code buttons and disable and deselect loyalty checkbox.
     */
    @FXML
    public void onCClick(){
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        newBrunswick.setSelected(false);
        newark.setSelected(false);
        camden.setSelected(false);
        loyalCustomer.setDisable(true);
        loyalCustomer.setSelected(false);
    }

    /**
     * When Savings button is clicked disable campus code buttons and enable loyalty checkbox.
     */
    @FXML
    public void onSClick(){
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        newBrunswick.setSelected(false);
        newark.setSelected(false);
        camden.setSelected(false);
        loyalCustomer.setDisable(false);
        loyalCustomer.setSelected(false);
    }

    /**
     * When Money Market button is clicked disable campus code buttons and disable loyalty checkbox but it is selected.
     */
    @FXML
    public void onMMClick(){
        newBrunswick.setDisable(true);
        newark.setDisable(true);
        camden.setDisable(true);
        newBrunswick.setSelected(false);
        newark.setSelected(false);
        camden.setSelected(false);
        loyalCustomer.setDisable(true);
        loyalCustomer.setSelected(true);
    }

    /**
     * Check the entries from the text fields and buttons for any exceptions and invalid inputs and display messages accordingly.
     * @param userInput
     * @return true if entries are valid, false otherwise.
     */
    private boolean checkEntries(String[] userInput) {
        if (!doEntriesHaveInput(userInput)) return false;

        boolean check = true;
        char[] chars = userInput[0].toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c)) check = false;
        }

        char[] chars2 = userInput[1].toCharArray();
        for (char c : chars2) {
            if (!Character.isLetter(c)) check = false;
        }
        if (!check) {
            message.appendText("Not a valid name!");
            return false;
        }

        Date dateOfBirth = null;
        try {
            dateOfBirth = new Date(userInput[2]);
        } catch (Exception e) {
            message.appendText("Invalid Date!");
            return false;
        }
        boolean checkDate = dateOfBirth.isValid();
        if (!checkDate) {
            message.appendText("Invalid Date!");
            return false;
        }

        double amt;
        try {
            amt = Double.parseDouble(userInput[3]);
        } catch (NumberFormatException e) {
            message.appendText("Not a valid amount!");
            return false;
        }
        if (amt <= MINBALANCE) {
            message.appendText("Not a valid amount!");
            return false;
        }
        return true;
    }

    /**
     * Check if any of the text fields are empty and display text message accordingly.
     * @param userInput
     * @return true if all entries have inputs, false otherwise
     */
    private boolean doEntriesHaveInput(String[] userInput){
        if(userInput[0].isEmpty()) {
            message.appendText("First name text field is empty!");
            return false;
        }
        else if(userInput[1].isEmpty()){
            message.appendText("Last name text field is empty!");
            return false;
        }
        else if(userInput[2].isEmpty()){
            message.appendText("Date of Birth text field is empty!");
            return false;
        }
        else if(userInput[3].isEmpty()){
            message.appendText("Amount text field is empty!");
            return false;
        }
        return true;
    }

    /**
     * When open button is clicked, perform various checks for input validation. If checks are passed open account.
     */
    @FXML
    public void onOpenClick() {
        message.clear();
        String[] userInput = new String[4];
        userInput[0] = first_name_open.getText();
        userInput[1] = last_name_open.getText();
        userInput[2] = dob_open.getText();
        userInput[3] = amount_open.getText();

        boolean success = checkEntries(userInput);
        if(!success) return;

        if(!checking_open.isSelected() && !collegeChecking_open.isSelected() && !savings_open.isSelected() && !moneyMarket_open.isSelected()){
            message.appendText("Select account type!");
            return;
        }
        else if(collegeChecking_open.isSelected()){
            if(!newBrunswick.isSelected() && !newark.isSelected() && !camden.isSelected()){
                message.appendText("Select campus code for college checking account!");
                return;
            }
        }

        Profile profile = new Profile(userInput[0], userInput[1], new Date(userInput[2]));
        if(checking_open.isSelected()){
            openChecking(profile, Double.parseDouble(userInput[3]));
        }
        else if(collegeChecking_open.isSelected()){
            openCollegeChecking(profile, Double.parseDouble(userInput[3]));
        }
        else if(savings_open.isSelected()){
            openSavings(profile, Double.parseDouble(userInput[3]));
        }
        else if(moneyMarket_open.isSelected()){
            if(Double.parseDouble(userInput[3]) < MINMONEYMARKETBALANCE) {
                message.appendText("Minimum of $2500 to open a MoneyMarket account.");
                return;
            }
            openMoneyMarket(profile, Double.parseDouble(userInput[3]));
        }

        clearFieldsOpen();
    }

    /**
     * Opens the Checking Account.
     * @param userProfile client profile.
     * @param balance amount to be added to account.
     */
    private void openChecking(Profile userProfile, double balance){
        Checking tempAccount = new Checking(userProfile, balance);
        boolean success = accountDatabase.open(tempAccount);
        if(success) message.appendText("Account opened.");
        else {
            boolean closeSuccess = accountDatabase.reopenAccountIfClosed(tempAccount);
            if(closeSuccess) message.appendText("Account reopened.");
            else message.appendText(userProfile.toString() + " same account(type) is in the database.");
        }
    }

    /**
     * Opens the College Checking Account.
     * @param userProfile client profile.
     * @param balance balance amount to be added to account.
     */
    private void openCollegeChecking(Profile userProfile, double balance){
        int campusCode = INITIALVALUE;
        if(newBrunswick.isSelected()) campusCode = NEWBRUNSWICK;
        else if(newark.isSelected()) campusCode = NEWARK;
        else if(camden.isSelected()) campusCode = CAMDEN;

        CollegeChecking tempAccount = new CollegeChecking(userProfile, balance, campusCode);
        boolean success = accountDatabase.open(tempAccount);
        if(success) message.appendText("Account opened.");
        else {
            boolean closeSuccess = accountDatabase.reopenAccountIfClosed(tempAccount);
            if(closeSuccess) message.appendText("Account reopened.");
            else message.appendText(userProfile.toString() + " same account(type) is in the database.");
        }
    }

    /**
     * Opens the Savings Account.
     * @param userProfile client profile.
     * balance balance amount to be added to account.
     */
    private void openSavings(Profile userProfile, double balance){
        int loyalty = NOTLOYALCUSTOMER;
        if(loyalCustomer.isSelected()) loyalty = ISLOYALCUSTOMER;

        Savings tempAccount = new Savings(userProfile, balance, loyalty);
        boolean success = accountDatabase.open(tempAccount);
        if(success)
            message.appendText("Account opened.");
        else {
            boolean closeSuccess = accountDatabase.reopenAccountIfClosed(tempAccount);
            if(closeSuccess) message.appendText("Account reopened.");
            else message.appendText(userProfile.toString() + " same account(type) is in the database.");
        }
    }

    /**
     *  Opens the Money Market Account.
     * @param userProfile client profile
     * @param balance amount to be added to account.
     */
    private void openMoneyMarket(Profile userProfile, double balance){
        MoneyMarket tempAccount = new MoneyMarket(userProfile, balance);
        boolean success = accountDatabase.open(tempAccount);
        if(success) message.appendText("Account opened.");
        else {
            boolean closeSuccess = accountDatabase.reopenAccountIfClosed(tempAccount);
            if(closeSuccess) message.appendText("Account reopened.");
            else message.appendText(userProfile.toString() + " same account(type) is in the database.");
        }
    }

    /**
     * When close button is clicked, perform various checks for input validation. If checks are passed close the account.
     */
    @FXML
    public void onCloseClick(){
        message.clear();
        String[] userInput = new String[4];
        userInput[0] = first_name_close.getText();
        userInput[1] = last_name_close.getText();
        userInput[2] = dob_close.getText();
        userInput[3] = MINAMOUNTVALUE;
        boolean success = checkEntries(userInput);
        if(!success) return;

        if(!checking_close.isSelected() && !collegeChecking_close.isSelected() && !savings_close.isSelected() && !moneyMarket_close.isSelected()){
            message.appendText("Select account type!");
            return;
        }

        Profile profile = new Profile(userInput[0], userInput[1], new Date(userInput[2]));

        boolean isAccountClosed = closeAccount(profile);

        if(isAccountClosed){
            message.appendText("Account closed.");
            clearFieldsClose();
        }
    }

    /**
     * Close the account corresponding to the account type and inputs given.
     * @param profile client profile.
     * @return true if account closed, false otherwise.
     */
    private boolean closeAccount(Profile profile){
        boolean accountClosed = false;
        if (checking_close.isSelected()) {
            Checking tempAccount = new Checking(profile, MINBALANCE);
            if(!checkIfAccountExists(tempAccount, "close")) return false;
            accountClosed = accountDatabase.close(tempAccount);
        }
        else if (collegeChecking_close.isSelected()) {
            CollegeChecking tempAccount = new CollegeChecking(profile, MINBALANCE, NEWARK);
            if(!checkIfAccountExists(tempAccount, "close")) return false;
            accountClosed = accountDatabase.close(tempAccount);
        }
        else if (savings_close.isSelected()) {
            Savings tempAccount = new Savings(profile, MINBALANCE, ISLOYALCUSTOMER);
            if(!checkIfAccountExists(tempAccount, "close")) return false;
            accountClosed = accountDatabase.close(tempAccount);
        }
        else if (moneyMarket_close.isSelected()) {
            MoneyMarket tempAccount = new MoneyMarket(profile, MINBALANCE);
            if(!checkIfAccountExists(tempAccount, "close")) return false;
            accountClosed = accountDatabase.close(tempAccount);
        }
        return accountClosed;
    }

    /**
     * Check to see if the radio button is selected for deposit/withdraw actions.
     * @return true if selected, false otherwise.
     */
    private boolean checkAccountForDW(){
        if(!checking_dw.isSelected() && !collegeChecking_dw.isSelected() && !savings_dw.isSelected() && !moneyMarket_dw.isSelected()){
            message.appendText("Select account type!");
            return false;
        }
        return true;
    }

    /**
     * When deposit button is clicked, perform various checks for input validation.
     * If checks are passed deposit money into the account.
     */
    @FXML
    public void onDepositClick(){
        message.clear();
        String[] userInput = new String[4];
        userInput[0] = first_name_dw.getText();
        userInput[1] = last_name_dw.getText();
        userInput[2] = dob_dw.getText();
        userInput[3] = amount_dw.getText();
        boolean success = checkEntries(userInput);
        if(!success) return;
        if(!checkAccountForDW()) return;

        double bal = Double.parseDouble(userInput[3]);

        Account tempAccount = null;
        Profile profile = new Profile(userInput[0], userInput[1], new Date(userInput[2]));

        if (checking_dw.isSelected()) {
            tempAccount = new Checking(profile, bal);
        }
        else if (collegeChecking_dw.isSelected()) {
            tempAccount = new CollegeChecking(profile, bal, NEWBRUNSWICK);
        }
        else if (savings_dw.isSelected()) {
            tempAccount = new Savings(profile, bal, NOTLOYALCUSTOMER);
        }
        else if (moneyMarket_dw.isSelected()) {
            tempAccount = new MoneyMarket(profile, bal);
        }

        if(!checkIfAccountExists(tempAccount, "deposit")) return;

        accountDatabase.deposit(tempAccount);
        message.appendText("Deposit - balance updated.");
        clearFieldsDepositWithdraw();
    }

    /**
     * When withdraw button is clicked, perform various checks for input validation.
     * If checks are passed withdraw money from account.
     */
    @FXML
    public void onWithdrawClick(){
        message.clear();
        String[] userInput = new String[4];
        userInput[0] = first_name_dw.getText();
        userInput[1] = last_name_dw.getText();
        userInput[2] = dob_dw.getText();
        userInput[3] = amount_dw.getText();
        boolean success = checkEntries(userInput);
        if(!success) return;
        if(!checkAccountForDW()) return;

        double bal = Double.parseDouble(userInput[3]);
        Account tempAccount = null;
        Profile profile = new Profile(userInput[0], userInput[1], new Date(userInput[2]));

        if (checking_dw.isSelected()) {
            tempAccount = new Checking(profile, bal);
        }
        else if (collegeChecking_dw.isSelected()) {
            tempAccount = new CollegeChecking(profile, bal, NEWBRUNSWICK);
        }
        else if (savings_dw.isSelected()) {
            tempAccount = new Savings(profile, bal, NOTLOYALCUSTOMER);
        }
        else if (moneyMarket_dw.isSelected()) {
            tempAccount = new MoneyMarket(profile, bal);
        }

        if(!checkIfAccountExists(tempAccount, "withdraw")) return;

        boolean isWithdrawn = accountDatabase.withdraw(tempAccount);
        if(isWithdrawn) {
            message.appendText("Withdraw - balance updated.");
            clearFieldsDepositWithdraw();
        }
        else message.appendText("Withdraw - Insufficient fund.");
    }


    /**
     * Checks if account exists in the account database array.
     * @param account account that is being searched.
     * @param command command if account is being open, closed, withdrawn, or deposited.
     * @return true if account doesn't exist, false otherwise.
     */
    private boolean checkIfAccountExists(Account account, String command){
        int find = accountDatabase.findWithoutCheckingAndCollegeChecking(account);
        if(find == -1) {
            if(account.getType().equals("Money Market Savings"))
                message.appendText(account.holder.toString() + " Money Market is not in the database.");
            else
                message.appendText(account.holder.toString() + " " + account.getType() + " is not in the database.");
            return false;
        }
        else{
            Account[] accounts_temp = accountDatabase.getAccounts();
            if(accounts_temp[find].closed == true){
                if(command.equals("deposit"))
                    message.appendText("Cannot deposit to a closed account.");
                else if(command.equals("close"))
                    message.appendText("Cannot close an already closed account.");
                else if(command.equals("withdraw"))
                    message.appendText("Cannot withdraw from a closed account.");
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the accounts in the account database.
     */
    @FXML
    public void onPrintDBClick(){
        message.clear();
        String[] accounts = accountDatabase.print();
        print(accounts);
    }

    /**
     * Prints out all accounts in the account database by
     * the account types.
     */
    @FXML
    public void onPrintDBAccountTypeClick(){
        message.clear();
        String[] accounts = accountDatabase.printByAccountType();
        print(accounts);
    }

    /**
     * Prints out the fees and interest applied on each account.
     */
    @FXML
    public void onPrintDBFeeInterestClick(){
        message.clear();
        String[] accounts = accountDatabase.printFeeAndInterest();
        print(accounts);
    }

    /**
     * Prints out the updated balances of all accounts in the database
     * after fees and interest are applied on each account.
     */
    @FXML
    public void onPrintBalancesClick(){
        message.clear();
        String[] accounts = accountDatabase.updateAllBalances();
        print(accounts);
    }

    /**
     * Print the accounts in the database.
     * @param accounts array that contains the accounts in the array formatted as strings.
     */
    private void print(String[] accounts){
        int tempNumAccts = accountDatabase.getNumAcct();
        if(tempNumAccts == 0) message.appendText("Account Database is empty!");
        for(int i = 0; i < tempNumAccts; i++) message.appendText(accounts[i] + "\n");
    }

    /**
     * After open command is executed properly, clear out the fields for new entry.
     */
    private void clearFieldsOpen(){
        first_name_open.clear();
        last_name_open.clear();
        dob_open.clear();
        amount_open.clear();
        checking_open.setSelected(false);
        collegeChecking_open.setSelected(false);
        moneyMarket_open.setSelected(false);
        savings_open.setSelected(false);
        newBrunswick.setSelected(false);
        newark.setSelected(false);
        camden.setSelected(false);
        loyalCustomer.setSelected(false);
        loyalCustomer.setDisable(true);
    }

    /**
     * After close command is executed properly, clear out the fields for new entry.
     */
    private void clearFieldsClose(){
        first_name_close.clear();
        last_name_close.clear();
        dob_close.clear();
        checking_close.setSelected(false);
        collegeChecking_close.setSelected(false);
        moneyMarket_close.setSelected(false);
        savings_close.setSelected(false);
    }

    /**
     * After deposit or withdraw command is executed properly, clear out the fields for new entry.
     */
    private void clearFieldsDepositWithdraw(){
        first_name_dw.clear();
        last_name_dw.clear();
        dob_dw.clear();
        amount_dw.clear();
        checking_dw.setSelected(false);
        collegeChecking_dw.setSelected(false);
        moneyMarket_dw.setSelected(false);
        savings_dw.setSelected(false);
    }
}