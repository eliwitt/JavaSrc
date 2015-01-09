package com.bcbst.ememberpayweb.forms;


import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.bcbst.ememberpayweb.Subscriber;

/**
 * Form bean for a Struts application.
 * Users may access 1 field on this form:
 * <ul>
 * <li>subscriber - [your comment here]
 * </ul>
 * @version 	1.0
 * @author
 */
public class SubInputForm extends ActionForm

{
	   
    private String subid = null;
    private Subscriber subscriber = new Subscriber();

    /**
     * Get subscriber
     * @return SubscriberClass
     */
    public Subscriber getSubscriber() {
        return this.subscriber;
    }

    /**
     * Set subscriber
     * @param <code>SubscriberClass</code>
     */
    public void setSubscriber(Subscriber s) {
        this.subscriber = s;
    }
 
        /**
         * Get subid
         * @return String
         */
        public String getSubid() {
            return subscriber.getSubid();
        }

        /**
         * Set subid
         * @param <code>String</code>
         */
        public void setSubid(String s) {
            subscriber.setSubid(s);
        }


    public void reset(ActionMapping mapping, HttpServletRequest request) {

    	if (this.getSubid()!=null) {
    		this.subscriber.setSubid(request.getParameter("subscriber.subid"));
    		this.subscriber.setAmtdue(0.0);
    	}
    	
    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        // Validate the fields in your form, adding
        // adding each error to this.errors as found, e.g.

        // if ((field == null) || (field.length() == 0)) {
        //   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
        // }
        return errors;

    }
}
