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
    <table class="w3-table w3-striped"  style="width:50%" border="1">
        <tr>
            <th>login</th>
            <th>phone</th>
            <th>first_name</th>
            <th>last_name</th>
            <th>Status</th>
            <th>User management</th>
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
                            <button class="w3-btn w3-blue "  type="submit" >Block user</button>
                        </p>
                    </form>
                </c:if>
                    <c:if test = "${bean.status == 'blocked'}">
                        <form class="clientop" action="controller" method="post">
                            <p>
                                <input type="hidden" name="command" value="BlockUser"/>
                                <input type="hidden" name="status" value="${bean.status}"/>
                                <input type="hidden" name="id_user" value="${bean.id_user}"/>
                                <button class="w3-btn w3-blue "  type="submit" >Unblock</button>
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