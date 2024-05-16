/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mahpi
 */
public class UpdateSalesOrderData {
    
    public static void saleData(DefaultTableModel model, JTable tableSaleOrder){
        String[] columnNames = {"Sales ID", "Salesperson ID", "Date", "Cus Name",
            "Cus Contact", "Cus Address", "Item Name", "Item Type", 
            "Delivery Fee", "Discount", "Unit Price", "Qty", "Approval Status", "Product Status"};
        model.setColumnIdentifiers(columnNames);
        
        try (BufferedReader reader = new BufferedReader(new FileReader("sales_data.txt"))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(", ");
                if(!"Approved".equals(rowData[12]) && !"Closed".equals(rowData[12])){
                    model.addRow(rowData);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing the data to a file: " + e.toString());
        }
        
        tableSaleOrder.setModel(model);
        tableSaleOrder.setDefaultEditor(Object.class, null); // Make the table non-editable
    }
    
    public static boolean numValidation(String str) {
        boolean decimalPointEncountered = false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == '.' && !decimalPointEncountered) {
                    decimalPointEncountered = true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean intValidation(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean discountValidation(String str) {
        // Regular expression pattern for float followed by %
        String pattern = "^\\d+(\\.\\d+)?%$";
        return str.matches(pattern);
    }
    
    public static boolean updateSalesData
            (String salesID, 
            String cusName,
            String cusContact, 
            String cusAddress,
            String itemType, 
            String itemName, 
            String qty, 
            String delivery, 
            String discount,
            String price, 
            String changedApprovalStatus,
            String reason) 
    {
        try {
            if ("<Select>".equals(changedApprovalStatus)){
                JOptionPane.showMessageDialog(null, "Please select approval status.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }else if ("<Select>".equals(reason)){
                JOptionPane.showMessageDialog(null, "Please select unapprove reason.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (cusName.equals("") || cusContact.equals("") || cusAddress.equals("")
                    || itemType.equals("") || itemName.equals("") || qty.equals("") 
                    || delivery.equals("") || discount.equals("") || price.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Required Data!");
                return false;
            } else if (!SalesOrderQuotation.isValidCustomerName(cusName)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid customer name (alphabets only)!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (!SalesOrderQuotation.isValidCustomerPhoneNumber(cusContact)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid phone number!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (cusAddress.contains(",")) {
                JOptionPane.showMessageDialog(null, "Commas (,) cannot be used in the address field!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (numValidation(price) == false || (numValidation(delivery) == false)) {
                JOptionPane.showMessageDialog(null, "Please enter a valid price/delivery fee (number only)!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (intValidation(qty) == false){
                JOptionPane.showMessageDialog(null, "Please enter a valid quantity (integer only)!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } else if (discountValidation(discount) == false){
                JOptionPane.showMessageDialog(null, "Please enter a valid discount (number followed by %)!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else {
                File inputFile = new File("sales_data.txt");
                File tempFile = new File("tempData.txt");

                BufferedWriter writer;
                boolean found;
                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                    writer = new BufferedWriter(new FileWriter(tempFile));
                    String line;
                    found = false;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(", ");
                        if (parts.length > 1 && parts[0].equals(salesID)) {
                            parts[3] = cusName;
                            parts[4] = cusContact;
                            parts[5] = cusAddress;
                            parts[6] = itemName;
                            parts[7] = itemType;
                            parts[8] = delivery;
                            parts[9] = discount;
                            parts[10] = price;
                            parts[11] = qty;
                            parts[12] = changedApprovalStatus;
                            parts[14] = reason;
                            line = String.join(", ", parts);
                            found = true;
                        }
                        writer.write(line + System.lineSeparator());
                    }
                }
                writer.close();

                if (found) {
                    if (!inputFile.delete()) {
                        JOptionPane.showMessageDialog(null, "Could not delete the original file.", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    if (!tempFile.renameTo(inputFile)) {
                        JOptionPane.showMessageDialog(null, "Could not rename the temporary file.", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    JOptionPane.showMessageDialog(null, "Sale Order Updated.", salesID, JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Sales ID not found in the data.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
