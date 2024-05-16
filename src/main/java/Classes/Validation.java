package Classes;

import javax.swing.JOptionPane;

public class Validation {
    public static boolean validateName(String name) {
        if(name.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill in Name!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!name.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(null, "Name should contain only letters and spaces", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    public static boolean validatePassword(String password) {
        if(password.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill in Password!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if ("!@#$%^&*()_+".indexOf(ch) != -1) {
                hasSpecialCharacter = true;
            } else if (Character.isWhitespace(ch)) {
                JOptionPane.showMessageDialog(null, "Password should not contain spaces", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (!hasUppercase) {
            JOptionPane.showMessageDialog(null, "Password should contain at least 1 uppercase letter", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!hasLowercase) {
            JOptionPane.showMessageDialog(null, "Password should contain at least 1 lowercase letter", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!hasDigit) {
            JOptionPane.showMessageDialog(null, "Password should contain at least 1 digit", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!hasSpecialCharacter) {
            JOptionPane.showMessageDialog(null, "Password should contain at least 1 special character", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (password.length() < 8) {
            JOptionPane.showMessageDialog(null, "Password should have at least 8 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean validateContact(String contactNo) {
        if(contactNo.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill in Contact Number!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(contactNo.trim().length() < 7 || contactNo.trim().length() > 15){
            JOptionPane.showMessageDialog(null, "Invalid Contact Number! (min 7 max 15)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean validateEmail(String email) {
        if(email.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill in Email Address!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String emailPattern = "^(.+)@(\\S+)$";
        if(!email.matches(emailPattern)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid Email", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
