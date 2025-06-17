package main;

import Pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static main.Board.*;


public class Input extends MouseAdapter {

    Board board;

   public boolean pressed;
   public int x, y;

   public Input(Board board) {
       this.board = board;
   }

   @Override
   public void mousePressed(MouseEvent e) {
       int col = e.getX() / SQUARE_SIZE;
       int row = e.getY() / SQUARE_SIZE;

       Piece piece = board.getPiece(col, row);
       if (piece != null) {
           board.selectedPiece = piece;
       }
    board.repaint();
   }

   @Override
    public void mouseReleased(MouseEvent e) {
      if (board.selectedPiece != null) {
          int col = e.getX() / SQUARE_SIZE;
          int row = e.getY() / SQUARE_SIZE;
          Move move = new Move(board, board.selectedPiece, col, row);

          if (board.isValidMove(move)) {
              board.makeMove(move, false);
          }
          else {
              board.selectedPiece.x = board.selectedPiece.col * SQUARE_SIZE;
              board.selectedPiece.y = board.selectedPiece.row * SQUARE_SIZE;
          }
      }
    board.repaint();
   }

   @Override
    public void mouseDragged(MouseEvent e) {
       if (board.selectedPiece != null) {
           board.selectedPiece.x = e.getX() - SQUARE_SIZE / 2;
           board.selectedPiece.y = e.getY() - SQUARE_SIZE / 2;
           board.repaint();
       }

   }

   @Override
    public void mouseMoved(MouseEvent e) {
       x = e.getX();
       y = e.getY();
   }



}
