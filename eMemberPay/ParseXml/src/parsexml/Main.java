/*
 * Main.java
 *
 * Created on November 15, 2007, 8:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package parsexml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.io.IOException;

/**
 *
 * @author Steve
 */
public class Main {
    
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document doc;
    NodeList nl;
    String fileInput;
    /** Creates a new instance of Main */
    public Main() {
        fileInput="xmlInput.xml";
    }
    /** Creates a new instance of Main with a parm*/
    public Main(String file) {
        fileInput=file;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main xmlObj;
        if(args.length<1){
            System.out.println("USage: ParseXml <input Xml file>");
            xmlObj = new Main();
        } else {
            xmlObj = new Main(args[0]);
        }
        
        System.out.println("Reading the file " + xmlObj.fileInput);
        xmlObj.ReadXmlFile();
        
        if(xmlObj.rcode().equals("AA")) {
            System.out.println("We were successful " + xmlObj.conID());
        } else {
            System.out.println(xmlObj.rmge() + "\n" + xmlObj.ecode() + " " + xmlObj.emge());
        }
    }
    /** Retrieve the response code */
    private String rcode() {
        return doc.getElementsByTagName("RESP_CODE").item(0).getFirstChild().getNodeValue();
    }
    /** Retrieve the message */
    private String rmge() {
        return doc.getElementsByTagName("RESP_MESSAGE").item(0).getFirstChild().getNodeValue();
    }
    /** Retrieve the message */
    private String conID() {
        return doc.getElementsByTagName("CONFIRMATION_ID").item(0).getFirstChild().getNodeValue();
    }    
    /** Retrieve the message */
    private String ecode() {
        return doc.getElementsByTagName("CODE").item(0).getFirstChild().getNodeValue();
    }
    /** Retrieve the message */
    private String emge() {
        return doc.getElementsByTagName("MESSAGE").item(0).getFirstChild().getNodeValue();
    }    
    /** Read the xml data from a file */
    private void ReadXmlFile() {
        
        dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(fileInput);
        }
        catch (IOException IOe) {
            System.out.print("IO Error " + IOe.toString());
        }
        catch (ParserConfigurationException Pe) {
             System.out.print("Parser error " + Pe.toString());
        }
        catch (SAXException Saxe) {
            System.out.print("SAX Error " + Saxe.toString());
        }

    }
    
}
