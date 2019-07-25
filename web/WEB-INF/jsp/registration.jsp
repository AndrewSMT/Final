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
    <div class = reg_block><label><fmt:message key="registration.label" var="labelValue" />${labelValue}</label></div>
    <input type="hidden" name="command" value="Registration"/>
    <fmt:message key="registration.input1" var="inputValue" />
    <input id="login-reg" class="w3-input w3-border" maxlength='20' minlength="6" type="text" name="login" placeholder="${inputValue}" required><p></p>
    <fmt:message key="registration.input2" var="inputValue" />
    <input class="w3-input w3-border" maxlength='15' minlength="6" type="password" name="pass" placeholder="${inputValue}" required><p></p>
    <fmt:message key="registration.input3" var="inputValue" />
    <input class="w3-input w3-border" maxlength='20' minlength="3" type="text" name="first_name" placeholder="${inputValue} " required><p></p>
    <fmt:message key="registration.input4" var="inputValue" />
    <input class="w3-input w3-border" maxlength='20' minlength="3" type="text" name="last_name" placeholder="${inputValue}" required><p></p>
    <input class="w3-input w3-border" type="text"  placeholder=" +38(___)___-__-__"
          maxlength='17'  name="phone"required/>
    <fmt:message key="registration.button1" var="buttonValue" />
    <p><button  class="w3-btn w3-blue " type="submit" name="submit">${buttonValue}</button></p>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>