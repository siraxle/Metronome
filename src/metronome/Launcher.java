package metronome;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Launcher extends Application {
  Scene scene;
  BorderPane root;
  int clickAmount = 0;
  Button button;
  Text bpmLabel;
  TextField bpmInput;
  boolean isPlaying;

  public static void main(String[] args) {
    launch(args);
  }

  EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
      int bpmValue = validatedBpmValue();
      if (isPlaying) {
        isPlaying = false;
        button.setText("STOP");
        startClicking(bpmValue);
      } else {
        isPlaying = true;
        button.setText("START");
        clickAmount = 0;
      }
    }
  };

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Metronome");
    setTextField();
    setBpmLable();
    setLaunchButton();
    this.scene = new Scene(root, 300, 300);
    primaryStage.setScene(this.scene);
    primaryStage.show();
  }

  private void setLaunchButton() {
    //Enter button to the right side of text field
    button = new Button();
    button.setText("START");
    this.root.setTop(button);
    this.root.setAlignment(button, Pos.CENTER);
    button.setOnAction(this.buttonHandler);
  }

  private void setBpmLable() {
    //Enter "BPM" text to the left side of text field
    bpmLabel = new Text("BPM");
    this.root.setLeft(bpmLabel);
    this.root.setAlignment(bpmLabel, Pos.CENTER_LEFT);
  }

  private void setTextField() {
    //Enter text field
    bpmInput = new TextField();
    bpmInput.setPromptText("Enter BPM");
    bpmInput.setFocusTraversable(false);
    this.root = new BorderPane();
    this.root.setCenter(bpmInput);
  }

  private int validatedBpmValue() {
    String bpmInputText = bpmInput.getText();
    int bpmValue = 0;
    if (bpmInputText.isEmpty()) {
      throw new IllegalArgumentException("empty value");
    }
      bpmValue = Integer.parseInt(bpmInputText);
    if (bpmValue <= 0) {
      throw new IllegalArgumentException("Negative value");
    }
    return bpmValue;
  }

  private void startClicking(int bpmValue){
    Thread clicking = new Thread(new Runnable() {
      @Override
      public void run() {
        File file = new File("assets/clickSimple.wav");
        Sound sound = new Sound(file);
        while (!isPlaying) {
          clickAmount++;
          try {
            long time = 60000 / bpmValue;
            System.out.println(time);
            try {
              TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            System.out.println(clickAmount);
            sound.play();
//                sound.playSound("assets/clickSimple.wav");
          } catch (NumberFormatException e) {
            System.out.println("Введите числовое значение BPM");
            bpmInput.clear();
            bpmInput.setPromptText("Введите числовое значение BPM");
            break;
          }
        }
      }
    });
    clicking.start();
  }

}
