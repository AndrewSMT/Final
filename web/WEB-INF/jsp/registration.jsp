<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<form method="post"  id="autForm" class="w3-container w3-display-middle">
    <p><label c>Регистрация</label></p>
    <input class="w3-input w3-border" type="text" name="login" placeholder="Логин"><p></p>
    <input class="w3-input w3-border" type="text" name="pass" placeholder="Пароль"><p></p>
    <input class="w3-input w3-border" type="text" name="name" placeholder="Имя"><p></p>
    <input class="w3-input w3-border" type="text" name="surname" placeholder="Фамилия"><p></p>
    <input class="w3-input w3-border" type="text" name="phone" placeholder="Телефон"><p></p>
    <p><button  class="w3-btn w3-blue " type="submit" name="submit">Зарегистрироваться</button></p>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>