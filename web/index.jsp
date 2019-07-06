<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html >
<%@ include file="/WEB-INF/jspf/pageConfig.jspf" %>
<body>
<!-- header -->
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<div id="autFor" class="w3-display-middle">    <!-- buttons holder -->
    <form  id = "form-2"  action="controller" method="get">
        <input type="hidden" name="command" value="LogPage"/>
        <button  class="w3-btn w3-green w3-border w3-border-gray w3-round-large">Authorization</button>
    </form>
    <form   id = "form-1"  action="controller" method="get">
        <input type="hidden" name="command" value="RegPage"/>
        <button  id="indexBut" class="w3-btn w3-green w3-border w3-border-gray w3-round-large"> Registration </button>
    </form>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>