package Snake;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    List<Point> snakePoints;
    int xDir, yDir;
    boolean isMoving, elongate;
    final int STARTSIZE = 10, STARTX = 160, STARTY = 160;
    static final int SNAKE_WIDTH = 10;

    public Snake() {
        snakePoints= new ArrayList<Point>();
        xDir = 1;
        yDir = 0;
        isMoving = false;
        elongate = false;
        snakePoints.add(new Point(STARTX, STARTY));
        for(int i = 1; i < STARTSIZE; i++) {
            snakePoints.add(new Point(STARTX - i * SNAKE_WIDTH, STARTY));
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        for(Point p : snakePoints) {
            g.fillRect(p.getX(), p.getY(), SNAKE_WIDTH, SNAKE_WIDTH);
        }
    }

    public int getXDir() {
        return xDir;
    }

    public int getYDir() {
        return yDir;
    }

    public void setXDir(int x) {
        xDir = x;
    }

    public void setYDir(int y) {
        yDir = y;
    }
    //Head Of Snake
    public int getX(){
        return snakePoints.get(0).getX();
    }

    public int getY() {
        return snakePoints.get(0).getY();
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setIsMoving(boolean b) {
        isMoving = b;
    }

    public void setElongate(boolean b) {
        elongate = b;
    }

    public void move() {
        if(!isMoving()) return;
        
        Point temp = snakePoints.get(0);
        Point last = snakePoints.get(snakePoints.size() - 1);
        Point newStart = new Point(temp.getX() + xDir * SNAKE_WIDTH, temp.getY() + yDir * SNAKE_WIDTH);
        for(int i = snakePoints.size() - 1; i >= 1; i--){
            snakePoints.set(i, snakePoints.get(i - 1));
        }
        snakePoints.set(0, newStart);
        if(elongate){
            snakePoints.add(last);
            elongate = false;
        }
    }

    /**
     * Returns true if snake has collided with itself, else false
     * */

    public boolean snakeCollision() {
        int x = this.getX();
        int y = this.getY();
        for(int i = 1; i < snakePoints.size(); i++){
            if(snakePoints.get(i).getX() == x && snakePoints.get(i).getY() == y){
                return true;
            }
        }
        return false;
    }
}
