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
	//
	// this function does not currently work for this screen 
	// we now have two tables in here so I would have two routines one 
	// for each table (i.e. CC or eCheck)
	//
	function jump2(element, content, next)
	{
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

	//var objDivStyle = eval('document.all.regCheck.style');
	//alert('What is the style for check ' + objDivStyle.visibility);
	//objDivStyle.visibility = 'visible';
	function openCCtbl()
	{
	document.all.regCheck.style.visibility = 'hidden';
	document.all.regbut.style.visibility = 'hidden';
	document.all.regcc.style.visibility = 'visible';
	}
	function openChecktbl()
	{
	document.all.regCheck.style.visibility = 'visible';
	document.all.regbut.style.visibility = 'hidden';
	document.all.regcc.style.visibility = 'hidden';
	}
	function reset2Top()
	{
	//
	// this function resets everything back to the top
	// should the users want to clear additional fields
	// you would do it here.
	//
	document.all.regbut.style.visibility = 'visible';
	document.all.regCheck.style.visibility = 'hidden';
	document.all.regcc.style.visibility = 'hidden';	
	document.all.payCC.checked = false;
	document.all.payCheck.checked = false;
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
				<h3 class="header-orange">First Time Registration</h3><p align=right><a href="/secure/restricted/redirect.asp"><img src="/secure/images/exit-red.gif" border="0"></a></p>
				<div class="unit-content">
<html:errors />
<html:form action="/setUpRegForm.do">
<div id=regbut>
<TABLE id=regButton class="table-results" border="0" width="80%">
	<TBODY>
		<tr>
			<td>
			<INPUT type="radio" name="payCC" value="1" onclick="openCCtbl()">Credit Card
			</td>
		</tr>
		<tr>
			<td>
			<INPUT type="radio" name="payCheck" value="2" onclick="openChecktbl()">Checking Account
			</td>
		</tr>
		<tr>
			<td>
			<INPUT type="button" name="butCan" value="Cancel" onclick="alert('go back');">
			</td>
		</tr>
	</TBODY>
</TABLE>
</div>
<!-- ==================Start of the dynamic cc and echeck tables============== -->
<div id=regcc style="position:relative;top:-80px;visibility:hidden">
<TABLE id=regmain class="table-results" border="0" width="80%">
	<TBODY>
		<tr>
			<td>
			<INPUT type="radio" checked>Credit Card
			</td>
		</tr>	
		<tr>
			<TD colspan=4>
			Enter the information below as it appears on the credit card billing statement.
			</TD>
		</tr>
		<tr><td><br></td></tr>
		<tr>
			<td colspan=2>First Name:</td>
			<td colspan=2>Last Name:</td>
		</tr>
		<tr>
			<td colspan=2><html:text property="subscriber.firstname" size="15" maxlength="15" onkeyup="jump2(this,this.value,1)"/></td>
			<td colspan=2><html:text property="subscriber.lastname" size="35" maxlength="35" onkeyup="jump2(this,this.value,2)"></html:text></td>
		</tr>
		<tr>
			<td colspan=4>Street Address:</td>
		</tr>		
		<tr>
			<td colspan=4><html:text property="subscriber.address1" size="50" maxlength="50" onkeyup="jump2(this,this.value,3)"/></td>
		</tr>
		<tr>
			<td colspan=4>Second Street Address:</td>
		</tr>		
		<tr>
			<td colspan=4><html:text property="subscriber.address2" size="50" maxlength="50" onkeyup="jump2(this,this.value,4)"/></td>
		</tr>
		<tr>
			<td>City:</td>
			<td>State:</td>
			<td align="left">Zip:</td><td></td>
		</tr>		
		<tr>
			<td><html:text property="subscriber.city" onkeyup="jump2(this,this.value,5)"/></td>
			<td><html:text property="subscriber.state" size="2" maxlength="2" onkeyup="jump2(this,this.value,6)"/></td>
			<td align="left"><html:text property="subscriber.zip" size="5" maxlength="5" onkeyup="jump2(this,this.value,7)"></html:text></td>
			<td></td>
		</tr>
		<tr>
			<td>Phone Number:</td>
			<td colspan=2>Email address:</td>
		</tr>		
		<tr>
			<td><html:text property="subscriber.phonenu" size="14" maxlength="13" onkeyup="jump2(this,this.value,8)"/></td>
			<td colspan=2><html:text property="subscriber.email" onkeyup="jump2(this,this.value,9)"/></td>
		</tr>
		<tr>
			<td></td>
			<td></td><td></td>
			<td>Expiration Date</td>
		</tr>
		<tr>
			<td>Credit Card Type</td>
			<td colspan=2>Credit Card Number</td>
			<td>(MM/YY)</td>
		</tr>
		<tr>
			<td><html:select size="1" property="subscriber.cctype">
				<html:option value="0">VISA</html:option>
				<html:option value="1">MasterCard</html:option>
				</html:select>
			</td>
			<td colspan=2><html:text property="subscriber.ccnum" size="18" maxlength="16" onkeyup="jump2(this,this.value,11)"></html:text></td>
			<td>
				<html:text property="subscriber.ccmo" maxlength="2" size="2" onkeyup="jump2(this,this.value,12)"></html:text>&nbsp;/
				<html:text property="subscriber.ccyyyy" maxlength="2" size="2" onkeyup="jump2(this,this.value,13)"></html:text>
			</td>
		</tr>
		<tr>
			<td colspan=4  align="right">
			<INPUT type="submit" name="bntVerify" value="Continue">
			<INPUT type="button" name="bntCan" value="Cancel" onclick="reset2Top();">
			</td>
		</tr>
	</TBODY>
</TABLE>
</div>
<!-- ==========================  Middle of the screen ======================== -->
<div id=regCheck style="position:relative;top:-500px;visibility:hidden">
<table class="table-results" border="0" width="80%">
	<TBODY>
		<tr>
			<td>
			<INPUT type="radio" checked>Checking Account
			</td>
		</tr>
	<tr>
		<td colspan=2><U>Enter Bank Account Details:</U></td>
		<td rowspan=5><IMG border="0" src="images\eCheck2.jpg" width="320" height="163"></td>
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
		<td colspan=4  align="right">
		<INPUT type="submit" name="bntVerify" value="Continue">
		<INPUT type="button" name="bntCan" value="Cancel" onclick="reset2Top();">
		</td>
	</tr>	
	</TBODY>
</table>
</div>
<!-- ==================End of the dynamic cc and echeck tables============== -->
</html:form>
</div>
</div>
<!-- END Content Area -->
		<div id="inc_footer">
		</div>
		<script LANGUAGE="JavaScript">init()</script>
		<div id="btmPageContent">
		</div>
	</div>
              </div>
</body>
</html>