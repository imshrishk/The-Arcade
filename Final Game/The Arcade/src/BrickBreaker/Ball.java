package BrickBreaker;

import java.util.Random;

public class Ball {
    private int x, y, velocityX, velocityY;
    private boolean out = false;
    private final int width, height;
    private static final int INITIAL_VELOCITY_X = 3;
    private static final int INITIAL_VELOCITY_Y = 3;
    private static final int INITIAL_X = 120;
    private static final int INITIAL_Y = 350;
    private static final Random RANDOM = new Random();

    public Ball(int x, int y, int velocityX, int velocityY, int width, int height) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.width = width;
        this.height = height;
    }

    public static Ball initBall() {
        return new Ball(INITIAL_X, INITIAL_Y, INITIAL_VELOCITY_X, INITIAL_VELOCITY_Y, 20, 20);
    }

    public void setPositions(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityX = INITIAL_VELOCITY_X;
        this.velocityY = INITIAL_VELOCITY_Y;
    }

    public void updatePositions() {
        x += velocityX;
        y += velocityY;
    }

    public void updateVelocities(int changeInX, int changeInY) {
        velocityX = (int) (Math.signum(velocityX) * (Math.abs(velocityX) + changeInX));
        velocityY = (int) (Math.signum(velocityY) * (Math.abs(velocityY) + changeInY));
    }

    public void checkCollision(int xboundStart, int xboundEnd, int yboundStart, int yboundEnd, int sliderX) {
        if (x < xboundStart) {
            x = xboundStart;
            velocityX *= -1;
        } else if (x > xboundEnd) {
            x = xboundEnd;
            velocityX *= -1;
        }

        if (y < yboundStart) {
            y = yboundStart;
            velocityY *= -1;
        } else if (y > yboundEnd) {
            out = true;
            y = yboundEnd;
        }

        if (x > sliderX && x < sliderX + 100 && y >= 500 && y <= 500 + velocityY) {
            velocityY *= -1;
            y = 500;
        }
    }

    public boolean checkForCollisionWithBrick(Brick[] bricks, int level) {
        boolean collided = false;

        for (Brick brick : bricks) {
            if (!brick.broken && isCollidingWithBrick(brick)) {
                collided = handleBrickCollision(brick, level);
            }
        }

        return collided;
    }

    private boolean isCollidingWithBrick(Brick brick) {
        return brick.x - Brick.width / 2 <= x && x <= brick.x + Brick.width / 2 &&
                brick.y - Brick.height / 2 <= y && y <= brick.y + Brick.height / 2;
    }

    private boolean handleBrickCollision(Brick brick, int level) {
        boolean collided = !brick.unbreakable;
        brick.hitBrick();

        if (brick.brickHealth != 0) {
            velocityX = generateRandomVelocity(velocityX);
            velocityY = generateRandomVelocity(velocityY);

            double speedFactor = getSpeedFactor(level);
            double magnitude = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
            velocityX = (int) Math.round(velocityX * speedFactor / magnitude);
            velocityY = (int) Math.round(velocityY * speedFactor / magnitude);

            System.out.println(velocityX + " " + velocityY);
        }

        return collided;
    }

    private int generateRandomVelocity(int currentVelocity) {
        int absVelocity = Math.abs(currentVelocity);
        return (int) (-Math.signum(currentVelocity) * (RANDOM.nextInt(Math.max(absVelocity, 1)) + 3));
    }

    private double getSpeedFactor(int level) {
        switch (level) {
            case 1:
                return 4.2;
            case 2:
                return 5.2;
            case 3:
                return 6.2;
            default:
                return 7.0;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean outOfBounds() {
        return out;
    }
}