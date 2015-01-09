
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE><bean:message key="title" /></TITLE>
</HEAD>

<BODY>
<h1>Pizza Order Page </h1>
		<html:errors/> 
	<html:form action="/submit.do">
	Name: <html:text property="customer.name"/><br>
	Address: <html:text property="customer.address"/><br>
		
	Size: <html:radio property ="size" value="S"/>Small
	<html:radio property ="size" value="M"/>Medium
	<html:radio property ="size" value="L"/>Large
			
	Toppings: <br>
	Pepperoni<html:checkbox property="topping(Pepperoni)"/><br>
	Onion<html:checkbox property="topping(Onion)"/><br>
	Mushroom<html:checkbox property="topping(Mushroom)"/><br>
	Hot Pepper<html:checkbox property="topping(Hot Pepper)"/><br>
	Bacon<html:checkbox property="topping(Bacon)"/><br>
			
	<html:select property ="type">
	<html:option value="a">Delivery</html:option>
	<html:option value="b">Pickup</html:option>
	</html:select>
			
	<html:submit/>
	<html:reset/>
	</html:form>
</BODY>
</html:html>
