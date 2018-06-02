package com.csj.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.csj.control.PropertyManager;
import com.csj.entry.Property;
import com.csj.exception.PropertyOSException;

/**
 * 修理页面
 * @author 陈尚均
 *
 */
public class MaintainPanel extends JPanel{

	private static final long serialVersionUID = -8739891611474560499L;
	JButton move;

	public MaintainPanel(){
		setBounds(Client.pageRect);
		setLayout(null);
		move = new JButton("修理完成");
		move.setBounds(60, 25, 90, 30);
		add(move);
		move.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.getPropertyManager();
				// TODO Auto-generated method stub
				String local = PropertyManager.REPOSITORY;
				try {
					//获取选中资源
					Property property = Client.getSelectProperty();
					//移动到指定位置
					property.setLocal(local);
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
		g.drawString("上面表格中的是公司所有修理中资源列表，修理完成后应转入仓库：", 20, 20);
	}
}
