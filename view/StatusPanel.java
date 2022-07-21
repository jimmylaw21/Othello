package view;

import components.ChessGridComponent;
import controller.GameController;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;

/**
 * 状态栏
 */

public class StatusPanel extends JPanel {
    private JLabel playerLabel;
    private JLabel scoreLabel;
    private JLabel heroLabel;
    private JLabel enemyLabel;
    private JLabel timeLabel;

    public StatusPanel(int width, int height) {
        this.setSize((int) (width), (int) (height));
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);

        this.playerLabel = new JLabel();
        this.playerLabel.setLocation(0, 10);
        this.playerLabel.setSize((int) (width * 0.4), (int) (height));
        this.playerLabel.setFont(new Font("Calibri", Font.ITALIC, 30));
        this.setPlayerText(ChessPiece.BLACK.name());
        add(playerLabel);

        this.scoreLabel = new JLabel();
        this.scoreLabel.setLocation((int) (width * 0.6), 10);
        this.scoreLabel.setSize((int) (width * 0.5), height);
        this.scoreLabel.setFont(new Font("Calibri", Font.ITALIC, 25));
        this.setScoreText(2, 2);
        add(scoreLabel);

//        this.timeLabel = new JLabel();
//        this.timeLabel.setLocation((int) (width * 0.4), 10);
//        this.timeLabel.setSize((int) (width * 0.4), height);
//        this.timeLabel.setFont(new Font("Calibri", Font.ITALIC, 40));
//        this.setTimeLabelText(0);
//        add(timeLabel);

        if (GameFrame.page == 5) {
            this.heroLabel = new JLabel();
            this.heroLabel.setLocation((int) (width * 0.3), -10);
            this.heroLabel.setSize((int) (width * 0.4), height);
            this.heroLabel.setFont(new Font("1", Font.ITALIC, 20));
            this.setheroLabelText(0, 0, false);
            add(heroLabel);

            this.enemyLabel = new JLabel();
            this.enemyLabel.setLocation((int) (width * 0.3), 20);
            this.enemyLabel.setSize((int) (width * 0.4), height);
            this.enemyLabel.setFont(new Font("2", Font.ITALIC, 20));
            this.setenemyLabelText(0, 0,0,0, false);
            add(enemyLabel);
        }
    }

    public void setScoreText(int black, int white) {
        this.scoreLabel.setText(String.format("BLACK: %d\tWHITE: %d", black, white));
    }

    public void setPlayerText(String playerText) {
        this.playerLabel.setText(playerText + "'s turn");
    }

    public void setheroLabelText(int heroChoose, int time, boolean isPlayed) {
        switch (heroChoose) {
            case 1:
                if (isPlayed) {
                    this.heroLabel.setText(String.format("The World!!!"));
                } else if (!ChessGridComponent.isTheWorldOK) {
                    this.heroLabel.setText(String.format("colddown:\n %d", time));
                } else if (ChessGridComponent.round < 4) {
                    this.heroLabel.setText(String.format("Not prepared"));
                } else {
                    this.heroLabel.setText(String.format("Ready"));
                }
                break;
            default:
                if (isPlayed) {
                    this.heroLabel.setText(String.format("Fire Ball!!!"));
                } else if (!ChessGridComponent.isFireBallOK) {
                    this.heroLabel.setText(String.format("colddown:\n %d", time));
                } else if (ChessGridComponent.round < 6) {
                    this.heroLabel.setText(String.format("Not prepared"));
                } else {
                    this.heroLabel.setText(String.format("Ready"));
                }
                break;
        }
    }

    public void setenemyLabelText(int enemyChoose, int clockTime, int X, int Y, boolean isStarFall) {
        switch (enemyChoose) {
            case 1:
                if (isStarFall) {
                    this.enemyLabel.setText(String.format("starFallPiece:\n (%d,%d)", X, Y));
                } else {
                    this.enemyLabel.setText(String.format("StarFallNotUsed"));
                }
                break;
            default:
                this.enemyLabel.setText(String.format(String.valueOf(clockTime)));
                break;

        }
    }
    public void setTimeLabelText(int clockTime){
        this.timeLabel.setText(String.format(String.valueOf(clockTime)));
    }
}
