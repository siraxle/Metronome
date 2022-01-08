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
  int i = 0;
  boolean toExit;
  Button button;
  Text bpmLabel;
  TextField bpmInput;

  public static void main(String[] args) {
    launch(args);
  }

  EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
      String[] strings = event.getTarget().toString().split("'");
      String buttonName = strings[1];
      System.out.println(buttonName);
      if (buttonName.equals("START") && !bpmInput.getText().isEmpty()) {
        toExit = false;
        try {
          Integer.parseInt(bpmInput.getText());
          button.setText("STOP");
        } catch (NumberFormatException e) {
          System.out.println("BPM не число");
        }
        Thread clicking = new Thread(new Runnable() {
          @Override
          public void run() {
            File file = new File("/Users/efremov/Documents/java_projects/metronome/assets/clickSimple.wav");
            Sound sound = new Sound(file);
            while (!toExit) {
              i++;
              try {
                long time = 60000 / Long.parseLong(bpmInput.getText());
                System.out.println(time);
                try {
                  TimeUnit.MILLISECONDS.sleep(time);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                System.out.println(i);
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
      } else {
        button.setText("START");
        toExit = true;
        i = 0;
      }
    }
  };

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Metronome");

    //Enter text field
    bpmInput = new TextField();
    bpmInput.setPromptText("Enter BPM");
    bpmInput.setFocusTraversable(false);
    this.root = new BorderPane();
    this.root.setCenter(bpmInput);

    //Enter "BPM" text to the left side of text field
    bpmLabel = new Text("BPM");
    this.root.setLeft(bpmLabel);
    this.root.setAlignment(bpmLabel, Pos.CENTER_LEFT);

    //Enter button to the right side of text field
    button = new Button();
    button.setText("START");
    this.root.setTop(button);
    this.root.setAlignment(button, Pos.CENTER);
    button.setOnAction(this.handler);

    this.scene = new Scene(root, 300, 300);
    primaryStage.setScene(this.scene);
    primaryStage.show();
  }
}
