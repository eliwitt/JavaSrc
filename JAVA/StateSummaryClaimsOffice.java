package com.erac.datamart.reporting;

import java.io.StringReader;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.erac.architecture.monitoring.Debug;

import com.erac.datamart.xml.XslFactory;
import com.erac.datamart.common.DatamartUser;
import com.erac.datamart.common.DatamartConstants;
import com.erac.datamart.common.ClaimOffice;
import com.erac.datamart.common.StringUtil;

/**
 * This report produces the State Summary by Claims Office for the Datamart.
 * Creation date: (8/20/01 12:19:29 PM)
 * @author: J. Steve Witt
 */
public class StateSummaryClaimsOffice extends ReportData {
	private int rowsToDisplay;
	
	private ArrayList claimOfficeIdList = new ArrayList();

	/** Number of claims offices for each state. */
	private ArrayList officeCount;

	/** Stores the state for the next page. Have to store this so we know where to
	* pick up at in fetchData(). */
	private int curState = -1;

	/** Need to store the first state which is displayed on each page since the
	* top total line is for that state on that page only. */
	private HashMap firstOfficeOnPageList = new HashMap();
	
	private int dataSize;
	private int rowTotal = 0;
/**
 * StateSummaryClaimsOffice constructor comment.
 * @param monthId int
 */
public StateSummaryClaimsOffice(ArrayList claimOfficeIdList, int rowsToDisplay,
	int monthId)
	throws DataRetrievalException
{
	super(monthId);
	this.rowsToDisplay = rowsToDisplay;
	this.claimOfficeIdList = claimOfficeIdList;
	this.officeCount = ReportSQL.getOfficeCount(claimOfficeIdList, getMonthId());
	dataSize = calculateDataSize(officeCount);
}
/**
 * Takes a list of Integers and the maximum number of rows to display and returns the 
 * number of pages of data there will be. 
 * Creation date: (7/12/01 8:32:21 AM)
 */
int calculateDataSize(ArrayList stateCount) 
{
	int numOfPages = 0;
	int rowCount = 0;

	for (int i = 0; i < stateCount.size(); i++)
	{
		ArrayList tmpObj = (ArrayList) stateCount.get(i);
		rowCount = ((Integer)tmpObj.get(1)).intValue();

		if (pageBreak(rowCount))
			numOfPages++;
	}
	
	if (rowTotal > 0)
	{
		numOfPages++;

		// Reset the row total for fetchData()
		rowTotal = 0;
	}

	return numOfPages;
}
/*
 *
 * fetchData method goes through the list of States we retrieved and
 * will produce the report.  For each state the total is retrieved then
 * every office in the state has a detail line that is retrieved.
 * 
 */
protected String fetchData() throws DataRetrievalException {
	
	Debug.log(METHOD_ENTRY_EXIT, "StateSummaryClaimsOffice.fetchData() Entry");
	StringBuffer html = new StringBuffer("");

	//  Found the current state that we are working with for this page.
	
	int startingElement = curState;
	
	if (startingElement == -1)
		startingElement = 0;

	// System.out.println("Start " + startingElement + "  " + curState + " rows allowed " + rowsToDisplay + " rowtotal " + rowTotal + "\n");
	//  For each state retrieve the state totals then each office
	//  has a detail line that needs to be shown
	
	for (int i = startingElement; i < officeCount.size(); i++)
	{
		curState = i;
		ArrayList currentState = (ArrayList) officeCount.get(i);
		//System.out.println("State " + ((Integer)currentState.get(0)).intValue() + " has " + ((Integer)currentState.get(1)).intValue() + " rows\n");
		int rowCount = ((Integer)currentState.get(1)).intValue();

		//  check for page overflow
		if (!pageBreak(rowCount))
		{

			String xml = ReportSQL.getOfficeSummaryTotal(((Integer)currentState.get(0)).intValue(), 
				getMonthId(), claimOfficeIdList);
			html.append(transform(xml));
			html.append(getBlankRow());
			
			html.append(transformData(ReportSQL.getOfficeSummaryData(((Integer)currentState.get(0)).intValue(), 
				getMonthId(), claimOfficeIdList)));
			html.append(getBlankRow());
		}
		else
			break;
	}
	
	rowTotal = 0;
	Debug.log(METHOD_ENTRY_EXIT, "StateSummaryClaimsOffice.fetchData() Exit");

	return getTable(html.toString());
	
}
/**
 * Returns a blank html row.  
 * Creation date: (7/13/01 2:10:14 PM)
 * @return java.lang.String
 */
private String getBlankRow() {
	return "<tr> <td colspan=\"11\"><img src=\"images/dotBlank.gif\" width=\"1\"" + 
		" height=\"4\"/></td> </tr>";
}
/**
 * getDataSize method comment.
 */
protected int getDataSize() {
	return dataSize;
}
/**
 * getName method comment.
 */
public String getName() {
	return STATE_SUMMARY_CLAIMS_OFFICE;
}
/**
 * Insert the method's description here.
 * Creation date: (8/20/01 2:41:59 PM)
 * @return java.lang.String
 */
public String getPrintableName() {
	return "State Summary by Claims Office";
}
/**
 * Insert the method's description here.
 * Creation date: (8/24/01 9:49:07 AM)
 * @return java.lang.String
 */
public String getTable(String data) {
	
	Debug.log(METHOD_ENTRY_EXIT, "StateSummaryClaimsOffice.getTable() Entry");
	StringBuffer preHtml = new StringBuffer("");

	// Html before the data -- column headings
	preHtml.append("<TABLE border=0 cellPadding=2 cellSpacing=1>");
	preHtml.append("<TR><TD align=left rowSpan=2 vAlign=top width=5><IMG height=1 src=\"images/dotBlank.gif\" width=5></TD>");
	preHtml.append("<TD align=left rowSpan=2 vAlign=center colspan=2>&nbsp;</TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 colspan=3 vAlign=center><B>--Invoices--</B></TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 class=smallText rowSpan=2 vAlign=center>Avg Rate<BR>Authorized<BR>thru ARMS</TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 class=smallText rowSpan=2 vAlign=center>Avg Rental<BR>Rate per<BR>Rental Day</TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 class=smallText rowSpan=2 vAlign=center>Avg Billed<BR>Amount per<BR>Billed Day*</TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 class=smallText rowSpan=2 vAlign=center>Avg Billed<BR>Days</TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 class=smallText rowSpan=2 vAlign=center>Avg No. of<BR>Extensions</TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 class=smallText rowSpan=2 vAlign=center>Average Billed<BR>Amount per<BR>Invoice*</TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 class=smallText rowSpan=2 vAlign=center>Total Amount<BR>Billed*</TD></TR>");

	preHtml.append("<TR><TD align=middle bgColor=#c1c1c1 class=smallText vAlign=center>&nbsp;ARMS&nbsp;</TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 class=smallText vAlign=center>&nbsp;Non-Arms&nbsp;</TD>");
	preHtml.append("<TD align=middle bgColor=#c1c1c1 class=smallText vAlign=center>&nbsp;Total&nbsp;</TD></TR>");

	// Post html
	StringBuffer postHtml = new StringBuffer("");	
	postHtml.append("</table>");

	Debug.log(METHOD_ENTRY_EXIT, "StateSummaryClaimsOffice.getTable() Exit");
	return preHtml.toString() + data + postHtml.toString();
}
/**
 * 
 * Creation date: (8/17/01 12:53:20 PM)
 * @return boolean
 * @param rowCount int
 */
boolean pageBreak(int rowCount) 
{
	// Add one if it's the first row on the screen, two otherwise
	if (rowTotal != 0)
		rowCount += 2;
	else
		rowCount++;

	if ( (rowCount + rowTotal) >= rowsToDisplay)
	{
		if (rowTotal == 0)
			if (rowCount >= rowsToDisplay)
			{
				rowTotal = 0;
				return true;
			}

		// Minus one since it will be the first one on the next page and will use the total
		// in the heading
		rowTotal = rowCount - 1;
		return true;
	}
	else
		rowTotal += rowCount;
 
	return false;
}
/**
 * Generate table for Office Detail
 *
 * Creation date: (7/13/01 2:42:30 PM)
 * @return java.lang.String
 * @param xml java.lang.String
 */
private String transform(String xml) throws DataRetrievalException
{
	TransformerFactory tFactory = TransformerFactory.newInstance();
	String xsl = XslFactory.buildClaimsOfficeTotal();
	StreamSource ss = new StreamSource(new StringReader(xsl));
	StringWriter writer = new StringWriter();

	try 
	{
		Transformer transformer = tFactory.newTransformer(ss);
		transformer.transform(new StreamSource(new StringReader(xml)), 
			new StreamResult(writer));
	} 
	catch (TransformerException e) 
	{
		Debug.log(USEFUL_INFO, "StateSummaryReportData.fetchData() TransformerException: " + e);
		throw new DataRetrievalException("StateSummaryReportData.fetchData(): " + 
			"TransformerException: " + e);
	}

	return writer.toString();
}
/**
 * Generate table for Office Detail
 *
 * Creation date: (7/13/01 2:42:30 PM)
 * @return java.lang.String
 * @param xml java.lang.String
 */
private String transformData(String xml) throws DataRetrievalException
{
	TransformerFactory tFactory = TransformerFactory.newInstance();
	String xsl = XslFactory.buildClaimsOfficeData();
	StreamSource ss = new StreamSource(new StringReader(xsl));
	StringWriter writer = new StringWriter();

	try 
	{
		Transformer transformer = tFactory.newTransformer(ss);
		transformer.transform(new StreamSource(new StringReader(xml)), 
			new StreamResult(writer));
	} 
	catch (TransformerException e) 
	{
		Debug.log(USEFUL_INFO, "StateSummaryReportData.fetchData() TransformerException: " + e);
		throw new DataRetrievalException("StateSummaryReportData.fetchData(): " + 
			"TransformerException: " + e);
	}

	return writer.toString();
}
}
