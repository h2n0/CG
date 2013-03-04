package ip.h2n0.main.Level.tiles;

import ip.h2n0.main.Level.Level;

import java.util.Random;

public class AnimatedlavaTile extends AnimatedTile {

    Level level;

    public AnimatedlavaTile(int id, int[][] animationCoords, int tileColour, int levelColour, int animationSwitchDelay) {
        super(id, animationCoords, tileColour, levelColour, animationSwitchDelay);
    }

    @Override
    public void tick() {
        Random r = new Random();
        if ((System.currentTimeMillis() - lastIterationTime) >= animationSwitchDelay) {
            lastIterationTime = System.currentTimeMillis();
            currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
            this.tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
        }
        if (r.nextInt(60) != 0)
            return;
    //    level.alterTile(getY(), getX(), Stone);
    }
}
