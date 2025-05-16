package main;

import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public static final int SQUARE_SIZE = 60;
    private static final int MAX_ROWS = 8;
    private static final int MAX_COLS = 8;

    static ArrayList<Piece> pieceList = new ArrayList<>();
    public Piece selectedPiece;
    public int colorToMove = 0;

    public Board() {
        addPieces();

        Input input = new Input(this);
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
    }

    public Piece getPiece(int col, int row) {
       for (Piece piece : pieceList) {
           if (piece.col == col && piece.row == row) {
               return piece;
           }
       } return null;
    }

    public void capture(Move move) {
        pieceList.remove(move.capture);
    }

    public void makeMove(Move move) {
        if (move.piece.name.equals("Pawn"))
            ((Pawn) move.piece).isFirstMove = false;

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.x = move.newCol * SQUARE_SIZE;
        move.piece.y = move.newRow * SQUARE_SIZE;
        capture(move);
        colorToMove = colorToMove ^ 1;
    }

    public boolean checkTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        else {
            return (p1.color == p2.color);
        }
    }

    public boolean isValidMove(Move move) {
        if (checkTeam(move.piece, move.capture))
            return false;

        else if (!(move.piece.color == colorToMove))
            return false;

        else if (!(move.piece.isValidPieceMove(move.newCol, move.newRow)))
            return false;

        else if (move.piece.checkForCollision(move.newCol, move.newRow))
            return false;

        return true;
    }

    public void addPieces() {
        // Black pieces
        // Rooks
        pieceList.add(new Rook(this, 0, 0, 1));
        pieceList.add(new Rook(this, 7, 0, 1));

        // Knights
        pieceList.add(new Knight(this, 1, 0, 1));
        pieceList.add(new Knight(this, 6, 0, 1));

        // Bishops
        pieceList.add(new Bishop(this, 2, 0, 1));
        pieceList.add(new Bishop(this, 5, 0, 1));

        // Queen & King
        pieceList.add(new Queen(this, 3, 0, 1));
        pieceList.add(new King(this, 4, 0, 1));

        // Black Pawns
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 1, 1));
        }

        // White pieces
        // Rooks
        pieceList.add(new Rook(this, 0, 7, 0));
        pieceList.add(new Rook(this, 7, 7, 0));

        // Knights
        pieceList.add(new Knight(this, 1, 7, 0));
        pieceList.add(new Knight(this, 6, 7, 0));

        // Bishops
        pieceList.add(new Bishop(this, 2, 7, 0));
        pieceList.add(new Bishop(this, 5, 7, 0));

        // Queen & King
        pieceList.add(new Queen(this, 3, 7, 0));
        pieceList.add(new King(this, 4, 7, 0));

        // White Pawns
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 6, 0));
        }
    };

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                if ((i + j) % 2 == 0) {
                    graphics.setColor(Color.WHITE);
                } else {
                    graphics.setColor(new Color(192, 164, 132));
                }
                graphics.fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }

        // To check for valid moves
        if (selectedPiece != null)
            for (int i = 0; i < MAX_ROWS; i++) {
                for (int j = 0; j < MAX_COLS; j++) {
                    if (isValidMove(new Move(this, selectedPiece, j, i))) {
                        graphics.setColor(new Color(66, 127, 46, 166));
                        graphics.fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    }
                }
            }

        for (Piece piece : pieceList) {
            piece.paint(graphics);
        }


    }

    public void display() {
            JFrame frame = new JFrame("Chess Board");
            frame.add(this);
            frame.setLocation(600, 200);
            frame.setSize(MAX_COLS * SQUARE_SIZE + 16, MAX_ROWS * SQUARE_SIZE + 39);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
}
