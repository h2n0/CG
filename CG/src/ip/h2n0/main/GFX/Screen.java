package ip.h2n0.main.GFX;

public class Screen {

    public final int MAP_WIDTH = 64;
    public final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

    public final byte BIT_MIRROR_X = 0x01;
    public final byte BIT_MIRROR_Y = 0x02;

    public int[] pixels;

    public int xOffset = 0;
    public int yOffset = 0;

    public int width;
    public int height;

    public SpriteSheet sheet;

    public Screen(int width, int height, SpriteSheet sheet) {
        this.width = width;
        this.height = height;
        this.sheet = sheet;

        pixels = new int[width * height];
    }

    /**
     * 
     * @param xPos
     * @param yPos
     * @param tile
     * @param colour
     * @param mirrorDir
     * @param scale
     */
    public void render(int xPos, int yPos, int tile, int colour, int mirrorDir, int scale) {
        xPos -= xOffset;
        yPos -= yOffset;

        boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
        boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

        int scaleMap = scale - 1;
        int xTile = tile % 32;
        int yTile = tile / 32;
        int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;
        for (int y = 0; y < 8; y++) {
            int ySheet = y;
            if (mirrorY)
                ySheet = 7 - y;

            int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3) / 2);

            for (int x = 0; x < 8; x++) {
                int xSheet = x;
                if (mirrorX)
                    xSheet = 7 - x;
                int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2);
                int col = (colour >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * 8)) & 255;
                if (col < 255) {
                    for (int yScale = 0; yScale < scale; yScale++) {
                        if (yPixel + yScale < 0 || yPixel + yScale >= height)
                            continue;
                        for (int xScale = 0; xScale < scale; xScale++) {
                            if (xPixel + xScale < 0 || xPixel + xScale >= width)
                                continue;
                            pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
                        }
                    }
                }
            }
        }
    }

    public void set(int c) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = c;
        }
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
