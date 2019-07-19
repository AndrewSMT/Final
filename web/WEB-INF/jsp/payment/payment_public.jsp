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
    <input type="hidden" name="command" value="VerificationInternet"/>
    <input type="hidden" name="pay" value="public"/>
    <p><label >Select card</label></p>
    <p> <label>
        <c:forEach var="bean" items="${viewCards}">
            ${bean.number}
    </label>
        <input  type="hidden" name="id_account_card" value="${bean.id_account}">
        <input type="radio" name="from" required value="${bean.number}"></p>
    </c:forEach>

    <p><label >Select service</label></p>
    <p> <label><c:forEach var="bean" items="${viewService}">
            ${bean.title}
    </label>
        <input type="hidden" name="title" value="${bean.title}">
    <input type="radio" name="id_account_service" required value="${bean.id_account}"></p>
    </c:forEach>
    <p><label >Personal account:</label>
        <input class="w3-input w3-border" type="number" type="text"  pattern="^[0-9]{1,9}$"
               name="personal_account"required ></p>
    <p><label >Amount:</label>$
        <input class="w3-input w3-border" type="text"  pattern="^[0-9]{1,9}$"
               name="howmuch" required></p>
    <p><button  class="w3-btn w3-blue " type="submit" name="submit">Next</button></p>
</form>
    </c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>