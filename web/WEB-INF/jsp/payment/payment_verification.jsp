<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
</head>
<body>
<!-- header -->
<%@ include file="/WEB-INF/jspf/header2.jspf" %>
<c:choose>
    <c:when test="${sessionScope.user.login == null}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p><fmt:message key="pageSec.text1" var="textValue" />
                ${textValue}</p>
    </c:when>
    <c:when test="${sessionScope.userRole != 'CLIENT'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p><fmt:message key="pageSec.text4" var="textValue" />
                ${textValue}</p>
    </c:when>
    <c:when test="${sessionScope.user.id_status != '2'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p><fmt:message key="pageSec.text2" var="textValue" />
                ${textValue}</p>
    </c:when>
    <c:otherwise>
<form id="reg_form" class="w3-container w3-display-middle" action="controller" method="post">
    <input type="hidden" name="command" value="ViewResult"/>
    <p> <label><fmt:message key="paymentVarificationServ.label1" var="textValue" />
            ${textValue}</label>
     <label>${from}</label>
        <input type="hidden" name="from" value="${from}"></p>
    <p><label><fmt:message key="paymentVarificationServ.label2" var="textValue" />
            ${textValue}</label>
        <input type="hidden" name="to" value="${to}">
        <input type="hidden" name="id_account_service" value="0">
        <label> ${to}</label></p>
    <p><label><fmt:message key="paymentTransfer.label3" var="textValue" />
            ${textValue}</label>
        <label>${howmuch}</label></p>
        <input type="hidden" name="howmuch" value="${howmuch}">
        <input type="hidden" name="id_payment" value="${id_payment}">
    <p><button <fmt:message key="paymentVarificationServ.button1" var="textValue" />
            class="w3-btn w3-blue " type="submit" name="submit">${textValue}</button></p>
</form>
    </c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
