package metronome_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import metronome.Sound;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class javaFxApplication extends Application {

  private Button launchButton;
  private TextField bpmInput;
  private int clickAmount = 0;
  private Metronome_v2 metronome;


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent panel = FXMLLoader.load(getClass().getResource("mainStage.fxml"));
    Scene scene = new Scene(panel, 400, 440);
    primaryStage.setTitle("Metronome");
    primaryStage.setScene(scene);
    primaryStage.show();
    initComponents(panel);
    setHandlers();
  }

  private void initComponents(Parent panel) {
    this.launchButton = (Button) panel.lookup("#launchButton");
    this.bpmInput = (TextField) panel.lookup("#bpmInput");
  }

  private void setHandlers() {
    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (metronome != null && metronome.isPlaying) {
          metronome.stop();
          launchButton.setText("START");
        }else {
          int bpm = 0;
          try {
            bpm = Integer.parseInt(bpmInput.getText());
          } catch (NumberFormatException exception) {
            System.out.println("Не цифровой BPM!!!");
            return;
          }
          metronome = new Metronome_v2(bpm);
          metronome.start();
          launchButton.setText("STOP");
        }
      }
    };
    launchButton.setOnAction(buttonHandler);
  }





}