package ip.h2n0.main.Level.tiles;

import ip.h2n0.main.Level.Level;

public class LavaTile extends AnimatedTile {

    public LavaTile(int id, int[][] animationCoords, int tileColour, int levelColour, int animationSwitchDelay) {
        super(id, animationCoords, tileColour, levelColour, animationSwitchDelay);
    }

    @Override
    public void tick(Level level, int x, int y) {
        if ((System.currentTimeMillis() - lastIterationTime) >= animationSwitchDelay) {
            lastIterationTime = System.currentTimeMillis();
            currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
            this.tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
        }
    }
}
