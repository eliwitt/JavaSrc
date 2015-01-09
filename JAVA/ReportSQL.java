package com.erac.datamart.reporting;

import java.sql.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.math.BigDecimal;
import com.erac.datamart.common.DatamartUser;
import com.erac.datamart.common.DatamartConstants;
import com.erac.datamart.common.ClaimOffice;
import com.erac.datamart.dbutil.DBManager;
import com.erac.datamart.dbutil.ConnectionFailedException;
import com.erac.architecture.monitoring.Debug;
import com.erac.datamart.xml.XmlFactory;

/**
 * ReportSQL - Methods for database querying for Reports
 * Creation date: (6/11/01 3:24:51 PM)
 * @author: Sundari Swamy
 */
public class ReportSQL implements DatamartConstants {
	private final static int FORMAT_PRECISION = 2;
	private String xml = "";
	private String preXML = "";
	private String postXML = "";
	private int monthKeyId = 0;
	private int detailCount = 0;
	private int requiredRows = 0;
	private final static String DEF_HO_SORTCOLNAME = "custname ";
	private final static String DEF_SS_SORTCOLNAME = "state ";
	private final static String DEF_DET_SORTCOLNAME = "state,claimnbr ";
/**
 * ReportSQL constructor 
 * Instance of ReportSQL used for DetailReport
 * @param int monthId
 * @param int rows - predefined rows for displaying
 */
public ReportSQL(int custKeyID, int monthID, int rows) throws DataRetrievalException
{
	super();
	this.requiredRows = rows;
	this.monthKeyId = monthID;
	getDetailCount(custKeyID);
	if (this.detailCount != 0)
	{
		getDetailData(custKeyID);
	}
}
/**
 * ReportSQL constructor 
 * Instance of ReportSQL used for DetailReport
 * @param int monthId
 * @param int rows - predefined rows for displaying
 * @param String sortColName
 * @param String sortOrder
 */
public ReportSQL(int custKeyID, int monthID, int rows, String sortColName, boolean sortOrder) throws DataRetrievalException
{
	super();
	this.requiredRows = rows;
	this.monthKeyId = monthID;
	getDetailCount(custKeyID);
	if (this.detailCount != 0)
	{
		getDetailData(custKeyID, sortColName, sortOrder);
	}
}
/**
 * Checking for null and formatting resultset object to the precision Mentioned
 * Creation date: (6/19/01 10:19:06 AM)
 * @return java.math.BigDecimal
 * @param Object obj
 * @param int precision
 */
private static BigDecimal format(Object obj, int precision)
{
	BigDecimal val = new BigDecimal(0);
	if (obj == null)
	{
		//
	}
	else
	{
		val = (BigDecimal) obj;
	}
	return val.setScale(precision, BigDecimal.ROUND_HALF_UP);
}
/**
 * to get the month,year that has data 
 * Creation date: (7/19/01 3:45:33 PM)
 * @return java.util.ArrayList
 * @exception com.erac.datamart.reporting.DataRetrievalException 
 */
public static MonthList getAllMonths() throws DataRetrievalException
{
	MonthList objMonth = new MonthList();
	Connection conn = null;
	PreparedStatement psCode = null;
	String keyIDs = "";
	StringBuffer strSQL = new StringBuffer("");
	try
	{
		strSQL.append(" SELECT ");
		strSQL.append(" data_profl_cde ");
		strSQL.append(" FROM ");
		strSQL.append(RMC_DATA_PROFL);
		strSQL.append(" WHERE data_profl_dsc = 'Direct Bill Insurance'");
		conn = DBManager.openConnection(USER_PROFILE_DATABASE);
		psCode = DBManager.getPreparedStatement(strSQL.toString(), conn);
		ResultSet rsKeyId = psCode.executeQuery();
		while (rsKeyId.next())
		{
			keyIDs = rsKeyId.getString("data_profl_cde");
		}
		DBManager.closeStatement(conn, psCode);
		DBManager.closeConnection(conn);
		strSQL.delete(0, strSQL.length());
		if (keyIDs.length() != 0)
		{
			strSQL.append(" SELECT ");
			strSQL.append(" avail_mth_key_id  ");
			strSQL.append(" FROM ");
			strSQL.append(RMM_DATA_LOG);
			strSQL.append(" WHERE data_profl_cde = ?");
			strSQL.append(" ORDER BY 1 ");
			conn = DBManager.openConnection(DATAMART_DATABASE);
			psCode = DBManager.getPreparedStatement(strSQL.toString(), conn);
			psCode.setString(1, keyIDs);
			ResultSet rsMonthId = psCode.executeQuery();
			while (rsMonthId.next())
			{
				int MonthId = rsMonthId.getInt("avail_mth_key_id");
				if (keyIDs.equals(""))
				{
					keyIDs = "" + MonthId + "";
				}
				else
				{
					keyIDs = keyIDs + "," + MonthId;
				}
			}
			DBManager.closeStatement(conn, psCode);
			strSQL.delete(0, strSQL.length());
			strSQL.append(" SELECT ");
			strSQL.append(" mth_key_id as keyID, mth_nam as month , yr_dsc  as year ");
			strSQL.append(" FROM ");
			strSQL.append(RMM_MONTH);
			strSQL.append(" WHERE mth_key_id  IN (" + keyIDs + ")");
			strSQL.append(" ORDER BY 1 ");
			psCode = DBManager.getPreparedStatement(strSQL.toString(), conn);
			ResultSet rsMonth = psCode.executeQuery();
			while (rsMonth.next())
			{
				objMonth.add(rsMonth.getInt("keyID"), rsMonth.getString("month") + " " + rsMonth.getString("year"));
			}
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException(" connection Failed Error:" + eConn);
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException(" could not retrieve data Error " + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psCode);
		DBManager.closeConnection(conn);
	}
	return objMonth;
}
/**
 * form customer Key Id String from the offices in collection
 * Creation date: (7/12/01 10:35:41 AM)
 * @return java.lang.String
 * @param officeList java.util.Collection
 */
private static String getCustKeyString(Collection officeList)
{
	String custKeyID = "";
	for (Iterator itrList = officeList.iterator(); itrList.hasNext();)
	{
		ClaimOffice objTemp = (ClaimOffice) itrList.next();
		int intKeyId = objTemp.getCustKeyID();
		if (custKeyID.equals(""))
		{
			custKeyID = "" + intKeyId + "";
		}
		else
		{
			custKeyID = custKeyID + "," + intKeyId;
		}
	}
	return custKeyID;
}
/**
 * get Method for detail Count
 * Creation date: (7/18/01 10:27:10 AM)
 * @return int
 */
public int getDetailCount()
{
	return this.detailCount;
}
/** *this method is to get the total States for the given CustomerKeyId 
 * Creation date: (7/11/01 8:37:10 AM)
 * @param custKeyID
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
private void getDetailCount(int custKeyID) throws DataRetrievalException
{
	int intDetailCount = 0;
	Connection conn = null;
	PreparedStatement psDetailCount = null;
	ResultSet rsDetailCount = null;
	try
	{
		if (custKeyID == 0 || monthKeyId == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		StringBuffer strSQL = new StringBuffer(" SELECT ");
		strSQL.append(" COUNT(clos_br_stprov_id) as totalstate ");
		strSQL.append(" FROM ");
		strSQL.append(RMM_CLAIM_INFO);
		strSQL.append(" WHERE cust_key_id = ? ");
		strSQL.append(" AND clos_mth_key_id = ? ");
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psDetailCount = DBManager.getPreparedStatement(strSQL.toString(), conn);
		psDetailCount.setInt(1, custKeyID);
		psDetailCount.setInt(2, monthKeyId);
		rsDetailCount = psDetailCount.executeQuery();
		while (rsDetailCount.next())
		{
			setDetailCount(rsDetailCount.getInt("totalstate"));
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psDetailCount);
		DBManager.closeConnection(conn);
	}
}
/**
 * Detail Report
 * Creation date: (6/11/01 4:36:43 PM)
 * @param int CustKeyID
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
private void getDetailData(int custKeyID) throws DataRetrievalException
{
	PreparedStatement psDetail = null;
	Connection conn = null;
	try
	{
		if (custKeyID == 0 || monthKeyId == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String strSQL = getQuery(DETAIL_REPORT, "");
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psDetail = DBManager.getPreparedStatement(strSQL, conn);
		psDetail.setInt(1, custKeyID);
		psDetail.setInt(2, monthKeyId);
		ResultSet rsDetail = psDetail.executeQuery();
		xml = XmlFactory.getReportDataXml(rsDetail);
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed" + eConn.getMessage());
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error:" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psDetail);
		DBManager.closeConnection(conn);
	}
}
/**
 * Detail Report
 * Creation date: (6/11/01 4:36:43 PM)
 * @param int CustKeyID
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
private void getDetailData(int custKeyID, String sortColName, boolean sortOrder) throws DataRetrievalException
{
	PreparedStatement psDetail = null;
	Connection conn = null;
	try
	{
		if (custKeyID == 0 || monthKeyId == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String strSQL = getQuery(DETAIL_REPORT, "", sortColName, sortOrder);
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psDetail = DBManager.getPreparedStatement(strSQL, conn);
		psDetail.setInt(1, custKeyID);
		psDetail.setInt(2, monthKeyId);
		ResultSet rsDetail = psDetail.executeQuery();
		xml = XmlFactory.getReportDataXml(rsDetail);
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed" + eConn.getMessage());
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error:" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psDetail);
		DBManager.closeConnection(conn);
	}
}
/**
 * Detail Report Result - used for download report
 * Creation date: (6/11/01 4:36:43 PM)
 * @return  java.util.ArrayList 
 * @param int custKeyID
 * @param int monthID
 * @exception com.erac.datamart.reporting.DataRetrievalException 
 */
public static ArrayList getDetailResult(int custKeyID, int monthID) throws DataRetrievalException
{
	Connection connDetail = null;
	PreparedStatement psDetail = null;
	ArrayList colDetail = new ArrayList();
	try
	{
		if (custKeyID == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String strSQL = getQuery(DETAIL_REPORT, "");
		connDetail = DBManager.openConnection(DATAMART_DATABASE);
		psDetail = DBManager.getPreparedStatement(strSQL, connDetail);
		psDetail.setInt(1, custKeyID);
		psDetail.setInt(2, monthID);
		ResultSet rsDetailResult = psDetail.executeQuery();
		char oldChar = ',';
		char newChar = '-';
		while (rsDetailResult.next())
		{
			ArrayList objDetail = new ArrayList();
			String strTemp = "";
			objDetail.add(rsDetailResult.getString("state").trim());
			objDetail.add(rsDetailResult.getString("claimNbr").trim());
			objDetail.add(rsDetailResult.getString("arms"));
			strTemp = rsDetailResult.getString("adjustor");
			if (strTemp == null || strTemp.length() == 0)
			{
				strTemp = " N/A";
			}
			strTemp = strTemp.replace(oldChar, newChar);
			objDetail.add(strTemp);
			strTemp = rsDetailResult.getString("renter");
			if (strTemp == null || strTemp.length() == 0)
			{
				strTemp = " N/A";
			}
			strTemp = strTemp.replace(oldChar, newChar);
			objDetail.add(strTemp);
			strTemp = rsDetailResult.getString("insured");
			if (strTemp == null || strTemp.length() == 0)
			{
				strTemp = " N/A";
			}
			strTemp = strTemp.replace(oldChar, newChar);
			objDetail.add(strTemp);
			strTemp = rsDetailResult.getString("repairshop");
			if (strTemp == null || strTemp.length() == 0)
			{
				strTemp = " N/A";
			}
			strTemp = strTemp.replace(oldChar, newChar);
			objDetail.add(strTemp);
			objDetail.add(format(rsDetailResult.getObject("authrate"), FORMAT_PRECISION));
			objDetail.add(format(rsDetailResult.getObject("dlyRentalRate"), FORMAT_PRECISION));
			objDetail.add(format(rsDetailResult.getObject("dlyBilledRate"), FORMAT_PRECISION));
			objDetail.add(new Integer(rsDetailResult.getInt("bDays")));
			objDetail.add(new Integer(rsDetailResult.getInt("noExtns")));
			objDetail.add(format(rsDetailResult.getObject("tBAmt"), FORMAT_PRECISION));
			colDetail.add(objDetail);
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(connDetail, psDetail);
		DBManager.closeConnection(connDetail);
	}
	return colDetail;
}
/**
 * to get Detail Total
 * Creation date: (6/11/01 4:36:43 PM)
 * @return com.erac.datamart.Summary 
 * @param int custKeyID
 * @param int monthID
 */
public static Summary getDetailTotal(int custKeyID, int monthID) throws DataRetrievalException
{
	return getStateSummaryTotal(custKeyID, monthID);
}
/** * Office Summary Report - to get XML string
 * Creation date: (6/11/01 4:37:10 PM)
 * @param officeList java.util.collection
 * @param int monthID
 * @return string
 * @exception com.erac.datamart.reporting.DataRetrievalException 
 */
public static String getHomeOfficeData(Collection officeList, int monthID) throws DataRetrievalException
{
	PreparedStatement psSummary = null;
	ResultSet rsSummary = null;
	Connection conn = null;
	String xml = "";
	try
	{
		if (officeList.size() == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String custKeyID = getCustKeyString(officeList);
		String strSQL = getQuery(OFFICE_SUMMARY_REPORT, custKeyID);
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL, conn);
		psSummary.setInt(1, monthID);
		rsSummary = psSummary.executeQuery();
		xml = XmlFactory.getReportDataXml(rsSummary);
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return xml;
}
/** * Office Summary Report - to get XML string
 * Creation date: (6/11/01 4:37:10 PM)
 * @param officeList java.util.collection
 * @param int monthID
 * @param String sortColName
 * @param String sortOrder
 * @return string
 * @exception com.erac.datamart.reporting.DataRetrievalException 
 */
public static String getHomeOfficeData(Collection officeList, int monthID, String sortColName, boolean sortOrder) throws DataRetrievalException
{
	PreparedStatement psSummary = null;
	ResultSet rsSummary = null;
	Connection conn = null;
	String xml = "";
	try
	{
		if (officeList.size() == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String custKeyID = getCustKeyString(officeList);
		String strSQL = getQuery(OFFICE_SUMMARY_REPORT, custKeyID, sortColName, sortOrder);
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL, conn);
		psSummary.setInt(1, monthID);
		rsSummary = psSummary.executeQuery();
		xml = XmlFactory.getReportDataXml(rsSummary);
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return xml;
}
/** *getHomeOfficeSortedClaimOffices
 * @Creation Date 08/31/2001
 * @return Collection
 * @param Collection CustKeyId
 * @param int monthID 
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static Collection getHomeOfficeSortedClaimOffices(Collection allofficeList, int monthID, String sortColumn, boolean sortOrder) throws DataRetrievalException
{
	Collection claimOffices = new ArrayList();
	Connection conn = null;
	PreparedStatement psSummary = null;
	String strSortOrder = " asc";
	if (!sortOrder)
	{
		strSortOrder = " desc";
	}
	if (sortColumn.equals(""))
	{
		sortColumn = " custKeyID ";
	}
	try
	{
		if (allofficeList.size() == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String custKeyID = getCustKeyString(allofficeList);
		String strSQL = getQuery(OFFICE_SUMMARY_REPORT, custKeyID, sortColumn, sortOrder);
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL, conn);
		psSummary.setInt(1, monthID);
		ResultSet rsSummary = psSummary.executeQuery();
		while (rsSummary.next())
		{
			ClaimOffice objClaim = new ClaimOffice();
			objClaim.setCustKeyID(rsSummary.getInt("custKeyID"));
			objClaim.setClaimOfficeName(rsSummary.getString("custName"));
			claimOffices.add(objClaim);
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return claimOffices;
}
/** * Office Summary Total - To get total  for each office
 * Creation date: (6/11/01 4:37:10 PM)
 * @return com.erac.datamart.reporting 
 * @param officeList java.util.collection
 * @param int monthID
 * @exception com.erac.datamart.reporting.DataRetrievalException 
 */
public static Summary getHomeOfficeTotal(Collection allofficeList, int monthID) throws DataRetrievalException
{
	Summary objSummary = new Summary();
	PreparedStatement psSummary = null;
	Connection conn = null;
	try
	{
		if (allofficeList.size() == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String custKeyID = getCustKeyString(allofficeList);
		StringBuffer strSQL = new StringBuffer(" SELECT ");
		strSQL.append("sum(cmf.rm_ra_tot_qty) as arms,");
		strSQL.append("sum(cmf.ra_tot_qty - cmf.rm_ra_tot_qty) as nonArms,");
		strSQL.append("sum(cmf.ra_tot_qty) as tInvoices,");
		strSQL.append("round(NVL(sum(cmf.auth_rate_tot_amt)/DECODE(sum(cmf.auth_rm_ra_tot_qty),0,NULL,sum(cmf.auth_rm_ra_tot_qty)),0)," + FORMAT_PRECISION + ") as avgRateArms,");
		strSQL.append("round(sum(cmf.time_incm_tot_amt) / sum(cmf.ra_day_tot_qty)," + FORMAT_PRECISION + ") as aRPRDay, ");
		strSQL.append("round(sum(bill_to_tot_amt) / sum(ra_bill_day_qty) ," + FORMAT_PRECISION + ")as avgBAPBDay, ");
		strSQL.append("round(sum(cmf.ra_bill_day_qty) / sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgBDays, ");
		strSQL.append("round(sum(cmf.ra_ext_tot_qty) / sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgNoExtns, ");
		strSQL.append("round(sum(cmf.bill_to_tot_amt) / sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgTBA, ");
		strSQL.append("round(sum(cmf.bill_to_tot_amt)," + FORMAT_PRECISION + ") as tBAmt ");
		strSQL.append(" FROM ");
		strSQL.append(RMM_CUSTOMER_VERSION + " cv, ");
		strSQL.append(RMM_CUSTOMER_MONTH_FACT + " cmf ");
		strSQL.append(" WHERE cmf.mth_key_id = ?");
		strSQL.append(" AND cmf.cust_key_id = cv.cust_key_id ");
		strSQL.append(" AND cv.curr_vrsn_ind = 'Y' ");
		strSQL.append(" AND cv.cust_key_id IN(" + custKeyID + ") ");
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL.toString(), conn);
		psSummary.setInt(1, monthID);
		ResultSet rsSummary = psSummary.executeQuery();
		while (rsSummary.next())
		{
			objSummary.setArmsInvoices(format(rsSummary.getObject("arms"), 0));
			objSummary.setNonArmsInvoices(format(rsSummary.getObject("nonArms"), 0));
			objSummary.setTotalInvoices(format(rsSummary.getObject("tInvoices"), 0));
			objSummary.setAvgRateArms(format(rsSummary.getObject("avgRateArms"), FORMAT_PRECISION));
			objSummary.setAvgRatePerRentalDay(format(rsSummary.getObject("aRPRDay"), FORMAT_PRECISION));
			objSummary.setAvgBilledAmtPerBilledDay(format(rsSummary.getObject("avgBAPBDay"), FORMAT_PRECISION));
			objSummary.setAvgBilledDays(format(rsSummary.getObject("avgBDays"), FORMAT_PRECISION));
			objSummary.setAvgNoExtns(format(rsSummary.getObject("avgNoExtns"), FORMAT_PRECISION));
			objSummary.setAvgTotalBilledAmt(format(rsSummary.getObject("avgTBA"), FORMAT_PRECISION));
			objSummary.setTotalBilledAmt(format(rsSummary.getObject("tBAmt"), FORMAT_PRECISION));
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException(" connection Failed ");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error " + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return objSummary;
}
/** * Office Summary -  returns rows in a collection for download report
 * Creation date: (6/11/01 4:37:10 PM)
 * @return java.util.collection 
 * @param officeList java.util.collection
 * @param int monthID
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static Collection getHOResult(Collection officeList, int monthID) throws DataRetrievalException
{
	Collection summaryList = new ArrayList();
	PreparedStatement psSummary = null;
	ResultSet rsSummary = null;
	Connection conn = null;
	try
	{
		if (officeList.size() == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String custKeyID = getCustKeyString(officeList);
		String strSQL = getQuery(OFFICE_SUMMARY_REPORT, custKeyID);
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL, conn);
		psSummary.setInt(1, monthID);
		rsSummary = psSummary.executeQuery();
		while (rsSummary.next())
		{
			Summary objSummary = new Summary();
			objSummary.setCustomerKeyID(rsSummary.getInt("custKeyID"));
			objSummary.setCustomerName(rsSummary.getString("custName"));
			objSummary.setArmsInvoices(format(rsSummary.getObject("arms"), 0));
			objSummary.setNonArmsInvoices(format(rsSummary.getObject("nonArms"), 0));
			objSummary.setTotalInvoices(format(rsSummary.getObject("tInvoices"), 0));
			objSummary.setAvgRateArms(format(rsSummary.getObject("avgRateArms"), FORMAT_PRECISION));
			objSummary.setAvgRatePerRentalDay(format(rsSummary.getObject("avgRPRDay"), FORMAT_PRECISION));
			objSummary.setAvgBilledAmtPerBilledDay(format(rsSummary.getObject("avgBAPBDay"), FORMAT_PRECISION));
			objSummary.setAvgBilledDays(format(rsSummary.getObject("avgBDays"), FORMAT_PRECISION));
			objSummary.setAvgNoExtns(format(rsSummary.getObject("avgNoExtns"), FORMAT_PRECISION));
			objSummary.setAvgTotalBilledAmt(format(rsSummary.getObject("avgTBA"), FORMAT_PRECISION));
			objSummary.setTotalBilledAmt(format(rsSummary.getObject("tBAmt"), FORMAT_PRECISION));
			summaryList.add(objSummary);
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return summaryList;
}
/** *this method is to get the month & year for the given monthId.
 * Creation date: (7/11/01 8:37:10 AM)
 * @return String monthyear 
 * @param int monthId
 * @exception com.erac.datamart.reporting.DataRetrievalException   
 */
public static String getMonthYear(int monthID) throws DataRetrievalException
{
	String mthYear = "";
	Connection conn = null;
	PreparedStatement psMonthId = null;
	ResultSet rsMonthId = null;
	try
	{
		if (monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data");
		}
		//
		StringBuffer strSQL = new StringBuffer(" SELECT ");
		strSQL.append(" mth_nam , yr_dsc ");
		strSQL.append(" FROM ");
		strSQL.append(RMM_MONTH);
		strSQL.append(" WHERE  mth_key_id  = ? ");
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psMonthId = DBManager.getPreparedStatement(strSQL.toString(), conn);
		psMonthId.setInt(1, monthID);
		rsMonthId = psMonthId.executeQuery();
		while (rsMonthId.next())
		{
			mthYear = rsMonthId.getString("mth_nam") + " " + rsMonthId.getString("yr_dsc");
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psMonthId);
		DBManager.closeConnection(conn);
	}
	return mthYear;
}
/** * to get the State Count for each claimOfficeID- returns rows in a ArrayList
 * Creation date: (6/11/01 4:37:10 PM)
 * @return ArrayList
 * @param OfficeList java.util.Collection
 * @param int monthID
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static ArrayList getOfficeCount(Collection allOfficeList, int monthID) throws DataRetrievalException
{
	ArrayList objCount = new ArrayList();
	Connection conn = null;
	PreparedStatement psStateCount = null;
	ResultSet rsStateTotal = null;
	String custKeyID = "";
	
	try
	{
		//  verify parameters passed in 
		if (allOfficeList.size() == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}

		//  build string with all of the offices
		//
		for (Iterator itrList = allOfficeList.iterator(); itrList.hasNext();)
		{
			ClaimOffice objTemp = (ClaimOffice) itrList.next();
			int intKeyId = objTemp.getCustKeyID();
			if (custKeyID.equals(""))
			{
				custKeyID = "" + intKeyId + "";
			}
			else
			{
				custKeyID = custKeyID + "," + intKeyId;
			}

		}

		//  build the sql statement to retrieve all of the states for this group
		//  of offices
		//
		StringBuffer strSQL = new StringBuffer(" SELECT ");
		strSQL.append(" brstp.stprov_key_id as state,");
		strSQL.append(" brstp.stprov_nam as statenam,");			
		strSQL.append(" count(distinct cv.cust_key_id) as offices ");
		strSQL.append(" FROM ");
		strSQL.append(RMM_CUSTOMER_MONTH_FACT + " cmf,");
		strSQL.append(RMM_CUSTOMER_VERSION + " cv,");
		strSQL.append(RMM_STATE + " brstp,");
		strSQL.append(RMM_STATE + " custstp ");
		strSQL.append(" WHERE   cmf.mth_key_id = ?");
		strSQL.append(" AND 	cmf.cust_key_id = cv.cust_key_id");
		strSQL.append(" AND 	cmf.clos_br_stprov_id = brstp.stprov_key_id");
		strSQL.append(" AND 	cv.stprov_key_id = custstp.stprov_key_id");
		strSQL.append(" AND 	cv.curr_vrsn_ind = 'Y'");
		strSQL.append(" AND cmf.cust_key_id IN (" + custKeyID + ")");
		strSQL.append(" GROUP BY 1, 2 ORDER BY 2");
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psStateCount = DBManager.getPreparedStatement(strSQL.toString(), conn);
		psStateCount.setInt(1, monthID);
		rsStateTotal = psStateCount.executeQuery();
		
		int iCtr = 0;
		while (rsStateTotal.next())
		{
			// System.out.println("State " + rsStateTotal.getInt("state") + " has " + rsStateTotal.getInt("offices") + "\n");
			ArrayList objTempCount = new ArrayList();
			objTempCount.add(new Integer(rsStateTotal.getInt("state")));
			objTempCount.add(new Integer(rsStateTotal.getInt("offices")));
			objCount.add(iCtr, objTempCount);
			iCtr++;
			objTempCount = null;
			
		}

	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psStateCount);
		DBManager.closeConnection(conn);
	}
	return objCount;
}
/** * get StateSummary Total - Summary Totals for a office
 * Creation date: (6/11/01 4:37:10 PM)
 * @return Summary
 * @param int CustKeyId
 * @param int monthID 
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static String getOfficeSummaryData(int stateID, int monthID, Collection offices) throws DataRetrievalException
{

	Connection conn = null;
	PreparedStatement psSummary = null;
	String xml = "";
	String custKeyID = "";
	
	try
	{
		//  verify we have input parameters
		if (stateID == 0 || monthID == 0 || offices.size() == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		//  build string with all of the offices
		//
		for (Iterator itrList = offices.iterator(); itrList.hasNext();)
		{
			ClaimOffice objTemp = (ClaimOffice) itrList.next();
			int intKeyId = objTemp.getCustKeyID();
			if (custKeyID.equals(""))
			{
				custKeyID = "" + intKeyId + "";
			}
			else
			{
				custKeyID = custKeyID + "," + intKeyId;
			}
		}

		//  build sql strings to retrieve the state total
		StringBuffer strSQL = new StringBuffer(" SELECT ");
		strSQL.append(" cv.cust_key_id as custKeyID,");
		strSQL.append("cv.cust_nam as custName,");
		strSQL.append("sum(cmf.rm_ra_tot_qty) as arms,");
		strSQL.append("sum(cmf.ra_tot_qty - cmf.rm_ra_tot_qty) as nonArms,");
		strSQL.append("sum(cmf.ra_tot_qty) as tInvoices,");
		strSQL.append("round(NVL(sum(cmf.auth_rate_tot_amt)/DECODE(sum(cmf.auth_rm_ra_tot_qty),0,NULL,sum(cmf.auth_rm_ra_tot_qty)),0)," + FORMAT_PRECISION + ") as avgRateArms,");
		strSQL.append("round(sum(cmf.time_incm_tot_amt)/sum(cmf.ra_day_tot_qty)," + FORMAT_PRECISION + ") as avgRPRDay,");
		strSQL.append("round(sum(bill_to_tot_amt)/sum(ra_bill_day_qty)," + FORMAT_PRECISION + ") as avgBAPBDay,");
		strSQL.append("round(sum(cmf.ra_bill_day_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgBDays,");
		strSQL.append("round(sum(cmf.ra_ext_tot_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgNoExtns,");
		strSQL.append("round(sum(cmf.bill_to_tot_amt)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgTBA,");
		strSQL.append("round(sum(cmf.bill_to_tot_amt)," + FORMAT_PRECISION + ") as tBAmt");
		strSQL.append("	FROM  " + RMM_CUSTOMER_MONTH_FACT + " cmf,");
		strSQL.append(RMM_CUSTOMER_VERSION + " cv,");
		strSQL.append(RMM_STATE + " brstp,");
		strSQL.append(RMM_STATE + " custstp ");
		strSQL.append(" WHERE cmf.mth_key_id = ?");
		strSQL.append(" AND brstp.stprov_key_id = ?");
		strSQL.append(" AND cmf.cust_key_id = cv.cust_key_id");
		strSQL.append(" AND cmf.clos_br_stprov_id = brstp.stprov_key_id");
		strSQL.append(" AND cv.stprov_key_id = custstp.stprov_key_id");
		strSQL.append(" AND cv.curr_vrsn_ind = 'Y'");
		strSQL.append(" AND cmf.cust_key_id IN (" + custKeyID + ")");
		strSQL.append(" GROUP BY 1,2 ");
		strSQL.append(" ORDER BY 2");		
		
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL.toString(), conn);
		psSummary.setInt(1, monthID);
		psSummary.setInt(2, stateID);
		ResultSet rsSummary = psSummary.executeQuery();
		xml = XmlFactory.getReportDataXml(rsSummary);
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return xml;
}
/** * get the totals for State - Summary Totals for every office in the state
 * Creation date: (6/11/01 4:37:10 PM)
 * @return Summary
 * @param int CustKeyId
 * @param int monthID 
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static String getOfficeSummaryTotal(int stateID, int monthID, Collection offices) throws DataRetrievalException
{

	Connection conn = null;
	PreparedStatement psSummary = null;
	String xml = "";
	String custKeyID = "";
	
	try
	{
		//  verify we have input parameters
		if (stateID == 0 || monthID == 0 || offices.size() == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		//  build string with all of the offices
		//
		for (Iterator itrList = offices.iterator(); itrList.hasNext();)
		{
			ClaimOffice objTemp = (ClaimOffice) itrList.next();
			int intKeyId = objTemp.getCustKeyID();
			if (custKeyID.equals(""))
			{
				custKeyID = "" + intKeyId + "";
			}
			else
			{
				custKeyID = custKeyID + "," + intKeyId;
			}
		}

		//  build sql strings to retrieve the state total
		StringBuffer strSQL = new StringBuffer(" SELECT ");
		strSQL.append("brstp.stprov_key_id as stateID,");			
		strSQL.append("brstp.stprov_nam as state,");		
		strSQL.append("sum(cmf.rm_ra_tot_qty) as arms,");
		strSQL.append("sum(cmf.ra_tot_qty - cmf.rm_ra_tot_qty) as nonArms,");
		strSQL.append("sum(cmf.ra_tot_qty) as tInvoices,");
		strSQL.append("round(NVL(sum(cmf.auth_rate_tot_amt)/DECODE(sum(cmf.auth_rm_ra_tot_qty),0,NULL,sum(cmf.auth_rm_ra_tot_qty)),0)," + FORMAT_PRECISION + ") as avgRateArms,");
		strSQL.append("round(sum(cmf.time_incm_tot_amt)/sum(cmf.ra_day_tot_qty)," + FORMAT_PRECISION + ") as avgRPRDay,");
		strSQL.append("round(sum(bill_to_tot_amt)/sum(ra_bill_day_qty)," + FORMAT_PRECISION + ") as avgBAPBDay,");
		strSQL.append("round(sum(cmf.ra_bill_day_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgBDays,");
		strSQL.append("round(sum(cmf.ra_ext_tot_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgNoExtns,");
		strSQL.append("round(sum(cmf.bill_to_tot_amt)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgTBA,");
		strSQL.append("round(sum(cmf.bill_to_tot_amt)," + FORMAT_PRECISION + ") as tBAmt");
		strSQL.append("	FROM  " + RMM_CUSTOMER_MONTH_FACT + " cmf,");
		strSQL.append(RMM_CUSTOMER_VERSION + " cv,");
		strSQL.append(RMM_STATE + " brstp,");
		strSQL.append(RMM_STATE + " custstp ");
		strSQL.append(" WHERE cmf.mth_key_id = ?");
		strSQL.append(" AND brstp.stprov_key_id = ?");
		strSQL.append(" AND cmf.cust_key_id = cv.cust_key_id");
		strSQL.append(" AND cmf.clos_br_stprov_id = brstp.stprov_key_id");
		strSQL.append(" AND cv.stprov_key_id = custstp.stprov_key_id");
		strSQL.append(" AND cv.curr_vrsn_ind = 'Y'");
		strSQL.append(" AND cmf.cust_key_id IN (" + custKeyID + ")");
		strSQL.append(" GROUP BY 1,2 ");
		strSQL.append(" ORDER BY 1");		
		
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL.toString(), conn);
		psSummary.setInt(1, monthID);
		psSummary.setInt(2, stateID);
		ResultSet rsSummary = psSummary.executeQuery();
		xml = XmlFactory.getReportDataXml(rsSummary);
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return xml;
}
/**
 * Common method for getting the required query for report
 * Creation date: (7/12/01 10:23:11 AM)
 * @return java.lang.String
 * @param reportName java.lang.String
 * @param custKeyID java.lang.String
 * if  State or Detail report Pass empty /"" string as the 2 param
 */
private static String getQuery(String reportName, String custKeyID)
{
	StringBuffer strSQL = new StringBuffer("SELECT ");
	if (reportName.equalsIgnoreCase(OFFICE_SUMMARY_REPORT))
	{
		strSQL.append("cv.cust_key_id as custKeyID,");
		strSQL.append("cv.cust_nam as custName,");
		strSQL.append("sum(cmf.rm_ra_tot_qty)as arms,");
		strSQL.append("sum(cmf.ra_tot_qty - cmf.rm_ra_tot_qty)as nonArms,");
		strSQL.append("sum(cmf.ra_tot_qty) as tInvoices,");
		strSQL.append("round(NVL(sum(cmf.auth_rate_tot_amt)/DECODE(sum(cmf.auth_rm_ra_tot_qty),0,NULL,sum(cmf.auth_rm_ra_tot_qty)),0) ," + FORMAT_PRECISION + ") as avgRateArms,");
		strSQL.append("round(sum(cmf.time_incm_tot_amt)/sum(cmf.ra_day_tot_qty) ," + FORMAT_PRECISION + ")as avgRPRDay,");
		strSQL.append("round(sum(bill_to_tot_amt)/sum(ra_bill_day_qty)," + FORMAT_PRECISION + ") as avgBAPBDay,");
		strSQL.append("round(sum(cmf.ra_bill_day_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgBDays,");
		strSQL.append("round(sum(cmf.ra_ext_tot_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgNoExtns,");
		strSQL.append("round(sum(cmf.bill_to_tot_amt)/sum(cmf.ra_tot_qty) ," + FORMAT_PRECISION + ")as avgTBA,");
		strSQL.append("round(sum(cmf.bill_to_tot_amt)," + FORMAT_PRECISION + ") as tBAmt");
		strSQL.append(" FROM ");
		strSQL.append(RMM_CUSTOMER_VERSION + " cv, ");
		strSQL.append("OUTER " + RMM_CUSTOMER_MONTH_FACT + " cmf ");
		strSQL.append(" WHERE cmf.mth_key_id = ?");
		strSQL.append(" AND cmf.cust_key_id = cv.cust_key_id ");
		strSQL.append(" AND cv.curr_vrsn_ind = 'Y' ");
		strSQL.append(" AND cv.cust_key_id IN (" + custKeyID + ") ");
		strSQL.append(" GROUP BY 1,2 ");
		strSQL.append(" ORDER BY 2 ");
	}
	else
		if (reportName.equalsIgnoreCase(STATE_SUMMARY_REPORT))
		{
			strSQL.append(" cv.cust_key_id as custKeyID,");
			strSQL.append("cv.cust_nam as custName,");
			strSQL.append("brstp.stprov_nam as state,");
			strSQL.append("sum(cmf.rm_ra_tot_qty)as arms,");
			strSQL.append("sum(cmf.ra_tot_qty - cmf.rm_ra_tot_qty) as nonArms,");
			strSQL.append("sum(cmf.ra_tot_qty) as tInvoices,");
			strSQL.append("round(NVL(sum(cmf.auth_rate_tot_amt)/DECODE(sum(cmf.auth_rm_ra_tot_qty),0,NULL,sum(cmf.auth_rm_ra_tot_qty)),0)," + FORMAT_PRECISION + ") as avgRateArms,");
			strSQL.append("round(sum(cmf.time_incm_tot_amt)/sum(cmf.ra_day_tot_qty)," + FORMAT_PRECISION + ") as avgRPRDay,");
			strSQL.append("round(sum(bill_to_tot_amt)/sum(ra_bill_day_qty)," + FORMAT_PRECISION + ") as avgBAPBDay,");
			strSQL.append("round(sum(cmf.ra_bill_day_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgBDays,");
			strSQL.append("round(sum(cmf.ra_ext_tot_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgNoExtns,");
			strSQL.append("round(sum(cmf.bill_to_tot_amt)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgTBA,");
			strSQL.append("round(sum(cmf.bill_to_tot_amt)," + FORMAT_PRECISION + ") as tBAmt");
			strSQL.append("	FROM  ");
			strSQL.append(RMM_CUSTOMER_MONTH_FACT + " cmf ,");
			strSQL.append(RMM_CUSTOMER_VERSION + " cv,");
			strSQL.append(RMM_STATE + " brstp,");
			strSQL.append(RMM_STATE + " custstp ");
			strSQL.append(" WHERE cmf.mth_key_id = ?");
			strSQL.append(" AND cmf.cust_key_id = cv.cust_key_id");
			strSQL.append(" AND cmf.clos_br_stprov_id = brstp.stprov_key_id");
			strSQL.append(" AND cv.stprov_key_id = custstp.stprov_key_id");
			strSQL.append(" AND cv.curr_vrsn_ind = 'Y'");
			strSQL.append(" AND cv.cust_key_id = ?");
			strSQL.append(" GROUP BY 1,2,3 ");
			strSQL.append(" ORDER BY 3");
		}
		else
		{
			strSQL.append("stp.stprov_cde as state,");
			strSQL.append("dci.clm_ref_nbr as claimNbr,");
			strSQL.append("dci.ra_arms_ind as arms,");
			strSQL.append("dci.adjr_nam as adjustor,");
			strSQL.append("dci.rntr_nam as renter,");
			strSQL.append("dci.ins_nam as insured,");
			strSQL.append("dci.shop_nam as repairshop,");
			strSQL.append("dci.rm_auth_rate_amt as authrate,");
			strSQL.append("dci.avg_time_incm_amt as dlyRentalRate,");
			strSQL.append("dci.avg_b2_incm_amt as dlyBilledRate,");
			strSQL.append("dci.bill_prd_day_qty as bDays,");
			strSQL.append("dci.auth_ext_qty as noExtns,");
			strSQL.append("dci.b2_pymt_amt as tBAmt");
			strSQL.append(" FROM ");
			strSQL.append(RMM_CLAIM_INFO + " dci,");
			strSQL.append(RMM_STATE + " stp ");
			strSQL.append(" WHERE dci.clos_br_stprov_id = stp.stprov_key_id ");
			strSQL.append(" AND dci.cust_key_id = ? ");
			strSQL.append(" AND dci.clos_mth_key_id = ? ");
			strSQL.append(" ORDER BY 1,2");
		}
	return strSQL.toString();
}
/**
 * Common method for getting the required query for report
 * Creation date: (7/12/01 10:23:11 AM)
 * @return java.lang.String
 * @param reportName java.lang.String
 * @param custKeyID java.lang.String
 * if  State or Detail report Pass empty /"" string as the 2 param
 */
private static String getQuery(String reportName, String custKeyID, String sortColName, boolean sortOrder)
{
	String strSortOrder = " asc";
	if (!sortOrder)
	{
		strSortOrder = " desc";
	}
	StringBuffer strSQL = new StringBuffer("SELECT ");
	if (reportName.equalsIgnoreCase(OFFICE_SUMMARY_REPORT))
	{
		if (sortColName.length() == 0)
		{
			sortColName = DEF_HO_SORTCOLNAME;
		}
		strSQL.append("cv.cust_key_id as custKeyID,");
		strSQL.append("cv.cust_nam as custName,");
		strSQL.append("sum(cmf.rm_ra_tot_qty)as arms,");
		strSQL.append("sum(cmf.ra_tot_qty - cmf.rm_ra_tot_qty)as nonArms,");
		strSQL.append("sum(cmf.ra_tot_qty) as tInvoices,");
		strSQL.append("round(NVL(sum(cmf.auth_rate_tot_amt)/DECODE(sum(cmf.auth_rm_ra_tot_qty),0,NULL,sum(cmf.auth_rm_ra_tot_qty)),0) ," + FORMAT_PRECISION + ") as avgRateArms,");
		strSQL.append("round(sum(cmf.time_incm_tot_amt)/sum(cmf.ra_day_tot_qty) ," + FORMAT_PRECISION + ")as avgRPRDay,");
		strSQL.append("round(sum(bill_to_tot_amt)/sum(ra_bill_day_qty)," + FORMAT_PRECISION + ") as avgBAPBDay,");
		strSQL.append("round(sum(cmf.ra_bill_day_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgBDays,");
		strSQL.append("round(sum(cmf.ra_ext_tot_qty)/sum(cmf.ra_tot_qty)," + FORMAT_PRECISION + ") as avgNoExtns,");
		strSQL.append("round(sum(cmf.bill_to_tot_amt)/sum(cmf.ra_tot_qty) ," + FORMAT_PRECISION + ")as avgTBA,");
		strSQL.append("round(sum(cmf.bill_to_tot_amt)," + FORMAT_PRECISION + ") as tBAmt");
		strSQL.append(" FROM ");
		strSQL.append(RMM_CUSTOMER_VERSION + " cv, ");
		strSQL.append("OUTER " + RMM_CUSTOMER_MONTH_FACT + " cmf ");
		strSQL.append(" WHERE cmf.mth_key_id = ?");
		strSQL.append(" AND cmf.cust_key_id = cv.cust_key_id ");
		strSQL.append(" AND cv.curr_vrsn_ind = 'Y' ");
		strSQL.append(" AND cv.cust_key_id IN (" + custKeyID + ") ");
		strSQL.append(" GROUP BY 1,2 ");
		strSQL.append(" ORDER BY " + sortColName + " " + strSortOrder);
	}
	else
		if (reportName.equalsIgnoreCase(STATE_SUMMARY_REPORT))
		{
			if (sortColName.length() == 0)
			{
				sortColName = DEF_SS_SORTCOLNAME;
			}
			strSQL.append(" cv.cust_key_id as custKeyID, ");
			strSQL.append(" cv.cust_nam as custName, ");
			strSQL.append(" brstp.stprov_nam as state, ");
			strSQL.append(" sum(cmf.rm_ra_tot_qty) as arms, ");
			strSQL.append(" sum(cmf.ra_tot_qty - cmf.rm_ra_tot_qty) as nonArms, ");
			strSQL.append(" sum(cmf.ra_tot_qty) as tInvoices, ");
			strSQL.append(" round(NVL(sum(cmf.auth_rate_tot_amt) / DECODE(sum(cmf.auth_rm_ra_tot_qty), 0, NULL, sum(cmf.auth_rm_ra_tot_qty)), 0), " + FORMAT_PRECISION + ") as avgRateArms, ");
			strSQL.append(" round(sum(cmf.time_incm_tot_amt) / sum(cmf.ra_day_tot_qty), " + FORMAT_PRECISION + ") as avgRPRDay, ");
			strSQL.append(" round(sum(bill_to_tot_amt) / sum(ra_bill_day_qty), " + FORMAT_PRECISION + ") as avgBAPBDay, ");
			strSQL.append(" round(sum(cmf.ra_bill_day_qty) / sum(cmf.ra_tot_qty), " + FORMAT_PRECISION + ") as avgBDays, ");
			strSQL.append(" round(sum(cmf.ra_ext_tot_qty) / sum(cmf.ra_tot_qty), " + FORMAT_PRECISION + ") as avgNoExtns, ");
			strSQL.append(" round(sum(cmf.bill_to_tot_amt) / sum(cmf.ra_tot_qty), " + FORMAT_PRECISION + ") as avgTBA, ");
			strSQL.append(" round(sum(cmf.bill_to_tot_amt), " + FORMAT_PRECISION + ") as tBAmt ");
			strSQL.append(" FROM ");
			strSQL.append(RMM_CUSTOMER_MONTH_FACT + " cmf, ");
			strSQL.append(RMM_CUSTOMER_VERSION + " cv, ");
			strSQL.append(RMM_STATE + " brstp, ");
			strSQL.append(RMM_STATE + " custstp ");
			strSQL.append(" WHERE cmf.mth_key_id = ? ");
			strSQL.append(" AND cmf.cust_key_id = cv.cust_key_id ");
			strSQL.append(" AND cmf.clos_br_stprov_id = brstp.stprov_key_id ");
			strSQL.append(" AND cv.stprov_key_id = custstp.stprov_key_id ");
			strSQL.append(" AND cv.curr_vrsn_ind = 'Y' ");
			strSQL.append(" AND cv.cust_key_id = ? ");
			strSQL.append(" GROUP BY 1, 2, 3 ");
			strSQL.append(" ORDER BY " + sortColName + " " + strSortOrder);
		}
		else
		{
			if (sortColName.length() == 0)
			{
				sortColName = DEF_DET_SORTCOLNAME;
			}
			else
			{
				sortColName = sortColName.trim();
				// Secondary Sorting. Except for sort by claimnumber ,append claimnumber for secondary 
				if (sortColName.equalsIgnoreCase("claimnbr") == false)
				{
					sortColName = sortColName + ",claimnbr";
				}
			}
			strSQL.append(" stp.stprov_cde as state, ");
			strSQL.append(" dci.clm_ref_nbr as claimnbr, ");
			strSQL.append(" dci.ra_arms_ind as arms, ");
			strSQL.append(" dci.adjr_nam as adjustor, ");
			strSQL.append(" dci.rntr_nam as renter, ");
			strSQL.append(" dci.ins_nam as insured, ");
			strSQL.append(" dci.shop_nam as repairshop, ");
			strSQL.append(" dci.rm_auth_rate_amt as authrate, ");
			strSQL.append(" dci.avg_time_incm_amt as dlyRentalRate, ");
			strSQL.append(" dci.avg_b2_incm_amt as dlyBilledRate, ");
			strSQL.append(" dci.bill_prd_day_qty as bDays, ");
			strSQL.append(" dci.auth_ext_qty as noExtns, ");
			strSQL.append(" dci.b2_pymt_amt as tBAmt ");
			strSQL.append(" FROM ");
			strSQL.append(RMM_CLAIM_INFO + " dci, ");
			strSQL.append(RMM_STATE + " stp ");
			strSQL.append(" WHERE dci.clos_br_stprov_id = stp.stprov_key_id ");
			strSQL.append(" AND dci.cust_key_id = ? ");
			strSQL.append(" AND dci.clos_mth_key_id = ? ");
			strSQL.append(" ORDER BY " + sortColName + " " + strSortOrder);
		}
	return strSQL.toString();
}
/** * SummaryReport - returns rows in a collection for download report
 * Creation date: (6/11/01 4:37:10 PM)
 * @return java.util.Collection
 * @param int OfficeID
 * @param int monthID
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static Collection getSSResult(int custKeyID, int monthID) throws DataRetrievalException
{
	PreparedStatement psSummary = null;
	Connection conn = null;
	Collection summaryList = new ArrayList();
	try
	{
		if (custKeyID == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String strSQL = getQuery(STATE_SUMMARY_REPORT, "");
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL, conn);
		psSummary.setInt(1, monthID);
		psSummary.setInt(2, custKeyID);
		ResultSet rsSummary = psSummary.executeQuery();
		while (rsSummary.next())
		{
			Summary objSummary = new Summary();
			objSummary.setCustomerKeyID(rsSummary.getInt("custKeyID"));
			objSummary.setCustomerName(rsSummary.getString("custName"));
			objSummary.setRentalState(rsSummary.getString("state"));
			objSummary.setArmsInvoices(format(rsSummary.getObject("arms"), 0));
			objSummary.setNonArmsInvoices(format(rsSummary.getObject("nonArms"), 0));
			objSummary.setTotalInvoices(format(rsSummary.getObject("tInvoices"), 0));
			objSummary.setAvgRateArms(format(rsSummary.getObject("avgRateArms"), FORMAT_PRECISION));
			objSummary.setAvgRatePerRentalDay(format(rsSummary.getObject("avgRPRDay"), FORMAT_PRECISION));
			objSummary.setAvgBilledAmtPerBilledDay(format(rsSummary.getObject("avgBAPBDay"), FORMAT_PRECISION));
			objSummary.setAvgBilledDays(format(rsSummary.getObject("avgBDays"), FORMAT_PRECISION));
			objSummary.setAvgNoExtns(format(rsSummary.getObject("avgNoExtns"), FORMAT_PRECISION));
			objSummary.setAvgTotalBilledAmt(format(rsSummary.getObject("avgTBA"), FORMAT_PRECISION));
			objSummary.setTotalBilledAmt(format(rsSummary.getObject("tBAmt"), FORMAT_PRECISION));
			summaryList.add(objSummary);
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed" + eConn.getMessage());
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return summaryList;
}
/** * to get the State Count for each claimOfficeID- returns rows in a ArrayList
 * Creation date: (6/11/01 4:37:10 PM)
 * @return ArrayList
 * @param OfficeList java.util.Collection
 * @param int monthID
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static ArrayList getStateCount(Collection allOfficeList, int monthID) throws DataRetrievalException
{
	ArrayList objCount = new ArrayList();
	ArrayList objStateCount = new ArrayList();
	Connection conn = null;
	PreparedStatement psStateCount = null;
	ResultSet rsStateTotal = null;
	String custKeyID = "";
	try
	{
		if (allOfficeList.size() == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		int iCtr = 0;
		for (Iterator itrList = allOfficeList.iterator(); itrList.hasNext();)
		{
			ClaimOffice objTemp = (ClaimOffice) itrList.next();
			int intKeyId = objTemp.getCustKeyID();
			if (custKeyID.equals(""))
			{
				custKeyID = "" + intKeyId + "";
			}
			else
			{
				custKeyID = custKeyID + "," + intKeyId;
			}
			ArrayList objTempCount = new ArrayList();
			objTempCount.add(new Integer(intKeyId));
			objTempCount.add(new Integer(0));
			objStateCount.add(iCtr, objTempCount);
			iCtr++;
		}
		StringBuffer strSQL = new StringBuffer(" SELECT ");
		strSQL.append(" cmf.cust_key_id  as custKeyID,");
		strSQL.append(" count( distinct cmf.clos_br_stprov_id) as totalstate ");
		strSQL.append(" FROM ");
		strSQL.append(RMM_CUSTOMER_MONTH_FACT + " cmf ");
		strSQL.append(" WHERE cmf.mth_key_id = ?");
		strSQL.append(" AND cmf.cust_key_id IN (" + custKeyID + ")");
		strSQL.append(" GROUP BY 1");
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psStateCount = DBManager.getPreparedStatement(strSQL.toString(), conn);
		psStateCount.setInt(1, monthID);
		rsStateTotal = psStateCount.executeQuery();
		while (rsStateTotal.next())
		{
			int intCustKeyID = rsStateTotal.getInt("custKeyID");
			ArrayList objTempCount = new ArrayList();
			objTempCount.add(new Integer(intCustKeyID));
			objTempCount.add(new Integer(0));
			iCtr = objStateCount.indexOf(objTempCount);
			ArrayList objTemp = new ArrayList();
			objTemp.add(new Integer(intCustKeyID));
			objTemp.add(new Integer(rsStateTotal.getInt("totalstate")));
			objStateCount.set(iCtr, objTemp);
			objTempCount = null;
			objTemp = null;
		}
		for (Iterator itr = objStateCount.iterator(); itr.hasNext();)
		{
			ArrayList objTemp = (ArrayList) itr.next();
			objCount.add(objTemp.get(1));
		}
		objStateCount = null;
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psStateCount);
		DBManager.closeConnection(conn);
	}
	return objCount;
}
/** * GetStateSummaryData - StateSummary report Data
 * Creation date: (6/11/01 4:37:10 PM)
 * @return String
 * @param int CustKeyId
 * @param int monthID
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static String getStateSummaryData(int custKeyID, int monthID) throws DataRetrievalException
{
	PreparedStatement psSummary = null;
	Connection conn = null;
	String xml = "";
	try
	{
		if (custKeyID == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String strSQL = getQuery(STATE_SUMMARY_REPORT, "");
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL, conn);
		psSummary.setInt(1, monthID);
		psSummary.setInt(2, custKeyID);
		ResultSet rsSummary = psSummary.executeQuery();
		xml = XmlFactory.getReportDataXml(rsSummary);
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return xml;
}
/** * GetStateSummaryData - StateSummary report Data
 * Creation date: (6/11/01 4:37:10 PM)
 * @return String
 * @param int CustKeyId
 * @param int monthID
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static String getStateSummaryData(int custKeyID, int monthID, String sortColName, boolean sortOrder) throws DataRetrievalException
{
	PreparedStatement psSummary = null;
	Connection conn = null;
	String xml = "";
	try
	{
		if (custKeyID == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String strSQL = getQuery(STATE_SUMMARY_REPORT, "", sortColName, sortOrder);
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL, conn);
		psSummary.setInt(1, monthID);
		psSummary.setInt(2, custKeyID);
		ResultSet rsSummary = psSummary.executeQuery();
		xml = XmlFactory.getReportDataXml(rsSummary);
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return xml;
}
/** * get StateSummary Total - Summary Totals for a office
 * @Creation Date 08/31/2001
 * @return ArrayList
 * @param Collection CustKeyId
 * @param int monthID 
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static Collection getStateSummarySortedClaimOffices(Collection allofficeList, int monthID, String sortColumn, boolean sortOrder) throws DataRetrievalException
{
	Collection claimOffices = new ArrayList();
	Connection conn = null;
	PreparedStatement psSummary = null;
	String strSortOrder = " asc";
	if (!sortOrder)
	{
		strSortOrder = " desc";
	}
	if (sortColumn.equals(""))
	{
		sortColumn = " custKeyID ";
	}
	try
	{
		if (allofficeList.size() == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		String custKeyID = getCustKeyString(allofficeList);
		StringBuffer strSQL = new StringBuffer(" SELECT ");
		strSQL.append("cv.cust_key_id as custKeyID,");
		strSQL.append("cv.cust_nam as custName,");
		strSQL.append("sum(cmf.rm_ra_tot_qty) as arms,");
		strSQL.append("sum(cmf.ra_tot_qty - cmf.rm_ra_tot_qty) as nonArms,");
		strSQL.append("sum(cmf.ra_tot_qty) as tInvoices,");
		strSQL.append("NVL(sum(cmf.auth_rate_tot_amt)/DECODE(sum(cmf.auth_rm_ra_tot_qty),0,NULL,sum(cmf.auth_rm_ra_tot_qty)),0) as avgRateArms,");
		strSQL.append("sum(cmf.time_incm_tot_amt)/sum(cmf.ra_day_tot_qty) as avgRPRDay,");
		strSQL.append("sum(bill_to_tot_amt)/sum(ra_bill_day_qty) as avgBAPBDay,");
		strSQL.append("sum(cmf.ra_bill_day_qty)/sum(cmf.ra_tot_qty) as avgBDays,");
		strSQL.append("sum(cmf.ra_ext_tot_qty)/sum(cmf.ra_tot_qty) as avgNoExtns,");
		strSQL.append("sum(cmf.bill_to_tot_amt)/sum(cmf.ra_tot_qty) as avgTBA,");
		strSQL.append("sum(cmf.bill_to_tot_amt) as tBAmt");
		strSQL.append("	FROM  " + RMM_CUSTOMER_MONTH_FACT + " cmf,");
		strSQL.append(RMM_CUSTOMER_VERSION + " cv,");
		strSQL.append(RMM_STATE + " brstp,");
		strSQL.append(RMM_STATE + " custstp ");
		strSQL.append(" WHERE cmf.mth_key_id = ?");
		strSQL.append(" AND cmf.cust_key_id = cv.cust_key_id");
		strSQL.append(" AND cmf.clos_br_stprov_id = brstp.stprov_key_id");
		strSQL.append(" AND cv.stprov_key_id = custstp.stprov_key_id");
		strSQL.append(" AND cv.curr_vrsn_ind = 'Y'");
		strSQL.append(" AND cv.cust_key_id in ( " + custKeyID + ")");
		strSQL.append(" GROUP BY 1,2");
		strSQL.append(" ORDER BY " + sortColumn + strSortOrder);
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL.toString(), conn);
		psSummary.setInt(1, monthID);
		ResultSet rsSummary = psSummary.executeQuery();
		while (rsSummary.next())
		{
			ClaimOffice objClaim = new ClaimOffice();
			objClaim.setCustKeyID(rsSummary.getInt("custKeyID")); //like 18456565 & not GEI12121 CO Code
			objClaim.setClaimOfficeName(rsSummary.getString("custName"));
			claimOffices.add(objClaim);
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return claimOffices;
}
/** * get StateSummary Total - Summary Totals for a office
 * Creation date: (6/11/01 4:37:10 PM)
 * @return Summary
 * @param int CustKeyId
 * @param int monthID 
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public static Summary getStateSummaryTotal(int custKeyID, int monthID) throws DataRetrievalException
{
	Summary objSummary = new Summary();
	Connection conn = null;
	PreparedStatement psSummary = null;
	try
	{
		if (custKeyID == 0 || monthID == 0)
		{
			throw new DataRetrievalException("could not retrieve data. Office not available or MonthID not available");
		}
		StringBuffer strSQL = new StringBuffer(" SELECT ");
		strSQL.append("sum(cmf.rm_ra_tot_qty) as arms,");
		strSQL.append("sum(cmf.ra_tot_qty - cmf.rm_ra_tot_qty) as nonArms,");
		strSQL.append("sum(cmf.ra_tot_qty) as tInvoices,");
		strSQL.append("NVL(sum(cmf.auth_rate_tot_amt)/DECODE(sum(cmf.auth_rm_ra_tot_qty),0,NULL,sum(cmf.auth_rm_ra_tot_qty)),0) as avgRateArms,");
		strSQL.append("sum(cmf.time_incm_tot_amt)/sum(cmf.ra_day_tot_qty) as avgRPRDay,");
		strSQL.append("sum(bill_to_tot_amt)/sum(ra_bill_day_qty) as avgBAPBDay,");
		strSQL.append("sum(cmf.ra_bill_day_qty)/sum(cmf.ra_tot_qty) as avgBDays,");
		strSQL.append("sum(cmf.ra_ext_tot_qty)/sum(cmf.ra_tot_qty) as avgNoExtns,");
		strSQL.append("sum(cmf.bill_to_tot_amt)/sum(cmf.ra_tot_qty) as avgTBA,");
		strSQL.append("sum(cmf.bill_to_tot_amt) as tBAmt");
		strSQL.append("	FROM  " + RMM_CUSTOMER_MONTH_FACT + " cmf,");
		strSQL.append(RMM_CUSTOMER_VERSION + " cv,");
		strSQL.append(RMM_STATE + " brstp,");
		strSQL.append(RMM_STATE + " custstp ");
		strSQL.append(" WHERE cmf.mth_key_id = ?");
		strSQL.append(" AND cmf.cust_key_id = cv.cust_key_id");
		strSQL.append(" AND cmf.clos_br_stprov_id = brstp.stprov_key_id");
		strSQL.append(" AND cv.stprov_key_id = custstp.stprov_key_id");
		strSQL.append(" AND cv.curr_vrsn_ind = 'Y'");
		strSQL.append(" AND cv.cust_key_id = ? ");
		conn = DBManager.openConnection(DATAMART_DATABASE);
		psSummary = DBManager.getPreparedStatement(strSQL.toString(), conn);
		psSummary.setInt(1, monthID);
		psSummary.setInt(2, custKeyID);
		ResultSet rsSummary = psSummary.executeQuery();
		while (rsSummary.next())
		{
			objSummary.setArmsInvoices(format(rsSummary.getObject("arms"), 0));
			objSummary.setNonArmsInvoices(format(rsSummary.getObject("nonArms"), 0));
			objSummary.setTotalInvoices(format(rsSummary.getObject("tInvoices"), 0));
			objSummary.setAvgRateArms(format(rsSummary.getObject("avgRateArms"), FORMAT_PRECISION));
			objSummary.setAvgRatePerRentalDay(format(rsSummary.getObject("avgRPRDay"), FORMAT_PRECISION));
			objSummary.setAvgBilledAmtPerBilledDay(format(rsSummary.getObject("avgBAPBDay"), FORMAT_PRECISION));
			objSummary.setAvgBilledDays(format(rsSummary.getObject("avgBDays"), FORMAT_PRECISION));
			objSummary.setAvgNoExtns(format(rsSummary.getObject("avgNoExtns"), FORMAT_PRECISION));
			objSummary.setAvgTotalBilledAmt(format(rsSummary.getObject("avgTBA"), FORMAT_PRECISION));
			objSummary.setTotalBilledAmt(format(rsSummary.getObject("tBAmt"), FORMAT_PRECISION));
		}
	}
	catch (ConnectionFailedException eConn)
	{
		throw new DataRetrievalException("connection Failed");
	}
	catch (SQLException e)
	{
		throw new DataRetrievalException("could not retrieve data Error" + e.getMessage());
	}
	finally
	{
		DBManager.closeStatement(conn, psSummary);
		DBManager.closeConnection(conn);
	}
	return objSummary;
}
/**
 * XML for Detail Report
 * Creation date: (6/11/01 4:36:43 PM)
 * @return String
 * @exception com.erac.datamart.reporting.DataRetrievalException  
 */
public String getXMLDetailData() throws DataRetrievalException
{
	String strXML = "";
	int intIndex = 0;
	if (xml.length() != 0)
	{ // if length of XML  is zero then don't process further
		if (preXML.equals("") || preXML.length() == 0)
		{
			intIndex = xml.indexOf("<ROW>");
			preXML = xml.substring(0, intIndex);
			xml = xml.substring(intIndex);
			intIndex = xml.lastIndexOf("</ROWSET>");
			postXML = xml.substring(intIndex);
			xml = xml.substring(0, intIndex).trim();
		}
		for (int intRowCount = 0; intRowCount <= this.requiredRows - 1; intRowCount++)
		{
			if (xml.length() != 0)
			{
				intIndex = xml.indexOf("</ROW>");
				intIndex = intIndex + 6;
				strXML += xml.substring(0, intIndex) + "\n";
				xml = xml.substring(intIndex).trim();
			}
			else
			{
				break; // if xml length = 0, break the loop
			}
		}
		strXML = preXML + strXML + postXML;
	}
	return strXML;
}
/**
 * Insert the method's description here.
 * Creation date: (7/18/01 10:27:10 AM)
 * @param newDetailCount int
 */
private void setDetailCount(int newDetailCount)
{
	this.detailCount = newDetailCount;
}
}
