<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html >
<%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
<body>
<!-- header -->
<%@ include file="/WEB-INF/jspf/header1.jspf"%>
<div id="autFor" class="w3-display-middle">    <!-- buttons holder -->
    <button  class="w3-btn  w3-border w3-cyan w3-border-gray w3-round-large w3-medium"
             onclick="location.href='/registration'"><p class="index-button">Registration</p></button>
    <button class="w3-btn w3-border  w3-cyan w3-border-gray w3-round-large w3-medium"
            onclick="location.href='/authorization'"><p class="index-button">Authorization</p></button>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>