package campsg.qunawan.dao;

import java.util.List;

import campsg.qunawan.entity.Trip;

public interface TripDao {

	/**
	 * 通过筛选表单获取分页行程列表
	 * 
	 * @param key 搜索关键字
	 * @param page 分页查询的页数
	 * @return 分页行程列表
	 */
	List<Trip> getTripByCondition(String key, Integer page);
	
	/**
	 * 获取产品总数
	 * 
	 * @param key 搜索关键字
	 * @return 产品总数
	 */
	Integer getTripNum(String key);
	
	

	/**
	 * 通过行程类型查找分页行程列表
	 * @param id 行程类型id
	 * @param start 分页开始索引
	 * @param maxCount 每页最大值
	 * @return 分页行程列表
	 */
	List<Trip> getPageTripsByType(int id, int start, int maxCount);
	
}
