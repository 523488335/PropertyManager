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
 * ����ҳ��
 * @author ���о�
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
		move = new JButton("����");
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
							ErrCode.���̳���, "���ܷ��䵽�ֿ������վ����������"));
				}
				try {
					//��ȡѡ����Դ
					Property property = Client.getSelectProperty();
					//�ƶ���ָ��λ��
					property.setLocal(local);
					//ˢ��ҳ��
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
		g.drawString("�������е��ǹ�˾����δ������Դ�б���ѡ����Դ�з�����Դ��", 20, 20);
		g.drawString("�ƶ���:", 40, 45);
	}
}
