package ip.h2n0.main;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.GFX.SpriteSheet;
import ip.h2n0.main.Level.Level;
import ip.h2n0.main.entities.Player;
import ip.h2n0.main.entities.PlayerMP;
import ip.h2n0.main.net.GameClient;
import ip.h2n0.main.net.GameServer;
import ip.h2n0.main.net.packets.Packet00Login;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 280;
    public static final int HEIGHT = 150;
    public static final int SCALE = 3;
    public static final String NAME = "CG";
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

    public String VERSION = "V0.6";

    public boolean debug = true;
    public boolean isApplet = false;

    public void colourInit() {
        game = this;
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
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/art/SpriteSheet.png"));
        input = new InputHandler(this);
        level = new Level("/art/levels/FB-Test.png");
        // level = new Level();
        player = new PlayerMP(level, 100, 100, input, JOptionPane.showInputDialog(this, "Please enter a username"), null, -1);
        level.addEntity(player);
        if (!isApplet) {
            Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.x, player.y);
            if (socketServer != null) {
                socketServer.addConnection((PlayerMP) player, loginPacket);
            }
        }
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, NAME + "_Game");
        thread.start();
        if (!isApplet) {
            if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
                socketServer = new GameServer(this);
                socketServer.start();
            }
            socketClient = new GameClient(this, JOptionPane.showInputDialog(this, "IP :"));
            socketClient.start();
        }
    }

    public synchronized void stop() {
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
        if(!hasFocus()){
            input.releaseAll();
        }
        tickCount++;
        level.tick();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            requestFocus();
            return;
        }
        int xOffset = player.x - (screen.width / 2);
        int yOffset = player.y - (screen.height / 2);
        level.renderTiles(screen, xOffset, yOffset);
        level.renderEntities(screen);
        Font.renderFrame(screen, "Time : " + tickCount / 60, 1, 3, 18, 9);
        if (!hasFocus()) {
            renderFocusNagger();
        }
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

    private void renderFocusNagger() {
        String msg = "Focus...";
        int xx = (WIDTH - msg.length() * 8) / 2;
        int yy = (HEIGHT - 8) / 2;
        int w = msg.length();
        int h = 1;

        screen.render(xx - 8, yy - 8, 0 + 13 * 32, Colours.get(-1, 1, 5, 445), 0 , 1);
        screen.render(xx + w * 8, yy - 8, 0 + 13 * 32, Colours.get(-1, 1, 5, 445), 1 , 1);
        screen.render(xx - 8, yy + 8, 0 + 13 * 32, Colours.get(-1, 1, 5, 445), 2 , 1);
        screen.render(xx + w * 8, yy + 8, 0 + 13 * 32, Colours.get(-1, 1, 5, 445), 3 , 1);
        for (int x = 0; x < w; x++) {
            screen.render(xx + x * 8, yy - 8, 1 + 13 * 32, Colours.get(-1, 1, 5, 445), 0 , 1);
            screen.render(xx + x * 8, yy + 8, 1 + 13 * 32, Colours.get(-1, 1, 5, 445), 2 , 1);
        }
        for (int y = 0; y < h; y++) {
            screen.render(xx - 8, yy + y * 8, 2 + 13 * 32, Colours.get(-1, 1, 5, 445), 0 , 1);
            screen.render(xx + w * 8, yy + y * 8, 2 + 13 * 32, Colours.get(-1, 1, 5, 445), 1 , 1);
        }

        if ((tickCount / 20) % 2 == 0) {
            Font.render(msg, screen, xx, yy, Colours.get(5, 333, 333, 333));
        } else {
            Font.render(msg, screen, xx, yy, Colours.get(5, 555, 555, 555));
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
