package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.entities.Player;

public class PauseMenu extends Menu {

    private Player player;
    private int animTime = 0;
    private int colour;


    public PauseMenu(Player player) {
        super();
        this.player = player;
    }

    @Override
    public void tick() {
        animTime++;
        timeCheck();
        if (input.esc.isPressed() && waitTime == 0) {
            game.setMenu(null);
        }
        if (animTime % 60 < 30) {
            colour = 555;
        } else {
            colour = 333;
        }
    }

    @Override
    public void render(Screen screen) {
        Font.render("Paused!", screen, player.x - 8, player.y, Colours.get(-1, -1, -1, colour));
    }
}
