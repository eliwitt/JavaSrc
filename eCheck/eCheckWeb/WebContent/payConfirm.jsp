<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>Bills</title>
	<link rel="stylesheet" href="/css/base.css" type="text/css"/>
	<link rel="stylesheet" href="/css/global.css" type="text/css"/>
	<link rel="stylesheet" href="/css/header.css" type="text/css"/>
	<link rel="stylesheet" href="/css/footer.css" type="text/css"/>
    <!--[if IE]>
    <link REL="stylesheet" HREF="/css/ie.css" type="text/css"/>
    <![endif]-->
	<script type="text/javascript" src="/js/common.js"></script>
	<script type="text/javascript" src="/js/bcbst.js"></script>
	<script type="text/javascript" src="/js/menu.js"></script>
	<script type="text/javascript" src="/js/ajax.js"></script>
	<script type="text/javascript">
	function init() {
		var ajaxObject = getXMLHttpObj();
		if(document.getElementById('inc_navigation')) {
			var navigation = getNavigation(ajaxObject);
			//alert(navigation);
			document.getElementById('inc_navigation').innerHTML=navigation;
		}
		if(document.getElementById('inc_footer')) {
			var footer = getFooter(ajaxObject);
			//alert(navigation);
			document.getElementById('inc_footer').innerHTML=footer;
		}
	}

	</script>
</head>
<body>
<div id="pageContent">
	<div class="inner">
		<div id="inc_navigation">
		</div>
		<div id="breadcrumb">
		</div>
		<div id="banner" class="utilities">
			<img src="/images/banners/blueaccess.gif" alt="Site Map" border="0"/>
		</div>
		<div id="printbanner"></div>
<!-- Begin Content Area -->
<div class="section-main"/>
<h3 class="header-orange">Payment Confirmation</h3><p align=right><a href="/secure/restricted/redirect.asp"><img src="/secure/images/exit-red.gif" border="0"></a></p>
<div class="unit-content">

<html:form action="/payconfirm.do">
<logic:equal value="AA" property="subscriber.bsrc" name="ProcessPayment">	
<center>
	<TABLE class="table-results" border="0" width="60%">
		<TBODY>
			<TR>
				<TD>
					TRANSACTION CONFIRMATION NUMBER:
				</TD>
				<td>
					<html:text property="subscriber.bscon" readonly="true" />
				</td>
			</tr>
			<tr>				
				<td>AMOUNT:</td>
				<TD><html:text property="subscriber.scramtdue"  readonly="true" ></html:text></TD>
			</tr>
			<TR>
				<TD>
					PAYMENT DATE:
				</TD>
				<td>
					<html:text property="subscriber.paydate" readonly="true" />
				</td>
			</TR>
		</TBODY>
	</TABLE>
</center>
<P>Thank you for your payment.  Please allow 24 hours for this transaction to be reflected 
on your account.  You may <a href="javascript:print()">print this page</a> for your records.</P>
</logic:equal>
<logic:notEqual value="AA" property="subscriber.bsrc" name="ProcessPayment">
<logic:equal value="28673" name="ProcessPayment" property="subscriber.bscon">
We were unable to process your payment.  You must go through the registration process first.
<INPUT type="submit" name="bntCan" value="Cancel">
</logic:equal>
<logic:notEqual value="28673" name="ProcessPayment" property="subscriber.bscon">
<p>We were unable to process your payment.  Please contact a Customer Service Representative at 1-800-725-6849.</p>
<!-- <bean:write name="ProcessPayment" property="subscriber.bsmsg" />&nbsp;<bean:write name="ProcessPayment" property="subscriber.bserror" /> -->
<INPUT type="submit" name="bntCan" value="Cancel">
</logic:notEqual>
</logic:notEqual>	
</html:form>
</div>
</div>
<!-- END Content Area -->
		<div id="inc_footer">
		</div>
		<script type="text/javascript">init()</script>
		<div id="btmPageContent">
		</div>
	</div>
              </div>
</body>
</html>