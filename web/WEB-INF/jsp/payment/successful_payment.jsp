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
<div class="w3-container w3-display-middle" >
    <p>Payment was successful</p>
    <button class="w3-btn w3-border  w3-cyan w3-border-gray w3-round-large w3-medium"
            onclick="location.href='/clientpage'"><p class="index-button">Home</p></button>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>
