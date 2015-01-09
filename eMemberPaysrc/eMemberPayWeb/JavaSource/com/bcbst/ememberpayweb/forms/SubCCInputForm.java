package com.bcbst.ememberpayweb.forms;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

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
public class SubCCInputForm extends ActionForm

{

	private String subid = null;
	private Subscriber subscriber = new Subscriber();
	private String ccnumber = null;
	private String ccmonth = null;
	private String ccyear = null;


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
	
    public void reset(ActionMapping mapping, HttpServletRequest request) {

    	if (this.getSubid() == null) { 
        // Reset values are provided as samples only. Change as appropriate.
    		setSubscriber((Subscriber)request.getAttribute("theccSub"));
    	}
    	
    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        if(request.getParameterMap().containsKey("bntCan")) {
        	return errors;
        }        
        // Validate the fields in your form, adding
        // adding each error to this.errors as found, e.g.
        int numo = 0;
        int nuyear = 2000;


        if ((subscriber.getCcnum() == null) || (subscriber.getCcnum().length() == 0)) {
           errors.add("Credit Card Number", new ActionError("error.ccnum.required"));
        } else {
        	if (subscriber.getCcnum().length() < 16) {
        		errors.add("Credit Card Number", new ActionError("error.ccnum.lengther"));
        	}
    		switch (subscriber.getCctype().charAt(0)) {
    		case '0':
    			if (subscriber.getCcnum().charAt(0) != '4') {
    				errors.add("Credit Card Number", new ActionError("error.ccnum.cctype"));
    			}
    			break;
    		case '1':
    			if (subscriber.getCcnum().charAt(0) != '5') {
    				errors.add("Credit Card Number", new ActionError("error.ccnum.cctype"));
    			}
    			break;
    		}
        }

        if ((subscriber.getCcmo() == null) || (subscriber.getCcmo().length() == 0)) {
           errors.add("Month", new ActionError("error.ccmo.required"));
        } else {
            if (subscriber.getCcmo().length() == 1) {
                errors.add("Month", new ActionError("error.ccmo.length"));
            } else {

            	try {
            	// process the month is it a valid month?
            		numo = Integer.parseInt(subscriber.getCcmo());
                		if ((numo < 1) || (numo > 12)) {
                			errors.add("Month", new ActionError("error.ccmo.invmo"));
                		}
                		//if (numo < 10) subscriber.setCcmo("0" + numo);
                } catch (NumberFormatException ne) {
                	errors.add("Month", new ActionError("error.ccmo.invmo"));
                }
            }
        }
        
        
        if ((subscriber.getCcyyyy() == null) || (subscriber.getCcyyyy().length() == 0)) {
           errors.add("Year", new ActionError("error.ccyear.required"));
        } else {
        
        	if (subscriber.getCcyyyy().length() == 1) {
        		errors.add("Year", new ActionError("error.ccyear.length"));
        	} else {
        		try {
            	// process the year is it a valid year?
                	nuyear = nuyear + Integer.parseInt(subscriber.getCcyyyy());
                	Calendar cudate = new GregorianCalendar();
                	int cuyr = cudate.get(Calendar.YEAR);
                	int cumo = cudate.get(Calendar.MONTH);
                	if (nuyear < cuyr) {
                		errors.add("Year", new ActionError("error.ccyear.invyr"));
                	}
            	// do we have a card with an expired date
                	if (nuyear == cuyr) {
                		if (numo <= cumo) {
                			errors.add("Expiration Date", new ActionError("error.expdt.inv"));
                		}
                	}
                } catch (NumberFormatException ne) {
                	errors.add("Year", new ActionError("error.ccyear.invyr"));
                }
        	}
        }
        
        if (errors.isEmpty()) {
        		subscriber.setCcyyyy(Integer.toString(nuyear));        	
        }

        return errors;

    }
}
