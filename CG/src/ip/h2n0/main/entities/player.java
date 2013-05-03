package ip.h2n0.main.entities;

import ip.h2n0.main.Game;
import ip.h2n0.main.InputHandler;
import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.GFX.menu.PauseMenu;
import ip.h2n0.main.Level.Level;
import ip.h2n0.main.Level.tiles.Tile;
import ip.h2n0.main.entities.particles.TextParticle;
import ip.h2n0.main.item.Item;
import ip.h2n0.main.net.packets.Packet02Move;

import java.util.Random;

public class player extends Mob {

    private InputHandler input;
    private Game game;
    public Screen screen;
    Random r = new Random();
    private int colour = Colours.get(-1, 111, 131, 543);;
    private int scale = 1;
    private int stamina;
    private int ticks = 0;
    private int walkingSpeed = 4;
    private static String username;
    private int inputDelay = 10;
    public Item activeItem;

    @SuppressWarnings("static-access")
    public player(Game game, Level level, int x, int y, InputHandler input, String username) {
        super("Player", x, y, 1);
        this.input = input;
        this.level = level;
        this.username = username;
        this.game = game;
        this.stamina = 5;
        this.activeItem = null;
    }

    public void tick() {
        if (inputDelay > 0) {
            inputDelay--;
        }
        int xa = 0;
        int ya = 0;
        if (input != null) {
            if (input.up.isPressed()) {
                ya--;
            }
            if (input.down.isPressed()) {
                ya++;
            }
            if (input.left.isPressed()) {
                xa--;
            }
            if (input.right.isPressed()) {
                xa++;
            }
            if (input.esc.isPressed() && inputDelay == 0) {
                game.setMenu(new PauseMenu(this));
                inputDelay = 10;
            }
            if (input.enter.isPressed() && inputDelay == 0) {
                level.alterTile(x, y, Tile.FarmLand);
            }
            if (input.shift.isPressed() && stamina != 0) {
                if (ticks % 60 == 59) {
                    stamina--;
                }
                this.speed = 2;
                this.walkingSpeed = 3;
            } else {
                this.speed = 1;
                this.walkingSpeed = 4;
                if (ticks % 120 == 119) {
                    if (stamina >= 5) {
                        stamina = 5;
                    } else {
                        stamina++;
                        try {
                            level.addEntity(new TextParticle("+1S", x, y, 050));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("+1S");
                    }
                }
            }
        }
        if (xa != 0 || ya != 0) {
            move(xa, ya);
            isMoving = true;
            if (!game.isApplet) {
                Packet02Move packet = new Packet02Move(getUsername(), this.x, this.y, this.numSteps, this.isMoving, this.movingDir);
                packet.writeData(Game.game.socketClient);
            }
        } else {
            isMoving = false;
        }
        ticks++;
    }

    public void render(Screen screen) {
        int xTile = 0;
        int yTile = 27;
        int flipTop = (numSteps >> walkingSpeed) & 1;
        int flipBottom = (numSteps >> walkingSpeed) & 1;

        if (movingDir == 1) {
            xTile += 2;
        } else if (movingDir > 1) {
            xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
            flipTop = (movingDir - 1) % 2;
        }

        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2 - 4;
        if (isSwimming()) {
            int waterColour;
            yOffset += 4;
            if (ticks % 60 < 15) {
                waterColour = Colours.get(-1, -1, 225, -1);
            } else if (15 <= ticks % 60 && ticks % 60 < 30) {
                yOffset -= 1;
                waterColour = Colours.get(-1, 225, 115, -1);
            } else if (30 <= ticks % 60 && ticks % 60 < 45) {
                waterColour = Colours.get(-1, 335, -1, 225);
            } else {
                yOffset -= 1;
                waterColour = Colours.get(-1, 225, 115, -1);
            }
            screen.render(xOffset, yOffset + 3, 0 + 26 * 32, waterColour, 0x00, 1);
            screen.render(xOffset + 8, yOffset + 3, 0 + 26 * 32, waterColour, 0x01, 1);
        }
        screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop, scale);
        screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop, scale);

        if (!isSwimming()) {
            screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom, scale);
            screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, flipBottom, scale);
        }
        if (username != null) {
            Font.render(username, screen, xOffset - ((username.length() - 2) / 2 * 8), yOffset - 10, Colours.get(-1, -1, -1, 555));
        }
    }

    public boolean hasCollided(int xa, int ya) {
        int xMin = 0;
        int xMax = 7;
        int yMin = 3;
        int yMax = 7;
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMin)) {
                return true;
            }
        }
        for (int x = xMin; x < xMax; x++) {
            if (isSolidTile(xa, ya, x, yMax)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMin, y)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {
            if (isSolidTile(xa, ya, xMax, y)) {
                return true;
            }
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public static String getUsernameO() {
        return username;
    }
}