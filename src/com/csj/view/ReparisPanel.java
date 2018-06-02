package com.csj.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.csj.control.PropertyManager;
import com.csj.entry.Property;
import com.csj.exception.PropertyOSException;

/**
 * 报修页面
 * @author 陈尚均
 *
 */
public class ReparisPanel extends JPanel{

	private static final long serialVersionUID = -8739891611474560499L;
	JButton move;

	public ReparisPanel(){
		setBounds(Client.pageRect);
		setLayout(null);
		move = new JButton("报修");
		move.setBounds(60, 25, 60, 30);
		add(move);
		move.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.getPropertyManager();
				// TODO Auto-generated method stub
				String local = PropertyManager.REPAIR;
				try {
					//获取选中资源
					Property property = Client.getSelectProperty();
					//移动到指定位置
					property.setLocal(local);
					//获取仓库中资源，替换报修资源
					List<Property> list = Client.getPropertyManager().
							getPropertyByLocal(PropertyManager.REPOSITORY);
					//如果报修资源不属于仓库，那么需要给他替换一样的资源
					if (!property.getOldLocal().equals(PropertyManager.REPOSITORY)) {
						boolean falg = false;
						for (Property property2 : list) {
							if (property2.equals(property)) {
								property2.setLocal(property.getOldLocal());
								falg = true;
								break;
							}
						}
						if (falg == false) {
							Client.showError(Client.getClient(), "仓库可供替换资源不足，请告知管理员解决");
						}
					}
					//刷新页面
					Client.updatePropertyList();
				} catch (PropertyOSException e1) {
					Client.showError(Client.getClient(), e1);
				}
			}
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.setFont(Client.LITTLE_FONT);
		g.drawString("上面表格中的是公司所有未报修资源列表，请选中资源行报修资源：", 20, 20);
	}
}
