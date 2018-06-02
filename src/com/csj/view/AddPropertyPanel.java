package com.csj.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.csj.control.PropertyManager;
import com.csj.entry.Chair;
import com.csj.entry.Desk;
import com.csj.entry.Desk.Color;
import com.csj.entry.Desk.Specification;
import com.csj.entry.Property;
import com.csj.exception.ErrCode;
import com.csj.exception.PropertyOSException;
/**
 * 添加资源页面
 * @author 陈尚均
 *
 */
public class AddPropertyPanel extends JPanel{
	
	private static final long serialVersionUID = 5832807406123015103L;
	JTextField[] nums = new JTextField[5],prices = new JTextField[5];
	JTextField batchIn;
	JButton submit;
	
	private static int propertyID = 0;
	

	public AddPropertyPanel(){
		setBounds(Client.pageRect);
		setLayout(null);
		initView();
	}
	
	private void initView() {
		for (int i = 0; i < nums.length; i++) {
			JTextField num = new JTextField(5);
			num.setBounds(240, 50 + 25 * i, 60, 20);
			num.setText("0");
			add(num);
			nums[i] = num;
		}
		for (int i = 0; i < nums.length; i++) {
			JTextField price = new JTextField(5);
			price.setBounds(460, 50 + 25 * i, 60, 20);
			price.setText("0");
			add(price);
			prices[i] = price;
		}
		batchIn = new JTextField(5);
		batchIn.setBounds(240, 50 + 25 * nums.length, 160, 25);
		add(batchIn);
		submit = new JButton("提交");
		submit.setBounds(240, 50 + 25 * nums.length + 40, 60, 40);
		add(submit);
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] propertyNums = new int[nums.length];
				float[] propertyPrices = new float[prices.length];
				String batch = batchIn.getText().trim();
				try {
					if (batch == null || batch.length() == 0) {
						Client.showError(Client.getClient(),
								new PropertyOSException(ErrCode.输入信息有误, "批号不能为空"));
						return;
					}
					for (int i = 0; i < nums.length; i++) {
						propertyNums[i] = Integer.parseInt(nums[i].getText());
						propertyPrices[i] = Integer.parseInt(prices[i].getText());
						if (propertyNums[i] < 0 || propertyPrices[i] < 0) {
							Client.showError(Client.getClient(), 
									new PropertyOSException(ErrCode.输入信息有误, "数量和价格不可小于0,请检查输入"));
							return;
						}
					}
					registerProperty(propertyNums,propertyPrices,batch);
					//刷新资源列表
					Client.updatePropertyList();
				} catch (NumberFormatException e2) {
					Client.showError(Client.getClient(), 
							new PropertyOSException(ErrCode.输入信息有误, "数量只能为整数，价格可以为分数,请检查输入"));
				} catch (PropertyOSException e2) {
					Client.showError(Client.getClient(), e2);
				}
			}
		});
	}
	
	private void registerProperty(int[] propertyNums, float[] propertyPrices, String batch) throws PropertyOSException{
		//储存需要注册的价格类型
		Map<Property, Float> priceMap = new HashMap<>();
		for (int i = 0; i < propertyNums[0]; i++) {
			Property property = new Desk(propertyID++,
					batch, PropertyManager.REPOSITORY, Color.WHITE, Specification.LONG);
			Client.getPropertyManager().add(property);
			if (i == 0) {
				priceMap.put(property, propertyPrices[0]);
			}
		}
		for (int i = 0; i < propertyNums[1]; i++) {
			Property property = new Desk(propertyID++,
					batch, PropertyManager.REPOSITORY, Color.BLACK, Specification.LONG);
			Client.getPropertyManager().add(property);
			if (i == 0) {
				priceMap.put(property, propertyPrices[1]);
			}
		}
		for (int i = 0; i < propertyNums[2]; i++) {
			Property property = new Desk(propertyID++,
					batch, PropertyManager.REPOSITORY, Color.WHITE, Specification.SHORT);
			Client.getPropertyManager().add(property);
			if (i == 0) {
				priceMap.put(property, propertyPrices[2]);
			}
		}
		for (int i = 0; i < propertyNums[3]; i++) {
			Property property = new Desk(propertyID++,
					batch, PropertyManager.REPOSITORY, Color.BLACK, Specification.SHORT);
			Client.getPropertyManager().add(property);
			if (i == 0) {
				priceMap.put(property, propertyPrices[3]);
			}
		}
		for (int i = 0; i < propertyNums[4]; i++) {
			Property property = new Chair(propertyID++, batch, PropertyManager.REPOSITORY);
			Client.getPropertyManager().add(property);
			if (i == 0) {
				priceMap.put(property, propertyPrices[4]);
			}
		}
		//为资源注册价格
		Client.getPropertyManager().registerPrice(priceMap);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setFont(Client.LITTLE_FONT);
		g.drawString("上面表格中的是公司所有资源列表，请参照表格采购所需资源：", 20, 20);
		g.drawString("类型               数量                单价", 40, 45);
		g.drawString("桌子(white,160cm)", 40, 70);
		g.drawString("桌子(black,160cm)", 40, 95);
		g.drawString("桌子(white,120cm)", 40, 120);
		g.drawString("桌子(black,120cm)", 40, 145);
		g.drawString("椅子", 40, 170);
		g.drawString("批号：", 40, 195);
	}
}
