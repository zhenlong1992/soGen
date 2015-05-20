package org.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import org.es_process.CreateIndex;

public class Frame_main extends JFrame {
	private JPanel outer;
	private JLabel one;
	private JLabel two;
	private JLabel three;
	private JLabel four;
	private JLabel five;
	private JTextField jt1;
	private JTextField jt2;
	private JTextField jt3;
	private JTextField jt4;
	private JTextField jt5;
	private JButton jb;


	public static void main(String[] args) {
		Frame_main inst = new Frame_main();
		inst.setResizable(false);
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public Frame_main() {
		initGUI();
	}

	private void initGUI() {
		try {
			setTitle("搜根");
			getContentPane().setLayout(null);
			{
				outer = new JPanel();
				getContentPane().add(outer);
				outer.setBounds(30, 30, 350, 250);
				outer.setBorder(BorderFactory.createTitledBorder("填写信息"));
				outer.setLayout(null);
				{
					one = new JLabel();
					outer.add(one);
					one.setText("名称(小写)");
					one.setBounds(44, 56, 69, 15);
				}
				{
					two = new JLabel();
					outer.add(two);
					two.setText("type名称");
					two.setBounds(44, 90, 69, 15);
				}
				{
					three = new JLabel();
					outer.add(three);
					three.setText("文件路径");
					three.setBounds(44, 126, 69, 15);
				}
				{
					four = new JLabel();
					outer.add(four);
					four.setText("ip地址");
					four.setBounds(44, 160, 69, 15);
				}
				{
					five = new JLabel();
					outer.add(five);
					five.setText("端口");
					five.setBounds(44, 196, 69, 15);
				}
				{
					jt1 = new JTextField();
					outer.add(jt1);
					jt1.setText("");
					jt1.setBounds(119, 52, 133, 22);
				}
				{
					jt2 = new JTextField();
					outer.add(jt2);
					jt2.setText("");
					jt2.setBounds(119, 86, 133, 22);
				}
				{
					jt3 = new JTextField();
					outer.add(jt3);
					jt3.setText("documents/");
					jt3.setBounds(119, 122, 133, 22);
				}
				{
					jt4 = new JTextField();
					outer.add(jt4);
					jt4.setText("127.0.0.1");
					jt4.setBounds(119, 160, 133, 22);
				}
				{
					jt5 = new JTextField();
					outer.add(jt5);
					jt5.setText("9300");
					jt5.setBounds(119, 190, 133, 22);
				}
			}
			jb = new JButton();
			outer.add(jb);
			jb.setText("提交");
			jb.setBounds(80, 220, 100, 22);
			jb.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					CreateIndex createIndex = new CreateIndex(jt1.getText(),
							jt2.getText(), jt3.getText(), jt4.getText(), jt5
									.getText());
					try {
						createIndex.create();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});
			setSize(400, 350);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}