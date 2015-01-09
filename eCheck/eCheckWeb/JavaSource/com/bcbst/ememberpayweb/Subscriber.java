package com.bcbst.ememberpayweb;
import java.text.DecimalFormat;

/**
 * @author j13155w
 *
 * This class holds the subscriber information and the results from the web service call
 * 
 */
public class Subscriber {
	
	private String subid;
	private String groupid;
	private String name;
	private double amtdue;
	private String cctype;
	private String ccnum;
	private String ccmo;
	private String ccyyyy;
	private String eligibilInd;
	private String billfrom;
	private String duedate;
	private double lastdue;
	private String fromaccount;
	private String paydate;
	private String bsrc;
	private String bscon;
	private String bsmsg;
	private String bserror;	
	private String firstname;
	private String lastname;
	private String address1;
	private String address2;
	private String phonenu;
	private String city;
	private String state;
	private String zip;
	private String email;
	private String cvv;
	
	/**
	 * @return Returns the ccmo.
	 */
	public String getCcmo() {
		return this.ccmo;
	}
	/**
	 * @param ccmo The ccmo to set.
	 */
	public void setCcmo(String ccmo) {
		this.ccmo = ccmo;
	}
	/**
	 * @return Returns the ccnum.
	 */
	public String getCcnum() {
		return this.ccnum;
	}
	/**
	 * @param ccnum The ccnum to set.
	 */
	public void setCcnum(String ccnum) {
		this.ccnum = ccnum;
	}
	/**
	 * @return Returns the cctype.
	 */
	public String getCctype() {
		return this.cctype;
	}
	/**
	 * @param cctype The cctype to set.
	 */
	public void setCctype(String cctype) {
		this.cctype = cctype;
	}
	/**
	 * @return Returns the ccyyyy.
	 */
	public String getCcyyyy() {
		return this.ccyyyy;
	}
	/**
	 * @param ccyyyy The ccyyyy to set.
	 */
	public void setCcyyyy(String ccyyyy) {
		this.ccyyyy = ccyyyy;
	}
/**
 * @return Returns the name.
 */
	public String getName() {
		return this.name;
	}
/**
 * @param name The name to set.
 */
	public void setName(String name) {
		this.name = name;
	}
/**
 * @return Returns the subid.
 */
	public String getSubid() {
		return this.subid;
	}
/**
 * @param subid The subid to set.
 */
	public void setSubid(String subid) {
		this.subid = subid;
	}
	/**
	 * @return Returns the subid.
	 */
		public String getGroupid() {
			return this.groupid;
		}
	/**
	 * @param subid The subid to set.
	 */
		public void setGroupid(String groupid) {
			this.groupid = groupid;
		}	
	/**
	 * @return Returns the amtdue.
	 */
	public double getAmtdue() {
		return this.amtdue;
	}
	
	public String getScramtdue() {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("$##,##0.00");
		//return '$' + (new Double(this.amtdue)).toString();
		return df.format(this.amtdue);
	}
	/**
	 * @param amtdue The amtdue to set.
	 */
	public void setAmtdue(double amtdue) {
		this.amtdue = amtdue;
	}
	
	public String getEligibilInd() {
		return this.eligibilInd;
	}
	
	public void setEligibilInd(String value) {
		this.eligibilInd = value;
	}
	
	public String getBillfrom() {
		return this.billfrom;
	}
	
	public void setBillfrom(String value) {
		this.billfrom = value;
	}
	
	
	public String getDuedate() {
		return this.duedate;
	}
	
	public void setDuedate(String value) {
		this.duedate = value;
	}	
	
	public double getLastdue(){
		return this.lastdue;
	}
	
	public void setLastdue(double value) {
		this.lastdue = value;
	}

	public String getScrlstdue() {
		//return '$' + (new Double(this.lastdue)).toString();
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("$##,##0.00");
		return df.format(this.lastdue);
	}
	
	public String getFromaccount() {
		return this.fromaccount;
	}	
	
	public void setFromaccount(String value) {
		this.fromaccount = value;
	}
	
	public String getPaydate() {
		return this.paydate;
	}	
	
	public void setPaydate(String value) {
		this.paydate = value;
	}

	
	public String getBsrc() {
		return this.bsrc;
	}
	
	public void setBsrc(String value) {
		this.bsrc = value;
	}
	
	public String getBscon(){
		return this.bscon;
	}
	
	public void setBscon(String value) {
		this.bscon = value;
	}
	
	public String getBsmsg() {
		return this.bsmsg;
	}	
	
	public void setBsmsg(String value) {
		this.bsmsg = value;
	}
	
	public String getBserror() {
		return this.bserror;
	}	
	
	public void setBserror(String value) {
		this.bserror = value;
	}
	
	public String getFirstname() {return this.firstname; }
	public void setFirstname(String value) {this.firstname=value; }
	
	public String getLastname() {return this.lastname; }
	public void setLastname(String value) {this.lastname=value; }
	
	public String getAddress1() {return this.address1; }
	public void setAddress1(String value) {this.address1 = value; }
	
	public String getAddress2() {return this.address2; }
	public void setAddress2(String value) {this.address2 = value; }
	
	public String getCity() {return this.city; }
	public void setCity(String value) {this.city = value; }
	
	public String getState() {return this.state; }
	public void setState(String value) {this.state = value; }
	
	public String getZip() { return this.zip; }	
	public void setZip(String value) { this.zip = value; }
	
	public String getPhonenu() { return this.phonenu; }	
	public void setPhonenu(String value) { this.phonenu = value; }
	
	public String getEmail() { return this.email; }	
	public void setEmail(String value) { this.email = value; }
	
	public String getCvv() { return this.cvv; }	
	public void setCvv(String value) { this.cvv = value; }
}
