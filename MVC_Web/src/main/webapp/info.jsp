<%@ page import="entity.User" %>
<%@ page import="java.util.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人信息界面</title>
    <link rel="stylesheet" href="css/info.css">
</head>
<body>
<%User user = (User) request.getAttribute("user");%>
<form method="post">
<div class="headMain">
    <div class="source_pane">
        <p>当前头像（大小：200 * 200）</p>
        <div class="img-preview">
            <div class="img-contbox">

                <%
                    byte[] pictureData = user.getPicture();
                    String dataUri;
                    if (pictureData != null) {
                        String base64Image = Base64.getEncoder().encodeToString(pictureData);
                        dataUri = "data:image/jpeg;base64," + base64Image;
                    } else {
                        // 如果用户头像数据为null，设置一个默认的dataUri或者采取其他处理方式
                        dataUri = "data:image/jpeg;base64,"; // 这里可以设置一个默认的Base64字符串
                    }
                %>
                <img src="<%=dataUri%>" id="smallPic" class="jcrop-preview"
                     alt="Preview">
            </div>
        </div>
        <div>
            <input type="file" name="image" id="imgOne" accept=".png,.jpg,.gif" onChange="preImg(this.id);"
                   style="margin-top: 10px">
        </div>
    </div>
    <div class="form">
        <td><input type="hidden" id="username_info" value="<%=user.getUsername()%>"></td>
        <td>姓名：<input type="text" id="name_info" value="<%=user.getName()%>"></td>
        <br>
        <td>年龄：<input type="text" id="age_info" value="<%=user.getAge()%>"></td>
        <br>
        <td>性别：<input type="text" id="sex_info" value="<%=user.getSex()%>"></td>
        <br>
        <td><input type="button" value="保存" style="margin-top: 10px" onclick="save()"></td>
    </div>
</div>
</form>
</body>
</html>
