package ip.h2n0.main;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameLauncher extends Applet {

    private static Game game = new Game();
    public static final boolean DEBUG = true;

    @Override
    public void init() {
        setLayout(new BorderLayout());
        add(game, BorderLayout.CENTER);
        setMaximumSize(Game.DIMENSIONS);
        setMinimumSize(Game.DIMENSIONS);
        setPreferredSize(Game.DIMENSIONS);
        game.debug = DEBUG;
        game.isApplet = true;
    }

    @Override
    public void start() {
        init();
        game.start();
    }

    @Override
    public void stop() {
        game.stop();
    }

    public static void main(String[] args) {
        game.frame = new JFrame(Game.NAME);
        Image icon = null;
        if (!game.isApplet) {
            try {
                icon = ImageIO.read(Game.class.getResourceAsStream("/art/Icon.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        game.setMinimumSize(Game.DIMENSIONS);
        game.setMaximumSize(Game.DIMENSIONS);
        game.setPreferredSize(Game.DIMENSIONS);

        game.frame.setIconImage(icon);
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLayout(new BorderLayout());

        game.frame.add(game, BorderLayout.CENTER);
        game.frame.pack();

        game.frame.setResizable(false);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.windowHandler = new WindowHandler(game);
        game.debug = DEBUG;

        game.start();
    }
}
