/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package SalesPerson;

import Classes.Furniture;
import Classes.SalesOrderQuotation;
import Classes.Salesperson;
import Classes.loginSession;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author johnp
 */
public class viewPersonalOrderList extends javax.swing.JFrame {
    loginSession ls;
    ArrayList<SalesOrderQuotation> quotations;
    Timer time;
    SimpleDateFormat st;
    /**
     * Creates new form viewPersonalOrderList
     * @param session
     */
    public viewPersonalOrderList(loginSession session) {
        this.ls = session;
        initComponents();
        String id = ls.getId();
        
        
        ArrayList<Salesperson> salesperson;
        
        salesperson = new Salesperson().importUsers(id);
        for(Salesperson s:salesperson){
            String salespersonId= s.getId();
            String username = s.getName();
            if(id.equals(salespersonId)){
                usernameTxt.setText(username);
            }
        }   
       
        quotations = new SalesOrderQuotation().ImportData(id);
        SalesOrderQuotation.importDataToTable(quotations, quotationTable); 
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(quotationTable.getModel());
        quotationTable.setRowSorter(sorter);
        dateTimeFunction();    
    }
    
    public void dateTimeFunction(){
        // Date
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dd = sdf.format(d);
        dateTxt.setText(dd);
        // Time
        time = new Timer (0, new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dt = new Date();
                st = new SimpleDateFormat("hh:mm:ss a");
                String tt = st.format(dt);
                timeTF.setText(tt);
            }
        }); 
           time.start();   
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameTxt = new javax.swing.JLabel();
        dateTxt = new javax.swing.JLabel();
        timeTF = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        quotationTable = new javax.swing.JTable();
        searchBar1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        clearSearchBtn1 = new javax.swing.JButton();
        filterBox = new javax.swing.JComboBox<>();
        backBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        usernameTxt.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        usernameTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameTxt.setText("Username");

        dateTxt.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        dateTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateTxt.setText("date");

        timeTF.setFont(new java.awt.Font("Lucida Bright", 1, 12)); // NOI18N
        timeTF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeTF.setText("time");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 36)); // NOI18N
        jLabel2.setText("Sale Order Quotation Listing");
        jLabel2.setPreferredSize(new java.awt.Dimension(200, 100));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        quotationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sales ID", "SP ID", "Date", "Customer Name", "Contact No", "Address", "Item Name", "Item Type", "Delivery", "Discount", "Unit Price", "Qty", "Status", "Reason"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        quotationTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        quotationTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quotationTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(quotationTable);
        if (quotationTable.getColumnModel().getColumnCount() > 0) {
            quotationTable.getColumnModel().getColumn(0).setMaxWidth(55);
            quotationTable.getColumnModel().getColumn(1).setMaxWidth(50);
            quotationTable.getColumnModel().getColumn(2).setMaxWidth(70);
            quotationTable.getColumnModel().getColumn(8).setMaxWidth(70);
            quotationTable.getColumnModel().getColumn(9).setMaxWidth(60);
            quotationTable.getColumnModel().getColumn(10).setMaxWidth(70);
            quotationTable.getColumnModel().getColumn(11).setMaxWidth(30);
        }

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1018, 260));

        searchBar1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchBar1FocusGained(evt);
            }
        });
        searchBar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchBar1KeyReleased(evt);
            }
        });
        jPanel6.add(searchBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 6, 298, -1));

        jLabel4.setText("Search:");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(296, 9, -1, -1));

        clearSearchBtn1.setText("Clear");
        clearSearchBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSearchBtn1ActionPerformed(evt);
            }
        });
        jPanel6.add(clearSearchBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(656, 6, -1, -1));

        filterBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Approved", "Pending", "Unapproved", "Closed" }));
        filterBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterBoxItemStateChanged(evt);
            }
        });
        jPanel6.add(filterBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, -1));

        backBtn.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        backBtn.setText("Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(timeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(backBtn))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void quotationTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quotationTableMouseClicked
       
    }//GEN-LAST:event_quotationTableMouseClicked

    private void searchBar1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBar1FocusGained
        // TODO add your handling code here:
        searchBar1.setText("");
    }//GEN-LAST:event_searchBar1FocusGained

    private void searchBar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBar1KeyReleased
        String filterQuery = filterBox.getSelectedItem().toString();
        String searchStr = searchBar1.getText();
        filterAndSearchData(filterQuery, searchStr);                                                                                                
    }//GEN-LAST:event_searchBar1KeyReleased

    private void clearSearchBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSearchBtn1ActionPerformed
        searchBar1.setText("");
        String filterQuery = filterBox.getSelectedItem().toString();
        String searchStr = searchBar1.getText();
        filterAndSearchData(filterQuery, searchStr);

        // Reset table
        DefaultTableModel tableModel = (DefaultTableModel) quotationTable.getModel();
        tableModel.setRowCount(0);
        SalesOrderQuotation.importDataToTable(quotations, quotationTable);        
    }//GEN-LAST:event_clearSearchBtn1ActionPerformed

    private void filterBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterBoxItemStateChanged
        String filterQuery = filterBox.getSelectedItem().toString();
        String searchStr = searchBar1.getText();
        filterAndSearchData(filterQuery, searchStr);
    }//GEN-LAST:event_filterBoxItemStateChanged
    
    public void filterAndSearchData(String filterQuery, String searchStr) {
        DefaultTableModel model = (DefaultTableModel) quotationTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        quotationTable.setRowSorter(sorter);

        RowFilter<Object, Object> filter = null;

        // Apply combo box
        if (!"All".equals(filterQuery)) {
            filter = RowFilter.regexFilter(filterQuery);
        }

        // Apply search filter
        RowFilter<Object, Object> searchFilter = RowFilter.regexFilter("(?i)" + searchStr);

        // Combine filters if necessary
        if (filter != null) {
            sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(filter, searchFilter)));
        } else {
            sorter.setRowFilter(searchFilter);
        }
    }
    
    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        SalespersonMainMenu mainpage = new SalespersonMainMenu(ls);
        mainpage.show();
        dispose();
    }//GEN-LAST:event_backBtnActionPerformed
    
   
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(viewPersonalOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(viewPersonalOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(viewPersonalOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(viewPersonalOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new viewPersonalOrderList(ls).setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JButton clearSearchBtn1;
    private javax.swing.JLabel dateTxt;
    private javax.swing.JComboBox<String> filterBox;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable quotationTable;
    private javax.swing.JTextField searchBar1;
    private javax.swing.JLabel timeTF;
    private javax.swing.JLabel usernameTxt;
    // End of variables declaration//GEN-END:variables
}
