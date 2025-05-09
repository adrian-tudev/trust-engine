package Pieces;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.Board;

import javax.imageio.ImageIO;

public class Piece {
    public BufferedImage image;
    public int color;
    public int x, y;
    public int row, col;
    public String name;

    Board board;

    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        }
        catch(IOException e) {
           e.printStackTrace();
        }
        return image;
    }

    public Piece(Board board) {
        this.board = board;
    }

    public int getX(int col) {
       return col * Board.SQUARE_SIZE;
    }

    public int getY(int row) {
        return row * Board.SQUARE_SIZE;
    }

    public void paint(Graphics graphics) {
       graphics.drawImage(image, x, y, null);
    }
}
