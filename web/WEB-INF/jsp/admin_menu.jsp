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
        <p> Sorry, you can`t get there page without authorization.</p>
    </c:when>
    <c:when test="${sessionScope.userRole != 'ADMIN'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p> Only admin can  get there page.</p>
    </c:when>
    <c:when test="${sessionScope.user.id_status != '2'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p>  Sorry, your personal page was blocked.</p>
    </c:when>
    <c:otherwise>
<%@ include file="/WEB-INF/jspf/header2.jspf" %>
<img id="img1" src="images/post.png">
<p class="login">${sessionScope.user.login}</p>
<div id="flex">
    <div>
        <form class="clientop" action="controller" method="post">
            <input type="hidden" name="command" value="ViewCard"/>
            <p><button class="w3-btn w3-blue " style="width:13%" type="submit" name="submit">View all accounts</button></p>
        </form>
        <form class="clientop" action="controller" method="post">
            <input type="hidden" name="command" value="ViewUsers"/>
            <p><button class="w3-btn w3-blue " style="width:13%" type="submit" name="submit">View all users</button></p>
        </form>
    </div>
</div>
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>