package edu.pcc.cis233j.sqlitetutorial;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;

/**
 * GUI for the FiredUp Customers program
 *
 * Version 2022.11.21 Modifications:
 *   PF: Added firedUp field for the database.
 *   PF: Modified getAllCustomerData method to receive data
 *       from the database and return that data.
 *   PF: Modified getStateCustomerData method to receive data
 *       from the database and return that data.
 *   PF: Removed getAllCustomerData and getStateCustomerData and
 *       placed their functionality inside viewCustomers method.
 *
 * @author Patrick Foster
 * @version 2022.11.21
 */
public class CustomersForm {
    private JPanel rootPanel;
    private JRadioButton allCustomersRadioButton;
    private JRadioButton customerStateRadioButton;
    private JTextField customerStateTextField;
    private JButton viewCustomersButton;
    private JTable customersTable;
    private FiredUpDB firedUp;

    /**
     * Constructor for CustomersForm
     */
    public CustomersForm() {
        createTable();

        allCustomersRadioButton.setSelected(true);
        customerStateTextField.setEnabled(false);

        allCustomersRadioButton.addActionListener(e -> allCustomersSelected());
        customerStateRadioButton.addActionListener(e -> customerStateSelected());

        viewCustomersButton.addActionListener(e -> viewCustomers());

        ButtonGroup optionsButtonGroup = new ButtonGroup();
        optionsButtonGroup.add(allCustomersRadioButton);
        optionsButtonGroup.add(customerStateRadioButton);

        firedUp = new FiredUpDB();
    }

    /**
     * Get the root panel of the form.
     * @return rootPanel
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * Create the customer table for the form.
     */
    private void createTable() {
        customersTable.setModel(new CustomersTableModel());

        TableColumnModel columns = customersTable.getColumnModel();
        columns.getColumn(0).setMinWidth(150);
        columns.getColumn(1).setMinWidth(150);
        columns.getColumn(1).setMaxWidth(150);
        columns.getColumn(2).setMinWidth(75);
        columns.getColumn(2).setMaxWidth(75);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(1).setCellRenderer(centerRenderer);
        columns.getColumn(2).setCellRenderer(centerRenderer);
    }

    /**
     * Actions to perform if the all customers
     * radio button is selected.
     */
    private void allCustomersSelected() {
        customerStateTextField.setText("");
        customerStateTextField.setEnabled(false);
    }

    /**
     * Actions to perform if the customer state
     * radio button is selected.
     */
    private void customerStateSelected() {
        customerStateTextField.setEnabled(true);
        customerStateTextField.requestFocusInWindow();
    }

    /**
     * Actions to perform if the view customers
     * button is clicked.
     */
    private void viewCustomers() {
        if(allCustomersRadioButton.isSelected()) {
            CustomersTableModel custModel = (CustomersTableModel)customersTable.getModel();
            custModel.setCustomers(firedUp.readCustomers("none"));
        }
        else if(customerStateRadioButton.isSelected()) {
            String stateUpper = customerStateTextField.getText().toUpperCase();
            CustomersTableModel custModel = (CustomersTableModel)customersTable.getModel();
            custModel.setCustomers(firedUp.readCustomers(stateUpper));
        }
    }

    /**
     * CustomerTableModel code from Lesson 7
     * Modified some parts to fit my program.
     */
    private class CustomersTableModel extends DefaultTableModel {
        private final String[] COL_NAMES = {"Name", "City", "State"};
        private ArrayList<Customer> customers;

        /**
         * Constructor for CustomersTableModel
         */
        public CustomersTableModel() {
            super();
            this.customers = null;
            setColumnIdentifiers(COL_NAMES);
            setRowCount(0);
        }

        /**
         * Set up the customer data for the table.
         * @param customers The customer data for the table.
         */
        public void setCustomers(ArrayList<Customer> customers) {
            setRowCount(0);
            this.customers = customers;
            setRowCount(customers.size());
        }

        /**
         * Returns an attribute value for the cell at row and column.
         * @param row the row whose value is to be queried
         * @param col the column whose value is to be queried
         * @return value at row and column
         */
        @Override
        public Object getValueAt(int row, int col) {
            switch(col) {
                case 0: return customers.get(row).getName();
                case 1: return customers.get(row).getCity();
                case 2: return customers.get(row).getState();
                default: return null;
            }
        }

        /**
         * Determines if cells are editable.
         * @param row the row whose value is to be queried
         * @param col the column whose value is to be queried
         * @return false so no cells can be edited.
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
}
