package ip.h2n0.main.Level.tiles;

import java.util.Random;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.Level.Level;

public abstract class Tile {

    public static final Tile[] tiles = new Tile[256];
    public static final Tile Void = new BasicSolidTile(0, 0, 0, Colours.get(000, -1, -1, -1), 0xFF000000);
    public static final Tile Stone = new StoneTile(1, 9, 1, Colours.get(131, 333, 444, 555), 0xFF555555);
    public static final Tile Grass = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1), 0xFF00FF00);
    public static final Tile Water = new WaterTile(3, new int[][] { { 0, 4 }, { 1, 4 }, { 2, 4 }, { 1, 4 } }, Colours.get(-1, 004, 115, -1), 0xFF0000FF, 750);
    public static final Tile Lava = new LavaTile(4, new int[][] { { 0, 5 }, { 1, 5 }, { 2, 5 }, { 1, 5 } }, Colours.get(-1, 530, 400, -1), 0xFFFF5933, 1250);
    public static final Tile FarmLand = new FarmTile(5, 3, 1, Colours.get(210, 310, 555, 111), 0xFFA54200, 100);

    public byte id;
    protected boolean solid;
    protected boolean emitter;
    private int levelColour;
    protected int x, y;
    Random r = new Random();

    protected boolean connectsToLava = false;
    protected boolean connectsToWater = false;
    protected boolean connectsToStone = false;

    public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour) {
        this.id = (byte) id;
        if (tiles[id] != null)
            throw new RuntimeException("Duplicate tile id on " + id + " with " + tiles[id]);
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

    public boolean connectsToLiquid() {
        return connectsToWater || connectsToLava;
    }

    public abstract void tick(Level level, int x, int y);

    public abstract void render(Screen screen, Level level, int x, int y);
}
