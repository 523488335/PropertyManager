package com.csj.entry;

/**
 * 桌子的实体类
 * @author 陈尚均
 *
 */
public class Desk extends Property{
	
	/**
	 * 颜色，用枚举表示。
	 */
	private Color color;
	/**
	 * 规格，用枚举表示。
	 */
	private Specification specification;
	/**
	 * 缓存计算的hashcode，避免重复计算浪费cpu。
	 */
	private int hashcode = 0;
	
	public static enum Color {
		WHITE,BLACK;
	}
	public static enum Specification {
		LONG(160),SHORT(120);
		/**
		 * 桌子大小
		 */
		private int size;
		/**
		 * 计量单位
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
	 * 为了方便比较桌子，重写桌子的equals方法，我把价格相同的桌子视为相同，方便价格查询，
	 * 影响桌子价格的因素：批号，颜色和规格
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
	 * 重写equals后根据规范重写hashCode
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
