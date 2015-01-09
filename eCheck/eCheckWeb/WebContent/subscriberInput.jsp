<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>		
<html>
<body>
<html:form action="/submit.do" focus="subscirber.subid">
   <p>Enter the subscriber id here. <html:text property="subscriber.subid"/></p> 
<html:submit/>
<html:reset/>
</html:form>
<body>
</html>