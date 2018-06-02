package com.csj.view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.csj.control.PropertyManager;
import com.csj.entry.Property;
import com.csj.exception.ErrCode;
import com.csj.exception.PropertyOSException;

/**
 * 分配页面
 * @author 陈尚均
 *
 */
public class MovePanel extends JPanel{

	private static final long serialVersionUID = -8739891611474560499L;
	JTextField dest;
	JButton move;

	public MovePanel(){
		setBounds(Client.pageRect);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		dest = new JTextField(5);
		setLayout(null);
		dest.setBounds(120, 25, 90, 30);
		add(dest);
		move = new JButton("分配");
		move.setBounds(220, 25, 60, 30);
		add(move);
		move.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String local = dest.getText().trim();
				if (local.equals(PropertyManager.REPAIR) ||  
					local.equals(PropertyManager.REPOSITORY)) {
					Client.showError(Client.getClient(), new PropertyOSException(
							ErrCode.流程出错, "不能分配到仓库或修理站等特殊区域"));
				}
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
		g.drawString("上面表格中的是公司所有未分配资源列表，请选中资源行分配资源：", 20, 20);
		g.drawString("移动到:", 40, 45);
	}
}
