package blockBuster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

  private boolean play = false;
  private int score = 0;
  private int totalBricks = 21;
  private Timer timer;
  private int delay = 8;
  private int playerX = 300;
  private int ballPosX = 120;
  private int ballPosY = 350;
  private int ballXdir = -1;
  private int ballYdir = -2;
  private MapGenerator map;

  public Gameplay() {
    map = new MapGenerator(3, 7);
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay, this);
    timer.start();
  }

  public void paint(Graphics g) {
    // background
    g.setColor(Color.DARK_GRAY);
    g.fillRect(1,1,683, 592);

    // draw map
    map.draw((Graphics2D)g);

    // boarders
    g.setColor(Color.GREEN);
    g.fillRect(0,0,3,592);
    g.fillRect(0,0,683,3);
    g.fillRect(683,0,3,592);

    // paddle
    g.setColor(Color.BLACK);
    g.fillRect(playerX, 500,100,8);

    // ball
    g.setColor(Color.GRAY);
    g.fillOval(ballPosX, ballPosY, 20,20);
    g.dispose();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    timer.start();
    Rectangle ball = new Rectangle(ballPosX, ballPosY,20,20);
    Rectangle paddle = new Rectangle(playerX,500,100,8);
    if (play) {
      if (ball.intersects(paddle)) {
        ballYdir = -ballYdir;
      }
      for (int i = 0; i < map.map.length; i++) {
        for (int j = 0; j < map.map[0].length; j++) {
          if (map.map[i][j] > 0) {
            int blockWidth = map.blockWidth;
            int blockHeight = map.blockHeight;
            int blockX = j * blockWidth + 80;
            int blockY = i * blockHeight + 50;

            Rectangle block = new Rectangle(blockX, blockY, blockWidth, blockHeight);
            if (ball.intersects(block)) {
              map.setBlockValue(0, i, j);
              ballYdir = -ballYdir;
              totalBricks--;
              score += 10;

            }
          }
        }
      }

      ballPosX += ballXdir;
      ballPosY += ballYdir;
      if (ballPosX < 0) {
        ballXdir = -ballXdir;
      }
      if (ballPosY < 0) {
        ballYdir = -ballYdir;
      }
      if (ballPosX > 670) {
        ballXdir = -ballXdir;
      }
    }
    repaint();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // not used
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if (playerX > 560) {
        playerX = 560;
      } else {
        moveRight();
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      if (playerX < 25) {
        playerX = 25;
      } else {
        moveLeft();
      }
    }
  }

  public void moveRight() {
    play = true;
    playerX += 20;
  }

  public void moveLeft() {
    play = true;
    playerX -= 20;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // not used
  }
}
