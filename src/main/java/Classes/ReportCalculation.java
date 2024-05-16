/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mahpi
 */
public class ReportCalculation {
    private double approved;
    private double closed;
    private double totalSalesCount;
    private double workdoneSalesCount;
    private double monthlySales;
    private double avgMonthlySales;
    private double highestSales = 0;
    private String highestSalesID;

    public double getApproved() {
        return approved;
    }

    public double getClosed() {
        return closed;
    }

    public double getTotalSalesCount() {
        return totalSalesCount;
    }

    public double getWorkdoneSalesCount() {
        return workdoneSalesCount;
    }

    public double getMonthlySales() {
        return monthlySales;
    }

    public double getAvgMonthlySales() {
        return avgMonthlySales;
    }

    public double getHighestSales() {
        return highestSales;
    }

    public String getHighestSalesID() {
        return highestSalesID;
    }
    
    public void ACdata(DefaultTableModel model, JTable ACtable, int selectedMonthIndex){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.setRowCount(0);
        
        try (BufferedReader reader = new BufferedReader(new FileReader("sales_data.txt"))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(", ");
                String dateData = rowData[2];                
                try{
                    Date date = dateFormat.parse(dateData);
                    int month = date.getMonth() + 1;

                    if (month == selectedMonthIndex) {
                        totalSalesCount += 1;
                        if("Approved".equals(rowData[12])){
                            model.addRow(rowData);
                            approved += 1;
                        }else if("Closed".equals(rowData[12])){
                            model.addRow(rowData);
                            closed += 1;
                        }
                    }
                } catch (ParseException e){
                    e.printStackTrace();
                }                
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing the data to a file: " + e.toString());
        }       
        ACtable.setModel(model);
        ACtable.setDefaultEditor(Object.class, null); // Make the table non-editable      
    }
    
    public void Workdonedata(DefaultTableModel model, JTable WDtable,int selectedMonthIndex){
        double totalSales = 0;
        double day = 0;
        double salesPrice;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.setRowCount(0);
        
        try (BufferedReader reader = new BufferedReader(new FileReader("sales_data.txt"))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(", ");
                String dateData = rowData[2];                
                try{
                    Date date = dateFormat.parse(dateData);
                    int month = date.getMonth() + 1;
                    int year = date.getYear();
                    switch (month){
                        case 1,3,5,7,8,10,12 -> {
                            day = 31;
                        }
                        case 4,6,9,11 -> {
                            day = 30;
                        }
                        case 2 -> {
                            if (year % 4 == 0 && !(year % 100 == 0)){
                                day = 29;
                            }else{
                                day = 28;
                            }
                        }
                    }
                    if (month == selectedMonthIndex) {
                        
                        if("Workdone".equals(rowData[13])){
                            double discount = Double.parseDouble(rowData[9].replace("%", ""))/100;
                            double delivery = Double.parseDouble(rowData[8]);
                            double unitPrice = Double.parseDouble(rowData[10]);
                            double quantity = Double.parseDouble(rowData[11]);
                            salesPrice = (unitPrice * quantity) * (1 - discount) + delivery;
                            totalSales +=  salesPrice;
                            if (salesPrice > highestSales){
                                highestSales = salesPrice;
                                highestSalesID = rowData[0];
                            }
                            monthlySales = totalSales;
                            avgMonthlySales = monthlySales / day;
                            model.addRow(rowData);
                            workdoneSalesCount += 1;
                        }
                    }
                } catch (ParseException e){
                    e.printStackTrace();
                }                
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing the data to a file: " + e.toString());
        }
        WDtable.setModel(model);
        WDtable.setDefaultEditor(Object.class, null); // Make the table non-editable
    }
}
