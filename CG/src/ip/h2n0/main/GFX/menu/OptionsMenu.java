package ip.h2n0.main.GFX.menu;



public class OptionsMenu extends Menu {

    public OptionsMenu(Menu parent) {
        this.parent = parent;
        options = new String[] { "Options" , "" ,"New colours?" };
    }

    public void tick() {
        timeCheck();
    }
}
