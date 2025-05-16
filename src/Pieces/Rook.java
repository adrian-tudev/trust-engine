package Pieces;

import main.Board;

public class Rook extends Piece {
    public Rook (Board board, int col, int row, int color) {
        super(board);
        this.col = col;
        this.row = row;
        this.x = getX(col);
        this.y = getY(row);
        this.color = color;
        this.name = "Knight";


        if (color == 0) {
            image = getImage("/resources/Chess_rlt60.png");
        }
        else {
            image = getImage("/resources/Chess_rdt60.png");
        }
    }

    @Override
    public boolean isValidPieceMove(int newCol, int newRow) {
        if (newCol == this.col || newRow == this.row)
            return true;
        return false;
    }

    @Override
    public boolean checkForCollision(int newCol, int newRow) {
        // check for up
        if (newRow < this.row) {
            for (int i = this.row - 1; i > newRow; i--) {
                if (board.getPiece(this.col, i) != null)
                    return true;
            }
        }
        // down
        if (newRow > this.row) {
            for (int i = this.row + 1; i < newRow; i++) {
                if (board.getPiece(this.col, i) != null)
                    return true;
            }
        }
        // left
        if (newCol < this.col) {
            for (int i = this.col - 1; i > newCol; i--) {
                if (board.getPiece(i, this.row) != null)
                    return true;
            }
        }
        if (newCol > this.col) {
            for (int i = this.col + 1; i < newCol; i++) {
                if (board.getPiece(i, this.row) != null)
                    return true;
            }
        }
        return false;
    }
}
