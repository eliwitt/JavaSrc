package com.bcbst.echeckweb.forms;


import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.bcbst.ememberpayweb.Subscriber;


/**
 * Form bean for a Struts application.
 * Users may access 3 fields on this form:
 * <ul>
 * <li>bntCan - [your comment here]
 * <li>subscriber - [your comment here]
 * <li>bntVerify - [your comment here]
 * </ul>
 * @version 	1.0
 * @author
 */
public class SubscribereReg extends ActionForm

{

    private String bntCan = null;

    private String bntVerify = null;

    private Subscriber subscriber  = new Subscriber();

    /**
     * Get bntCan
     * @return String
     */
    public String getBntCan() {
        return bntCan;
    }

    /**
     * Set bntCan
     * @param <code>String</code>
     */
    public void setBntCan(String b) {
        this.bntCan = b;
    }

    /**
     * Get bntVerify
     * @return String
     */
    public String getBntVerify() {
        return bntVerify;
    }

    /**
     * Set bntVerify
     * @param <code>String</code>
     */
    public void setBntVerify(String b) {
        this.bntVerify = b;
    }

    /**
     * Get subscriber
     * @return SubscriberClass
     */
    public Subscriber getSubscriber() {
        return subscriber;
    }

    /**
     * Set subscriber
     * @param <code>SubscriberClass</code>
     */
    public void setSubscriber(Subscriber s) {
        this.subscriber = s;
    }

    class SubscriberClass {
        private String[] cctype = null;

        private String address1 = null;

        private String ccnum = null;

        private String lastname = null;

        private String ccmo = null;

        private String address2 = null;

        private String firstname = null;

        private String state = null;

        private String email = null;

        private String zip = null;

        private String ccyyyy = null;

        private String city = null;

        private String phonenu = null;

        /**
         * Get cctype
         * @return String[]
         */
        public String[] getCctype() {
            return cctype;
        }

        /**
         * Set cctype
         * @param <code>String[]</code>
         */
        public void setCctype(String[] c) {
            this.cctype = c;
        }

        /**
         * Get address1
         * @return String
         */
        public String getAddress1() {
            return address1;
        }

        /**
         * Set address1
         * @param <code>String</code>
         */
        public void setAddress1(String a) {
            this.address1 = a;
        }

        /**
         * Get ccnum
         * @return String
         */
        public String getCcnum() {
            return ccnum;
        }

        /**
         * Set ccnum
         * @param <code>String</code>
         */
        public void setCcnum(String c) {
            this.ccnum = c;
        }

        /**
         * Get lastname
         * @return String
         */
        public String getLastname() {
            return lastname;
        }

        /**
         * Set lastname
         * @param <code>String</code>
         */
        public void setLastname(String l) {
            this.lastname = l;
        }

        /**
         * Get ccmo
         * @return String
         */
        public String getCcmo() {
            return ccmo;
        }

        /**
         * Set ccmo
         * @param <code>String</code>
         */
        public void setCcmo(String c) {
            this.ccmo = c;
        }

        /**
         * Get address2
         * @return String
         */
        public String getAddress2() {
            return address2;
        }

        /**
         * Set address2
         * @param <code>String</code>
         */
        public void setAddress2(String a) {
            this.address2 = a;
        }

        /**
         * Get firstname
         * @return String
         */
        public String getFirstname() {
            return firstname;
        }

        /**
         * Set firstname
         * @param <code>String</code>
         */
        public void setFirstname(String f) {
            this.firstname = f;
        }

        /**
         * Get state
         * @return String
         */
        public String getState() {
            return state;
        }

        /**
         * Set state
         * @param <code>String</code>
         */
        public void setState(String s) {
            this.state = s;
        }

        /**
         * Get email
         * @return String
         */
        public String getEmail() {
            return email;
        }

        /**
         * Set email
         * @param <code>String</code>
         */
        public void setEmail(String e) {
            this.email = e;
        }

        /**
         * Get zip
         * @return String
         */
        public String getZip() {
            return zip;
        }

        /**
         * Set zip
         * @param <code>String</code>
         */
        public void setZip(String z) {
            this.zip = z;
        }

        /**
         * Get ccyyyy
         * @return String
         */
        public String getCcyyyy() {
            return ccyyyy;
        }

        /**
         * Set ccyyyy
         * @param <code>String</code>
         */
        public void setCcyyyy(String c) {
            this.ccyyyy = c;
        }

        /**
         * Get city
         * @return String
         */
        public String getCity() {
            return city;
        }

        /**
         * Set city
         * @param <code>String</code>
         */
        public void setCity(String c) {
            this.city = c;
        }

        /**
         * Get phonenu
         * @return String
         */
        public String getPhonenu() {
            return phonenu;
        }

        /**
         * Set phonenu
         * @param <code>String</code>
         */
        public void setPhonenu(String p) {
            this.phonenu = p;
        }

    } // end class SubscriberClass

    public void reset(ActionMapping mapping, HttpServletRequest request) {

        // Reset values are provided as samples only. Change as appropriate.

        bntCan = null;
        bntVerify = null;
        //subscriber = null;

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
