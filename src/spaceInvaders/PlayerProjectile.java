package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerProjectile {

    Point cord;
    Rectangle hitBox;
    private BufferedImage projectilePlayer;
    private int frame = 0;
    BufferedImage after;
    public PlayerProjectile(int x, int y) {
        cord = new Point(x, y);
        hitBox = new Rectangle(cord.x, cord.y, 10, 12);

        try {
            projectilePlayer = javax.imageio.ImageIO.read(PlayerProjectile.class.getResource("/spaceInvaders/resources/playerProjectile.png"));
            AffineTransform at = new AffineTransform();
            at.scale(0.5, 0.5);
            after = new BufferedImage(308, 43, BufferedImage.TYPE_INT_ARGB);
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            after = scaleOp.filter(projectilePlayer, after);
        } catch (IOException e) {
            projectilePlayer = new BufferedImage(308,43, 2);
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

    public void setHitBox() {
        hitBox.move(getX() + 23, getY() - 30);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
    
    private BufferedImage returnFrame(){
        
        BufferedImage keyFrame = new BufferedImage(308, 43, 2);
        Graphics2D graphics2D = (Graphics2D) keyFrame.getGraphics();
        
        
        graphics2D.drawImage(after.getSubimage(frame * 44, 0, 44, 43), null, null);
        
        if ( frame == 6 ){
            frame = 0;
        } else {
            frame++;
        }
        
        return keyFrame;
    }

    public void drawProjectile(Graphics g) {
        g.setColor(Color.green);
        //g.fillRect(getX() + 23, getY() - 30, 10, 12);
        g.drawImage(returnFrame(), getX()+5, getY()-40, null);
        
    }
}
