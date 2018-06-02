package com.csj.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.csj.control.PropertyManager;
import com.csj.control.PropertyManagerSimpleImp;
import com.csj.entry.Chair;
import com.csj.entry.Desk;
import com.csj.entry.Desk.Specification;
import com.csj.entry.Property;
import com.csj.exception.ErrCode;
import com.csj.exception.PropertyOSException;

public class Client extends JFrame{

	private static final long serialVersionUID = -8667789053222259951L;
	public static final Font LITTLE_FONT = new Font("����", Font.BOLD, 20);
	public static final String[] columnNames = {"���","��Դ����","����","�۸�/Ԫ","λ��"};
	public static final Rectangle pageRect = new Rectangle(0, 600, 800, 400);
	private Map<Labels,JPanel> labelMaps = new HashMap<>();
	
	private static PropertyManager propertyManager = PropertyManagerSimpleImp.getInstance();
	
	private static DefaultTableModel tableModel;
	private static JTable table;
	
	private static JFrame client;
	
	private static Labels currLabels;
	
	public enum Labels{
		��ӭ {
			@Override
			public void updatePropertyList() {
				//��ѯȫ����Դ
				List<Property> list = propertyManager.getAllProperty();
				updatePropertyList(list);
			}
		},��� {
			@Override
			public void updatePropertyList() {
				//��ѯȫ����Դ
				List<Property> list = propertyManager.getAllProperty();
				updatePropertyList(list);
			}
		},���� {
			@Override
			public void updatePropertyList() throws PropertyOSException {
				//��ѯ�ֿ�����Դ
				List<Property> list = propertyManager.getPropertyByLocal(PropertyManager.REPOSITORY);
				updatePropertyList(list);
			}
		},���� {
			@Override
			public void updatePropertyList() throws PropertyOSException {
				//��ѯ��������վ�е���Դ
				List<Property> list = propertyManager.getPropertyNotLocal(PropertyManager.REPAIR);
				updatePropertyList(list);
			}
		},ά�� {
			@Override
			public void updatePropertyList() throws PropertyOSException {
				//��ѯ��������Դ
				List<Property> list = propertyManager.getPropertyByLocal(PropertyManager.REPAIR);
				updatePropertyList(list);
			}
		};
		public abstract void updatePropertyList() throws PropertyOSException;
		/**
		 * ������Դ�б�
		 * @param list ��Ҫ��ʾ����Դ
		 */
		private static void updatePropertyList(List<Property> list) {
			DefaultTableModel model = getTableModel();
			model.getDataVector().removeAllElements();
			model.setDataVector(listToStr2D(list), columnNames);
		}
	}
	{
		labelMaps.put(Labels.��ӭ, new WelcomePanel());
		labelMaps.put(Labels.���, new AddPropertyPanel());
		labelMaps.put(Labels.����, new MovePanel());
		labelMaps.put(Labels.����, new ReparisPanel());
		labelMaps.put(Labels.ά��, new MaintainPanel());
	}
	
	public static void main(String[] args) {
		client = new Client();
	}
	public Client(){
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setTitle("��ҳ��");
        initView();
        setVisible(true);
    }
	
	public void initView(){
		//���ùرհ�ť����
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //����һ���˵���
        JMenuBar menuBar = new JMenuBar();
        //����һ���˵�
        JMenu fileMenu = new JMenu("�л�ҳ��");
        fileMenu.setFont(LITTLE_FONT);
        // һ���˵���ӵ��˵���
        menuBar.add(fileMenu);
        //���� "�ļ�" һ���˵����Ӳ˵���ҳ��
        for (Labels labels : labelMaps.keySet()) {
        	JMenuItem item = new JMenuItem(labels.toString() + "ҳ��");
        	item.setFont(LITTLE_FONT);
        	item.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					switchLable(labels);
				}
			});
        	fileMenu.add(item);
        	add(labelMaps.get(labels));
		}
        //��� �Ѳ˵������õ�����
        setJMenuBar(menuBar);
        
        JPanel propertysPanel = new JPanel();
        propertysPanel.setBounds(0, 0, 800, 600);
        add(propertysPanel);
        String[][] datas = listToStr2D(Client.getPropertyManager().getAllProperty());
        //������Դ�ı��
        tableModel = new DefaultTableModel(datas, columnNames);
        table = new JTable(tableModel);
        // ������ʾ���Ĺ������
        JScrollPane scrollPane = new JScrollPane(table);
        // �����������ӵ��߽粼�ֵ��м�
        propertysPanel.add(scrollPane);
        //���ڴ�ʱ��ʾ��ӭҳ��
        switchLable(Labels.��ӭ);
	}
	/**
	 * ��������ҳ��
	 */
	private void hideAllPage(){
		for (Labels labels : labelMaps.keySet()) {
			labelMaps.get(labels).setVisible(false);
		}
	}
	/**
	 * ����Դ����ת��Ϊ�ַ�����ά���飨��񣩡�
	 * @param list
	 * @return
	 */
	private static String[][] listToStr2D(List<Property> list){
		String[][] str2D = new String[list.size()][5];
		for (int i = 0; i < list.size(); i++) {
			str2D[i] = propertyToStrs(list.get(i));
		}
		return str2D;
	}
	/**
	 * ����Դ����ת��Ϊ�ַ���һά���飨�У���
	 * @param property
	 * @return
	 */
	private static String[] propertyToStrs(Property property){
		String[] strings = new String[5];
		strings[0] = property.getID() + "";
		StringBuilder propertyType = new StringBuilder();
		if (property instanceof Chair) {
			propertyType.append("����");
		}else if (property instanceof Desk) {
			Desk entry = (Desk) property;
			propertyType.append("����(");
			propertyType.append(entry.getColor() + ",");
			propertyType.append(entry.getSpecification().getSize() +
					Specification.getUnits() + ")");
		}
		strings[1] = propertyType.toString();
		strings[2] = property.getBatch();
		try {
			strings[3] = property.getPrice() + "";
		} catch (PropertyOSException e) {
			strings[3] = "��Դδ�Ǽ�";
		}
		strings[4] = property.getLocal();
		return strings;
	}
	
	public void switchLable(Labels labels){
		setTitle(labels.toString());
		hideAllPage();
		if (tableModel != null) {
		}
		//��ʾlabelsҳ��
		labelMaps.get(labels).setVisible(true);
		setCurrLabels(labels);
		try {
			updatePropertyList();
		} catch (PropertyOSException e) {
			showError(e);
		}
	}
	public static void updatePropertyList() throws PropertyOSException{
		getCurrLabels().updatePropertyList();
	}
	public static Property getSelectProperty() throws PropertyOSException{
		int index = table.getSelectedRow();
		if(index == -1){
			throw new PropertyOSException(ErrCode.���̳���, "����ѡ����Դ");
		}
		int propertyNum = Integer.parseInt((String) tableModel.getValueAt(index, 0));
		for (Property property : propertyManager.getAllProperty()) {
			if (property.getID() == propertyNum) {
				return property;
			}
		}
		throw new PropertyOSException(ErrCode.�Ҳ�����Դ, "�Ҳ�����Դ");
	}
	
	public static void showError(Component component,String msg){
		JOptionPane.showMessageDialog(component, msg, "��ʾ",JOptionPane.WARNING_MESSAGE);
	}
	public static void showError(Component component,PropertyOSException e){
		JOptionPane.showMessageDialog(component, e.getErrCode() + ":" + e.getMessage(), "������Ϣ",JOptionPane.WARNING_MESSAGE);
	}
	public void showError(String msg){
		showError(this, msg);
	}
	public void showError(PropertyOSException e){
		showError(this, e);
	}
	public static DefaultTableModel getTableModel() {
		return tableModel;
	}
	public static void setTableModel(DefaultTableModel tableModel) {
		Client.tableModel = tableModel;
	}
	public static JTable getTable() {
		return table;
	}
	public static void setTable(JTable table) {
		Client.table = table;
	}
	public static JFrame getClient() {
		return client;
	}
	public static PropertyManager getPropertyManager() {
		return propertyManager;
	}
	public static Labels getCurrLabels() {
		return currLabels;
	}
	public static void setCurrLabels(Labels currLabels) {
		Client.currLabels = currLabels;
	}
}
