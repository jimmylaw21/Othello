package model;

import java.awt.*;

/**
 * 棋子属性
 */
public enum ChessPiece {
    BLACK(Color.BLACK),
    WHITE(Color.WHITE),
    HIGHLIGHT(new Color(194, 111, 87)),
    FIREBALL(new Color(150, 50, 0)),
    FIREBALL2(new Color(200, 50, 0)),
    FIREBALL3(new Color(250, 50, 0)),
    STARFALL(new Color(150, 0, 150));

    private final Color color;

    ChessPiece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Color getOppositeColor(){
        if(this.color.equals(Color.BLACK)){
            return Color.WHITE;
        }
        else
            return Color.BLACK;
    }

    public ChessPiece getOppositeChessPiece(){
        if(this.color.equals(Color.BLACK)){
            return ChessPiece.WHITE;
        }
        else
            return ChessPiece.BLACK;
    }

    public ChessPiece transColor(){
        if(this.getColor() == Color.BLACK)
            return WHITE;
        else if(this.getColor() == Color.WHITE)
            return BLACK;
        else return null;
    }
}