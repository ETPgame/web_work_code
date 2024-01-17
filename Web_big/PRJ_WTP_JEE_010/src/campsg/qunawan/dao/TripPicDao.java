package campsg.qunawan.dao;

import java.util.Set;

import campsg.qunawan.entity.TripPicture;

public interface TripPicDao {

	/**
	 * 根据行程id获取对应图集
	 * 
	 * @param tripId 行程id
	 * @return 行程图片集合
	 */
	Set<TripPicture> getTripPicsByTripId(Integer tripId);
}
