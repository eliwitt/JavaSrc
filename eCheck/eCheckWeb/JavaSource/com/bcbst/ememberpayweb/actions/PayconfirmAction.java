package com.bcbst.ememberpayweb.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bcbst.ememberpayweb.forms.ProcessPayment;

/**
 * @version 	1.0
 * @author
 */
public class PayconfirmAction extends Action {

	private static Logger _logger = Logger.getLogger(PayconfirmAction.class);
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward(); // return value
        ProcessPayment ccForm = (ProcessPayment) form;

        try {
        	//  do something
        } catch (Exception e) {
        	_logger.error("execute --> Exception", e);
            // Report the error using the appropriate name and ID.
            errors.add("name", new ActionError("id"));
        }

        // If a message is required, save the specified key(s)
        // into the request for use by the <struts:errors> tag.

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
        }
        if(request.getParameterMap().containsKey("bntCan")) {
        	forward = mapping.findForward("cancel");
            
            //HttpSession sess = request.getSession(true);
            //sess.invalidate();
           	ccForm.setCcmonth("");
        	ccForm.setCcnumber("");
        	ccForm.setCcyear("");
        	ccForm.setFirstName("");
        	ccForm.setLastName("");
        	ccForm.setAddress1("");
        	ccForm.setAddress2("");
        	ccForm.setCity("");
        	ccForm.setState("");
        	ccForm.setZip("");
        	ccForm.setPhoneNu("");
        	ccForm.setEmail("");
        	ccForm.setCvv("");
        	ccForm.setBsrc("AA");        	
        } 
        // Write logic determining how the user should be forwarded.
        //forward = mapping.findForward("paystatus");

        // Finish with
        return (forward);

    }
}
