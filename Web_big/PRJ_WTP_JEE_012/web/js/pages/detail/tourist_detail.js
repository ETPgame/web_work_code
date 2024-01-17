/**
 * 文件注释：
 * 旅游产品详情中，控制页面交互部分的js
 * 1、百度地图API
 * 2、勾选日期与人数后，更新当前总价格
 * 3、日历组件的初始化
 * 4、侧边导航栏的交互效果
 * 2015.12
 */

/**
 * 文档加载完毕执行本函数
 */
$(document).ready(function() {

	// 勾选旅游人数时，自动更新总价
	$(".young").change(function() {
		var num = $(".young").val();
		var price = $("#current_price").text();
		update_total_price(num, price);
	});

	// 旅游行程天数
	$("#J_scrollnav1 a").on("click", ".travel-fixed-item", function() {

		$(".travel-fixed-item").removeClass("active");
		$(this).addClass("active");

	});

	$("#J_scrollnav1 a").click(function() {

		var target = $(this).attr("href");
		var targetScrolls = $(target).offset().top;
		$("html,body").animate({
			scrollTop : targetScrolls
		}, 300);
		return false;
	});

});

// 自定义日历初始化
function AjaxTime(e) {
	var data = $("#price_data").html();
	if (data.length > 1)
		pickerEvent.setPriceArr(eval("(" + data + ")"));
	pickerEvent.Init("start_time");
	e.stopPropagation();
}

/**
 * 更新当前总价格
 * 1、选择人数
 * 2、选择日期与价格
 * @param num 人数
 * @param price 价格
 */
function update_total_price(num, price) {
	if (price == null || price == "")
		return;
	$("#total-price-value").text(price * num);
}

/*
 * 设置导航选项卡的滚动效果、不同内容时选项卡的被选状态
 */
$(function() {
	//优势页面点击子导航
	var subNav_active = $(".adv_active");
	var subNav_scroll = function(target) {
		subNav_active.removeClass("adv_active");
		target.parent().addClass("adv_active");
		subNav_active = target.parent();
	};

	$("#subNav a").click(function() {
		subNav_scroll($(this));
		var target = $(this).attr("href");
		var targetScroll = $(target).offset().top - 40;
		$("html,body").animate({
			scrollTop : targetScroll
		}, 300);
		return false;
	});

	//页面跳转时定位
	if (window.location.hash) {
		var targetScroll = $(window.location.hash).offset().top - 80;
		$("html,body").animate({
			scrollTop : targetScroll
		}, 300);

	}
	$(window).scroll(function() {

		var $this = $(this);
		var targetTop = $(this).scrollTop();

		//获取产品详情、旅游介绍、交通地图、综合评介的div在页面中的位置
		var product_detail = $('#product_detail').offset().top - 50;
		var travel_introduce = $('#travel_introduce').offset().top - 50;
		var tafficMap = $('#tafficMap').offset().top - 100;
		var reviewAll = $('#reviewAll').offset().top - 100;
		var reviewAll = $('#reviewAll').offset().top - 100;

		var publicfood = $('.public_ft_box').offset().top - 100;

		//根据产品详情、旅游介绍、交通地图、综合评介的div在页面中的位置，判断不同在这些不同范围的位置，设置导航栏被选状态
		if (targetTop >= product_detail) {

			$("#subNav").addClass("fixedSubNav");
			$(".empty-placeholder").removeClass("hidden");
		} else {
			$("#subNav").removeClass("fixedSubNav");
			$(".empty-placeholder").addClass("hidden");
		}
		if (targetTop <= travel_introduce) {
			subNav_scroll($(".protuctdetail"));
		} else if (targetTop > travel_introduce && targetTop < tafficMap) {
			subNav_scroll($(".travelintroduce"));
		} else if (targetTop > tafficMap && targetTop < reviewAll) {
			subNav_scroll($(".trafficmap"));
		} else if (targetTop > reviewAll) {
			subNav_scroll($(".reviewall"));
		}
		if (targetTop > publicfood) {
			$("#subNav").removeClass("fixedSubNav");

		}
	})

}());
