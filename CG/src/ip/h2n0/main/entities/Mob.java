package ip.h2n0.main.entities;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.Level.Level;
import ip.h2n0.main.Level.tiles.Tile;
import ip.h2n0.main.entities.particles.TextParticle;

public abstract class Mob extends Entity {

    protected String name;
    protected double speed;
    protected int numSteps = 0;
    protected boolean isMoving;
    protected int movingDir = 1;
    protected int scale = 1;
    public int tickTime = 0;

    public Mob(Level level, String name, int x, int y, double speed) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public void tick() {
        tickTime++;
        if (level.getTile(x, y) == Tile.Lava) {
            System.out.println("LAVA HURT");
        }
    }

    public void move(int xa, int ya) {
        if (xa != 0 && ya != 0) {
            move(xa, 0);
            move(0, ya);
            numSteps--;
            return;
        }
        numSteps++;
        if (!hasCollided(xa, ya)) {
            if (ya < 0)
                movingDir = 0;
            if (ya > 0)
                movingDir = 1;
            if (xa < 0)
                movingDir = 2;
            if (xa > 0)
                movingDir = 3;
            x += xa * speed;
            y += ya * speed;
        }
    }

    public abstract boolean hasCollided(int xa, int ya);

    public void hurt(Mob mob, int damage, int attackDir) {
        doHurt(damage, attackDir);
    }

    public void hurt(Tile tile, int x, int y, int dmg) {
        int attackDir = dir ^ 1;
        doHurt(dmg, attackDir);
    }

    protected boolean isSolidTile(int xa, int ya, int x, int y) {
        if (level == null) {
            return false;
        }
        Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
        Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
        if (!lastTile.equals(newTile) && newTile.isSolid()) {
            return true;
        }
        return false;
    }

    public void doHurt(int dmg, int dir) {
        level.addEntity(new TextParticle("" + dmg, x, y, Colours.get(-1, -1, -1, 555)));
    }

    protected boolean isSwimming() {
        Tile tile = level.getTile(x >> 3, y >> 3);
        return tile == Tile.Water;
    }

    protected boolean onLava() {
        Tile tile = level.getTile(x >> 3, y >> 3);
        return tile == Tile.Lava;
    }

    public String getName() {
        return name;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void setMovingDir(int movingDir) {
        this.movingDir = movingDir;
    }
}
