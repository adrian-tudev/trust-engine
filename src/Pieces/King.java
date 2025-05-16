package Pieces;

import main.Board;

public class King extends Piece {
    public King (Board board, int col, int row, int color) {
        super(board);
        this.col = col;
        this.row = row;
        this.x = getX(col);
        this.y = getY(row);
        this.color = color;
        this.name = "Knight";


        if (color == 0) {
            image = getImage("/resources/Chess_klt60.png");
        }
        else {
            image = getImage("/resources/Chess_kdt60.png");
        }

    }
    @Override
    public boolean isValidPieceMove(int newCol, int newRow) {
        int diffCol = Math.abs(newCol - this.col);
        int diffRow = Math.abs(newRow - this.row);
        return (diffRow == 1 && diffCol == 1) || (diffRow == 0 && diffCol == 1)
                || (diffRow == 1 && diffCol == 0);
    }
}
