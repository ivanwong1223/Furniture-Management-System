package Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Update {
//method overloading
    private String filename;
    
    //constructor
    public Update(String filename) {
        this.filename = filename;
    }
    
    //method with changing status (manage worker profile)
    public boolean updateDetails(String id, String name, String password, String role, String contactNo, String email, String status){
        String staffId = id;
        String newName = name;
        String newPassword = password;
        String staffRole = role;
        String newContactNo = contactNo;
        String newEmail = email;
        String newStatus = status;

        try {
            File inputFile = new File(filename);
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
                    userDetails[6] = newStatus;
                    
                    String newDetails = String.join(", ", userDetails); //join back after spliting it in array
                    writer.println(newDetails);
                } else {
                    writer.println(details);
                }
            }

            scanner.close();
            writer.close();
            
            inputFile.delete();
            
            tempFile.renameTo(new File(filename));
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //method without changing status (manage personal profile)
//    public boolean updateDetails(String id, String name, String password, String role, String contactNo, String email){
//        String staffId = id;
//        String newName = name;
//        String newPassword = password;
//        String staffRole = role;
//        String newContactNo = contactNo;
//        String newEmail = email;
//
//        try {
//            File inputFile = new File(filename);
//            File tempFile = new File("temp.txt");
//            Scanner scanner = new Scanner(inputFile);
//            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
//
//            while (scanner.hasNextLine()) {
//                String details = scanner.nextLine();
//                if (details.contains(staffId) && details.contains(staffRole)) {
//                    String[] userDetails = details.split(", ");//it will split the data into an array
//                    userDetails[1] = newName;
//                    userDetails[2] = newPassword;
//                    userDetails[4] = newContactNo;
//                    userDetails[5] = newEmail;
//                    
//                    String newDetails = String.join(", ", userDetails); //join back after spliting it in array
//                    writer.println(newDetails);
//                } else {
//                    writer.println(details);
//                }
//            }
//
//            scanner.close();
//            writer.close();
//            
//            inputFile.delete();
//            
//            tempFile.renameTo(new File(filename));
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
}
