<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
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
<h3 class="header-orange">Review Payment Details</h3><p align=right><a href="/secure/restricted/redirect.asp"><img src="/secure/images/exit-red.gif" border="0"></a></p>
<div class="unit-content">
<html:form action="/subConfirm.do">
<center>
	<TABLE class="table-results" border="0" width="60%">
		<TBODY>
			<TR>
				<TD>
					PAY FROM ACCOUNT*:
				</TD>
				<td>
					<html:text property="subscriber.fromaccount" readonly="true" />
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
			<tr><td><br></td></tr>
			<tr><td colspan=2>Submit Payment only once to avoid multiple payments.</td></tr>
			<tr>
				<td><INPUT type="submit" name="bntPay" value="Submit Payment"></td>
				<td><INPUT type="submit" name="bntCan" value="Cancel"></td>				
			</tr>
			<tr><td colspan=2>Please wait while your confirmation number is retrieved.</td></tr>		
			<tr><td colspan=2>*Your account information is not stored by BlueCross BlueShield of Tennessee.  Payment information is encrypted and sent to a clearing house for processing.</td></tr>
		</TBODY>
	</TABLE>
	</center>
<p></p>
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