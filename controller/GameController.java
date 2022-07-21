package controller;

//import com.sun.org.apache.xpath.internal.objects.XBooleanStatic;

import components.ChessGridComponent;
import components.Clock;
import model.ChessPiece;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;


/**
 * 游戏系统
 */
public class GameController extends JFrame implements Runnable {

    public ChessBoardPanel gamePanel;
    private StatusPanel statusPanel;
    private ChessPiece currentPlayer;    //当前玩家
    public static int blackScore;              //黑棋分数
    public static int whiteScore;              //白棋分数
    public int cantMove;
    TreeMap<Integer, Integer> tm = new TreeMap<>();//key为序号 value为坐标对应的数组index
    TreeMap<Integer, Integer> tmTemp;//读取进度时用，点击加载覆盖tm
    int[][] highlightArray;
    public static boolean wrong = false;


    //战役模式
    public static int clickRound = 0;
    public static int OKRound = 0;
    public static int coldDownTime = 0;


    //联网属性
    BufferedReader obr = null;
    BufferedWriter obw = null;//联网的流
    public static String yourIP = null;
    public static String yourPort = null;
    public static String matchIP = null;
    public static String matchPort = null;
    public ServerSocket server = null;
    public Socket yoursocket = null;
    public Socket matchsocket = null;
    public Random random = new Random();
    boolean yourTurn = false;
    boolean restartWaitting = false;
    boolean restartCanPress = true;
    boolean regrateCanPress = true;
    public static final int ONLINE_RESTART_REQUEST = 9;
    public static final int ONLINE_RESTART_YES = 10;
    public static final int ONLINE_REGRATE_REQUEST = 11;
    public static final int ONLINE_REGRATE_YES = 12;
    public static final int ONLINE_REGRATE_NO = 13;
    public static final int ONLINE_SURRENDER = 14;
    public static final int ONLINE_MYTURN = 15;
    public static final int ONLINE_YOURTURN = 16;
    public static final int ONLINE_WIN = 17;
    public static final int ONLINE_DISCONNECT = 404;
    int myNum;

    //构造器，获取游戏栏，状态栏，玩家初始分数
    public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel) {
        this.gamePanel = gamePanel;
        this.statusPanel = statusPanel;
        this.currentPlayer = ChessPiece.BLACK;
        blackScore = 2;
        whiteScore = 2;
    }

    //核心：输入已经判断过的可以下棋的位置、棋手颜色，更新下完之后的棋盘
    public void updateFileArray(int x, int y, Color c) {
        int cl;
        if (c == Color.BLACK) {
            cl = -1;
        } else {
            cl = 1;
        }

        int[][] b = new int[8][8];
        for (int ii = 0; ii < 8; ii++) {
            System.arraycopy(gamePanel.fileArray[ii], 0, b[ii], 0, 8);
        }
        int[][] ard = ChessBoardPanel.aroundMove(x, y);


        for (int i = 0; i < ard.length; i++) {   //遍历周围每一个棋子
            int x1 = ard[i][0];
            int y1 = ard[i][1];
            int xx = x1 - x;
            int yy = y1 - y;
            int d = 0;

            if (b[x1][y1] == cl || b[x1][y1] == 0) //排除不是敌方的情况
                continue;

            //若是敌方,递推
            while (x1 + xx <= 7 && x1 + xx >= 0 && y1 + yy <= 7 && y1 + yy >= 0) {
                if (b[x1 + xx][y1 + yy] == cl) {    //若是我方棋子
                    for (int j = -1; j <= d; j++) {
                        b[ard[i][0] + j * xx][ard[i][1] + j * yy] = cl; //换颜色
                    }
                    break;
                }
                if (b[x1 + xx][y1 + yy] == 0) {     //若是空白
                    break;
                }
                if (b[x1 + xx][y1 + yy] == cl * (-1)) {  //若是敌方棋子，继续前推
                    x1 += xx;
                    y1 += yy;
                    d += 1;
                }
            }
        }

        for (int ii = 0; ii < 8; ii++)
            System.arraycopy(b[ii], 0, gamePanel.fileArray[ii], 0, 8);

    }

    public void fireBallPlay(int x, int y) {
        Dimension size = GameFrame.controller.gamePanel.chessGrids[x][y].getSize();
        while (true) {
            if (0 < x && x < 7 && 0 < y && y < 7) {
                for (int a = x-1 ; a<=x+1 ; a++ ){
                    for (int b = y-1 ; b<=y+1 ; b++ ){
                        GameFrame.controller.gamePanel.fileArray[a][b] = -1;
                        GameFrame.controller.gamePanel.chessGrids[a][b].setChessPiece(ChessPiece.FIREBALL);
                        GameFrame.controller.gamePanel.chessGrids[a][b].paintImmediately(0, 0, size.width, size.height);
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int a = x-1 ; a<=x+1 ; a++ ){
                    for (int b = y-1 ; b<=y+1 ; b++ ){
                        GameFrame.controller.gamePanel.chessGrids[a][b].setChessPiece(ChessPiece.FIREBALL2);
                        GameFrame.controller.gamePanel.chessGrids[a][b].paintImmediately(0, 0, size.width, size.height);
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int a = x-1 ; a<=x+1 ; a++ ){
                    for (int b = y-1 ; b<=y+1 ; b++ ){
                        GameFrame.controller.gamePanel.chessGrids[a][b].setChessPiece(ChessPiece.FIREBALL3);
                        GameFrame.controller.gamePanel.chessGrids[a][b].paintImmediately(0, 0, size.width, size.height);
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int a = x-1 ; a<=x+1 ; a++ ){
                    for (int b = y-1 ; b<=y+1 ; b++ ){
                        GameFrame.controller.gamePanel.chessGrids[a][b].setChessPiece(ChessPiece.BLACK);
                        GameFrame.controller.gamePanel.chessGrids[a][b].paintImmediately(0, 0, size.width, size.height);
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GameFrame.controller.updateStatues();
                break;
            } else {
                JOptionPane.showMessageDialog(this, "不能在边角位释放奥数火球");
            }
        }
    }

    //切换下棋玩家
    public void swapPlayer() {
        //先计算分数
        countScore();

        //然后执行切换
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;

        //再将下一个玩家、玩家得分更新在状态栏上
        updateStatues();
    }

    public void updateStatues() {
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
        if (GameFrame.page == 5) {
            switch (GameFrame.heroChoose) {
                case 1:
                    statusPanel.setheroLabelText(GameFrame.heroChoose, coldDownTime, ChessGridComponent.isTheWorld);
                    break;
                default:
                    statusPanel.setheroLabelText(GameFrame.heroChoose, coldDownTime, ChessGridComponent.isFireBall);
                    break;
            }
            switch (GameFrame.enemyChoose) {
                case 1:
                    statusPanel.setenemyLabelText(GameFrame.enemyChoose, Clock.clockTime, Robot.starFallX, Robot.starFallY, ChessGridComponent.isStarFall);
                    break;
                default:
                    statusPanel.setenemyLabelText(GameFrame.enemyChoose, Clock.clockTime, 0, 0, ChessGridComponent.isFireBall);
                    break;
            }
        }
//        statusPanel.setTimeLabelText(Clock.clockTime);
    }

    public void cntCantMove(ChessPiece chessPiece) {

        //判断完成点击事件的棋手的对方
        GameFrame.controller.gamePanel.hasValidMoves(chessPiece, GameFrame.controller.gamePanel.fileArray);
        if (GameFrame.controller.gamePanel.isNull == 0) {
            cantMove += 1;

            //再判断自己
            GameFrame.controller.gamePanel.hasValidMoves(chessPiece.getOppositeChessPiece(), GameFrame.controller.gamePanel.fileArray);
            if (GameFrame.controller.gamePanel.isNull == 0) {
                cantMove += 1;
            }

        }

        System.out.println("int canMove: " + cantMove);
    }

    public boolean isSkip() {
        return (cantMove != 0);
    }

    public boolean isOver() {
        return (cantMove >= 2 || blackScore + whiteScore == 64);
    }

    public String winner() {
        if (blackScore > whiteScore)
            return "Congratulations! Black player wins!";
        else if (blackScore < whiteScore)
            return "Congratulations! White player wins!";
        else return "Flat game!";
    }

    //计算分数
    public void countScore() {
        //todo: modify the countScore method
        blackScore = 0;
        whiteScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gamePanel.fileArray[i][j] == 1) {
                    whiteScore++;
                } else if (gamePanel.fileArray[i][j] == -1)
                    blackScore++;
            }
        }
    }

    public ChessPiece getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessBoardPanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(ChessBoardPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public String getCurrentPath() throws IOException {
        File directory = new File(".");
        return directory.getCanonicalPath();
    }

    public void readFileData(String fileName, int k) {
        System.out.println("readFileData");
        System.out.println(k);
        //todo: read date from file
        List<String> fileData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(
                "C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\stepFile"
                        + fileName + "\\undo" + k + ".txt")
        )) {

            String line = br.readLine();
            int num = 0;
            int chessCheck = 0;
            if (!wrong) {
                while (line != null && num < 8) {
                    String[] temp = line.split(",");
                    if (temp.length == 8) {
                        for (int i = 0; i < temp.length; i++) {

                            if (Integer.parseInt(temp[i]) == 1 || Integer.parseInt(temp[i]) == -1 || Integer.parseInt(temp[i]) == 0) {
                                if (Integer.parseInt(temp[i]) == 1 || Integer.parseInt(temp[i]) == -1) {
                                    chessCheck++;
                                }
                                ChessBoardPanel.fileArray[num][i] = Integer.parseInt(temp[i]);
                            } else {
                                JOptionPane.showMessageDialog(this, "102，棋子错误");
                                wrong = true;
                            }
                        }
                        line = br.readLine();
                        num++;
                    } else {
                        JOptionPane.showMessageDialog(this, "101，棋盘错误");
                        wrong = true;
                        break;
                    }
                }
            }
            if (!wrong) {
                if (line.equals("BLACK") || line.equals("WHITE")) {
                    currentPlayer = ChessPiece.valueOf(line);
                } else {
                    JOptionPane.showMessageDialog(this, "103，缺少行棋方");
                    wrong = true;
                }
            }
            line = br.readLine();
            if (!wrong) {
                if (Integer.parseInt(line) == (chessCheck - 4)) {
                    ChessGridComponent.round = Integer.parseInt(line);
                } else {
                    JOptionPane.showMessageDialog(this, "106，回合数错误");
                    wrong = true;
                }
            }
            //用file数组复现棋盘
            highlightArray = ChessBoardPanel.validMovesStatic((currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK, GameFrame.controller.gamePanel.fileArray);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Dimension size = ChessBoardPanel.chessGrids[i][j].getSize();
                    if (GameFrame.controller.gamePanel.fileArray[i][j] == 1) {
                        ChessBoardPanel.chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                        ChessBoardPanel.chessGrids[i][j].paintImmediately(0, 0, size.width, size.height);
                    } else if (GameFrame.controller.gamePanel.fileArray[i][j] == -1) {
                        ChessBoardPanel.chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
//                        ChessBoardPanel.chessGrids[i][j].repaint();
                        ChessBoardPanel.chessGrids[i][j].paintImmediately(0, 0, size.width, size.height);
                    } else if (highlightArray[i][j] == 1) {
                        GameFrame.controller.gamePanel.chessGrids[i][j].setChessPiece(ChessPiece.HIGHLIGHT);
//                        GameFrame.controller.gamePanel.chessGrids[i][j].repaint();
                        ChessBoardPanel.chessGrids[i][j].paintImmediately(0, 0, size.width, size.height);
                    } else {
                        ChessBoardPanel.chessGrids[i][j].setChessPiece(null);
//                        ChessBoardPanel.chessGrids[i][j].repaint();
                        ChessBoardPanel.chessGrids[i][j].paintImmediately(0, 0, size.width, size.height);
                    }
                }
            }
            //repaint();

            String s = new String();
            if (currentPlayer == ChessPiece.BLACK)
                s = "黑";
            else s = "白";
            //JOptionPane.showMessageDialog(null, "打开成功 后面到" + s + "方下");
            countScore();
            statusPanel.setPlayerText(currentPlayer.name());
            statusPanel.setScoreText(blackScore, whiteScore);
            wrong = false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "104，文件格式错误");
        }
    }

    public void writeDataToFile(String fileName) {
        //在未点击的情况下，保存最后一次棋盘
        File file = new File("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\stepFile\\undo" + ChessGridComponent.round + ".txt");
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
            String roundString = String.valueOf(ChessGridComponent.round);
            bw.write(roundString);
            bw.flush();
            bw.close();
        } catch (Exception e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }


        File stepFile = new File("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\stepFile" + fileName);
        if (!stepFile.exists()) {//如果文件夹不存在
            stepFile.mkdir();//创建文件夹

            for (int i = 0; i <= ChessGridComponent.round; i++) {
                String startPath = "C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\stepFile\\undo" + i + ".txt";
                String endPath = "C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\stepFile" + fileName + "\\undo" + i + ".txt";
                File oldPaths = new File(startPath);
                File newPaths = new File(endPath);
                if (!newPaths.exists()) {
                    try {
                        Files.copy(oldPaths.toPath(), newPaths.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    newPaths.delete();
                    try {
                        Files.copy(oldPaths.toPath(), newPaths.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("文件移动成功！起始路径：" + startPath);
            }
        }

//        //todo: write data into file
//        File file = new File("file" + fileName + ".txt");
//        try {
//            //用BufferedWriter高效地将数据写入文件
//            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    if (j != 0)
//                        bw.write(",");
//                    bw.write(String.valueOf(ChessBoardPanel.fileArray[i][j]));
//                }
//                bw.write("\r\n");
//            }
//            bw.write(currentPlayer + "\r\n");
//            bw.write(ChessGridComponent.round + "\r\n");
//            bw.write("0表示空白，1表示黑棋，-1表示白棋。true表示该黑棋走了");
//            bw.flush();
//            bw.close();
//            JOptionPane.showMessageDialog(null, "请记住您的存档号码：" + fileName);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public void undo() {
        try {
            if (ChessGridComponent.round > 0) {
                File file = new File(GameFrame.currentDirectory() + "\\src\\stepFile\\undo" + (ChessGridComponent.round - 1) + ".txt");
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                int num = 0;
                while (line != null && num < 8) {
                    String[] temp = line.split(",");
                    for (int i = 0; i < temp.length; i++) {
                        ChessBoardPanel.fileArray[num][i] = Integer.parseInt(temp[i]);
                    }
                    line = br.readLine();
                    num++;
                }
                currentPlayer = ChessPiece.valueOf(line);
                //用file数组复现棋盘
                int[][] highlightArray;
                highlightArray = ChessBoardPanel.validMovesStatic((currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK, GameFrame.controller.gamePanel.fileArray);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (GameFrame.controller.gamePanel.fileArray[i][j] == 1) {
                            ChessBoardPanel.chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                            ChessBoardPanel.chessGrids[i][j].repaint();
                        } else if (GameFrame.controller.gamePanel.fileArray[i][j] == -1) {
                            ChessBoardPanel.chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                            ChessBoardPanel.chessGrids[i][j].repaint();
                        } else if (highlightArray[i][j] == 1) {
                            GameFrame.controller.gamePanel.chessGrids[i][j].setChessPiece(ChessPiece.HIGHLIGHT);
                            GameFrame.controller.gamePanel.chessGrids[i][j].repaint();
                        } else {
                            ChessBoardPanel.chessGrids[i][j].setChessPiece(null);
                            ChessBoardPanel.chessGrids[i][j].repaint();
                        }
                    }
                }
                String s = new String();
                if (currentPlayer == ChessPiece.BLACK)
                    s = "黑";
                else s = "白";
                JOptionPane.showMessageDialog(null, "悔棋成功");
                countScore();
                statusPanel.setPlayerText(currentPlayer.name());
                statusPanel.setScoreText(blackScore, whiteScore);
                //删除文件
                file.delete();
                ChessGridComponent.round--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //调用view文件夹里的ChessBoardPanel判断是否合法
    public boolean canClick(int row, int col) {
        return (gamePanel.canClickGrid(row, col, gamePanel.validMoves(currentPlayer, gamePanel.fileArray)));
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub

        System.out.println("执行run()");
        String line = null;
        try {
            server = new ServerSocket(Integer.parseInt(yourPort));
            while (true) {
                matchsocket = server.accept();
                System.out.println("连接成功");
                obr = new BufferedReader(new InputStreamReader(matchsocket.getInputStream()));
                line = obr.readLine();

                int x = Integer.parseInt(line.trim());
                System.out.println("读取数据" + x);

                int y = Integer.parseInt(line.trim());
                System.out.println("读取数据" + y);

                Color c = Color.decode((line.trim()));
                System.out.println("读取数据" + c);
                translator(x, y, c);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void send(int x, int y, Color c) {
        try {
            yoursocket = new Socket(matchIP, Integer.parseInt(matchPort.trim()));
            obw = new BufferedWriter(new OutputStreamWriter(yoursocket.getOutputStream()));

            obw.write(String.valueOf(x));
            obw.newLine();
            System.out.println("发送数据" + x);

            obw.write(String.valueOf(y));
            obw.newLine();
            System.out.println("发送数据" + y);

            obw.write(String.valueOf(c));
            obw.newLine();
            System.out.println("发送数据" + c);

            obw.flush();
            obw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void translator(int x, int y, Color c) {

    }

    public void translator1(int x, int y, Color c) {
        if (ONLINE_DISCONNECT == x) {
            yourIP = null;
            yourPort = null;
            matchIP = null;
            matchPort = null;
            obw = null;
            obr = null;
            yoursocket = null;
            matchsocket = null;
            server = null;

            GameFrame.page = 1;
            SwingUtilities.invokeLater(() -> {
                GameFrame mainFrame1 = new GameFrame(800);
                mainFrame1.setVisible(true);
                mainFrame1.dispose();
                this.repaint();
            });

        } else if (ONLINE_RESTART_REQUEST == x) {
            //只能判断胜负后才能重开
            restartWaitting = true;
            JOptionPane.showMessageDialog(this, "对手希望再来一局");

        } else if (ONLINE_RESTART_YES == x) {
            restartWaitting = false;
            restartCanPress = true;
            //onlineRestart();
            int j = random.nextInt(100);
            myNum = ONLINE_DISCONNECT + j + 1;
            send(myNum, 8, Color.BLACK);

        } else if (ONLINE_REGRATE_REQUEST == x) {
            regrateCanPress = false;
            int isYes = JOptionPane.showConfirmDialog(this, "对方请求悔棋，是否同意", "take back a move", JOptionPane.YES_NO_OPTION);
            if (isYes == 0) {
                send(ONLINE_REGRATE_YES, 8, Color.black);

                yourTurn = !yourTurn;
                undo();
                regrateCanPress = true;
                this.repaint();

            } else {
                send(ONLINE_REGRATE_NO, 8, Color.black);

                regrateCanPress = true;
            }
        } else if (x > ONLINE_DISCONNECT && x <= ONLINE_DISCONNECT + 100) {

            yourTurn = myNum > x ? true : false;
            currentPlayer = ChessPiece.BLACK;
            if (yourTurn) {
                send(ONLINE_MYTURN, 8, Color.black);
                JOptionPane.showMessageDialog(this, "执先手");
            } else {
                send(ONLINE_YOURTURN, 8, Color.black);
                JOptionPane.showMessageDialog(this, "执后手");
            }

        } else if (ONLINE_REGRATE_YES == x) {

            yourTurn = !yourTurn;
            undo();
            regrateCanPress = true;
            this.repaint();

        } else if (ONLINE_REGRATE_NO == x) {
            JOptionPane.showMessageDialog(this, "对方不同意悔棋");
            regrateCanPress = true;
        } else if (ONLINE_SURRENDER == x) {
            //canPlay = false;
            JOptionPane.showMessageDialog(this, "胜利：对方认输");
        } else if (x >= 0 && x < 8) {
            if (GameFrame.controller.canClick(x, y)) {
//                if (this.chessPiece == null || this.chessPiece == ChessPiece.HIGHLIGHT) {
//                    this.chessPiece = GameFrame.controller.getCurrentPlayer();
//                    System.out.print("已获得当前棋手");
//                }
                GameFrame.controller.updateFileArray(x, y, currentPlayer.getColor());
                GameFrame.controller.swapPlayer();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        System.out.print(GameFrame.controller.gamePanel.fileArray[i][j]);
                    }
                    System.out.println();
                }

//            GameFrame.controller.gamePanel.chessGrids[0][0].setChessPiece(ChessPiece.BLACK);
//            GameFrame.controller.gamePanel.chessGrids[0][0].repaint();
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
                this.repaint();
            } else if (ONLINE_MYTURN == x) {
                yourTurn = false;
                currentPlayer = ChessPiece.BLACK;
            } else if (ONLINE_YOURTURN == x) {
                yourTurn = true;
                currentPlayer = yourTurn ? ChessPiece.BLACK : ChessPiece.WHITE;
            } else if (ONLINE_WIN == x) {
                JOptionPane.showMessageDialog(this, "游戏结束");
            }
        }
    }

    public Random getRandom() {
        return random;
    }

    //战役模式技能
    public void theWorld() {
        if (ChessGridComponent.round > 4) {
            if (ChessGridComponent.isTheWorldOK) {
                ChessGridComponent.isTheWorld = true;
                ChessGridComponent.isTheWorldOK = false;
                clickRound = ChessGridComponent.round;
                coldDownTime = (4 - (OKRound - clickRound));
                System.out.println("时间暂停成功释放");
            } else {
                System.out.println("时间暂停正在冷却，还剩" + coldDownTime + "个回合");
            }
        } else {
            System.out.println("无法在第0,1,2,3,4,5回合释放时间暂停");
        }
    }

    public void fireBall() {
        if (ChessGridComponent.round > 6) {
            if (ChessGridComponent.isFireBallOK) {
                ChessGridComponent.isFireBall = true;
                ChessGridComponent.isFireBallOK = false;
                clickRound = ChessGridComponent.round;
                coldDownTime = (3 - (OKRound - clickRound));
                System.out.println("奥数火球成功释放");
                GameFrame.controller.updateStatues();
            } else {
                System.out.println("奥数火球正在冷却，还剩" + coldDownTime + "个回合");
            }
        } else {
            System.out.println("无法在第0,1,2,3，4，5回合释放奥数火球");
        }
    }


    public void setRandom(Random random) {
        this.random = random;
    }

    public int getMyNum() {
        return myNum;
    }

    public void setMyNum(int myNum) {
        this.myNum = myNum;
    }
}
