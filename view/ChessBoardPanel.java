package view;

import components.ChessGridComponent;
import controller.GameController;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;

/**
 * 棋盘
 */
public class ChessBoardPanel extends JPanel {
    private final int CHESS_COUNT = 8;
    public static ChessGridComponent[][] chessGrids;
    public static int[][] fileArray = new int [8][8]; //用于临时记录棋盘变化

    public int isNull = 0;

    //初始化棋盘
    public ChessBoardPanel(int width, int height) {
        this.fileArray = new int [8][8];
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        int length = Math.min(width, height);
        this.setSize(length, length);
        this.setOpaque(false);
        ChessGridComponent.gridSize = length / CHESS_COUNT;        //网格尺寸
        ChessGridComponent.chessSize = (int) (ChessGridComponent.gridSize * 0.8);   //棋子尺寸
        System.out.printf("width = %d height = %d gridSize = %d chessSize = %d\n",
                width, height, ChessGridComponent.gridSize, ChessGridComponent.chessSize);

        initialChessGrids();//返回空的棋盘
        initialGame();//添加初始时的4个棋子

        repaint();
    }

    /**
     * set an empty chessboard设置一个空的棋盘
     */
    public void initialChessGrids() {
        chessGrids = new ChessGridComponent[CHESS_COUNT][CHESS_COUNT];

        //draw all chess grids 画出所以棋盘网格
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                ChessGridComponent gridComponent = new ChessGridComponent(i, j);
                gridComponent.setLocation(j * ChessGridComponent.gridSize, i * ChessGridComponent.gridSize);
                chessGrids[i][j] = gridComponent;
                this.add(chessGrids[i][j]);
            }
        }
    }

    /**
     * initial origin four chess
     */
    public void initialGame() {
        ChessGridComponent.round = 0;
        chessGrids[3][3].setChessPiece(ChessPiece.BLACK);
        chessGrids[3][4].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][3].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][4].setChessPiece(ChessPiece.BLACK);
        chessGrids[2][4].setChessPiece(ChessPiece.HIGHLIGHT);
        chessGrids[3][5].setChessPiece(ChessPiece.HIGHLIGHT);
        chessGrids[4][2].setChessPiece(ChessPiece.HIGHLIGHT);
        chessGrids[5][3].setChessPiece(ChessPiece.HIGHLIGHT);
        fileArray[3][3] = -1;
        fileArray[3][4] = 1;
        fileArray[4][3] = 1;
        fileArray[4][4] = -1;

    }


    //设置网格线（底色）
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    //判断是否可以下
    public boolean canClickGrid(int row, int col, int[][] validMoves) {
        //todo: complete this method
        return validMoves[row][col] == 1;
    }

    //输出可以下的位置（二维数组，坐标）
    public int[][] validMoves(ChessPiece currentPlayer, int[][] fileArray){
        // 获取当前棋手颜色
        int cl;

        if (currentPlayer.getColor()==Color.BLACK){
            cl = -1;}
        else{
            cl = 1;}

        int[][] enemyLoc = new int[64][2];
        int m = 0;

        int[][] nb = new int[8][8];

        //记录敌方位置
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                if (fileArray[j][k] == cl * (-1)) {
                    enemyLoc[m][0] = j;
                    enemyLoc[m][1] = k;
                    m++;
                }
            }
        }


        //遍历每一个敌方棋子
        for (int j = 0; j < m; j++) {
            int x = enemyLoc[j][0];
            int y = enemyLoc[j][1];
            if ((x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7)) {
                continue;
            }
            int[][] ard = around(x, y);
            int[][] loc = availableLoc(x, y, ard, cl, fileArray);

            for (int k = 0; k < loc.length; k++) {
                nb[loc[k][0]][loc[k][1]] = 1;
            }
        }
        return nb;
    }



    //此方法为上一个方法的复制。用于判断 下一步是否有可以下的位置，输出为isNull的改变
    public void hasValidMoves(ChessPiece currentPlayer, int[][] fileArray){
        // 获取当前棋手颜色
        int cl;
        isNull = 0;

        if (currentPlayer.getColor()==Color.BLACK){
            cl = -1;}
        else{
            cl = 1;}

        int[][] enemyLoc = new int[64][2];
        int m = 0;

        int[][] nb = new int[8][8];

        //记录敌方位置
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                if (fileArray[j][k] == cl * (-1)) {
                    enemyLoc[m][0] = j;
                    enemyLoc[m][1] = k;
                    m++;
                }
            }
        }


        //遍历每一个敌方棋子
        for (int j = 0; j < m; j++) {
            int x = enemyLoc[j][0];
            int y = enemyLoc[j][1];
            if ((x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7)) {
                continue;
            }
            int[][] ard = around(x, y);
            int[][] loc = availableLoc(x, y, ard, cl, fileArray);

            for (int k = 0; k < loc.length; k++) {
                nb[loc[k][0]][loc[k][1]] = 1;
            }
            isNull += loc.length;
        }
    }

    //输出可以下的位置2 （抽象方法）
    public static int[][] validMovesStatic(ChessPiece currentPlayer, int[][] fileArray){
        // 获取当前棋手颜色
        int cl;
        if (currentPlayer.getColor() == Color.BLACK){
            cl = 1;}
        else{
            cl = -1;}

        int[][] enemyLoc = new int[64][2];
        int m = 0;

        int[][] nb = new int[8][8];

        //记录敌方位置
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                if (fileArray[j][k] == cl * (-1)) {
                    enemyLoc[m][0] = j;
                    enemyLoc[m][1] = k;
                    m++;
                }
            }
        }


        //遍历每一个敌方棋子
        for (int j = 0; j < m; j++) {
            int x = enemyLoc[j][0];
            int y = enemyLoc[j][1];
            if ((x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7)) {
                continue;
            }
            int[][] ard = around(x, y);
            int[][] loc = availableLoc(x, y, ard, cl, fileArray);

            for (int k = 0; k < loc.length; k++) {
                nb[loc[k][0]][loc[k][1]] = 1;
            }
        }
        return nb;
    }


    //收集周围所有的格子（用于判断是否可以下）
    public static int[][] around(int x, int y) {
        if (y == 0 || y == 7 || x == 0 || x == 7) {
            if (y == 0 || y == 7) {
                return new int[][]{{x - 1, y}, {x + 1, y}}; //边缘棋子
            } else {
                return new int[][]{{x, y - 1}, {x, y + 1}};
            }
        } else                                             //中央棋子
            return new int[][]{{x, y + 1}, {x, y - 1}, {x - 1, y}, {x + 1, y}, {x - 1, y - 1}, {x + 1, y - 1}, {x + 1, y + 1}, {x - 1, y + 1}};
    }

    //收集周围所有的格子（用于实现下棋）
    public static int[][] aroundMove(int x, int y) {
        if (y == 0 || y == 7 || x == 0 || x == 7) {
            if (x==0 && y==0){
                return new int[][]{{x+1,y}, {x,y+1}, {x+1,y+1}};
            } else if (x==7 && y==0){
                return new int[][]{{x-1,y}, {x,y+1}, {x-1,y+1}};
            } else if (x==0 && y==7){
                return new int[][]{{x+1,y}, {x,y-1}, {x+1,y-1}};
            } else if (x==7 && y==7){
                return new int[][]{{x-1,y}, {x,y-1}, {x-1,y-1}};
            } else if (y == 0) {
                return new int[][]{{x - 1, y}, {x, y + 1}, {x + 1, y}, {x - 1, y + 1}, {x + 1, y + 1}};
            } else if (y == 7) {
                return new int[][]{{x - 1, y}, {x, y - 1}, {x + 1, y}, {x - 1, y - 1}, {x + 1, y - 1}};
            } else if (x == 0) {
                return new int[][]{{x, y - 1}, {x, y + 1}, {x + 1, y}, {x + 1, y + 1}, {x + 1, y - 1}};
            } else {
                return new int[][]{{x, y - 1}, {x, y + 1}, {x - 1, y}, {x - 1, y + 1}, {x - 1, y - 1}};
            }
        } else
            return new int[][]{{x, y + 1}, {x, y - 1}, {x - 1, y}, {x + 1, y}, {x - 1, y - 1}, {x + 1, y - 1}, {x + 1, y + 1}, {x - 1, y + 1}};
    }


    //判断周围的格子      1，空格子   2，对面不是空格子   3，对面是我方棋子   4，对面是敌方棋子，前推，直到 墙/空f，我方 t（while循环）
    public static int[][] availableLoc(int x, int y, int[][] ard, int cl, int[][] b) {
        int[][] loc0 = new int[64][2];
        int n = 0;

        for (int[] ints : ard) {  //遍历每一个周围棋子
            if (b[ints[0]][ints[1]] != 0)  //保证是空白
                continue;
            int x1 = 2 * x - ints[0];
            int y1 = 2 * y - ints[1];
            int xx = x - ints[0];
            int yy = y - ints[1];

            if (b[x1][y1] == cl) {  //待测‘可能符合的周围棋子’ 的另一侧 是我方棋子
                loc0[n][0] = ints[0];
                loc0[n][1] = ints[1];
                n++;
            } else {                   //另一侧是敌方棋子，开始向前追溯
                while (x1 <= 7 && x1 >= 0 && y1 <= 7 && y1 >= 0) {
                    if (b[x1][y1] == cl) {    //若是我方棋子
                        loc0[n][0] = ints[0];
                        loc0[n][1] = ints[1];
                        n++;
                        break;
                    }
                    if (b[x1][y1] == 0)      //若是空白
                        break;
                    if (b[x1][y1] == cl * (-1)) {  //若是敌方棋子，继续前推
                        x1 += xx;
                        y1 += yy;
                    }
                }
            }
        }

        int[][] loc1 = new int[n][2];
        for (int ii = 0; ii < n; ii++) {
            System.arraycopy(loc0[ii], 0, loc1[ii], 0, 2);
        }
        return loc1;
    }

    //输出可以下的位置3 （抽象方法）(颜色不反转）
    public static int[][] validMovesStatic3(ChessPiece currentPlayer, int[][] fileArray) {
        // 获取当前棋手颜色
        int cl;
        if (currentPlayer.getColor() == Color.BLACK) {
            cl = -1;
        } else {
            cl = 1;
        }

        int[][] enemyLoc = new int[64][2];
        int m = 0;

        int[][] nb = new int[8][8];

        //记录敌方位置
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                if (fileArray[j][k] == cl * (-1)) {
                    enemyLoc[m][0] = j;
                    enemyLoc[m][1] = k;
                    m++;
                }
            }
        }

        int ct = 0;

        //遍历每一个敌方棋子
        for (int j = 0; j < m; j++) {
            int x = enemyLoc[j][0];
            int y = enemyLoc[j][1];
            if ((x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7)) {
                continue;
            }
            int[][] ard = around(x, y);
            int[][] loc = availableLoc(x, y, ard, cl, fileArray);

            for (int k = 0; k < loc.length; k++) {
                if (nb[loc[k][0]][loc[k][1]] != 1) {
                    nb[loc[k][0]][loc[k][1]] = 1;
                    ct += 1;
                }
            }
        }

        int[][] validList = new int[ct][2];

        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (nb[i][j] == 1) {
                    validList[index][0] = i;
                    validList[index][1] = j;
                    index += 1;
                }
            }
        }
        return validList;
    }
}