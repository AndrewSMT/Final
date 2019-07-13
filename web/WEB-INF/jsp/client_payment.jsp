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
<div>
    <table class="w3-table w3-striped "  style="width:50%" border="1">
        <tr>
            <th>From card</th>
            <th>To card/service account</th>
            <th>Value</th>
            <th>Date</th>
            <th>Service title</th>
        </tr>
        <c:forEach var="bean" items="${viewPayments}"  >
            <tr>
                <td>${bean.numberFrom}</td>
                <td>${bean.number}</td>
                <td>${bean.value}</td>
                <td>${bean.date}</td>
            </tr>
                </c:forEach>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
