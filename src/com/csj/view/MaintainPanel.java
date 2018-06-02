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
 * ����ҳ��
 * @author ���о�
 *
 */
public class MaintainPanel extends JPanel{

	private static final long serialVersionUID = -8739891611474560499L;
	JButton move;

	public MaintainPanel(){
		setBounds(Client.pageRect);
		setLayout(null);
		move = new JButton("�������");
		move.setBounds(60, 25, 90, 30);
		add(move);
		move.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.getPropertyManager();
				// TODO Auto-generated method stub
				String local = PropertyManager.REPOSITORY;
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
		g.drawString("�������е��ǹ�˾������������Դ�б�������ɺ�Ӧת��ֿ⣺", 20, 20);
	}
}
