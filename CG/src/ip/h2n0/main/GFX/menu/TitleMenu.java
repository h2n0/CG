package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public class TitleMenu extends Menu {

    public TitleMenu() {
        super();
        options = new String[] { "Play", "Help", "About","Credits", "Exit"};
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
                    System.exit(3);
                    break;
                }
            }
        }
    }

    @Override
    public void render(Screen screen) {
        screen.set(0);
        for (int i = 0; i < options.length; i++) {
            String msg = options[i];
            colour = 333;
            if (i == selected) {
                colour = 555;
                msg = msg + " <";
                Font.render(msg, screen, 20 - msg.length(), 41 + (20 * i), Colours.get(-1, -1, -1, colour - 333)); 
            }
            Font.render(msg, screen, 20 - msg.length(), 40 + (20 * i), Colours.get(-1, -1, -1, colour));
        }
    }
}
