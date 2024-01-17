package campsg.qunawan.service;

import java.util.List;
import java.util.Set;

import campsg.qunawan.bean.SearchBean;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.TripPicture;
import campsg.qunawan.form.SearchForm;

public interface TripService {

	/**
	 * 根据筛选表单填装该表单用于显示部分的数据
	 * @param vo 筛选表单
	 * @return 填装数据后用于显示的表单
	 */
	SearchBean getSearchBean(SearchForm vo);
	
	/**
	 * 根据表单内容查找筛选后的行程列表
	 * @param vo 筛选表单
	 * @return 搜索产品列表
	 */
	List<Trip> getSearchTripsByVo(SearchForm vo);

	/**
	 * 初始化图片缓存
	 * @return
	 */
	void initTripPicture(Set<TripPicture> pictures, String basePath);

}
