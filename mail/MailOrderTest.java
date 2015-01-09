/**
 * @version 1.00 07 Feb 1996 
 */

import java.awt.*;
import java.applet.*;
import java.util.*;
import java.net.*;
import java.io.*;
import corejava.*;

public class MailOrderTest extends Applet
{  public void init()
   {  
      if (DEBUG) { System.err.println("In init"); }
      Panel p = new Panel();
      p.setLayout(new FlowLayout());
      name = new Choice();
      try      
      {  URL url = new URL(getDocumentBase(), "prices.dat");
         if (DEBUG) { System.err.println("url " + url); }
         prices.load(url.openStream());
      } catch(Exception e) { showStatus("Error " + e); }

      Enumeration e = prices.propertyNames();
      while (e.hasMoreElements()) 
         name.addItem(((String)e.nextElement()).replace('+',
            ' '));
      quantity = new IntTextField(1, 0, 100, 4);
      p.add(name);
      p.add(quantity);
      p.add(new Button("Add"));
      p.add(new Button("Done"));
      p.add(new Button("Send"));
      Panel p2 = new Panel();
      p2.setLayout(new GridLayout(2, 1));
      p2.add(addressDialog());
      p2.add(p);
      
      add("North", p2);
      add("Center", canvas = new PurchaseOrderCanvas());
      //canvas.resize(250, 150);
	  canvas.reshape(250,150);
      canvas.redraw(a);
   }
   
   private Panel addressDialog()
   {  
      if (DEBUG) { System.err.println("In addressDialog"); }
      Panel p = new Panel();
      GridBagLayout gbl = new GridBagLayout();
      p.setLayout(gbl);
      
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 100;
      gbc.weighty = 100;
      add(p, new Label("Name"), gbl, gbc, 0, 0, 1, 1);
      add(p, nameField, gbl, gbc, 1, 0, 3, 1);
      add(p, new Label("Street"), gbl, gbc, 0, 1, 1, 1);
      add(p, streetField, gbl, gbc, 1, 1, 3, 1);
      add(p, new Label("City"), gbl, gbc, 0, 2, 1, 1);
      add(p, cityField, gbl, gbc, 1, 2, 3, 1);
      add(p, new Label("State"), gbl, gbc, 0, 3, 1, 1);
      add(p, stateField, gbl, gbc, 1, 3, 1, 1);
      add(p, new Label("Zip"), gbl, gbc, 2, 3, 1, 1);
      add(p, zipField, gbl, gbc, 3, 3, 1, 1);
      
      return p;
   }

   private void add(Container p, Component c, 
      GridBagLayout gbl, GridBagConstraints gbc,
      int x, int y, int w, int h)
   {  
      if (DEBUG) { System.err.println("In add"); }
      gbc.gridx = x;
      gbc.gridy = y;
      gbc.gridwidth = w;
      gbc.gridheight = h;
      gbl.setConstraints(c, gbc);
      p.add(c);
   }
   
   public boolean action(Event evt, Object arg)
   {  
      if (DEBUG) { System.err.println("In action"); }
      if (arg.equals("Add"))
      {  if (quantity.isValid())
         {  String itemName = name.getSelectedItem();
            a.addElement(new Item(itemName, 
               quantity.getValue(), 
               Format.atof((String)
                  prices.get(itemName.replace(' ', '+')))));
         }
      }   
      else if (arg.equals("Remove"))
      {  if (a.size() > 0) 
            a.removeElementAt(a.size() - 1);
      }
      else if (arg.equals("Done"))
      {  a.addElement(new Item("State Tax", 1, 0.00));
         a.addElement(new Item("Shipping", 1, 5.00));
         a.trimToSize();
      }
      else if (arg.equals("Send"))
      {  int i;
         String data;
         data = nameField.getText() + "\n"
            + streetField.getText() + "\n"
            + cityField.getText() + " "
            + stateField.getText() + " "
            + zipField.getText() + "\n\n";
         for (i = 0; i < a.size(); i++)
            data += a.elementAt(i).toString() + "\n";
         mailOrder(data);
         a = new Vector();
      }
      else return super.action(evt, arg);
      canvas.redraw(a);
      return true;
   }
   
   public void mailOrder(String sdata)
   {  
      if (DEBUG) { System.err.println("In mailOrder"); }

      String home = "lnxdaydev13";
      //String script = "/cgi-bin/stevewi/mailto.pl";
	  String script = "mailto:steve.witt@lexis-nexis.com";
      int port = 80;
      Socket s = null;
      String rdata = "";

      try {

	 s = new Socket(home, port);

         DataOutputStream os 
                 = new DataOutputStream(s.getOutputStream());
         DataInputStream is 
                 = new DataInputStream(s.getInputStream());

         os.writeBytes("POST " + script
            + " HTTP/1.0\r\n"
            + "Content-type: application/octet-stream\r\n"
            + "Content-length: "
	    + sdata.length() + "\r\n\r\n");
         os.writeBytes(sdata);

         String line;
         while ((line = is.readLine()) != null)
            rdata += line + "\n";
         showStatus(rdata);

         os.close();
         is.close();
      }
      catch (Exception e) {  
         showStatus("Error " + e);
         if (s != null) {
			 try {
				 s.close(); }
            catch (IOException ex) {}
         }
      }

   }

   private Vector a = new Vector();
   private Choice name;
   private IntTextField quantity;
   private PurchaseOrderCanvas canvas;   
   private int m = 1;
   private Properties prices = new Properties();
   private TextField nameField = new TextField();
   private TextField streetField = new TextField();
   private TextField cityField = new TextField();
   private TextField stateField = new TextField();
   private TextField zipField = new TextField();
   private boolean DEBUG=true;
}

class Item
{  Item(String n, int q, double u)
   {  
      if (DEBUG) { System.err.println("In Item Constructor"); }
      name = n;
      quantity = q;
      unitPrice = u;
   }
   
   public String toString()
   {  
      if (DEBUG) { System.err.println("In toString"); } 
      return new Format("%-20s").form(name)
         + new Format("%6d").form(quantity)
         + new Format("%8.2f").form(unitPrice);
   }

   private String name;
   private int quantity;
   private double unitPrice;
   private boolean DEBUG=true;
}

class PurchaseOrderCanvas extends Canvas
{  public void redraw(Vector new_a)
   {  
      if (DEBUG) { System.err.println("In redraw"); }
      a = new_a;
      repaint();
   }
   
   public void paint(Graphics g)
   {  
      if (DEBUG) { System.err.println("In paint"); }
      Font f = new Font("Courier", Font.PLAIN, 12);
      g.setFont(f);
      FontMetrics fm = g.getFontMetrics(f);
      int height = fm.getHeight();
      int x = 0;
      int y = 0; 
      int i = 0;
      y += height;
      g.drawString("Your Order: ", x, y);
      for (i = 0; i < a.size(); i++)
      {  y += height;      
         g.drawString(a.elementAt(i).toString(), x, y);
      }
   }
  
   private Vector a;
   private boolean DEBUG=true;
}
