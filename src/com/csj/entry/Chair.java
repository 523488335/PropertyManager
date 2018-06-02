package com.csj.entry;

/**
 * 椅子的实体类
 * @author 陈尚均
 *
 */
public class Chair extends Property{

	public Chair(int ID,String batch, String local) {
		super(ID,batch, local);
	}
	/**
	 * 为了方便比较椅子，重写桌子的equals方法，我把价格相同的桌子视为相同，方便价格查询，
	 * 影响椅子价格的因素：批号
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
	 * 重写equals后根据规范重写hashCode
	 */
	@Override
	public int hashCode() {
		return this.batch.hashCode();
	}
}
