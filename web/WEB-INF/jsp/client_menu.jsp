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
        <p><fmt:message key="pageSec.text1" var="textValue"/>
                ${textValue}</p>
    </c:when>
    <c:when test="${sessionScope.userRole != 'CLIENT'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p><fmt:message key="pageSec.text4" var="textValue"/>
                ${textValue}</p>
    </c:when>
    <c:when test="${sessionScope.user.id_status != '2'}">
        <%@ include file="/WEB-INF/jspf/header1.jspf" %>
        <p><fmt:message key="pageSec.text2" var="textValue"/>
                ${textValue}</p>
    </c:when>
    <c:otherwise>
        <%@ include file="/WEB-INF/jspf/header2.jspf" %>

            <img id="img1" src="/images/post.png">
            <p class="login">${sessionScope.user.login}</p>

        <div id="flex">
            <div>
                <form class="clientop" action="addcard" method="get">
                    <p>
                        <button <fmt:message key="clientMenu.button1" var="buttonValue"/>
                                class="w3-btn w3-blue " style="width:13%" type="submit"
                                name="submit">${buttonValue}</button>
                    </p>
                </form>

                <form class="clientop" action="controller" method="post">
                    <p>
                        <button <fmt:message key="clientMenu.button2" var="buttonValue"/>
                                class="w3-btn w3-blue " style="width:13%" type="submit" name="command"
                                value="ViewPayment">${buttonValue}</button>
                    </p>
                </form>

                <form class="clientop" action="controller" method="post">
                    <p>
                        <button <fmt:message key="clientMenu.button3" var="buttonValue"/>
                                class="w3-btn w3-blue " style="width:13%" type="submit" name="command"
                                value="ViewCard">${buttonValue}</button>
                    </p>
                </form>
            </div>
            <div id="client-menu">
                <div class="block-1">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="Payment"/>
                        <button <fmt:message key="clientMenu.button4" var="buttonValue"/>
                                class="w3-btn w3-border  w3-blue w3-border-gray w3-round-large w3-medium"
                                style="width:187%"
                                name="payment" value="transfer"><p class="index-button">${buttonValue}</p></button>
                    </form>
                </div>
                <div class="block-1">
                    <form style="width:92%" action="controller" method="get">
                        <input type="hidden" name="command" value="Payment"/>
                        <button <fmt:message key="clientMenu.button5" var="buttonValue"/>
                                class="w3-btn w3-border  w3-blue w3-border-gray w3-round-large w3-medium"
                                style="width:28%"
                                name="payment" value="internet"><p class="index-button">${buttonValue}</p></button>

                        <button <fmt:message key="clientMenu.button6" var="buttonValue"/>
                                class="w3-btn w3-border  w3-blue w3-border-gray w3-round-large w3-medium"
                                name="payment" value="public"><p class="index-button">${buttonValue}</p></button>
                    </form>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
