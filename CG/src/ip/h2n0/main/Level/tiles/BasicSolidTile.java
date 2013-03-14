package ip.h2n0.main.Level.tiles;

import java.util.Random;

public class BasicSolidTile extends BasicTile {

    Random r = new Random();

    public BasicSolidTile(int id, int x, int y, int tileColour, int levelColour) {
        super(id, x, y, tileColour, levelColour);
        this.solid = true;
    }

}
