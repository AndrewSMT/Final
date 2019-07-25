<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.user.login == null}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p> <fmt:message key="pageSec.text1" var="textValue" />
                ${textValue}</p>
    </c:when>
    <c:when test="${sessionScope.userRole != 'ADMIN'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p><fmt:message key="pageSec.text3" var="textValue" />
                ${textValue}</p>
    </c:when>
    <c:when test="${sessionScope.user.id_status != '2'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p> <fmt:message key="pageSec.text2" var="textValue" />
                ${textValue}</p>
    </c:when>
    <c:otherwise>
<%@ include file="/WEB-INF/jspf/header2.jspf" %>
<img id="img1" src="images/post.png">
<p class="login">${sessionScope.user.login}</p>
<div id="flex">
    <div>
        <form class="clientop" action="controller" method="post">
            <input type="hidden" name="command" value="ViewCard"/>
            <p><button <fmt:message key="adminMenu.button1" var="buttonValue" />
                    class="w3-btn w3-blue " style="width:13%" type="submit" name="submit">${buttonValue}</button></p>
        </form>
        <form class="clientop" action="controller" method="post">
            <input type="hidden" name="command" value="ViewUsers"/>
            <p><button <fmt:message key="adminMenu.button2" var="buttonValue" />
                    class="w3-btn w3-blue " style="width:13%" type="submit" name="submit">${buttonValue}</button></p>
        </form>
    </div>
</div>
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>