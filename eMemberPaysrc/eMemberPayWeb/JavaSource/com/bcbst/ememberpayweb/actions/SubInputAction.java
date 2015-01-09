package com.bcbst.ememberpayweb.actions;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import com.bcbst.ememberpayweb.forms.SubInputForm;

/**
 * @version 	1.0
 * @author
 */
public class SubInputAction extends Action {
	
	private static Logger _logger = Logger.getLogger(SubInputAction.class);	

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward(); // return value
        SubInputForm subInputForm = (SubInputForm) form;
        
        HttpSession sess = request.getSession(true);
        sess.invalidate();
        //Subscriber sub;
        //sub = (Subscriber)sess.getAttribute("theccSub");

        try {    
        	_logger.info("execute --> subId: " + subInputForm.getSubscriber().getSubid());        	
            if (subInputForm.getSubscriber().getSubid() != null) {
            	response.addHeader("subscribernu", subInputForm.getSubscriber().getSubid());
            	request.setAttribute("subid", subInputForm.getSubscriber().getSubid());
            }
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
        forward = mapping.findForward("success");

        // Finish with
        return (forward);

    }
}
