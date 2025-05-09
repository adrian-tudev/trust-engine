package main;

import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public static final int SQUARE_SIZE = 60;
    private static final int MAX_ROWS = 8;
    private static final int MAX_COLS = 8;

    ArrayList<Piece> pieceList = new ArrayList<>();

    public Board() {
        addPieces();
    }

    public void addPieces() {
        pieceList.add(new Knight(this,1,0,1));
        pieceList.add(new Knight(this,1,7,0));
        pieceList.add(new Knight(this,6,0,1));
        pieceList.add(new Knight(this,6,7,0));
        pieceList.add(new Bishop(this, 2,0,1));
        // Black pieces (top of the board)
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

        // White pieces (bottom of the board)
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
        for (Piece piece : pieceList) {
            piece.paint(graphics);
        }
    }

    public void display() {
            JFrame frame = new JFrame("Chess Board");
            frame.add(this);
            frame.setSize(MAX_COLS * SQUARE_SIZE + 16, MAX_ROWS * SQUARE_SIZE + 39);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
}
