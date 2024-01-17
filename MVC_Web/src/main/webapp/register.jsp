<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <!-- Include jQuery library -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/bootstrap/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/bootstrap/bootstrap.min.js">
    <script src="JS/city.js"></script>
    <link rel="stylesheet" href="css/login.css">
    <script src="JS/register.js" type="text/javascript"></script>

</head>
<body>
<form action="registerServlet" method="post">
    <div id="contents" class="contents">用户注册</div>
    <table>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="username" id="uname">
                <span id="unameTips"></span></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password" id="upwd">
                <span id="upwdTips"></span></td>
        </tr>
        <tr>
            <td>邮箱：</td>
            <td><input type="text" name="email" id="uemail">
                <span id="emailTips"></span></td>
        </tr>
        <tr>
            <td>省：</td>
            <td>
                <select id="province" name="province"></select>
            </td>
        </tr>
        <tr>
            <td>市：</td>
            <td>
                <select id="city" name="city"></select>
            </td>
        </tr>
        <tr>
            <td>区：</td>
            <td>
                <select id="district" name="district"></select>
            </td>

        <tr>
            <td><input type="submit" value="注册"></td>
        </tr>
    </table>
</form>
</body>
</html>
