import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private static final int SQUARE_SIZE = 60;
    private static final int MAX_ROWS = 8;
    private static final int MAX_COLS = 8;

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < MAX_COLS; j++) {
                if ((i + j) % 2 == 0) {
                    graphics.setColor(Color.WHITE);
                }
                else {
                    graphics.setColor(new Color(192, 164, 132));
                }
                graphics.fillRect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }

    }

    public void display() {
        JFrame frame = new JFrame("Chess Board");
        Board chessBoard = new Board();
        frame.add(chessBoard);
        frame.setSize(MAX_COLS * SQUARE_SIZE + 16, MAX_ROWS * SQUARE_SIZE + 39);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
