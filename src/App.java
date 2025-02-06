import javax.swing.*;
import java.awt.event.KeyEvent;

public class App {
   public static int boardWidth = 600;
   public static int boardHeight = boardWidth;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardWidth,boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }

}