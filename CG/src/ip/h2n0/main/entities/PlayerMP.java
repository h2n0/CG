package ip.h2n0.main.entities;

import ip.h2n0.main.Game;
import ip.h2n0.main.InputHandler;
import ip.h2n0.main.Level.Level;

import java.net.InetAddress;

public class PlayerMP extends Player {

    public InetAddress ipAddress;
    public int port;

    public PlayerMP(Game game, Level level, int x, int y, InputHandler input, String username, InetAddress ipAddress, int port) {
        super(game, level, x, y, input, username);
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public PlayerMP(Game game, Level level, int x, int y, String username, InetAddress ipAddress, int port) {
        super(game, level, x, y, null, username);
        this.ipAddress = ipAddress;
        this.port = port;
    }
}
