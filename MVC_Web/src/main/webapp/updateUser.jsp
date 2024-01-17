<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>更新用户</title>
</head>
<body>

<%User user=(User) (request.getAttribute("user"));%>
<form action="/updateUserServlet" method="post">
    <input type="hidden" name="id" value="<%=user.getId()%>"/>
    <table>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="username" value="<%=user.getUsername()%>"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password" value="<%=user.getPassword()%>"></td>
        </tr>
        <tr>
            <td>邮箱：</td>
            <td><input type="text" name="email" value="<%=user.getEmail()%>"></td>
        </tr>
        <tr>
            <td><input type="submit" value="更新"></td>
        </tr>
    </table>
</form>
</body>
</html>
