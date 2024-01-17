<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">

<title>上海一日游</title>

<link rel="stylesheet" href="css/tourist_detail.css" type="text/css" />
<link rel="stylesheet" href="common/css/common.css" type="text/css" />
<link rel="stylesheet" href="css/common.css" type="text/css" />
<script type="text/javascript" src="js/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="js/pages/detail/calendar.js"></script>
</head>
<body>
	<!-- header【开始】 -->
	<div id="1" style="width: 100%; height: 163px;">
		<script type="text/javascript" src="common/js/header.js"></script>
	</div>
	<!-- header【结束】 -->

	<!-- 隐藏域，用于存储当前订单总价的数据 -->
	<div style="display: none" id="current_price"></div>
	<!-- 隐藏域，用于存储当前订单总价的数据 -->
	<div style="display: none" id="price_data">${trip.date_list}</div>

	<div class="base">
		<!-- 主体【开始】 -->
		<div class="body_bg" style="padding-bottom: 30px;">

			<!-- 导航栏【开始】 -->
			<div class="navigation">
				<span class="font_g9"> <a href="index.jhtml">首页</a> > 
				
					<a href="#">自驾游</a> > 
					<a class="cur">上海一日游</a>
				
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
							
								<img src="image_cache/${trip.main_picname}" />
							
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

									<li><a href="image_cache/${trip.main_picname}"><img
											src="image_cache/${trip.main_picname}"></a></li>

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
						<div class="product-name">
						
							<h1>${trip.title}</h1>
						
						</div>

						<div class="product-price">
							<div class="product-price-item clearfix">
								<div
									class="product-price-title color-style1 product-qunawan-price">
									去哪玩价：</div>
								<div class="product-price-content product-price-content-val">
									<span class="product-price-rmb"> 
									
										<span class="rmb-symbol">¥</span> 
										<span class="product-price-value">${trip.min_price} </span>
									
									</span> <span class="fs-12 color-style1"> 起/人(最少成团人数:10人) <i
										class="protect-icon price-explain-icon"></i> <span
										tip-content="本起价是指未包含附加服务（如单人房差、保险费等）的基本价格。您最终确认的价格将会随所选出行日期、人数及服务项目而相应变化">起价说明</span>
									</span>
								</div>
							</div>
							<div class="product-price-item clearfix">
								<div class="product-price-title color-style1">出 发 地：</div>

								<div class="product-price-content">
									<span>${trip.start.name}</span>
								</div>
								
							</div>
							<div class="product-price-item clearfix">
								<div class="product-price-title color-style1">目 的 地：</div>
								<div class="product-price-content">
									<span>
										${trip.s_title}
									</span>
								</div>
							</div>
							<div class="product-price-item clearfix">
								<div class="product-price-title color-style1">往返交通：</div>
								
								<div class="product-price-content">
									<span>${trip.traffic}</span>
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
								
								<div class="pull-right">
									<span>${trip.num}</span>
								</div>
							
							</div>
							<div class="product-no clearfix">
								<div class="pull-left">
									<span>好评率：</span>
								</div>
								
								<div class="pull-right">
									<span class="val">${trip.good_rate}%</span>
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
								class="total-price-symbol rmb-symbol">￥</span> <span
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

					<!-- 引入 JSTL core 标签库 -->
					<c:if test="${not empty trip.detail.place or not empty trip.detail.food or not empty trip.detail.hotel}">

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
							<c:if test="${not empty trip.detail.place}">
							<div class="feature ">
									<span> <img alt="" src="img/scenic.jpg" height="69px;">
									</span>
									<br>
									${trip.detail.place}
							</div>
							</c:if>
							<!-- 详情数据模块 - 美食介绍 -->
							<c:if test="${not empty trip.detail.food}">
							<div class="feature ">
									<span> <img alt="" src="img/foodphotoes.jpg"
										height="69px;">
									</span>
									<br>
	                            	${trip.detail.food}
							</div>
							</c:if>
							<!-- 详情数据模块 - 美食介绍 -->
							<c:if test="${not empty trip.detail.hotel}">
							<div class="feature ">
									<span> <img alt="" src="img/hotel.jpg" height="69px;">
									</span>
									<br>
									${trip.detail.hotel}
							</div>
							</c:if>

						</div>
					</div>
					</c:if>
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
										<!--   注释：每个li到链接地址相似，需要以 #travel-day-128006-n 的格式命名 ， 跳转到第n天的具体信息的div时，第n天div的id需要与li的链接相同travel-day-128006-n -->
										<ul id="J_scrollnav1">
											<a href="#travel-day-128006-0"><li
												class="travel-all-item active">交通</li></a>

											<a href="#travel-day-128006-1"><li
												class="travel-all-item">第1天</li></a>
											<a href="#travel-day-128006-2"><li
												class="travel-all-item">第2天</li></a>
											<a href="#travel-day-128006-3"><li
												class="travel-all-item">第3天</li></a>
											<a href="#travel-day-128006-4"><li
												class="travel-all-item">第4天</li></a>

										</ul>
									</div>
									<div class="travel-case">

										<div class="travel-title clearfix">
											<div id="travel-day-128006-0"
												class=" day_div bg_none traffic_bus"></div>
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
																				<p title="管家加州派酒店（地址：成都市金牛区二环路北一段14号）">上车点：人民广场4号口</p>
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
																				<p title="九寨沟沟口">上车点：西湖</p>
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
									<div class="travel-days">

										<div class="travel-case">
											<div class="travel-title clearfix">

												<!--   注释：第n天的id需要与li的链接相同travel-day-128006-n -->
												<div id="travel-day-128006-1" class=" day_div bg_none "></div>
												<span class="day">D1</span> <span class="destination">上海--三亚</span>
											</div>
											<div class="travel-detailTime-line clearfix">
												<div class="time">
													<span></span> <i class="travel-flag-icon"></i>
												</div>
												<div class="detail">乘机前往向往已久的三亚。出机场找到了贵宾接机站。接机站工作人员的笑容我们倍感亲切，给我们做了旅游期间注意事项的介绍，把我们直接送达三亚湾红树林度假世界酒店。如果时间还早，你可以赶紧换上泳装，来一场水世界的激情，累了，闭上眼睛，美美的享受假期的幸福。</div>
											</div>
											<div class="travel-detailTime-line  clearfix">
												<div class="time">
													<span></span> <i class="travel-notice-icon"></i>
												</div>
												<div class="detail-care">
													<p>
														<b>住宿：</b> 含住宿(三亚湾红树林度假酒店)
													</p>
													<p>
														<b>用餐：</b> 早餐(敬请自理) 午餐(敬请自理) 晚餐(敬请自理)
													</p>
													<p>
														<b>交通：</b> 飞机 巴士
													</p>
												</div>
											</div>
										</div>

										<div class="travel-case">
											<div class="travel-title clearfix">

												<!--   注释：第n天的id需要与li的链接相同travel-day-128006-n -->
												<div id="travel-day-128006-2" class=" day_div"></div>
												<span class="day">D2</span> <span class="destination">三亚一地【亚龙湾热带天堂森林公园、亚龙湾海底世界】</span>
											</div>
											<div class="travel-detailTime-line clearfix">
												<div class="time">
													<span></span> <i class="travel-flag-icon"></i>
												</div>
												<div class="detail">07：30阳光爬上您的窗台，就是三亚对您温柔的叫早！
													08:00下楼享受约有60个品种的中西式自助早餐美食。早餐后，我们的导游已经在楼下恭候我们了
													08:30旅行正式开始，大约40分钟车程亲临4AAAA【亚龙湾热带天堂】（游览约150分钟）位于三亚市东南方向亚龙湾国家旅游度假区两侧山体，犹如伸展的双臂环抱着亚龙湾。乘车登顶远看航母基地和南中国海、近看《非II》电影木屋别墅群——星罗棋布的鸟巢，一览亚龙湾无敌美景。
													11:30中午安排海南当地特色团队餐； 14:00左右我们继续出发下一站
													【亚龙湾海底世界】，这里的沙滩很细很白，海很蓝，光着脚丫漫步，享受迷人的椰风海韵。还可以自费参加各种海上娱乐项目，特别推荐潜水，零距离遨游美丽的海底世界。
													17:00晚餐品尝藤桥排骨，品尝琼南风味家常餐，地道的海南本地美食，让你唇齿留香。</div>
											</div>
											<div class="travel-detailTime-line  clearfix">
												<div class="time">
													<span></span> <i class="travel-notice-icon"></i>
												</div>
												<div class="detail-care">
													<p>
														<b>住宿：</b> 含住宿(三亚湾红树林度假酒店)
													</p>
													<p>
														<b>用餐：</b> 早餐(含) 午餐(含) 晚餐(含)
													</p>
													<p>
														<b>交通：</b> 巴士
													</p>
												</div>
											</div>
										</div>

										<div class="travel-case">
											<div class="travel-title clearfix">

												<!--   注释：第n天的id需要与li的链接相同travel-day-128006-n -->
												<div id="travel-day-128006-3" class=" day_div"></div>
												<span class="day">D3</span> <span class="destination">三亚一地【蜈支洲岛】</span>
											</div>
											<div class="travel-detailTime-line clearfix">
												<div class="time">
													<span></span> <i class="travel-flag-icon"></i>
												</div>
												<div class="detail">07：00建议大家今天早点起床；
													07:30在茂密的热带丛林之间享受我们的早餐。为一天的旅行开始充电……
													08:00乘车前往被誉为“海上世外桃源”的【蜈支洲岛】（游览半天），岛上自然风光绮丽迷人，漫山叠翠，山石嶙峋，海水清澈，沙质洁白细腻，恍若玉带天成，给足您岛上游玩的疯狂时间。
													15:00傍晚乘船前往后海，这里是一片未被世人打扰的世外桃源，本地渔民（疍家人）的海上人家，只有你身临其境才能体会。在这里我们为您安排了鱼排三步曲，想要热情的我们就举行电子篝火晚会；霓虹灯，嗨歌，一起在海上乐翻天，想玩沉淀你就品品鹧鸪茶、海上垂钓渔舟唱晚观海上夕阳西下，这里海域海水清澈透明，是天然的大渔场。晚餐我们品尝海上海鲜边炉大餐，品种有、海鲤、剑槽、海虾、螃蟹、大海麻虾……（根据季节，会适当的调整品种）现捞现吃新鲜野生海鲜，海风轻抚，鱼排轻摇，大快朵颐、不亦乐乎！
													18：00晚下榻三亚海棠湾喜来登度假酒店。</div>
											</div>
											<div class="travel-detailTime-line  clearfix">
												<div class="time">
													<span></span> <i class="travel-notice-icon"></i>
												</div>
												<div class="detail-care">
													<p>
														<b>住宿：</b> 含住宿(海棠湾喜来登度假酒店（备选酒店：希尔顿逸林、万丽、豪华精选或同级）)
													</p>
													<p>
														<b>用餐：</b> 早餐(含) 午餐(含) 晚餐(含)
													</p>
													<p>
														<b>交通：</b> 巴士
													</p>
												</div>
											</div>
										</div>

										<div class="travel-case">
											<div class="travel-title clearfix">

												<!--   注释：第n天的id需要与li的链接相同travel-day-128006-n -->
												<div id="travel-day-128006-4" class=" day_div"></div>
												<span class="day">D4</span> <span class="destination">三亚一地【奥特莱斯、天涯海角】</span>
											</div>
											<div class="travel-detailTime-line clearfix">
												<div class="time">
													<span></span> <i class="travel-flag-icon"></i>
												</div>
												<div class="detail">07：30阳光爬上您的窗台，就是三亚对您温柔的叫早！
													08:00下楼享受约有60个品种的中西式自助早餐美食。早餐后，我们的导游已经在楼下恭候我们了
													08:30悠闲的早餐后前往【奥特莱斯】购物商场，选择适合您带给家人的特产礼物。 11:30中午安排海南当地特色团队餐；
													13:00前往海南象征性旅游胜地【天涯海角】。这里是天之涯，海之角。请留下你们美好的许愿。让您感受天之涯、海之角梦幻般的神奇与神秘。南天一柱石、天涯海角石、海判南天石，日月同辉石赐予您亲情、友情、和爱情的同时还赋予您财富和健康、智慧和仁爱。如晚上还不尽兴，可自主要求导游带您感受激情奔放的三亚之夜。
													17:00安排海南当地特色团队餐； 18:00晚下榻三亚湾三亚湾红树林度假世界。</div>
											</div>
											<div class="travel-detailTime-line  clearfix">
												<div class="time">
													<span></span> <i class="travel-notice-icon"></i>
												</div>
												<div class="detail-care">
													<p>
														<b>住宿：</b> 含住宿(三亚湾红树林度假酒店)
													</p>
													<p>
														<b>用餐：</b> 早餐(含) 午餐(含) 晚餐(含)
													</p>
													<p>
														<b>交通：</b> 巴士
													</p>
												</div>
											</div>
										</div>

										<div class="clearfix"></div>
										<div class="end-of-travel">
											<i class="travel-end-icon"></i>
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
						<div id="allmap"></div>
					</div>
					<!--   交通地图 结束  -->

					<!--   综合点评 开始  -->
					<div class="product-detail-instance" id="reviewAll">
						<div class="flags"></div>
						<div class="instance-title clearfix">
							<div class="title-icon-container">
								<i class="product-review-icon"></i>
							</div>
							<h3>综合点评</h3>
						</div>
						<div class="comwrap">
							<!-- 点评信息概况 -->
							<div class="new-cominfo">
								<div class="comstati clearfix">

									<div class="com-null">
										<div class="com-count">
											<i class="percentum f60" data-mark="dynamicNum"
												data-level="95">98</i><span class="f60">%</span> <em>好评率</em>
											<em>来自<a class="f60" id="comment_num" hidefocus="false">78</a>位驴友的真实点评
											</em>
										</div>

										<ul class="clearfix">
											<li><span class="comcount"><em>景点</em>（4.8）</span> <span
												class="comlevel"> <!--  改变width的值，显示不同的进度--> <i
													data-level="4.6" data-mark="dynamicNum"
													style="width: ${4.8/5*100}%"></i>
											</span></li>
											<li><span class="comcount"><em>酒店</em>（4.8）</span> <span
												class="comlevel"> <i data-level="4.4"
													data-mark="dynamicNum" style="width: ${4.8/5*100}%"></i>
											</span></li>
											<li><span class="comcount"><em>服务</em>（4.9）</span> <span
												class="comlevel"> <i data-level="4.5"
													data-mark="dynamicNum" style="width: ${4.9/5*100}%"></i>
											</span></li>
											<li><span class="comcount"><em>交通</em>（4.5）</span> <span
												class="comlevel"> <i data-level="4.4"
													data-mark="dynamicNum" style="width: ${4.5/5*100}%"></i>
											</span></li>
										</ul>
									</div>

									<div class="com-btns">
										<a class="nlogin" href="javascript:;" hidefocus="false">有订单，写体验点评返奖金</a>
									</div>
								</div>
								<!-- //com-btns -->
							</div>

							<div class="comheatd">
								<ul class="comheatd-ul JS_com-tabnav">
									<li class="active"><a href="javascript:;"
										hidefocus="false" id="refCmt">相关点评<span>(78)</span></a></li>
								</ul>
							</div>
							<!-- //comhead -->

							<div id="comments_detail">

								<div class="feature_spoc">
									<div class="comments_level_level">
										<strong class="star star5"></strong> <span
											class="span_comment"><em>景点</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>交通</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>酒店</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>服务</em><i>&nbsp;5（推荐）</i></span>
									</div>
									<div class="comments_level_content">
										<p>温泉有好几种，去的时候有点下小雨，富有诗意，环境不错，服务很好。</p>
									</div>
									<div>
										<div class="DB_gallery DB_galleryb">
											<div class="DB_imgSet big_small" style="display: none;">
												<div class="DB_imgWin  DB_imgWinb ">
													<img src="/PRJ_WTP_JEE_011/image_cache/main.jpg"
														style="display: inline;">
												</div>
												<div class="DB_prevBtn  DB_prevBtnb" style="display: none;"></div>
												<div class="DB_nextBtn DB_nextBtnb"></div>
											</div>
											<div class="DB_thumSet DB_thumSetb">
												<ul class="DB_thumMove">
													<c:forEach items="${trip.pic_list}" var="t_pic">
														<li>
															<a href="/PRJ_WTP_JEE_011/image_cache/${t_pic.name}">
																<img src="/PRJ_WTP_JEE_011/image_cache/${t_pic.name}">
															</a>
														</li>
													</c:forEach>

													<div class="DB_thumLine" style="left: 0px;"></div>
												</ul>
												<div class="DB_prevPageBtn tourist_detail_review_pre"
													style="display: none;">
													<img src="img/prev_page.png" alt="上一页">
												</div>
												<div class="DB_nextPageBtn tourist_detail_review_next"
													style="display: none;">
													<img src="img/next_page.png" alt="下一页">
												</div>
											</div>
										</div>
										<span class="stop text_color_blue" style="display: none;">收起
											<span class="icon_retract"> </span class="icon_retract">
										</span>
										<div class="comment">
											<p>
												<a href="javascript:;" hidefocus="false">清风</a> 对 <span
													class="com-proTit"> <a href="javascript:;"
													title="【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园" hidefocus="false">”【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园</a>
												</span> ” 发表点评 <em>2015-12-11 17:07:10</em> <a class="com-user-app"
													href="javascript:;" hidefocus="false"> <i
													class="iconcom iconcom-mobiles"></i>
												</a>
											</p>
										</div>
										<div class="comment_status">
											<ul>
												<li><span class="span_text " id="usefull_102"
													style="cursor: pointer;">赞 (0)</span></li>
												<li><span class="span_text " id="useless_102"
													style="cursor: pointer;">踩 (0)</span></li>
												<li><span class="span_text gray">已点评</span></li>
											</ul>
										</div>
									</div>
								</div>

								<div class="feature_spoc">
									<div class="comments_level_level">
										<strong class="star star5"></strong> <span
											class="span_comment"><em>景点</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>交通</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>酒店</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>服务</em><i>&nbsp;5（推荐）</i></span>
									</div>
									<div class="comments_level_content">
										<p>温泉有好几种，去的时候有点下小雨，富有诗意，环境不错，服务很好。</p>
									</div>
									<div>
										<div class="DB_gallery DB_galleryb">
											<div class="DB_imgSet big_small" style="display: none;">
												<div class="DB_imgWin  DB_imgWinb ">
													<img src="/PRJ_WTP_JEE_011/image_cache/main.jpg"
														style="display: inline;">
												</div>
												<div class="DB_prevBtn  DB_prevBtnb" style="display: none;"></div>
												<div class="DB_nextBtn DB_nextBtnb"></div>
											</div>
											<div class="DB_thumSet DB_thumSetb">
												<ul class="DB_thumMove">
													<li><a href="/PRJ_WTP_JEE_011/image_cache/main.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/main.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/1.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/1.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/2.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/2.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/3.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/3.jpg">
													</a></li>
													<div class="DB_thumLine" style="left: 0px;"></div>
												</ul>
												<div class="DB_prevPageBtn tourist_detail_review_pre"
													style="display: none;">
													<img src="img/prev_page.png" alt="上一页">
												</div>
												<div class="DB_nextPageBtn tourist_detail_review_next"
													style="display: none;">
													<img src="img/next_page.png" alt="下一页">
												</div>
											</div>
										</div>
										<span class="stop text_color_blue" style="display: none;">收起
											<spanclass="icon_retract"> </spanclass="icon_retract">
										</span>
										<div class="comment">
											<p>
												<a href="javascript:;" hidefocus="false">清风</a> 对 <span
													class="com-proTit"> <a href="javascript:;"
													title="【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园" hidefocus="false">”【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园</a>
												</span> ” 发表点评 <em>2015-12-11 17:07:10</em> <a class="com-user-app"
													href="javascript:;" hidefocus="false"> <i
													class="iconcom iconcom-mobiles"></i>
												</a>
											</p>
										</div>
										<div class="comment_status">
											<ul>
												<li><span class="span_text " id="usefull_102"
													style="cursor: pointer;">赞 (0)</span></li>
												<li><span class="span_text " id="useless_102"
													style="cursor: pointer;">踩 (0)</span></li>
												<li><span class="span_text gray">已点评</span></li>
											</ul>
										</div>
									</div>
								</div>

								<div class="feature_spoc">
									<div class="comments_level_level">
										<strong class="star star5"></strong> <span
											class="span_comment"><em>景点</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>交通</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>酒店</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>服务</em><i>&nbsp;5（推荐）</i></span>
									</div>
									<div class="comments_level_content">
										<p>温泉有好几种，去的时候有点下小雨，富有诗意，环境不错，服务很好。</p>
									</div>
									<div>
										<div class="DB_gallery DB_galleryb">
											<div class="DB_imgSet big_small" style="display: none;">
												<div class="DB_imgWin  DB_imgWinb ">
													<img src="/PRJ_WTP_JEE_011/image_cache/main.jpg"
														style="display: inline;">
												</div>
												<div class="DB_prevBtn  DB_prevBtnb" style="display: none;"></div>
												<div class="DB_nextBtn DB_nextBtnb"></div>
											</div>
											<div class="DB_thumSet DB_thumSetb">
												<ul class="DB_thumMove">
													<li><a href="/PRJ_WTP_JEE_011/image_cache/main.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/main.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/1.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/1.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/2.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/2.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/3.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/3.jpg">
													</a></li>
													<div class="DB_thumLine" style="left: 0px;"></div>
												</ul>
												<div class="DB_prevPageBtn tourist_detail_review_pre"
													style="display: none;">
													<img src="img/prev_page.png" alt="上一页">
												</div>
												<div class="DB_nextPageBtn tourist_detail_review_next"
													style="display: none;">
													<img src="img/next_page.png" alt="下一页">
												</div>
											</div>
										</div>
										<span class="stop text_color_blue" style="display: none;">收起
											<spanclass="icon_retract"> </spanclass="icon_retract">
										</span>
										<div class="comment">
											<p>
												<a href="javascript:;" hidefocus="false">清风</a> 对 <span
													class="com-proTit"> <a href="javascript:;"
													title="【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园" hidefocus="false">”【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园</a>
												</span> ” 发表点评 <em>2015-12-11 17:07:10</em> <a class="com-user-app"
													href="javascript:;" hidefocus="false"> <i
													class="iconcom iconcom-mobiles"></i>
												</a>
											</p>
										</div>
										<div class="comment_status">
											<ul>
												<li><span class="span_text " id="usefull_102"
													style="cursor: pointer;">赞 (0)</span></li>
												<li><span class="span_text " id="useless_102"
													style="cursor: pointer;">踩 (0)</span></li>
												<li><span class="span_text gray">已点评</span></li>
											</ul>
										</div>
									</div>
								</div>

								<div class="feature_spoc">
									<div class="comments_level_level">
										<strong class="star star5"></strong> <span
											class="span_comment"><em>景点</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>交通</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>酒店</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>服务</em><i>&nbsp;5（推荐）</i></span>
									</div>
									<div class="comments_level_content">
										<p>温泉有好几种，去的时候有点下小雨，富有诗意，环境不错，服务很好。</p>
									</div>
									<div>
										<div class="DB_gallery DB_galleryb">
											<div class="DB_imgSet big_small" style="display: none;">
												<div class="DB_imgWin  DB_imgWinb ">
													<img src="/PRJ_WTP_JEE_011/image_cache/main.jpg"
														style="display: inline;">
												</div>
												<div class="DB_prevBtn  DB_prevBtnb" style="display: none;"></div>
												<div class="DB_nextBtn DB_nextBtnb"></div>
											</div>
											<div class="DB_thumSet DB_thumSetb">
												<ul class="DB_thumMove">
													<li><a href="/PRJ_WTP_JEE_011/image_cache/main.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/main.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/1.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/1.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/2.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/2.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/3.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/3.jpg">
													</a></li>
													<div class="DB_thumLine" style="left: 0px;"></div>
												</ul>
												<div class="DB_prevPageBtn tourist_detail_review_pre"
													style="display: none;">
													<img src="img/prev_page.png" alt="上一页">
												</div>
												<div class="DB_nextPageBtn tourist_detail_review_next"
													style="display: none;">
													<img src="img/next_page.png" alt="下一页">
												</div>
											</div>
										</div>
										<span class="stop text_color_blue" style="display: none;">收起
											<spanclass="icon_retract"> </spanclass="icon_retract">
										</span>
										<div class="comment">
											<p>
												<a href="javascript:;" hidefocus="false">清风</a> 对 <span
													class="com-proTit"> <a href="javascript:;"
													title="【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园" hidefocus="false">”【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园</a>
												</span> ” 发表点评 <em>2015-12-11 17:07:10</em> <a class="com-user-app"
													href="javascript:;" hidefocus="false"> <i
													class="iconcom iconcom-mobiles"></i>
												</a>
											</p>
										</div>
										<div class="comment_status">
											<ul>
												<li><span class="span_text " id="usefull_102"
													style="cursor: pointer;">赞 (0)</span></li>
												<li><span class="span_text " id="useless_102"
													style="cursor: pointer;">踩 (0)</span></li>
												<li><span class="span_text gray">已点评</span></li>
											</ul>
										</div>
									</div>
								</div>

								<div class="feature_spoc">
									<div class="comments_level_level">
										<strong class="star star5"></strong> <span
											class="span_comment"><em>景点</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>交通</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>酒店</em><i>&nbsp;5（推荐）</i></span> <span
											class="span_comment"><em>服务</em><i>&nbsp;5（推荐）</i></span>
									</div>
									<div class="comments_level_content">
										<p>温泉有好几种，去的时候有点下小雨，富有诗意，环境不错，服务很好。</p>
									</div>
									<div>
										<div class="DB_gallery DB_galleryb">
											<div class="DB_imgSet big_small" style="display: none;">
												<div class="DB_imgWin  DB_imgWinb ">
													<img src="/PRJ_WTP_JEE_011/image_cache/main.jpg"
														style="display: inline;">
												</div>
												<div class="DB_prevBtn  DB_prevBtnb" style="display: none;"></div>
												<div class="DB_nextBtn DB_nextBtnb"></div>
											</div>
											<div class="DB_thumSet DB_thumSetb">
												<ul class="DB_thumMove">
													<li><a href="/PRJ_WTP_JEE_011/image_cache/main.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/main.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/1.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/1.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/2.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/2.jpg">
													</a></li>
													<li><a href="/PRJ_WTP_JEE_011/image_cache/3.jpg">
															<img src="/PRJ_WTP_JEE_011/image_cache/3.jpg">
													</a></li>
													<div class="DB_thumLine" style="left: 0px;"></div>
												</ul>
												<div class="DB_prevPageBtn tourist_detail_review_pre"
													style="display: none;">
													<img src="img/prev_page.png" alt="上一页">
												</div>
												<div class="DB_nextPageBtn tourist_detail_review_next"
													style="display: none;">
													<img src="img/next_page.png" alt="下一页">
												</div>
											</div>
										</div>
										<span class="stop text_color_blue" style="display: none;">收起
											<spanclass="icon_retract"> </spanclass="icon_retract">
										</span>
										<div class="comment">
											<p>
												<a href="javascript:;" hidefocus="false">清风</a> 对 <span
													class="com-proTit"> <a href="javascript:;"
													title="【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园" hidefocus="false">”【上海松江1天1夜】住上海旗山大酒店1晚，免费游佘山森林公园</a>
												</span> ” 发表点评 <em>2015-12-11 17:07:10</em> <a class="com-user-app"
													href="javascript:;" hidefocus="false"> <i
													class="iconcom iconcom-mobiles"></i>
												</a>
											</p>
										</div>
										<div class="comment_status">
											<ul>
												<li><span class="span_text " id="usefull_102"
													style="cursor: pointer;">赞 (0)</span></li>
												<li><span class="span_text " id="useless_102"
													style="cursor: pointer;">踩 (0)</span></li>
												<li><span class="span_text gray">已点评</span></li>
											</ul>
										</div>
									</div>
								</div>

							</div>
							<div class="Pages">
								<a href="javascript:void(0);" title="上一页" class="PrevPage">上一页</a>
								<a href="javascript:void(0);" class="pagesel"
									style="background-color: #DF1A7A; color: white" id="orderPage1">1</a>
								<a href="javascript:void(0);" class="pagesel" id="orderPage2">2</a>
								<a href="javascript:void(0);" title="下一页" class="NextPage">下一页</a>
							</div>
						</div>
					</div>
					<!--   综合点评 结束  -->
				</div>
			</div>
		</div>
	</div>
	<!-- 主体【结束】 -->

	<!-- footer【开始】 -->
	<iframe id="bottom" src="common/footer.html" width="100%" height="650"
		frameborder="0" scrolling="no"></iframe>
	<!-- footer【结束】 -->

	<!-- 此JS文件必须放在文件末尾 -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/js/pages/detail/tourist_detail.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/js/pages/detail/jquery.DB_gallery.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/js/pages/detail/comment.js"></script>
	<script type="text/javascript">
	$('.DB_gallery').DB_gallery({
		thumWidth : 83,
		thumGap : 8,
		thumMoveStep : 4,
		moveSpeed : 300,
		fadeSpeed : 500
	});
	</script>
</body>
</html>