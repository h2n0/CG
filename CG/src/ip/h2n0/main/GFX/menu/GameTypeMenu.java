package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public class GameTypeMenu extends Menu {

    private int d = 0;
    private int m = 0;

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
        m++;
    }

    @Override
    public void render(Screen screen) {
        screen.set(0);
        if(m % 45 <= 23){
            d = 0;
        }else{
            d = 5;
        }
        for (int i = 0; i < options.length; i++) {
             colour = 222;
            String msg = options[i];
            if (i == selected) {
                colour = 555;
                Font.render(msg, screen, 20 - msg.length(), 41 + (20 * i), Colours.get(-1, -1, -1, colour - 333));
                for (int x = 0; x < 2; x++) {
                    screen.render(120 + d + (x * 8), 41 + (20 * i), (26 + x) + 0 * 32, Colours.get(-1, newColour(), newColour(), newColour()), 0, 1);
                }
            }
            Font.render(msg, screen, 20 - msg.length(), 40 + (20 * i), Colours.get(-1, -1, -1, colour));
        }
    }
}
