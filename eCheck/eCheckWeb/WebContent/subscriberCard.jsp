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
		switch(window.event.keyCode) {
			case 9:
				break
			case 16:
				break
			default:
			if (content.length==element.maxLength) {
				if (next<document.forms["EnterCardInfo"].elements.length) {
					document.forms["EnterCardInfo"].elements[next].focus()
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
<h3 class="header-orange">Credit Card Information</h3><p align=right><a href="/secure/restricted/redirect.asp"><img src="/secure/images/exit-red.gif" border="0"></a></p>
<div class="unit-content">
<html:errors />
<html:form action="/subVerify.do" focus="subscriber.ccnum">
	<center>
	<TABLE class="table-results" border="0" width="80%">
		<TBODY>
			<TR>
				<TD></TD>
				<TD></TD>
				<TD height="6">Expiration Date</TD>
			</TR>
			<TR>
				<TD height="6" width="30%">Credit Card Type</TD>
				<TD height="6">Credit Card Number</TD>
				<TD height="6">(MM/YY)</TD>
			</TR>
			<TR>
				<TD height="34">
					<html:select size="1" property="subscriber.cctype">
						<html:option value="0">VISA</html:option>
						<html:option value="1">MasterCard</html:option>
					</html:select>
				</TD>
				<TD height="34" width="16"><html:text property="subscriber.ccnum" size="18" maxlength="16" onkeyup="jump2(this,this.value,2)"></html:text></TD>
				<TD height="34" width="120" nowrap>&nbsp;
					<html:text property="subscriber.ccmo" maxlength="2" size="2" onkeyup="jump2(this,this.value,3)"></html:text>&nbsp;/&nbsp;<html:text
							property="subscriber.ccyyyy" maxlength="2" size="2" onkeyup="jump2(this,this.value,4)"></html:text>
				</TD>
			</TR>			
			<tr>
				<td colspan=3 align=right>
				<INPUT type="submit" name="bntVerify" value="Continue">
				<INPUT type="submit" name="bntCan" value="Cancel">
				</td>
			</tr>
		</TBODY>
	</TABLE>
	</center>
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