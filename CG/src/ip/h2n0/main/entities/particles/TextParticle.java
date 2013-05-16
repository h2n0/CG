package ip.h2n0.main.entities.particles;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

import java.util.Random;

public class TextParticle extends Particle {

    private String msg;
    private int colour;
    private int age = 0;
    private double x1, y1, z1;
    private double x2, y2, z2;

    public Random r = new Random();

    public TextParticle(String msg, int x, int y, int colour) {
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.colour = colour;
        x1 = x;
        y1 = y;
        z1 = 2;
        x2 = r.nextGaussian() * 0.3;
        y2 = r.nextGaussian() * 0.2;
        z2 = r.nextFloat() * 0.7 + 2;
    }

    @Override
    public void tick() {
        age++;
        if (age > 45) {
            remove();
        }
        x1 += x2;
        y1 += y2;
        z1 += z2;

        if (z1 < 0) {
            z1 = 0;
            z2 *= -0.5;
            x2 *= 0.6;
            y2 *= 0.6;
        }
        z2 -= 0.15;
        x = (int) x1;
        y = (int) y1;
    }

    public void render(Screen screen) {
        Font.render(msg, screen, x - msg.length() * 4 + 1, y - (int) (z1) + 1, Colours.get(-1, -1, -1, 0));
        Font.render(msg, screen, x - msg.length() * 4, y - (int) (z1), Colours.get(-1, -1, -1, colour));
    }
}
