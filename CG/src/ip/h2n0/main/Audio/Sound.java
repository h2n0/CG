package ip.h2n0.main.Audio;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

    public Sound[] sound = new Sound[20];
    public final Sound test = new Sound("/Audio/Sound/test.wav");

    private AudioClip clip;

    public Sound(String path) {
        try {
            clip = Applet.newAudioClip(Sound.class.getResource(path));
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public void play() {
        try {
            new Thread() {
                public void run() {
                    clip.play();
                }
            }.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
