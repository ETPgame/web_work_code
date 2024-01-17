package campsg.qunawan.dao;

import java.util.List;

import campsg.qunawan.entity.ThemeOnTrip;

public interface ThemeOnTripDao {

	/**
	 * 根据行程id获取对应的主题集
	 * 
	 * @param tripId 行程id
	 * @return 行程图片集合
	 */
	List<ThemeOnTrip> getThemesByTripId(Integer tripId);
}
