import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //Snake
    Tile shakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //game logic
    Timer gameloop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        shakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10,10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameloop = new Timer(100, this);
        gameloop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //Food
        g.setColor(Color.RED);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        //Snake Head
        g.setColor(Color.GREEN);
        g.fill3DRect(shakeHead.x * tileSize,shakeHead.y * tileSize, tileSize, tileSize, true);

        //Snake body
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.RED);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        //eat food
        if (collision(shakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake Body
        for (int i = snakeBody.size() -1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i==0){
                snakePart.x = shakeHead.x;
                snakePart.y = shakeHead.y;
            }
            else{
               Tile prevSnakePart = snakeBody.get(i-1);
               snakePart.x = prevSnakePart.x;
               snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Head
        shakeHead.x += velocityX;
        shakeHead.y += velocityY;

        //game over conditions
        for(int i=0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            if(collision(shakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(shakeHead.x*tileSize < 0 || shakeHead.x*tileSize > boardWidth ||
        shakeHead.y*tileSize < 0 || shakeHead.y*tileSize > boardHeight){
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameloop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W && velocityY != 1){
            velocityX = 0 ;
            velocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_S && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_D && velocityX != 1) {
            velocityX = 1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_A && velocityX != -1) {
            velocityX = -1;
            velocityY = 0;
        }

    }


    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
