package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemyProjectile {

    Point cord;
    Rectangle hitBox;
    private int enemyFrame = 0;
    private BufferedImage projectileEnemy;

    public EnemyProjectile(int x, int y) {
        cord = new Point(x, y);
        hitBox = new Rectangle(cord.x, cord.y, 10, 12);

        try {
            projectileEnemy = javax.imageio.ImageIO.read(EnemyProjectile.class.getResource("/spaceInvaders/resources/enemyProjectile.png"));

        } catch (IOException e) {

            projectileEnemy = new BufferedImage(308, 43, 2);
        }
    }

    private BufferedImage returnFrame() {

        BufferedImage keyFrame = new BufferedImage(308, 43, 2);
        Graphics2D graphics2D = (Graphics2D) keyFrame.getGraphics();

        graphics2D.drawImage(projectileEnemy.getSubimage(enemyFrame * 44, 0, 44, 43), null, null);

        if (enemyFrame == 6) {
            enemyFrame = 0;
        } else {
            enemyFrame++;
        }

        return keyFrame;
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
    
    public void drawProjectile(Graphics g) {
        g.setColor(Color.yellow);
        //g.fillRect(getX() + 22, getY() + 60, 7, 12);
        g.drawImage(returnFrame(), getX()+5, getY()+40, null);
    }
    
    public void setHitBox() {
        hitBox.move(getX() + 22, getY() + 60);
    }
}
