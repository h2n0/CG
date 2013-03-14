package ip.h2n0.main.Level.tiles;

import ip.h2n0.main.Level.Level;

public class AnimatedTile extends BasicTile {

    protected int[][] animationTileCoords;
    protected int currentAnimationIndex;
    protected long lastIterationTime;
    protected int animationSwitchDelay;

    public AnimatedTile(int id, int[][] animationCoords, int tileColour, int levelColour, int animationSwitchDelay) {
        super(id, animationCoords[0][0], animationCoords[0][1], tileColour, levelColour);
        this.animationTileCoords = animationCoords;
        this.currentAnimationIndex = 0;
        this.lastIterationTime = System.currentTimeMillis();
        this.animationSwitchDelay = animationSwitchDelay;
    }

    @Override
    public void tick(Level level , int x , int y) {
        if ((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)) {
            lastIterationTime = System.currentTimeMillis();
            currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
            this.tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
        }
    }
}
