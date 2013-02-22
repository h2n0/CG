package ip.h2n0.main.Audio;

import java.applet.Applet;
import java.applet.AudioClip;

public class Music {

    public Music[] music = new Music[20];
    public final Music title = new Music("/Audio/music/Title.wav");
    
    
    private AudioClip clip;

    public Music(String path) {
        try {
            clip = Applet.newAudioClip(Sound.class.getResource(path));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void loop(){
        try{
           new Thread(){
               public void run(){
                   clip.loop();
               }
           }.start();
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
    public void stop(){
        try{
            new Thread(){
                public void run(){
                    clip.stop();
                }
            }.start();
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
}
