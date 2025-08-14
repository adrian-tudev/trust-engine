package engine;

public class BitBoard {
    public static long setBit(long bitboard, int index) {
        return bitboard | (1L << index);
    }
    public static long clearBit(long bitboard, int index) {
        return bitboard & ~(1L << index);
    }
    public static boolean isOnBoard(int dest) {
        int row = getRow(dest);
        int col = getCol(dest);
        return (row >= 0 && col >= 0) && (row < 8 && col < 8);
    }
    public static int getRow(int index) {
        return index >> 3;
    }
    public static int getCol(int index) {
        return index & 7;
    }
    public static boolean isValidDirection(int start, int destination, int direction) {
        int startCol = getCol(start);
        int destCol = getCol(destination);

        // Check correct change in rows for all directions
        return switch (Math.abs(direction)) {
            // Horizontal should not change row
            case 1 -> getRow(start) == getRow(destination);
            // Vertical should not change col
            case 8 -> startCol == destCol;
            // Diagonal should differ 1
            case 7, 9 -> Math.abs(startCol - destCol) == 1;
            default -> false;
        };
    }

    public static int SquareToIndex(int row, int col) {
        return 8 * row + col;
    }

    public static String indexToSquare(int index) {
        int col = index % 8;
        int row = (index / 8) + 1;
        char fileChar = (char) ('a' + col);
        return "" + fileChar + row;
    }

    public static String bitboardSquares(long bitboard) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            if (((bitboard >>> i) & 1L) != 0L) {
                if (sb.length() > 0) sb.append(' ');
                sb.append(indexToSquare(i));
            }
        }
        return sb.toString();
    }
}
