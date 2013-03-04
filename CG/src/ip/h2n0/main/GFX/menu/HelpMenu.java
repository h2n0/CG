package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public class HelpMenu extends Menu {

    private int helpTime = 10;
    private int helpPage = 0;

    public HelpMenu(Menu parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void tick() {
        if (helpTime > 0) {
            helpTime--;
        }
        if (helpPage == 0) {
            options = new String[] { "How to play!", "", "W - Move up", "A - Move left", "S - Move Down", "D - Move right" };
        } else if (helpPage == 1) {
            options = new String[] { "How to play - Combat!" };
        } else if (helpPage == 2) {
            options = new String[] { "How to play - Firends!" };
        } else if (helpPage == 3) {
            options = new String[] { "Finally loot!" };
        }
        if (input.right.isPressed() && helpTime == 0) {
            if (helpPage == 3) {
                helpPage = -1;
                helpTime = 10;
            }
            helpPage++;
            helpTime = 10;
        }
        if (input.left.isPressed() && helpTime == 0) {
            if (helpPage == -1) {
                helpPage = 3;
                helpTime = 10;
            }
            helpPage--;
            helpTime = 10;
        }
        if (input.esc.isPressed()) {
            game.setMenu(parent);
        }
    }

    @Override
    public void render(Screen screen) {
        screen.set(0);
        for (int i = 0; i < options.length; i++) {
            String msg = options[i];
            int colour;
            if (i == 0) {
                colour = 555;
            } else {
                colour = 333;
            }
            Font.render(msg, screen, 10, (10 * i) + 40, Colours.get(-1, -1, -1, colour));
        }
        Font.render("Press \"ESC\" to back to the main menu ", screen, 10, 200, Colours.get(-1, -1, -1, 222));
    }
}
