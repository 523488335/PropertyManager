package com.csj.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * ��ӭҳ��
 * @author ���о�
 *
 */
public class WelcomePanel extends JPanel{

	private static final long serialVersionUID = -8739891611474560499L;

	public WelcomePanel(){
		setFont(new Font("����", 30, 30));
		setBounds(Client.pageRect);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Font font=new Font("����",Font.BOLD,50);
		g.setFont(font);
		Color c=g.getColor();
		g.setColor(Color.red);
		g.drawString("��ӭʹ����Դ����ϵͳ", 100, 100);
		g.setColor(c);
	}
}
