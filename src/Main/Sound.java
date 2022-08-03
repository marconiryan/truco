package Main;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {

    Clip clip;

    URL[] soundURL = new URL[10];

    public Sound() {
        soundURL[0] = this.getClass().getResource("/Sounds/loopjazz.wav");
       // soundURL[1] = this.getClass().getResource("/Sounds/chicoteada.wav"); To-Do
      //  soundURL[2] = this.getClass().getResource("/Sounds/winner.wav");
    }

    public void Playfile(int i) {
        try {
            AudioInputStream AudioINP = AudioSystem.getAudioInputStream(soundURL[i]);
            DataLine.Info info = new DataLine.Info(Clip.class, AudioINP.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(AudioINP);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | NullPointerException e) {
            System.out.println("Invalid Sound!");
            e.printStackTrace();	

        }
    }

    public void play() {
        clip.start();

    }
    
    public void loop() {
    	clip.loop(Clip.LOOP_CONTINUOUSLY);
    	
    }

}
