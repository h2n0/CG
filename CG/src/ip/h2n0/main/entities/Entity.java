package ip.h2n0.main.entities;

import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.Level.Level;

public abstract class Entity {

    public int x, y , dir;
    protected Level level;
    public boolean removed;

    public Entity(Level level) {
        init(level);
    }

    public final void init(Level level) {
        this.level = level;
    }

    public void removed() {
        removed = true;
    }

    public void tick() {

    }

    public abstract void render(Screen screen);
}
