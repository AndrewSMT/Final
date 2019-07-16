<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
</head>
<body>
<!-- header -->
<%@ include file="/WEB-INF/jspf/header1.jspf"%>
<c:if test="${valid != null}">
    <div class="valid-block">
        <p>${valid} !!!</p>
    </div>
</c:if>
    <form id="reg_form" class="w3-container w3-display-middle" action="controller" method="post">
        <input type="hidden" name="command" value="login"/>
        <div class = reg_block><label>Login</label></div>
        <input id="login-reg" class="w3-input w3-border"  maxlength='20' minlength="6" type="text"  name="login" placeholder="Login">
        <p></p>
        <input class="w3-input w3-border" maxlength='15' minlength="6" type="password" name="pass" placeholder="Password">
        <p><button  class="w3-btn w3-blue " type="submit" name="submit">Sign in</button></p>
    </form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>