package com.vcode.ticket.utils;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

/**
 * CheckBoxList的勾选与取消勾选
 * @author hh
 *
 */
public class CheckListener implements MouseListener, KeyListener {
	
	protected JList m_list;

	public CheckListener(JList parent) {
		m_list = parent;
	}

	public void mouseClicked(MouseEvent e) {
		doCheck();
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == ' ')
			doCheck();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	protected void doCheck() {
		int index = m_list.getSelectedIndex();
		if (index < 0)
			return;
		InstallData data = (InstallData) m_list.getModel().getElementAt(index);
		data.invertSelected();
		m_list.repaint();
	}
}