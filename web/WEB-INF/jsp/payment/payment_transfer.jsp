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
    <input type="hidden" name="command" value="Verification"/>
    <p><label >Select card</label></p>
    <p> <label>
        <c:forEach var="bean" items="${viewCards}">
        ${bean.number}
    </label>
        <input  type="hidden" name="id_account" value="${bean.id_account}">
    <input type="radio" id="contactChoice2"
           name="from" value="${bean.number}"></p>
    </c:forEach>
    <p><label>To card:</label>
    <input class="w3-input w3-border" type="text" name="to" ></p>
    <p><label >How much:</label>
        <input class="w3-input w3-border" type="text" name="howmuch" ></p>
    <p><button  class="w3-btn w3-blue " type="submit" name="submit">Next</button></p>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
