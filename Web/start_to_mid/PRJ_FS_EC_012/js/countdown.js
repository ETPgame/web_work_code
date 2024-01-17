function checkSaid() {
    // 将受限制的词句组成正则表达式,g代表全局匹配(查找所有匹配而非在找到第一个匹配后停止)，其中敏感词为【爆头】
    var filter=/爆头/g;
    // 使用document对象的getElementById方法获取表单文本框的值
    var keyword=document.getElementById("keyword").value;
    // 使用test方法检索字符串中指定的值，如果含有敏感词，就使用alert语句显示警告【该语句含有受限制的语句，系统已经过滤！】，否则就输出原话
    if (filter.test(keyword)) {
        // 显示警告
      alert("该语句含有受限制的语句，系统已经过滤！");
    } else {
        // 输出原话
      alert(keyword);
    }
}



//使用setTimeout方法设置每一秒调用一次倒计时函数，函数为【count_down()】
setTimeout("count_down()",1000);
//定义一个变量用于保存容器，变量名为【times_container】，容器id为【times_container】
var times_container=document.getElementById("times_container");
//根据天，时，分，秒的ID找到相对应的元素，id分别为【times_day】、【times_hour】、【times_minute】、【second】
var time_day=document.getElementById("times_day");
var time_hour=document.getElementById("times_hour");
var time_minute=document.getElementById("times_minute");
var time_second=document.getElementById("second");
// 创建一个Data对象用于保存活动结束结束时间，对象名为【time_end】，结束时间为【2016/01/01 00:00:00】
var time_end=new Date("2024/01/01 00:00:00")
// 使用Date对象的getTime方法返回毫秒数，返回值赋给对象【time_end】
var time_end_ms=time_end.getTime();
//定义倒计时函数，函数名为【count_down】
function count_down() {
    // 创建Date对象获取当前时间，对象名为【time_now】
  var time_now=new Date();
    // 使用Date对象的getTime方法返回毫秒数，返回值赋给对象【time_now】
  var time_now_ms=time_now.getTime();
    // 定义一个变量用于保存时间差，变量名为【time_distance】，算法为：时间差=活动结束时间减去当前时间
  var time_distance=time_end_ms-time_now_ms;
    // 定义四个变量分别保存天，时，分，秒,变量名分别为【int_day】、【int_hour】、【int_minute】、【int_second】
  var int_day;
  var int_hour;
  var int_minute;
  var int_second;
    // 如果时间差大于或者等于0
  if (time_distance>0) {
      // 相减的差数换算成天数，算法为：天数=Math.floor（时间差/86400000）
    int_day=Math.floor(time_distance/86400000);
      // 更新时间差，算法为：时间差=时间差-天数*86400000
    time_distance -= int_day*86400000;
      // 相减的差数换算成小时，算法为：小时=Math.floor(时间差 / 3600000)
    int_hour=Math.floor(time_distance/3600000);
      // 更新时间差，算法为：时间差=时间差-小时*3600000
    time_distance-=int_hour*3600000;
      // 相减的差数换算成分钟，算法为：Math.floor(时间差 / 60000)
    int_minute=Math.floor(time_distance/60000);
      // 更新时间差：算法为：时间差=时间差-分钟*60000
    time_distance-=int_minute*60000;
      // 相减的差数换算成秒数，算法为：Math.floor(时间差 / 1000)
    int_second=Math.floor(time_distance/1000);
      // 判断小时小于10时，前面加0进行占位
    if (int_hour<10){
      int_hour="0"+int_hour;
    }
      // 判断分钟小于10时，前面加0进行占位
    if (int_minute<10){
      int_minute="0"+int_minute;
    }
      // 判断秒数小于10时，前面加0进行占位
    if (int_second<10){
      int_second="0"+int_second;
    }
      // 将倒计时效果显示在页面上
      time_day.innerHTML = int_day;
      time_hour.innerHTML = int_hour;
      time_minute.innerHTML = int_minute;
      time_second.innerHTML = int_second;
      //使用setTimeout方法设置每一秒调用一次倒计时函数，函数为【count_down()】
      setTimeout("count_down()", 1000);
  } else {
      //活动结束的提示语句，语句为【对不起，活动结束，倒计时完毕!】
    times_container.innerHTML="对不起，活动结束，倒计时完毕!";
  }
}
//调用函数【count_down】
count_down();
