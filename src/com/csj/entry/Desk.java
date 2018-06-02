package com.csj.entry;

/**
 * ���ӵ�ʵ����
 * @author ���о�
 *
 */
public class Desk extends Property{
	
	/**
	 * ��ɫ����ö�ٱ�ʾ��
	 */
	private Color color;
	/**
	 * �����ö�ٱ�ʾ��
	 */
	private Specification specification;
	/**
	 * ��������hashcode�������ظ������˷�cpu��
	 */
	private int hashcode = 0;
	
	public static enum Color {
		WHITE,BLACK;
	}
	public static enum Specification {
		LONG(160),SHORT(120);
		/**
		 * ���Ӵ�С
		 */
		private int size;
		/**
		 * ������λ
		 */
		private final static String Units = "cm";
		private Specification(int size) {
			this.size = size;
		}
		public int getSize() {
			return size;
		}
		public static String getUnits() {
			return Units;
		}
	}
	public Desk(int ID,String batch, String local, Color color,
			Specification specification){
		super(ID, batch, local);
		this.color = color;
		this.specification = specification;
	}
	/**
	 * Ϊ�˷���Ƚ����ӣ���д���ӵ�equals�������ҰѼ۸���ͬ��������Ϊ��ͬ������۸��ѯ��
	 * Ӱ�����Ӽ۸�����أ����ţ���ɫ�͹��
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Desk) {
			Desk desk = (Desk) obj;
			return this.color == desk.color && this.batch.equals(desk.batch)
					&& this.specification == desk.specification;
		}
		return false;
	}
	/**
	 * ��дequals����ݹ淶��дhashCode
	 */
	@Override
	public int hashCode() {
		if (hashcode == 0) {
			int code = 17;
			int coeff = 6;
			code = code*coeff + color.hashCode();
			code = code*coeff + batch.hashCode();
			code = code*coeff + specification.hashCode();
			hashcode = code;
		}
		return hashcode;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Specification getSpecification() {
		return specification;
	}
	public void setSpecification(Specification specification) {
		this.specification = specification;
	}
}
