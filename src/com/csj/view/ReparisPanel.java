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
 * ����ҳ��
 * @author ���о�
 *
 */
public class ReparisPanel extends JPanel{

	private static final long serialVersionUID = -8739891611474560499L;
	JButton move;

	public ReparisPanel(){
		setBounds(Client.pageRect);
		setLayout(null);
		move = new JButton("����");
		move.setBounds(60, 25, 60, 30);
		add(move);
		move.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client.getPropertyManager();
				// TODO Auto-generated method stub
				String local = PropertyManager.REPAIR;
				try {
					//��ȡѡ����Դ
					Property property = Client.getSelectProperty();
					//�ƶ���ָ��λ��
					property.setLocal(local);
					//��ȡ�ֿ�����Դ���滻������Դ
					List<Property> list = Client.getPropertyManager().
							getPropertyByLocal(PropertyManager.REPOSITORY);
					//���������Դ�����ڲֿ⣬��ô��Ҫ�����滻һ������Դ
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
							Client.showError(Client.getClient(), "�ֿ�ɹ��滻��Դ���㣬���֪����Ա���");
						}
					}
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
		g.drawString("�������е��ǹ�˾����δ������Դ�б���ѡ����Դ�б�����Դ��", 20, 20);
	}
}
