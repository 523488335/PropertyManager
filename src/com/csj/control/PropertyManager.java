package com.csj.control;

import java.util.List;
import java.util.Map;

import com.csj.entry.Property;
import com.csj.exception.PropertyOSException;

/**
 * ��Դ������ӿ�
 * @author ���о�
 */
public interface PropertyManager {
	public static final String REPOSITORY = "�ֿ�",REPAIR = "����վ";
	/**
	 * ��Դ�ƶ��ص�
	 * @param property ���ƶ�����Դ
	 */
	void propertyChange(Property property) throws PropertyOSException;
	/**
	 * ��ѯ������Դ
	 * @return ������Դ���б�
	 */
	List<Property> getAllProperty();
	/**
	 * ��Դ��ӽӿڡ�
	 * @param property ����ӵ���Դ
	 */
	void add(Property property);
	/**
	 * ��Դ����ӿڡ�
	 * @param property ���������Դ
	 * @param local ����Ŀ�ĵ�
	 */
	void allotProperty(int propertyIndex, String local) throws PropertyOSException;
	/**
	 * ��ѯ��Դ�ӿ�
	 * @return ����localλ���ϵ���Դ
	 */
	List<Property> getPropertyByLocal(String local) throws PropertyOSException;
	/**
	 * ��ѯ��Դ�ӿ�
	 * @return ���г���localλ���ϵ���Դ
	 */
	List<Property> getPropertyNotLocal(String local) throws PropertyOSException;
	/**
	 * @param property Ҫ��ѯ�۸����Դ
	 * ��ѯ��Դ�۸�ӿڡ�
	 */
	float getPrice(Property property);
	/**
	 * @param property Ҫ��ѯ�۸����Դ
	 * �۸�ע��ӿڡ�
	 */
	void registerPrice(Map<Property,Float> priceMap) throws PropertyOSException;
}
