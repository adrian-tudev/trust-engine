package main;

import Pieces.Piece;

import java.util.ArrayList;
import java.util.Random;

import static main.Board.pieceList;

public class AI {
    public int depth = 3;
    Board board;

    public final int pawnVal = 100;
    public final int knightVal = 300;
    public final int bishopVal = 301;
    public final int rookVal = 500;
    public final int queenVal = 900;
    public final int kingVal = 100000;

    public AI(Board board) {
        this.board = board;
    }

    public int search(int depth, int alpha, int beta) {
        if (depth == 0) {
            return evaluate();
        }

        ArrayList<Move> moves = getAllValidMoves();

        if (moves.isEmpty()) {
            Piece king = board.scanner.findKing(board.colorToMove);
            if (board.scanner.isInCheck(king.col, king.row, board.colorToMove)) {
                return -99999;
            } else {
                return 0;
            }
        }

        for (Move move : moves) {
            Move undoInfo = board.makeMove(move, true);
            int eval = -search(depth - 1, -beta, -alpha);
            board.undoMove(undoInfo);

            if (eval >= beta)
                return beta;

            alpha = Math.max(alpha, eval);
        }
        return alpha;
    }

    public int evaluate() {
        int whiteScore = countMaterial(0);
        int blackScore = countMaterial(1);

        int eval = blackScore - whiteScore;
        return eval;
    }

    public int countMaterial(int color) {
        int materialScore = 0;

        materialScore += board.countPieces(color, "Pawn") * pawnVal;
        materialScore += board.countPieces(color, "Knight") * knightVal;
        materialScore += board.countPieces(color, "Bishop") * bishopVal;
        materialScore += board.countPieces(color, "Queen") * queenVal;
        materialScore += board.countPieces(color, "Rook") * rookVal;
        materialScore += board.countPieces(color, "King") * kingVal;

        return materialScore;
    }

    public void makeAIMove() {
        System.out.println("AI makeAIMove() called - starting to think...");

        ArrayList<Move> validMoves = getAllValidMoves();

        if (validMoves.isEmpty()) {
            return;
        }

        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Move move : validMoves) {
            Move undoInfo = board.makeMove(move, true);
            int score = search(depth - 1, -Integer.MAX_VALUE, Integer.MAX_VALUE);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
            board.undoMove(undoInfo);

            System.out.println("Score: " + score);


        }

        System.out.println("AI finished thinking");

        if (bestMove != null) {
            board.makeMove(bestMove, false);
        } else {
            System.out.println("ERROR: No best move found!");
        }
    }

    private ArrayList<Move> getAllValidMoves() {
        ArrayList<Move> validMoves = new ArrayList<>();

        ArrayList<Piece> piecesCopy = new ArrayList<>(pieceList);

        // Get all pieces of the current player's color
        for (Piece piece : piecesCopy) {
            if (piece.color == board.colorToMove) {
                // Try all possible squares for each piece
                for (int row = 0; row < board.MAX_ROWS; row++) {
                    for (int col = 0; col < board.MAX_COLS; col++) {
                        Move move = new Move(board, piece, col, row);
                        move.capture = board.getPiece(col, row);

                        if (board.scanner.isValidMove(move)) {
                            validMoves.add(move);
                        }
                    }
                }
            }
        }
        return validMoves;
    }

}
