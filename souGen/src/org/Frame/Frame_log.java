package org.Frame;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Frame_log {
	JFrame jf;
	JTextArea jt;

	public Frame_log() {
		jf = new JFrame();
		jt = new JTextArea();
		jt.setSelectedTextColor(Color.RED);
		jt.setLineWrap(true); // 激活自动换行功能
		jt.setWrapStyleWord(true);
		jt.setSize(350, 300);
		jt.setEditable(false);

		jf.add(new JScrollPane(jt));
		jf.setResizable(true);
		jf.setBounds(50, 50, 400, 350);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void append(String content) {
		jt.append(content);
	}
}
