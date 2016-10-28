package com.vcode.http.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;

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
				if(e.getClickCount()==2){
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
	    
	    conn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
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
}
