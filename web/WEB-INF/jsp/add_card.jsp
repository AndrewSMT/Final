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
<form id="autForm" class="w3-container w3-display-middle" action="controller" method="post">
    <input type="hidden" name="command" value="NewCard"/>
    <p><label c>Enter card information</label></p>
    <p></p>
    <input class="w3-input w3-border" type="text" name="number" placeholder="Number">
    <p></p>
    <input class="w3-input w3-border" type="text" name="date" placeholder="Expiration date">
    <p></p>
    <input class="w3-input w3-border" type="text" name="code" placeholder="СVV2-code">
    <p></p>
    <select  class="select" name="type" form="autForm">
        <option>Checking account</option>
        <option>Personal account</option>
    </select>

    <p><button  class="w3-btn w3-blue " type="submit" name="submit">submit</button></p>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
