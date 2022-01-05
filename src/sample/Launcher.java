package sample;

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
      if (buttonName.equals("START")) {
        toExit = false;
        button.setText("STOP");
        Thread clicking = new Thread(new Runnable() {
          @Override
          public void run() {
            while (!toExit) {
              i++;
              long time =(long) 60000 / Long.parseLong(bpmInput.getText());
              try {
                Thread.sleep(time);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              System.out.println(i);
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

    //click on START button
//    button.setOnAction(new EventHandler<ActionEvent>() {
//      @Override
//      public void handle(ActionEvent event) {
//        boolean isContinue = true;
//        if (button.getText().equals("STOP")) {
//          button.setText("START");
//          System.out.println("pressed stop button");
//          isContinue = false;
//        } else {
//          button.setText("STOP");
//          System.out.println("pressed start button");
//          while (isContinue) {
//            i++;
//            try {
//              Thread.sleep(1000);
//            } catch (InterruptedException e) {
//              e.printStackTrace();
//            }
//            System.out.println(i);
//          }
//        }
//        System.out.println(isContinue);
//      }
//    });

    this.scene = new Scene(root, 300, 300);
    primaryStage.setScene(this.scene);
    primaryStage.show();
  }
}
