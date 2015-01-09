package com.bcbst.ememberpayweb.forms;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
public class RegPayment extends ActionForm {
	
	private static Logger _logger = Logger.getLogger(RegPayment.class);	

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
	
	public void setFirstName(String s) { subscriber.setFirstname(s); }
	public void setLastName(String s) { subscriber.setLastname(s); }
	public void setAddress1(String s) { subscriber.setAddress1(s); }
	public void setAddress2(String s) { subscriber.setAddress2(s); }
	public void setCity(String s) { subscriber.setCity(s); }
	public void setState(String s) { subscriber.setState(s); }
	public void setZip(String s) { subscriber.setZip(s); }
	public void setPhoneNu(String s) { subscriber.setPhonenu(s); }
	public void setEmail(String s) { subscriber.setEmail(s); }
	public void setBsrc(String s) { subscriber.setBsrc(s); }
	public void setCvv(String s) { subscriber.setCvv(s); }
	
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
        int tmpzip;
        int tmpcvv;
        int numo = 0;
        int nuyear = 2000;
        String tmpphonenu;

        if ((subscriber.getFirstname() == null) || (subscriber.getFirstname().length() == 0)) {
           errors.add("First Name", new ActionError("error.fnam.required"));
        }

        if ((subscriber.getLastname() == null) || (subscriber.getLastname().length() == 0)) {
           errors.add("Last Name", new ActionError("error.lnam.required"));
        }

        if ((subscriber.getAddress1() == null) || (subscriber.getAddress1().length() == 0)) {
           errors.add("Street Address", new ActionError("error.address1.required"));
        }

        if ((subscriber.getAddress2() == null) || (subscriber.getAddress1().length() == 0)) {
           errors.add("Second Street Address", new ActionError("error.address2.required"));
        }
        
        if ((subscriber.getCity() == null) || (subscriber.getCity().length() == 0)) {
           errors.add("City", new ActionError("error.city.required"));
        }
        
        if ((subscriber.getState() == null) || (subscriber.getState().length() == 0)) {
           errors.add("State", new ActionError("error.state.required"));
        }

        if ((subscriber.getZip() == null) || (subscriber.getZip().length() == 0)) {
           errors.add("Zip", new ActionError("error.zip.required"));
        } else {
        	//  verify the zip code
            if (subscriber.getZip().length() < 5) {
               errors.add("Zip", new ActionError("error.zip.length"));
            } else {
            	try {
            	 	tmpzip = Integer.parseInt(subscriber.getZip()); 
            	} catch (NumberFormatException ne) {
            	 	errors.add("Zip", new ActionError("error.zip.length"));
            	}
            }        	
        }

        if ((subscriber.getEmail() == null) || (subscriber.getEmail().length() == 0)) {
           errors.add("Email Address", new ActionError("error.email.required"));
        }
        
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
        /*
         * Turn off the CVV field
        if ((subscriber.getCvv() == null) || (subscriber.getCvv().length() == 0)) {
           errors.add("CVV", new ActionError("error.cvv.required"));
        } else {
        	//  verify the zip code
            if (subscriber.getCvv().length() < 3) {
               errors.add("CVV", new ActionError("error.cvv.length"));
            } else {
            	try {
            	 	tmpcvv = Integer.parseInt(subscriber.getCvv()); 
            	} catch (NumberFormatException ne) {
            	 	errors.add("CVV", new ActionError("error.cvv.invcvv"));
            	}
            }        	
        }
        */
        subscriber.setCvv("");
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
        
        if ((subscriber.getPhonenu()==null) || (subscriber.getPhonenu().length() == 0)) {
            errors.add("Phone Number", new ActionError("error.phone.required"));
        } else {        	
        	tmpphonenu = subscriber.getPhonenu();
            tmpphonenu = tmpphonenu.replaceAll("\\D", "");
            subscriber.setPhonenu(tmpphonenu);
            if (subscriber.getPhonenu().length() < 10) {
                errors.add("Phone Number", new ActionError("error.phone.format"));
            } else {
            	try{
            		Double.parseDouble(subscriber.getPhonenu());
            		}
            	catch (NumberFormatException nfe){
            		errors.add("Phone Number", new ActionError("error.phone.format"));
            	}
            }
        }
        //
        //  Now submit the registration request to 
        //  BankServ and see what we get.
        //
        if (errors.isEmpty()) {
        	SubBankServ subServ = new SubBankServ();
    		subServ.callRegServ(this.subscriber);
    		if (!this.subscriber.getBsrc().equals("AA")) {
    			Object[] regmsg = {this.subscriber.getBsmsg() + this.subscriber.getBserror()};
    			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.register",regmsg));
    		}
        }
        //
        // after everything has passed all edits then we are
        // good to go baby.
        //
        if (errors.isEmpty()) {
        		subscriber.setCcyyyy(Integer.toString(nuyear));        	
        }

        return errors;

    }
}
