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
public class TestSeniorOccupant {
    public static void main(String[] args) 
    {
        Occupant occ1 = new Occupant(2,3,1);
        Occupant occ2 = new Occupant(2,0,4);
        SeniorOccupant srOcc1 = new SeniorOccupant(2,3,1,true,false);
        SeniorOccupant srOcc2 = new SeniorOccupant(2,3,1,false,true);
        SeniorOccupant srOcc3 = new SeniorOccupant(2,3,1,true,true);
        SeniorOccupant srOcc4 = new SeniorOccupant(2,3,1,false,false);

        DisplayOccupant(occ1);
        DisplayOccupant(occ2);
        DisplayOccupant(srOcc1);
        DisplayOccupant(srOcc2);
        DisplayOccupant(srOcc3);
        DisplayOccupant(srOcc4);
    }
    
    private static void DisplayOccupant(Occupant occ)
    {
      DecimalFormat number = new DecimalFormat("#,###.00");
      System.out.println("Total charge; " + number.format(occ.getTotalCharge()) + "\n");
      System.out.println(occ);
      System.out.println("\n" + "------------------" + "\n");
    }
}
