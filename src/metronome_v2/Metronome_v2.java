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
  Sound simpleSound = new Sound(new File("assets/clickSimple.wav"));
  Sound highSound = new Sound(new File("assets/clickHigh.wav"));
  int barIndex = 0;

  public Metronome_v2(int bpm) {
    this.bpm = bpm;
  }

  public void start(List<Integer> signature) { //передал лист заполненый в сильных долях цифрами - остальное null
    isPlaying = true;                            // [1, null, null, null]
    System.out.println("metronome is playing");
    timerTask = new TimerTask() {
      @Override
      public void run() {
        if (signature.size() == 0) {
          barIndex = barIndex % 4;
        } else {
          barIndex = barIndex % signature.size();
        }
        System.out.println(barIndex);
        if (signature.size() > 0 && signature.get(barIndex) != null) {
          highSound.play();
        } else {
          simpleSound.play();
        }
        barIndex++;
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
