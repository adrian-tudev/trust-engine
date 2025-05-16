package Pieces;

import main.Board;

public class Pawn extends Piece {
    public boolean isFirstMove = true;
    public int squareDiff = 1;

    public Pawn (Board board, int col, int row, int color) {
        super(board);
        this.col = col;
        this.row = row;
        this.x = getX(col);
        this.y = getY(row);
        this.color = color;
        this.name = "Pawn";

        if (color == 0) {
            image = getImage("/resources/Chess_plt60.png");
        }
        else {
            image = getImage("/resources/Chess_pdt60.png");
        }
    }

    @Override
    public boolean isValidPieceMove(int newCol, int newRow) {
        if (this.color == 0)
            squareDiff = -1;
        // Push pawn 1 square
        if (newRow == row + squareDiff && newCol == this.col) {
            return true;
        }
        // Push pawn 2 squares if first move
        if (this.isFirstMove && newRow == row + squareDiff * 2 && newCol == this.col) {
            return true;
        }

        return false;
    }

    @Override
    public boolean checkForCollision(int newCol, int newRow) {
        if (board.getPiece(newCol, newRow) != null)
            return true;
        return false;
    }
}
