package Pieces;

import main.Board;

public class Knight extends Piece {
    public Knight (Board board, int col, int row, int color) {
        super(board);
        this.col = col;
        this.row = row;
        this.x = getX(col);
        this.y = getY(row);
        this.color = color;
        this.name = "Knight";


        if (color == 0) {
            image = getImage("/resources/Chess_nlt60.png");
        }
        else {
            image = getImage("/resources/Chess_ndt60.png");
        }
    }
}
