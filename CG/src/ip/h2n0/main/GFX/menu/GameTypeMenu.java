package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public class GameTypeMenu extends Menu {


    public GameTypeMenu(Menu parent) {
        super();
        this.parent = parent;
        options = new String[] { "Single Player", "Multiplayer" };
    }

    @Override
    public void tick() {
        if (selectTime > 0) {
            selectTime--;
        }
        if (waitTime > 0)
            waitTime--;
        if (input.up.isPressed() && waitTime == 0) {
            selected--;
            waitTime = 10;
        }
        if (input.down.isPressed() && waitTime == 0) {
            selected++;
            waitTime = 10;
        }
        if (selected < 0) {
            selected += options.length;
        }
        if (selected >= options.length) {
            selected = 0;
        }
        if (input.enter.isPressed()) {
            if (selected == 0 && selectTime == 0) {
                game.setMenu(null);
            } else if (selected == 1 && selectTime == 0) {
                game.setMenu(new MultiTypeMenu(this));
            }
        }
        if (input.esc.isPressed()) {
            game.setMenu(parent);
        }
    }

    @Override
    public void render(Screen screen) {
        screen.set(0);
        for (int i = 0; i < options.length; i++) {
            int colour = 222;
            String msg = options[i];
            if (i == selected) {
                msg = "> " + msg + " <";
                colour = 555;
            }
            Font.render(msg, screen, 20 - msg.length(), 40 + (20 * i), Colours.get(-1, -1, -1, colour));
        }
    }
}
