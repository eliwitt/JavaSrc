package com.bcbst.ememberpayweb;

import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import com.bcbst.BankServ.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
//import org.xml.sax.SAXException;
/**
 * @author j13155w
 *
 * This class handles the cc request and submits it to the Bankserv web service.
 *
 */
public class SubBankServ {
	private static Logger _logger = Logger.getLogger(SubBankServ.class);
	public static String merchantID = System.getProperty("webmerch");
	public static String webpath = System.getProperty("webpath");
	public static String profileID = "WEB";
	//
	//  Submit the payment using the member's registered card
	//
	public void callCCServ(Subscriber eSub){
	
		_logger.info("callCCServ --> Begin");
		long startTime = System.currentTimeMillis();
		String ccStr = null;
		_logger.info("callCCServ --> ccType: " + eSub.getCctype().charAt(0));
		
		switch (eSub.getCctype().charAt(0)) {
		case '0':
			ccStr = "VISA";
			break;
		case '1':
			ccStr = "MASTERCARD";
			break;
		case '2':
			ccStr = "DISCOVER";
			break;
		case '3':
			ccStr = "AMEX";
			break;
		default:
			_logger.error("callCCServ --> Unknown Credit Card Type: " + eSub.getCctype().charAt(0));			
		}		
		
		String response = null;		
		try {

            _logger.debug("callCCServ --> webpath: " + webpath);
            BSTransactionProxy proxy =	new BSTransactionProxy();
            proxy.setEndpoint(webpath);
			response = proxy.creditCardAuth(eSub.getSubid(),
						eSub.getGroupid(),
						merchantID,
						profileID,
						eSub.getCcnum(),
						ccStr,
						eSub.getCcmo() + eSub.getCcyyyy(),
						String.valueOf(eSub.getAmtdue()));
			
		} catch (RemoteException e) {
			_logger.error("callCCServ --> RemoteException", e);			
			response = "<SDP_XML_RESP><RESP_CODE>FF</RESP_CODE><RESP_MESSAGE>Web service error " + e.toString() + "</RESP_MESSAGE></SDP_XML_RESP>";
		} catch (Exception e) {
			_logger.error("callCCServ --> Exception", e);			
			response = "<SDP_XML_RESP><RESP_CODE>FF</RESP_CODE><RESP_MESSAGE>An unknown web service error group id " + System.getProperty("groupid") + " " + e.getMessage() + "</RESP_MESSAGE></SDP_XML_RESP>";
		}
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(response)));
	        ccStr = null;
	        if (doc.getElementsByTagName("RESP_CODE").getLength() > 0) {
	        	eSub.setBsrc(doc.getElementsByTagName("RESP_CODE").item(0).getFirstChild().getNodeValue());
	        	if(doc.getElementsByTagName("RESP_CODE").item(0).getFirstChild().getNodeValue().equals("AA")) {
	        		eSub.setBscon(doc.getElementsByTagName("CONFIRMATION_ID").item(0).getFirstChild().getNodeValue());
	        	} else {
	        		if (doc.getElementsByTagName("RESP_MESSAGE").getLength()> 0) {
	        			eSub.setBsmsg(doc.getElementsByTagName("RESP_MESSAGE").item(0).getFirstChild().getNodeValue());
	        		}
	        		if (doc.getElementsByTagName("CODE").getLength() > 0) {
	        			ccStr = doc.getElementsByTagName("CODE").item(0).getFirstChild().getNodeValue() + " = ";
	        			eSub.setBscon(doc.getElementsByTagName("CODE").item(0).getFirstChild().getNodeValue());
	        		}
	        		if (doc.getElementsByTagName("MESSAGE").getLength() > 0) {
	        			eSub.setBserror(ccStr + doc.getElementsByTagName("MESSAGE").item(0).getFirstChild().getNodeValue());
	        		}
	        	}
	        } else {
	        	_logger.error("callCCServ --> Unknown Response Code");	        	
	        	eSub.setBsrc("FF");
	        	eSub.setBsmsg("Unknown Failure");
	        	eSub.setBserror("9996 = There was an unknown error");
	        }

		} catch (ParserConfigurationException e) {
			_logger.error("callCCServ --> ParserConfigurationException", e);			
        	eSub.setBsrc("FF");
        	eSub.setBsmsg("Parser error ");
			eSub.setBserror(e.toString());
		} catch (SAXException e) {
			_logger.error("callCCServ --> SAXException", e);			
        	eSub.setBsrc("FF");
        	eSub.setBsmsg("SAX Error ");			
			eSub.setBserror(e.toString());
        } catch (IOException e) {
			_logger.error("callCCServ --> IOException", e);        	
        	eSub.setBsrc("FF");
        	eSub.setBsmsg("IO Error ");        	
        	eSub.setBserror(e.getMessage());
        } catch (Exception e) {
			_logger.error("callCCServ --> Exception", e);        	
        	eSub.setBsrc("FF");
        	eSub.setBsmsg("Error ");
        	eSub.setBserror("An unknown parser error " + e.getMessage());
        }		
		_logger.info("callCCServ --> End (runtime=" + (System.currentTimeMillis() - startTime) + " milis)");        
	}
	
	//
	//  Register the members cc with bankserv
	//
	public void callRegServ(Subscriber eSub){
	
		_logger.info("callRegServ --> Begin");
		long startTime = System.currentTimeMillis();
		String ccStr = null;
		_logger.info("callRegServ --> ccType: " + eSub.getCctype().charAt(0));		
		switch (eSub.getCctype().charAt(0)) {
		case '0':
			ccStr = "VISA";
			break;
		case '1':
			ccStr = "MASTERCARD";
			break;
		case '2':
			ccStr = "DISCOVER";
			break;
		case '3':
			ccStr = "AMEX";
			break;
		default:
			_logger.error("callRegServ --> Unknown Credit Card Type: " + eSub.getCctype().charAt(0));			
		}		
		
		int nuyear = 2000;
		String response = null;
		try {
            _logger.debug("callRegServ --> webpath: " + webpath);            
            BSTransactionProxy proxy =	new BSTransactionProxy();
            proxy.setEndpoint(webpath);
            nuyear = nuyear + Integer.parseInt(eSub.getCcyyyy());
            _logger.debug("callRegServ --> nuyear: " + nuyear);            
            response = proxy.customerReg(
            		eSub.getSubid(), 
					eSub.getGroupid(), 
					merchantID, 
					profileID, 
					eSub.getFirstname(), 
					eSub.getLastname(), 
					eSub.getAddress1(), 
					eSub.getAddress2(), 
					eSub.getCity(), 
					eSub.getState(), 
					eSub.getZip(), 
					eSub.getPhonenu(),
					eSub.getEmail(),
					eSub.getCcnum(), 
					ccStr, 
					eSub.getCcmo() + nuyear, 
					eSub.getCvv());
			
		} catch (RemoteException e) {
			_logger.error("callRegServ --> RemoteException", e);
			response = "<SDP_XML_RESP><RESP_CODE>FF</RESP_CODE><RESP_MESSAGE>Web service error " + e.toString() + "</RESP_MESSAGE></SDP_XML_RESP>";
		} catch (Exception e) {
			_logger.error("callRegServ --> RemoteException", e);			
			response = "<SDP_XML_RESP><RESP_CODE>FF</RESP_CODE><RESP_MESSAGE>An unknown web service error group id " + System.getProperty("groupid") + " " + e.getMessage() + "</RESP_MESSAGE></SDP_XML_RESP>";
		}
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(response)));
	        ccStr = null;
	        if (doc.getElementsByTagName("RESP_CODE").getLength() > 0) {
	        	eSub.setBsrc(doc.getElementsByTagName("RESP_CODE").item(0).getFirstChild().getNodeValue());
	        	if (!doc.getElementsByTagName("RESP_CODE").item(0).getFirstChild().getNodeValue().equals("AA")) {
	        	//	eSub.setBscon(doc.getElementsByTagName("CONFIRMATION_ID").item(0).getFirstChild().getNodeValue());
	        	//} else {
	        		if (doc.getElementsByTagName("RESP_MESSAGE").getLength()> 0) {
	        			eSub.setBsmsg(doc.getElementsByTagName("RESP_MESSAGE").item(0).getFirstChild().getNodeValue());
	        		}
	        		if (doc.getElementsByTagName("CODE").getLength() > 0) {
	        			ccStr = doc.getElementsByTagName("CODE").item(0).getFirstChild().getNodeValue() + " = "; 
	        		}
	        		if (doc.getElementsByTagName("MESSAGE").getLength() > 0) {
	        			eSub.setBserror(ccStr + doc.getElementsByTagName("MESSAGE").item(0).getFirstChild().getNodeValue());
	        		}
	        	}
	        } else {
	        	_logger.error("callRegServ --> Unknown Response Code");	        	
	        	eSub.setBsrc("FF");
	        	eSub.setBsmsg("Unknown Failure");
	        	eSub.setBserror("9996 = There was an unknown error");
	        }

		} catch (ParserConfigurationException e) {
			_logger.error("callRegServ --> ParserConfigurationException", e);			
        	eSub.setBsrc("FF");
        	eSub.setBsmsg("Parser error ");
			eSub.setBserror(e.toString());
		} catch (SAXException e) {
			_logger.error("callRegServ --> SAXException", e);			
        	eSub.setBsrc("FF");
        	eSub.setBsmsg("SAX Error ");			
			eSub.setBserror(e.toString());
        } catch (IOException e) {
			_logger.error("callRegServ --> IOException", e);        	
        	eSub.setBsrc("FF");
        	eSub.setBsmsg("IO Error ");        	
        	eSub.setBserror(e.getMessage());
        } catch (Exception e) {
			_logger.error("callRegServ --> Exception", e);        	
        	eSub.setBsrc("FF");
        	eSub.setBsmsg("Error ");
        	eSub.setBserror("An unknown parser error " + e.getMessage());
        }		
        
		_logger.info("callRegServ --> End (runtime=" + (System.currentTimeMillis() - startTime) + " milis)");        
	}
	
}
