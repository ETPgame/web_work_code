var index;
$(function(){
    var ele;
    var htmls= '';
    htmls += '<div   class="comappend" style="display: none;  " >';
    htmls += '<div class="JS_closeBox comform combd" style="display: block;">';
    htmls += ' <i  class="iconcom iconcom-boxdir"></i>';
    htmls += '<i class="JS_close iconcom iconcom-close"></i>';
    htmls += '<form id="saveCommentForm" method="POST" enctype="multipart/form-data">';
    htmls += '<div class="comform_a">';
    htmls += '<div class="comform_a_left">';
    htmls += '<ul class="clearfix">';
    htmls += '<li>';
    htmls += '<span class="comcount"><em>景点</em> &nbsp;&#40; &nbsp;&nbsp;&nbsp;&nbsp;&#41;  </span>';
    htmls += '<div class="box-body">';
    htmls += '<select class="example-f" name="rating">';
    htmls += '<option value="1">1</option>';
    htmls += '<option value="2">2</option>';
    htmls += ' <option value="3">3</option>';
    htmls += '<option value="4">4</option>';
    htmls += '<option value="5">5</option>';
    htmls += '</select>';
    htmls += '</div>';
    htmls += '</li>';
    htmls += '<li>';
    htmls += '<span class="comcount"><em>酒店</em> &nbsp;&#40; &nbsp;&nbsp;&nbsp;&nbsp;&#41; </span>';
    htmls += '<div class="box-body">';
    htmls += '<select class="example-f" name="rating">';
    htmls += '<option value="1">1</option>';
    htmls += '<option value="2">2</option>';
    htmls += '<option value="3">3</option>';
    htmls += '<option value="4">4</option>';
    htmls += '<option value="5">5</option>';
    htmls += '</select>';
    htmls += '</div>';
    htmls += '</li>';
    htmls += '<li>';
    htmls += '<span class="comcount"><em>服务</em> &nbsp;&#40; &nbsp;&nbsp;&nbsp;&nbsp;&#41; </span>';
    htmls += '<div class="box-body">';
    htmls += '<select class="example-f" name="rating">';
    htmls += '<option value="1">1</option>';
    htmls += '<option value="2">2</option>';
    htmls += '<option value="3">3</option>';
    htmls += '<option value="4">4</option>';
    htmls += '<option value="5" checked>5</option>';
    htmls += '</select>';
    htmls += '</div>';
    htmls += '</li>';
    htmls += '<li>';
    htmls += '<span class="comcount"><em>交通</em> &nbsp;&#40; &nbsp;&nbsp;&nbsp;&nbsp;&#41; </span>';
    htmls += '<div class="box-body">';
    htmls += '<select class="example-f" name="rating">';
    htmls += '<option value="1">1</option>';
    htmls += '<option value="2">2</option>';
    htmls += '<option value="3">3</option>';
    htmls += '<option value="4">4</option>';
    htmls += '<option value="5">5</option>';
    htmls += '</select>';
    htmls += '</div>';
    htmls += '</li>';
    htmls += '</ul>';
    htmls += '</div>';
    htmls += '<div class="comform_a_right">';
    htmls += '<span>';
    htmls += '精华秘籍：超200字+美图+原创内容丰富实用，满足以上即有机会被设为精华哦！';
    htmls += '</span>';
    htmls += '<textarea name="content" id="content"  maxlength="100"  onfocus="if(value==\'旅途中的喜闻乐见，美景美食美人，都记录下来吧~\') {value=\'\'}"   onblur="if (value ==\'\'){value=\'旅途中的喜闻乐见，美景美食美人，都记录下来吧~\'}">旅途中的喜闻乐见，美景美食美人，都记录下来吧~</textarea>';
    htmls += '<div class="comcontent-info clearfix">';
    htmls += '<span>不知如何写？看看示例: <a href="javascript:void(0);" class="JS_opendemo">示例</a></span>';
    htmls += '<span class="fr">输入20-1000字</span>';
    htmls += '</div>';
    htmls += '</div>';
    htmls += '</div>';
    htmls += '<div class="comform_b">';
    htmls += '<div id="demo" class="demo" style="width: 780px; min-height:64px;">';
    htmls += '</div>';
    htmls += '</div>';
    htmls += '<div class="comform_c">';
    htmls += '<em>*图片大小10M以下，支持上传10张，格式支持：png，jpeg</em>';
    htmls += '<div class="status_bar comform-subbox fr clearfix">';
    htmls += '<div class="btns">';
    htmls += '<input id="fileImage" type="file" size="30" name="fileselect[]">';
    htmls += '<input type="submit" onclick="return false;"   value="开始上传" class="JS_inset comcon-submit fr upload_btn">';
    htmls += '</div>';
    htmls += '</div>';
    htmls += '</div>';
    htmls += '</form>';
    htmls += '</div>';
    htmls += '</div>';


    $(".icon_writeBtn").click(function(){   //点击待点评图标

        index = $(this).index(".icon_writeBtn");   //获取当前图标索引

        if($(this).parent().parent().parent().siblings().children().hasClass("comappend")){   //判断每个待点评表单是否已经打开点评下拉框
            $(".comappend").remove();
            $('.comappend').animate({height:'hide'});
        }
        if(!$(".comstati").children().hasClass("comappend")){

            $(".comstati").eq(index).append(htmls);

            var oHead = document.getElementsByTagName('HEAD').item(0);

            var oScript= document.createElement("script");

            oScript.type = "text/javascript";
            oScript.src="js/examples.js";
            oHead.appendChild( oScript);

            var oScript1= document.createElement("script");

            oScript1.type = "text/javascript";
            oScript1.src="js/jquery.barrating.js";
            oHead.appendChild( oScript1);

            var oScript2= document.createElement("script");

            oScript2.type = "text/javascript";
            oScript2.src="js/zyFile.js";
            oHead.appendChild( oScript2);

            var oScript3= document.createElement("script");

            oScript3.type = "text/javascript";
            oScript3.src="js/zyUpload.js";
            oHead.appendChild( oScript3);

            var oScript4= document.createElement("script");

            oScript4.type = "text/javascript";
            oScript4.src="js/upFile.js";
            oHead.appendChild( oScript4);

            $('.comappend').animate({height:'show'});

        }else{
            $(".comappend").remove();
            $('.comappend').animate({height:'hide'});
        }

    });
});

//关闭图片上传下拉框
$(document).on("click",".iconcom-close",function(){
    $(".comappend").remove();
    $('.comappend').animate({height:'hide'});
});








//显示上传图片 开始
$(".stop").click(function(){
    $(".DB_imgSet").fadeOut(1000);
    $(".stop").fadeOut();

});

$(".DB_thumMove").click(function(){
    $(this).parent().parent().next().fadeIn();

});


//显示上传图片 结束
