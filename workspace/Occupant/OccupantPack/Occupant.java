/*
 * Created on Nov 10, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package OccupantPack;
import java.text.DecimalFormat;
/**
 * @author j13155w
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Occupant {
	  private int adults;
	  private int children;
	  private int numberNights;

	  private final double MOTEL_TAX_RATE = .10;
	  private final double ADULT_RATE = 30.00;
	  private final double ADULT_RATE_DISCOUNT = 25.00;
	  private final double CHILD_RATE = 10.00;
	  private final double CHILD_RATE_DISCOUNT = 8.00;
	  private final double BASE_CHARGE_DISCOUNT = .15;
	  
	   public Occupant(int inAdults, int inChildren, int inNumberNights)
	   {
	      adults = inAdults;
	      children = inChildren;
	      numberNights = inNumberNights;
	   }

	   public int getAdults()
	   {
	     return adults;
	   }
	   
	   public int getChildren()
	   {
	     return children;
	   }
	   
	   public int getNumberNights()
	   {
	     return numberNights;
	   }
	   
	   public double getMOTEL_TAX_RATE()
	   {
	     return MOTEL_TAX_RATE;
	   }
	   
	   public double getADULT_RATE()
	   {
	     return ADULT_RATE;
	   }
	   
	   public double getADULT_RATE_DISCOUNT()
	   {
	     return ADULT_RATE_DISCOUNT;
	   }
	   
	   public double getCHILD_RATE()
	   {
	     return CHILD_RATE;
	   }
	   
	   public double getCHILD_RATE_DISCOUNT()
	   {
	     return CHILD_RATE_DISCOUNT;
	   }
	   
	   public double getBASE_CHARGE_DISCOUNT()
	   {
	     return BASE_CHARGE_DISCOUNT;
	   }

	   public double getAdultCharge()
	   {
	     if (getAdults() >= 3)
	         return adults * ADULT_RATE_DISCOUNT * numberNights;
	     else
	         return adults * ADULT_RATE * numberNights;
	   }
	   
	   public double getChildCharge()
	   {
	     if (getChildren() >= 3)
	         return children * CHILD_RATE_DISCOUNT * numberNights;
	     else
	         return children * CHILD_RATE * numberNights;
	   }

	   public double getBaseCharge()
	   {
	      if (getNumberNights() >= 3)
	         return  getAdultCharge() + getChildCharge() - ((getAdultCharge() + getChildCharge()) * getBASE_CHARGE_DISCOUNT());
	     else
	         return getAdultCharge() + getChildCharge();
	   }
	   
	   public double getMotelTax()
	   {
	     return getBaseCharge() * MOTEL_TAX_RATE;
	   }
	   
	   public double getTotalCharge()
	   {
	     return getBaseCharge() + getMotelTax();
	   }
	   
	   public void setAdults(int inAdults)
	   {
	     adults = inAdults;
	   }
	   
	   public void setChildren(int inChildren)
	   {
	     children = inChildren;
	   }
	   
	   public void setNumberNights(int inNumberNights)
	   {
	     numberNights = inNumberNights;
	   }
	   
	   public String toString()
	   {
	      DecimalFormat number = new DecimalFormat("$,###.00");
	      return "Adults: " + adults + "\n" +
	             "Children: " + children + "\n" +
	             "Number of Nights: " + numberNights + "\n" +
	             "Adult Charge: " + number.format(getAdultCharge()) + "\n" +
	             "Child Charge: " + number.format(getChildCharge()) + "\n" +
	             "Base Charge: " + number.format(getBaseCharge()) + "\n" +
	             "Motel Tax: " + number.format(getMotelTax()) + "\n" +
	             "Total Charge: " + number.format(getTotalCharge()) + "\n" ;
	   }
}
