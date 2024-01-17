// JavaScript Document
$(document).ready(function(){

    ////<!--  点击个人资料 开始   -->

    $("#per_de").css("background","white");
    $("#per_de").css("color","#000");
    $("#per_de").css("font-weight","900");
	
    //个人资料
    $("#per_de").click(function(){
        $(this).css("color","#000");
        $("#per_de").css("background","white");
        $("#per_de").css("color","#000");
        $("#per_de").css("font-weight","900");
        $("#my_head").css("color","#fefefe");
        $("#per_de").addClass('content_right_li_bg_c');
        $("#my_head").removeClass('content_right_li_bg_c');
        $(".per_detail_content").show();
        $(".head_detail_content").hide();
    });

    //我的头像
    $("#my_head").click(function(){
       $("#per_de").css("color","#fefefe");
        $(this).css("color","#000");
        $("#my_head").css("font-weight","900");
        $("#per_de").css("background","#d8619b");
        $("#per_de").css("color","white");
        $("#per_de").removeClass('content_right_li_bg_c');
        $("#my_head").addClass('content_right_li_bg_c');
        $(".per_detail_content").hide();
        $(".head_detail_content").show();
    });
    ////<!--  点击个人资料 结束   -->



    ///*密码修改 开始*/
    //$("#oldPassWord").focus(function(){
    //    if ($("#oldPassWord").val() == "" ) {
    //        $("#passWordMsg").css("display", "inline-block");
    //    }
    //}).blur(function(){
    //    $("#passWordMsg").css("display","none");
    //});
    //
    //$("#newPassWord").focus(function(){
    //    if ($("#newPassWord").val() == "" ) {
    //        $("#newWordMsg").css("display", "inline-block");
    //    }
    //}).blur(function(){
    //    $("#newWordMsg").css("display","none");
    //});
    //
    //
    //$("#newPassWordt").focus(function(){
    //    if ($("#newPassWordt").val() == "" ) {
    //        $("#newWordMsgt").css("display", "inline-block");
    //    }
    //}).blur(function(){
    //    $("#newWordMsgt").css("display","none");
    //});
    ///*密码修改 结束*/





    //<!--  城市下拉框 开始   -->
    var currentShowCity=0;
    $("#province").change(function(){
        $("#province option").each(function(i,o){
            if($(this).attr("selected"))
            {
                $(".city").hide();
                $(".city").eq(i).show();
                currentShowCity=i;
            }
        });
    });
    $("#province").change();
    //<!--  城市下拉框 结束   -->



    //<!--  我的点评 开始   -->
    $("#to_comment").css("background-color","white");
    $("#to_comment").css("color","#000");
    $("#to_comment").css("font-weight","900");

    $("#to_comment").click(function(){
        $(".to_comment_detail").show();
        $(".comments_detail").hide();
        $("#to_comment").css("background-color","white");
        $("#to_comment").css("color","#000");
        $("#to_comment").css("font-weight","900");
        $("#comments").css("background-color","#d8619b");
        $("#comments").css("color","white");
    });

    $("#comments").click(function(){
        $(".comments_detail").show();
        $(".to_comment_detail").hide();

        $("#comments").css("background-color","white");
        $("#comments").css("color","#000");
        $("#comments").css("font-weight","900");
        $("#to_comment").css("background-color","#d8619b");
        $("#to_comment").css("color","white");

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
    //<!--  我的点评 结束   -->





    //<!--  点击常用联系信息  开始  -->
        //点击新增常用联系人
            $(".add_often_tourist").click(function(){
                $(".tourist").show();
            });

            ////点击新增常用联系人中的修改
            //$(".modify").click(function(){
            //    $(".tourist").show();
            //});


            ////点击新增常用联系人中 关闭删除对话框
            //$("#close").click(function(){
            //    $(".del_dialog").hide();
            //});

            //点击新增常用联系人中的取消
            //$(".del_btn_cancel").click(function(){
            //    $(".del_dialog").hide();
            //});

            ////点击新增常用联系人中的删除
            //$(".delete").click(function(){
            //    $(".del_dialog").show();
            //});

            //点击新增常用联系人中的确认删除
            //$(".del_btn_ok").click(function(){
            //    $(".del_dialog").hide();
            //});
    //<!--  点击常用联系信息  结束  -->

});

function endRTime(){
    var EndTime= new Date('2016/5/1 10:00:00'); //截止时间
    var NowTime = new Date();
    var t =EndTime.getTime() - NowTime.getTime();
    if(t<=0){
        document.getElementById("t_d").innerHTML = "已过期";
        document.getElementById("t_h").innerHTML = "";
        document.getElementById("t_m").innerHTML = "";
        document.getElementById("t_s").innerHTML = "";
    }else{
        setTimeout("alert(1234)",500);
    }
}



//<!--场景  PRJ-WTP-WEB-027  我的订单定时弹窗【2/2 START】-->
function startClock(){
    var EndTime= new Date('2016/5/1 10:00:00'); //截止时间
    var NowTime = new Date();
    var t =EndTime.getTime() - NowTime.getTime();
    if(t>0){
        var colorText = document.getElementById("clock1");
        colorText.innerHTML="提醒已开";

        var times="我是小助手乐乐，每5分钟提醒一次。距您旅游时间又进了一步啦。";
        alert(times);
        setTimeout("startClock()",1000*60*5);
    }
}
//<!--场景  PRJ-WTP-WEB-027  我的订单定时弹窗【2/2 START】-->




//<!--场景  PRJ-WTP-WEB-028 我的订单倒计时 【2/2 START】-->
function getRTime(){
    var EndTime= new Date('2016/5/1 10:00:00'); //截止时间
    var NowTime = new Date();
    var t =EndTime.getTime() - NowTime.getTime();
    if(t<=0){
        document.getElementById("t_d").innerHTML = "已过期";
        document.getElementById("t_h").innerHTML = "";
        document.getElementById("t_m").innerHTML = "";
        document.getElementById("t_s").innerHTML = "";
    }else{
        var d=Math.floor(t/1000/60/60/24);
        var h=Math.floor(t/1000/60/60%24);
        var m=Math.floor(t/1000/60%60);
        var s=Math.floor(t/1000%60);

        document.getElementById("t_d").innerHTML = d + "天  ";
        document.getElementById("t_h").innerHTML = h + "时  ";
        document.getElementById("t_m").innerHTML = m + "分  ";
        document.getElementById("t_s").innerHTML = s + "秒  ";

    }

}
setInterval(getRTime,1000);


//<!--场景  PRJ-WTP-WEB-028 我的订单倒计时 【2/2 START】-->






//<!--场景 PRJ-WTP-WEB-022   个人信息修改姓名验证  昵昵称文本框验证 【2/2  START 】-->
//javascript 验证昵称
function checkNames() {
    var myname = document.getElementById("myname").value;
    var myname_msg = document.getElementById("myname_msg");
    if (myname == ''|| myname<=0) {
        myname_msg.innerHTML='昵称不能为空！';
        myname_msg.style.color  = 'red';
        return false;
    }

    myname_msg.innerHTML='昵称输入正确！';
    myname_msg.style.color  = '#34c518';
    return true;
}
//<!--场景 PRJ-WTP-WEB-022   个人信息修改姓名验证  昵昵称文本框验证 【2/2  END 】-->



/*密码修改 开始*/
$("#oldPassWord").focus(function(){
    if ($("#oldPassWord").val() == "" ) {
        $("#passWordMsg").css("display", "inline-block");
    }
}).blur(function(){
    $("#passWordMsg").css("display","none");
});

$("#newPassWord").focus(function(){
    if ($("#newPassWord").val() == "" ) {
        $("#newWordMsg").css("display", "inline-block");
    }
}).blur(function(){
    $("#newWordMsg").css("display","none");
});


$("#newPassWordt").focus(function(){
    if ($("#newPassWordt").val() == "" ) {
        $("#newWordMsgt").css("display", "inline-block");
    }
}).blur(function(){
    $("#newWordMsgt").css("display","none");
});
/*密码修改 结束*/









//<!--场景 PRJ-WTP-WEB-024 个人信息修改  验证手机号checkMobile函数 【2/4  START】-->
//js验证手机号码
function checkMobile() {
    var mobile = document.getElementById("mobile").value;
    var mobile_tip = document.getElementById("mobile_tip");
    document.getElementById("mobile").style.background="#fff";
    if (mobile == ''|| mobile<=0) {
        mobile_tip.innerHTML='手机号码不能为空！';
        mobile_tip.style.color  = 'red';
        return false;
    }
    mobile_tip.innerHTML='手机号码输入正确！';
    mobile_tip.style.color  = '#34c518';
    return true;
}
//<!--场景 PRJ-WTP-WEB-024 个人信息修改  验证手机号checkMobile函数 【2/4  END】-->



//<!--场景 PRJ-WTP-WEB-024 个人信息修改  mouseOver函数 【4/4 END】-->
function mouseOver(){
    document.getElementById("save_button").style.backgroundColor="#d8619b";
}


function mouseOut(){
    document.getElementById("save_button").style.backgroundColor="#f48c29";
}
//<!--场景 PRJ-WTP-WEB-024 个人信息修改  mouseOver函数 【4/4 END】-->


//jquery验证手机号码
//function checkMobile() {

//   if ($("#mobile").val() == "") {
//       $("#mobile_tip").html("手机号码不能为空！");
//        $("#mobile_tip").css('color','red');
//        return false;
//    }

//    if (!$("#mobile").val().match(/^(\d{11})$/)) {
//       $("#mobile_tip").html("手机号码格式不正确！");
//       $("#mobile_tip").css('color','red');
//       return false;
//    }
//    $("#mobile_tip").css('color','#34c518');
//    $("#mobile_tip").html("手机号码输入正确！");
//    return true;
//}


//验证邮箱
function checkEmail() {
    document.getElementById("email").style.background="#fff";
    if ($("#email").val() == "") {
        $("#email_msg").html("邮箱地址不能为空！");
        $("#email_msg").css('color','red');
        return false;
    }
    if (!$("#email").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)) {
        $("#email_msg").html("邮箱格式不正确！");
        $("#email_msg").css('color','red');
        return false;
    }
    $("#email_msg").html("邮箱输入正确！");
    $("#email_msg").css('color','#34c518');
    return true;
}


//验证真实姓名
function checkName() {
    document.getElementById("realname").style.background="#fff";
    if ($("#realname").val() == "" || $.trim($("#realname").val()).length <= 0) {
        $("#realname_msg").html("姓名不能为空！");
        $("#realname_msg").css('color','red');
        return false;
    }

    //姓名必须是两位到二十位以内的中主或两位到二十位的英文
    if (!$("#realname").val().match(/^[\u4e00-\u9fa5 ]{2,20}$/)) {
        if (!$("#realname").val().match(/^([A-Za-z]+\s?)*[A-Za-z]{2,20}$/)) {
            $("#realname_msg").html("姓名格式不正确！");
            $("#realname_msg").css('color','red');
            return false;
        }
    }

    $("#realname_msg").html("真实姓名输入正确！");
    $("#realname_msg").css('color','#34c518');
    return true;
}



////jquery 验证昵称
//function checkNames() {
//    if ($("#myname").val() == "" || $.trim($("#myname").val()).length <= 0) {
//        $("#myname_msg").html("昵称不能为空！");
//        $("#myname_msg").css('color','red');
//        return false;
//    }
//
//    //姓名必须是两位到二十位以内的中主或两位到二十位的英文
//        if (!$("#realname").val().match(/^[\u4e00-\u9fa5 ]{2,20}$/)) {
//            if (!$("#realname").val().match(/^([A-Za-z]+\s?)*[A-Za-z]{2,20}$/)) {
//                $("#realname_msg").html("姓名格式不正确！");
//                $("#realname_msg").css('color','red');
//                return false;
//            }
//        }
//
//    $("#myname_msg").html("昵称输入正确！");
//    $("#myname_msg").css('color','#34c518');
//    return true;
//}



//验证身份证号
function checkCardId() {
    document.getElementById("cardId").style.background="#fff";
    if ($("#cardId").val() == "") {
        $("#cardIdMsg").html("身份证号不能为空！");
        $("#cardIdMsg").css('color','red');
        return false;
    }


    //身份证必须是十五位或18位或17位加一个字母
    if (!$("#cardId").val().match(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/)) {
            $("#cardIdMsg").html("身份证号格式不正确！");
            $("#cardIdMsg").css('color','red');
        return false;
    }

    $("#cardIdMsg").html("身份证号输入正确！");
    $("#cardIdMsg").css('color','#34c518');
    return true;
}




function checkSelect() {

    if ($("#province").val() == "" ) {
        $("#city_msg").html("请选择省份");
        $("#city_msg").css('color','red');
        return false;
    }

  if($("#select_city").val() == ""){
      $("#city_msg").html("请先选择省份");
      $("#city_msg").css('color', 'red');
  }



    $("#city_msg").html("城市选择成功");
    $("#city_msg").css('color','#34c518');
    return true;
}

function checkSelectCity(){
    if ($("#province").val() == "" ) {
        $("#city_msg").html("请先选择省份");
        $("#city_msg").css('color', 'red');
    }
}

//<!--场景 PRJ-WTP-WEB-023   个人密码修改    调用checkOldPassWord验证旧密码 【2/6  START】-->
function setInputStyle(x){
    document.getElementById(x).style.background="#FFFFDF";
}
//<!--场景 PRJ-WTP-WEB-023   个人密码修改    调用checkOldPassWord验证旧密码 【2/6  END】-->

//javascript验证旧密码

function checkOldPassWord(){
    document.getElementById("oldPassWord").style.background="#fff";
    var oldPassWord=document.getElementById("oldPassWord").value;
    var passWordMsg=document.getElementById("passWordMsg");
    if (oldPassWord=="" ) {
        passWordMsg.innerHTML='请输入新密码！';
        passWordMsg.style.color  = 'red';
        return false;
    }
    passWordMsg.innerHTML='旧密码输入正确！';
    passWordMsg.style.color  = '#34c518';
    return true;
}


//<!--场景 PRJ-WTP-WEB-023   个人密码修改    调用checkOldPassWord验证旧密码 【4 START】-->
//javascript验证新密码
function checkNewPassWord(){
    document.getElementById("newPassWord").style.background="#fff";
    var newPassWord=document.getElementById("newPassWord").value;
    var newWordMsg=document.getElementById("newWordMsg");
    if (newPassWord=="" ) {
        newWordMsg.innerHTML='请输入新密码！';
        newWordMsg.style.color  = 'red';
        return false;
    }
    newWordMsg.innerHTML='输入新密码正确！';
    newWordMsg.style.color  = '#34c518';
    return true;
}
//<!--场景 PRJ-WTP-WEB-023   个人密码修改   调用checkOldPassWord验证旧密码 【4  END】-->



//javascript验证新密码是否相同
function checkNewPassWordt(){
    document.getElementById("newPassWordt").style.background="#fff";
    var newPassWordt=document.getElementById("newPassWordt").value;
    var newPassWord=document.getElementById("newPassWord").value;
    var newWordMsgt=document.getElementById("newWordMsgt");
    if (newPassWordt=="" ) {
        newWordMsgt.innerHTML='请再次输入新密码！';
        newWordMsgt.style.color  = 'red';
        return false;
    }
    if (newPassWord != newPassWordt ) {
        newWordMsgt.innerHTML='两次新密码不同，重新输入！';
        newWordMsgt.style.color  = 'red';
        return false;
    }
    newWordMsgt.innerHTML='再次输入新密码正确！';
    newWordMsgt.style.color  = '#34c518';
    return true;
}

//<!--场景 PRJ-WTP-WEB-023   个人密码修改    checkPassWord函数对表单验证 【6/6     START】-->
function checkPassWord(){
    checkOldPassWord();
    checkNewPassWord();
    checkNewPassWordt();
    var form=document.getElementById("form");
    form.action("");
    form.submit();
}
//<!--场景 PRJ-WTP-WEB-023   个人密码修改   checkPassWord函数对表单验证 【6/6    START】-->



//jquery验证密码
//function checkPassWord(){
//    if ($("#oldPassWord").val() =="" ) {
//        $("#passWordMsg").css("display", "inline-block");
//        $("#passWordMsg").html("请输入旧密码");
//        return false;
//    }
//    if ($("#newPassWord").val() == ""  ) {
//        $("#newWordMsg").css("display", "inline-block");
//        $("#newWordMsg").html("请输入新密码");
//        return false;
//    }
//    if ($("#newPassWordt").val() == "" ) {
//        $("#newWordMsgt").css("display", "inline-block");
//        $("#newWordMsgt").html("请再次输入新密码");
//        return false;
//    }
//    if (($("#newPassWord").val() != "" &&  $("#newPassWordt").val() == "" )|| ($("#newPassWord").val() == "" &&  $("#newPassWordt").val() != "" )
//        || ($("#newPassWord").val() == " " &&  $("#newPassWordt").val() == " " ) ) {
//        $("#newWordMsg").css("display", "inline-block");
//        $("#newWordMsg").html("请输入新密码");
//        return false;
//    }
//
//    if ($("#newPassWord").val() != $("#newPassWordt").val() ) {
//        $("#newWordMsg").css("display", "inline-block");
//        $("#newWordMsg").html("两次新密码不同，请重新输入");
//        return false;
//    }
//
//    $("#newWordMsg").css("display", "inline-block");
//    $("#newWordMsg").html("两次新密码相同，输入正确");
//    $("#form").submit();
//    return true;
//}



//常用联系信息验证中文名
function checkCNName(){
	 if ($("#cnName").val() == "" || $.trim($("#cnName").val()).length <= 0) {
        $("#cnNameMsg").html("姓名不能为空！");
        $("#cnNameMsg").css('color','red');
        return false;
    }

    //姓名必须是两位到二十位以内的中主或两位到二十位的英文
    if (!$("#cnName").val().match(/^[\u4e00-\u9fa5 ]{2,20}$/)) {
            $("#cnNameMsg").html("姓名格式不正确！");
            $("#cnNameMsg").css('color','red');
            return false;
    }

    $("#cnNameMsg").html("姓名输入成功！");
    $("#cnNameMsg").css('color','#34c518');
    return true;
}


//常用联系信息验证手机号
function checkTel(){
	
	if ($("#phone").val() == "") {
        $("#phoneMsg").html("手机号码不能为空！");
        $("#phoneMsg").css('color','red');
        return false;
    }

    if (!$("#phone").val().match(/^(\d{11})$/)) {
        $("#phoneMsg").html("手机号码格式不正确！");
        $("#phoneMsg").css('color','red');
        return false;
    }
	
	$("#phoneMsg").css('color','#34c518');
    $("#phoneMsg").html("手机号码输入正确！");
    return true;
	
}

//常用联系信息中验证邮箱
function checkEmails() {
    if ($("#emails").val() == "") {
        $("#emailsMsg").html("邮箱地址不能为空！");
        $("#emailsMsg").css('color','red');
        return false;
    }
    if (!$("#emails").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)) {
        $("#emailsMsg").html("邮箱格式不正确！");
        $("#emailsMsg").css('color','red');
        return false;
    }
    $("#emailsMsg").html("邮箱输入正确！");
    $("#emailsMsg").css('color','#34c518');
    return true;
}

function checkUsuallyT(){
  

    if ($("#cnName").val() == "" || $.trim($("#cnName").val()).length <= 0 ) {
        $("#cnNameMsg").css("color", "red");
        $("#cnNameMsg").html("中文姓名不能为空");
        $(".tourist").show();
        return false;
    }

    if ($("#phone").val() == "" || $.trim($("#phone").val()).length <= 0 ) {
        $("#phoneMsg").css("color", "red");
        $("#phoneMsg").html("手机号不能为空");
        $(".tourist").show();
        return false;
    }

    if ($("#emails").val() == "" || $.trim($("#emails").val()).length <= 0 ) {
        $("#emailsMsg").css("color", "red");
        $("#emailsMsg").html("邮箱不能为空");
        $(".tourist").show();
        return false;
    }

    $(".tourist").hide();
    return true;
}



//关闭弹出层函数（通用JS）
function close() {

    $("#del_dialog").hide();
    return true;
}




//验证身份证号
function checkCardIds() {
    if ($("#cardIds").val() == "") {
        $("#cardIdsMsg").html("身份证号不能为空！");
        $("#cardIdsMsg").css('color','red');
        return false;
    }


    //身份证必须是十五位或18位或17位加一个字母
    if (!$("#cardIds").val().match(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/)) {
        $("#cardIdsMsg").html("身份证号格式不正确！");
        $("#cardIdsMsg").css('color','red');
        return false;
    }

    $("#cardIdsMsg").html("身份证号输入正确！");
    $("#cardIdsMsg").css('color','#34c518');
    return true;
}