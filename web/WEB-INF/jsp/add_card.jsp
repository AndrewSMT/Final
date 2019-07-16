<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.user.login == null}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p> Sorry, you can`t get there page without authorization.</p>
    </c:when>
    <c:when test="${sessionScope.userRole != 'CLIENT'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p> Only client can  get there page.</p>
    </c:when>
    <c:when test="${sessionScope.user.id_status != '2'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p>  Sorry, your personal page was blocked.</p>
    </c:when>
    <c:otherwise>
<%@ include file="/WEB-INF/jspf/header2.jspf" %>
        <c:if test="${valid != null}">
            <div class="valid-block">
                <p>${valid} !!!</p>
            </div>
        </c:if>
<form id="reg_form" class="w3-container w3-display-middle" action="controller" method="post">
    <input type="hidden" name="command" value="NewCard"/>
    <p><label class = reg_block>Enter card information</label></p>
    <p></p>
    <input id="login-reg" class="w3-input w3-border" maxlength='9' minlength="9" pattern="^[0-9]{9}$" type="text" name="number" placeholder="Card number" required>
    <p></p>
    <input class="w3-input w3-border" maxlength='5' minlength="5" type="text" name="date" pattern="^[0-9]{2}\/[0-9]{2}$" placeholder="MM/YY" required>
    <p></p>
    <input class="w3-input w3-border" maxlength='3' minlength="3" type="text" name="code" pattern="^[0-9]{3}$" placeholder="СVV2-code" required>
    <p></p>
    <select  class="select" name="type" form="reg_form">
        <option>Checking account</option>
        <option>Personal account</option>
    </select>

    <p><button  class="w3-btn w3-blue " type="submit" name="submit">submit</button></p>
</form>
    </c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
