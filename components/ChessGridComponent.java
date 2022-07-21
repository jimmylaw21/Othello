package components;

import controller.GameController;
import controller.Robot;
import model.*;
import view.ChessBoardPanel;
import view.GameFrame;
import view.LoginFrame;
import view.clickMusic;
//import view.Music;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * 游戏栏：单个棋子和其所在的格子
 */
public class ChessGridComponent extends BasicComponent implements Runnable {
    public static int chessSize;
    public static int gridSize;

    public static Color gridColor1 = new Color(80, 80, 80);
    public static Color gridColor2 = new Color(120, 120, 120);

    public static Color gridColor3 = new Color(100, 100, 100);
    public static Color gridColor4 = new Color(246, 127, 13);

    public static Color gridColor5 = new Color(255, 150, 50);
    public static Color gridColor6 = new Color(246, 127, 13);

    public static int round = 0;
    public static boolean isClick = false;
    int a = 0;
    public boolean linkClickAndRelease = false;
    public static boolean isHaveSkip = false;

    private ChessPiece chessPiece;
    private int row;
    private int col;
    public static boolean isCheat = false;

    List<String> fileData = new ArrayList<>();

    //战役模式
    public static boolean isTheWorld = false;
    public static boolean isTheWorldOK = true;

    public static boolean isFireBall = false;
    public static boolean isFireBallOK = true;

    public static boolean isStarFall = false;

    Thread thread2 = new Thread(this);
    static boolean running = true;


    //用户排名
    public static HashMap<String, Integer> userScore = new HashMap<>();
    public static StringBuilder rank = new StringBuilder();
    static boolean b = true;//修bug

    @Override
    public void run() {
//        while (true){
//            try {
//                Thread.sleep(50);
//                if(isClick) {
//                    new Music();
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (JavaLayerException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void checkRank() {
        userScore = sortByComparator(userScore);
        StringBuilder rank2 = new StringBuilder();
        int rankNum = 1;
        for (Map.Entry<String, Integer> entry : userScore.entrySet()) {
            rank2.append(rankNum + " " + entry.getKey() + " 胜场：" + entry.getValue() + "\n");
            rankNum++;
        }
        rank = rank2;
    }

    public void winnerScoreRead() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(GameFrame.currentDirectory() + "\\src\\rankingFile"));
            String line;
            ArrayList<String> readList = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                readList.add(line);
            }
            br.close();
            //将用户积分文件读入HashMap中
            for (String a : readList) {
                String[] temp = a.split(",");
                userScore.put(temp[0], Integer.valueOf(temp[1]));
            }
            userScore = sortByComparator(userScore);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void winnerScoreWrite() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(GameFrame.currentDirectory() + "\\src\\rankingFile"));
            String line;

            //将HashMap读入用户积分文件中
            userScore = sortByComparator(userScore);
            for (Map.Entry<String, Integer> entry : userScore.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }

            bw.flush();
            bw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

//    public HashMap<String,String> sortMap(HashMap<String,String> userscore){
//        HashMap<String, String> userScore = sortByComparator(userscore);
//        return userScore;
//    }

    private static HashMap sortByComparator(HashMap unsortMap) {
        List list = new LinkedList(unsortMap.entrySet());
        // sort list based on comparator
        Collections.sort(list, new Comparator() {
            public int compare(Object o2, Object o1) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        HashMap sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }


    //构造器
    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);
        this.row = row;
        this.col = col;
        thread2.start();
        if (b) {
            winnerScoreRead();
            checkRank();
            b = false;
        }
        File stepFile = new File(GameFrame.currentDirectory() + "\\src\\stepFile");
        if (!stepFile.exists()) {//如果文件夹不存在
            stepFile.mkdir();//创建文件夹
        }
    }

    public void undoSave() {
        File file = new File(GameFrame.currentDirectory() + "\\src\\stepFile\\undo" + round + ".txt");
        try {
            //用BufferedWriter高效地将数据写入文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (j != 0)
                        bw.write(",");
                    bw.write(String.valueOf(ChessBoardPanel.fileArray[i][j]));
                }
                bw.write("\r\n");
            }
            bw.write(GameFrame.controller.getCurrentPlayer() + "\r\n");
            String roundString = String.valueOf(round);
            bw.write(roundString);
            bw.flush();
            bw.close();
        } catch (Exception e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }
    }


    @Override
    public void onMouseClicked() {


            isClick = true;
            isStarFall = false;
            Clock.clockTime = 0;

            System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
            //todo: complete mouse click method

            //为悔棋创建文件
            undoSave();

            GameController.OKRound = round;
            switch (GameFrame.heroChoose) {
                case 1:
                    GameController.coldDownTime = (4 - (GameController.OKRound - GameController.clickRound));
                    break;
                default:
                    GameController.coldDownTime = (3 - (GameController.OKRound - GameController.clickRound));
                    break;
            }
            System.out.println("现在是第" + round + "回合");

            if (GameController.coldDownTime < 0) {
                isTheWorldOK = true;
                isFireBallOK = true;
            }
            GameFrame.controller.updateStatues();

            a = 0;
            //第一步。如果位置合法，执行以下方法，更新临时数组和棋盘
            if (ChessGridComponent.isCheat && !GameFrame.controller.canClick(row, col)) {
                GameFrame.controller.cantMove = 0;
                linkClickAndRelease = true;
                a = 1;
                if (this.chessPiece == null || this.chessPiece == ChessPiece.HIGHLIGHT) {
                    this.chessPiece = GameFrame.controller.getCurrentPlayer();
                    System.out.print("已获得当前棋手");
                }

                int cl;
                if (chessPiece.getColor() == Color.BLACK) {
                    cl = -1;
                } else {
                    cl = 1;
                }

                GameFrame.controller.gamePanel.fileArray[row][col] = cl;
                allRepaint();
            } else if (GameFrame.controller.canClick(row, col)) {
                GameFrame.controller.cantMove = 0;
                linkClickAndRelease = true;
                round++;
                a = 1;
                if (this.chessPiece == null || this.chessPiece == ChessPiece.HIGHLIGHT) {
                    this.chessPiece = GameFrame.controller.getCurrentPlayer();
                    System.out.print("已获得当前棋手");
                }
                if (!isFireBall) {
                    GameFrame.controller.updateFileArray(row, col, chessPiece.getColor());
                    //GameController.send(row, col,this.chessPiece.getColor());
                } else {
                    GameFrame.controller.fireBallPlay(row, col);
                    new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\炮弹降落.wav")).start();
                    isFireBall = false;
                }
                allRepaint();
            }


            //如果位置不合法，不做操作
            else {
                System.out.println("非法位置！");
            }

        //如果无子可下，计数
        GameFrame.controller.cntCantMove(chessPiece.getOppositeChessPiece());

        //双人模式时，如果无子可下，切换颜色
        if (GameFrame.controller.isSkip() && GameFrame.page == 2) {
            GameFrame.controller.swapPlayer();
            isHaveSkip = true;

            //打印
            String cl = "NULL";
            if (chessPiece.getColor() == Color.BLACK)
                cl = "black";
            else if (chessPiece.getColor() == Color.WHITE)
                cl = "white";
            System.out.println("无子可下，已切换颜色" + cl);

        }

        //如果结束
//        if (GameFrame.controller.isOver()) {
//            JFrame gameOver = new JFrame();
//            gameOver.setSize(500, 200);
//            Toolkit tk = this.getToolkit();// 得到窗口工具条
//            Dimension dm = tk.getScreenSize();//获得屏幕尺寸
//            gameOver.setLocation((int) (dm.getWidth() - 300) / 2, (int) (dm.getHeight() - 200) / 2);// 显示在屏幕中央
//
//            JLabel winnerMessage = new JLabel();
//            winnerMessage.setFont(new Font("Calibri", Font.ITALIC, 30));
//            winnerMessage.setText(GameFrame.controller.winner());
//
//            //胜局积分
//            winnerScoreRead();
//            if (userScore.containsKey(LoginFrame.username)) {//判断如果存在 key ,就修改value
//                int currentScore = userScore.get(LoginFrame.username);
//                currentScore++;
//                userScore.put(LoginFrame.username, currentScore);//修改
//            }
//            checkRank();
//            winnerScoreWrite();
//
//            JPanel selection = new JPanel();
//            JButton restart = new JButton();
//
//            JButton backToHall = new JButton();
//            backToHall.setSize(100, 30);
//            restart.setSize(100, 30);
//            backToHall.setText("Go back to Hall");
//            restart.setText("Replay this game");
//
//            selection.add(restart);
//            selection.add(backToHall);
//            backToHall.addActionListener(e -> {
////                GameFrame.reboot();
//            });
//
//
//            gameOver.setDefaultLookAndFeelDecorated(true);
//            gameOver.add(selection, BorderLayout.SOUTH);
//            gameOver.add(winnerMessage, BorderLayout.CENTER);
//            gameOver.setVisible(true);
////            gameOver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        }


    }

    @Override
    protected void onMouseReleased() {

            if (linkClickAndRelease) {
                new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\1秒落子音效.wav")).start();
                if (this.chessPiece == null || this.chessPiece == ChessPiece.HIGHLIGHT) {
                    this.chessPiece = GameFrame.controller.getCurrentPlayer();
                    System.out.print("已获得当前棋手");
                }

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isClick = false;

                //人机模式：实现白子下棋
                switch (GameFrame.difficultyPath) {
                    case 1:
                        robot1Play();
                        break;
                    case 2:
                        robot2Play();
                        break;
                    default:
                        robot3Play();
                        break;
                }

                //战役模式：实现白子下棋
                switch (GameFrame.enemyChoose) {
                    case 1:
                        astrologerPlay();
                        break;
                    default:
                        madnessPlay();
                        break;
                }
                GameFrame.controller.updateStatues();


                //双人模式：进行切换玩家操作
                if (GameFrame.page == 2 && linkClickAndRelease) {
                    GameFrame.controller.swapPlayer();
                }
                isClick = false;

                allRepaint();

                //如果尚未结束

                if (!GameFrame.controller.isOver() && GameFrame.page == 2) {

                    int[][] highlightArray;

                    if (isHaveSkip) {
                        highlightArray = ChessBoardPanel.validMovesStatic(chessPiece.getOppositeChessPiece(), GameFrame.controller.gamePanel.fileArray);
                        isHaveSkip = false;
                    } else {
                        highlightArray = ChessBoardPanel.validMovesStatic(chessPiece, GameFrame.controller.gamePanel.fileArray);
                    }

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (highlightArray[i][j] == 1) {
                                GameFrame.controller.gamePanel.chessGrids[i][j].setChessPiece(ChessPiece.HIGHLIGHT);
                                GameFrame.controller.gamePanel.chessGrids[i][j].repaint();
                            }
                        }
                    }

                    repaint();
                } else if (!GameFrame.controller.isOver() && (GameFrame.page == 5 || GameFrame.page == 3 || GameFrame.page == 1)) {

                    int changeMark = 0;

                    chessPiece = ChessPiece.WHITE;
                    if (GameFrame.controller.gamePanel.fileArray[row][col] == 1) {
                        GameFrame.controller.gamePanel.fileArray[row][col] = -1;
                        changeMark = 1;

                    }

                    if (changeMark == 1) {
                        GameFrame.controller.gamePanel.fileArray[row][col] = 1;
                        GameFrame.controller.gamePanel.chessGrids[row][col].setChessPiece(ChessPiece.WHITE);
                        GameFrame.controller.gamePanel.chessGrids[row][col].repaint();
                    }
                    int[][] highlightArray;
                    highlightArray = ChessBoardPanel.validMovesStatic(chessPiece, GameFrame.controller.gamePanel.fileArray);

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (highlightArray[i][j] == 1) {
                                GameFrame.controller.gamePanel.chessGrids[i][j].setChessPiece(ChessPiece.HIGHLIGHT);
                                GameFrame.controller.gamePanel.chessGrids[i][j].repaint();
                            }
                        }
                    }

                    if ((GameFrame.page == 5 || GameFrame.page == 3 || GameFrame.page == 1) && a == 1 && changeMark == 0) {
                        GameFrame.controller.gamePanel.chessGrids[row][col].setChessPiece(ChessPiece.BLACK);
                        GameFrame.controller.gamePanel.chessGrids[row][col].repaint();
                    }

                    repaint();
                    //如果已经结束
                } else {
                    JFrame gameOver = new JFrame();
                    gameOver.setSize(500, 200);
                    Toolkit tk = this.getToolkit();// 得到窗口工具条
                    Dimension dm = tk.getScreenSize();//获得屏幕尺寸
                    gameOver.setLocation((int) (dm.getWidth() - 300) / 2, (int) (dm.getHeight() - 200) / 2);// 显示在屏幕中央

                    JLabel winnerMessage = new JLabel();
                    winnerMessage.setFont(new Font("Calibri", Font.ITALIC, 30));
                    winnerMessage.setText(GameFrame.controller.winner());

                    //胜局积分
                    winnerScoreRead();
                    if (userScore.containsKey(LoginFrame.username)) {//判断如果存在 key ,就修改value
                        int currentScore = userScore.get(LoginFrame.username);
                        currentScore++;
                        userScore.put(LoginFrame.username, currentScore);//修改
                    }
                    checkRank();
                    winnerScoreWrite();

                    JPanel selection = new JPanel();
                    JButton restart = new JButton();

                    JButton backToHall = new JButton();
                    backToHall.setSize(100, 30);
                    restart.setSize(100, 30);
                    backToHall.setText("Go back to Hall");
                    restart.setText("Replay this game");

                    selection.add(restart);
                    selection.add(backToHall);
                    backToHall.addActionListener(e -> {
//                        GameFrame.reboot();
                    });


                    gameOver.setDefaultLookAndFeelDecorated(true);
                    gameOver.add(selection, BorderLayout.SOUTH);
                    gameOver.add(winnerMessage, BorderLayout.CENTER);
                    gameOver.setVisible(true);
//                    gameOver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        linkClickAndRelease = false;
    }


    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    //下棋后（包括初始的4个），对棋盘和棋子的图形进行描绘
    public void drawPiece(Graphics g) {
        if (!isTheWorld) {
            if ((row + col) % 2 == 0) {
                g.setColor(gridColor1);
            } else {
                g.setColor(gridColor2);
            }
        } else {
            if ((row + col) % 2 == 0) {
                g.setColor(Color.blue);
            } else {
                g.setColor(Color.CYAN);
            }
        }
//        switch (GameFrame.bgNum) {
//            case 1:
//                if ((row + col) % 2 == 0){
//                    g.setColor(gridColor1);
//                }else{ g.setColor(gridColor2);}
//            case 2:
//                if ((row + col) % 2 == 0) {
//                    g.setColor(gridColor3);
//                }else{ g.setColor(gridColor4);}
//            default:
//                if ((row + col) % 2 == 0) {
//                    g.setColor(gridColor5);
//                }else{ g.setColor(gridColor6);}
//        }
        g.fillRect(1, 35, this.getWidth() - 2, this.getHeight() - 68);
        g.fillRect(35, 1, this.getWidth() - 68, this.getHeight() - 2);
        g.fillOval(30, 30, this.getWidth() - 60, this.getHeight() - 60);

//        for (int i = 80; i <= 720; i += 40) {
//            g.setColor(Color.BLACK);
//            g.drawLine(80, i, 720, i);
//            g.drawLine(i, 80, i, 720);
//        }

        if (this.chessPiece != null) {
            g.setColor(chessPiece.getColor());
            g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, chessSize);
        }


    }

    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        drawPiece(g);
    }

    public void robot1Play() {
        if (GameFrame.page == 3 || GameFrame.page == 1) {
            //如果无子可下，计数
            GameFrame.controller.cntCantMove(chessPiece.transColor());

            GameFrame.controller.gamePanel.fileArray = Robot.playerVsRobot1(GameFrame.controller.gamePanel.fileArray);
            GameFrame.controller.countScore();
            GameFrame.controller.updateStatues();

        }
    }


    public void robot2Play() {
        if (GameFrame.page == 3 || GameFrame.page == 1) {
            //如果无子可下，计数
            GameFrame.controller.cntCantMove(chessPiece.transColor());

            GameFrame.controller.gamePanel.fileArray = Robot.playerVsRobot2(GameFrame.controller.gamePanel.fileArray);
            GameFrame.controller.countScore();
            GameFrame.controller.updateStatues();

        }
    }

    public void robot3Play() {
        if (GameFrame.page == 3 || GameFrame.page == 1) {
            //如果无子可下，计数
            GameFrame.controller.cntCantMove(chessPiece.transColor());

            Robot rb = new Robot();
            ChessBoardPanel.fileArray = rb.playerVsRobot3(ChessBoardPanel.fileArray, ChessPiece.BLACK, 3);
            GameFrame.controller.countScore();
            GameFrame.controller.updateStatues();

        }
    }

    public void astrologerPlay() {
        if (GameFrame.page == 5) {
            //狂暴状态判断
            if (GameController.blackScore - GameController.whiteScore >= 10 && Robot.rage == false) {
                Robot.randomAbility = 6;
                Robot.rage = true;
                System.out.println("占星者已狂暴");
                JOptionPane.showMessageDialog(this, "占星者已进入狂暴状态");
                int thisRound = round;
            }
            //如果无子可下，计数
            if (!isTheWorld) {
                GameFrame.controller.cntCantMove(chessPiece.transColor());
                GameFrame.controller.gamePanel.fileArray = Robot.Astrologer(GameFrame.controller.gamePanel.fileArray);
                Robot rb = new Robot();
                ChessBoardPanel.fileArray = rb.playerVsRobot3(ChessBoardPanel.fileArray, ChessPiece.BLACK, 3);
            } else {
                isTheWorld = false;
            }
            GameFrame.controller.countScore();
            GameFrame.controller.updateStatues();
        }
    }

    public void madnessPlay() {
        if (GameFrame.page == 5) {
            //狂暴状态判断,由clock类代执行
            System.out.println(Robot.randomAbility2);
            //如果无子可下，计数
            if (!isTheWorld) {
                GameFrame.controller.cntCantMove(chessPiece.transColor());
                GameFrame.controller.gamePanel.fileArray = Robot.Madness(GameFrame.controller.gamePanel.fileArray);
                GameFrame.controller.gamePanel.fileArray = Robot.playerVsRobot2(GameFrame.controller.gamePanel.fileArray);

            } else {
                isTheWorld = false;
            }
            GameFrame.controller.countScore();
            GameFrame.controller.updateStatues();
        }
    }


    public void allRepaint() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (GameFrame.controller.gamePanel.fileArray[i][j] == 1) {
                    GameFrame.controller.gamePanel.chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                    GameFrame.controller.gamePanel.chessGrids[i][j].repaint();
                } else if (GameFrame.controller.gamePanel.fileArray[i][j] == -1) {
                    GameFrame.controller.gamePanel.chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                    GameFrame.controller.gamePanel.chessGrids[i][j].repaint();
                } else {
                    GameFrame.controller.gamePanel.chessGrids[i][j].setChessPiece(null);
                    GameFrame.controller.gamePanel.chessGrids[i][j].repaint();
                }
            }
        }
        repaint();
    }
}