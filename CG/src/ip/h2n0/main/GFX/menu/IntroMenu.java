package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public class IntroMenu extends Menu {

    private int waitTime = 375;
    private int animTime = 0;

    public IntroMenu() {
        super();
        options = new String[] { "C", "G", version, "press \"Enter\"/\"Return\" to play" };
    }

    @Override
    public void tick() {
        while (waitTime > 0) {
            waitTime--;
        }
        if (input.enter.isPressed() && waitTime == 0) {
            game.setMenu(new TitleMenu());
        } else {
            animTime++;
        }
    }

    @Override
    public void render(Screen screen) {
        int colour = 333;
        if (animTime % 60 <= 30) {
            colour = 555;
        } else {
            colour = 333;
        }
        screen.set(0);
        for (int i = 0; i < 2; i++) {
            String msg = options[i];
            String version = options[2];
            String note = options[3];
            Font.renderScale(msg, screen, 120 + (i * 35), (35 * i) + 65, 5, Colours.get(-1, -1, -1, 555));
            Font.renderScale(version, screen, 180, 120, 1, Colours.get(-1, -1, -1, 222));
            Font.render(note, screen, 20, 155, Colours.get(-1, -1, -1, colour));
        }
    }
}
