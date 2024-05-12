package Snake;

import java.awt.*;

public class Point {
    private int x, y;
    private static final int SIZE = 10; // Size of the point on the screen
    private static final Color DEFAULT_COLOR = Color.WHITE;

    public Point() {
        this(0, 0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        draw(g, DEFAULT_COLOR);
    }

    public void draw(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
    }

    public void drawWithGradient(Graphics g, Color startColor, Color endColor, float ratio) {
        g.setColor(interpolateColor(startColor, endColor, ratio));
        g.fillRect(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
    }

    private Color interpolateColor(Color start, Color end, float ratio) {
        int red = (int) (start.getRed() * (1 - ratio) + end.getRed() * ratio);
        int green = (int) (start.getGreen() * (1 - ratio) + end.getGreen() * ratio);
        int blue = (int) (start.getBlue() * (1 - ratio) + end.getBlue() * ratio);
        return new Color(red, green, blue);
    }
}