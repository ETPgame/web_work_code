// JavaScript Document
$("#search").focus(function(e) {
	if ($("#search").val()=="目的地、主题、签证或景区名称"){
    	$("#search").val("");
	}
});
$("#search").blur(function(e) {
	if ($("#search").val()==""){
		$("#search").val("目的地、主题、签证或景区名称");
	}
});
$("#menu1").hover(function(e) {
	$(this).addClass("bgcolor");
	Show("detail1");
});
$("#menu1").mouseleave(function(e) {
	$(this).removeClass("bgcolor");
});
$("#menu_div1").mouseleave(function(e) {
	$(this).removeClass("bgcolor");
    Hide("detail1");
});
$("#menu2").hover(function(e) {
	$(this).addClass("bgcolor");
	Show("detail2");
});
$("#menu2").mouseleave(function(e) {
	$(this).removeClass("bgcolor");
});
$("#menu_div2").mouseleave(function(e) {
	$(this).removeClass("bgcolor");
    Hide("detail2");
});
$("#menu3").hover(function(e) {
	$(this).addClass("bgcolor");
	Show("detail3");
});
$("#menu3").mouseleave(function(e) {
	$(this).removeClass("bgcolor");
});
$("#menu_div3").mouseleave(function(e) {
	$(this).removeClass("bgcolor");
    Hide("detail3");
});
$("#menu4").hover(function(e) {
	$(this).addClass("bgcolor");
	Show("detail4");
});
$("#menu4").mouseleave(function(e) {
	$(this).removeClass("bgcolor");
});
$("#menu_div4").mouseleave(function(e) {
    Hide("detail4");
});

function Show(e1){
	document.getElementById(e1).style.display="block";
}
function Hide(e2){
	document.getElementById(e2).style.display="none";
}

$(".top_link a.wx").hover(function(){
	$(this).find("i").css("background-position", "-20px -90px")
}, function(){
	$(this).find("i").css("background-position", "0px -90px")
});

$(".top_link a.wb").hover(function(){
	$(this).find("i").css("background-position", "-20px -110px")
}, function(){
	$(this).find("i").css("background-position", "0px -110px")
})

$("#person").hover(function(){
	$("#person .personlist").show();
}, function(){
	$("#person .personlist").hide();
});

$(".p_ul li").hover(function(){
	$(this).css("color","orange");
}, function(){
	$(this).css("color","black");
});

$("#product li").hover(function(){
	$(this).css("background","#fef2f9");
}, function(){
	$(this).css("background","white");
});

$("#product").click(function(event){
	$("#setout .dropdownlist").hide();
	var flag = $("#product .dropdownlist").css("display");
	if(flag == 'block')
		$("#product .dropdownlist").hide();
	else
		$("#product .dropdownlist").show();
	event.stopPropagation();
});

$("#setout").click(function(event){
	$("#product .dropdownlist").hide();
	var flag = $("#setout .dropdownlist").css("display");
	if(flag == 'block')
		$("#setout .dropdownlist").hide();
	else
		$("#setout .dropdownlist").show();
	event.stopPropagation();
});

$(document).click(function(){
	$(".dropdownlist").hide();
});

$(".products li").click(function(){
	$("#type").attr("value",$(this).find("span").text());
	$("#product").find("span").eq(0).text($(this).find("span").text());
});

$(".citys li span").click(function(){
	$("#city").attr("value",$(this).text());
	$("#setout").find("span").eq(0).text($(this).text());
});
