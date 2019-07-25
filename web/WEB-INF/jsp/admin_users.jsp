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
    <p><fmt:message key="pageSec.text2" var="textValue" />
            ${textValue}</p>
</c:when>
<c:otherwise>
<%@ include file="/WEB-INF/jspf/header2.jspf" %>
    <table class="w3-table w3-striped"  style="width:50%" border="1">
        <tr>
            <fmt:message key="adminUsers.tableHead1" var="thValue" />
            <th>${thValue}</th>
            <fmt:message key="adminUsers.tableHead2" var="thValue" />
            <th>${thValue}</th>
            <fmt:message key="adminUsers.tableHead3" var="thValue" />
            <th>${thValue}</th>
            <fmt:message key="adminUsers.tableHead4" var="thValue" />
            <th>${thValue}</th>
            <fmt:message key="adminUsers.tableHead5" var="thValue" />
            <th>${thValue}</th>
            <fmt:message key="adminUsers.tableHead6" var="thValue" />
            <th>${thValue}</th>
        </tr>
        <c:forEach var="bean" items="${viewUsers}">
            <tr>
                <td>${bean.login}</td>
                <td>${bean.phone}</td>
                <td>${bean.first_name}</td>
                <td>${bean.last_name}</td>
                <td>${bean.status}</td>
                <td><c:if test = "${bean.status == 'unblocked'}">
                    <form class="clientop" action="controller" method="post">
                        <p>
                            <input type="hidden" name="command" value="BlockUser"/>
                            <input type="hidden" name="status" value="${bean.status}"/>
                            <input type="hidden" name="id_user" value="${bean.id_user}"/>
                            <button <fmt:message key="adminUsers.button1" var="buttonValue" />
                                    class="w3-btn w3-blue "  type="submit" >${buttonValue}</button>
                        </p>
                    </form>
                </c:if>
                    <c:if test = "${bean.status == 'blocked'}">
                        <form class="clientop" action="controller" method="post">
                            <p>
                                <input type="hidden" name="command" value="BlockUser"/>
                                <input type="hidden" name="status" value="${bean.status}"/>
                                <input type="hidden" name="id_user" value="${bean.id_user}"/>
                                <button <fmt:message key="adminUsers.button2" var="buttonValue" />
                                        class="w3-btn w3-blue "  type="submit" >${buttonValue}</button>
                            </p>
                        </form></c:if>
                </td>
            </tr>

        </c:forEach>
    </table>
</div>
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>