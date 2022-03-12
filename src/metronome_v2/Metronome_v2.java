package metronome_v2;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Metronome_v2 {
  public boolean isPlaying;
  public int bpm = 60;
  Timer timer;
  TimerTask timerTask;
  Integer signature = javaFxApplication.checkBoxArea.getChildren().size();
  Sound simpleSound = new Sound(new File("assets/clickSimple.wav"));
  Sound highSound = new Sound(new File("assets/clickHigh.wav"));

  public Metronome_v2(int bpm) {
    this.bpm = bpm;
  }

  public void start(List<Integer> strongBeats) {
    isPlaying = true;
    System.out.println("metronome is playing");
    timerTask = new TimerTask() {
      @Override
      public void run() {
        if (strongBeats.size() > 0) {

        } else {
          System.out.println("tick");
          simpleSound.play();
        }
      }
    };
    timer = new Timer();
    int period = 60000 / bpm;
    timer.schedule(timerTask, 0, period);
  }

  public void stop() {
    isPlaying = false;
    System.out.println("metronome is not playing");
    timer.cancel();
  }

}
