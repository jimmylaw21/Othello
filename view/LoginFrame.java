package view;

import view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class LoginFrame extends JFrame {
    public static void main(String[] args) {
        selectUserView();
    }

    private static JFrame frame;            //整个窗口
    private static JLayeredPane layerPanel; //分层面板
    private static JPanel framePanel;       // 内容面板(玻璃面板)
    private static ImageIcon bg;// 登录背景图片
    public static String username;

    static {
        bg = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\loginBg1.png");//背景图片
    }

    public static void selectUserView() {
        if (frame == null) {
            //--------显示一个窗口(首次打开程序)---------
            frame = new JFrame("黑白棋");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            layerPanel = frame.getLayeredPane();
            framePanel = (JPanel) frame.getContentPane();
            framePanel.setLocation(200,200);

            JLabel background = new JLabel(bg);        //把背景图片添加到标签里
            background.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());
            layerPanel.add(background, new Integer(Integer.MIN_VALUE));
            frame.setBounds(800, 200, bg.getIconWidth(), bg.getIconHeight());
        } else {
            //--------切换一个窗口(退出后的操作)--------
            JLabel label = (JLabel) layerPanel.getComponent(1);
            label.setIcon(bg);

            framePanel.removeAll();
            framePanel.setOpaque(false);
            framePanel.setLayout(new BorderLayout());

        }
        //--------------------------------------

        framePanel.setOpaque(false);
        framePanel.setLayout(new GridLayout(16, 2, 2, 20));
        framePanel.add(new JLabel());
        framePanel.add(new JLabel());
        framePanel.add(new JLabel());
        framePanel.add(new JLabel());
        framePanel.add(new JLabel());
        framePanel.add(new JLabel());
        framePanel.add(new JLabel());
        framePanel.add(new JLabel());



        //--------标题--------
//        JPanel titlePanel = new JPanel();
//        titlePanel.setOpaque(false);
//        titlePanel.setLayout(new FlowLayout());
//        JLabel titleLabel = new JLabel("奥赛罗");
//        titleLabel.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,30));
//
//        titlePanel.add(titleLabel);
//        framePanel.add(titlePanel);
        //--------窗口中显示一个用户名输入框-------
        JPanel unamePanel = new JPanel();
        unamePanel.setOpaque(false);
        unamePanel.setLayout(new FlowLayout());
        JLabel unameLabel = new JLabel("用户名：");
        unameLabel.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
        JTextField unameInput = new JTextField();
        unameInput.setPreferredSize(new Dimension(200, 30));

        unamePanel.add(unameLabel);
        unamePanel.add(unameInput);
        framePanel.add(unamePanel);

        //--------窗口中显示一个密码输入框---------
        JPanel upassPanel = new JPanel();
        upassPanel.setOpaque(false);
        upassPanel.setLayout(new FlowLayout());

        JLabel upassLabel = new JLabel(" 密  码：");
        upassLabel.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
        JPasswordField upassInput = new JPasswordField();
        upassInput.setPreferredSize(new Dimension(200, 30));
        upassPanel.add(upassLabel);
        upassPanel.add(upassInput);
        framePanel.add(upassPanel);

        //---------窗口中显示一个登录按钮和注册按钮-------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());

        JButton button = new JButton("登录");
        button.setContentAreaFilled(false);//设置按钮透明
        button.setBorder(null);//取消边框
        button.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));

//        JLabel unameLabel2 = new JLabel("点这里：");

        JButton button2 = new JButton("      注册     ");
        button2.setContentAreaFilled(false);//设置按钮透明
        button2.setBorder(null);//取消边框
        button2.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));

        JButton button3 = new JButton("游客登录");
        button3.setContentAreaFilled(false);//设置按钮透明
        button3.setBorder(null);//取消边框
        button3.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));

//        buttonPanel.add(unameLabel2);
        buttonPanel.add(button);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        framePanel.add(buttonPanel);

        //------设置登录按钮的操作事件--------
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*登录认证*/
                String uname = unameInput.getText();
                String upass = String.valueOf(upassInput.getPassword());
                try {
                    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\loginFile"));
                    String line;
                    ArrayList<String> readList = new ArrayList<String>();
                    while ((line = br.readLine()) != null) {
                        readList.add(line);

                    }
                    br.close();
                    for (String a : readList) {
                        if (a.equals(uname + "=" + upass)) {
                            JOptionPane.showMessageDialog(frame, "登录成功", "提示", 1);
                            username = uname;
                            //跳转下一个界面
                            SwingUtilities.invokeLater(() -> {
                                GameFrame mainFrame1 = new GameFrame(800);
                                mainFrame1.setVisible(true);
                            });
                            frame.dispose();
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "用户名密码错误", "提示", 0);
                    upassInput.setText("");


                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //--------设计完毕，显示窗口---------
        frame.setVisible(true);

        //------设置注册按钮的操作事件--------
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uname = unameInput.getText();
                String upass = String.valueOf(upassInput.getPassword());
                try {
                    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\loginFile"));
                    String line;
                    ArrayList<String[]> readLists = new ArrayList<String[]>();

                    while ((line = br.readLine()) != null) {
                        String[] s = line.split("=");
                        readLists.add(s);
                    }
                    br.close();
                    for (String[] b : readLists) {
                        for (String c : b) {
                            if (c.equals(uname)) {
                                JOptionPane.showMessageDialog(frame, "用户名已存在", "提示", 1);
                                return;
                            }

                        }
                    }
                    if (uname.equals("") || upass.equals("")) {
                        JOptionPane.showMessageDialog(frame, "用户名或密码不能为空", "提示", 1);
                    } else {
                        String info = uname + "=" + upass;
                        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\loginFile", true));
                        bw.write(info);
                        bw.newLine();
                        bw.flush();
                        bw.close();

                        BufferedWriter bw2 = new BufferedWriter(new FileWriter("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\rankingFile", true));
                        bw2.write(uname + ",0");
                        bw2.newLine();
                        bw2.flush();
                        bw2.close();
                        JOptionPane.showMessageDialog(frame, "注册成功", "提示", 1);
                        unameInput.setText("");
                        upassInput.setText("");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*登录认证*/

                username = "游客";
                //跳转下一个界面
                SwingUtilities.invokeLater(() -> {
                    GameFrame mainFrame1 = new GameFrame(800);
                    mainFrame1.setVisible(true);
                });
                frame.dispose();

            }
        });


    }
}
