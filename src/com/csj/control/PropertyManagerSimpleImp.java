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
 * ��Դ������ʵ�֣�ʵ������Դ����ӿڣ����õ�����ơ�
 * ���ʵ�ֽ����е���Դ������ͬһ�������У��ʺϹ�����С�������ݡ�
 * @author ���о�
 */
public class PropertyManagerSimpleImp implements PropertyManager{
	
	private Set<String> localSets = new HashSet<>();
	
	private static PropertyManager propertyManager;
	/**
	 * ��Դ�б������˹�˾��������Դ��
	 */
	private List<Property> propertyLists = new ArrayList<>();
	/**
	 * �۸�ӳ�䣬������ÿ�ֱ���Ϊ��ͬ��Դ�ļ۸�
	 */
	private Map<Property,Float> priceMap = new HashMap<>();
	
	{
		localSets.add(REPOSITORY);
		localSets.add(REPAIR);
		localSets.add("�칫��A");
		localSets.add("�칫��B");
		localSets.add("�칫��C");
		localSets.add("�칫��D");
		localSets.add("�칫��E");
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
		//��ȡ��Դ�ƶ�ǰ��λ��
		String oldLocal = property.getOldLocal();
		//��ȡ��Դ�ƶ���Ŀ��λ��
		String local = property.getLocal();
		if (!localSets.contains(local)) {
			property.setLocal(oldLocal);
			throw new PropertyOSException(ErrCode.������Ϣ����, "��λ�ò����ڣ��޷��ƶ�");
		}
		//��ӡ��־
		System.out.println("ID=" + property.getID() + "����Դ���ƶ�,ԭλ�ã�"
		+ oldLocal + "����λ�ã�" + local);
	}
	@Override
	public float getPrice(Property property) {
		//����Դӳ���ѯ����Դ�ļ۸�
		return priceMap.get(property);
	}
	@Override
	public void add(Property property) {
		//����Դ��ӽ���Դ�б�
		propertyLists.add(property);
		//����Դע����Դ�������
		property.setPropertyManager(this);
	}
	@Override
	public void allotProperty(int propertyIndex, String local) throws PropertyOSException {
		//����Դ�б��в�����Ҫ�ƶ�����Դ
		Property property = propertyLists.get(propertyIndex);
		//�������ʧ�����׳��쳣
		if (property == null)
			throw new PropertyOSException(ErrCode.�Ҳ�����Դ, "��Դ�±������ȷ����Դ�±�");
		property.setLocal(local);
	}
	@Override
	public List<Property> getAllProperty() {
		return propertyLists;
	}
	@Override
	public List<Property> getPropertyByLocal(String local) throws PropertyOSException {
		if (!localSets.contains(local)) {
			throw new PropertyOSException(ErrCode.�Ҳ�����Դ, "�����ڸ�λ��,����");
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
		//ȡ��map������һԪ��
		Property property = null;
		for (Property key : map.keySet()) {
			property = key;
			break;
		}
		//��map������һԪ����۸�ӳ��������Ԫ��������Ƚϣ����Ƿ������ͬ���ţ��������׳��쳣
		for (Property key : priceMap.keySet()) {
			if (key.getBatch().equals(property.getBatch())) {
				throw new PropertyOSException(ErrCode.���̳���,"�����ظ�,��ȷ������");
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
