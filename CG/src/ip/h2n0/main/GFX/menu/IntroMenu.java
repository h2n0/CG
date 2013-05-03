package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.Game;
import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public class IntroMenu extends Menu {

    private int waitTime = 375;
    private int animTime = 0;
    private boolean ready = false;

    public IntroMenu() {
        super();
    }

    @Override
    public void tick() {
        while (waitTime > 0) {
            waitTime--;
        }
        if (ready) {
            if (input.enter.isPressed() && waitTime == 0) {
                game.setMenu(new TitleMenu());
            } else {
                animTime++;
            }
        }
        tick++;
    }

    @Override
    public void render(Screen screen) {
        int scale = 2;
        if (animTime % 60 <= 30) {
            colour = 555;
        } else {
            colour = 333;
        }
        if (!ready) {
            if (tick % 300 == 299) {
                ready = true;
            }
        }
        if (ready) {
            screen.set(0);
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 9; x++) {
                    screen.render(30 + (x * 8 * scale), 80 + (y * 8 * scale), (23 + x) + (1 + y) * 32, Colours.get(-1, 121, 153, 245), 0, scale);

                }
            }
            if(tick > 60 * 10){
                game.setMenu(new ExpositionMenu(this));
            }
            Font.render("Press \"Enter\"/\"Return\"to continue", screen, 10, 150, Colours.get(-1, -1, -1, colour));
            Font.render(Game.VERSION, screen, 40, 115, Colours.get(-1, -1, -1, 222));
        } else {
            scale = 4;
            screen.set(0);
            for (int y = 0; y < 2; y++) {
                for (int x = 0; x < 2; x++) {
                    screen.render(125 + (x * 8 * scale), 80 + (y * 8 * scale), (30 + x) + (4 + y) * 32, Colours.get(-1, 210, 321, 520), 0, scale);
                }
            }
            Font.render("(C) Fire Leaf Studios 2013", screen, 38, 150, Colours.get(-1, -1, -1, 520));
        }
    }
}