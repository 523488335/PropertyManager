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
 * ���ʵ�ֽ���Դͨ����ŵص���з��࣬����ڲ�ͬ�����У��ʺϹ���������ݡ�
 * @author ���о�
 * 
 */
public class PropertyManagerImp implements PropertyManager{
	
	private final String REPOSITORY = "�ֿ�",REPAIR = "����վ";
	
	/**
	 * �洢�������ڵ�λ��
	 */
	private Set<String> collects = new HashSet<>();
	
	private static PropertyManager propertyManager = null;
	
	/**
	 * ��Դ�б������˹�˾��������Դ��
	 */
	private List<Property> propertyLists;
	/**
	 * �۸�ӳ�䣬������ÿ�ֱ���Ϊ��ͬ��Դ�ļ۸�
	 */
	private Map<Property,Float> priceMap = new HashMap<>();
	/**
	 * ��Դλ��ӳ�䣬������ÿ����Դ����ŵ�λ�á�
	 */
	private Map<String,List<Property>> localMap = new HashMap<>();
	
	{
		collects.add(REPAIR);
		collects.add(REPOSITORY);
		collects.add("�칫��A");
		collects.add("�칫��B");
		collects.add("�칫��C");
		collects.add("�칫��D");
		collects.add("�칫��E");
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
	 * @param property ��Ҫɾ������Դ
	 * @param local ��Ҫɾ����Դ��λ��
	 * @return ����ɾ�����
	 */
	private boolean rmProperty(Property property, String local){
		List<Property> lists = localMap.get(local);
		return lists.remove(property);
	}
	/**
	 * �ƶ���Դ����ԭλ���ƶ���Ŀ��λ��
	 * @param property
	 * @param src ԭλ��
	 * @param dest Ŀ��λ��
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
		//��ȡ��Դ�ƶ�ǰ��λ��
		String oldLocal = property.getOldLocal();
		//��ȡ��Դ�ƶ���Ŀ��λ��
		String local = property.getLocal();
		//����Դ��ԭλ���ƶ���Ŀ��λ��
		mvProperty(property, oldLocal, local);
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
		//����ӵ���Դ���䵽�ֿ�
		localMap.get(REPOSITORY);
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
	public List<Property> getPropertyByLocal(String local) throws PropertyOSException{
		List<Property> properties = localMap.get(local);
		if(properties == null){
			throw new PropertyOSException(ErrCode.�Ҳ�����Դ, "�����ڸ�λ�ã�����");
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
