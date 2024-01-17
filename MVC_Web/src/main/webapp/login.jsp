<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录界面</title>
    <link rel="stylesheet" href="css/login.css">

</head>
<body>


<%
    String username="";
    String password="";
    Cookie[] cookies=request.getCookies();
    if(cookies!=null && cookies.length>0){
        for (int i=0;i<cookies.length;i++){
            if("username".equals(cookies[i].getName())){
                username=cookies[i].getValue();
            }
            else if("password".equals(cookies[i].getName())){
                password=cookies[i].getValue();
            }
        }
    }
%>

<form action="loginServlet" method="post">
    <div id="contents" class="contents">用户登录</div>
    <table>
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username" id="username"></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="password" id="password"></td>
            <td style="color: red"><%=request.getAttribute("loginError")== null ? "":request.getAttribute("loginError")%></td>
        </tr>
        <tr>
            <td>验证码:</td>
            <td><input type="text" name="inputCode" id="inputCode"></td>
            <td style="color: red"><%=request.getAttribute("checkCodeError")== null ? "":request.getAttribute("checkCodeError")%></td>
        </tr>
        <tr>
            <td colspan="2"><img src="/checkCodeServlet"><button onclick="refreshCaptcha()">刷新验证码</button></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"></td>
            <td><a href="register.jsp">注册</a></td>
        </tr>
    </table>
</form>

<script>
    function refreshCaptcha() {
        // 获取验证码图片元素
        var captchaImg = document.getElementById('captchaImg');

        // 修改验证码图片的src属性，添加一个随机参数来确保每次都是新的请求
        captchaImg.src = '/checkCodeServlet?' + new Date().getTime();
    }
</script>

</body>
</html>
