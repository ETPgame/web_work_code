function clearText(field) {
  if (field.defaultValue == field.value){
    field.value="";
  }
  else if (field.value == ""){
    field.value = field.defaultValue;
  }
}

function changeColor(field){
  field.style.color = "blue";
}

function getHand(field) {
  field.style.cursor = "pointer";
}
