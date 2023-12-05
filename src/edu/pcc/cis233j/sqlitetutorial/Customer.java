package edu.pcc.cis233j.sqlitetutorial;

import java.util.ArrayList;

/**
 * Stores customer information from a database
 *
 * @author Patrick Foster
 * @version 2022.10.24
 */
public class Customer {
    private int id;
    private String name;
    private String city;
    private String state;
    private ArrayList<String> emailAddresses;

    Customer(int id, String name, String city, String state) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        emailAddresses = new ArrayList<>();
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public ArrayList<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void addEmailAddress(String emailAddress) {
        emailAddresses.add(emailAddress);
    }
}
