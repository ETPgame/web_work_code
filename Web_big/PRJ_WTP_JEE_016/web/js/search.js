$(function(){
	var init = function(){
		$(".body .option").each(function(e){
			if(e>3)
				$(this).hide();
		});
	};
	var showCondition = function(){
		var total = $(".body .option").length;
		var del = $(".body .del").length;
	
		if(total - del <= 4)
			$(".filter_box .tail .condition").hide();
		else
			$(".filter_box .tail .condition").show();
	};
	var showAll = function(){
		$(".body .option").each(function(){
			if(!$(this).hasClass("del"))
				$(this).show();
		});
	};
	var showPart = function(){
		var num = 0;
		$(".body .option").each(function(){
			$(this).hide();
			if(!$(this).hasClass("del")){
				num++;
				if(num<=4)
					$(this).show();
			}
		});
	};

	init();
	showCondition();

	/* 判断是否显示单项筛选条件的【更多(↓)】 */
	$(".body .ddlist").each(function(){
		var lastIndex = $(this).find("dd").css("height").length-2;
		var height = $(this).find("dd").css("height").substring(0,lastIndex);
		if(height > 50){
			$(this).next().show();
		}
	});

	/* 删除筛选条件 */
	$(document).on("click",".request_list a.request_close",function(){
		var dd = $(this).parent().parent().parent();
		$(this).parent().parent().remove();
		if(dd.find("dd").length == 1)
			dd.hide();
		var title = $(this).parent().text().substring(0,$(this).parent().text().indexOf("："));
		$(".del").each(function(){
			if($(this).find(".title").text() == title)
				$(this).removeClass("del");
		});
		showPart();
		showCondition();
	});

	/* 添加筛选条件 */
	$(".ddlist a").click(function(){
		$(".filter_box .tail .request_list").show();
		$(this).parent().parent().parent().parent().addClass("del");
		$(this).parent().parent().parent().parent().hide();

		var index = $(".tail .request_list dd").length - 1;
		var e = $(".tail .request_list dd");
		var title = $(this).parent().parent().parent().prev().text();
		var content = $(this).text().substring(0,$(this).text().indexOf("(")-1);
		
		e.eq(index).before("<dd><span>"+title+"："+content+"<a class=\"request_close\" href=\"#\">×</a></span></dd>");
		showPart();
		showCondition();
	});

	/* 收起或展开所有筛选条件 */
	$(".filter_box .tail .condition").click(function(){
		var e = $(this);
		e.hide();
		if(e.hasClass("condition_down")){
			$(".condition_up").show();
			showAll();
		}else{
			$(".condition_down").show();
			showPart();
		}
	});

	/* 收起或展开单项筛选条件 */
	$(".filter_box .body a.more").click(function(){
		var o = $(this);
		if(o.hasClass("more_y")){
			$(this).prev().css("max-height",$(this).prev().find("dd").css("height"));
			$(this).html("收起<i class=\"arrow_up\"></i>");
			$(this).removeClass("more_y");
			$(this).addClass("more_n");
		}else{
			$(this).prev().css("max-height","50px");
			$(this).html("更多<i class=\"arrow_down\"></i>");
			$(this).removeClass("more_n");
			$(this).addClass("more_y");
		}
	});

	$(".filter_box li").click(function(){
		$(".filter_box li").removeClass("cur");
		$(this).addClass("cur");
	});

	/* 【更多(↓)的hover变色】 */
	$(".filter_box .body a.more").hover(function(){
		$(this).css("color","#F90");
		var o = $(this);
		if(o.hasClass("more_y"))
			$(this).find("i.arrow_down").css("border-color","#F90 #fff #fff")
		else
			$(this).find("i.arrow_up").css("border-color","#fff #fff #F90 #fff");
	}, function(){
		$(this).css("color","#999");
		var o = $(this);
		if(o.hasClass("more_y"))
			$(this).find("i.arrow_down").css("border-color","#999 #fff #fff");
		else
			$(this).find("i.arrow_up").css("border-color","#fff #fff #999 #fff");
	});

	/* 排序的箭头显示 */
	$(".main_l .sort .price,.main_l .sort .comment").click(function(){
		var e = $(this).find("i");
		if(e.hasClass("sort_up")){
			e.removeClass("sort_up");
			e.addClass("sort_down");
		}else{
			e.removeClass("sort_down");
			e.addClass("sort_up");
		}
	})
});

function validNum(){
	if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)||(event.keyCode == 8)))
    event.returnValue=false;	
};