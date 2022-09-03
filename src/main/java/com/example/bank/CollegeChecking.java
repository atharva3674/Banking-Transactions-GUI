package com.example.bank;
/**
 * The type of account is a College Checking account.
 * Different variables define the characteristics of the class such as interest rate and monthly fees.
 * Implement different methods to calculate interest and fees of the account.
 * @author Akshat Mehta and Atharva Patil
 */
public class CollegeChecking extends Checking{
    private static final double INTERESTRATE = 0.0025;
    private int campusCode;
    private static final int NEWBRUNSWICK = 0;
    private static final int NEWARK = 1;
    private static final int CAMDEN = 2;


    /**
     * Take in the client profile, automatically set it to open in super class, take in the balance, and the campus code of the client.
     * Call the super class to create object.
     * @param profile profile of client.
     * @param balance balance in the account.
     * @param campCode campus code of where client is located.
     */
    public CollegeChecking(Profile profile, double balance, int campCode){
        super(profile, balance);
        this.campusCode = campCode;
    }

    /**
     * Calculate the monthly interest of College Checking account.
     * @return double, monthly interest.
     */
    @Override
    public double monthlyInterest() {
        return (INTERESTRATE / NUMMONTHSINYEAR) * balance;
    }

    /**
     * Calculate the fee of the account, but this account doesn't have a fee.
     * @return double, which is 0.00
     */
    @Override
    public double fee() {
        return NOFEE;
    }

    /**
     * Return the type of account: College Checking
     * @return String "College Checking"
     */
    @Override
    public String getType() {
        return "College Checking";
    }

    /**
     * Returns the campus code that is an integer as text
     * @return string that is the name of campus based on code
     */
    public String getCampusCode(){
        if(campusCode == NEWBRUNSWICK){
            return "NEW_BRUNSWICK";
        }
        else if(campusCode == NEWARK){
            return "NEWARK";
        }
        else if(campusCode == CAMDEN){
            return "CAMDEN";
        }
        return null;
    }
}
