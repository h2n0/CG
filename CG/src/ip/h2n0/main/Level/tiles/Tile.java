package ip.h2n0.main.Level.tiles;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.Level.Level;

public abstract class Tile {

    public static final Tile[] tiles = new Tile[256];
    public static final Tile Void = new BasicSolidTile(0, 0, 0, Colours.get(000, -1, -1, -1), 0xFF000000);
    public static final Tile Stone = new BasicSolidTile(1, 1, 0, Colours.get(-1, 333, -1, -1), 0xFF555555);
    public static final Tile Grass = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1), 0xFF00FF00);
    public static final Tile Water = new AnimatedTile(3, new int[][] { { 0, 4 }, { 1, 4 }, { 2, 4 }, { 1, 4 } }, Colours.get(-1, 004, 115, -1), 0xFF0000FF, 750);
    public static final Tile Lava = new AnimatedlavaTile(4, new int[][] { { 0, 5 }, { 1, 5 }, { 2, 5 }, { 1, 5 } }, Colours.get(-1, 530, 400, -1), 0xFFFF5933, 1250);

    protected byte id;
    protected boolean solid;
    protected boolean emitter;
    private int levelColour;
    protected int x, y;

    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour) {
        this.id = (byte) id;
        if (tiles[id] != null)
            throw new RuntimeException("Duplicate tile id on " + id);
        this.solid = isSolid;
        this.emitter = isEmitter;
        this.levelColour = levelColour;
        tiles[id] = this;
    }

    public byte getId() {
        return id;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isEmitter() {
        return emitter;
    }

    public int getLevelColour() {
        return levelColour;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void tick();

    public abstract void render(Screen screen, Level level, int x, int y);
}
