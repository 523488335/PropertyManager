package com.csj.entry;

/**
 * ���ӵ�ʵ����
 * @author ���о�
 *
 */
public class Chair extends Property{

	public Chair(int ID,String batch, String local) {
		super(ID,batch, local);
	}
	/**
	 * Ϊ�˷���Ƚ����ӣ���д���ӵ�equals�������ҰѼ۸���ͬ��������Ϊ��ͬ������۸��ѯ��
	 * Ӱ�����Ӽ۸�����أ�����
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Chair) {
			Chair chair = (Chair) obj;
			return this.batch.equals(chair.batch);
		}
		return false;
	}
	/**
	 * ��дequals����ݹ淶��дhashCode
	 */
	@Override
	public int hashCode() {
		return this.batch.hashCode();
	}
}
