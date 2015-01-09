<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="pragma" content="no-cache" />
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
<h3 class="header-orange">Account Information</h3><p align=right><a href="/secure/restricted/redirect.asp"><img src="/secure/images/exit-red.gif" border="0"></a></p>
<div class="unit-content">
<html:form action="/setUpPayForm.do">
<logic:equal value="AA" property="subscriber.bsrc" name="ReadPayment">	
	<logic:equal value="N" property="subscriber.eligibilInd" name="ReadPayment">
	<p>This account is currently listed as terminated.  Please contact a Customer Service Representative at 1-800-725-6849.</p>
	</logic:equal>
	<logic:equal  value="Y" property="subscriber.eligibilInd" name="ReadPayment">
		<logic:equal value="0.0" property="subscriber.amtdue" name="ReadPayment">
	<TABLE class="table-results" border="0" width="100%">
		<TBODY>
			<TR>
				<TD>
					CURRENT BALANCE DUE:
				</TD>
				<td>
					<html:text property="subscriber.scramtdue" readonly="true" />
				</td>
			</TR>
			<TR>
				<TD>
					DUE DATE:
				</TD>
				<td>
					<html:text property="subscriber.duedate" readonly="true" />
				</td>
			</TR>			
			<TR>
				<td>LAST PAYMENT DATE:</td>
				<TD><html:text property="subscriber.billfrom"  readonly="true" ></html:text></TD>
			</tr>
			<TR>
				<TD>
					LAST PAYMENT AMOUNT:
				</TD>
				<td>
					<html:text property="subscriber.scrlstdue" readonly="true" />
				</td>
			</TR>
		</TBODY>
	</TABLE>
		<p>The account currently has no balance due.  Please call the customer service number on your card if you have any questions.</p>
		</logic:equal>
		<logic:notEqual value="0.0" property="subscriber.amtdue" name="ReadPayment">
	<TABLE class="table-results" border="0" width="100%">
		<TBODY>
			<TR>
				<TD>
					CURRENT BALANCE DUE:
				</TD>
				<td>
					<html:text property="subscriber.scramtdue" readonly="true" />
				</td>
			</TR>
			<TR>
				<TD>
					DUE DATE:
				</TD>
				<td>
					<html:text property="subscriber.duedate" readonly="true" />
				</td>
			</TR>			
			<TR>
				<td>LAST PAYMENT DATE:</td>
				<TD><html:text property="subscriber.billfrom"  readonly="true" ></html:text></TD>
			</tr>
			<TR>
				<TD>
					LAST PAYMENT AMOUNT:
				</TD>
				<td>
					<html:text property="subscriber.scrlstdue" readonly="true" />
				</td>
			</TR>

			<tr>
				<td></td>
				<td valign="middle">
				<INPUT type="submit" name="bntReg" value="Register"> First time registration
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
				<INPUT type="submit" name="bntPay" value="Pay with Credit Card">
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
				<INPUT type="submit" name="bntCheck" value="Pay with Electronic Check"> 
				</td>
			</tr>
		</TBODY>
	</TABLE>

		</logic:notEqual>
	</logic:equal>
</logic:equal>
<logic:notEqual value="AA" property="subscriber.bsrc" name="ReadPayment">
	<logic:equal value="DE" property="subscriber.bsrc" name="ReadPayment">
Hi there	<html:text property="subscriber.subid"></html:text>
	</logic:equal>
	<logic:notEqual value="DE" property="subscriber.bsrc" name="ReadPayment">	
<p>We were unable to locate your subscriber information.  Please contact a Customer Service Representative at 1-800-725-6849.</p>
		<INPUT type="hidden" name="hidErrormsg" size="20"
		value="<bean:write name="ReadPayment" property="subscriber.bsmsg" />" >
		<INPUT type="hidden" name="hidError"
		value="<bean:write name="ReadPayment" property="subscriber.bserror" />" >
<!-- 
	<html:text property="subscriber.bsmsg" readonly="true" size="60"/><br>
	<html:textarea property="subscriber.bserror" readonly="true" cols="60" rows="7"/>
 -->		
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