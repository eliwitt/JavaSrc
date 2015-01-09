package com.erac.datamart.xml;

import com.erac.datamart.reporting.ReportConstants;

/**
 * 
 * Creation date: (6/6/01 8:47:11 AM)
 * @author: Bill Lovewell
 */
public class XslFactory implements ReportConstants
{
	private static String indent2 = "  ";
	private static String indent4 = "    ";
	private static String indent6 = "      ";
	private static String indent8 = "        ";
	private static String indent10 = "          ";
	private static String indent12 = "            ";
/**
 * XslFactory constructor comment.
 */
private XslFactory() {
	super();
}
/**
 * 
 * Creation date: (6/22/01 10:23:25 AM)
 * @return java.lang.String
 */
public static String buildClaimsOfficeData() 
{
	StringBuffer dataTable = new StringBuffer("");
	
	// XSL info
	dataTable.append(constructHeader());
	dataTable.append(constructStylesheetBegin());
	dataTable.append(constructTemplateBegin());

	String[] elements = constructClaimsOfficeTotalElements();

	dataTable.append(new XmlForEachData("ROWSET/ROW").toString());
	dataTable.append("<tr>");
	
	dataTable.append("<TD align=\"left\" vAlign=\"top\" width=\"5\"><IMG height=\"1\" src=\"images/dotBlank.gif\" width=\"10\" /></TD>");
	dataTable.append(new XmlValueOfData("<TD colspan=\"2\" align=\"left\" class=\"smallText\" vAlign=\"center\">", elements[1], "<xsl:text disable-output-escaping=\"yes\">&amp;nbsp;</xsl:text></TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[3], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[4], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[5], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[6], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[7], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[8], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[9], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[10], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[11], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[12], "</TD>").toString());

	dataTable.append("</tr>");
	dataTable.append(new XmlForEachData().toString());

	dataTable.append(constructTemplateEnd());
	dataTable.append(constructStylesheetEnd());
	
	return dataTable.toString();
}
/**
 * 
 * Creation date: (6/22/01 10:23:25 AM)
 * @return java.lang.String
 */
public static String buildClaimsOfficeTotal() 
{
	StringBuffer dataTable = new StringBuffer("");
	
	// XSL info
	dataTable.append(constructHeader());
	dataTable.append(constructStylesheetBegin());
	dataTable.append(constructTemplateBegin());

	String[] elements = constructClaimsOfficeTotalElements();

	dataTable.append(new XmlForEachData("ROWSET/ROW").toString());
	dataTable.append("<tr>");
	
	dataTable.append("<TD align=\"left\" vAlign=\"top\" width=\"5\"><IMG height=\"1\" src=\"images/dotBlank.gif\" width=\"10\" /></TD>");
	dataTable.append(new XmlValueOfData("<TD colspan=\"2\" bgColor=\"#dbdbdb\" valign=\"middle\" align=\"left\">", elements[2], "<xsl:text disable-output-escaping=\"yes\">&amp;nbsp;</xsl:text></TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[3], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[4], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[5], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[6], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[7], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[8], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[9], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[10], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[11], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD valign=\"middle\" bgColor=\"#dbdbdb\" align=\"right\" class=\"smallText\">", elements[12], "</TD>").toString());


	dataTable.append("</tr>");
	dataTable.append(new XmlForEachData().toString());

	dataTable.append(constructTemplateEnd());
	dataTable.append(constructStylesheetEnd());
	
	return dataTable.toString();
}
/**
 * Creates a comma separated XSL.  
 * Creation date: (6/14/01 1:12:07 PM)
 * @return java.lang.String
 */
public static String buildCS(String[] columnNames, String[] columnValues) 
{
	StringBuffer xsl = new StringBuffer(constructHeader());
	xsl.append(constructStylesheetBegin());
	xsl.append(constructTemplateBegin());
	xsl.append(constructCS(columnNames, columnValues));
	xsl.append(constructTemplateEnd());
	xsl.append(constructStylesheetEnd());

	return xsl.toString();
}
/**
 * 
 * Creation date: (6/22/01 10:23:25 AM)
 * @return java.lang.String
 */
public static String buildDetailData() 
{
	StringBuffer dataTable = new StringBuffer("");
	
	// XSL info
	dataTable.append(constructHeader());
	dataTable.append(constructStylesheetBegin());
	dataTable.append(constructTemplateBegin());

	String[] elements = constructDetailElements();

	dataTable.append(new XmlForEachData("ROWSET/ROW").toString());
	dataTable.append("<tr>");

	dataTable.append(new XmlValueOfData("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">", elements[0], "<IMG height=\"1\" src=\"images/dotBlank.gif\" width=\"10\" /></TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">", elements[1], " </TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"middle\" class=\"smallText\" vAlign=\"center\">", elements[2], "</TD>").toString());

	dataTable.append(new XmlValueOfData("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">", elements[3], " </TD>").toString());
//	dataTable.append("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">{" + elements[3] + "} </TD>");

	dataTable.append(new XmlValueOfData("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">", elements[4], " </TD>").toString());

	dataTable.append(new XmlValueOfData("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">", elements[5], " </TD>").toString());
//	dataTable.append("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">{" + elements[5] + "} </TD>");

	dataTable.append(new XmlValueOfData("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">", elements[6], " </TD>").toString());
//	dataTable.append("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">{" + elements[6] + "} </TD>");

	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[7], " </TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[8], " </TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[9], " </TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[10], " </TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[11], " </TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[12], " </TD>").toString());

	dataTable.append("</tr>");
	dataTable.append(new XmlForEachData().toString());

	dataTable.append(constructTemplateEnd());
	dataTable.append(constructStylesheetEnd());

	return dataTable.toString();
}
/**
 * Constructs the XSL for the Home office data. 
 * Creation date: (7/5/01 10:25:34 AM)
 */
public static String buildHomeOfficeData() 
{
	StringBuffer dataTable = new StringBuffer("");
	
	// XSL info
	dataTable.append(constructHeader());
	dataTable.append(constructStylesheetBegin());
	dataTable.append(constructTemplateBegin());

	String[] elements = constructHomeOfficeElements();

	dataTable.append(new XmlForEachData("ROWSET/ROW").toString());
	dataTable.append("<tr>");
	dataTable.append("<td valign=\"top\" align=\"left\" width=\"5\" height=\"20\"><img src=\"images/dotBlank.gif\" width=\"5\" height=\"20\"/></td>");
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"left\" class=\"smallText\"><a href=\"javascript:submitPage(\'report?" + CUSTOMER_SELECTED + "=" 
		+ "{" + elements[0] + "}" + "_{" + elements[1] + "}" + "\')" +"\">", elements[1], "</a><xsl:text disable-output-escaping=\"yes\">&amp;nbsp;</xsl:text></td>").toString());
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[2], "</td>").toString());	
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[3], "</td>").toString());
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[4], "</td>").toString());	
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[5], "</td>").toString());
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[6], "</td>").toString());
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[7], "</td>").toString());
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[8], "</td>").toString());
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[9], "</td>").toString());
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[10], "</td>").toString());
	dataTable.append(new XmlValueOfData("<td valign=\"middle\" align=\"right\" class=\"smallText\">", elements[11], "</td>").toString());
	dataTable.append("</tr>");
	dataTable.append(new XmlForEachData().toString());

	dataTable.append(constructTemplateEnd());
	dataTable.append(constructStylesheetEnd());

	return dataTable.toString();
}
/**
 * 
 * Creation date: (6/22/01 10:23:25 AM)
 * @return java.lang.String
 */
public static String buildStateSummaryData() 
{
	StringBuffer dataTable = new StringBuffer("");
	
	// XSL info
	dataTable.append(constructHeader());
	dataTable.append(constructStylesheetBegin());
	dataTable.append(constructTemplateBegin());

	String[] elements = constructStateSummaryElements();

	dataTable.append(new XmlForEachData("ROWSET/ROW").toString());
	dataTable.append("<tr>");

	dataTable.append("<TD align=\"left\" vAlign=\"top\" width=\"5\"><IMG height=\"1\" src=\"images/dotBlank.gif\" width=\"10\" /></TD>");
	dataTable.append(new XmlValueOfData("<TD align=\"left\" class=\"smallText\" vAlign=\"center\">", elements[2], "<xsl:text disable-output-escaping=\"yes\">&amp;nbsp;</xsl:text></TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[3], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[4], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[5], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[6], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[7], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[8], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[9], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[10], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[11], "</TD>").toString());
	dataTable.append(new XmlValueOfData("<TD align=\"right\" class=\"smallText\" vAlign=\"center\">", elements[12], "</TD>").toString());

	dataTable.append("</tr>");
	dataTable.append(new XmlForEachData().toString());

	dataTable.append(constructTemplateEnd());
	dataTable.append(constructStylesheetEnd());

	return dataTable.toString();
}
/**
 * Constructs a simple XSL from the parameters given.  
 * Creation date: (6/6/01 8:48:47 AM)
 * @return String XSL returned.
 * @param tableHeaders String[] The headings to display in the table. The number of these
 * 	should match the number of elements. 
 * @param forEach String The for-each select string. E.g. "ROWSET/ROW" or "CAR".
 * @param elements String Each XML element to display in the table.  These much match the
 * 	elements in the XML to transform. 
 * @param tableAttributes String Attributes to apply to the table constructed. E.g. 
 *	"border=\"1\" color=\"blue\"".  Note that if quotes are put around the values, then
 *  the escape character needs to be used.
 */
public static String buildXslTable(String[] tableHeaders, String forEach, String[] elements, 
	String tableAttributes) 
{
	StringBuffer xsl = new StringBuffer(constructHeader());
	xsl.append(constructStylesheetBegin());
	xsl.append(constructTemplateBegin());
	xsl.append(constructTableBegin(tableAttributes));
	xsl.append(constructTableHeaders(tableHeaders));
  xsl.append(constructForEachBegin(forEach));
	xsl.append(constructRows(elements));
	xsl.append(constructForEachEnd(forEach));
	xsl.append(constructTableEnd());
	xsl.append(constructTemplateEnd());
	xsl.append(constructStylesheetEnd());

	return xsl.toString();
}
/**
 * 
 * Creation date: (6/26/01 8:53:52 AM)
 */
private static String[] constructClaimsOfficeTotalElements()
{
	String[] elements = new String[13];
	elements[0] = COLUMN_CUSTOMER_KEY;
	elements[1] = COLUMN_CUSTOMER_NAME;
	elements[2] = COLUMN_STATE;
	elements[3] = COLUMN_ARMS;
	elements[4] = COLUMN_NON_ARMS;
	elements[5] = COLUMN_TOTAL_INVOICES;
	elements[6] =	COLUMN_AVG_ARMS_RATE;
	elements[7] = COLUMN_AVG_RATE_PER_DAY;
	elements[8] = COLUMN_AVG_BILLED_PER_DAY;
	elements[9] = COLUMN_AVG_BILLED_DAYS;
	elements[10] = COLUMN_AVG_EXTENSIONS;
	elements[11] = COLUMN_AVG_BILLED_PER_INVOICE;
	elements[12] = COLUMN_TOTAL_BILLED;

	return elements;
}
/**
 * 
 * Creation date: (6/14/01 1:26:38 PM)
 * @return java.lang.String
 * @param columnNames java.lang.String[]
 * @param columnValues java.lang.String[]
 */
private static String constructCS(String[] columnNames, String[] columnValues) 
{
	StringBuffer b = new StringBuffer("");

	if (columnNames != null)
	{
		for (int i = 0; i < columnNames.length; i++)
		{
			if (i == 0)
				b.append(columnNames[i].trim());
			else
				b.append("," + columnNames[i].trim());
		}
	}

	b.append("\n<xsl:for-each select=\"ROWSET/ROW\">\n");

	if (columnValues != null)
	{
		for (int i = 0; i < columnValues.length; i++)
		{
			if (i == 0)
				b.append("<xsl:value-of select=\"" + columnValues[i].trim() + "\"/>");
			else
				b.append("," + "<xsl:value-of select=\"" + columnValues[i].trim() + "\"/>");
		}

		b.append("<xsl:text>&#010;</xsl:text>");
	}
	
	b.append("\n</xsl:for-each>\n");

	return b.toString();
}
/**
 * 
 * Creation date: (6/26/01 8:53:52 AM)
 */
private static String[] constructDetailElements()
{
	String[] elements = new String[13];
	elements[0] = COLUMN_STATE;
	elements[1] = COLUMN_CLAIM_NUMBER;
	elements[2] = COLUMN_ARMS;
	elements[3] = COLUMN_ADJUSTOR;
	elements[4] = COLUMN_RENTER;
	elements[5] =	COLUMN_INSURED;
	elements[6] = COLUMN_REPAIR_SHOP;
	elements[7] = COLUMN_AUTHORIZED_RATE;
	elements[8] = COLUMN_DAILY_RENTAL_RATE;
	elements[9] = COLUMN_DAILY_BILLED_RATE;
	elements[10] = COLUMN_BILLED_DAYS;
	elements[11] = COLUMN_EXTENSIONS;
	elements[12] = COLUMN_TOTAL_BILLED;

	return elements;
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructForEachBegin(String forEach) {
	if (forEach != null)
		return indent4 + "<xsl:for-each select=\"" + forEach + "\">\n";
	else 
		return "";
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructForEachEnd(String forEach) {
	if (forEach != null)
		return indent2 + "</xsl:for-each>\n";
	else 
		return "";
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructHeader() {
	return "<?xml version=\"1.0\"?>\n";
}
/**
 * 
 * Creation date: (6/26/01 8:53:52 AM)
 */
private static String[] constructHomeOfficeElements()
{
	String[] elements = new String[12];
	elements[0] = COLUMN_CUSTOMER_KEY;
	elements[1] = COLUMN_CUSTOMER_NAME;
	elements[2] = COLUMN_ARMS;
	elements[3] = COLUMN_NON_ARMS;
	elements[4] = COLUMN_TOTAL_INVOICES;
	elements[5] =	COLUMN_AVG_ARMS_RATE;
	elements[6] = COLUMN_AVG_RATE_PER_DAY;
	elements[7] = COLUMN_AVG_BILLED_PER_DAY;
	elements[8] = COLUMN_AVG_BILLED_DAYS;
	elements[9] = COLUMN_AVG_EXTENSIONS;
	elements[10] = COLUMN_AVG_TOTAL_BILLED;
	elements[11] = COLUMN_TOTAL_BILLED;

	return elements;
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructRows(String[] elements) 
{
	if (elements != null)
	{
		if (elements.length > 0)
		{
			StringBuffer rows = new StringBuffer(indent2 + "<tr>\n");

			for (int i = 0; i < elements.length; i++)
			{
				rows.append(indent4 + "<td>\n" + indent6 + "<xsl:value-of select=\"" + 
					elements[i] + "\"/>\n" + indent4 + "</td>\n");
			}

			rows.append(indent2 + "</tr>\n");
			return rows.toString();
		}
		else
			return "";
	}
	else
		return "";
}
/**
 * 
 * Creation date: (6/26/01 8:53:52 AM)
 */
private static String[] constructStateSummaryElements()
{
	String[] elements = new String[13];
	elements[0] = COLUMN_CUSTOMER_KEY;
	elements[1] = COLUMN_CUSTOMER_NAME;
	elements[2] = COLUMN_STATE;
	elements[3] = COLUMN_ARMS;
	elements[4] = COLUMN_NON_ARMS;
	elements[5] = COLUMN_TOTAL_INVOICES;
	elements[6] =	COLUMN_AVG_ARMS_RATE;
	elements[7] = COLUMN_AVG_RATE_PER_DAY;
	elements[8] = COLUMN_AVG_BILLED_PER_DAY;
	elements[9] = COLUMN_AVG_BILLED_DAYS;
	elements[10] = COLUMN_AVG_EXTENSIONS;
	elements[11] = COLUMN_AVG_BILLED_PER_INVOICE;
	elements[12] = COLUMN_TOTAL_BILLED;

	return elements;
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructStylesheetBegin() {
	return "<xsl:stylesheet " +	"version=\"1.0\" " + 
		"xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" + 
		"<xsl:output method=\"html\" encoding=\"utf-8\" />";
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructStylesheetEnd() {
	return "</xsl:stylesheet>";
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructTableBegin(String tableAttributes) {
	if (tableAttributes != null)
		return "<table " + tableAttributes + ">\n";
	else
		return "<table>\n";
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructTableEnd() {
	return "</table>\n";
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructTableHeaders(String[] tableHeaders) {
	if (tableHeaders != null)
	{
		if (tableHeaders.length > 0)
		{
			StringBuffer sb = new StringBuffer(indent2 + "<tr>\n");

			for (int i = 0; i < tableHeaders.length; i++)
			{
				sb.append(indent4 + "<th>" + tableHeaders[i] + "</th>\n");
			}
				
			sb.append(indent2 + "</tr>\n");
			return sb.toString();
		}
		else
			return " ";
	}
	else
		return "";
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructTemplateBegin() {
	return "<xsl:template match=\"/\">\n";
}
/**
 * 
 * Creation date: (6/6/01 8:52:00 AM)
 * @return java.lang.String
 */
private static String constructTemplateEnd() {
	return "</xsl:template>\n";
}
/**
 * 
 * Creation date: (6/6/01 8:53:29 AM)
 * @param args java.lang.String[]
 */
public static void main(String[] args) 
{
	System.out.println(buildStateSummaryData());
	System.out.println("---------------");
	System.out.println(buildHomeOfficeData());
	System.out.println("---------------");
	System.out.println(buildDetailData());
}
}
