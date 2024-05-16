/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import Classes.Furniture;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author johnp
 */
public class SalesOrderQuotation extends Furniture {
    private String salesId;
    private String salesPersonId;
    private String date;
    private String customerName;
    private String customerContactNo;
    private String customerAddress;
    private String quantity;
    private String status;
    private String productStatus;
    private String reason;

    public SalesOrderQuotation(String salesId, String salesPersonId, String date, String customerName, String customerContactNo, String customerAddress, String itemName, String itemType, String deliveryFees, String discount, String price, String quantity, String status, String productStatus, String reason) {
        super(itemName, itemType, deliveryFees, discount, price);
        this.salesId = salesId;
        this.salesPersonId = salesPersonId;
        this.date = date;
        this.customerName = customerName;
        this.customerContactNo = customerContactNo;
        this.customerAddress = customerAddress;
        this.quantity = quantity;
        this.status = status;
        this.productStatus = productStatus;
        this.reason = reason;
    }
    
    public SalesOrderQuotation(){}

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(String salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContactNo() {
        return customerContactNo;
    }

    public void setCustomerContactNo(String customerContactNo) {
        this.customerContactNo = customerContactNo;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    //custom validations
    public static boolean isValidCustomerName(String customerName) {
        return customerName.matches("^[a-zA-Z\\s]+$"); // Only allow alphabetical characters and spaces
    }

    public static boolean isValidCustomerPhoneNumber(String customerContactNo) {
        return customerContactNo.matches("^\\+?\\d{1,3}(\\s\\d{1,15})?$|^\\d{1,15}$");
    }

    public static boolean isValidCustomerAddress(String customerAddress) {
        return customerAddress.matches("^[a-zA-Z0-9\\s.,\\-\\n]+$");
    }

    public ArrayList<SalesOrderQuotation> ImportData(String salespersonId) {
        ArrayList<SalesOrderQuotation> quotations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("sales_data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] list = line.split(", ");

                // Check if the array has enough elements
                if (list.length >= 15) {
                    String dataSalespersonId = list[1]; // Assuming salespersonId is at index 1
                    if (salespersonId.equals(dataSalespersonId)) {
                        quotations.add(new SalesOrderQuotation(list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13], list[14]));
                    }
                } else {
                    // Handle the case where there are not enough elements in the array
                    System.err.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotations;
    }
    
    public ArrayList<SalesOrderQuotation> ImportData(String salespersonId, String statusFilter) {
        ArrayList<SalesOrderQuotation> quotations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("sales_data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] list = line.split(", ");

                // Check if the array has enough elements
                if (list.length >= 15) {
                    String dataSalespersonId = list[1]; 
                    String dataStatus = list[12]; 

                    if (salespersonId.equals(dataSalespersonId) && statusFilter.equals(dataStatus)) {
                        quotations.add(new SalesOrderQuotation(list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13], list[14]));
                    }
                } else {
                    // Handle the case where there are not enough elements in the array
                    System.err.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quotations;
    }
    
    public static void importDataToTable(ArrayList<SalesOrderQuotation> quotations, JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for(SalesOrderQuotation q:quotations){
            String[] allDataRow = {q.getSalesId(),q.getSalesPersonId(),q.getDate(),q.getCustomerName(),
                q.getCustomerContactNo(),q.getCustomerAddress(),q.getItemName(),q.getItemType(),q.getDeliveryFees(),
                q.getDiscount(),q.getPrice(),q.getQuantity(),q.getStatus(),q.getReason()};
            model.addRow(allDataRow);
        }
    }
    
    public static void WriteNewData(ArrayList<SalesOrderQuotation> quotations) {
        try {
            // Create a set to store unique and sorted entries
            Set<String> uniqueSortedEntries = new TreeSet<>();

            // Read existing data from the file
            try (BufferedReader br = new BufferedReader(new FileReader("sales_data.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    uniqueSortedEntries.add(line);
                }
            }

            // Check if each new entry already exists, and add only unique entries
            for (SalesOrderQuotation i : quotations) {
                String entry = i.getSalesId() + ", " + i.getSalesPersonId() + ", " + i.getDate() + ", " + i.getCustomerName() + ", "
                        + i.getCustomerContactNo() + ", " + i.getCustomerAddress() + ", " + i.getItemName() + ", " + i.getItemType() + ", "
                        + i.getDeliveryFees() + ", " + i.getDiscount() + ", " + i.getPrice() + ", " + i.getQuantity() + ", " + i.getStatus() + ", " + i.getProductStatus() + ", " + i.getReason();

                if (uniqueSortedEntries.add(entry)) {
                    // Only add if the entry is unique
                    uniqueSortedEntries.add(entry);
                }
            }

            // Write the unique and sorted entries back to the file
            try (PrintWriter pr = new PrintWriter("sales_data.txt")) {
                for (String line : uniqueSortedEntries) {
                    pr.println(line);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(SalesOrderQuotation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public static ArrayList<String> ReadFile(String file){
        ArrayList<String> data = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            
            while((line=br.readLine())!=null){
                data.add(line);
            }
        br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SalesOrderQuotation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SalesOrderQuotation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return data; 
    }
    
   public ArrayList<SalesOrderQuotation> UpdateData(ArrayList<SalesOrderQuotation> quotations, String salesId) {
        // Modify the specific record in memory
        for (SalesOrderQuotation q : quotations) {
            if (salesId.equals(q.getSalesId())) {
                q.setSalesPersonId(this.getSalesPersonId());
                q.setCustomerName(this.getCustomerName());
                q.setCustomerContactNo(this.getCustomerContactNo());
                q.setCustomerAddress(this.getCustomerAddress());
                q.setItemName(this.getItemName());
                q.setItemType(this.getItemType());
                q.setDeliveryFees(this.getDeliveryFees());
                q.setDiscount(this.getDiscount());
                q.setPrice(this.getPrice());
                q.setQuantity(this.getQuantity());
                q.setStatus(this.getStatus());

                // Save the updated record to the file immediately
                saveUpdatedRecordToFile(q);
            }
        }

        return quotations;
    }

    private void saveUpdatedRecordToFile(SalesOrderQuotation updatedQuotation) {
        ArrayList<String> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("sales_data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] list = line.split(", ");
                String currentSalesId = list[0];
                // Check if the record corresponds to the updated sales ID
                if (updatedQuotation.getSalesId().equals(currentSalesId)) {
                    // Use the updated data for the modified record
                    data.add(updatedQuotation.getSalesId() + ", " + updatedQuotation.getSalesPersonId() + ", " + updatedQuotation.getDate() + ", "
                            + updatedQuotation.getCustomerName() + ", " + updatedQuotation.getCustomerContactNo() + ", "
                            + updatedQuotation.getCustomerAddress() + ", " + updatedQuotation.getItemName() + ", "
                            + updatedQuotation.getItemType() + ", " + updatedQuotation.getDeliveryFees() + ", "
                            + updatedQuotation.getDiscount() + ", " + updatedQuotation.getPrice() + ", "
                            + updatedQuotation.getQuantity() + ", " + updatedQuotation.getStatus() + ", "
                            + updatedQuotation.getProductStatus() + ", " + updatedQuotation.getReason());
                } else {
                    // Keep other records unchanged
                    data.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write back all the modified records to the file
        try (PrintWriter pr = new PrintWriter("sales_data.txt")) {
            for (String line : data) {
                pr.println(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SalesOrderQuotation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public static ArrayList<SalesOrderQuotation>DeleteData(ArrayList<SalesOrderQuotation> quotations, String salesId){
        ArrayList<SalesOrderQuotation> newQuotations = new ArrayList<>();
        try {
           for(SalesOrderQuotation q:quotations){
               String salesID = q.getSalesId();
               if(salesID.equals(salesId)){
               } else {
                   newQuotations.add(q);
               }
            }
               PrintWriter pr = new PrintWriter("sales_data.txt");
               for (SalesOrderQuotation i: newQuotations){
                
                String salesid = i.getSalesId();
                String salesPersonId = i.getSalesPersonId();
                String date = i.getDate();
                String customerName = i.getCustomerName();
                String customerContactNo = i.customerContactNo;
                String customerAddress = i.getCustomerAddress();
                String itemName = i.getItemName();
                String itemType = i.getItemType();
                String deliveryFee = i.getDeliveryFees();
                String discount = i.getDiscount();
                String price = i.getPrice();
                String quantity = i.getQuantity();
                String status = i.getStatus();
                String productStatus = i.getProductStatus();
                String reason = i.getReason();
                                            
                pr.println(salesid+ ", " +salesPersonId + ", " + date + ", "+ customerName + ", " + customerContactNo + ", " + 
                        customerAddress + ", " + itemName + ", " + itemType + ", " + deliveryFee + ", " + discount + ", " + price + 
                        ", " + quantity + ", " + status + ", " + productStatus + ", " + reason);
               }          
                pr.close();
               
        } catch (FileNotFoundException ex) {
           Logger.getLogger(SalesOrderQuotation.class.getName()).log(Level.SEVERE, null, ex);         
       }
       return newQuotations;
    }
        
    
}
