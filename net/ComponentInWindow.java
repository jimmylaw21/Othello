package net;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ComponentInWindow extends JFrame implements ActionListener {
    JTextField text1,text2,text3,text4;
    JButton button;
    public ComponentInWindow() {
        init();
        setVisible(true);
        this.setResizable(false);
        this.setBounds(0, 0, 320, 150);
        this.setTitle("网络链接");
        this.setLocation((this.getWidth() - 320) / 2, (this.getHeight() - 150) / 2 );
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        button.addActionListener(this);
        add(button);
    }

    public void init() {
        setLayout(new FlowLayout()); // 设置布局

        add(new JLabel("你的IP:"));
        text1 = new JTextField(10);
        add(text1);

        add(new JLabel("你的端口:"));
        text2 = new JTextField(10);
        add(text2);
        add(new JLabel("对手IP:"));
        text3 = new JTextField(10);
        add(text3);
        add(new JLabel("对手端口:"));
        text4 = new JTextField(10);
        add(text4);

        add(new JLabel("按钮:"));
        button = new JButton("开始联机");
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        controller.GameController.yourIP = text1.getText();
        controller.GameController.yourPort = text2.getText();
        controller.GameController.matchIP = text3.getText();
        controller.GameController.matchPort = text4.getText();
        if(!controller.GameController.yourIP.equals("") && !controller.GameController.yourPort.equals("") && !controller.GameController.matchIP.equals("") && !controller.GameController.matchPort.equals("")) {
//            controller.GameController.hasWindow = false;
//            controller.GameController.isFilled = true;

            this.dispose();
        }else {
            JOptionPane.showMessageDialog(this, "请输入IP和端口");
        }


    }
}
