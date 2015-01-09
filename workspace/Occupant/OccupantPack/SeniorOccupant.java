/*
 * Created on Nov 10, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package OccupantPack;
/**
 * @author j13155w
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SeniorOccupant extends Occupant {
	  private boolean aARP;
	  private boolean aAA;
	  
	  public SeniorOccupant(int inAdults, int inChildren, int inNumberNights, boolean inAARP, boolean inAAA)
	  {
	    super(inAdults, inChildren, inNumberNights);
	    aARP = inAARP;
	    aAA = inAAA;
	  }
	  

	  public double getTotalCharge()
	  {

	    if ((aARP == true) && (aAA == true))
	            return super.getTotalCharge() * .85;
	    else if ((aARP == true) || (aAA == true))
	        return super.getTotalCharge() * .90;
	    else
	        return super.getTotalCharge();
	  }
	  
	  public String toString()
	  {
	    return super.toString() +
	           "AARP: " + aARP + "\n" +
	           "AAA: " + aAA + "\n";
	  }

}
