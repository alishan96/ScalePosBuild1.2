/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.sales.JProductLineEdit;
import com.openbravo.pos.sales.JTicketLines;
import com.openbravo.pos.sales.TicketsEditor;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.walkingcustomers.WalkingCustomers;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author s
 */
public  class WalkingCustomerJPanel extends JPanel implements JPanelView, BeanFactoryApp, TicketsEditor {
    protected JTicketLines m_ticketlines;
        protected TicketInfo m_oTicket; 
    protected AppView m_App;
   private TicketLineInfo wCustomers;
//   private String wCust= wCustomers.getwCustomers();
  //private String nam= "dadads";
   
  /* public WalkingCustomerJPanel(){
   wCust = wCustomers.getwCustomers();
   
   
   
   }*/

   
   /* @Override
    public String getName() {
       if (wCust == null) {
            return null;
        } else {
            return wCust;
        }
    
    
            }
   */
    

   

    @Override
    public String getTitle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void activate() throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deactivate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JComponent getComponent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getBean() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setActiveTicket(TicketInfo oTicket, Object oTicketExt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TicketInfo getActiveTicket() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String getCustomerName(){
   /*
        
         int i = m_ticketlines.getSelectedIndex();
        if (i < 0){
            Toolkit.getDefaultToolkit().beep(); // no line selected
        } else {
            try {
                TicketLineInfo newline = JProductLineEdit.showMessage(this, m_App, m_oTicket.getLine(i));
               
                if (newline != null) {
                    // line has been modified
                    paintTicketLine(i, newline);
                }
            } catch (BasicException e) {
                new MessageInf(e).show(this);
            }
        }
        */
        String val = null;
        try{
           
            //int i = m_ticketlines.getSelectedIndex();
        TicketLineInfo newline = JProductLineEdit.showMessage(this, m_App, m_oTicket.getLine(1));
        val = newline.getwCustomers();
     
    }
    catch(Exception e){
    
    
    }
     return val;
    }
}
