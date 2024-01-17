<%@ page import="java.util.List" %>
<%@ page import="entity.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.Page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>用户名单</title>
    <!-- 在 head 部分添加 Dialog 库的引用 -->
    <script src="plugins/artDialog/dist/dialog-plus.js"></script>
    <link rel="stylesheet" href="plugins/artDialog/css/dialog.css" />

    <script src="plugins/jquery-2.2.4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/users.css">

</head>
<body>
<%Page p = (Page) (request.getAttribute("page"));
    List<User> users = (ArrayList) (request.getAttribute("users"));%>
<form>
    <div class="header">
        <button type="button" onclick="add()">新增用户</button>
    </div>

    <table>
        <thead>
        <tr>
            <th>序号</th>
            <th>用户名</th>
            <th>密码</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (User user : users) {
        %>
        <tr>
            <td><%=user.getId()%></td>
            <td><%=user.getUsername()%></td>
            <td><%=user.getPassword()%></td>
            <td><%=user.getEmail()%></td>
            <td>
                <a href="#" onclick="
                    <%int idd = user.getId();%>
                        console.log(<%=idd%>);
                        $.ajax({
                        url: '/SearchId?id=<%=user.getId()%>',
                        type: 'get',
                        success:update,
                        error: function () {
                        },
                        });
                        ">更新</a>
                <a href="javascript:if (confirm('确定要删除这条记录吗？')) {
            $.ajax({
                url: '/delete?id=<%=user.getId()%>',
                type: 'get',
                success: function (data) {
                loadPage(<%=p.getPageNow()%>)
                },
                error: function () {
                }
            });
            }">删除</a>
            </td>
        </tr>
        <%}%>
        共<%=p.getTotalRows()%>条记录，<%=p.getPageNow()%>/<%=p.getTotalPage()%>
        <a href="#" onclick="
                $.ajax({
                url : '/paging?pageNow=<%=p.getPageNow() - 1%>',
                type:'GET',
                success:function (data){
                $('#content').html(data);
                },
                error:function (){
                alert('Fail to load page.');
                }
                });
                return false;">上一页</a>&nbsp;&nbsp;

        <a href="#" onclick="
                $.ajax({
                url : '/paging?pageNow=<%=p.getPageNow() + 1%>',
                type:'GET',
                success:function (data){
                $('#content').html(data);
                },
                error:function (){
                alert('Fail to load page.');
                }
                });
                return false;">下一页</a>

        </tbody>
    </table>
</form>
<script>
    function add() {
        var d = dialog({
            title: '新增用户',
            width: 200,
            height: 200,
            content: `
            用户名：<input type="text" id="username1"><br>
            密码：<input type="password" id="password1"><br>
            邮箱：<input type="text" id="email1"><br>
            `,
            cancelValue: '取消',
            cancel: true,
            okValue: '提交',
            ok: function () {
                $.ajax({
                    url: '/UserAddServlet',
                    type: 'post',
                    data: {
                        username: $('#username1').val(),
                        password: $('#password1').val(),
                        email: $('#email1').val()
                    },
                    success: function (data) {
                        alert('提交成功');
                        loadPage(<%=p.getPageNow()%>)
                    },
                    error: function () {
                        alert('提交失败');
                    },
                });
            }
        });
        d.showModal();
    }

    function update(data) {
        console.log(data);
        console.log("before dialog");
        var d = dialog({
            title: '更新用户',
            width: 200,
            height: 200,
            content: data,
            cancelValue: '取消',
            cancel: true,
            okValue: '提交',
            ok: function () {
                $.ajax({
                    url: '/UpdateUserServlet?id='+$('#idd').val(),
                    type: 'post',
                    data: {
                        username: $('#username2').val(),
                        password: $('#password2').val(),
                        email: $('#email2').val()
                    },
                    success: function (data) {
                        alert('提交成功');
                        loadPage(<%=p.getPageNow()%>)
                    },
                    error: function () {
                        alert('提交失败');
                    },
                });
            }
        });

        console.log("after dialog");
        d.showModal();
        console.log("dialog.showModal");
    }

    function loadPage(pageNow){
        $.ajax({
            url:'/paging?pageNow='+pageNow,
            type:'GET',
            success:function (data){
                $('#content').html(data);
            },
            error:function (){
                alert('Fail to load page.');
            }
        });
    }
</script>
</body>
</html>
