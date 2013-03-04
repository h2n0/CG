package ip.h2n0.main.entities.particles;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

import java.util.Random;

public class TextParticle extends Particle {

    private String msg;
    private int colour;
    private int lifeTime = 0;
    private int x1, y1, z1;
    private double x2, y2, z2;

    public Random r;

    public TextParticle(String msg, int x, int y, int colour) {
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.colour = colour;
        x1 = x;
        y1 = y;
        z1 = 2;
        x2 = r.nextGaussian() * 0.3;
        y2 = r.nextGaussian() * 0.5;
        z2 = r.nextFloat() * 0.2 + 7;
    }

    @Override
    public void tick() {
        lifeTime++;
        if (lifeTime > 60) {
            remove();
        }
        x1 += x2;
        y1 += y2;
        z1 += z2;

        if (z1 < 0) {
            z1 = 0;
        }

        x = (int) x1;
        y = (int) y1;
    }

    public void render(Screen screen) {
        Font.render(msg, screen, x - msg.length() * 4 + 1, y - (int) (y2), Colours.get(-1, -1, -1, 0));
        Font.render(msg, screen, x - msg.length() * 4, y - (int) (y2), colour);
    }
}
