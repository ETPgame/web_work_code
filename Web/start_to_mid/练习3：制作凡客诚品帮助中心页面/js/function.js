$(document).ready(function () {

  $(".search").focus(function () {
    $(this).attr({"value": ""});
  })
    .blur(function () {
    if ($("input").attr("value") == "") {
      $(this).attr({"value": "请输入要查询的问题"});
    }
  });

  $(".qiehuan input:eq(1)").click(function () {
    $(".wenti").show();
  });


  $("ul div.first").click(function (){
    $(".second").hide();
    $(this).siblings('ul').toggle();
  })

  $("#tab_bg>div").hover(function (){
    var index=$("#tab_bg>div").index(this);
    // alert("tab_bg"+index);
    $("#tab_bg").addClass("tab_bg"+index);
  }
  );


});
