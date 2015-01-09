<%@ page language="java" import="SMenu.MenuBean" import="java.util.*" %>
<jsp:useBean id="smenu" scope = "session" class="SMenu.MenuBean"/>
<jsp:setProperty name="smenu" property="*"/>
<html>
<head>
    <TITLE>Favorite sites</TITLE>
    <style>A       {text-decoration: none}</style>
    <script src=
    <% out.println("\"" +smenu.getBrowserMenu() +"\""); %>
    
    language="JavaScript">
    </script>

    <script language="JavaScript">
     function setMenus() {
      menuBackColor = "#ffffff";
      menuForeColor = "#FFFF00";
      menuHighColor = "#ffc000";
      menuX = 50;
      menuY = 100;
      menuWidth = 200;
      menuHeight = 20;
      menuBottom = 300;
      
       var index = 0;
       addMenu("Lab Software", index++, "http://labsoftware.com");
       addMenu("Connecticut Swimming", index++,"http://ctswim.org");
       addMenu("Amazon", index++, "http://amazon.com");
       addMenu("US Postal Service", index++, "http://usps.gov");
       drawMenus();
    }
</script>
<style>
a:hover{color:#ff00ff; font-weight:bold; background-color:#FFFF00; }
</style>

<style> A
 {text-decoration:none}
</style>
<body onLoad="setMenus()">
<h1> Favorite Sites</h1>
<form>
<INPUT TYPE="HIDDEN" NAME="browserType" value=
<% out.println("\"" + smenu.getBrowserType() +"\""); %>
>
<INPUT TYPE="HIDDEN" NAME="browserVersion" value=
<% out.println("\"" + smenu.getBrowserVersion() +"\""); %>
>
</form>
</body></html>
