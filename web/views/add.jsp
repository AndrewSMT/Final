<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="w3.css">
    <meta charset="UTF-8">
    <title>My super project!</title>
</head>
<body>
<!-- header -->
<div>
    <h1>Add new user</h1>
</div>
<div>
    <form method="post" class="form-1">
        <p class="field">
            <input type="text" name="name" placeholder="login">
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
<%
    if (request.getAttribute("userName") != null) {
        out.println(" User '" + request.getAttribute("userName") + "' added! ");
    }
%>
</body>
</html>