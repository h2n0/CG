package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.Game;
import ip.h2n0.main.InputHandler;
import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public abstract class Menu {
    protected Game game;
    protected InputHandler input;
    protected Menu parent;
    protected int selected = 0;
    protected int waitTime = 10;
    protected int selectTime = 10;
    protected String[] options = new String[0];
    protected String version = Game.VERSION;

    /**
     * initilizes the menus
     * 
     * @param game
     * @param input
     */
    public void init(Game game, InputHandler input) {
        this.game = game;
        this.input = input;
    }

    public void tick() {
        if (input.esc.isPressed()) {
            game.setMenu(parent);
        }
    }

    /**
     * Count all of the menu time variable down to 0
     */
    public void timeCheck() {
        if (waitTime > 0)
            waitTime--;
        if (selectTime > 0) {
            selectTime--;
        }
        if (selected < 0) {
            selected += options.length;
        }
        if (selected >= options.length) {
            selected = 0;
        }
    }

    /**
     * used to render text thats inside of the options array.
     * 
     * @param screen
     */
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
    }
}
