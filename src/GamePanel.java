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

    Timer timer;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(16, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (wDown) player.move(0.05, map);
        if (sDown) player.move(-0.05, map);
        if (aDown) player.rotate(0.03);
        if (dDown) player.rotate(-0.03);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ceiling
        g.setColor(new Color(50, 50, 50));
        g.fillRect(0, 0, WIDTH, HEIGHT / 2);

        // floor
        g.setColor(new Color(100, 100, 100));
        g.fillRect(0, HEIGHT / 2, WIDTH, HEIGHT / 2);

        // walls
        raycaster.render(g, player, map, WIDTH, HEIGHT);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) wDown = true;
        if (key == KeyEvent.VK_S) sDown = true;
        if (key == KeyEvent.VK_A) aDown = true;
        if (key == KeyEvent.VK_D) dDown = true;
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
