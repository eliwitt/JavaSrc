package Steve;
import java.util.*;

/**
 *
 * @author Copyright (c) 2000 by BEA Systems, Inc. All Rights Reserved.
 */
public class CalendarBean {

    protected Calendar cal;
    private String Color = "#EEEEEE";

    public CalendarBean() {
	cal = new GregorianCalendar();
    }

    // setter for Color property
    public void setColor(String color) {
	Color=color;
    }

    public String getHtmlMonth() {
	// remember what day it is, because we reset
	// it temporarily
	int saveDay = cal.get(Calendar.DAY_OF_MONTH);
	String top= "<table border=0 width=200 bgcolor=\"" + 
            Color + "\">\n<tr>" +
	    "<th colspan=7>" + 
	    moy() + " " +cal.get(Calendar.YEAR) +
	    "</th></tr>\n<tr><td>S</td><td>M</td>"+
	    "<td>T</td><td>W</td><td>T</td>"+
	    "<td>F</td><td>S</td></tr>\n";
	String bottom = "\n</table>";
	cal.set(Calendar.DAY_OF_MONTH, 1);

	String firstWeek = "<tr>";
	int i = 1;
	int day = 1;
	for (i=1; i < 8; i++) {
	    if (i == cal.get(Calendar.DAY_OF_WEEK))
		break;
	    firstWeek += "<td>&nbsp</td>";
	}
	cal.set(Calendar.DAY_OF_MONTH, saveDay);
	while (i < 8) {
	    firstWeek += ("<td>" + day + "</td>");
	    ++day;
	    ++i;
	}
	firstWeek+="</tr>\n";

	String middle = "";
	while (day <= cal.getMaximum(Calendar.DAY_OF_MONTH)) {
	    middle+="<tr>"; // a new week
	    for (i = 1; i < 8; i++) {
		if (day > cal.getMaximum(Calendar.DAY_OF_MONTH))
		    break;
		middle+=("<td>"+day+"</td>");
		++day;
	    }
	    middle+="</tr>\n";
	}
	middle+= "</tr>\n";

	return top+firstWeek+middle+bottom;
    }

    public String getTodayString() 
    {
	String todayString = 
	    dow() + ", " +  moy() + " " + 
	    cal.get(Calendar.DAY_OF_MONTH) +
	    ", " + cal.get(Calendar.YEAR);
	return todayString;
    }	

    String dow()
    {
	String[] days = {
	    "Sunday", "Monday", "Tuesday",
	    "Wednesday", "Thursday", "Friday",
	    "Saturday" };
	return (days[cal.get(Calendar.DAY_OF_WEEK)-1]);
    }

    String moy()
    {
	String[] months = {
	    "January", "February", "March", "April",
	    "May", "June", "July", "August", "September",
	    "October", "November", "December" };
	return (months[cal.get(Calendar.MONTH)]);
    }
}

    
