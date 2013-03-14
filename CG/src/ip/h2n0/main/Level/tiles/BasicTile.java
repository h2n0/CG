package ip.h2n0.main.Level.tiles;

import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.Level.Level;

public class BasicTile extends Tile {

    protected int tileId;
    protected int tileColour;

    public BasicTile(int id, int x, int y, int tileColour, int levelColour) {
        super(id, false, false, levelColour);
        this.tileId = x + y * 32;
        this.tileColour = tileColour;
        this.x = x;
        this.y = y;
    }

    public void tick(Level level, int x, int y) {
    }

    public void render(Screen screen, Level level, int x, int y) {
        screen.render(x, y, tileId, tileColour, 0x00, 1);
    }

}
