package com.csj.entry;

import com.csj.control.PropertyManager;
import com.csj.exception.ErrCode;
import com.csj.exception.PropertyOSException;

/**
 * ��Դ�࣬��˾������Դ����
 * @author ���о�
 */
public class Property {

	protected final int ID;
	protected String batch;
	protected PropertyManager propertyManager;
	private String oldLocal = null;
	protected String local;
	
	public Property(int ID, String batch, String local) {
		super();
		this.ID = ID;
		this.batch = batch;
		this.local = local;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) throws PropertyOSException {
		//�����ƶ�ǰ��λ��
		this.oldLocal = this.local;
		this.local = local;
		if (propertyManager != null) {
			propertyManager.propertyChange(this);
		}
	}
	
	public float getPrice() throws PropertyOSException{
		if(propertyManager == null){
			throw new PropertyOSException(ErrCode.���̳���,"���ܲ���δע����Դ�ļ۸�");
		}
		return propertyManager.getPrice(this);
	}

	public String getOldLocal() {
		return oldLocal;
	}

	public void setPropertyManager(PropertyManager propertyManager) {
		this.propertyManager = propertyManager;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	public int getID() {
		return ID;
	}
}
