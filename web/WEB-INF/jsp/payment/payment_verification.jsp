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
<form  id="autForm" class="w3-container w3-display-middle"  action="controller" method="post">
    <input type="hidden" name="command" value="ViewResult"/>
    <p> <label>From card:</label>
     <label>${from}</label>
        <input type="hidden" name="from" value="${from}"></p>
    <p><label>To card:</label>
        <input type="hidden" name="to" value="${to}">
        <input type="hidden" name="id_account_service" value="0">
        <label> ${to}</label></p>
    <p><label>How much:</label>
        <label>${howmuch}</label></p>
        <input type="hidden" name="howmuch" value="${howmuch}">
        <input type="hidden" name="id_payment" value="${id_payment}">
    <p><button  class="w3-btn w3-blue " type="submit" name="submit">Send</button></p>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
