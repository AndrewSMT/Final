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
        <input type="hidden" name="command" value="ViewCard"/>
        <label><fmt:message key="clientAccount.sort1" var="sortValue" />${sortValue}</label>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNumbUp">↑</button>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNumbDown">↓</button>
</form>
    </div >
    <div class="st">
    <form  action="controller" method="post">
        <input type="hidden" name="command" value="ViewCard"/>
        <label><fmt:message key="clientAccount.sort2" var="sortValue" />${sortValue}</label>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNamebUp">↑</button>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNameDown">↓</button>
    </form>
    </div>
    <div class="st">
    <form  action="controller" method="post">
        <input type="hidden" name="command" value="ViewCard"/>
        <label><fmt:message key="clientAccount.sort3" var="sortValue" />${sortValue}</label>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortBalUp">↑</button>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortBalDown">↓</button>
    </form>
    </div>
</div>
        <c:choose>
            <c:when test="${fn:length(viewCards) == 0}">
                <fmt:message key="clientAccount.errorCard" var="errorValue" />${errorValue}</c:when>
            <c:otherwise>
<div >
    <table class="w3-table w3-striped"  style="width:50%" border="1">
     <tr>
         <fmt:message key="clientAccount.tableHead1" var="thValue" />
         <th>${thValue}</th>
         <fmt:message key="clientAccount.tableHead2" var="thValue" />
         <th>${thValue}</th>
         <fmt:message key="clientAccount.tableHead3" var="thValue" />
         <th>${thValue}</th>
         <fmt:message key="clientAccount.tableHead4" var="thValue" />
         <th>${thValue}</th>
         <fmt:message key="clientAccount.tableHead5" var="thValue" />
         <th>${thValue}</th>
         <fmt:message key="clientAccount.tableHead6" var="thValue" />
         <th>${thValue}</th>
     </tr>
        <c:forEach var="bean" items="${viewCards}">
            <tr>
                <td>${bean.number}</td>
                <td>${bean.balance}</td>
                <td>${bean.date}</td>
                <td>${bean.name}</td>
                <td>${bean.status}</td>
                <td><c:if test = "${bean.status == 'unblocked'}">
                    <form class="clientop" action="controller" method="post">
                        <p>
                            <input type="hidden" name="command" value="BlockCard"/>
                            <input type="hidden" name="id_account" value="${bean.id_account}"/>
                            <input type="hidden" name="status" value="${bean.status}"/>
                            <button <fmt:message key="clientAccount.button1" var="buttonValue"/>
                                    class="w3-btn w3-blue "  type="submit" >${buttonValue}</button>
                        </p>
                    </form></c:if>
                    <c:if test = "${bean.status == 'blocked'}">
                        <form class="clientop" action="controller" method="post">
                            <p>
                                <input type="hidden" name="command" value="UnBlockCard"/>
                                <input type="hidden" name="id_account" value="${bean.id_account}"/>
                                <button <fmt:message key="clientAccount.button2" var="buttonValue" />
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
    </c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
