<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html >
<%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
<body>
<!-- header -->
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<div class="w3-display-middle">    <!-- buttons holder -->

    <button  class="w3-btn w3-green w3-border w3-border-gray w3-round-large" onclick="location.href='/registration'">Регистрация</button>
    <button  class="w3-btn w3-green w3-border w3-border-gray w3-round-large" onclick="location.href='/authorization'">Авторизация</button>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>