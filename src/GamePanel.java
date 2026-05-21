import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    Player player = new Player();
    Map map = new Map();
    Raycaster raycaster = new Raycaster();

    boolean wDown, sDown, aDown, dDown;
    boolean gameWon = false;

    Timer timer;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(8, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameWon) {
            if (wDown) player.move(0.05, map);
            if (sDown) player.move(-0.05, map);
            if (aDown) player.rotate(-0.03);
            if (dDown) player.rotate(0.03);

            if (map.isEnd(player.x, player.y)) gameWon = true;
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(50, 50, 50)); //ceiling
        g.fillRect(0, 0, WIDTH, HEIGHT / 2);

        g.setColor(new Color(100, 100, 100)); //floor
        g.fillRect(0, HEIGHT / 2, WIDTH, HEIGHT / 2);

        raycaster.render(g, player, map, WIDTH, HEIGHT); //walls

        if (gameWon) {
            g.setColor(new Color(0, 180, 0, 160));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 72));
            FontMetrics fm = g.getFontMetrics();
            String msg = "YOU WIN!";
            g.drawString(msg, (WIDTH - fm.stringWidth(msg)) / 2, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            fm = g.getFontMetrics();
            String sub = "Press R to play again";
            g.drawString(sub, (WIDTH - fm.stringWidth(sub)) / 2, HEIGHT / 2 + 50);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) wDown = true;
        if (key == KeyEvent.VK_S) sDown = true;
        if (key == KeyEvent.VK_A) aDown = true;
        if (key == KeyEvent.VK_D) dDown = true;
        if (key == KeyEvent.VK_R && gameWon) {
            map = new Map();
            player = new Player();
            gameWon = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) wDown = false;
        if (key == KeyEvent.VK_S) sDown = false;
        if (key == KeyEvent.VK_A) aDown = false;
        if (key == KeyEvent.VK_D) dDown = false;
    }

    public void keyTyped(KeyEvent e) {}
}
