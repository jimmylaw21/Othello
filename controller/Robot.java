package controller;

import com.sun.org.apache.xerces.internal.xs.StringList;
import components.ChessGridComponent;
import model.ChessPiece;
import view.ChessBoardPanel;
import view.GameFrame;
import view.clickMusic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Robot {

    public static boolean rage = false;
    public static int randomAbility = 2;
    public static int randomAbility2 = 2;
    public static int starFallX;
    public static int starFallY;

    //难度1人机（随机数）
    public static int[][] playerVsRobot1(int[][] board) {

        ChessPiece robotPiece = ChessPiece.BLACK; //方法为共用方法，存在反转，故此处black实际为white
        int[][] validMove = ChessBoardPanel.validMovesStatic(robotPiece, board);
        ArrayList<Integer> validArray = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (validMove[i][j] == 1) {
                    validArray.add(i);
                    validArray.add(j);
                }
            }
        }

        Random rd = new Random();
        int randomNumber = rd.nextInt((validArray.size() / 2));

        int row = validArray.get(randomNumber * 2);
        int col = validArray.get(randomNumber * 2 + 1);

        GameFrame.controller.updateFileArray(row, col, Color.WHITE);
        return GameFrame.controller.gamePanel.fileArray;
    }

    //难度2人机（简单一阶局部最优）
    public static int[][] playerVsRobot2(int[][] board) {
        int[][] validMoves = ChessBoardPanel.validMovesStatic(ChessPiece.BLACK, board);
        int row = 1;
        int col = 1;
        int weight = 1;
        int maxNub = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {


                if (validMoves[i][j] == 1) {
                    if ((i == 0 && j == 0) || (i == 7 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 7)) {
                        row = i;
                        col = j;
                        weight = 3;
                        break;
                    } else if (i == 0 || j == 0 || i == 7 || j == 7) {
                        row = i;
                        col = j;
                        weight = 2;
                    } else if (getNumberCanEat(ChessPiece.WHITE, i, j, board) > maxNub && weight == 1) {
                        row = i;
                        col = j;
                        maxNub = getNumberCanEat(ChessPiece.WHITE, i, j, board);
                    }
                }
            }
            if (weight == 3)
                break;
        }

        GameFrame.controller.updateFileArray(row, col, Color.WHITE);
        return GameFrame.controller.gamePanel.fileArray;

    }

    //难度3人机    判断行动力 +  α-β剪枝
    public int[][] playerVsRobot3(int[][] board, ChessPiece CP, int depth) {

        int[][] optionalLoc = ChessBoardPanel.validMovesStatic(CP, board);
        int bestRow = -1;
        int bestCol = -1;
        int bestValue = -10000;
        int[][] values = new int[8][8];


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (optionalLoc[i][j] == 1) {
                    System.out.println("-----------------------------------------新的根节点---------------------------------------------------");
                    Node rootNode = new Node();
                    rootNode.setBoard(board);
                    rootNode.setRow(i);
                    rootNode.setCol(j);
                    rootNode.setCl(CP.getColor());
                    int value = minimax(false, depth, rootNode, -10000, 10000);
                    values[i][j] = value;
                    System.out.println("value:" + value);

                    if (bestValue < value) {
                        bestValue = value;
                        bestRow = i;
                        bestCol = j;
                    }

                }
            }
        }

        for(int i = 0; i < 8; i++){
            System.out.println(Arrays.toString(values[i]));
        }


        return updateBoard(bestRow, bestCol, CP.getOppositeColor(), board);
    }

//    private int getMinimax(boolean isMax, int currentDepth, int a, int b, Node node){
//        //如果到了叶子（可能提前，因为无子可下）
//        if(currentDepth == 0 || node.getChildren().size() == 0){
//            return node.getValue();
//        }
//        //MIN层（指需要选择下层中最小的，该层每个节点需要从下一层获取beta，也就是最坏情况的上界）
//        if(!isMax){
//            int min = Integer.MAX_VALUE;
//
//            //遍历 每一个子节点
//            for (int i=0; i<node.getChildren().size(); i++){
//                int blow = getMinimax(false,currentDepth + 1, a, b, node.getChildren().get(i));
//
//                // beta表示从这个节点往下搜索最坏结局的上界
//                if(blow < b){
//                    b = blow;
//                }
//                // 孩子结点最小值 为父节点的值
//                if(min > node.getChildren().get(i).getValue()){
//                    min = node.getChildren().get(i).getValue();
//                }
//                node.setValue(min);
//
//                // 剪枝 ：如果从当前格局搜索下去，不可能找到比已知最优解更好的解，则停止这个格局分支的搜索（剪枝），回溯到父节点继续搜索。
//
//
//            }
//        }
//    }

    static class Node {
        int row;
        int col;
        Color cl;
        int value;
        int[][] board;

        public void setBoard(int[][] board) {
            this.board = board;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public void setCl(Color cl) {
            this.cl = cl;
        }


        List<Node> children = new ArrayList<>();

        public void computeChildren(int x, int y, Color cl, int[][] board) {
            ChessPiece childColor;
            if (cl == Color.BLACK) {
                childColor = ChessPiece.WHITE;
                cl = Color.WHITE;
            } else {
                childColor = ChessPiece.BLACK;
                cl = Color.BLACK;
            }
            int[][] copy = new int[8][8];
            for (int ii = 0; ii < 8; ii++)
                System.arraycopy(board[ii], 0, copy[ii], 0, 8);
            board = updateBoard(x, y, cl, copy);



            int[][] childrenLoc = ChessBoardPanel.validMovesStatic3(childColor, board);

            for (int i = 0; i < childrenLoc.length; i++) {
                Node child = new Node();
                child.setRow(childrenLoc[i][0]);
                child.setCol(childrenLoc[i][1]);
                child.setCl(cl);
                child.setBoard(board);
                children.add(child);
            }
        }

//        public void setPruning(boolean isAlpha){
//            if(isAlpha){
//                children.remove();
//            }
//        }

        public List<Node> getChildren() {
            System.out.println("长度是" +  children.size());
            computeChildren(row, col, cl, board);
            return children;
        }

//        public void setPruning(boolean is)

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }


    /*
     * 剪枝算法实现 depth：设置搜索的最大深度 isAI：默认为false,我方先下 alpha和beta继承自父节点
     */
    private int minimax(boolean isAI, int depth, Node node, int a, int b) {
        if (node.getChildren().size() == 0 || depth == 0) {
            System.out.println("获取叶子节点:" + node.row + " " + node.col + "   value:" + weightTableLoc(node.row, node.col));
            return weightTableLoc(node.row, node.col);
        }
        // beta
        if (isAI) {
            int min = Integer.MAX_VALUE;
            int index = 0;
            System.out.println("新一个最小层节点。开始搜索孩子节点。当前αβ为 " + a + "," + b + "   当前深度为：" + depth);
            for (Node children : node.getChildren()) {
                System.out.println();
                System.out.println("新一轮 对MIN层节点的孩子 循环");
                index++;
                int bCopy = b;
                int tmp = minimax(false, depth - 1, children, a, b);

                // beta表示从这个节点往下搜索最坏结局的上界
                if (b > tmp) {
                    System.out.println("b更新为" + tmp + "   当前深度为：" + depth);
                    b = tmp;
                }

                // 孩子结点最小值 为父节点的值
                if (min > children.getValue()) {
                    min = children.getValue();
                }
                node.setValue(min);

                // 剪枝 ：如果从当前格局搜索下去，不可能找到比已知最优解更好的解，则停止这个格局分支的搜索（剪枝），回溯到父节点继续搜索。
                if (b <= a) {
                    // 此节点剩下的孩子结点被剪枝
//                    for (Node node2 : node.getChildren().subList(index, node.getChildren().size())) {
//                        node2.setPruning(false);
//                        prunedNodes.add(node2);
//                    }
                    System.out.println("剪枝 beta");
                    return bCopy;
                }

            }

            return b;
        } else {// alpha
            int max = Integer.MIN_VALUE;
            int index = 0;
            System.out.println("新一个最大层节点。开始搜索孩子节点。当前αβ为 " + a + "," + b + "   当前深度为：" + depth);
            for (Node children : node.getChildren()) {
                System.out.println();
                System.out.println("新一轮 对MAX层节点的孩子 循环");
                // 标识此结点的第几个孩子结点，为剪枝做准备
                index++;
                int tmp = minimax(true, depth - 1, children, a, b);

                int aCopy = a;
                // alpha表示搜索到当前节点时已知的最好选择的下界
                if (a < tmp) {
                    System.out.println("a更新为" + tmp + "   当前深度为：" + depth);
                    a = tmp;
                }
                // 孩子结点最大值 为父节点的值
                if (max < children.getValue()) {
                    max = children.getValue();
                }
                node.setValue(max);

                // 剪枝 ：如果从当前格局搜索下去，不可能找到比已知最优解更好的解，则停止这个格局分支的搜索（剪枝），回溯到父节点继续搜索。
                if (b <= a) {
//                    for (Node node2 : node.getChildren().subList(index, node.getChildren().size())) {
//                        node2.setPruning(true);
//                        prunedNodes.add(node2);
//                    }
//                    System.out.println("剪枝 alpha");
                    return aCopy;
                }
            }
            return a;
        }
    }


    //1.权值表
    public static int weightTableLoc(int row, int cal) {
        int[][] table = {
                {500,-3,10, 7, 7,10,-3,500},
                {-3,-5, 1, 2, 2, 1,-5,-3},
                {10, 1, 5, 2, 2, 5, 1, 10},
                {7, 2, 3, 0, 0, 3, 2, 7},
                {7, 2, 3, 0, 0, 3, 2, 7},
                {10, 1, 5, 3, 3, 5, 1, 10},
                {-3,-5, 1, 1, 1, 1,-5, -3},
                {500,-3,10,7,7,10,-3,500}
        };
        return table[row][cal];
    }

    //2.稳定子（）


    //(core)输入已经判断过的可以下棋的位置、棋手颜色，
    public static int[][] updateBoard(int x, int y, Color c, int[][] board) {
        int cl;
        if (c == Color.BLACK) {
            cl = -1;
        } else {
            cl = 1;
        }

        int[][] b = new int[8][8];
        for (int ii = 0; ii < 8; ii++) {
            System.arraycopy(board[ii], 0, b[ii], 0, 8);
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
            System.arraycopy(b[ii], 0, board[ii], 0, 8);

        return board;
    }


    public static int[][] antiPlayerVsRobot2(int[][] board) {
        int[][] validMoves = ChessBoardPanel.validMovesStatic(ChessPiece.WHITE, board);
        int row = 1;
        int col = 1;
        int weight = 1;
        int maxNub = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {


                if (validMoves[i][j] == 1) {
                    if ((i == 0 && j == 0) || (i == 7 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 7)) {
                        row = i;
                        col = j;
                        weight = 3;
                        break;
                    } else if (i == 0 || j == 0 || i == 7 || j == 7) {
                        row = i;
                        col = j;
                        weight = 2;
                    } else if (antiNumberCanEat(ChessPiece.BLACK, i, j, board) > maxNub && weight == 1) {
                        row = i;
                        col = j;
                        maxNub = antiNumberCanEat(ChessPiece.BLACK, i, j, board);
                    }
                }
            }
            if (weight == 3)
                break;
        }

        GameFrame.controller.updateFileArray(row, col, Color.BLACK);
        return GameFrame.controller.gamePanel.fileArray;

    }

    //占星者技能
    public static int[][] Astrologer(int[][] board) {
        int[][] validMoves = ChessBoardPanel.validMovesStatic(ChessPiece.BLACK, board);
        int row = 1;
        int col = 1;
        int weight = 1;
        int maxNub = 0;
        Dimension size = GameFrame.controller.gamePanel.chessGrids[row][col].getSize();

        //陨石技能
        Random r = new Random();
        Random x = new Random();
        Random y = new Random();
        int randomR = r.nextInt(10);

        if (randomR <= randomAbility && randomR >= 1) {
            for (int i = 0; i < 1; i++) {
                int randomX = x.nextInt(8);
                int randomY = y.nextInt(8);
                row = randomX;
                starFallX = randomX;
                col = randomY;
                starFallY = randomY;
                GameFrame.controller.gamePanel.chessGrids[row][col].setChessPiece(ChessPiece.STARFALL);
                GameFrame.controller.gamePanel.chessGrids[row][col].paintImmediately(0, 0, size.width, size.height);
                new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\炮声（沉闷）.wav")).start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GameFrame.controller.updateFileArray(row, col, Color.WHITE);
            }
            System.out.println("陨石坠落已使用");
            ChessGridComponent.isStarFall = true;
            GameFrame.controller.updateStatues();
        }

        return GameFrame.controller.gamePanel.fileArray;

    }

    //疯狂造物技能
    public static int[][] Madness(int[][] board) {
        int[][] validMoves = ChessBoardPanel.validMovesStatic(ChessPiece.BLACK, board);
        int row = 1;
        int col = 1;
        int weight = 1;
        int maxNub = 0;
        Dimension size = GameFrame.controller.gamePanel.chessGrids[row][col].getSize();

        //陨石技能
        Random r = new Random();
        Random x = new Random();
        Random y = new Random();
        int randomR = r.nextInt(10);
        if(GameController.blackScore > 6) {
            if (randomR <= randomAbility2 && randomR >= 1) {
                int i = 0;
                new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\喀秋莎音效.wav")).start();
                while (true) {
                    int randomX = x.nextInt(8);
                    int randomY = y.nextInt(8);
                    row = randomX;
                    col = randomY;
                    if (GameFrame.controller.gamePanel.fileArray[row][col] == -1) {
                        GameFrame.controller.gamePanel.chessGrids[row][col].setChessPiece(ChessPiece.FIREBALL2);
                        GameFrame.controller.gamePanel.chessGrids[row][col].paintImmediately(0, 0, size.width, size.height);
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        GameFrame.controller.updateFileArray(row, col, null);
                        i++;
                    }
                    if (i > 2) {
                        break;
                    }
                }
                System.out.println("空袭已使用");

                GameFrame.controller.updateStatues();
            }
        }

        return GameFrame.controller.gamePanel.fileArray;
    }


    //方法：获取可以吃掉的敌方子数 &
    public static int getNumberCanEat(ChessPiece CP, int row, int col, int[][] board) {


        //ps:这里必须把board复制一下再搞，不然源头（fileArray）都会改变。why？？
        int[][] bd = new int[8][8];
        for (int ii = 0; ii < 8; ii++) {
            System.arraycopy(board[ii], 0, bd[ii], 0, 8);
        }

        int[][] ard = ChessBoardPanel.aroundMove(row, col);
        int number = 0;

        for (int[] ints : ard) {   //遍历周围每一个棋子
            int x1 = ints[0];
            int y1 = ints[1];
            int xx = x1 - row;
            int yy = y1 - col;
            int d = 0;

            int cl;
            if (CP == ChessPiece.BLACK)
                cl = -1;
            else cl = 1;

            if (bd[x1][y1] == cl || bd[x1][y1] == 0) //排除不是敌方的情况(白或空）
                continue;

            //若是敌方,递推
            while (x1 + xx <= 7 && x1 + xx >= 0 && y1 + yy <= 7 && y1 + yy >= 0) {
                if (bd[x1 + xx][y1 + yy] == cl) {    //若是我方棋子
                    for (int j = -1; j <= d; j++) {
                        bd[ints[0] + j * xx][ints[1] + j * yy] = cl; //换颜色
                    }
                    number += (d + 1);
                    break;
                }
                if (bd[x1 + xx][y1 + yy] == 0) {     //若是空白
                    break;
                }
                if (bd[x1 + xx][y1 + yy] == cl * (-1)) {  //若是敌方棋子，继续前推
                    x1 += xx;
                    y1 += yy;
                    d += 1;
                }
            }
        }

        return number;
    }

    //方法：获取可以吃掉的敌方子数
    public static int antiNumberCanEat(ChessPiece CP, int row, int col, int[][] board) {


        //ps:这里必须把board复制一下再搞，不然源头（fileArray）都会改变。why？？
        int[][] bd = new int[8][8];
        for (int ii = 0; ii < 8; ii++) {
            System.arraycopy(board[ii], 0, bd[ii], 0, 8);
        }

        int[][] ard = ChessBoardPanel.aroundMove(row, col);
        int number = 0;

        for (int[] ints : ard) {   //遍历周围每一个棋子
            int x1 = ints[0];
            int y1 = ints[1];
            int xx = x1 - row;
            int yy = y1 - col;
            int d = 0;

            int cl;
            if (CP == ChessPiece.WHITE)
                cl = -1;
            else cl = 1;

            if (bd[x1][y1] == cl || bd[x1][y1] == 0) //排除不是敌方的情况(白或空）
                continue;

            //若是敌方,递推
            while (x1 + xx <= 7 && x1 + xx >= 0 && y1 + yy <= 7 && y1 + yy >= 0) {
                if (bd[x1 + xx][y1 + yy] == cl) {    //若是我方棋子
                    for (int j = -1; j <= d; j++) {
                        bd[ints[0] + j * xx][ints[1] + j * yy] = cl; //换颜色
                    }
                    number += (d + 1);
                    break;
                }
                if (bd[x1 + xx][y1 + yy] == 0) {     //若是空白
                    break;
                }
                if (bd[x1 + xx][y1 + yy] == cl * (-1)) {  //若是敌方棋子，继续前推
                    x1 += xx;
                    y1 += yy;
                    d += 1;
                }
            }
        }

        return number;
    }

    //测试人机（简单一阶局部最优）
    public static int[][] playerVsRobotTest(int[][] board) {
        int[][] validMoves = ChessBoardPanel.validMovesStatic(ChessPiece.WHITE, board);
        int row = 1;
        int col = 1;
        int weight = 1;
        int maxNub = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {


                if (validMoves[i][j] == 1) {
                    if ((i == 0 && j == 0) || (i == 7 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 7)) {
                        row = i;
                        col = j;
                        weight = 3;
                        break;
                    } else if (i == 0 || j == 0 || i == 7 || j == 7) {
                        row = i;
                        col = j;
                        weight = 2;
                    } else if (getNumberCanEat2(ChessPiece.BLACK, i, j, board) > maxNub && weight == 1) {
                        row = i;
                        col = j;
                        maxNub = getNumberCanEat(ChessPiece.BLACK, i, j, board);
                    }
                }
            }
            if (weight == 3)
                break;
        }

        GameFrame.controller.updateFileArray(row, col, Color.WHITE);
        return GameFrame.controller.gamePanel.fileArray;

    }


    //方法：获取可以吃掉的敌方子数
    public static int getNumberCanEat2(ChessPiece CP, int row, int col, int[][] board) {


        //ps:这里必须把board复制一下再搞，不然源头（fileArray）都会改变。why？？
        int[][] bd = new int[8][8];
        for (int ii = 0; ii < 8; ii++) {
            System.arraycopy(board[ii], 0, bd[ii], 0, 8);
        }

        int[][] ard = ChessBoardPanel.aroundMove(row, col);
        int number = 0;

        for (int[] ints : ard) {   //遍历周围每一个棋子
            int x1 = ints[0];
            int y1 = ints[1];
            int xx = x1 - row;
            int yy = y1 - col;
            int d = 0;

            int cl;
            if (CP == ChessPiece.WHITE)
                cl = -1;
            else cl = 1;

            if (bd[x1][y1] == cl || bd[x1][y1] == 0) //排除不是敌方的情况(白或空）
                continue;

            //若是敌方,递推
            while (x1 + xx <= 7 && x1 + xx >= 0 && y1 + yy <= 7 && y1 + yy >= 0) {
                if (bd[x1 + xx][y1 + yy] == cl) {    //若是我方棋子
                    for (int j = -1; j <= d; j++) {
                        bd[ints[0] + j * xx][ints[1] + j * yy] = cl; //换颜色
                    }
                    number += (d + 1);
                    break;
                }
                if (bd[x1 + xx][y1 + yy] == 0) {     //若是空白
                    break;
                }
                if (bd[x1 + xx][y1 + yy] == cl * (-1)) {  //若是敌方棋子，继续前推
                    x1 += xx;
                    y1 += yy;
                    d += 1;
                }
            }
        }

        return number;
    }
}
