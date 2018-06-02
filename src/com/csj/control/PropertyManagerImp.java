package com.csj.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.csj.entry.Property;
import com.csj.exception.ErrCode;
import com.csj.exception.PropertyOSException;
/**
 * 资源管理者实现，实现了资源管理接口，采用单例设计。
 * 这个实现将资源通过存放地点进行分类，存放在不同容器中，适合管理大量数据。
 * @author 陈尚均
 * 
 */
public class PropertyManagerImp implements PropertyManager{
	
	private final String REPOSITORY = "仓库",REPAIR = "修理站";
	
	/**
	 * 存储容器存在的位置
	 */
	private Set<String> collects = new HashSet<>();
	
	private static PropertyManager propertyManager = null;
	
	/**
	 * 资源列表，保存了公司的所有资源。
	 */
	private List<Property> propertyLists;
	/**
	 * 价格映射，保存了每种被视为不同资源的价格。
	 */
	private Map<Property,Float> priceMap = new HashMap<>();
	/**
	 * 资源位置映射，保存了每个资源被存放的位置。
	 */
	private Map<String,List<Property>> localMap = new HashMap<>();
	
	{
		collects.add(REPAIR);
		collects.add(REPOSITORY);
		collects.add("办公室A");
		collects.add("办公室B");
		collects.add("办公室C");
		collects.add("办公室D");
		collects.add("办公室E");
		for (String office : collects) {
			localMap.put(office, new ArrayList<>());
		}
	}

	private PropertyManagerImp() {
	}
	public static PropertyManager getInstance(){
		if(propertyManager == null){
			propertyManager = new PropertyManagerImp();
		}
		return propertyManager;
	}
	/**
	 * @param property 需要删除的资源
	 * @param local 需要删除资源的位置
	 * @return 返回删除结果
	 */
	private boolean rmProperty(Property property, String local){
		List<Property> lists = localMap.get(local);
		return lists.remove(property);
	}
	/**
	 * 移动资源，从原位置移动到目标位置
	 * @param property
	 * @param src 原位置
	 * @param dest 目标位置
	 * @return
	 */
	private boolean mvProperty(Property property, String src, String dest){
		if (rmProperty(property, src)) {
			localMap.get(dest).add(property);
			return true;
		}
		return false;
	}
	@Override
	public void propertyChange(Property property) {
		//获取资源移动前的位置
		String oldLocal = property.getOldLocal();
		//获取资源移动的目标位置
		String local = property.getLocal();
		//将资源从原位置移动到目标位置
		mvProperty(property, oldLocal, local);
	}
	@Override
	public float getPrice(Property property) {
		//用资源映射查询该资源的价格
		return priceMap.get(property);
	}
	@Override
	public void add(Property property) {
		//将资源添加进资源列表
		propertyLists.add(property);
		//给资源注册资源管理对象
		property.setPropertyManager(this);
		//新添加的资源分配到仓库
		localMap.get(REPOSITORY);
	}
	@Override
	public void allotProperty(int propertyIndex, String local) throws PropertyOSException {
		//从资源列表中查找需要移动的资源
		Property property = propertyLists.get(propertyIndex);
		//如果查找失败则抛出异常
		if (property == null)
			throw new PropertyOSException(ErrCode.找不到资源, "资源下标出错请确认资源下标");
		property.setLocal(local);
	}
	@Override
	public List<Property> getAllProperty() {
		return propertyLists;
	}
	@Override
	public List<Property> getPropertyByLocal(String local) throws PropertyOSException{
		List<Property> properties = localMap.get(local);
		if(properties == null){
			throw new PropertyOSException(ErrCode.找不到资源, "不存在该位置，请检查");
		}
		return properties;
	}
	@Override
	public void registerPrice(Map<Property,Float> map) {
		priceMap.putAll(map);
	}
	@Override
	public List<Property> getPropertyNotLocal(String local) throws PropertyOSException {
		// TODO Auto-generated method stub
		return null;
	}
}
