<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header1.jspf"%>
<c:if test="${valid != null}">
<div class="valid-block">
   <p>${valid} !!!</p>
</div>
</c:if>
<form id="reg_form" class="w3-container w3-display-middle" action="controller" method="post">
    <div class = reg_block><label>Register</label></div>
    <input type="hidden" name="command" value="Registration"/>
    <input id="login-reg" class="w3-input w3-border" maxlength='20' minlength="6" type="text" name="login" placeholder="Login" required><p></p>
    <input class="w3-input w3-border" maxlength='15' minlength="6" type="password" name="pass" placeholder="Password" required><p></p>
    <input class="w3-input w3-border" maxlength='20' minlength="3" type="text" name="first_name" placeholder="First name" required><p></p>
    <input class="w3-input w3-border" maxlength='20' minlength="3" type="text" name="last_name" placeholder="Last name" required><p></p>
    <input class="w3-input w3-border" type="text"  placeholder=" +38(___)___-__-__"
          maxlength='17'  name="phone"required/>
    <p><button  class="w3-btn w3-blue " type="submit" name="submit">Register Now</button></p>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>