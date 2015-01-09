package com.bcbst.ememberpayweb.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
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
public class SubmitPayment extends ActionForm

{

	private String subid = null;
	private Subscriber subscriber = new Subscriber();
	private String ccnumber = null;
	private String ccmonth = null;
	private String ccyear = null;
	private boolean PayReset = false;


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
	
	public String getBillfrom() {
		return subscriber.getBillfrom();
	}
	
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	PayReset = true;
    	
    	if (this.getSubid()==null) {
    		//  when we move to the actual site this should work
    		String subid = null;
    		if (request.getHeader("userid") == null) {
    			subid = request.getAttribute("subid").toString();
    		} else {
    			subid = request.getHeader("userid");
    		}
    		if ((subid == null) || (subid.equalsIgnoreCase(""))) {
    			this.subscriber.setBsrc("FF");
    			this.subscriber.setBsmsg("Your id could not be passed.");
    			this.subscriber.setBserror("The id you are using was not passed to this code correctly.");
    		} else {
    			this.setSubid(subid);    		
    		//setSubscriber((Subscriber)request.getAttribute("theSubscriber"));
    			DbSubscriber dbSub = new DbSubscriber();
    			dbSub.retrieveSubscriber(this.subscriber);
    		}
    	}
    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        // no validation necessary at this point
        return errors;

    }
}
