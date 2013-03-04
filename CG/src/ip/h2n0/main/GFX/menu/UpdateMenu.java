package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.Update;
import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public class UpdateMenu extends Menu {

    public UpdateMenu(Menu parent) {
        super();
        this.parent = parent;
        options = new String[] { "yes", "no" };
    }

    @Override
    public void tick() {
        timeCheck();
        if (waitTime > 0) {
            waitTime--;
        }
        if (input.up.isPressed() && waitTime == 0) {
            selected++;
            waitTime = 20;
        }
        if (input.down.isPressed() && waitTime == 0) {
            selected--;
            waitTime = 20;
        }
        if (input.esc.isPressed()) {
            game.setMenu(parent);
        }
        if(input.enter.isPressed() && selectTime == 0){
            input.releaseAll();
            switch(selected){
            case 0:
                Update.update();
                break;
            case 1:
                game.setMenu(parent);
                break;
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
    }
}
