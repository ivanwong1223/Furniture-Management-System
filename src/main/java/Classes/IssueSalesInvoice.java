/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.BufferedReader;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import officer_role.ApproveSales;

/**
 *
 * @author User
 */

public class IssueSalesInvoice {
    private static ApproveSales approveSalesInstance;
    
    public static void setApproveSalesInstance(ApproveSales instance) {
        approveSalesInstance = instance;
    }
    
    public static void displayApprovedSales(DefaultTableModel model){
        try {
            File file = new File("sales_data.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            List<String[]> approvedRows = new ArrayList<>();
            List<String[]> closedRows = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.trim().split(", ");
                if ("Approved".equals(rowData[12])) {
                    approvedRows.add(new String[]{rowData[0], rowData[1], rowData[2], rowData[3], rowData[10], rowData[12], rowData[13]});
                } else if ("Closed".equals(rowData[12])) {
                    closedRows.add(new String[]{rowData[0], rowData[1], rowData[2], rowData[3], rowData[10], rowData[12], rowData[13]});
                }
            }
            br.close();
            
            approvedRows.sort(Comparator.comparing(arr -> arr[0])); // Sorting the approvedRows

            closedRows.sort(Comparator.comparing(arr -> arr[0]));  // Sorting closedRows

            for (String[] selectedColumns : approvedRows) {    // Add the approvedRows first, then closedRows to the model
                model.addRow(selectedColumns);
            }

            for (String[] selectedColumns : closedRows) {
                model.addRow(selectedColumns);
            }
        }catch (IOException ex) {
            
        }
    }
    
    public static boolean verifyInvoiceExist(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            String salesID = model.getValueAt(selectedRow, 0).toString(); // SalesID is in the first column

            // Check if the SalesID exists in the second column of the invoice.txt file
            if (isSalesIDInInvoice(salesID)) {
                JOptionPane.showMessageDialog(null, "Data has already issued the invoice.", "Error", JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }
        return false;
    }
    
    private static boolean isSalesIDInInvoice(String salesID) {
        try (BufferedReader br = new BufferedReader(new FileReader("invoice.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.trim().split(", ");
                if (rowData.length > 1 && rowData[1].equals(salesID)) {
                    return true; // SalesID exists in the second column of invoice.txt
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // SalesID not found in invoice.txt
    }
    
    public static boolean updateSalesData(String salesID, String salesApprovalStatus) {
        try {
            File inputFile = new File("sales_data.txt");
            File tempFile = new File("tempSalesData.txt");

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(", ");
                    if (parts[0].equals(salesID)) {
                        if ("Closed".equals(parts[12])) {     // Check if the thirteenth column is already "Closed"
                            JOptionPane.showMessageDialog(null, "Data cannot be updated. It is already Closed.", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                        
                        if ("In Progress".equals(parts[13]) && "Unapproved".equals(salesApprovalStatus)) {
                            JOptionPane.showMessageDialog(null, "Data cannot be Unapproved. It is already In Progress.", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                        
                        if ("Pending".equals(parts[13]) && "Closed".equals(salesApprovalStatus)) {
                            JOptionPane.showMessageDialog(null, "Data cannot be Closed. Must issue an invoice first.", "Error", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                        
                        if ("Closed".equals(salesApprovalStatus)) {     //Allow update to "Closed" even if it is "In Progress"
                            parts[12] = salesApprovalStatus;
                            parts[13] = "Workdone";
                        } else {
                            parts[12] = salesApprovalStatus;
                        }
                        line = String.join(", ", parts);
                        found = true;
                    }
                    writer.write(line + System.lineSeparator());
                }

                if (found) {
                    writer.close();    // Close the files before attempting to rename
                    reader.close();
                    inputFile.delete();

                    if (!tempFile.renameTo(inputFile)) {
                        JOptionPane.showMessageDialog(null, "Could not rename the temporary file.", "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    
                    DefaultTableModel model = new DefaultTableModel();
                    displayApprovedSales(model);

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
        }finally {
            if (approveSalesInstance != null) {
                approveSalesInstance.refreshForm();
            }
        }
    }
}
