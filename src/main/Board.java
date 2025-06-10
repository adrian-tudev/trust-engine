package main;

import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    public static final int SQUARE_SIZE = 60;
    public static final int MAX_ROWS = 8;
    public static final int MAX_COLS = 8;

    public String FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    public String FENTest = "r3k2r/1P6/8/8/8/8/8/R3K2R";

    public static ArrayList<Piece> pieceList = new ArrayList<>();
    public Piece selectedPiece;
    public int colorToMove = 0;
    public Scanner scanner = new Scanner(this);


    public Board() {
        addPieces(FENTest);

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


    public void promotion(Move move) {
        Piece pawn = getPiece(move.col,move.row);
        pieceList.remove(pawn);

        // simple popup choice
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose promotion piece:",
                "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        Piece promotedPiece = createPromotedPiece(choice, move.newCol, move.newRow, move.piece.color);
        pieceList.add(promotedPiece);
    }


    public Piece createPromotedPiece(int choice, int col, int row, int color) {
        return switch (choice) {
            case 1 -> new Rook(this, col, row, color);
            case 2 -> new Bishop(this, col, row, color);
            case 3 -> new Knight(this, col, row, color);
            default -> new Queen(this, col, row, color);
        };
    }


    public void castling(Move move) {
        if (move.newCol == 2) {
            Piece rook = getPiece(0,move.row);
            Move rookMove = new Move(this, rook, 3,move.row);
            makeMove(rookMove);
        }
        else {
            Piece rook = getPiece(7, move.row);
            Move rookMove = new Move(this, rook, 5, move.row);
            makeMove(rookMove);
        }
        colorToMove ^= 1;
    }


    public void enPassant(Move move) {
        int squareDiff;
        if (move.piece.color == 0)
            squareDiff = 1;
        else
            squareDiff = -1;

        Piece pawn = getPiece(move.newCol, move.newRow + squareDiff);

        pieceList.remove(pawn);
    }


    public void firstMove(Piece piece) {
        piece.isFirstMove = false;
    }


    public void makeMove(Move move) {
        handleFirstMove(move.piece);

        handleSpecialMoves(move);

        executeMove(move);

        capture(move);

        colorToMove = colorToMove ^ 1;
    }


    public void handleFirstMove(Piece piece) {
        if (piece.name.equals("Pawn") || piece.name.equals("King") || piece.name.equals("Rook"))
            firstMove(piece);
    }


    public void handleSpecialMoves(Move move) {
        // Castling
        if (move.piece.name.equals("King") && Math.abs(move.piece.col - move.newCol) == 2) {
            castling(move);
            return;
        }

        // Setup en passant
        if (move.piece.name.equals("Pawn") && Math.abs(move.piece.row - move.newRow) == 2)
            scanner.enPassantPossible(move.piece);
        else
            scanner.enPassantEnable = false;

        if (isEnPassant(move))
            enPassant(move);

        if (isPawnPromotion(move)) {
            promotion(move);
        }
    }


    public boolean isEnPassant(Move move) {
        return (move.piece.name.equals("Pawn") && move.newCol ==
                scanner.enPassantCol && Math.abs(move.piece.col - move.newCol) == 1);
    }


    public boolean isPawnPromotion(Move move) {
        return move.piece.name.equals("Pawn") &&
                (move.newRow == 0 || move.newRow == 7);
    }


    public void executeMove(Move move) {
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.x = move.newCol * SQUARE_SIZE;
        move.piece.y = move.newRow * SQUARE_SIZE;
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
        return scanner.isValidMove(move);
    }


    // load position from FEN string
    public void addPieces(String FEN) {
        pieceList.clear();
        int row = 0;
        int col = 0;
        int color;
        for (int i = 0; i < FEN.length(); i++) {
            char ch = FEN.charAt(i);
            if (ch == '/') {
                row++;
                col = 0;
            }
            else if (Character.isDigit(ch)) {
                col += Character.getNumericValue(ch);
            }
            else {
                if (Character.isUpperCase(ch)) {
                    color = 0;
                }
                else color = 1;
                char piece = Character.toLowerCase(ch);

                switch (piece) {
                    case 'r':
                        pieceList.add(new Rook(this,col,row,color));
                        break;
                    case 'n':
                        pieceList.add(new Knight(this,col,row,color));
                        break;
                    case 'b':
                        pieceList.add(new Bishop(this,col,row,color));
                        break;
                    case 'q':
                        pieceList.add(new Queen(this,col,row,color));
                        break;
                    case 'k':
                        pieceList.add(new King(this,col,row,color));
                        break;
                    case 'p':
                        pieceList.add(new Pawn(this,col,row,color));
                        break;
                }
                col++;
            }
        }
    }


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

        if (scanner.scanCheckMate(colorToMove)) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.BOLD, 50));
            graphics.drawString(((colorToMove == 0) ? "Black" : "White" ) + " wins!", 100, MAX_ROWS * SQUARE_SIZE / 2);
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
