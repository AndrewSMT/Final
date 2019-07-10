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
<div class="sortb"> <form class="clientop" action="addcard" method="get">
    <p>
        <button class="w3-btn w3-blue " style="width:13%" type="submit" name="submit">Add new card</button>
    </p>
</form>

    <form class="clientop" action="controller" method="post">
        <input type="hidden" name="command" value="ViewCard"/>
        <p>
            <button class="w3-btn w3-blue " style="width:13%" type="submit" name="submit">My payment</button>
        </p>
    </form>

    <form class="clientop" action="controller" method="post">
        <input type="hidden" name="command" value="ViewCard"/>
        <p>
            <button class="w3-btn w3-blue " style="width:13%" type="submit" name="submit">My account</button>
        </p>
    </form>
</div>
<div>
    <table class="w3-table w3-striped "  style="width:50%" border="1">
        <tr>
            <th>Number</th>
            <th>Balance</th>
            <th>Date</th>
            <th>Name</th>
            <th>Status</th>
        </tr>
        <c:forEach var="bean" items="${viewCards}">
            <tr>
                <td>${bean.number}</td>
                <td>${bean.balance}</td>
                <td>${bean.date}</td>
                <td>${bean.name}</td>
                <td>${bean.status}</td>
                <td><form class="clientop" action="controller" method="post">
                    <p>
                        <input type="hidden" name="command" value="BlockCard"/>
                        <input type="hidden" name="id_account" value="${bean.id_account}"/>
                        <button class="w3-btn w3-blue "  type="submit" >Block card</button>
                    </p>
                </form></td>
            </tr>

        </c:forEach>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
