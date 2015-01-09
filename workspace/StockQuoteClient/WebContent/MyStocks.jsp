<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>MyStocks.jsp</TITLE>
</HEAD>
<BODY>
<H1>My Stock List</H1>
<jsp:useBean id="proxy" class="samples.StockQuoteProxy">
</jsp:useBean>
<TABLE border="1">
	<TBODY>
		<TR>
			<TH>Stock symbol</TH>
			<TH>Price</TH>
		</TR>
		<TR>
			<TD>CSCO</TD>
			<TD><%=String.valueOf(proxy.getQuote("CSCO"))%></TD>
		</TR>
		<TR>
			<TD>FNM</TD>
			<TD><%=String.valueOf(proxy.getQuote("FNM"))%></TD>
		</TR>		
		<TR>
			<TD>IBM</TD>
			<TD><%=String.valueOf(proxy.getQuote("IBM"))%></TD>
		</TR>
		<TR>
			<TD>INTC</TD>
			<TD><%=String.valueOf(proxy.getQuote("INTC"))%></TD>
		</TR>
	</TBODY>
</TABLE>
</BODY>
</HTML>
