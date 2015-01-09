<%@ page language="java" 
    import="SMenu.MenuBean" %>
<jsp:useBean id="smenu" scope = "session" 
    class="SMenu.MenuBean"/>
<jsp:setProperty name="smenu" property="*"/>
<html><head>
<title>Opener Page</title>
<!-- -----------------  -->
<script language ="JAVASCRIPT">
//detects which browser we are using
 function showData(form) {
    form.action= "ShowMenu.jsp";   
    var browser = navigator.appName;
    //sets string into hidden input variables
    form.browserType.value = browser;
    form.browserVersion.value = 
        navigator.appVersion;
    form.submit();  
 } 
</script>
</head>
<!-- -----------------  -->
<body>
<center>
<h2>Welcome to the Opener</h2>

<form>
<input type=button name="search" 
    value="Enter site" 
    onClick="showData(this.form)"> 
<INPUT TYPE="HIDDEN" NAME="browserType" value="foo">
<INPUT TYPE="HIDDEN" NAME="browserVersion" value="foo2">
</form>

</center>
</body>
</html>

