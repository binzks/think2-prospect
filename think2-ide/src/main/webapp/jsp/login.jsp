<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${systemName}|Login</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="../css/animate.css" rel="stylesheet">
    <link href="../css/style.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <h1 class="logo-name">${systemAbbreviation}</h1>
        <h2>Welcome to ${systemName}</h2>
        <p>${systemProfile}</p>
        <form class="m-t" role="form" method="post" action="../admin/login.do">
            <div style="color: red">${errMsg}</div>
            <div class="form-group">
                <input type="text" name="username" class="form-control" placeholder="Username" required="">
            </div>
            <div class="form-group">
                <input type="password" name="password" class="form-control" placeholder="Password" required="">
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b">Login</button>
        </form>
        <p class="m-t">
            <small>${systemName} base on zb &copy; 2016-2018</small>
        </p>
    </div>
</div>

<script src="../js/jquery-2.1.1.js"></script>
<script src="../js/bootstrap.min.js"></script>

</body>

</html>
