package com.vcode.ticket.utils;
/**
 * CheckBoxList的内容类
 * @author hh
 *
 */
public class InstallData {
	
	protected String value;

	protected boolean m_selected;

	public InstallData(String data) {
		m_selected = false;
		value = data;
	}

	public void setSelected(boolean selected) {
		m_selected = selected;
	}

	public void invertSelected() {
		m_selected = !m_selected;
	}

	public boolean isSelected() {
		return m_selected;
	}
	
	public String toString(){
		return value;
	}
}