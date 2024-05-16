/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author johnp
 */
public class Furniture {
    private String itemName;
    private String itemType;
    private String deliveryFees;
    private String discount;
    private String price;

    public Furniture(String itemName, String itemType, String deliveryFees, String discount, String price) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.deliveryFees = deliveryFees;
        this.discount = discount;
        this.price = price;
    }
    
    public Furniture(){}

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getDeliveryFees() {
        return deliveryFees;
    }

    public void setDeliveryFees(String deliveryFees) {
        this.deliveryFees = deliveryFees;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    public ArrayList<Furniture> importData() {
        String filePath = "Furniture Price Prediction.csv";
        ArrayList<Furniture> furnitures = new ArrayList<>();
        Set<String> uniqueRows = new HashSet<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            reader.skip(1); // Skip the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                // Check if any field is empty
                if (Arrays.stream(line).anyMatch(String::isEmpty)) {
                    continue; // Skip the row if any field is empty
                }

                // Join all fields to create a unique identifier for each row
                String row = String.join(",", line);

                // Check if the entire row is already in the set (indicating a duplicate)
                if (!uniqueRows.contains(row)) {
                    // Add the row to the set for future checks
                    uniqueRows.add(row);

                    // Add the Furniture object to the list
                    furnitures.add(new Furniture(line[0], line[1], line[4], line[5], line[6]));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Furniture.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | CsvValidationException ex) {
            Logger.getLogger(Furniture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return furnitures;
    }


    
    public static void setDataToTable(ArrayList<Furniture> furnitures, JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for(Furniture f:furnitures){
            String[] allDataRow = {f.getItemName(),f.getItemType(),f.getDeliveryFees(),f.getDiscount(),f.getPrice()};
            model.addRow(allDataRow);
        }
    }
    
    
    

    
}
