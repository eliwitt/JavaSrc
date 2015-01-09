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

import com.bcbst.ememberpayweb.forms.SubmitPayment;

/**
 * @version 	1.0
 * @author
 */
public class SetUpPayFormAction extends Action {
	
	private static Logger _logger = Logger.getLogger(SetUpPayFormAction.class);	

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward(); // return value
        SubmitPayment ccForm = (SubmitPayment) form;

        try {
        	request.setAttribute("theccSub", ccForm.getSubscriber());
        	//request.setAttribute("subid", subid);
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
        // Write logic determining how the user should be forwarded.
        if(request.getParameterMap().containsKey("bntReg")) {
        	forward = mapping.findForward("register");
        }else {
        	if(request.getParameterMap().containsKey("bntCheck")) {
        
        		forward = mapping.findForward("echeck");
        	} else {
        		forward = mapping.findForward("continue");
        	}
        }

        // Finish with
        return (forward);
    }
}
