package com.vcode.ticket.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.UIManager;

public class PopList {
	
	static boolean isShow = false;
	
	static Popup mypopup = null;
	
	/**
	 * 
	 * @param conn	控件名称，假如想点击button时弹出窗口，则传入button；如果想label弹出，则传入label
	 * @param listModel	弹出窗中的list数据源
	 * 其它默认设置：默认上方、conn宽度、数据源高度、浅灰色
	 */
	public static void initPopup(final Component conn,final Component conn2,DefaultListModel<Object> listModel){
		initPopup(conn,conn2,listModel,"up");
	}
	
	/**
	 * 
	 * @param conn 控件名称，假如想点击button时弹出窗口，则传入button；如果想label弹出，则传入label
	 * @param listModel	弹出来的List数据源
	 * @param direction	显示在conn哪个方位，有4个选项：up、down、left、right
	 * @param width	弹出窗宽度
	 * @param height	弹出窗高度
	 * @param color	弹出窗底色
	 * @wbp.parser.entryPoint
	 */
	public static void initPopup(final Component conn,final Component conn2,DefaultListModel<Object> listModel,final String direction){
		
		final JList<Object> list = new JList<Object>(listModel);
	    list.setSize(new Dimension(conn.getWidth(), listModel.getSize()*18));
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setBackground(new java.awt.Color(240, 240, 240));
	    list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				@SuppressWarnings("unchecked")
				ListModel<Object> lmodel = ((JList<Object>)conn2).getModel();
				if (lmodel.getSize()>=5) {
					return;
				}
				
				DefaultListModel<Object> model = new DefaultListModel<Object>();
				for (int i =0;i<lmodel.getSize();i++) {
					model.addElement(lmodel.getElementAt(i));
				}
				if (!model.contains(list.getSelectedValue().toString())){
					model.addElement(list.getSelectedValue().toString());
				}
				((JList<Object>)conn2).setModel(model);
			}
			
			public void mouseExited(MouseEvent e) {
				if (isShow) {
        			if (mypopup!=null) {
        				mypopup.hide();
            			isShow = false;
        			}
        		}
			}
		});
	    list.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				list.setSelectedIndex(list.locationToIndex(e.getPoint()));
			}
		});
	    
	    conn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isShow) {
        			if (mypopup!=null) {
        				mypopup.hide();
            			isShow = false;
        			}
        		}else {
        			if (mypopup != null) mypopup.hide();
        			Point point = conn.getLocationOnScreen();
        			int x = conn.getWidth();
        			int y = conn.getHeight();
        			
        			JPanel jpanel = new JPanel();
        	        jpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0xAA, 0xAA, 0xAA)));
        	        jpanel.setPreferredSize(new Dimension(x, list.getHeight()+20));
        	        jpanel.add(list);
        	        
        	        int pupX = 0;
        	        int pupY = 0;
        	        if ("up".equals(direction)) {
        	        	pupX = (int)point.getX();
        	        	pupY = (int)(point.getY()-(list.getHeight()+20));
        	        }else if ("down".equals(direction)){
        	        	pupX = (int)point.getX();
        	        	pupY = (int)(point.getY()+y);
        	        }else if ("left".equals(direction)){
        	        	pupX = (int)point.getX()-x;
        	        	pupY = (int)(point.getY());
        	        }else if ("right".equals(direction)){
        	        	pupX = (int)point.getX()+x;
        	        	pupY = (int)(point.getY());
        	        }
        	        
        	        mypopup = PopupFactory.getSharedInstance().getPopup(
        	        		conn,jpanel, pupX,pupY);
        	        mypopup.show();
        			isShow = true;
        		}
			}
			
		});
	}
	
	/**
	 * 更多日期弹出框
	 * @param conn	更多日期选择框
	 * @param ListData	弹出框内容的数据源
	 * @param direction	弹出框方位，分别为在conn上下左右4个方向弹出，值分别为：up,down,left,right
	 */
	public static void moreDatePopup(final Component conn, String[] ListData, final String direction){
		InstallData[] options = new InstallData[ListData.length];
		for (int i=0;i<ListData.length;i++) {
			options[i] = new InstallData(ListData[i]);
		}
		
		JList<InstallData> list = new JList<InstallData>(options);
		list.setBackground(new java.awt.Color(240, 240, 240));
		CheckListCellRenderer renderer = new CheckListCellRenderer();
		
		JScrollPane JscrollPanel = new JScrollPane();
		JscrollPanel.setPreferredSize(new Dimension(conn.getWidth(), options.length*18+3));
		
		JscrollPanel.setViewportView(list);
		list.setCellRenderer(renderer);
		
		CheckListener lst = new CheckListener(list); 
		list.addMouseListener(lst); 
		list.addKeyListener(lst);
		
        conn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isShow) {
        			if (mypopup!=null) {
        				mypopup.hide();
            			isShow = false;
        			}
        		}else {
        			if (mypopup != null) mypopup.hide();
    				Point point = conn.getLocationOnScreen();
    		        int pupX = 0;
    		        int pupY = 0;
    		        int x = conn.getWidth();
    				int y = conn.getHeight();
    				
    		        if ("up".equals(direction)) {
    		        	pupX = (int)point.getX();
    		        	pupY = (int)(point.getY()-JscrollPanel.getPreferredSize().getHeight());
    		        }else if ("down".equals(direction)){
    		        	pupX = (int)point.getX();
    		        	pupY = (int)(point.getY()+y);
    		        }else if ("left".equals(direction)){
    		        	pupX = (int)point.getX()-x;
    		        	pupY = (int)(point.getY());
    		        }else if ("right".equals(direction)){
    		        	pupX = (int)point.getX()+x;
    		        	pupY = (int)(point.getY());
    		        }
    				
    				mypopup = PopupFactory.getSharedInstance().getPopup(
    						conn,JscrollPanel, pupX,pupY);
    		        mypopup.show();
        			isShow = true;
        		}
			}
		});
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JFrame frame = new JFrame("");
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JCheckBox btnTest = new JCheckBox("更多日期");
		btnTest.setBounds(174, 106, 93, 23);
		frame.getContentPane().add(btnTest);
		
		PopList.moreDatePopup(btnTest,new String[]{"111","222","333"} , "up");
		frame.setVisible(true);
	}
}
