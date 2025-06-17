package main;

import Pieces.Piece;

public class Move {
    int col, row;
    int newCol, newRow;
    public int oldColorToMove;

    Piece piece;
    Piece capture;

    public Move(Board board, Piece piece, int newCol, int newRow) {
        col = piece.col;
        row = piece.row;
        this.newCol = newCol;
        this.newRow = newRow;

        this.piece = piece;
        this.capture = board.getPiece(newCol, newRow);
    }


}
