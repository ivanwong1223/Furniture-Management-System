package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//method overloading
public class Search {
    private JTable table;
    
    //constructor
    public Search(JTable t) {
        table = t;
    }

    //search for manage worker profile (search through staff ID)
    public boolean searchDetails(String id) {
        boolean isExistOrNot = false;
        try {
            File details = new File("user.txt");
            Scanner scanner = new Scanner(details);
            DefaultTableModel model = new DefaultTableModel();
            //"ID", "Name", "Password", "Role", "Contact Number", "Email", "Status"
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Password");
            model.addColumn("Role");
            model.addColumn("Contact Number");
            model.addColumn("Email");
            model.addColumn("Status");

            while (scanner.hasNextLine()) {
                String info = scanner.nextLine();
                String[] userDetails = info.split(", ");

                if (userDetails[0].equals(id) && !userDetails[3].equals("admin")) {
                    model.addRow(userDetails);
                    isExistOrNot = true;
                }
            }

            scanner.close();
            table.setModel(model);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return isExistOrNot;
    }
    
    //search for generate report (search through report ID)
    public boolean searchDetails(String id,int flag){
        
        boolean isExistOrNot = false;
        try {
            File details = new File("sales.txt"); // new File(salesReport.txt)
            Scanner scanner = new Scanner(details);
            DefaultTableModel model = new DefaultTableModel();
            //"ID", "Name", "Password", "Role", "Contact Number", "Email", "Status"
            model.addColumn("Report ID");
            model.addColumn("Title");
            model.addColumn("Date Created");

            while (scanner.hasNextLine()) {
                String info = scanner.nextLine();
                String[] reportDetails = info.split(", ");

                if (flag == 1) {
                    if (reportDetails[0].equals(id)) {
                        model.addRow(reportDetails);
                        isExistOrNot = true;
                    }
                }
            }

            scanner.close();
            table.setModel(model);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return isExistOrNot;
        
    }
    
}
