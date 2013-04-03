package ip.h2n0.main.entities.particles;

import ip.h2n0.main.GFX.Screen;
import ip.h2n0.main.entities.Entity;

public class Particle extends Entity {

    protected int lifeTime;
    protected int x, y;

    public Particle() {
    }
    
    public Particle(String type,int x , int y , int lifeTime){
        switch(type.toLowerCase()){
        default:
            break;
        case "flame":
            break;
        case "smash":
            break;
        }
    }

    public void render(Screen screen) {

    }

    @Override
    public void tick() {

    }

}
