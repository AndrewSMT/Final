<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="w3.css">
    <meta charset="UTF-8">
    <title>OnlinePayment</title>
</head>
<body>
<!-- header -->
<div>
    <h1>Users list</h1>
</div>
<!-- content -->
<div>
    <%
        List names = (List) request.getAttribute("userNames");
        if (names != null && !names.isEmpty()) {
            out.println("");
            for (Object s : names) {
                out.println(" " + s + " ");
            }
            out.println("");

        } else
            out.println(request.getAttribute("temp"));%>
</div>
</body>
</html>