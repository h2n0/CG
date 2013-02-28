package ip.h2n0.main.Level;

import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.Level.tiles.Tile;
import ip.h2n0.main.entities.Entity;
import ip.h2n0.main.entities.Player;
import ip.h2n0.main.entities.PlayerMP;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Level {

    private byte[] tiles;
    public int width;
    public int height;
    private List<Entity> entities = new ArrayList<Entity>();
    private String imagePath;
    private BufferedImage image;
    Player player;

    public Level(String imagePath) {
        if (imagePath != null) {
            this.imagePath = imagePath;
            this.loadLevelFromFile();
        } 
    }
    
    public Level(){
        this.width = 128;
        this.height = 128;
        this.generateLevel(width , height);
    }

    private void loadLevelFromFile() {
        try {
            this.image = ImageIO.read(Level.class.getResource(this.imagePath));
            this.width = this.image.getWidth();
            this.height = this.image.getHeight();
            tiles = new byte[width * height];
            this.loadTiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTiles() {
        int[] tileColours = this.image.getRGB(0, 0, width, height, null, 0, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileCheck: for (Tile t : Tile.tiles) {
                    if (t != null && t.getLevelColour() == tileColours[x + y * width]) {
                        this.tiles[x + y * width] = t.getId();
                        break tileCheck;
                    }
                }
            }
        }
    }

    @SuppressWarnings("unused")
    private void saveLevelToFile() {
        try {
            ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void alterTile(int x, int y, Tile newTile) {
        this.tiles[x + y * width] = newTile.getId();
        image.setRGB(x, y, newTile.getLevelColour());
    }

    public void generateLevel(int width , int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x * y % 10 < 7) {
                    tiles[x + y * width] = Tile.Water.getId();
                } else {
                    tiles[x + y * width] = Tile.Lava.getId();
                }
            }
        }
    }

    public synchronized List<Entity> getEntitys() {
        return this.entities;
    }

    public void tick() {
        if (player != null) {
            gameCheck();
        }
        for (Entity e : entities) {
            e.tick();
        }

        for (Tile t : Tile.tiles) {
            if (t == null) {
                break;
            }
            t.tick();
        }
    }

    public void renderTiles(Screen screen, int xOffset, int yOffset) {
        if (xOffset < 0)
            xOffset = 0;
        if (xOffset > ((width << 3) - screen.width))
            xOffset = ((width << 3) - screen.width);
        if (yOffset < 0)
            yOffset = 0;
        if (yOffset > ((height << 3) - screen.height))
            yOffset = ((height << 3) - screen.height);

        screen.setOffset(xOffset, yOffset);

        for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
            for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
                getTile(x, y).render(screen, this, x << 3, y << 3);
            }
        }
    }

    public void renderEntities(Screen screen) {
        for (Entity e : getEntitys()) {
            e.render(screen);
        }
    }

    public Tile getTile(int x, int y) {
        if (0 > x || x >= width || 0 > y || y >= height)
            return Tile.Void;
        return Tile.tiles[tiles[x + y * width]];
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Player) {
            player = (Player) entity;
        }
        entity.removed = false;
        entities.add(entity);
        entity.init(this);
    }

    public void removePlayerMP(String username) {
        int index = 0;
        for (Entity e : entities) {
            if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
                break;
            }
            index++;
        }
        this.entities.remove(index);
    }

    private void gameCheck() {
        if (player.removed) {
            System.out.print(true);
        }
    }

    private int getPlayerMPIndex(String username) {
        int index = 0;
        for (Entity e : entities) {
            if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void movePlayer(String username, int x, int y , int numSteps , boolean isMoving , int movingDir) {
        int index = getPlayerMPIndex(username);
        PlayerMP player = (PlayerMP) this.getEntitys().get(index);
        player.x = x;
        player.y = y;
        player.setMoving(isMoving);
        player.setNumSteps(numSteps);
        player.setMovingDir(movingDir);
    }
}