package ip.h2n0.main.entities;

import ip.h2n0.main.Level.tiles.Tile;
import ip.h2n0.main.entities.particles.TextParticle;

public abstract class Mob extends Entity {

    protected String name;
    protected int speed;
    protected int numSteps = 0;
    protected boolean isMoving;
    protected int movingDir = 1;
    protected int scale = 1;
    public int tickTime = 0;
    protected int stamina;
    public int attackDir = 0;
    protected int maxHealth = 10;
    protected int health = 10;
    protected int hurtTime = 0;
    protected int swimTimer = 0;

    public Mob(String name, int x, int y, int speed) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public void tick() {
        tickTime++;
        if (health <= 0) {
            die();
        }
        if (hurtTime > 0) {
            hurtTime--;
        }
        if (onLava()) {
            hurt(Tile.Lava, dir, 8);
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

    protected void die() {
        remove();
    }

    public abstract boolean hasCollided(int xa, int ya);

    public void hurt(Mob mob, int damage, int attackDir) {
        doHurt(damage, attackDir);
    }

    public void hurt(Tile tile, int dir, int dmg) {
        attackDir = dir ^ 1;
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
        if (hurtTime > 0) return;
        level.addEntity(new TextParticle("" + dmg, x, y, 500));
        hurtTime = 10;
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
