package campsg.qunawan.dao;

import java.util.List;

import campsg.qunawan.entity.Trip;

public interface TripDao {

	/**
	 * 通过行程类型查找分页行程列表
	 * @param seqId 行程类型id
	 * @param start 分页开始索引
	 * @param maxCount 每页最大值
	 * @return 分页行程列表
	 */
	List<Trip> getPageTripsByType(int seqId, int start, int maxCount);

}
