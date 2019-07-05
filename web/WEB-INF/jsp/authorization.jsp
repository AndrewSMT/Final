<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
</head>
<body>
<!-- header -->
<%@ include file="/WEB-INF/jspf/header.jspf"%>
    <form id="autForm" class="w3-container w3-display-middle">
       <p><label c>Вход в личный кабинет</label></p>
        <input class="w3-input w3-border" type="text" name="login" placeholder="Логин">
        <p></p>
        <input class="w3-input w3-border" type="password" name="pass" placeholder="Пароль">
        <p><button  class="w3-btn w3-blue " type="submit" name="submit">submit</button></p>
    </form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>