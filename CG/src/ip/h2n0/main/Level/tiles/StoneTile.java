package ip.h2n0.main.Level.tiles;

import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.Level.Level;

public class StoneTile extends BasicSolidTile {

    boolean u;
    boolean d;
    boolean l;
    boolean r;

    public StoneTile(int id, int x, int y, int tileColour, int levelColour) {
        super(id, x, y, tileColour, levelColour);
    }

    public void tick(Level level, int x, int y) {
       int a = 8;
        if (level.getTile(x + a, y) == this) {
            r = true;
            System.out.println("r = true");
        }
        if (level.getTile(x - a, y) == this) {
            l = true;
            System.out.println("l = true");
        }
        if (level.getTile(x, y + a) == this) {
            u = true;
            System.out.println("u = true");
        }
        if (level.getTile(x, y - a) == this) {
            d = true;
            System.out.println("d = true");
        }
    }

    public void render(Screen screen, Level level, int x, int y) {
        this.x = x;
        this.y = y;
        if (u) {
            screen.render(x, y, 9 + 0 * 32, tileColour, 0, 1);
        } else if (d) {
            screen.render(x, y, 9 + 2 * 32, tileColour, 0, 1);
        } else if (l) {
            screen.render(x, y, 8 + 1 * 32, tileColour, 0, 1);
        } else if (r) {
            screen.render(x, y, 10 + 1 * 32, tileColour, 0, 1);
        } else if (l && u) {
            screen.render(x, y, 8 + 2 * 32, tileColour, 0, 1);
        } else {
            screen.render(x, y, 9 + 1 * 32, tileColour, 0, 1);
        }
    }
}
