<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.sql.*" %>
<%
// this code uses the id passed by the startTest page to retrieve
// the member data.
    Connection _con;
    Statement _stmt;
    ResultSet _rs;
    double UserAmt = 0.0;
    String results = "";
	Class.forName("com.sybase.jdbc2.jdbc.SybDriver");

	String sqlStr = "select distinct BILL.BLEI_CK, BILL.BLBL_BILLED_AMT, BILL.BLBL_RCVD_AMT," +
					"BILL.BLBL_BILLED_AMT - BILL.BLBL_RCVD_AMT as amtdue," +
					"max(BILL.BLBL_DUE_DT) curdt " +
					"from CMC_SBSB_SUBSC SBSB " +
					"right join CMC_BLEI_ENTY_INFO BLEI on BLEI.BLEI_BILL_LEVEL_CK = SBSB.SBSB_CK " + 
					"and BLEI.BLEI_BILL_LEVEL = 'I' " +
					"right join CMC_BLBL_BILL_SUMM BILL on BILL.BLEI_CK = BLEI.BLEI_CK " +
					"where SBSB_ID = '" + request.getParameter("userid") + "' " +
					"group by BILL.BLEI_CK, BLEI.BLEI_NET_DUE, BILL.BLBL_BILLED_AMT, BILL.BLBL_RCVD_AMT " +
					"order by curdt desc";
		
	try {
		_con = DriverManager.getConnection("jdbc:sybase:Tds:CPSTEST:12026/facets_test_01", "faoper", "test69");
		_stmt = _con.createStatement();
		_rs = _stmt.executeQuery(sqlStr);
		while (_rs.next()) {
			UserAmt = _rs.getDouble("amtdue");
			break;
		}
		_stmt.close();
		_con.close();
		results = "Successfully retrieved the row";
	} catch (Exception e) {
		results = e.toString();
	}
 %>
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>displayMember.jsp</TITLE>
</HEAD>
<BODY>
<P>Member <%=request.getParameter("userid")%> has <%=UserAmt%></P>
<hr>
<%=results%>
</BODY>
</HTML>
