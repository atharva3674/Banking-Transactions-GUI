package com.example.bank;

/**
 * Create a profile for a bank client.
 * We use their first name, last name, and date of birth to create their profile. Initialize those variables in the constructor.
 * We implement different methods to manipulate the profile objects.
 * @author Akshat Mehta and Atharva Patil
 */
public class Profile{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * We initialize the first name, last name, and date of birth variables to create the Profile object.
     * @param firstName first name of client
     * @param lastName last name of client
     * @param birthday birthday of client
     */
    public Profile(String firstName, String lastName, Date birthday){
        this.fname = firstName;
        this.lname = lastName;
        this.dob = birthday;
    }

    /**
     * Check if two profiles are equal by comparing the first name, last name, and date of birth.
     * @param profile profile object
     * @return true if equal, otherwise false
     */
    public boolean equals(Profile profile){
        if(this.dob.compareTo(profile.dob) == 0){
            if(this.lname.toUpperCase().equals(profile.lname.toUpperCase())){
                if(this.fname.toUpperCase().equals(profile.fname.toUpperCase()))
                    return true;
            }
        }
        return false;
    }

    /**
     * Textually represent the profile object.
     * @return String profile object represented as a String.
     */
    @Override
    public String toString(){
        return this.fname + " " + this.lname + " " + this.dob.toString();
    }
}
