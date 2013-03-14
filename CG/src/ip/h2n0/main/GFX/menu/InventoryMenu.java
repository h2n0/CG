package ip.h2n0.main.GFX.menu;

import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.entities.Player;

public class InventoryMenu extends Menu {
    
    public InventoryMenu(Player player){
        
    }
    
    public void tick(){
        if(input.esc.isPressed())
            game.setMenu(null);
    }
    
    @Override
    public void render(Screen screen){
        screen.set(0);
    }
}
