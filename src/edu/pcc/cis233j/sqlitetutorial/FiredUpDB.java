package edu.pcc.cis233j.sqlitetutorial;

import java.sql.*;
import java.util.ArrayList;

/**
 * Connection to the SQLite FiredUp database
 *
 * Version 2022.11.21 Modifications:
 *   PF: Changed FIREDUP_URL field to FIREDUP_DB to use SQLite database.
 *   PF: Removed USERNAME and PASSWORD fields.
 *   PF: Modified getConnection method to connect to local SQLite database.
 *   PF: Added CUSTOMERS_STATE_SQL field.
 *   PF: Modified readCustomerBasics to be able to allow retrieving data for
 *       customers from a certain state.
 *   PF: Modified readCustomers to accept a state argument.
 *
 * @author Patrick Foster
 * @version 2022.11.21
 */

public class FiredUpDB {
    private static final String FIREDUP_DB = "jdbc:sqlite:FiredUp.db";
    private static final String CUSTOMERS_SQL = "SELECT CustomerID, Name, City, StateProvince FROM CUSTOMER";
    private static final String CUSTOMERS_STATE_SQL = "SELECT CustomerID, Name, City, StateProvince " +
                                                        "FROM CUSTOMER WHERE StateProvince = ?";
    private static final String EMAIL_SQL = "SELECT EmailAddress FROM EMAIL WHERE FK_CustomerID = ?";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(FIREDUP_DB);
    }

    public ArrayList<Customer> readCustomers(String state) {
        ArrayList<Customer> customers = readCustomerBasics(state);
        readEmailAddresses(customers);
        return customers;
    }

    private ArrayList<Customer> readCustomerBasics(String state) {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        String sqlStatement;
        if(state.equals("none")) {
            sqlStatement = CUSTOMERS_SQL;
        }
        else {
            sqlStatement = CUSTOMERS_STATE_SQL;
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlStatement)
        ){
            if(!state.equals("none")) {
                stmt.setString(1, state);
            }

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                customers.add(new Customer(rs.getInt("CustomerID"), rs.getString("Name"),
                        rs.getString("City"), rs.getString("StateProvince")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    private void readEmailAddresses(ArrayList<Customer> customers) {
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(EMAIL_SQL)
        ) {
            for(Customer customer : customers) {
                stmt.setInt(1, customer.getID());
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    customer.addEmailAddress(rs.getString("EmailAddress"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
