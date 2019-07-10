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
<div id="flex">
    <div class="st">
    <form  action="controller" method="post">
        <input type="hidden" name="command" value="ViewCard"/>
        <label>Sort by number</label>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNumbUp">↑</button>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNumbDown">↓</button>
</form>
    </div >
    <div class="st">
    <form  action="controller" method="post">
        <input type="hidden" name="command" value="ViewCard"/>
        <label>Sort by name</label>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNamebUp">↑</button>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortNameDown">↓</button>
    </form>
    </div>
    <div class="st">
    <form  action="controller" method="post">
        <input type="hidden" name="command" value="ViewCard"/>
        <label>Sort by balance</label>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortBalUp">↑</button>
        <button class="w3-button w3-black  w3-tiny" type="submit" name="sort" value="sortBalDown">↓</button>
    </form>
    </div>
</div>
<div >
    <table class="w3-table w3-striped"  style="width:50%" border="1">
     <tr>
         <th>Number</th>
         <th>Balance</th>
         <th>Date</th>
         <th>Name</th>
         <th>Status</th>
         <th>Сard management</th>
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
                            <button class="w3-btn w3-blue "  type="submit" >Block card</button>
                        </p>
                    </form></c:if>
                    <c:if test = "${bean.status == 'blocked'}">
                        <form class="clientop" action="controller" method="post">
                            <p>
                                <input type="hidden" name="command" value="UnBlockCard"/>
                                <input type="hidden" name="id_account" value="${bean.id_account}"/>
                                <button class="w3-btn w3-blue "  type="submit" >Send request</button>
                            </p>
                        </form></c:if>
                        </td>
            </tr>

        </c:forEach>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
