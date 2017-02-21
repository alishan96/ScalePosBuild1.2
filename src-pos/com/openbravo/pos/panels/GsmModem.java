/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.panels;


import java.io.InputStream;
import java.io.PrintStream;
import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

public class GsmModem
{
  private static String port;
  private static int bitrate;
  private static String modem;
  private static String modemPin;
  private static String smsc;
  private static String tp;
  private static String msgBody;
  private int x=0;

  public static void configModem(String p, int b, String m, String pi, String s)
  {
    port = p;
    bitrate = b;
    modem = m;
    modemPin = pi;
    smsc = s;
  }

  public void Sender(String tpnum, String message) throws Exception {
    tp = tpnum;
    msgBody = message;
    doIt();
  }
public void setGateway(){


}
  public void doIt() throws Exception
  {
    OutboundNotification outboundNotification = new OutboundNotification();
    System.out.println("-----------------------------");
    System.out.println("*** Muhammad Ghous Khan***");
    System.out.println("Ghous");
    System.out.println("-----------------------------");
    System.out.println("Example: Send message from a serial gsm modem.");
    System.out.println(Library.getLibraryDescription());
    System.out.println("Version: " + Library.getLibraryVersion());
    SerialModemGateway gateway = new SerialModemGateway("modem.com1", port, bitrate, modem, "");
    gateway.setInbound(true);
    gateway.setOutbound(true);
    gateway.setSimPin(modemPin);
   // System.out.println("gateway model currently "+gateway.getModel().toString());
    gateway.setSmscNumber(smsc);
    
    Service.getInstance().setOutboundMessageNotification(outboundNotification);
 //  if(gateway.getModel()=="null"){
     
    Service.getInstance().addGateway(gateway);
  //  }
    Service.getInstance().startService();
    System.out.println();
    System.out.println("Modem Information:");
    System.out.println("  Manufacturer: " + gateway.getManufacturer());
    System.out.println("  Model: " + gateway.getModel());
    System.out.println("  Serial No: " + gateway.getSerialNo());
    System.out.println("  SIM IMSI: " + gateway.getImsi());
    System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
    System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
    System.out.println(" the value of x:"+x);
    System.out.println();

  //  OutboundMessage msg = new OutboundMessage(tp, msgBody);
   // Service.getInstance().sendMessage(msg);
   // System.out.println(msg);
   
  //  System.out.println("Now Sleeping - Hit <enter> to terminate.");
   // System.in.read();
  // Service.getInstance().
    //Service.getInstance().stopService();
    
   // Service.getInstance().S.
   x++;
  }
  public class OutboundNotification implements IOutboundMessageNotification {
    public OutboundNotification() {
    }

   
    public void process(AGateway gateway, OutboundMessage msg) {
      System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
      System.out.println(msg);
    }
  }
}