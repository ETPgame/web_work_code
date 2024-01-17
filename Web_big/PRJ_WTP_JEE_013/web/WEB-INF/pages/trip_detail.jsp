<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
<title>旅游详情页</title>
<link rel="stylesheet" href="common/css/common.css">
<link rel="stylesheet" href="css/tourist_detail.css" type="text/css" />
<script type="text/javascript" src="js/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/pages/detail/calendar.js"></script>

	<script type="text/javascript" src="http://api.map.baidu.com/Library/SearchInfowindow/1.5/src/SearchInfowindow_min.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=bAI2dbsSmZReruB3ASpk1nKOpmRenHs2"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfowindow/1.5/src/SearchInfowindow_min.css" />

</head>
<body>
	<!-- header【开始】 -->
	<div class="topbar_js_box">
		<div class="topbar_box">
			<ul class="top_link">
				<!-- 一系列的链接，比如，我的资料，积分商城，帮助，微信，微博，电话，等等 -->
				<li id="person" class="dropdown"><a rel="nofollow"
					class="lv_link" href="javascript:void(0);">个人中心</a><i
					class="icon_arrow"></i>
					<div class="personlist">
						<ul class="p_ul">
							<li><a href="/PRJ_WTP_JEE_013/myorder.jhtml?type=init">我的订单</a></li>
							<li><a href="/PRJ_WTP_JEE_013/personalinfo.jhtml?type=init">个人资料</a></li>
							<li><a href="/PRJ_WTP_JEE_013/updatepwd.jhtml?type=init">修改密码</a></li>
							<li><a
								href="/PRJ_WTP_JEE_013/mycontact.jhtml?type=getContacts">常用游客</a></li>
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
					<c:choose>
						<c:when test="${empty user}">
							<a href="/PRJ_WTP_JEE_013/login.jsp">请登录</a>
            		 | 		<a href="/PRJ_WTP_JEE_013/register.jhtml?type=init">免费注册</a>
						</c:when>
						<c:otherwise>
            				当前用户：
	            			<c:choose>
								<c:when test="${not empty user.name }">${user.name }</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${not empty user.phone }">${user.phone }</c:when>
										<c:otherwise>${user.email }</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<a href="/PRJ_WTP_JEE_013/logout.jhtml">注销</a>
						</c:otherwise>
					</c:choose>
					&nbsp;&nbsp;&nbsp; <a href="/PRJ_WTP_JEE_013/onLinePersonal.jhtml">当前在线用户</a>

				</div>
			</div>
		</div>
	</div>
	<div class="header">
		<div class="inner_herder">
			<a href="/PRJ_WTP_JEE_013/index.jhtml">
				<div class="lv_logo">
					<img src="/PRJ_WTP_JEE_013/common/img/here.png" /> <span>去哪玩旅游网</span>
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

						<form class="form_search" id="headSearchForm"
							action="/PRJ_WTP_JEE_013/search.jhtml" method="post">
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

				<li id="home" onclick="toIndex('/PRJ_WTP_JEE_013/index.jhtml')"><a>首页</a></li>
				<li id="ticket" class="head-live"><a href="javascript:void(0);">自驾游</a></li>
				<li id="liner" class="head-live"><a href="javascript:void(0);">国内游</a></li>
				<li id="zijia" class="head-live"><a href="javascript:void(0);">出境游</a></li>
				<li style="cursor: default" id="lvyue"><a
					href="javascript:void(0);" style="cursor: default">亲子游</a></li>
				<li style="cursor: default" id="tuangou"><a
					href="javascript:void(0);" style="cursor: default">特卖会</a></li>
				<li style="cursor: default" id="custom"><a
					href="javascript:void(0);" style="cursor: default">定制游</a></li>
			</ul>
		</div>
	</div>

	<script type="text/javascript"
		src="/PRJ_WTP_JEE_013/common/js/header.js"></script>
	<!-- header【结束】 -->

	<!-- 隐藏域，用于存储当前订单总价的数据 -->
	<div style="display: none" id="current_price"></div>

	<div class="base">
		<!-- 主体【开始】 -->
		<div class="body_bg" style="padding-bottom: 30px;">

			<!-- 导航栏【开始】 -->
			<div class="navigation">
				<span class="font_trip_title"> <a href="index.jhtml">首页</a> > <a
					href="#">自驾游</a> > <a class="cur">上海一日游</a>
				</span>
			</div>
			<!-- 导航栏【结束】 -->

			<!-- 顶部产品概览 开始 -->

			<div class="product-main clearfix border_gray">

				<div class="protect-icon tuangou-icon" id="tour-type"></div>

				<div class="comment_img_div">
					<div class="DB_gallery">
						<div class="DB_imgSet" style="display: block;">
							<div class="DB_imgWin">
								<img src="image_cache/2.jpg" />
							</div>
							<div class="DB_prevBtn">
								<img src="img/prev_off.png" />
							</div>
							<div class="DB_nextBtn">
								<img src="img/next_off.png" />
							</div>
						</div>
						<div class="DB_thumSet">
							<ul class="DB_thumMove">

								<c:forEach items="${trip.pic_list}" var="t_pic">
									<li><a href="image_cache/2.jpg"><img
											src="image_cache/2.jpg"></a></li>
								</c:forEach>

							</ul>
							<div class="DB_thumLine"></div>
							<div class="DB_prevPageBtn">
								<img src="img/prev_page.png" alt="上一页" />
							</div>
							<div class="DB_nextPageBtn">
								<img src="img/next_page.png" alt="下一页" />
							</div>
						</div>
					</div>
				</div>

				<div class="overview-description">
					<div class="overview-description">
						<div class="trip-name">
							<h1>旅游产品标题</h1>
						</div>

						<div class="product-price">
							<div class="product-price-item clearfix">
								<div
									class="product-price-title color-style1 product-qunawan-price">
									去哪玩价：</div>
								<div class="trip-prices-content trip-prices-content-val">
									<span class="product-price-rmb"> <span
										class="rmb-price">¥</span> <span class="product-price-value">
											999.99 </span>
									</span> <span class="fs-12 color-style1"> 起/人(最少成团人数:10人) <i
										class="protect-icon price-explain-icon"></i> <span
										tip-content="本起价是指未包含附加服务（如单人房差、保险费等）的基本价格。您最终确认的价格将会随所选出行日期、人数及服务项目而相应变化">起价说明</span>
									</span>
								</div>
							</div>
							<div class="product-price-item clearfix">
								<div class="product-price-title color-style1">出 发 地：</div>
								<!--若有值，则是多出发地产品，按多出发地需求展示，否则按原有方式展示-->
								<div class="trip-prices-content">
									<span>中国</span>
								</div>
								<!--出发地	6.30 add end -->
							</div>
							<div class="product-price-item clearfix">
								<div class="product-price-title color-style1">目 的 地：</div>
								<div class="trip-prices-content">
									<span>三亚</span>
								</div>
							</div>
							<div class="product-price-item clearfix">
								<div class="product-price-title color-style1">往返交通：</div>
								<div class="trip-prices-content">
									<span>大巴</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="product-over-protect">
					<div class="overview-protect-top clearfix">
						<div class="product-basic-info">
							<div class="product-no clearfix">
								<div class="pull-left">
									<span>产品编号：</span>
								</div>
								<div class="put-right">
									<span>20185961</span>
								</div>
							</div>
							<div class="product-no clearfix">
								<div class="pull-left">
									<span>好评率：</span>
								</div>
								<div class="put-right">
									<span class="val">99.6%</span>
								</div>
							</div>
						</div>
					</div>
					<div class="overview-protect-middle clearfix">
						<div class="product-protect-info h_center">
							<ul>
								<li class="overview-protect-price"><i
									class="protect-icon price-icon margin5"></i> <a
									href="javascript:;" target="_blank" rel="nofollow"
									hidefocus="false">价格保障</a></li>
								<li class="overview-protect-refund"><i
									class="protect-icon refund-icon"></i> <a href="javascript:;"
									target="_blank" rel="nofollow" hidefocus="false">退货保障</a></li>
								<li class="overview-protect-help"><i
									class="protect-icon help-icon"></i> <a href="javascript:;"
									target="_blank" rel="nofollow" hidefocus="false">救援保障</a></li>
								<li class="overview-protect-allday"><i
									class="protect-icon allday-icon"></i> <a href="javascript:;"
									target="_blank" rel="nofollow" hidefocus="false">7×24小时服务</a></li>
							</ul>
						</div>
					</div>
					<div class="overview-protect-qrcode">
						<img src="img/weixin.png" alt="qr code" width="78" height="78">

						<p>扫一扫更优惠</p>
					</div>
				</div>
			</div>

			<!-- 顶部产品概览 结束 -->

			<!-- 开始预定 开始 -->
			<form method="POST" action="myorder.jhtml?type=confirmOrder"
				name="orderForm" id="order-submit">
				<div class="product-preorder clearfix color-style1"
					id="product-preorder">

					<div class="preorder-config">
						<div class="preorder-input-group chrome-hack-1">
							<span class="chrome-hack-2">出行日期：</span>

							<div class="trip-time" id="trip-time">
								<input class="preorder-start-time" id="start_time" type="text"
									placeholder="请选择出行日期" autocomplete="off"
									onclick="AjaxTime(event);" name="date" readonly="readonly">
							</div>
						</div>

						<div class="preorder-input-group">
							<span>人数：</span> <select name="people" class="young">
								<option rel="1" class="liActive">1</option>
								<option rel="2" class="">2</option>
								<option rel="3" class="">3</option>
								<option rel="4" class="">4</option>
								<option rel="5" class="">5</option>
								<option rel="6" class="">6</option>
								<option rel="7" class="">7</option>
								<option rel="8" class="">8</option>
								<option rel="9" class="">9</option>
								<option rel="10" class="">10</option>
								<option rel="11" class="">11</option>
								<option rel="12" class="">12</option>
								<option rel="13" class="">13</option>
								<option rel="14" class="">14</option>
								<option rel="15" class="">15</option>
								<option rel="16" class="">16</option>
								<option rel="18" class="">18</option>
								<option rel="19" class="">19</option>
								<option rel="20" class="">20</option>
								<option rel="21" class="">21</option>
								<option rel="22" class="">22</option>
								<option rel="23" class="">23</option>
								<option rel="24" class="">24</option>
								<option rel="25" class="">25</option>
								<option rel="26" class="">26</option>
								<option rel="27" class="">27</option>
								<option rel="28" class="">28</option>
								<option rel="29" class="">29</option>
								<option rel="30" class="">30</option>
								<option rel="31" class="">31</option>
								<option rel="32" class="">32</option>
								<option rel="33" class="">33</option>
								<option rel="34" class="">34</option>
								<option rel="35" class="">35</option>
								<option rel="36" class="">36</option>
								<option rel="37" class="">37</option>
								<option rel="38" class="">38</option>
								<option rel="39" class="">39</option>
								<option rel="40" class="">40</option>
								<option rel="41" class="">41</option>
								<option rel="42" class="">42</option>
								<option rel="43" class="">43</option>
								<option rel="44" class="">44</option>
								<option rel="45" class="">45</option>
								<option rel="46" class="">46</option>
								<option rel="47" class="">47</option>
								<option rel="48" class="">48</option>
								<option rel="49" class="">49</option>
								<option rel="50" class="">50</option>
							</select>
						</div>
					</div>

					<!-- 在form提交时，上传的游玩的人数&旅游产品的id -->
					<input id="num" name="num" type="hidden" /> <input id="trip_id"
						name="trip_id" type="hidden" />

					<div class="preorder-confirm">
						<a id="preorder-confirm-button" hidefocus="false">开始预订</a>

						<div class="preorder-total-price">
							<span class="tip-icon tip-icon-help js_tips"
								style="display: none" id="DuiJie_TraffocTip"
								tip-content="机票价格更新频繁，以实际支付为准，为避免价格波动，请您尽快预订；"></span> <span
								class="total-price-value" id="total-price-value">--</span> <span
								class="total-price-symbol rmb-price">￥</span> <span
								class="total-price-title">总价：</span> <span class="fangcha"
								style="display: none;"></span>
						</div>
					</div>
				</div>
			</form>
			<!-- 开始预定 结束 -->

			<!--  产口详情、行程介绍、交通地图、综合点评-->

			<div class="product-detail">
				<div class="empty-placeholder hidden"></div>
				<div id="subNav">
					<ul class="wrap">
						<li class="adv_active"><a href="#product_detail"
							class="protuctdetail adv_active">产品详情 </a></li>
						<li><a href="#travel_introduce" class="travelintroduce">
								行程介绍 </a></li>
						<li><a href="#tafficMap" class="trafficmap "> 交通地图 </a></li>
						<li><a href="#reviewAll" class="reviewall   "> 综合点评 </a></li>
					</ul>
				</div>

				<div class="wrap" style="width: 1000px;">
					<div class="show_title">
						<div class="product-pm-recommand clearfix">
							<div class="pm-recommand-title clearfix">
								<div class="recommand-left-quotation"></div>
								<!--新增模块-->
								<!--新增模块-->
								<h3 class="recommand-title-main">产品经理推荐</h3>

								<div class="recommand-right-quotation"></div>
							</div>
							<div class="pm-recomman-body">
								<ul>
									<li>
										恩施不偏不倚，她落脚于神秘的“北纬30度”，早已气势磅礴地傲立天地间。她的神秘，源于天然和原始，毫无矫揉造作。她的奇幻在于山水的真义与不可复制。她以仙山和秀水的胸襟，以白云和绿色的情怀，诚邀世界，迎接八方！她那么美，我们一起去看看！
									</li>
									<li>行程特色：行程丰富，大牌景区，优选品质酒店，完美的超值体验。</li>
								</ul>
							</div>
						</div>
					</div>

					<!--   产品详情 开始  -->
					<div class="product-detail-instance" id="product_detail">
						<div class="flags"></div>
						<div class="instance-title clearfix">
							<div class="title-icon-container">
								<i class="product-detail-icon"></i>
							</div>

							<h3>产品详情</h3>
						</div>
						<div class="detail-instance-body">

							<div class="product-point">
								<span><img alt="" src="img/scenic.jpg" height="69px;"></span>
								<br>景点介绍
							</div>

							<div class="product-point">
								<span><img alt="" src="img/foodphotoes.jpg" height="69px;"></span>
								<br>美食介绍
							</div>

							<div class="product-point">
								<span><img alt="" src="img/hotel.jpg" height="69px;"></span>
								<br>酒店介绍
							</div>

						</div>
					</div>

					<!--   产品详情 结束  -->

					<!--   行程介绍 开始  -->
					<div class="product-detail-instance" id="travel_introduce">
						<div class="flags"></div>
						<div class="instance-title clearfix">
							<div class="title-icon-container">
								<i class="product-line-icon"></i>
							</div>
							<h3>行程介绍</h3>
						</div>
						<div class="instance_list2_box">
							<div class="instance_list2" style="display: block;">
								<div class="detail-instance-body  padding0 clearfix">
									<div class="travel-all">
										<ul id="J_scrollnav1">
											<a href="#travel0"><li class="travel-fixed-item active">交通</li></a>
											<a href="#travel"><li class="travel-fixed-item">第1天</li></a>
											<a href="#travel"><li class="travel-fixed-item">第2天</li></a>
										</ul>
									</div>
									<div class="travel-case">

										<div class="travel-title clearfix">
											<div id="travel-day-128006-0"
												class=" day_flag bg_none traffic_bus"></div>
											<p class="tips fs-12">注：以下交通信息仅限参考</p>
											<div class="traffic-type clearfix" id="traffic-type">
												<ul>
													<li data-traffic="traffic1-60896" class="active">交通<i
														class="arrow"></i>
													</li>
												</ul>
											</div>
											<div class="traffic-container" id="traffic-container">
												<div id="traffic1-60896-container" class="traffic-instance"
													style="display: block;">
													<dl class="traffic-item clearfix">
														<dt class="title">去程</dt>
														<dd class="outer-container">
															<div class="container clearfix">
																<div class="left">
																	<ul>
																		<!--判断是否是转机-->
																		<li class="clearfix bus">
																			<div class="icon">
																				<i class="traffic-bus-icon"></i>
																			</div>
																			<div>
																				<p>上车点：人民广场4号口</p>
																				<p>
																					发车时间：<span class="bus-time">8:00</span>
																				</p>
																			</div>
																		</li>
																	</ul>
																</div>
															</div>
														</dd>
													</dl>
													<dl class="traffic-item clearfix">
														<dt class="title">返程</dt>
														<dd class="outer-container">
															<div class="container clearfix">
																<div class="left">
																	<ul>
																		<li class="clearfix bus">
																			<div class="icon">
																				<i class="traffic-bus-icon"></i>
																			</div>
																			<div>
																				<p>上车点：西湖</p>
																				<p>
																					发车时间：<span class="bus-time">第二天16:00</span>
																				</p>
																			</div>
																		</li>
																	</ul>
																</div>
															</div>
														</dd>
													</dl>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!--   行程介绍 结束  -->

					<!--   交通地图 开始  -->
					<div class="product-detail-instance" id="tafficMap">
						<div class="flags"></div>
						<div class="instance-title clearfix">
							<div class="title-icon-container">
								<i class="product-map-icon"></i>
							</div>
							<h3>交通地图</h3>
						</div>
						<!-- 百度地图在这里显示 -->
						<div id="allmap"></div>
					</div>
					<!--   交通地图 结束  -->


				</div>
			</div>
		</div>
	</div>
	<!-- 主体【结束】 -->
	


	<!-- 此JS文件必须放在文件末尾 -->
	<script type="text/javascript"
		src="/PRJ_WTP_JEE_013/js/pages/detail/tourist_detail.js"></script>
	<script type="text/javascript"
		src="/PRJ_WTP_JEE_013/js/pages/detail/mybaidu.js"></script>
	<script type="text/javascript"
		src="/PRJ_WTP_JEE_013/js/pages/detail/jquery.DB_gallery.js"></script>
	<script type="text/javascript"
		src="/PRJ_WTP_JEE_013/js/pages/detail/comment.js"></script>
	<script type="text/javascript">
		$('.DB_gallery').DB_gallery({
			thumWidth : 83,
			thumGap : 8,
			thumMoveStep : 4,
			moveSpeed : 300,
			fadeSpeed : 500
		});



		// 通过 EL 表达式获取经度（东经）、纬度（北纬）、所在城市
		var p1 = "${trip.position.p1}";
		var p2 = "${trip.position.p2}";
		var city = "${trip.start.name}";

		// 创建百度地图实例
		var map = new BMap.Map("allmap");
		// 创建 BMap.Point 对象，封装景点坐标位置
		var poi = new BMap.Point(p1, p2);
		// 为百度地图设置坐标、所在城市
		map.centerAndZoom(poi, 15);
		map.setCurrentCity(city);
		// 创建 BMap.Marker 对象，确保百度地图呈现红色标记点
		var marker = new BMap.Marker(poi);
		map.addOverlay(marker);
		// 为百度地图设置缩放属性
		map.enableScrollwheelZoom(true);


	</script>
</body>
</html>