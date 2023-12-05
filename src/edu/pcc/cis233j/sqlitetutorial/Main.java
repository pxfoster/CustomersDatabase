package edu.pcc.cis233j.sqlitetutorial;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Displays a GUI for displaying requested customer
 * information from a SQLite database.
 *
 * Version 2022.11.21:
 *   PF: Modified main method to display the program's GUI.
 *
 * @author Patrick Foster
 * @version 2022.11.21
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CustomersForm customersUI = new CustomersForm();
                JPanel root = customersUI.getRootPanel();
                JFrame frame = new JFrame();
                frame.setTitle("FiredUp Customers");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(root);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
