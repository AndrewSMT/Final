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
            <form class="clientop" action="addcard" method="get">
                <p><button class="w3-btn w3-blue " style="width:13%" type="submit" name="submit">Add new card</button></p>
            </form>

            <form class="clientop" action="controller" method="post">
                <p><button class="w3-btn w3-blue " style="width:13%" type="submit" name="command" value="ViewCard">My payment</button></p>
            </form>

            <form class="clientop" action="controller" method="post">
                <p><button class="w3-btn w3-blue " style="width:13%" type="submit" name="command" value="ViewCard">My account</button></p>
            </form>
        </div>
        <div id="client-menu">
            <div class="block-1">
                <form  action="controller" method="get">
                    <input type="hidden" name="command" value="ViewPayment"/>
                    <button class="w3-btn w3-border  w3-blue w3-border-gray w3-round-large w3-medium" style="width:28%"
                            name="payment" value="transfer"><p class="index-button">Transfer between cards</p></button>
                </form>
            </div>
            <div class="block-1">
                <form  action="controller" method="get">
                    <input type="hidden" name="command" value="ViewPayment"/>
                    <button class="w3-btn w3-border  w3-blue w3-border-gray w3-round-large w3-medium" style="width:14%"
                            name="payment" value="internet"><p class="index-button">Internet payment</p></button>

                    <button class="w3-btn w3-border  w3-blue w3-border-gray w3-round-large w3-medium" style="width:14%"
                            name="payment" value="public"><p class="index-button">Public service</p></button>
                </form>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
