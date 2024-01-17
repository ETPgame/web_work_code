function clock(){
  var today=new Date();
  var week={
    0:"星期日",
    1:"星期一",
    2:"星期二",
    3:"星期三",
    4:"星期四",
    5:"星期五",
    6:"星期六",
  }
  var year=today.getFullYear();
  var month=today.getMonth()+1;
  var day=today.getDate();
  var aweek=week[today.getDay()];
  var hour=today.getHours();
  var minute=today.getMinutes();
  var second=today.getSeconds();

  document.getElementById("times_container").innerHTML=
    "<h2>"+year+"年"+month+"月"+day+"日"+aweek+"</h2><h1>"+hour+":"+minute+":"+second+"</h1>"
}
var mytime=setInterval("clock()",1000);
