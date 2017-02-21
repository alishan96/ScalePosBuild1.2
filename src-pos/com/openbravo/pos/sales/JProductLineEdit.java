package com.openbravo.pos.sales;

/*    Wanda POS  - Africa's Gift to the World*/
//    Copyright (c) 2014-2015 IT-Kamer & previous Unicenta POS and Openbravo POS works
//    www.erp-university-africa.com
//
//    This file is part of Wanda POS
//
//    Wanda POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   Wanda POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Wanda POS.  If not, see <http://www.gnu.org/licenses/>.


//package com.openbravo.pos.sales;
//import .*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.PreparedSentence;
import com.openbravo.data.loader.SentenceExec;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.data.loader.SerializerWriteBasicExt;
import com.openbravo.data.loader.SerializerWriteParams;
import com.openbravo.data.loader.Session;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.editor.JEditorString;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.inventory.LocationsView;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.walkingcustomers.WalkingCustomers;
import com.openbravo.pos.walkingcustomers.walkingCustomersInfo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.net.aso.s;


/**
 *
 * @author adrianromero
 */
public class JProductLineEdit extends javax.swing.JDialog {
   protected BrowsableEditableData m_bd;
    private TicketLineInfo returnLine;
    private TicketLineInfo m_oLine;
    private boolean m_bunitsok;
    private boolean m_bpriceok;
    private TicketInfo w_Line;
    private TicketInfo w_ReturnLine;
    
    
    /*incomplete*/
     protected Session s;
     private TableDefinition tlocations;
    private LocationsView jeditor;
    protected AppView app;
            
    private  String customerW;
    private walkingCustomersInfo var; 
   
    /** Creates new form JProductLineEdit */
    private JProductLineEdit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }
    /** Creates new form JProductLineEdit */
    private JProductLineEdit(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }

   // public JProductLineEdit() {
    // super();   
   // }

    
    
    
    private TicketLineInfo init(AppView app, TicketLineInfo oLine) throws BasicException {
        // Inicializo los componentes
      
        initComponents();

        if (oLine.getTaxInfo() == null) {
            throw new BasicException(AppLocal.getIntString("message.cannotcalculatetaxes"));
        }

        m_oLine = new TicketLineInfo(oLine);
     
      
        m_bunitsok = true;
        m_bpriceok = true;

//  JG 7 May 14 Allow User edit of Product.Name if has EditLine permissions
//        m_jName.setEnabled(m_oLine.getProductID() == null && app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
      //  m_jRecievableTotal.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        m_jAdvance.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        m_jName1.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        m_jName1.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        m_jCustomerName.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));         
        m_jPrice.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        m_jPriceTax.setEnabled(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
        
//        m_jName.setText(m_oLine.getProperty("product.name"));

      //  m_jRecievableTotal.setText(oLine.getRecievableTotal());
        m_jAdvance.setDoubleValue(oLine.getAdvance());
        m_jName1.setText(oLine.getProductName()); 
        m_jCustomerName.setText(m_jCustomerName.getText());
        m_jUnits1.setDoubleValue(oLine.getMultiply());
        m_jPrice.setDoubleValue(oLine.getPrice()); 
        m_jPriceTax.setDoubleValue(oLine.getPriceTax());
        m_jTaxrate.setText(oLine.getTaxInfo().getName());
        
      //  m_jRecievableTotal.addPropertyChangeListener("Edition", new RecalculateRecievableTotal());
        m_jAdvance.addPropertyChangeListener("Edition", new RecalculateAdvance());
        m_jCustomerName.addPropertyChangeListener("Edition", new RecalculateWcustomers());
        m_jName1.addPropertyChangeListener("Edition", new RecalculateName());
        m_jUnits1.addPropertyChangeListener("Edition", new RecalculateUnits());
        m_jPrice.addPropertyChangeListener("Edition", new RecalculatePrice());
        m_jPriceTax.addPropertyChangeListener("Edition", new RecalculatePriceTax());

    //    m_jRecievableTotal.addEditorKeys(m_jKeys);
        m_jAdvance.addEditorKeys(m_jKeys);
        m_jCustomerName.addEditorKeys(m_jKeys);
        m_jName1.addEditorKeys(m_jKeys);
        m_jUnits1.addEditorKeys(m_jKeys);
        m_jPrice.addEditorKeys(m_jKeys);
        m_jPriceTax.addEditorKeys(m_jKeys);
        
        if (m_jName1.isEnabled()) {
            m_jName1.activate();
        } else {
            m_jUnits1.activate();
       }
        
        printTotals();
        printTotalRecievable();

        getRootPane().setDefaultButton(m_jButtonOK);   
        returnLine = null;
        setVisible(true);
         
      
       return returnLine;
       
       
     
        
    }
    
     public void printTotalRecievable() {
        
        if (m_bunitsok && m_bpriceok) {
           // m_jSubtotal.setText(m_oLine.printSubValue());
          //  m_jTotal.setText(m_oLine.printValue());
            m_jTotalRecievable.setText(m_oLine.printRecievableTotal());
            m_jButtonOK.setEnabled(true);
       } else {
            m_jSubtotal.setText(null);
            m_jTotalRecievable.setText(null);
          //  m_jTotal.setText(null);
            m_jButtonOK.setEnabled(false);
        }
    }
    
    private void printTotals() {
        
        if (m_bunitsok && m_bpriceok) {
            m_jSubtotal.setText(m_oLine.printSubValue());
            m_jTotal.setText(m_oLine.printValue());
            m_jButtonOK.setEnabled(true);
       } else {
            m_jSubtotal.setText(null);
            m_jTotal.setText(null);
            m_jButtonOK.setEnabled(false);
        }
    }
    
    private class RecalculateUnits implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Double value = m_jUnits1.getDoubleValue();
            if (value == null || value == 0.0) {
                m_bunitsok = false;
            } else {
                m_oLine.setMultiply(value);
                m_bunitsok = true;                
            }

            printTotals();
            printTotalRecievable();
        }
    }
    
    private class RecalculatePrice implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            Double value = m_jPrice.getDoubleValue();
            if (value == null || value == 0.0) {
                m_bpriceok = false;
            } else {
                m_oLine.setPrice(value);
                m_jPriceTax.setDoubleValue(m_oLine.getPriceTax());
                m_bpriceok = true;
            }

            printTotals();
            printTotalRecievable();
        }
    }    
    
    private class RecalculatePriceTax implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            Double value = m_jPriceTax.getDoubleValue();
            if (value == null || value == 0.0) {
                // m_jPriceTax.setValue(m_oLine.getPriceTax());
                m_bpriceok = false;
            } else {
                m_oLine.setPriceTax(value);
                m_jPrice.setDoubleValue(m_oLine.getPrice());
                m_bpriceok = true;
            }

            printTotals();
            printTotalRecievable();
        }
    }  
     private class RecalculateWcustomers implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
         //   String Var = m_jCustomerName.getText();
         m_oLine.setwCustomers(m_jCustomerName.getText());
          
        }
    }
     
     private class RecalculateAdvance implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
         m_oLine.setAdvance(m_jAdvance.getDoubleValue());
          printTotalRecievable();
        }
    }
     
    /*  private class RecalculateRecievableTotal implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            m_oLine.setRecievableTotal(m_jRecievableTotal.getDoubleValue);
         
          
        }
    }
     */
    
    
    private class RecalculateName implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            m_oLine.setProperty("product.name", m_jName1.getText());
         
        }
    }
    
    
    
     public void propertyChange(PropertyChangeEvent evt) {
            m_oLine.setProperty("product.name", m_jName1.getText());
         
        }
    
    
    
    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        } else if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        } else {
            return getWindow(parent.getParent());
        }
    }
   
  /*  public  String testWalkinCustomer(){
    customerW = m_jCustomerName.getText();
   
   return customerW;
    
    }
    */
    /**
     *
     * @param parent
     * @param app
     * @param oLine
     * @return
     * @throws BasicException
     */
     
    public static TicketLineInfo showMessage(Component parent, AppView app, TicketLineInfo oLine) throws BasicException {
         
        Window window = getWindow(parent);
        
        JProductLineEdit myMsg;
        if (window instanceof Frame) { 
            myMsg = new JProductLineEdit((Frame) window, true);
        } else {
            myMsg = new JProductLineEdit((Dialog) window, true);
        }
        return myMsg.init(app, oLine);
    }        

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        m_jCustomerName = new com.openbravo.editor.JEditorString();
        m_jUnits1 = new com.openbravo.editor.JEditorDouble();
        m_jPrice = new com.openbravo.editor.JEditorCurrency();
        m_jPriceTax = new com.openbravo.editor.JEditorCurrency();
        m_jTaxrate = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        m_jTotal = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        m_jSubtotal = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        m_jName1 = new com.openbravo.editor.JEditorString();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        m_jTotalRecievable = new javax.swing.JLabel();
        m_jAdvance = new com.openbravo.editor.JEditorCurrency();
        jPanel1 = new javax.swing.JPanel();
        m_jButtonCancel = new javax.swing.JButton();
        m_jButtonOK = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        m_jKeys = new com.openbravo.editor.JEditorKeys();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(AppLocal.getIntString("label.editline")); // NOI18N

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText(AppLocal.getIntString("label.price")); // NOI18N
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 120, 90, 25);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(AppLocal.getIntString("label.units")); // NOI18N
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 90, 90, 25);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("label.pricetax")); // NOI18N
        jPanel2.add(jLabel3);
        jLabel3.setBounds(10, 150, 90, 25);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("label.customer")); // NOI18N
        jLabel4.setToolTipText("");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 30, 90, 25);

        m_jCustomerName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jCustomerName);
        m_jCustomerName.setBounds(100, 30, 270, 25);

        m_jUnits1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jUnits1);
        m_jUnits1.setBounds(100, 90, 240, 25);

        m_jPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jPrice);
        m_jPrice.setBounds(100, 120, 240, 25);

        m_jPriceTax.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jPriceTax);
        m_jPriceTax.setBounds(100, 150, 240, 25);

        m_jTaxrate.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.disabledBackground"));
        m_jTaxrate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jTaxrate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jTaxrate.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTaxrate.setOpaque(true);
        m_jTaxrate.setPreferredSize(new java.awt.Dimension(150, 25));
        m_jTaxrate.setRequestFocusEnabled(false);
        jPanel2.add(m_jTaxrate);
        m_jTaxrate.setBounds(100, 180, 210, 25);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText(AppLocal.getIntString("label.tax")); // NOI18N
        jPanel2.add(jLabel5);
        jLabel5.setBounds(10, 180, 90, 25);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText(AppLocal.getIntString("label.totalcash")); // NOI18N
        jPanel2.add(jLabel6);
        jLabel6.setBounds(10, 240, 90, 25);

        m_jTotal.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.disabledBackground"));
        m_jTotal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jTotal.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTotal.setOpaque(true);
        m_jTotal.setPreferredSize(new java.awt.Dimension(150, 25));
        m_jTotal.setRequestFocusEnabled(false);
        jPanel2.add(m_jTotal);
        m_jTotal.setBounds(100, 240, 210, 25);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText(AppLocal.getIntString("label.subtotalcash")); // NOI18N
        jPanel2.add(jLabel7);
        jLabel7.setBounds(10, 210, 90, 25);

        m_jSubtotal.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.disabledBackground"));
        m_jSubtotal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jSubtotal.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jSubtotal.setOpaque(true);
        m_jSubtotal.setPreferredSize(new java.awt.Dimension(150, 25));
        m_jSubtotal.setRequestFocusEnabled(false);
        jPanel2.add(m_jSubtotal);
        m_jSubtotal.setBounds(100, 210, 210, 25);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText(AppLocal.getIntString("label.item")); // NOI18N
        jPanel2.add(jLabel8);
        jLabel8.setBounds(10, 60, 90, 25);

        m_jName1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jName1);
        m_jName1.setBounds(100, 60, 270, 25);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jLabel9.setText(bundle.getString("label.advance")); // NOI18N
        jPanel2.add(jLabel9);
        jLabel9.setBounds(10, 274, 60, 20);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText(bundle.getString("label.totalrecievable")); // NOI18N
        jPanel2.add(jLabel10);
        jLabel10.setBounds(10, 300, 90, 30);

        m_jTotalRecievable.setBackground(javax.swing.UIManager.getDefaults().getColor("TextField.disabledBackground"));
        m_jTotalRecievable.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jTotalRecievable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jTotalRecievable.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTotalRecievable.setOpaque(true);
        m_jTotalRecievable.setPreferredSize(new java.awt.Dimension(150, 25));
        m_jTotalRecievable.setRequestFocusEnabled(false);
        jPanel2.add(m_jTotalRecievable);
        m_jTotalRecievable.setBounds(100, 300, 210, 30);

        m_jAdvance.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel2.add(m_jAdvance);
        m_jAdvance.setBounds(100, 270, 240, 25);

        jPanel5.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        m_jButtonCancel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/cancel.png"))); // NOI18N
        m_jButtonCancel.setText(AppLocal.getIntString("Button.Cancel")); // NOI18N
        m_jButtonCancel.setFocusPainted(false);
        m_jButtonCancel.setFocusable(false);
        m_jButtonCancel.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonCancel.setRequestFocusEnabled(false);
        m_jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonCancelActionPerformed(evt);
            }
        });
        jPanel1.add(m_jButtonCancel);

        m_jButtonOK.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jButtonOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/ok.png"))); // NOI18N
        m_jButtonOK.setText(AppLocal.getIntString("Button.OK")); // NOI18N
        m_jButtonOK.setFocusPainted(false);
        m_jButtonOK.setFocusable(false);
        m_jButtonOK.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonOK.setRequestFocusEnabled(false);
        m_jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonOKActionPerformed(evt);
            }
        });
        jPanel1.add(m_jButtonOK);

        jPanel5.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));
        jPanel4.add(m_jKeys);

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        setSize(new java.awt.Dimension(647, 490));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonCancelActionPerformed

        dispose();
  
    }//GEN-LAST:event_m_jButtonCancelActionPerformed

    private void m_jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonOKActionPerformed

       //  m_oLine.setwCustomers(m_jCustomerName.getText());
       
       
        
       
          
           returnLine = m_oLine;
           
          
          
         // var.setwCustomers(m_jCustomerName.getText());
       
           
             dispose();
           
            
            
         
      
       
        
        
           
       

    }//GEN-LAST:event_m_jButtonOKActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private com.openbravo.editor.JEditorCurrency m_jAdvance;
    private javax.swing.JButton m_jButtonCancel;
    private javax.swing.JButton m_jButtonOK;
    private com.openbravo.editor.JEditorString m_jCustomerName;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private com.openbravo.editor.JEditorString m_jName1;
    private com.openbravo.editor.JEditorCurrency m_jPrice;
    private com.openbravo.editor.JEditorCurrency m_jPriceTax;
    private javax.swing.JLabel m_jSubtotal;
    private javax.swing.JLabel m_jTaxrate;
    private javax.swing.JLabel m_jTotal;
    private javax.swing.JLabel m_jTotalRecievable;
    private com.openbravo.editor.JEditorDouble m_jUnits1;
    // End of variables declaration//GEN-END:variables
    
}
