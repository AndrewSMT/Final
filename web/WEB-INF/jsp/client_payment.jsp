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
    <c:when test="${sessionScope.userRole != 'CLIENT'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p><fmt:message key="pageSec.text4" var="textValue" />
                ${textValue}</p>
    </c:when>
    <c:when test="${sessionScope.user.id_status != '2'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p> <fmt:message key="pageSec.text2" var="textValue" />
                ${textValue}</p>
    </c:when>
    <c:otherwise>
<%@ include file="/WEB-INF/jspf/header2.jspf" %>
<div id="flex">
    <div class="st">
        <form  action="controller" method="post">
            <input type="hidden" name="command" value="ViewPayment"/>
            <label><fmt:message key="clientPayment.sort1" var="sortValue" />${sortValue}</label>
            <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNumbUp">↑</button>
            <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNumbDown">↓</button>
        </form>
    </div >
    <div class="st">
        <form  action="controller" method="post">
            <input type="hidden" name="command" value="ViewPayment"/>
            <label><fmt:message key="clientPayment.sort2" var="sortValue" />${sortValue}</label>
            <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNameUp">↑</button>
            <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNameDown">↓</button>
        </form>
    </div>
</div>
        <c:choose>

            <c:when test="${fn:length(viewPayments) == 0}">
                <fmt:message key="clientPayment.errorPayment" var="errorValue" />${errorValue}</c:when>
            <c:otherwise>
<div>
    <table class="w3-table w3-striped "  style="width:50%" border="1">
        <tr>
            <fmt:message key="clientPayment.tableHead1" var="thValue" />
            <th>${thValue}</th>
            <fmt:message key="clientPayment.tableHead2" var="thValue" />
            <th>${thValue}</th>
            <fmt:message key="clientPayment.tableHead3" var="thValue" />
            <th>${thValue}</th>
            <fmt:message key="clientPayment.tableHead4" var="thValue" />
            <th>${thValue}</th>
            <fmt:message key="clientPayment.tableHead5" var="thValue" />
            <th>${thValue}</th>
        </tr>
        <c:forEach var="bean" items="${viewPayments}"  >
            <tr>
                <td>${bean.numberFrom}</td>
                <td>${bean.number}</td>
                <td>${bean.value}</td>
                <td>${bean.date}</td>
                <td>${bean.service}</td>
            </tr>
                </c:forEach>
    </table>
</div>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
