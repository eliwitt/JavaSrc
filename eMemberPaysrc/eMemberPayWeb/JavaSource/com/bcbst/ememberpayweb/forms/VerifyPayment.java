package com.bcbst.ememberpayweb.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import java.util.*;
import java.text.*;
import com.bcbst.ememberpayweb.*;

/**
 * Form bean for a Struts application.
 * Users may access 1 field on this form:
 * <ul>
 * <li>subscriber - [your comment here]
 * </ul>
 * @version 	1.0
 * @author
 */
public class VerifyPayment extends ActionForm

{
	private String subid = null;
	private Subscriber subscriber = new Subscriber();

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber s) {
		this.subscriber = s;
	}

	public String getSubid() {
		return subscriber.getSubid();
	}
	
	public void setSubid(String s) {
		subscriber.setSubid(s);
	}
	        
	public double getAmtdue() {
		return subscriber.getAmtdue();
	}

	public void setAmtdue(double s) {
		subscriber.setAmtdue(s);
	}

	public String getCcnumber() {
		return subscriber.getCcnum();
	}
	
	public void setCcnumber(String s) {
		subscriber.setCcnum(s);
	}

	public String getCcmonth() {
		return subscriber.getCcmo();
	}
	
	public void setCcmonth(String s) {
		subscriber.setCcmo(s);
	}	

	public String getCcyear() {
		return subscriber.getCcyyyy();
	}
	
	public void setCcyear(String s) {
		subscriber.setCcyyyy(s);
	}
		
	public String getEligibilInd() {
		return subscriber.getEligibilInd();
	}
	
	public void setFirstName(String s) { subscriber.setFirstname(s); }
	public void setLastName(String s) { subscriber.setLastname(s); }
	public void setAddress1(String s) { subscriber.setAddress1(s); }
	public void setAddress2(String s) { subscriber.setAddress2(s); }
	public void setCity(String s) { subscriber.setCity(s); }
	public void setState(String s) { subscriber.setState(s); }
	public void setZip(String s) { subscriber.setZip(s); }
	public void setBsrc(String s) { subscriber.setBsrc(s); }
	
    public void reset(ActionMapping mapping, HttpServletRequest request) {

    	if (this.getSubid() == null) { 
        // Reset values are provided as samples only. Change as appropriate.
    		setSubscriber((Subscriber)request.getAttribute("VerifySub"));
    		String ccStr = null;
    		switch (this.subscriber.getCctype().charAt(0)) {
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
    		}		
    		this.subscriber.setFromaccount("********" + this.subscriber.getCcnum().substring(12) + "/" + ccStr);
    		Date now = new Date();
    		String paydt = null;
    		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    		paydt = sdf.format(new Date());
    		this.subscriber.setPaydate(paydt);
    	}
    	
    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        // Validate the fields in your form, adding
        // adding each error to this.errors as found, e.g.

        return errors;

    }
}
