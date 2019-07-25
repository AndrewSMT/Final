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
        <div class = reg_block><label><fmt:message key="authorization.label" var="labelValue" />${labelValue}</label></div>
        <fmt:message key="authorization.input1" var="inputValue" />
        <input id="login-reg" class="w3-input w3-border"  maxlength='20' minlength="6" type="text"  name="login" placeholder="${inputValue}">
        <p></p>
        <fmt:message key="authorization.input2" var="inputValue" />
        <input class="w3-input w3-border" maxlength='15' minlength="6" type="password" name="pass" placeholder="${inputValue}">
        <fmt:message key="authorization.button1" var="buttonValue" />
        <p><button  class="w3-btn w3-blue " type="submit" name="submit">${buttonValue}</button></p>
    </form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>