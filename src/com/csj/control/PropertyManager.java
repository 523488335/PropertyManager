package com.csj.control;

import java.util.List;
import java.util.Map;

import com.csj.entry.Property;
import com.csj.exception.PropertyOSException;

/**
 * 资源管理类接口
 * @author 陈尚均
 */
public interface PropertyManager {
	public static final String REPOSITORY = "仓库",REPAIR = "修理站";
	/**
	 * 资源移动回调
	 * @param property 被移动的资源
	 */
	void propertyChange(Property property) throws PropertyOSException;
	/**
	 * 查询所有资源
	 * @return 所有资源的列表
	 */
	List<Property> getAllProperty();
	/**
	 * 资源添加接口。
	 * @param property 待添加的资源
	 */
	void add(Property property);
	/**
	 * 资源分配接口。
	 * @param property 待分配的资源
	 * @param local 分配目的地
	 */
	void allotProperty(int propertyIndex, String local) throws PropertyOSException;
	/**
	 * 查询资源接口
	 * @return 所有local位置上的资源
	 */
	List<Property> getPropertyByLocal(String local) throws PropertyOSException;
	/**
	 * 查询资源接口
	 * @return 所有除了local位置上的资源
	 */
	List<Property> getPropertyNotLocal(String local) throws PropertyOSException;
	/**
	 * @param property 要查询价格的资源
	 * 查询资源价格接口。
	 */
	float getPrice(Property property);
	/**
	 * @param property 要查询价格的资源
	 * 价格注册接口。
	 */
	void registerPrice(Map<Property,Float> priceMap) throws PropertyOSException;
}
