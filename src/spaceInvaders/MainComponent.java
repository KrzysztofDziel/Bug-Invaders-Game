package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainComponent extends javax.swing.JComponent implements java.awt.event.ActionListener, java.awt.event.KeyListener {

    static JPanel panel;
    private BufferedImage background;
    private BufferedImage backgroundFin;
    private BufferedImage pauseImg;
    private Player player;
    private final int SHIFT = 110;
    private int sX = 220;
    private int sY = 20;
    private boolean stage = true;
    private int direction = 1;
    private int clock = 0;
    private int fireRatio = 15;
    private boolean endGame = false;
    private Timer timer;
    private int enemySpeed = 50;
    ArrayList<PlayerProjectile> pp = new ArrayList<PlayerProjectile>();
    ArrayList<BasicEnemy> be = new ArrayList<BasicEnemy>();
    ArrayList<EnemyProjectile> ep = new ArrayList<EnemyProjectile>();
    ArrayList<BasicEnemy> be1 = new ArrayList<BasicEnemy>();
    ArrayList<PlayerProjectile> pp1 = new ArrayList<PlayerProjectile>();
    private javafx.scene.media.AudioClip enemyDeath;
    private javafx.scene.media.AudioClip enemyAttack;
    private javafx.scene.media.AudioClip playerAttack;
    private javafx.scene.media.AudioClip gameOver;
    private int shooters = 5;
    private int score = 0;
    private int level = 1;
    private boolean activeGame = false;
    private boolean timerExists;
    private int firstShot = 0;
    private MediaPlayer backgroundGUI;
    private MediaPlayer backgroundGame;
    private boolean isPause = false;
    private int frame = 0;
    private int dir = 1;

    public MainComponent(JPanel panel) {
        JFXPanel sound = new JFXPanel();
        this.add(sound);
        this.setFocusable(true);
        addKeyListener(this);
        this.panel = panel;
        setBounds(0, 0, panel.getWidth(), panel.getHeight());

        try {
            background = javax.imageio.ImageIO.read(MainComponent.class.getResource("/spaceInvaders/resources/backgroundFinal.jpg"));
            backgroundFin = javax.imageio.ImageIO.read(MainComponent.class.getResource("/spaceInvaders/resources/gameOver.jpg"));
            pauseImg = javax.imageio.ImageIO.read(MainComponent.class.getResource("/spaceInvaders/resources/pause.png"));
        } catch (IOException e) {
            background = new BufferedImage(panel.getWidth(), panel.getHeight(), 2);
        }
        URL enemyD = SpaceInvaders.class.getResource("/spaceInvaders/resources/music/enemyDeath.wav");
        URL enemyA = SpaceInvaders.class.getResource("/spaceInvaders/resources/music/enemyAttack.wav");
        URL playerA = SpaceInvaders.class.getResource("/spaceInvaders/resources/music/playerAttack.wav");
        URL gameOv = SpaceInvaders.class.getResource("/spaceInvaders/resources/music/gameOver.wav");
        URL backGr = SpaceInvaders.class.getResource("/spaceInvaders/resources/music/backgroundGUI.mp3");
        URL backGm = SpaceInvaders.class.getResource("/spaceInvaders/resources/music/backgroundGame.mp3");

        backgroundGUI = new MediaPlayer(new javafx.scene.media.Media(backGr.toString()));
        backgroundGame = new MediaPlayer(new javafx.scene.media.Media(backGm.toString()));
        enemyDeath = new javafx.scene.media.AudioClip(enemyD.toString());
        enemyAttack = new javafx.scene.media.AudioClip(enemyA.toString());
        playerAttack = new javafx.scene.media.AudioClip(playerA.toString());
        gameOver = new javafx.scene.media.AudioClip(gameOv.toString());

        backgroundGUI.setOnEndOfMedia(new Runnable() {
            public void run() {
                backgroundGUI.seek(Duration.ZERO);
            }
        });

        backgroundGame.setOnEndOfMedia(new Runnable() {
            public void run() {
                backgroundGame.seek(Duration.ZERO);
            }
        });

        backgroundGUI.setVolume(0.7);
        backgroundGame.setVolume(0.6);

        timerExists = false;
        player = new Player(400, 850, 50, 50);

    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public boolean getPause() {
        return isPause;
    }

    public void paintComponent(Graphics g) {

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawImage(background.getSubimage(frame * 850, 0, 850, 900), null, null);

        if (frame == 3) {
            dir = 0;
        }

        if (frame == 0) {
            dir = 1;
        }

        if (dir == 1) {
            frame++;
        } else {
            frame--;
        }

        if (activeGame) {
            clock++;
            player.drawPlayer(graphics2D);
            player.interateFrame();
            drawPlayerProjectiles(graphics2D);
            drawEnemyProjectiles(graphics2D);
            graphics2D.setColor(Color.darkGray);
            graphics2D.fillRect(0, 0, 100, 55);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(0, 0, 100, 55);
            graphics2D.drawLine(0, 26, 100, 26);
            graphics2D.setColor(Color.red);
            graphics2D.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            graphics2D.drawString("Score : " + score, 10, 20);
            graphics2D.drawString("Level : " + level, 10, 45);
            if (stage) {
                for (int i = 0; i <= 4; i++) {
                    be.add(new BasicEnemy(sX, sY));
                    sX += SHIFT;
                    if (i == 4) {
                        sY += 70;
                        sX = 220;
                    }

                    if (sY > 240) {
                        stage = false;
                    }
                }
            }
            if (be.isEmpty()) {
                stage = true;
                sX = 220;
                sY = 20;
                if (enemySpeed > 5) {
                    enemySpeed -= 5;
                }
                level++;
            }
            if (!stage && clock % enemySpeed == 0) {

                if (!be.isEmpty()) {
                    for (BasicEnemy j : be) {
                        Random rnd = new Random();
                        int setFire = rnd.nextInt(2);
                        if (direction == 1) {
                            j.SetX(j.getX() + 27);
                        } else {
                            j.SetX(j.getX() - 27);
                        }

                        if (setFire == 1 && j.getReload() <= 0 && shooters > 0) {

                            j.setShot(true);
                            j.setReload(5);
                            shooters--;
                        } else {
                            j.setShot(false);
                        }
                        if (j.getShot()) {
                            ep.add(new EnemyProjectile(j.getX(), j.getY()));
                            if (firstShot == 0) {
                                firstShot++;
                            }
                        }
                        j.setReload(j.getReload() - 1);

                    }
                }

                BasicEnemy firstEnemy = be.get(0);
                BasicEnemy lastEnemy = be.get(be.size() - 1);

                if (firstEnemy.getX() <= 0) {
                    direction = 1;
                    for (BasicEnemy i : be) {
                        i.SetY(i.getY() + 30);
                    }
                }
                if (lastEnemy.getX() >= 720) {
                    direction = 0;
                    for (BasicEnemy i : be) {
                        i.SetY(i.getY() + 30);
                    }
                }
            }

            for (BasicEnemy i : be) {

                if (i.getHitBox().intersects(player.getHitBox())) {
                    endGame = true;
                }

                i.setDirection(direction);
                i.drawEnemy(graphics2D);

            }

            for (EnemyProjectile i : ep) {
                if (i.getHitBox().intersects(player.getHitBox())) {
                    endGame = true;
                }
            }

            for (PlayerProjectile i : pp) {
                for (BasicEnemy j : be) {
                    if (i.getHitBox().intersects(j.getHitBox())) {
                        pp1.add(i);
                        be1.add(j);
                        enemyDeath.play();
                        score++;
                    }
                }
            }

            for (PlayerProjectile i : pp1) {
                for (BasicEnemy j : be1) {

                    pp.remove(i);
                    be.remove(j);

                }
            }

            fireRatio--;
            shooters = 5;
            if (firstShot == 1) {
                firstShot = 0;
                enemyAttack.play();
            }

            if (isPause) {
                graphics2D.drawImage(pauseImg, 320, 340, null);
            }
            if (endGame) {
                player.state(false);
                stopBackgroundGame();
                gameOver.play();
                graphics2D.drawImage(backgroundFin, 170, 170, null);
                timer.stop();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int keyCode = e.getKeyChar();
        switch (keyCode) {
            case 'd':
                if (player.getX() != 800) {
                    player.SetX(player.getX() + 10);
                    player.setHitBox();
                }
                break;
            case 'a':
                if (player.getX() != 0) {
                    player.SetX(player.getX() - 10);
                    player.setHitBox();
                }
                break;
            case ' ':
                if (fireRatio <= 0) {
                    pp.add(new PlayerProjectile(player.getX(), player.getY()));
                    fireRatio = 15;
                    playerAttack.play();
                }
                break;
        }
    }

    public void drawPlayerProjectiles(Graphics2D g) {
        Graphics2D graphics2D = (Graphics2D) g;
        if (!pp.isEmpty()) {
            for (PlayerProjectile i : pp) {
                if (i != null) {
                    i.drawProjectile(graphics2D);
                    i.SetY(i.getY() - 15);
                    i.setHitBox();
                    if (i.getY() < 0) {
                        i = null;
                    }
                }
            }
        }
    }

    public void drawEnemyProjectiles(Graphics2D g) {
        Graphics2D graphics2D = (Graphics2D) g;
        if (!ep.isEmpty()) {
            for (EnemyProjectile i : ep) {
                if (i != null) {
                    i.drawProjectile(graphics2D);
                    i.SetY(i.getY() + 5);
                    i.setHitBox();

                    if (i.getY() > 800) {
                        i = null;
                        ep.remove(i);
                    }
                }
            }
        }

    }

    public void timerStart() {
        timer = new Timer(50, this);
        timerExists = true;
    }

    public void timerPause() {
        timer.stop();
    }

    public void timerUnPause() {
        timer.start();
        activeGame = true;
    }

    public boolean gameOver() {
        return player.isAlive();
    }

    public void setState(boolean state) {
        player.state(state);
    }

    public void restartGame() {
        sX = 220;
        sY = 20;
        stage = true;
        direction = 1;
        clock = 0;
        fireRatio = 15;
        endGame = false;
        enemySpeed = 50;
        shooters = 5;
        score = 0;
        level = 1;
        activeGame = false;
        player.SetX(400);
        player.SetY(850);
        pp.removeAll(pp);
        be.removeAll(be);
        ep.removeAll(ep);

    }

    public boolean isOn() {
        return activeGame;
    }

    public boolean isTimer() {
        return timerExists;
    }

    public void playBackgroundGame() {
        backgroundGame.play();
    }

    public void playBackgroundGUI() {
        backgroundGUI.play();
    }

    public void pauseBackgroundGame() {
        backgroundGame.pause();
    }

    public void pauseBackgroundGUI() {
        backgroundGUI.pause();
    }

    public void stopBackgroundGame() {
        backgroundGame.stop();
    }

    public void stopBackgroundGUI() {
        backgroundGUI.stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
