package BrickBreaker;
import java.awt.Color;
import java.util.Random;

public class Brick {
    int x, y, brickHealth;
    boolean broken = false;
    boolean unbreakable;
    static int width = 70, height = 30;
    static Color healthColour3 = Color.RED, healthColor2 = Color.orange, healthColor1 = Color.white, unbreakableColor = Color.DARK_GRAY;

    Brick(int x, int y, int brickHealth, boolean unbreakable){
        this.x = x;
        this.y = y;
        this.brickHealth = brickHealth;
        this.unbreakable = unbreakable;
    }

    public static void initBricks(int numOfUnbreakableBricks, int[] unbreakableBricksIndex, int brickRows, int brickColumns, Brick[] bricks, int level){
        Random r = new Random();
        for(int i = 0; i < numOfUnbreakableBricks; i++){
            unbreakableBricksIndex[i] = r.nextInt(brickRows * brickColumns);
        }
        for(int i = 0; i < brickRows; i++){
            for(int j = 0; j < brickColumns; j++){
                boolean unbreakable = false;
                for(int k = 0; k < numOfUnbreakableBricks; k++){
                    if(unbreakableBricksIndex[k] == i * brickColumns + j){
                        unbreakable = true;
                    }
                }
                bricks[i * brickColumns + j] = new Brick(110 + (Brick.width + 2) * j, 30 + (Brick.height + 2) * i, r.nextInt(level > 3 ? 3 : level) + 1, unbreakable);
            }
        }
    }

    public void hitBrick(){
        if(!unbreakable){
            brickHealth --;
            if(brickHealth == 0){
                broken = true;
            }
        }
    }

    public static boolean checkAllBricksDestroyed(Brick[] bricks){
        boolean allBricksDestroyed = true;

        for(int i = 0; i < bricks.length; i++){
            if(!bricks[i].unbreakable && !bricks[i].broken){
                allBricksDestroyed = false;
            }
        }

        return allBricksDestroyed;
    }
}
