package Snake;

import java.awt.*;
import java.util.Random;

public class Apple {
    private static final Random RANDOM = new Random();
    private int x, y, score;

    public Apple(Snake s) {
        change();
    }

    public void change() {
        x = (Snake.SNAKE_WIDTH) * RANDOM.nextInt(SnakeGameFrame.SCREEN_WIDTH / Snake.SNAKE_WIDTH);
        y = (Snake.SNAKE_WIDTH) * RANDOM.nextInt(SnakeGameFrame.SCREEN_HEIGHT / Snake.SNAKE_WIDTH);
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, Snake.SNAKE_WIDTH, Snake.SNAKE_WIDTH);
    }

    public static double lerp(double val, double lb, double ub, double lv, double uv) {
        return lv + (val - lb) * (uv - lv) / (ub - lb);
    }

    public boolean snakeCollision() {
        Snake snake = SnakeGameFrame.snake;
        if (snake.getX() == x && snake.getY() == y) {
            handleCollision(snake);
            return true;
        }
        return false;
    }

    private void handleCollision(Snake snake) {
        change();
        score++;
        updateFPS();
        snake.setElongate(true);
        System.out.println("Elongate");
    }

    private void updateFPS() {
        SnakeGameFrame.FPS = (int) lerp(score, 0, 20, 25, 40);
    }
}