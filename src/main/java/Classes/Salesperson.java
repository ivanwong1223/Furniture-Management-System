/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;


import Classes.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johnp
 */
public class Salesperson extends User {

    public Salesperson(String id, String name, String password, String role, String contactNo, String email, String status) {
        super(id, name, password, role, contactNo, email, status);
    }
    public Salesperson(){}
    
    @Override
    public void setName(String name) {
        super.setName(name);
    }
    
    @Override
    public ArrayList<Salesperson> importUsers(String id) {
        ArrayList<Salesperson> salespersonList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[3].equals("salesperson") && id.equals(data[0])) {
                    salespersonList.add(new Salesperson(data[0], data[1], data[2], data[3], data[4], data[5], data[6]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return salespersonList;
    }


    @Override
    public boolean updateDetails(String id, String name, String password, String role, String contactNo, String email) {
        String staffId = id;
        String newName = name;
        String newPassword = password;
        String staffRole = role;
        String newContactNo = contactNo;
        String newEmail = email;

        try {
            File inputFile = new File("user.txt");
            File tempFile = new File("temp.txt");
            Scanner scanner = new Scanner(inputFile);
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

            while (scanner.hasNextLine()) {
                String details = scanner.nextLine();
                if (details.contains(staffId) && details.contains(staffRole)) {
                    String[] userDetails = details.split(", ");//it will split the data into an array
                    userDetails[1] = newName;
                    userDetails[2] = newPassword;
                    userDetails[4] = newContactNo;
                    userDetails[5] = newEmail;
                    
                    String newDetails = String.join(", ", userDetails); //join back after spliting it in array
                    writer.println(newDetails);
                } else {
                    writer.println(details);
                }
            }

            scanner.close();
            writer.close();
            
            inputFile.delete();
            
            tempFile.renameTo(new File("user.txt"));
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
