<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<html>
<head>
    <link rel="stylesheet" href="plugins/bootstrap/bootstrap.min.css">
    <script src="plugins/jquery-2.2.4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="plugins/bootstrap/bootstrap.min.js"></script>

    <link rel="stylesheet" href="plugins/artDialog/css/dialog.css" />
    <script src="plugins/artDialog/dist/dialog-plus.js"></script>

    <link rel="stylesheet" href="css/welcome.css">

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>温大优秀管理系统</title>
</head>

<body>
<div class="container-fluid">
    <div class="jumbotron">
        <h1>温大优秀管理系统</h1>
    </div>
    <div id="welcome_message">
        <p>
            Online people : ${applicationScope.count},Welcome:
            <span id="username"><%=session.getAttribute("username")%></span>!
            <a href="quitServlet">退出登录</a>
        </p>
    </div>
    <div class="row">
        <!-- 左侧边栏 -->
        <nav class="col-md-2 d-none d-md-block sidebar">
            <div class="sidebar-sticky">
                <ul class="nav nav-tabs flex-column">

                    <li class="nav-item">
                        <a class="nav-link nav-active" href="#" onclick="showContent('section1')">欢迎</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" onclick="showContent('section2')">显示员工</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" onclick="showContent('section3')">查看邮件</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" onclick="showContent('section4')">个人信息</a>
                    </li>
                    <!-- 添加更多的部分链接 -->
                </ul>
            </div>
        </nav>

        <!-- 右侧内容区域 -->
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <div id="content">
                <!-- 这里将显示相应的内容 -->
                <img src="image/1-3.jpg" style="max-width: 100%;max-height: 80vh;height: auto;display: block;margin: auto">
            </div>
        </main>
    </div>
</div>


<script>
    $(document).ready(function() {
        // 在页面加载时检查 URL 参数并执行相应的操作
        var urlParams = new URLSearchParams(window.location.search);
        var defaultSection = urlParams.get('defaultSection');

        if (defaultSection) {
            showContent(defaultSection);
        }
    });

    function showContent(section) {
        // 根据选择的部分显示相应的内容
        const contentDiv = $('#content')[0];
        $('.nav-tabs li a').removeClass('nav-active');
        switch (section) {
            case 'section1':
                $('.nav-tabs li:nth-child(1) a').addClass('nav-active');
                contentDiv.innerHTML = '<img src="image/1-3.jpg" style="max-width: 100%;max-height: 80vh;height: auto;display: block;margin: auto">';
                break;

            case 'section2':
                $('.nav-tabs li:nth-child(2) a').addClass('nav-active');
                $.ajax({
                    url:'/paging',
                    type:'GET',
                    success:function (data){
                        contentDiv.innerHTML=data;
                    },
                    error:function (){
                        alert('fail to load page.');
                    }
                });
                break;

            case 'section3':
                $('.nav-tabs li:nth-child(3) a').addClass('nav-active');
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        contentDiv.innerHTML = xhr.responseText;
                    }
                };
                xhr.open('GET', '/findEmailServlet?username=<%=session.getAttribute("username")%>', true);
                xhr.send();
                break;

            case 'section4':
                $('.nav-tabs li:nth-child(4) a').addClass('nav-active');
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        contentDiv.innerHTML = xhr.responseText;
                    }
                };
                xhr.open('GET', '/ShowInfoServlet?username=<%=session.getAttribute("username")%>', true);
                xhr.send();
                break;

            // 添加更多的部分内容
            default:
                contentDiv.innerHTML = '<h2>Welcome</h2><p>Select a section from the left sidebar.</p>';
        }
    }

    function send() {
        var d = dialog({
            title: '发送邮件',
            width: 200,
            height: 200,
            content: `
                收件人：<input type="text" id="recipient_send"><br>
                主题：<input type="text" id="subject_send"><br>
                内容：<input type="text" id="content_send"><br>
        `,
            cancelValue: '取消',
            cancel: true,
            okValue: '发送',
            ok: function () {
                $.ajax({
                    url: '/sendEmailServlet',
                    type: 'post',
                    data: {
                        sender: '<%=session.getAttribute("username")%>',
                        recipient: $('#recipient_send').val(),
                        subject: $('#subject_send').val(),
                        content: $('#content_send').val()
                    },
                    success: function (data) {
                        alert('发送成功');
                    },
                    error: function () {
                        alert('发送失败');
                    },
                });
            }
        });
        d.showModal();
    }

    function preImg(inputId) {
        var input = document.getElementById(inputId);
        var preview = document.getElementById('smallPic');
        var file = input.files[0];

        if (file) {
            var reader = new FileReader();

            reader.onload = function (e) {
                var img = new Image();
                img.src = e.target.result;

                img.onload = function () {
                    var canvas = document.createElement('canvas');
                    var ctx = canvas.getContext('2d');
                    canvas.width = 200;
                    canvas.height = 200;
                    ctx.drawImage(img, 0, 0, 200, 200);

                    preview.src = canvas.toDataURL('image/jpeg');
                };
            };

            reader.readAsDataURL(file);
        }
    }

    function save() {
        var name = document.getElementById('name_info').value;
        var age = document.getElementById('age_info').value;
        var sex = document.getElementById('sex_info').value;
        var username=document.getElementById('username_info').value;

        // 获取图像预览的源
        var preview = document.getElementById('smallPic');
        var picture = dataURItoBlob(preview.src);

        // 创建 FormData 对象
        var formData = new FormData();
        formData.append('username', username);
        formData.append('picture', picture);
        formData.append('name', name);
        formData.append('age', age);
        formData.append('sex', sex);

        for (var [a, b] of formData.entries()) {
            console.log(a, b);
        }

        // 发送 FormData 到服务器
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/uploadImageServlet', true);
        xhr.send(formData);
        alert("保存成功")
    }

    // 将 data URI 转换为 Blob 对象
    function dataURItoBlob(dataURI) {
        var byteString = atob(dataURI.split(',')[1]);
        var ab = new ArrayBuffer(byteString.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < byteString.length; i++) {
            ia[i] = byteString.charCodeAt(i);
        }
        return new Blob([ab], {type: 'image/jpeg'});
    }
</script>
</body>
</html>
