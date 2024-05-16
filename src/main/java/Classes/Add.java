package Classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * encapsulation
 */
public class Add {
    private String userID;
    private String name;
    private String password ;
    private String cfmPassword;
    private String role ;
    private String contactNo;
    private String email ;
    private String status;
    
    //constructor
    public Add(String n, String p, String cp, String r, String c, String e) {
        name = n;
        password = p;
        cfmPassword = cp;
        role = r;
        contactNo = c;
        email = e;
    }
    
    public String setId(String userId) {
        userID = userId;
        int maximum = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(", ");
                if (userData.length == 7) {
                    String dataID = userData[0];

                    // Extract the numeric part of the ID using regex
                    Matcher matcher = Pattern.compile("(A|O|SP)(\\d{4})").matcher(dataID);

                    if (matcher.find()) {
                        String numberUserID = matcher.group(2);
                        System.out.println(numberUserID);
                        int number = Integer.parseInt(numberUserID);
                        maximum = Math.max(maximum, number);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        if (role.equals("officer")) {
            return userID = String.format("O%04d", maximum + 1);
        } else {
            return userID = String.format("SP%04d", maximum + 1);
        }
    }
    
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    public String getCfmPassword() {
        return cfmPassword;
    }

    public String getRole() {
        return role;
    }
    
    public String getContactNo() {
        return contactNo;
    }

    public String getEmail() {
        return email;
    }
    

    public String addNewWorker() {
        try {
            FileWriter fw = new FileWriter("user.txt", true);
            PrintWriter outputFile = new PrintWriter(fw);
            outputFile.println(setId(userID) + ", " + getName() + ", " + getCfmPassword() + ", " + getRole() + ", " + getContactNo() + ", " + getEmail() + ", " + "active");
            outputFile.close();
            return "Success";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
}
