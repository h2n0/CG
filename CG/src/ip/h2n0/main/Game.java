package ip.h2n0.main;

import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.GFX.SpriteSheet;
import ip.h2n0.main.GFX.menu.IntroMenu;
import ip.h2n0.main.GFX.menu.Menu;
import ip.h2n0.main.Level.Level;
import ip.h2n0.main.entities.Player;
import ip.h2n0.main.entities.PlayerMP;
import ip.h2n0.main.net.GameClient;
import ip.h2n0.main.net.GameServer;
import ip.h2n0.main.net.packets.Packet00Login;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 280;
    public static final int HEIGHT = 150;
    public static final int SCALE = 3;
    public static final String NAME = "CG";
    public static String VERSION = "V0.6.3_1";
    public static final Dimension DIMENSIONS = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
    public JFrame frame;

    private Thread thread;

    public boolean running = false;
    public int tickCount = 0;
    public long startTime = System.currentTimeMillis();
    public long endTime = System.currentTimeMillis();

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private int[] colours = new int[6 * 6 * 6];

    public static Game game;
    private Screen screen;
    public InputHandler input = new InputHandler(this);
    public WindowHandler windowHandler;
    public Level level;
    public Menu menu;
    public Player player;

    public GameClient socketClient;
    public GameServer socketServer;

    public boolean debug = true;
    public boolean isApplet = false;

    public void colourInit() {
        int index = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);

                    colours[index++] = rr << 16 | gg << 8 | bb;
                }
            }
        }
    }

    public void init() {
        game = this;
        setMenu(new IntroMenu());
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/art/SpriteSheet.png"));
        input = new InputHandler(this);
        level = new Level("/art/levels/FB-Test.png");
        // level = new Level();
        player = new PlayerMP(this, level, 100, 100, input, JOptionPane.showInputDialog(this, "Please enter a username"), null, -1);
        level.addEntity(player);
        if (!isApplet) {
            Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.x, player.y);
            if (socketServer != null) {
                socketServer.addConnection((PlayerMP) player, loginPacket);
            }
        }
    }

    public void startServer() {
        socketServer = new GameServer(this);
        socketServer.start();
        socketClient = new GameClient(this, "localhost");
        socketClient.start();
    }

    public void join() {
        socketClient = new GameClient(this, JOptionPane.showInputDialog(this, "IP to join: "));
        socketClient.start();
    }

    public void start() {
        running = true;
        thread = new Thread(this, NAME + "_Game");
        if (!isApplet) {
            if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
                socketServer = new GameServer(this);
                socketServer.start();
            }
            socketClient = new GameClient(this, JOptionPane.showInputDialog(this, "IP :"));
            socketClient.start();
        }
        thread.start();
    }

    public void stop() {
        if (running = false)
            return;
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        colourInit();
        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                debug(DebugLevel.INFO, ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick() {
        tickCount++;
        if (menu != null) {
            menu.tick();
        } else {
            level.tick();
        }
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        if (menu != null)
            menu.init(this, input);
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        int xOffset = player.x - (screen.width / 2);
        int yOffset = player.y - (screen.height / 2);
        level.renderTiles(screen, xOffset, yOffset);
        level.renderEntities(screen);
        renderGUI();
        for (int y = 0; y < screen.height; y++) {
            for (int x = 0; x < screen.width; x++) {
                int colourCode = screen.pixels[x + y * screen.width];
                if (colourCode < 255)
                    pixels[x + y * WIDTH] = colours[colourCode];
            }
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public void renderGUI() {
        if (menu != null) {
            menu.render(screen);
        } else {
            // Font.renderFrame(screen, "Things", 0, 19, 34, 22);
        }
    }

    public void debug(DebugLevel level, String msg) {
        switch (level) {
        default:
        case GAME:
            if (debug) {
                System.out.println("[" + NAME + " : " + VERSION + "] " + msg);
            }
            break;
        case INFO:
            System.out.println("[" + NAME + "] [INFO] " + msg);
            break;
        case WARNING:
            System.out.println("[" + NAME + "] [WARNING] " + msg);
            break;
        case SEVERE:
            System.out.println("[" + NAME + "] [SEVERE]" + msg);
            this.stop();
            break;
        }
    }

    public static enum DebugLevel {
        GAME, INFO, WARNING, SEVERE;
    }
}
