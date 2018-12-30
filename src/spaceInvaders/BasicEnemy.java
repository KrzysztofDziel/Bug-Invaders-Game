package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BasicEnemy {

    private Point cord;
    private Rectangle hitBox;
    private boolean shoot;
    private int reload;
    private BufferedImage enemy1;
    private int frame = 0;
    private double direction = 1.0D;

    public BasicEnemy(int x, int y) {
        cord = new Point(x, y);
        hitBox = new Rectangle(cord.x, cord.y, 50, 30);
        reload = 0;

        try {
            enemy1 = javax.imageio.ImageIO.read(BasicEnemy.class.getResource("/spaceInvaders/resources/enemy1.png"));

        } catch (IOException e) {

            enemy1 = new BufferedImage(1100, 83, 2);
        }
    }

    public int getX() {
        return cord.x;
    }

    public int getY() {
        return cord.y;
    }

    public void SetX(int x) {
        cord.x = x;
    }

    public void SetY(int y) {
        cord.y = y;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setShot(boolean shoot) {
        this.shoot = shoot;
    }

    public boolean getShot() {
        return shoot;
    }

    public void setReload(int reload) {
        this.reload = reload;
    }

    public int getReload() {
        return reload;
    }

    public void setDirection(double dir) {
        if ( dir == 1 ){
        direction = dir;
        }
        else {
        direction = -1.0D;
        }
    }

    private BufferedImage returnFrame() {

        BufferedImage keyFrame = new BufferedImage(110, 83, 2);
        AffineTransform transform = AffineTransform.getScaleInstance(direction, 1.0D);
        Graphics2D graphics2D = (Graphics2D) keyFrame.getGraphics();

        if (direction == 1.0D) {
            transform.translate(0, 0);

        } else {

            transform.translate(-110, 0);
        }
        
        graphics2D.drawImage(enemy1.getSubimage(frame * 110, 0, 110, 83), transform, null);
        
        
        if (frame == 9) {
            frame = 0;
        } else {
            frame++;
        }
        
        return keyFrame;
    }

    public void drawEnemy(Graphics2D g) {
        g.setColor(Color.BLUE);
        //g.fillRect(getX(), getY(), 50, 30);
        hitBox.move(getX(), getY());
        g.drawImage(returnFrame(), getX()-30, getY()-30, null);
    }
}
