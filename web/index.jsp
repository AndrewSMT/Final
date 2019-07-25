<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html lang="${language}">
<%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
<body>
<!-- header -->
<%@ include file="/WEB-INF/jspf/header1.jspf"%>
<div id="autFor" class="w3-display-middle">    <!-- buttons holder -->
    <button <fmt:message key="index.button1" var="buttonValue" /> class="w3-btn  w3-border w3-cyan w3-border-gray w3-round-large w3-medium"
             onclick="location.href='/registration'"><p class="index-button">${buttonValue}</p></button>
    <button <fmt:message key="index.button2" var="buttonValue" /> class="w3-btn w3-border  w3-cyan w3-border-gray w3-round-large w3-medium"
            onclick="location.href='/authorization'"><p class="index-button">${buttonValue}</p></button>
</div>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
    </select>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>

