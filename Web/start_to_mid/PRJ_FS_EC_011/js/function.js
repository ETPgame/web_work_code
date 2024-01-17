function clearText(){
  var x=document.getElementById("keyword");
  if (x.defaultValue==x.value) {
    x.value="";
  }
  else if (x.value=="") {
    x.value=x.defaultValue;
  }
  x.style.border="dotted";
  x.style.color="blue";
}

function setHref(){
  location.href="https://www.baidu.com/"
}

