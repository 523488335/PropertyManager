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
	public static final Font LITTLE_FONT = new Font("宋体", Font.BOLD, 20);
	public static final String[] columnNames = {"序号","资源类型","批次","价格/元","位置"};
	public static final Rectangle pageRect = new Rectangle(0, 600, 800, 400);
	private Map<Labels,JPanel> labelMaps = new HashMap<>();
	
	private static PropertyManager propertyManager = PropertyManagerSimpleImp.getInstance();
	
	private static DefaultTableModel tableModel;
	private static JTable table;
	
	private static JFrame client;
	
	private static Labels currLabels;
	
	public enum Labels{
		欢迎 {
			@Override
			public void updatePropertyList() {
				//查询全部资源
				List<Property> list = propertyManager.getAllProperty();
				updatePropertyList(list);
			}
		},添加 {
			@Override
			public void updatePropertyList() {
				//查询全部资源
				List<Property> list = propertyManager.getAllProperty();
				updatePropertyList(list);
			}
		},分配 {
			@Override
			public void updatePropertyList() throws PropertyOSException {
				//查询仓库中资源
				List<Property> list = propertyManager.getPropertyByLocal(PropertyManager.REPOSITORY);
				updatePropertyList(list);
			}
		},报修 {
			@Override
			public void updatePropertyList() throws PropertyOSException {
				//查询不在修理站中的资源
				List<Property> list = propertyManager.getPropertyNotLocal(PropertyManager.REPAIR);
				updatePropertyList(list);
			}
		},维修 {
			@Override
			public void updatePropertyList() throws PropertyOSException {
				//查询修理中资源
				List<Property> list = propertyManager.getPropertyByLocal(PropertyManager.REPAIR);
				updatePropertyList(list);
			}
		};
		public abstract void updatePropertyList() throws PropertyOSException;
		/**
		 * 更新资源列表
		 * @param list 需要显示的资源
		 */
		private static void updatePropertyList(List<Property> list) {
			DefaultTableModel model = getTableModel();
			model.getDataVector().removeAllElements();
			model.setDataVector(listToStr2D(list), columnNames);
		}
	}
	{
		labelMaps.put(Labels.欢迎, new WelcomePanel());
		labelMaps.put(Labels.添加, new AddPropertyPanel());
		labelMaps.put(Labels.分配, new MovePanel());
		labelMaps.put(Labels.报修, new ReparisPanel());
		labelMaps.put(Labels.维修, new MaintainPanel());
	}
	
	public static void main(String[] args) {
		client = new Client();
	}
	public Client(){
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setTitle("主页面");
        initView();
        setVisible(true);
    }
	
	public void initView(){
		//设置关闭按钮监听
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //创建一个菜单栏
        JMenuBar menuBar = new JMenuBar();
        //创建一级菜单
        JMenu fileMenu = new JMenu("切换页面");
        fileMenu.setFont(LITTLE_FONT);
        // 一级菜单添加到菜单栏
        menuBar.add(fileMenu);
        //创建 "文件" 一级菜单的子菜单和页面
        for (Labels labels : labelMaps.keySet()) {
        	JMenuItem item = new JMenuItem(labels.toString() + "页面");
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
        //最后 把菜单栏设置到窗口
        setJMenuBar(menuBar);
        
        JPanel propertysPanel = new JPanel();
        propertysPanel.setBounds(0, 0, 800, 600);
        add(propertysPanel);
        String[][] datas = listToStr2D(Client.getPropertyManager().getAllProperty());
        //创建资源的表格
        tableModel = new DefaultTableModel(datas, columnNames);
        table = new JTable(tableModel);
        // 创建显示表格的滚动面板
        JScrollPane scrollPane = new JScrollPane(table);
        // 将滚动面板添加到边界布局的中间
        propertysPanel.add(scrollPane);
        //窗口打开时显示欢迎页面
        switchLable(Labels.欢迎);
	}
	/**
	 * 隐藏所有页面
	 */
	private void hideAllPage(){
		for (Labels labels : labelMaps.keySet()) {
			labelMaps.get(labels).setVisible(false);
		}
	}
	/**
	 * 将资源集合转换为字符串二维数组（表格）。
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
	 * 将资源集合转换为字符串一维数组（行）。
	 * @param property
	 * @return
	 */
	private static String[] propertyToStrs(Property property){
		String[] strings = new String[5];
		strings[0] = property.getID() + "";
		StringBuilder propertyType = new StringBuilder();
		if (property instanceof Chair) {
			propertyType.append("椅子");
		}else if (property instanceof Desk) {
			Desk entry = (Desk) property;
			propertyType.append("桌子(");
			propertyType.append(entry.getColor() + ",");
			propertyType.append(entry.getSpecification().getSize() +
					Specification.getUnits() + ")");
		}
		strings[1] = propertyType.toString();
		strings[2] = property.getBatch();
		try {
			strings[3] = property.getPrice() + "";
		} catch (PropertyOSException e) {
			strings[3] = "资源未登记";
		}
		strings[4] = property.getLocal();
		return strings;
	}
	
	public void switchLable(Labels labels){
		setTitle(labels.toString());
		hideAllPage();
		//显示labels页面
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
			throw new PropertyOSException(ErrCode.流程出错, "请先选择资源");
		}
		int propertyNum = Integer.parseInt((String) tableModel.getValueAt(index, 0));
		for (Property property : propertyManager.getAllProperty()) {
			if (property.getID() == propertyNum) {
				return property;
			}
		}
		throw new PropertyOSException(ErrCode.找不到资源, "找不到资源");
	}
	
	public static void showError(Component component,String msg){
		JOptionPane.showMessageDialog(component, msg, "提示",JOptionPane.WARNING_MESSAGE);
	}
	public static void showError(Component component,PropertyOSException e){
		JOptionPane.showMessageDialog(component, e.getErrCode() + ":" + e.getMessage(), "错误信息",JOptionPane.WARNING_MESSAGE);
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
