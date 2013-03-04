package ip.h2n0.main.GFX.menu;


public class AboutMenu extends Menu {

    public AboutMenu(Menu parent) {
        super();
        this.parent = parent;
        options = new String[] { "This is a game", "", "made by some people", "that turned out pretty well", "It's something" };
    }
}