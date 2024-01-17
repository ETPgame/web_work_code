<!-- PRJ-WTP-JEE-015：首页登录状态显示【Start 2/3】 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- PRJ-WTP-JEE-015：首页登录状态显示【End 2/3】 -->

	<div class="topbar_js_box">
		<div class="topbar_box">
			<ul class="top_link">
				<!-- 一系列的链接，比如，我的资料，积分商城，帮助，微信，微博，电话，等等 -->
				<li id="person" class="dropdown"><a rel="nofollow"
					class="lv_link" href="javascript:void(0);">个人中心</a><i class="icon_arrow"></i>
					<div class="personlist">
						<ul class="p_ul">
							<li><a
								href="javascript:void(0);">我的订单</a></li>
							<li><a
								href="javascript:void(0);">个人资料</a></li>
							<li><a
								href="javascript:voi(0);">修改密码</a></li>
							<li><a
								href="javascript:void(0);">常用游客</a></li>
						</ul>
					</div></li>
				<li><a class="lv_link" rel="nofollow"
					href="javascript:void(0);">积分商城</a></li>
				<li><a class="lv_link" rel="nofollow"
					href="javascript:void(0);">帮助</a></li>
				<li class="dropdown"><a rel="nofollow" class="lv_link wx"
					href="javascript:void(0);"><i class="lv_icon icon_wx"></i>微信 </a></li>
				<li class="dropdown"><a rel="nofollow" class="lv_link wb"
					href="javascript:void(0);"><i class="lv_icon icon_wb"></i>微博</a></li>
				<li><a rel="nofollow" class="lv_link"
					href="javascript:void(0);">010-12345678</a></li>
			</ul>
			<div class="topbar_login">
				<!-- 登录，注册链接 -->
				<div>

					<c:if test="${empty sessionScope.user}">
						<a href="login.jsp">请登录</a>
						| <a href="#">免费注册</a>
					</c:if>

					<c:if test="${not empty sessionScope.user}">
						<span>当前用户:${sessionScope.user.name}</span>
						<a href="${pageContext.request.contextPath}/logout.jhtml">注销按钮</a>
					</c:if>

				</div>
			</div>
		</div>
	</div>
	<div class="header">
		<div class="inner_herder">
			<a href="${pageContext.request.contextPath }/index.jhtml">
				<div class="lv_logo">
					<img src="${pageContext.request.contextPath }/common/img/here.png" />
					<span>去哪玩旅游网</span>
				</div>
			</a>
			<ul class="lv_baozhang">
				<li><i class="lv_icon icon_bz1"></i> 价格保证</li>
				<li><i class="lv_icon icon_bz2"></i> 退订保障</li>
				<li><i class="lv_icon icon_bz3"></i> 救援保障</li>
				<li><i class="lv_icon icon_bz4"></i> 24时服务</li>
			</ul>
			<div class="search_div">
				<div class="search_box">
					<div id="product" class="pointer search_items search_product">
						<span>所有产品</span><i class="search_ico search_ico_r"></i>
						<div class="products dropdownlist">
							<ul>
								<li class="item"><span>自驾游</span></li>
								<li class="item"><span>国内游</span></li>
								<li><span>出境游</span></li>
							</ul>
						</div>
					</div>
					<div id="setout" class="pointer search_items search_city">
						<span>出发地点</span><i class="search_ico search_ico_g"></i>
						<div class="citys dropdownlist">
							<ul>
								<li><span>热门出发城市</span></li>
								<li class="item">
									<p>
										<span>中国</span> <span>上海</span> <span>天津</span> <span>重庆</span>
										<span>桂林</span> <span>杭州</span> <span>丽江</span> <span>嘉兴</span>
										<span>西塘</span> <span>昆明</span>
									</p>
								</li>
							</ul>
						</div>
					</div>
					<div class="search_input">
					
						<form class="form_search" id="headSearchForm">
						
							<input type="text" id="head_search" name="search_key"
								value="景点、主题和标题名称" class="search w_260" /> <input type="hidden"
								id="head_triptype" name="triptype" value="" /> <input
								type="hidden" id="head_start_place" name="start_place" value="" />
						</form>
					</div>
					<span class="pointer search_button" id="search_btn"></span>
				</div>
			</div>
		</div>
	</div>
	<div class="lv_nav_bg">
		<div class="lv_nav">
			<ul class="menu">
			
				<li id="home"
					onclick="toIndex('${pageContext.request.contextPath}/<%=response.encodeURL("index.jhtml")%>')"><a>首页</a></li>
				
				<li id="ticket" class="head-live"><a href="javascript:void(0);">自驾游</a></li>
				<li id="liner" class="head-live"><a href="javascript:void(0);">国内游</a></li>
				<li id="zijia" class="head-live"><a href="javascript:void(0);">出境游</a></li>
				<li style="cursor:default" id="lvyue"><a href="javascript:void(0);" style="cursor:default">亲子游</a></li>
				<li style="cursor:default" id="tuangou"><a href="javascript:void(0);" style="cursor:default">特卖会</a></li>
				<li style="cursor:default" id="custom"><a href="javascript:void(0);" style="cursor:default">定制游</a></li>
			</ul>
		</div>
	</div>

	<script type="text/javascript"
		src="${pageContext.request.contextPath }/common/js/header.js"></script>
