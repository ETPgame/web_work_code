<%@ page import="entity.User" %>
<%@ page import="entity.Email" %>
<%@ page import="java.util.List" %>
<%@ page import="Service.UserService" %>
<%@ page import="Service.UserServiceImplements" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- 在 head 部分添加 Dialog 库的引用 -->
    <script src="plugins/artDialog/dist/dialog-plus.js"></script>
    <script src="plugins/artDialog/dist/dialog.js"></script>
    <link rel="stylesheet" href="plugins/artDialog/css/dialog.css" />

    <script src="plugins/jquery-2.2.4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/email.css">
    <title>邮件来回界面</title>

</head>
<body>

<form>
    <div class="header">
        <button type="button" onclick="send()">发送邮件</button>
    </div>

    <table>
        <thead>
        <tr>
            <th>发件人</th>
            <th>收件人</th>
            <th>主题</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Email> emails= (List<Email>) request.getAttribute("emails");
            UserService userService=new UserServiceImplements();
            for (Email email:emails) {
        %>
        <tr>
            <td><%=userService.findUserById(Integer.parseInt((email.getSender()))).getUsername()%></td>
            <td><%=userService.findUserById(Integer.parseInt((email.getRecipient()))).getUsername()%></td>
            <td><%=email.getSubject()%></td>
            <td><a href="#" onclick="alert('<%=email.getContent()%>')">查看</a></td>
        </tr>
        <%}%>
        </tbody>
    </table>
</form>
</body>
</html>
