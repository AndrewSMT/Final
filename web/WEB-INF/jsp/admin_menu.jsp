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
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>