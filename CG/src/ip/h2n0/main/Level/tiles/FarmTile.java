package ip.h2n0.main.Level.tiles;

import ip.h2n0.main.GFX.Colours;
import ip.h2n0.main.Level.Level;


public class FarmTile extends BasicTile {

    private int colour = 310;
    public FarmTile(int id, int x, int y, int tileColour, int levelColour, int ageLimit) {
        super(id, x, y, tileColour, levelColour);
    }
    
    @Override
    public void tick(Level level , int x , int y4){
        tileColour = Colours.get(colour, colour, colour, colour);
    }

}
