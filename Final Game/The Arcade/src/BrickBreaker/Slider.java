package BrickBreaker;

public class Slider {
    private int x, velocity;
    public final int width, height;

    public Slider(int x, int velocity, int width, int height) {
        this.x = x;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
    }

    public void updateVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void updatePositions(int xBoundStart, int xBoundEnd) {
        x += velocity;
        if (x < xBoundStart) x = xBoundStart;
        if (x + width > xBoundEnd) x = xBoundEnd - width;
    }

    public int getX() {
        return x;
    }
}