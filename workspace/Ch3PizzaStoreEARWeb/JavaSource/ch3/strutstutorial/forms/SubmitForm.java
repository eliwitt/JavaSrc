package ch3.strutstutorial.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import java.util.HashMap;
import ch3.strutstutorial.Customer;
/**
 * Form bean for a Struts application.
 * Users may access 6 fields on this form:
 * <ul>
 * <li>toppings - [your comment here]
 * <li>type - [your comment here]
 * <li>address - [your comment here]
 * <li>customer - [your comment here]
 * <li>size - [your comment here]
 * <li>name - [your comment here]
 * </ul>
 * @version 	1.0
 * @author
 */
public class SubmitForm extends ActionForm

{

    private java.util.HashMap toppings = new HashMap();

    private String type = null;

    private String address = null;

    private ch3.strutstutorial.Customer customer = new Customer();

    private String size = null;

    private String name = null;

    /**
     * Get toppings
     * @return java.util.HashMap
     */
    public Object getTopping(String key) {
        return toppings.get(key);
    }

    /**
     * Set toppings
     * @param <code>java.util.HashMap</code>
     */
    public void setToppings(String key, Object value) {
        toppings.put(key, value);
    }

    /**
     * Get type
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Set type
     * @param <code>String</code>
     */
    public void setType(String t) {
        this.type = t;
    }

    /**
     * Get address
     * @return String
     */
    public String getAddress() {
        return customer.getAddress();
    }

    /**
     * Set address
     * @param <code>String</code>
     */
    public void setAddress(String a) {
        customer.setAddress(a);
    }

    /**
     * Get customer
     * @return ch3.strutstutorial.Customer
     */
    public ch3.strutstutorial.Customer getCustomer() {
        return customer;
    }

    /**
     * Set customer
     * @param <code>ch3.strutstutorial.Customer</code>
     */
    public void setCustomer(ch3.strutstutorial.Customer c) {
        this.customer = c;
    }

    /**
     * Get size
     * @return String
     */
    public String getSize() {
        return size;
    }

    /**
     * Set size
     * @param <code>String</code>
     */
    public void setSize(String s) {
        this.size = s;
    }

    /**
     * Get name
     * @return String
     */
    public String getName() {
        return customer.getName();
    }

    /**
     * Set name
     * @param <code>String</code>
     */
    public void setName(String n) {
        customer.setName(n);
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {

        // Reset values are provided as samples only. Change as appropriate.

        toppings = new HashMap();
        type = null;
        address = null;
        customer = new Customer();
        size = null;
        name = null;

    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        // Validate the fields in your form, adding
        // adding each error to this.errors as found, e.g.

        // if ((field == null) || (field.length() == 0)) {
        //   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
        // }
        if ((customer.getName()==null) ||
        		(customer.getName().equals(""))) {
        			errors.add("Name", new ActionError
        					("error.customer.name"));
        			System.out.println("errors ....");
        		}
        return errors;

    }
}
