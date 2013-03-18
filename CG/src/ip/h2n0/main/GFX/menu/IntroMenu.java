package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.GFX.Font;
import ip.h2n0.main.GFX.Screen;

public class IntroMenu extends Menu {

    private int waitTime = 375;
    private int animTime = 0;
    private int fTime = 0;

    public IntroMenu() {
        super();
        options = new String[] { "C", "G", version, "press \"Enter\"/\"Return\" to play","(C) Fire Leaf Studios 2013" };
        fTime = r.nextInt(100);
        System.out.println(fTime);
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
        int fColour = 333;
        if (animTime % 60 <= 30) {
            colour = 555;
        } else {
            colour = 333;
        }
        if(fTime <=50 ){
            fColour = 520;
        }else{
            fColour = 542;
        }
        screen.set(0);
        for (int i = 0; i < 2; i++) {
            String msg = options[i];
            String version = options[2];
            String note = options[3];
            String C = options[4];
            Font.renderScale(msg, screen, 120 + (i * 35), (35 * i) + 65, 5, Colours.get(-1, -1, -1, 555));
            Font.render(version, screen, 180, 120, Colours.get(-1, -1, -1, 222));
            Font.render(note, screen, 23, 145, Colours.get(-1, -1, -1, colour));
            Font.render(C, screen, 30, 155, Colours.get(-1, -1, -1,fColour));

        }
    }
}
