<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header1.jspf"%>
<form id="autForm" class="w3-container w3-display-middle" action="controller" method="post">
    <p><label c>Registration</label></p>
    <input type="hidden" name="command" value="Registration"/>
    <input class="w3-input w3-border" type="text" name="login" placeholder="Login"><p></p>
    <input class="w3-input w3-border" type="text" name="pass" placeholder="Password"><p></p>
    <input class="w3-input w3-border" type="text" name="first_name" placeholder="First name"><p></p>
    <input class="w3-input w3-border" type="text" name="last_name" placeholder="Last name"><p></p>
    <input class="w3-input w3-border" type="text" name="phone" placeholder="Phone"><p></p>
    <p><button  class="w3-btn w3-blue " type="submit" name="submit">Registration</button></p>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>