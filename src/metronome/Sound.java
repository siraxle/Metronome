package metronome;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
  private boolean released = false;
  private Clip clip = null;
  private boolean playing = false;

  public Sound(File file) {
    try {
      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
      clip = AudioSystem.getClip();
      clip.open(stream);
      clip.addLineListener(new Listener());
      released = true;
    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc){
      exc.printStackTrace();
      released = false;
    }
  }

  public boolean isReleased() {
    return playing;
  }

  public boolean isPlaying() {
    return playing;
  }

  public void play(boolean breakOld) {
    if (released) {
      if (breakOld) {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
        playing = true;
      } else if (!isPlaying()) {
        clip.setFramePosition(0);
        clip.start();
        playing = true;
      }
    }
  }

  public void play() {
    play(true);
  }

  public void stop() {
    if (playing) {
      clip.stop();
    }
  }

  public void join() {
    if (!released) {
      return;
    }
    synchronized (clip) {
      try {
        while (playing) clip.wait();
      } catch (InterruptedException exception) {}
    }
  }

  public static Sound playSound(String sound) {
    File f = new File(sound);
    Sound snd = new Sound(f);
    snd.play();
    return snd;
  }

  private class Listener implements LineListener {

    @Override
    public void update(LineEvent event) {
      if (event.getType() == LineEvent.Type.STOP) {
        playing = false;
        synchronized (clip) {
          clip.notify();
        }
      }
    }
  }

}


