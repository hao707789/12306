package com.vcode.ticket.utils;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * CheckBoxList类
 * @author hh
 *
 */
public class CheckListCellRenderer extends JCheckBox implements ListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7005091686507720066L;
	protected static Border m_noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	public CheckListCellRenderer() {
		super();
		setOpaque(true);
		setBorder(m_noFocusBorder);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setText(value.toString());

		setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		InstallData data = (InstallData) value;
		setSelected(data.isSelected());
		setFont(list.getFont());
		setBorder((cellHasFocus) ? UIManager.getBorder("List.focusCellHighlightBorder") : m_noFocusBorder);
		return this;
	}
}