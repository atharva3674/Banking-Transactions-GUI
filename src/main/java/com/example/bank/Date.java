package com.example.bank;
import java.util.Calendar;
/**
 * The class is used to make the date for the profile class information.
 * We get the date from the user or have a default date constructor if none are received.
 * Initialize all variables, year, month, and day to create the Date object.
 * Implement methods to manipulate Date object.
 * @author Akshat Mehta and Atharva Patil
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    public static final int MAX_YEAR_MONTHS = 12;
    public static final int MIN_YEAR_MONTHS = 1;
    public static final int PLACEHOLDER = 0;
    public static final int MONTHSWITHTHIRTYONEDAYS = 31;
    public static final int MONTHSWITHTHIRTYDAYS = 30;
    public static final int MONTH_FEBRUARY = 2;
    public static final int DAY_FEBRUARY_NONLEAP_DEFAULT = 28;
    public static final int DAY_FEBRUARY_LEAP = 29;
    public static final int MAX_YEAR = 2022;
    public static final int MIN_YEAR = 1900;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    /**
     * take "mm/dd/yyyy" and create a Date object
     * @param date date object
     */
    public Date(String date){
        String [] date2 = date.split("/");
        this.month = Integer.parseInt(date2[0]);
        this.day = Integer.parseInt(date2[1]);
        this.year = Integer.parseInt(date2[2]);
    }

    /**
     * create a Date object with today's date if no parameters are received.
     */
    public Date(){
        Calendar currentDate = Calendar.getInstance();
        this.month = currentDate.get(Calendar.MONTH) + 1;
        this.day = currentDate.get(Calendar.DAY_OF_MONTH);
        this.year = currentDate.get(Calendar.YEAR);
    }

    /**
     * check if the date object is a valid date and not a future date or invalid.
     * @return true if it is valid, false otherwise
     */
    public boolean isValid() {
        Date todaysDate = new Date();

        if(this.month > MAX_YEAR_MONTHS || this.month < MIN_YEAR_MONTHS ||
                this.year > MAX_YEAR || this.year < MIN_YEAR){
            return false;
        }

        int[] mArray = {PLACEHOLDER, MONTHSWITHTHIRTYONEDAYS, DAY_FEBRUARY_NONLEAP_DEFAULT, MONTHSWITHTHIRTYONEDAYS,
                MONTHSWITHTHIRTYDAYS, MONTHSWITHTHIRTYONEDAYS, MONTHSWITHTHIRTYDAYS, MONTHSWITHTHIRTYONEDAYS, MONTHSWITHTHIRTYONEDAYS,
                MONTHSWITHTHIRTYDAYS, MONTHSWITHTHIRTYONEDAYS, MONTHSWITHTHIRTYDAYS, MONTHSWITHTHIRTYONEDAYS};
        if (this.month != MONTH_FEBRUARY) {
            if (this.day > mArray[this.month]) {
                return false;
            }
        }
        else {
            if (checkLeapYear(this.year) == true) {
                mArray[MONTH_FEBRUARY] = DAY_FEBRUARY_LEAP;
            }
            if (this.day > mArray[this.month]) {
                return false;
            }
        }

        if(this.year > todaysDate.year) {
            return false;
        }
        else if(this.year == todaysDate.year) {
            if (this.month > todaysDate.month) {
                return false;
            }
            if (this.month == todaysDate.month) {
                if (this.day > todaysDate.day)
                    return false;
                else
                    return true;
            }
            else
                return true;
        }
        return true;
    }

    /**
     * check if the year is a leap year
     * @param year the year in the date
     * @return true if it is a leap year, false otherwise
     */
    public boolean checkLeapYear(int year) {
        if(year % QUADRENNIAL == 0){
            if(year % CENTENNIAL == 0) {
                if (year % QUATERCENTENNIAL == 0) {
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    /**
     * Formats and textually represents the Date object as mm/dd/yyyy.
     * @return the date formatted as a String
     */
    @Override
    public String toString(){
        return this.month + "/" + this.day + "/" + this.year;
    }

    /**
     * compare two date objects using year, month, and day
     * @return 1 if first date is greater than second date, -1 if less than, and 0 if equal to
     */
    @Override
    public int compareTo(Date date) {
        if(this.year > date.year){
            return 1;
        }
        else if(this.year < date.year){
            return -1;
        }
        else{
            if(this.month > date.month){
                return 1;
            }
            else if(this.month < date.month){
                return -1;
            }
            else{
                if(this.day > date.day){
                    return 1;
                }
                else if (this.day < date.day){
                    return -1;
                }
            }
        }
        return 0;
    }
}
