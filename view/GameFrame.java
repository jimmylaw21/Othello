package view;


import components.ChessGridComponent;
import components.Clock;
import controller.GameController;
import net.ComponentInWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 界面Frame；操作栏；连接各部分和gui显示
 */
public class GameFrame extends JFrame {
    public static GameController controller;
    private ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;
    public static int page = 1;//1 为开始界面。
    static boolean hasWindow = false;
    static boolean threadIsStart = false;
    int myNum;
    public static int bgNum = 8;
    public static int cbNum = 6;
    public static int difficultyPath = 3;
    public static int heroChoose;
    public static int enemyChoose;


    Thread aThread;


    public static String currentDirectory() {
        File file = new File("");
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return "error";
        }
    }

    public long getList(File f) {//递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getList(flist[i]);
                size--;
            }
        }
        return size;
    }



    public GameFrame(int frameSize) {

        this.setTitle("2021F CS102A Project Reversi");
        this.setLayout(null);

        //背景图片Label
        ImageIcon background = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\bgimage.jpg");
        JLabel bg1Label = new JLabel();
        bg1Label.setIcon(background);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //背景图片Label
        ImageIcon background2 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\bgimage2.jpg");
        JLabel bg2Label = new JLabel();
        bg2Label.setIcon(background2);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //背景图片Label
        ImageIcon background3 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\bgimage3.jpg");
        JLabel bg3Label = new JLabel();
        bg3Label.setIcon(background3);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //背景图片Label
        ImageIcon background4 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\bg4.jpg");
        JLabel bg4Label = new JLabel();
        bg4Label.setIcon(background4);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //背景图片Label
        ImageIcon background5 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\bg5.jpg");
        JLabel bg5Label = new JLabel();
        bg5Label.setIcon(background5);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //背景图片Label
        ImageIcon background6 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\bg6.jpg");
        JLabel bg6Label = new JLabel();
        bg6Label.setIcon(background6);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //背景图片Label
        ImageIcon background7 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\bg7.jpg");
        JLabel bg7Label = new JLabel();
        bg7Label.setIcon(background7);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //背景图片Label
        ImageIcon background8 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\bg8.jpg");
        JLabel bg8Label = new JLabel();
        bg8Label.setIcon(background8);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //背景图片Label
        ImageIcon background9 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\bg9.jpg");
        JLabel bg9Label = new JLabel();
        bg9Label.setIcon(background9);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //棋盘图片Label
        ImageIcon chessBoard = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\chessBoard.jpg");
        JLabel chessBoardLabel = new JLabel();
        chessBoardLabel.setIcon(chessBoard);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //棋盘图片Label
        ImageIcon chessBoard2 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\chessBoard2.jpg");
        JLabel chessBoard2Label = new JLabel();
        chessBoard2Label.setIcon(chessBoard2);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);


        //棋盘图片Label
        ImageIcon chessBoard3 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\chessBoard3.jpg");
        JLabel chessBoard3Label = new JLabel();
        chessBoard3Label.setIcon(chessBoard3);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //棋盘图片Label
        ImageIcon chessBoard4 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\chessBoard4.jpg");
        JLabel chessBoard4Label = new JLabel();
        chessBoard4Label.setIcon(chessBoard4);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //棋盘图片Label
        ImageIcon chessBoard5 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\chessBoard5.jpg");
        JLabel chessBoard5Label = new JLabel();
        chessBoard5Label.setIcon(chessBoard5);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //棋盘图片Label
        ImageIcon chessBoard6 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\chessBoard6.jpg");
        JLabel chessBoard6Label = new JLabel();
        chessBoard6Label.setIcon(chessBoard6);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //棋盘图片Label
        ImageIcon chessBoard7 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\chessBoard7.jpg");
        JLabel chessBoard7Label = new JLabel();
        chessBoard7Label.setIcon(chessBoard7);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);



        //英雄图片Label
        ImageIcon player = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\希特勒.jpg");
        JLabel playerLabel = new JLabel();
        playerLabel.setIcon(player);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //英雄图片Label
        ImageIcon player2 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\斯大林.jpg");
        JLabel player2Label = new JLabel();
        player2Label.setIcon(player2);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //敌方图片Label
        ImageIcon enemy = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\苏联.jpg");
        JLabel enemyLabel = new JLabel();
        enemyLabel.setIcon(enemy);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //敌方图片Label
        ImageIcon enemy2 = new ImageIcon("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\image\\德三.jpg");
        JLabel enemy2Label = new JLabel();
        enemy2Label.setIcon(enemy2);//将图片设置到JLabel中
        ((JPanel) this.getContentPane()).setOpaque(false);

        //获取窗口边框的长度，将这些值加到主窗口大小上，这能使窗口大小和预期相符
        Insets inset = this.getInsets();
        this.setSize(frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);

        this.setLocationRelativeTo(null);
        //设置棋盘图片显示的区域
        switch (cbNum) {
            case 1:
                chessBoardLabel.setBounds(120, 80, frameSize + inset.left + inset.right - 240, frameSize + inset.top + inset.bottom - 240);
                this.getLayeredPane().add(chessBoardLabel, new Integer(Integer.MIN_VALUE));
                break;
            case 2:
                chessBoard2Label.setBounds(120, 80, frameSize + inset.left + inset.right - 240, frameSize + inset.top + inset.bottom - 240);
                this.getLayeredPane().add(chessBoard2Label, new Integer(Integer.MIN_VALUE));
                break;
            case 3:
                chessBoard3Label.setBounds(120, 80, frameSize + inset.left + inset.right - 240, frameSize + inset.top + inset.bottom - 240);
                this.getLayeredPane().add(chessBoard3Label, new Integer(Integer.MIN_VALUE));
                break;
            case 4:
                chessBoard4Label.setBounds(120, 80, frameSize + inset.left + inset.right - 240, frameSize + inset.top + inset.bottom - 240);
                this.getLayeredPane().add(chessBoard4Label, new Integer(Integer.MIN_VALUE));
                break;
            case 5:
                chessBoard5Label.setBounds(120, 80, frameSize + inset.left + inset.right - 240, frameSize + inset.top + inset.bottom - 240);
                this.getLayeredPane().add(chessBoard5Label, new Integer(Integer.MIN_VALUE));
                break;
            case 6:
                chessBoard6Label.setBounds(120, 80, frameSize + inset.left + inset.right - 240, frameSize + inset.top + inset.bottom - 240);
                this.getLayeredPane().add(chessBoard6Label, new Integer(Integer.MIN_VALUE));
                break;

            default:
                chessBoard7Label.setBounds(120, 80, frameSize + inset.left + inset.right - 240, frameSize + inset.top + inset.bottom - 240);
                this.getLayeredPane().add(chessBoard7Label, new Integer(Integer.MIN_VALUE));
                break;
        }

        //设置背景图片显示的区域
        switch (bgNum) {
            case 1:
                bg1Label.setBounds(0, 0, frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);
                this.getLayeredPane().add(bg1Label, new Integer(Integer.MIN_VALUE));
                break;
            case 2:
                bg2Label.setBounds(0, 0, frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);
                this.getLayeredPane().add(bg2Label, new Integer(Integer.MIN_VALUE));
                break;
            case 3:
                bg3Label.setBounds(0, 0, frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);
                this.getLayeredPane().add(bg3Label, new Integer(Integer.MIN_VALUE));
                break;
            case 4:
                bg4Label.setBounds(0, 0, frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);
                this.getLayeredPane().add(bg4Label, new Integer(Integer.MIN_VALUE));
                break;
            case 5:
                bg5Label.setBounds(0, 0, frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);
                this.getLayeredPane().add(bg5Label, new Integer(Integer.MIN_VALUE));
                break;
            case 6:
                bg6Label.setBounds(0, 0, frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);
                this.getLayeredPane().add(bg6Label, new Integer(Integer.MIN_VALUE));
                break;
            case 7:
                bg8Label.setBounds(0, 0, frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);
                this.getLayeredPane().add(bg8Label, new Integer(Integer.MIN_VALUE));
                break;
            case 8:
                bg9Label.setBounds(0, 0, frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom);
                this.getLayeredPane().add(bg9Label, new Integer(Integer.MIN_VALUE));
                break;
        }


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chessBoardPanel = new ChessBoardPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.7));
        chessBoardPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() - chessBoardPanel.getHeight()) / 3);
        this.getLayeredPane().add(chessBoardPanel, new Integer(Integer.MIN_VALUE - 2));

        //状态栏
        statusPanel = new StatusPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.1));
        statusPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, 0);
        this.getLayeredPane().add(statusPanel, new Integer(Integer.MIN_VALUE - 2));

        controller = new GameController(chessBoardPanel, statusPanel);
        controller.setGamePanel(chessBoardPanel);


        JButton cancel = new JButton("No, continue");
        JButton surelyReboot = new JButton("Yes!");
        JFrame isSure = new JFrame();
        surelyReboot.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                GameFrame mainFrame = new GameFrame(800);
                mainFrame.setVisible(true);
            });
            dispose();
            this.repaint();
        });
        cancel.addActionListener(e -> {
            isSure.dispose();
        });

        //更换背景 按钮
        JButton bgBtn = new JButton("背景");
        bgBtn.setSize(117, 50);
        bgBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2 - 118, 60);
        bgBtn.setContentAreaFilled(false);//设置按钮透明
        bgBtn.setBorder(null);//取消边框
        bgBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
        add(bgBtn);
        bgBtn.addActionListener(e -> {
            System.out.println("clicked bgBtn");
//            String bgPath = JOptionPane.showInputDialog(this, "input the bgNum here");
            bgNum = (int) (Math.random() * (7)+1);
            System.out.println(bgNum);
            GameFrame mainFrame1 = new GameFrame(800);
            mainFrame1.setVisible(true);
            dispose();
            this.repaint();

        });

        //更换棋盘 按钮
        JButton cbBtn = new JButton("棋盘");
        cbBtn.setSize(117, 50);
        cbBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2 - 118, 120);
        cbBtn.setContentAreaFilled(false);//设置按钮透明
        cbBtn.setBorder(null);//取消边框
        cbBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
        add(cbBtn);
        cbBtn.addActionListener(e -> {
            System.out.println("clicked cbBtn");
//            String cbPath = JOptionPane.showInputDialog(this, "input the cbNum here");
            cbNum = (int) (Math.random() * (7) + 1);
            GameFrame mainFrame1 = new GameFrame(800);
            mainFrame1.setVisible(true);
            dispose();
            this.repaint();

        });

        //查看排名 按钮
        JButton crBtn = new JButton("排名");
        crBtn.setSize(117, 50);
        crBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2 - 118, 180);
        crBtn.setContentAreaFilled(false);//设置按钮透明
        crBtn.setBorder(null);//取消边框
        crBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
        add(crBtn);
        crBtn.addActionListener(e -> {
            System.out.println("clicked crBtn");
            JOptionPane.showMessageDialog(this, LoginFrame.username);
            JOptionPane.showMessageDialog(this, ChessGridComponent.rank);
        });

        //作弊模式 按钮
        JButton cheatBtn = new JButton("化神");
        cheatBtn.setSize(117, 50);
        cheatBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2 - 118, 240);
        cheatBtn.setContentAreaFilled(false);//设置按钮透明
        cheatBtn.setBorder(null);//取消边框
        cheatBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
        add(cheatBtn);
        cheatBtn.addActionListener(e -> {
            System.out.println("clicked crBtn");
            ChessGridComponent.isCheat = ChessGridComponent.isCheat == false? true : false;

        });

        //查看规则 按钮
        JButton ruleBtn = new JButton("规则");
        ruleBtn.setSize(117, 50);
        ruleBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2 - 118, 300);
        ruleBtn.setContentAreaFilled(false);//设置按钮透明
        ruleBtn.setBorder(null);//取消边框
        ruleBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
        add(ruleBtn);
        ruleBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, rule);
            System.out.println("clicked crBtn");
        });


        /**
         * 五个页面  page 1,2,3,4,5
         * page1 大厅
         * page2 本地双人对战
         * page3 人机对战，难度1
         * page4 网络双人对战
         * page5 战役模式
         */
        if (page == 1 && !hasWindow) {


            //本地双人对战 按钮
            JButton twoPlayerBtn = new JButton("经典");
            twoPlayerBtn.setSize(117, 50);
            twoPlayerBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
            twoPlayerBtn.setContentAreaFilled(false);//设置按钮透明
            twoPlayerBtn.setBorder(null);//取消边框
            twoPlayerBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(twoPlayerBtn);
            twoPlayerBtn.addActionListener(e -> {
                System.out.println("clicked twoPlayerBtn");
                new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\clickMusic.wav")).start();
                ChessGridComponent.round = 0;
                page = 2;
                SwingUtilities.invokeLater(() -> {
                    GameFrame mainFrame2 = new GameFrame(800);
                    mainFrame2.setVisible(true);
                    dispose();
                    this.repaint();
                });
            });

            //人机对战 按钮
            JButton playWithBotBtn = new JButton("人机");
            playWithBotBtn.setSize(117, 50);
            playWithBotBtn.setLocation(twoPlayerBtn.getX() + twoPlayerBtn.getWidth() + 30, twoPlayerBtn.getY());
            add(playWithBotBtn);
            playWithBotBtn.setContentAreaFilled(false);//设置按钮透明
            playWithBotBtn.setBorder(null);
            playWithBotBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            playWithBotBtn.addActionListener(e -> {
                System.out.println("clicked playWithBotBtn");
                new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\clickMusic.wav")).start();
                difficultyPath = Integer.parseInt(JOptionPane.showInputDialog(this, "难度选择,1-新兵,2-老兵,3-炼狱"));
                if (difficultyPath != 0) {
                    ChessGridComponent.round = 0;
                    page = 3;
                    SwingUtilities.invokeLater(() -> {
                        GameFrame mainFrame3 = new GameFrame(800);
                        mainFrame3.setVisible(true);
                        dispose();
                        this.repaint();
                    });
                }
            });


            //网络双人对战 按钮
            JButton netPlayerBtn = new JButton("联机");
            netPlayerBtn.setSize(117, 50);
            netPlayerBtn.setLocation(playWithBotBtn.getX() + playWithBotBtn.getWidth() + 30, playWithBotBtn.getY());
            add(netPlayerBtn);
            netPlayerBtn.setContentAreaFilled(false);//设置按钮透明
            netPlayerBtn.setBorder(null);
            netPlayerBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            netPlayerBtn.addActionListener(e -> {
                System.out.println("clicked netPlayerBtn");
                new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\clickMusic.wav")).start();
                ChessGridComponent.round = 0;
                new ComponentInWindow();
                int i = controller.getRandom().nextInt(100);
                myNum = controller.ONLINE_DISCONNECT + i + 1;
                System.out.println("随机数是" + myNum);
                page = 4;
                SwingUtilities.invokeLater(() -> {
                    GameFrame mainFrame4 = new GameFrame(800);
                    mainFrame4.setVisible(true);
                    dispose();
                    this.repaint();
                });
            });


            //战役模式 按钮
            JButton campaignBtn = new JButton("战役");
            campaignBtn.setSize(117, 50);
            campaignBtn.setLocation(netPlayerBtn.getX() + netPlayerBtn.getWidth() + 30, netPlayerBtn.getY());
            campaignBtn.setContentAreaFilled(false);//设置按钮透明
            campaignBtn.setBorder(null);//取消边框
            campaignBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));

            add(campaignBtn);
            campaignBtn.addActionListener(e -> {
                System.out.println("clicked campaignBtn");
                new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\clickMusic.wav")).start();
                heroChoose = Integer.parseInt(JOptionPane.showInputDialog(this, "选择我方,1-闪击战,2-火箭炮,"));
                enemyChoose = Integer.parseInt(JOptionPane.showInputDialog(this, "选择敌方,1-伞兵,2-空袭,"));
                ChessGridComponent.round = 0;
                page = 5;
                SwingUtilities.invokeLater(() -> {
                    GameFrame mainFrame5 = new GameFrame(800);
                    mainFrame5.setVisible(true);
                    dispose();
                    this.repaint();
                    JOptionPane.showMessageDialog(this, plot0);

                });
            });


        } else if (page == 2 && !hasWindow) {
//            new Thread(new Clock()).start();
            //重新开始 按钮
            JButton restartBtn = new JButton("Reboot");
            restartBtn.setSize(117, 50);
            restartBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
            restartBtn.setContentAreaFilled(false);//设置按钮透明
            restartBtn.setBorder(null);//取消边框
            restartBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(restartBtn);
            restartBtn.addActionListener(e -> {
                System.out.println("click restart Btn");
                ChessGridComponent.round = 0;
                Toolkit tk = this.getToolkit();// 得到窗口工具条
                int width = 650;
                int height = 500;
                Dimension dm = tk.getScreenSize();
                isSure.setSize(300, 200);// 设置程序的大小
                isSure.setLocation((int) (dm.getWidth() - width) / 2, (int) (dm.getHeight() - height) / 2);// 显示在屏幕中央
                isSure.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                isSure.setVisible(true);
                JLabel messagePanel = new JLabel();
                messagePanel.setText("ARE YOU SURE TO REBOOT?");
                messagePanel.setLocation(50, 50);
                messagePanel.setSize(200, 50);
                isSure.setContentPane(messagePanel);
                JPanel contentPane = new JPanel();
                contentPane.setLocation(0, 100);
                contentPane.add(surelyReboot);
                contentPane.add(cancel);
                isSure.setContentPane(contentPane);
                /*//初始化
                ChessGridComponent.round = 0;
                chessBoardPanel.initialChessGrids();
                chessBoardPanel.initialGame();*/

                //打印棋盘（debug用）
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        System.out.print(GameFrame.controller.gamePanel.fileArray[i][j]);
                    }
                    System.out.println();
                }

            });

            //载入 按钮
            JButton loadGameBtn = new JButton("Load");
            loadGameBtn.setSize(117, 50);
            loadGameBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            loadGameBtn.setContentAreaFilled(false);//设置按钮透明
            loadGameBtn.setBorder(null);//取消边框
            loadGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(loadGameBtn);
            loadGameBtn.addActionListener(e -> {
                System.out.println("clicked Load Btn");
                String filePath = JOptionPane.showInputDialog(this, "input the path here");
                if (filePath != null) {
                    File fileToRead = new File("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\stepFile"
                            + filePath);

                    for (int k = 0; k <= (int) (getList(fileToRead) - 1); k++) {

                        int finalK = k;
                        aThread = new Thread(() -> {
                            controller.readFileData(filePath, finalK);
                            try {
                                aThread.sleep(500);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        });

                        aThread.run();

                    }
                }
            });

            //保存 按钮
            JButton saveGameBtn = new JButton("Save");
            saveGameBtn.setSize(117, 50);
            saveGameBtn.setLocation(loadGameBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            saveGameBtn.setContentAreaFilled(false);//设置按钮透明
            saveGameBtn.setBorder(null);//取消边框
            saveGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(saveGameBtn);
            saveGameBtn.addActionListener(e -> {
                System.out.println("clicked Save Btn");
                String filePath = JOptionPane.showInputDialog(this, "input the path here");
                if (filePath != null) {
                    controller.writeDataToFile(filePath);
                }
            });

            //悔棋 按钮
            JButton undoGameBtn = new JButton("Undo");
            undoGameBtn.setSize(117, 50);
            undoGameBtn.setLocation(saveGameBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            undoGameBtn.setContentAreaFilled(false);//设置按钮透明
            undoGameBtn.setBorder(null);//取消边框
            undoGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(undoGameBtn);
            undoGameBtn.addActionListener(e -> {
                System.out.println("clicked Undo Btn");
                controller.undo();
            });

            //返回大厅 按钮
            JButton backToHallBtn = new JButton("返回大厅");
            backToHallBtn.setSize(117, 50);
            backToHallBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() + 90, 9);
            backToHallBtn.setContentAreaFilled(false);//设置按钮透明
            backToHallBtn.setBorder(null);//取消边框
            backToHallBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(backToHallBtn);
            backToHallBtn.addActionListener(e -> {
                System.out.println("clicked backToHallBtn");
                String filePath = JOptionPane.showInputDialog(this, "返回大厅前请记得保存");
                controller.writeDataToFile(filePath);
                page = 1;
                SwingUtilities.invokeLater(() -> {
                    GameFrame mainFrame1 = new GameFrame(800);
                    mainFrame1.setVisible(true);
                    dispose();
                    this.repaint();
                });
            });

            this.setVisible(true);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        } else if (page == 3 && !hasWindow) {

            //重新开始 按钮
            JButton restartBtn = new JButton("Reboot");
            restartBtn.setSize(117, 50);
            restartBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
            restartBtn.setContentAreaFilled(false);//设置按钮透明
            restartBtn.setBorder(null);//取消边框
            restartBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(restartBtn);
            restartBtn.addActionListener(e -> {
                System.out.println("click restart Btn");
                ChessGridComponent.round = 0;
                Toolkit tk = this.getToolkit();// 得到窗口工具条
                int width = 650;
                int height = 500;
                Dimension dm = tk.getScreenSize();
                isSure.setSize(300, 200);// 设置程序的大小
                isSure.setLocation((int) (dm.getWidth() - width) / 2, (int) (dm.getHeight() - height) / 2);// 显示在屏幕中央
                isSure.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                isSure.setVisible(true);
                JLabel messagePanel = new JLabel();
                messagePanel.setText("ARE YOU SURE TO REBOOT?");
                messagePanel.setLocation(50, 50);
                messagePanel.setSize(200, 50);
                isSure.setContentPane(messagePanel);
                JPanel contentPane = new JPanel();
                contentPane.setLocation(0, 100);
                contentPane.add(surelyReboot);
                contentPane.add(cancel);
                isSure.setContentPane(contentPane);
            });

            //载入 按钮
            JButton loadGameBtn = new JButton("Load");
            loadGameBtn.setSize(117, 50);
            loadGameBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            loadGameBtn.setContentAreaFilled(false);//设置按钮透明
            loadGameBtn.setBorder(null);//取消边框
            loadGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(loadGameBtn);
            loadGameBtn.addActionListener(e -> {
                System.out.println("clicked Load Btn");
                String filePath = JOptionPane.showInputDialog(this, "input the path here");
                if (filePath != null) {
                    File fileToRead = new File("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\stepFile"
                            + filePath);

                    for (int k = 0; k <= (int) (getList(fileToRead) - 1); k++) {

                        int finalK = k;
                        aThread = new Thread(() -> {
                            controller.readFileData(filePath, finalK);
                            try {
                                aThread.sleep(500);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        });

                        aThread.run();

                    }
                }
            });

            //保存 按钮
            JButton saveGameBtn = new JButton("Save");
            saveGameBtn.setSize(117, 50);
            saveGameBtn.setLocation(loadGameBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            saveGameBtn.setContentAreaFilled(false);//设置按钮透明
            saveGameBtn.setBorder(null);//取消边框
            saveGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(saveGameBtn);
            saveGameBtn.addActionListener(e -> {
                System.out.println("clicked Save Btn");
                String filePath = JOptionPane.showInputDialog(this, "input the path here");
                if (filePath != null) {
                    controller.writeDataToFile(filePath);
                }
            });

            //悔棋 按钮
            JButton undoGameBtn = new JButton("Undo");
            undoGameBtn.setSize(117, 50);
            undoGameBtn.setLocation(saveGameBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            undoGameBtn.setContentAreaFilled(false);//设置按钮透明
            undoGameBtn.setBorder(null);//取消边框
            undoGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(undoGameBtn);
            undoGameBtn.addActionListener(e -> {
                System.out.println("clicked Undo Btn");
                controller.undo();
            });

            //返回大厅 按钮
            JButton backToHallBtn = new JButton("返回大厅");
            backToHallBtn.setSize(117, 50);
            backToHallBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() + 90, 9);
            backToHallBtn.setContentAreaFilled(false);//设置按钮透明
            backToHallBtn.setBorder(null);//取消边框
            backToHallBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(backToHallBtn);
            backToHallBtn.addActionListener(e -> {
                System.out.println("clicked backToHallBtn");
                String filePath = JOptionPane.showInputDialog(this, "返回大厅前请记得保存");
                if (filePath != null) {
                    controller.writeDataToFile(filePath);
                }
                page = 1;
                SwingUtilities.invokeLater(() -> {
                    GameFrame mainFrame1 = new GameFrame(800);
                    mainFrame1.setVisible(true);
                    dispose();
                    this.repaint();
                });
            });

            this.setVisible(true);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        } else if (page == 4 && !hasWindow) {
            if (!hasWindow && !threadIsStart) {
                Thread t = new Thread((Runnable) this);
                t.start();

                System.out.println("线程开启");
                //GameController.send(myNum,8,Color.black);
                threadIsStart = true;
            }


            //重新开始 按钮
            JButton restartBtn = new JButton("Reboot");
            ChessGridComponent.round = 0;
            restartBtn.setSize(117, 50);
            restartBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
            restartBtn.setContentAreaFilled(false);//设置按钮透明
            restartBtn.setBorder(null);//取消边框
            restartBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(restartBtn);
            restartBtn.addActionListener(e -> {
                System.out.println("click restart Btn");
                Toolkit tk = this.getToolkit();// 得到窗口工具条
                int width = 650;
                int height = 500;
                Dimension dm = tk.getScreenSize();
                isSure.setSize(300, 200);// 设置程序的大小
                isSure.setLocation((int) (dm.getWidth() - width) / 2, (int) (dm.getHeight() - height) / 2);// 显示在屏幕中央
                isSure.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                isSure.setVisible(true);
                JLabel messagePanel = new JLabel();
                messagePanel.setText("ARE YOU SURE TO REBOOT?");
                messagePanel.setLocation(50, 50);
                messagePanel.setSize(200, 50);
                isSure.setContentPane(messagePanel);
                JPanel contentPane = new JPanel();
                contentPane.setLocation(0, 100);
                contentPane.add(surelyReboot);
                contentPane.add(cancel);
                isSure.setContentPane(contentPane);
            });

            //载入 按钮
            JButton loadGameBtn = new JButton("Load");
            loadGameBtn.setSize(117, 50);
            loadGameBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            loadGameBtn.setContentAreaFilled(false);//设置按钮透明
            loadGameBtn.setBorder(null);//取消边框
            loadGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(loadGameBtn);
            loadGameBtn.addActionListener(e -> {
                System.out.println("clicked Load Btn");
                String filePath = JOptionPane.showInputDialog(this, "input the path here");
                if (filePath != null) {
                    File fileToRead = new File("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\stepFile"
                            + filePath);

                    for (int k = 0; k <= (int) (getList(fileToRead) - 1); k++) {

                        int finalK = k;
                        aThread = new Thread(() -> {
                            controller.readFileData(filePath, finalK);
                            try {
                                aThread.sleep(500);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        });

                        aThread.run();

                    }
                }
            });

            //保存 按钮
            JButton saveGameBtn = new JButton("Save");
            saveGameBtn.setSize(117, 50);
            saveGameBtn.setLocation(loadGameBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            saveGameBtn.setContentAreaFilled(false);//设置按钮透明
            saveGameBtn.setBorder(null);//取消边框
            saveGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(saveGameBtn);
            saveGameBtn.addActionListener(e -> {
                System.out.println("clicked Save Btn");
                String filePath = JOptionPane.showInputDialog(this, "input the path here");
                if (filePath != null) {
                    controller.writeDataToFile(filePath);
                }
            });

            //悔棋 按钮
            JButton undoGameBtn = new JButton("Undo");
            undoGameBtn.setSize(117, 50);
            undoGameBtn.setLocation(saveGameBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            undoGameBtn.setContentAreaFilled(false);//设置按钮透明
            undoGameBtn.setBorder(null);//取消边框
            undoGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(undoGameBtn);
            undoGameBtn.addActionListener(e -> {
                System.out.println("clicked Undo Btn");
                controller.undo();
            });

            //返回大厅 按钮
            JButton backToHallBtn = new JButton("返回大厅");
            backToHallBtn.setSize(117, 50);
            backToHallBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() + 90, 9);
            backToHallBtn.setContentAreaFilled(false);//设置按钮透明
            backToHallBtn.setBorder(null);//取消边框
            backToHallBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(backToHallBtn);
            backToHallBtn.addActionListener(e -> {
                System.out.println("clicked backToHallBtn");
                String filePath = JOptionPane.showInputDialog(this, "返回大厅前请记得保存");
                if (filePath != null) {
                    controller.writeDataToFile(filePath);
                }
                page = 1;
                SwingUtilities.invokeLater(() -> {
                    GameFrame mainFrame1 = new GameFrame(800);
                    mainFrame1.setVisible(true);
                    dispose();
                    this.repaint();
                });
            });

            this.setVisible(true);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        } else if (page == 5 && !hasWindow) {

            //设英雄图片显示的区域
            switch(heroChoose) {
                case 1:
                    playerLabel.setBounds(675, 0, frameSize + inset.left + inset.right - 400, frameSize + inset.top + inset.bottom - 25);//设图片显示的区域
                    this.getLayeredPane().add(playerLabel, new Integer(Integer.MIN_VALUE) + 1);
                    break;
                case 2:
                    player2Label.setBounds(675, 0, frameSize + inset.left + inset.right - 400, frameSize + inset.top + inset.bottom - 25);//设图片显示的区域
                    this.getLayeredPane().add(player2Label, new Integer(Integer.MIN_VALUE) + 1);
                    break;
            }
            //设敌人图片显示的区域
            switch(enemyChoose) {
                case 1:
                        enemyLabel.setBounds(0, 0, frameSize + inset.left + inset.right - 25, frameSize + inset.top + inset.bottom - 25);//设图片显示的区域
                        this.getLayeredPane().add(enemyLabel, new Integer(Integer.MIN_VALUE) + 1);
                    break;
                default:
                    enemy2Label.setBounds(0, 0, frameSize + inset.left + inset.right - 25, frameSize + inset.top + inset.bottom - 25);//设图片显示的区域
                    this.getLayeredPane().add(enemy2Label, new Integer(Integer.MIN_VALUE) + 1);
                    Clock.clockTime = 0;
                    new Thread(new Clock()).start();
                    break;
            }
            //重新开始 按钮
            JButton restartBtn = new JButton("Reboot");
            restartBtn.setSize(117, 50);
            restartBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
            restartBtn.setContentAreaFilled(false);//设置按钮透明
            restartBtn.setBorder(null);//取消边框
            restartBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(restartBtn);
            restartBtn.addActionListener(e -> {
                System.out.println("click restart Btn");
                //战役模式属性初始化
                ChessGridComponent.round = 0;
                GameController.coldDownTime = 0;
                ChessGridComponent.isTheWorldOK = true;
                ChessGridComponent.isFireBallOK = true;

                Toolkit tk = this.getToolkit();// 得到窗口工具条
                int width = 650;
                int height = 500;
                Dimension dm = tk.getScreenSize();
                isSure.setSize(300, 200);// 设置程序的大小
                isSure.setLocation((int) (dm.getWidth() - width) / 2, (int) (dm.getHeight() - height) / 2);// 显示在屏幕中央
                isSure.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                isSure.setVisible(true);
                JLabel messagePanel = new JLabel();
                messagePanel.setText("ARE YOU SURE TO REBOOT?");
                messagePanel.setLocation(50, 50);
                messagePanel.setSize(200, 50);
                isSure.setContentPane(messagePanel);
                JPanel contentPane = new JPanel();
                setLocation(0, 100);
                contentPane.add(surelyReboot);
                contentPane.add(cancel);
                isSure.setContentPane(contentPane);
            });

            //载入 按钮
            JButton loadGameBtn = new JButton("Load");
            loadGameBtn.setSize(117, 50);
            loadGameBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            loadGameBtn.setContentAreaFilled(false);//设置按钮透明
            loadGameBtn.setBorder(null);//取消边框
            loadGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(loadGameBtn);
            loadGameBtn.addActionListener(e -> {
                System.out.println("clicked Load Btn");
                String filePath = JOptionPane.showInputDialog(this, "input the path here");
                if (filePath != null) {
                    File fileToRead = new File("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\stepFile"
                            + filePath);

                    for (int k = 0; k <= (int) (getList(fileToRead) - 1); k++) {

                        int finalK = k;
                        aThread = new Thread(() -> {
                            controller.readFileData(filePath, finalK);
                            try {
                                aThread.sleep(500);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        });

                        aThread.run();

                    }
                }
            });

            //保存 按钮
            JButton saveGameBtn = new JButton("Save");
            saveGameBtn.setSize(117, 50);
            saveGameBtn.setLocation(loadGameBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            saveGameBtn.setContentAreaFilled(false);//设置按钮透明
            saveGameBtn.setBorder(null);//取消边框
            saveGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(saveGameBtn);
            saveGameBtn.addActionListener(e -> {
                System.out.println("clicked Save Btn");
                String filePath = JOptionPane.showInputDialog(this, "input the path here");
                if (filePath != null) {
                    controller.writeDataToFile(filePath);
                }
            });

            //悔棋 按钮
            JButton undoGameBtn = new JButton("Undo");
            undoGameBtn.setSize(117, 50);
            undoGameBtn.setLocation(saveGameBtn.getX() + restartBtn.getWidth() + 30, restartBtn.getY());
            undoGameBtn.setContentAreaFilled(false);//设置按钮透明
            undoGameBtn.setBorder(null);//取消边框
            undoGameBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(undoGameBtn);
            undoGameBtn.addActionListener(e -> {
                System.out.println("clicked Undo Btn");
                controller.undo();
            });

            //返回大厅 按钮
            JButton backToHallBtn = new JButton("返回大厅");
            backToHallBtn.setSize(117, 50);
            backToHallBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() - 235, 9);
            backToHallBtn.setContentAreaFilled(false);//设置按钮透明
            backToHallBtn.setBorder(null);//取消边框
            backToHallBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(backToHallBtn);
            backToHallBtn.addActionListener(e -> {
                System.out.println("clicked backToHallBtn");
                String filePath = JOptionPane.showInputDialog(this, "返回大厅前请记得保存");
                if (filePath != null) {
                    controller.writeDataToFile(filePath);
                }
                page = 1;
                SwingUtilities.invokeLater(() -> {
                    GameFrame mainFrame1 = new GameFrame(800);
                    mainFrame1.setVisible(true);
                    dispose();
                    this.repaint();
                });
            });

            //己方技能显示 按钮
            JButton playerAbilityBtn = new JButton("我方技能");
            playerAbilityBtn.setSize(117, 50);
            playerAbilityBtn.setLocation(saveGameBtn.getX() + restartBtn.getWidth() + 145, restartBtn.getY() - 200);
            playerAbilityBtn.setContentAreaFilled(false);//设置按钮透明
            playerAbilityBtn.setBorder(null);//取消边框
            playerAbilityBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(playerAbilityBtn);
            playerAbilityBtn.addActionListener(e -> {
                System.out.println("clicked playerAbilityBtn");
                switch(heroChoose) {
                    case 1:
                        JOptionPane.showMessageDialog(this, PAname1);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, PAname2);
                        break;
                }
            });

            //敌方技能显示 按钮
            JButton enemyAbilityBtn = new JButton("敌方技能");
            enemyAbilityBtn.setSize(117, 50);
            enemyAbilityBtn.setLocation(restartBtn.getX() + restartBtn.getWidth() - 235, restartBtn.getY() - 200);
            enemyAbilityBtn.setContentAreaFilled(false);//设置按钮透明
            enemyAbilityBtn.setBorder(null);//取消边框
            enemyAbilityBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(enemyAbilityBtn);
            enemyAbilityBtn.addActionListener(e -> {
                System.out.println("clicked enemyAbilityBtn");
                switch(enemyChoose) {
                    case 1:
                        JOptionPane.showMessageDialog(this, EAname1);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, EAname2);
                        break;
                }
                GameFrame.controller.updateStatues();
            });

            //己方技能释放 按钮
            JButton abilityPlayBtn = new JButton("释放技能");
            abilityPlayBtn.setSize(117, 50);
            abilityPlayBtn.setLocation(saveGameBtn.getX() + restartBtn.getWidth() + 145, restartBtn.getY() - 150);
            abilityPlayBtn.setContentAreaFilled(false);//设置按钮透明
            abilityPlayBtn.setBorder(null);//取消边框
            abilityPlayBtn.setFont(new Font("华文新魏",Font.LAYOUT_LEFT_TO_RIGHT,25));
            add(abilityPlayBtn);
            abilityPlayBtn.addActionListener(e -> {
                System.out.println("clicked abilityPlayBtn");
                switch(heroChoose) {
                    case 1:
                        new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\clickMusic.wav")).start();
                        controller.theWorld();
                        break;
                    default:
//                        new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\clickMusic.wav")).start();
                        controller.fireBall();
                        break;
                }
                GameFrame.controller.updateStatues();
            });

            this.setVisible(true);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    public static int getPage() {
        return page;
    }

    public static void setPage(int page) {
        GameFrame.page = page;
    }

    String pAname = "你的技能\n时间暂停\n使用该技能后,拥有额外一个回合，然后在本回合后正常下棋\n在额外回合和前四回合中无法使用该技能，冷却时间为四回合，不包括额外回合";

    String eAname = "占星者技能\n陨石坠落\n使用该技能后，随机使一个棋子（包括己方棋子）或空格变为己方棋子，然后在本回合后正常下棋\n敌方每回合有较低概率会释放技能（进入狂暴状态后会频繁释放技能）";

    String pAname2 = "你的技能\n奥数火球\n使用该技能后,取消本回合正常下棋，然后使下棋（仍受可下规则限制,且不能释放在边角位）处3*3范围内的棋子变为己方棋子\n在前六回合中无法使用该技能，冷却时间为三回合";

    String eAname2 = "疯狂造物技能\n触手突刺\n使用该技能后，随机使三个敌方棋子变为空格，然后在本回合后正常下棋\n敌方每回合有较低概率会释放技能，但是随着现实时间（超过15s）推移，敌方释放技能的概率会增大";

    String plot0 = "1920年7月21日19点20分，阿卡姆城向南3公里处的一座天文台，\n这栋古朴的建筑里微微散发着诡异的光芒，不可名状之物在星空中隐约可见";

    String PAname1 ="我方技能\n闪电战\n使用该技能后,拥有额外一个回合，然后在本回合后正常下棋\n在额外回合和前四回合中无法使用该技能，冷却时间为四回合，不包括额外回合";

    String EAname1 ="敌方技能\n伞兵渗透\n使用该技能后，随机使一个棋子（包括己方棋子）或空格变为己方棋子，然后在本回合后正常下棋\n敌方每回合有较低概率会释放技能（进入反击状态后会频繁释放技能）";

    String PAname2 ="我方技能\n喀秋莎火箭炮\n使用该技能后,取消本回合正常下棋，然后使下棋（仍受可下规则限制,且不能释放在边角位）处3*3范围内的棋子变为己方棋子\n在前六回合中无法使用该技能，冷却时间为三回合";

    String EAname2 ="敌方技能\n空袭\n使用该技能后，随机使三个敌方棋子变为空格，然后在本回合后正常下棋\n敌方每回合有较低概率会释放技能，但是随着现实时间（超过15s）推移，敌方释放技能的概率会增大";

    String rule ="棋盘为8×8的方格布局，开局时在棋盘正中有摆好的四枚棋子，黑白各2枚，交叉放置，由执黑棋的一方先落子，双方交替下子，棋子落在方格内，一局游戏结束后双方更换执子颜色。\n" +
            "步合法的棋步包括：在一个空格新落下一个棋子，并且翻转对手一个或多个棋子。\n" +
            "下子方式：把自己颜色的棋子放在棋盘的空格上，而当自己放下的棋子在横、竖、斜八个方向内有一个自己的棋子，则被夹在中间的对方棋子全部翻转会成为自己的棋子。夹住的位置上必须全部是对手的棋子，不能有空格。并且，只有在可以翻转棋子的地方才可以下子。\n" +
            "一步棋可以在数个方向上翻棋，任何被夹住的棋子都必须被翻转过来，棋手无权选择不去翻某个棋子必须是刚下的子夹对方才能够翻对方的子，因翻转对方的棋子而夹住的子是不能被翻的。\n" +
            "翻转棋子时，每次下子最少必须翻转对方一个棋子，若棋局中下子都不能翻转对方棋子，则自动pass轮空，您无子可下由对方继续下子，由于对方无子可下，您可继续下子。若二个玩家都不能下子翻转对方棋子，游戏结束。";

}