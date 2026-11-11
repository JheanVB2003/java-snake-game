package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    static final int CELL_SIZE = 25;
    static final int COLUMN = 24;
    static final int LINES = 24;
    static final int WIDTH = COLUMN * CELL_SIZE;
    static final int HEIGHT = LINES * CELL_SIZE;
    static final int SPEED_MS = 135;

    private ArrayList<Point> snake;
    private Point food;
    private int dx, dy;
    private int nextDx, nextDy;
    private int point;
    private boolean endGame;

    private final Timer timer;
    private final Random random = new Random();

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(20,20,30));
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(SPEED_MS, this);
        newGame();
    }

    public void newGame(){
        snake = new ArrayList<>();
        snake.add(new Point(COLUMN / 2, LINES / 2));
        dx = 1; dy = 0;
        nextDx = dx; nextDy = dy;
        point = 0;
        endGame = false;

        generateFood();
        timer.start();
    }

    public void generateFood() {
        Point p;
        do {
            p = new Point(random.nextInt(COLUMN), random.nextInt(LINES));
        } while (snake.contains(p));
        food = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!endGame){
            move();
        } repaint();
    }

    private void move(){
        dx = nextDx;
        dy = nextDy;

        Point head = snake.get(0);
        Point newHead = new Point(head.x + dx, head.y + dy);

        if (newHead.x < 0 || newHead.x >= COLUMN || newHead.y < 0 || newHead.y >= LINES){
            end();
            return;
        }

        if (snake.contains(newHead)){
            end();
            return;
        }

        snake.add(0, newHead);

        if (newHead.equals(food)){
            point++;
            generateFood();
        }else {
            snake.remove(snake.size() - 1);
        }
    }

    private void end(){
        endGame = true;
        timer.stop();
    }

    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(230,70,70));
        g2.fillOval(food.x * CELL_SIZE + 3, food.y * CELL_SIZE + 3, CELL_SIZE - 6, CELL_SIZE - 6);

        for(int i = 0; i < snake.size(); i++){
            Point p = snake.get(i);
            g2.setColor(i == 0 ? new Color(120,255,120) : new Color(50,190,80));
            g2.fillRoundRect(p.x * CELL_SIZE + 1, p.y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE -2,8,8);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        g2.drawString("Points:" + point, 10, 22);

        if (endGame){
            g2.setFont(new Font("SansSerif", Font.BOLD, 36));
            centerDrawing(g2, "End Game", HEIGHT / 2 - 10);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
            centerDrawing(g2, "Press SPACE for new game", HEIGHT / 2 + 25);
        }
    }

    private void centerDrawing(Graphics2D g2, String text, int y){
        int width = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, (WIDTH - width) / 2, y);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP && dy != 1){nextDx = 0; nextDy = -1;}
        else if (key == KeyEvent.VK_DOWN && dy != -1){nextDx = 0; nextDy = 1;}
        else if (key == KeyEvent.VK_LEFT && dx != 1){nextDx = -1; nextDy = 0;}
        else if (key == KeyEvent.VK_RIGHT && dx != -1){nextDx = 1; nextDy = 0;}
        else if (key == KeyEvent.VK_SPACE && endGame) {newGame();}

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
