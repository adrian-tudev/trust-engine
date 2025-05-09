package Pieces;

import main.Board;

public class Queen extends Piece {
    public Queen (Board board, int col, int row, int color) {
        super(board);
        this.col = col;
        this.row = row;
        this.x = getX(col);
        this.y = getY(row);
        this.color = color;
        this.name = "Knight";


        if (color == 0) {
            image = getImage("/resources/Chess_qlt60.png");
        }
        else {
            image = getImage("/resources/Chess_qdt60.png");
        }
    }
}
