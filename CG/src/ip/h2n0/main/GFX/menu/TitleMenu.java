package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.Game;
import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public class TitleMenu extends Menu {

    public TitleMenu() {
        super();
        options = new String[] { "Play", "Help", "About", "Credits", "update" };
    }

    @Override
    public void tick() {
        timeCheck();
        if (input.up.isPressed() && waitTime == 0) {
            selected--;
            waitTime = 10;
        }
        if (input.down.isPressed() && waitTime == 0) {
            selected++;
            waitTime = 10;
        }
        if (input.enter.isPressed()) {
            input.releaseAll();
            if (selectTime == 0 && waitTime == 0) {
                switch (selected) {
                case 0:
                    game.setMenu(new GameTypeMenu(this));
                    break;
                case 1:
                    game.setMenu(new HelpMenu(this));
                    break;
                case 2:
                    game.setMenu(new AboutMenu(this));
                    break;
                case 3:
                    game.setMenu(new CreditsMenu(this));
                    break;
                case 4:
                    game.setMenu(new UpdateMenu(this));
                    break;
                }
            }
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
        Font.render(Game.VERSION, screen, 10, 155, Colours.get(-1, -1, -1, 222));

    }
}
