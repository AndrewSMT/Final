<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <link rel="stylesheet" href="w3.css">
    <meta charset="UTF-8">
    <title>OnlinePayment</title>
</head>
<body>
<!-- header -->
<div>
    <h1>Авторизция</h1>
</div>
<div>
    <form method="post" class="form-1">
        <p class="field">
            <input type="text" name="login" placeholder="login">
            <i class="icon-user icon-large"></i>
        </p>
        <p class="field">
            <input type="password" name="pass" placeholder="password">
            <i class="icon-lock icon-large"></i>
        </p>
        <p class="submit">
            <button type="submit" name="submit">submit</button>
        </p>
    </form>
</div>
</body>
</html>