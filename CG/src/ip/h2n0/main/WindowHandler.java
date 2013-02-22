package ip.h2n0.main;

import ip.h2n0.main.Game.DebugLevel;
import ip.h2n0.main.net.packets.Packet01Disconnect;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class WindowHandler implements WindowListener {

    private final Game game;

    public WindowHandler(Game game) {
        this.game = game;
        this.game.frame.addWindowListener(this);
    }

    @Override
    public void windowActivated(WindowEvent event) {
    }

    @Override
    public void windowClosed(WindowEvent event) {
    }

    @Override
    public void windowClosing(WindowEvent event) {
        Packet01Disconnect packet = new Packet01Disconnect(this.game.player.getUsername());
        packet.writeData(this.game.socketClient);
        game.debug(DebugLevel.GAME , "Ending game");
    }

    @Override
    public void windowDeactivated(WindowEvent event) {
    }

    @Override
    public void windowDeiconified(WindowEvent event) {
    }

    @Override
    public void windowIconified(WindowEvent event) {
    }

    @Override
    public void windowOpened(WindowEvent event) {
       game.debug(DebugLevel.GAME , "Starting game");
    }

}
