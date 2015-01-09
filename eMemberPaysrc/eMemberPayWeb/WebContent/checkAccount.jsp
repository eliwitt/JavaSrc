<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
	<title>Bills</title>
	<link rel="stylesheet" href="/css/base.css" type="text/css"/>
	<link rel="stylesheet" href="/css/global.css" type="text/css"/>
	<link rel="stylesheet" href="/css/header.css" type="text/css"/>
	<link rel="stylesheet" href="/css/footer.css" type="text/css"/>
	<link rel="stylesheet" href="css/eMemCss.css" type="text/css"/>
    <!--[if IE]>
    <link REL="stylesheet" HREF="/css/ie.css" type="text/css"/>
    <![endif]-->
	<script type="text/javascript" src="/js/common.js"></script>
	<script type="text/javascript" src="/js/bcbst.js"></script>
	<script type="text/javascript" src="/js/menu.js"></script>
	<script type="text/javascript" src="/js/ajax.js"></script>
	<script LANGUAGE="JavaScript">
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

	function jump2(element, content, next)
	{
		//
		//  Steve
		//
		//  you have to change the form name to match the one for 
		//  this new page.  By the way add the form to the page dummy!!!
		//
		switch(window.event.keyCode) {
			case 9:
				break
			case 16:
				break
			default:
			if (content.length==element.maxLength) {
				if (next<document.forms["RegPayment"].elements.length) {
					document.forms["RegPayment"].elements[next].focus()
				}		
			}
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
				<h3 class="header-orange">Checking Account</h3><p align=right><a href="/secure/restricted/redirect.asp"><img src="/secure/images/exit-red.gif" border="0"></a></p>
				<div class="unit-content">
<html:errors />

<table class="table-results">
	<tr>
		<td>
		<B>Existing Bank Accounts</B>
		</td>
		<td>
		<select>
			<option value="0">Select an Account</option>
			<option value="23">Test Checking</option>
		</select>
		</td>
	</tr>
	<tr>
		<td colspan=2><U>Enter Bank Account Details:</U></td>
		<td rowspan=6><IMG border="0" src="images\eCheck2.jpg" width="320" height="163"></td>
	</tr>
	<tr>
		<td><B>Bank Routing Number</B></td>
		<td><input type=text></td>
	</tr>
	<tr>
		<td></td>
		<td>United States Banks Only</td>
	</tr>
	<tr>
		<td><B>Checking Account Number</B></td>
		<td><input type=text></td>
	</tr>
	<tr>
		<td><B>Account Type</B></td>
		<td><select>
			<option value="0"></option>
			<option value="23">Test Checking</option>
		</select>
		</td>	
	</tr>
	<tr>
		<td colspan=2 align="right">
			<INPUT type="submit" name="bntVerify" value="Continue">
			<INPUT type="submit" name="bntCan" value="Cancel">
		</td>
	</tr>
</table>

</div>
</div>
<!-- END Content Area -->
		<div id="inc_footer">
		</div>
		<script LANGUAGE="JavaScript">init()</script>
		<div id="btmPageContent">
		</div>
	</div>

</body>
</html>