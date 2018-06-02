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
 * 这个实现将所有的资源保存在同一个容器中，适合管理中小量的数据。
 * @author 陈尚均
 */
public class PropertyManagerSimpleImp implements PropertyManager{
	
	private Set<String> localSets = new HashSet<>();
	
	private static PropertyManager propertyManager;
	/**
	 * 资源列表，保存了公司的所有资源。
	 */
	private List<Property> propertyLists = new ArrayList<>();
	/**
	 * 价格映射，保存了每种被视为不同资源的价格。
	 */
	private Map<Property,Float> priceMap = new HashMap<>();
	
	{
		localSets.add(REPOSITORY);
		localSets.add(REPAIR);
		localSets.add("办公室A");
		localSets.add("办公室B");
		localSets.add("办公室C");
		localSets.add("办公室D");
		localSets.add("办公室E");
	}
	
	private PropertyManagerSimpleImp() {
	}
	public static PropertyManager getInstance(){
		if(propertyManager == null){
			propertyManager = new PropertyManagerSimpleImp();
		}
		return propertyManager;
	}
	@Override
	public void propertyChange(Property property) throws PropertyOSException{
		//获取资源移动前的位置
		String oldLocal = property.getOldLocal();
		//获取资源移动的目标位置
		String local = property.getLocal();
		if (!localSets.contains(local)) {
			property.setLocal(oldLocal);
			throw new PropertyOSException(ErrCode.输入信息有误, "该位置不存在，无法移动");
		}
		//打印日志
		System.out.println("ID=" + property.getID() + "的资源被移动,原位置："
		+ oldLocal + "，现位置：" + local);
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
	public List<Property> getPropertyByLocal(String local) throws PropertyOSException {
		if (!localSets.contains(local)) {
			throw new PropertyOSException(ErrCode.找不到资源, "不存在改位置,请检查");
		}
		List<Property> properties = new ArrayList<>();
		for (Property property : propertyLists) {
			if(property.getLocal().equals(local)){
				properties.add(property);
			}
		}
		return properties;
	}
	@Override
	public void registerPrice(Map<Property,Float> map) throws PropertyOSException{
		if (map == null || map.size() == 0) {
			return;
		}
		//取出map中任意一元素
		Property property = null;
		for (Property key : map.keySet()) {
			property = key;
			break;
		}
		//将map中任意一元素与价格映射中所有元素批号相比较，看是否存在相同批号，存在则抛出异常
		for (Property key : priceMap.keySet()) {
			if (key.getBatch().equals(property.getBatch())) {
				throw new PropertyOSException(ErrCode.流程出错,"批次重复,请确认批次");
			}
		}
		priceMap.putAll(map);
	}
	@Override
	public List<Property> getPropertyNotLocal(String local) throws PropertyOSException {
		List<Property> properties = new ArrayList<>();
		for (Property property : propertyLists) {
			if(!property.getLocal().equals(local)){
				properties.add(property);
			}
		}
		return properties;
	}
}
