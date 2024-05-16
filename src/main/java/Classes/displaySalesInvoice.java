/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;


import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class displaySalesInvoice {
    private JLabel cusName;
    private JLabel cusContact;
    private JLabel cusAddress;
    private JLabel SalesPersonID;
    private JLabel Date;
    private JLabel Time;
    private JTable InvoiceTable;
    private JLabel subTotal;
    private JLabel deliveryFee;
    private JLabel serviceTax;
    private JLabel total;
    private JLabel invoiceID;
    
    public displaySalesInvoice(JLabel cusName, JLabel cusContact, JLabel cusAddress, JLabel SalesPersonID, JLabel Date, JLabel Time, JTable InvoiceTable, JLabel subTotal, JLabel deliveryFee, JLabel serviceTax, JLabel total, JLabel invoiceID) {
        this.cusName = cusName;
        this.cusContact = cusContact;
        this.cusAddress = cusAddress;
        this.SalesPersonID = SalesPersonID;
        this.Date = Date;
        this.Time = Time;
        this.InvoiceTable = InvoiceTable;
        this.subTotal = subTotal;
        this.deliveryFee = deliveryFee;
        this.serviceTax = serviceTax;
        this.total = total;
        this.invoiceID = invoiceID;
    }
    
    public void display(String customerName, String salesDate, String salesPersonID, String salesID) {
        cusName.setText(customerName);
        Date.setText(salesDate);
        SalesPersonID.setText(salesPersonID);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = timeFormat.format(new Date());
        Time.setText(currentTime);
        DefaultTableModel tableModel = (DefaultTableModel) InvoiceTable.getModel();
        
        try {
            File file = new File("sales_data.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] rowData = line.trim().split(", ");
                
                if (rowData[0].equals(salesID)) {  // Check the current row if the specified salesID
                    String contact = rowData[4];
                    String address = rowData[5];
                    String item = rowData[6];  
                    String quantityStr = rowData[11];
                    String unitPriceStr = rowData[10]; 
                    String discount = rowData[9];
                    String deliveryFeeStr = rowData[8];
                    
                    int quantity = Integer.parseInt(quantityStr);
                    double unitPrice = Double.parseDouble(unitPriceStr);
                    double discountPercentage = parseDiscountString(discount);
                    double DeliveryFee = Double.parseDouble(deliveryFeeStr);
                    
                    double discountedPrice = calculateDiscountedPrice(unitPrice, discountPercentage);
                    double totalPrice = calculateTotalPrice(discountedPrice, quantity);
                    double roundedTotal = Double.parseDouble(String.format("%.2f", totalPrice));
                    
                    cusContact.setText(contact);
                    cusAddress.setText(address);
                    invoiceID.setText(generateNextInvoiceNumber());
                    subTotal.setText(String.format("%.2f", roundedTotal));
                    deliveryFee.setText(deliveryFeeStr);
                    double ServiceTax = 0.06*roundedTotal;
                    serviceTax.setText(String.format("%.2f", ServiceTax));
                    double SubtotalAftTax = roundedTotal + ServiceTax + DeliveryFee;
                    total.setText(String.format("%.2f", SubtotalAftTax));
                    tableModel.addRow(new Object[]{salesID, item, quantity, unitPrice, discount, roundedTotal});
                }
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static double calculateDiscountedPrice(double price, double discountPercentage) {
        double discountFactor = 1 - (discountPercentage / 100);
        return price * discountFactor;
    }
    
    private static double calculateTotalPrice(double discountedPrice, int quantity) {
        return discountedPrice * quantity;
    }
    
    private static double parseDiscountString(String discount) {
        //String percentageValue = discount.substring(1, discount.length() - 1);
        //return Double.parseDouble(percentageValue);
        String numericValue = discount.replaceAll("[^\\d.]", "");
        return Double.parseDouble(numericValue);
    }
    
    private String generateNextInvoiceNumber() {
        try (BufferedReader br = new BufferedReader(new FileReader("invoice.txt"))) {
            int lineCount = 0;
            while (br.readLine() != null) {
                lineCount++;
            }
            return String.format("IN%04d", lineCount + 1);  // Format the invoice number with leading zeros, assuming a maximum of 4 digits
        } catch (IOException e) {
            e.printStackTrace();
            return "";  
        }
    }
    
    
    public static boolean writeSalesInvoice(String InvoiceID, String customerName, String customerContact, String customerAddress, String salesPersonID, String salesDate, String salesTotal, List<String> salesIDs, List<String> items, List<String> quantities) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("invoice.txt", true))) {

            for (int i = 0; i < salesIDs.size(); i++) {
                String salesID = salesIDs.get(i);
                String item = items.get(i);
                String quantity = quantities.get(i);

                String line = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                        InvoiceID, salesID, salesPersonID, customerName, customerContact, customerAddress, item, quantity, salesDate, salesTotal);
                
                writer.write(line);
                writer.newLine();
            }
            
            updateSalesDataFromInvoice(salesIDs);

            JOptionPane.showMessageDialog(null, "The invoice has been issued sucessfully.", InvoiceID, JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static void updateSalesDataFromInvoice(List<String> salesIDs) {
        try {
            File salesDataFile = new File("sales_data.txt");
            File tempSalesDataFile = new File("tempSalesData.txt");

            try (BufferedReader reader = new BufferedReader(new FileReader(salesDataFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempSalesDataFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(", ");
                    String currentSalesID = parts[0];

                    if (salesIDs.contains(currentSalesID)) {
                        parts[13] = "In Progress";
                    }

                    line = String.join(", ", parts);
                    writer.write(line + System.lineSeparator());
                }
            }
            
            salesDataFile.delete();
            if (!tempSalesDataFile.renameTo(salesDataFile)) {
                System.out.println("Could not rename the temporary file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
