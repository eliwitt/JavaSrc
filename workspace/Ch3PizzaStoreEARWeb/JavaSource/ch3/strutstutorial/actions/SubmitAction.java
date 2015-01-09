package ch3.strutstutorial.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch3.strutstutorial.forms.SubmitForm;

/**
 * @version 	1.0
 * @author
 */
public class SubmitAction extends Action

{

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ActionErrors errors = new ActionErrors();
        ActionForward forward = new ActionForward(); // return value
        SubmitForm submitForm = (SubmitForm) form;

        try {

            // do something here
        	String name = submitForm.getCustomer().getName();
        	System.out.println("The name is: " + name);
        	request.setAttribute("name",name);
        	String dtype = submitForm.getType();
        	request.setAttribute("method",dtype);
        	String sz = submitForm.getSize();
        	char szval = sz.charAt(0);
        	String fsz = null;
        	switch (szval) {
        		case 'L':
        			fsz = "Large";
        			break;
        		case 'S':
        			fsz = "Small";
        			break;
        		case 'M':
        			fsz = "Medium";
        			break;
        	}

        	request.setAttribute("size", fsz);

        } catch (Exception e) {

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
