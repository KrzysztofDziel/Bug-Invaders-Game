package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    private Point cord;
    private final int WIDTH;
    private final int HEIGHT;
    private boolean isAlive;
    private Rectangle hitBox;
    private BufferedImage playerAvatar;
    private int frame = 0;

    public Player(int x, int y, int width, int height) {
        cord = new Point(x, y);
        this.HEIGHT = height;
        this.WIDTH = width;
        this.isAlive = true;
        hitBox = new Rectangle(cord.x, cord.y, width, height);

        try {
            playerAvatar = javax.imageio.ImageIO.read(Player.class.getResource("/spaceInvaders/resources/player.png"));

        } catch (IOException e) {

            playerAvatar = new BufferedImage(960, 91, 2);
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

    public void state(boolean live) {
        isAlive = live;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox() {
        hitBox.move(getX(), getY());
    }

    private BufferedImage returnFrame() {

        BufferedImage keyFrame = new BufferedImage(960, 91, 2);
        Graphics2D graphics2D = (Graphics2D) keyFrame.getGraphics();

        graphics2D.drawImage(playerAvatar.getSubimage(frame * 120, 0, 120, 91), null, null);

        return keyFrame;
    }

    public void interateFrame() {
        if (frame == 7) {
            frame = 0;
        } else {
            frame++;
        }
    }

    public void drawPlayer(Graphics2D g) {
        g.setColor(Color.red);
        //g.fillRect(getX(), getY(), WIDTH, HEIGHT);
        g.drawImage(returnFrame(), getX() - 35, getY() - 24, null);
    }
}
