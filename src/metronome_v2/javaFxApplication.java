package metronome_v2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class javaFxApplication extends Application {

  private Button launchButton;
  private TextField bpmInput;
  private int clickAmount = 0;
  private Metronome_v2 metronome;
  private ChoiceBox<String> choiceBoxes;
  private HBox checkBoxArea;

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
    setButtonHandlers();
    setChoiceBoxes();
    setChoiceHandler();
  }

  private void initComponents(Parent panel) {
    this.launchButton = (Button) panel.lookup("#launchButton");
    this.bpmInput = (TextField) panel.lookup("#bpmInput");
    this.choiceBoxes = (ChoiceBox<String>) panel.lookup("#time_signature");
    this.checkBoxArea = (HBox) panel.lookup("#strongBeatsArea");
  }

  private void setButtonHandlers() {
    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (metronome != null && metronome.isPlaying) {
          metronome.stop();
          launchButton.setText("START");
        } else {
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

  private void setChoiceHandler() {
    EventHandler<ActionEvent> choiceHandler = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        printCheckBoxArea();
      }
    };
    choiceBoxes.setOnAction(choiceHandler);
  }

  private void setChoiceBoxes() {
    String[] signatures = {"3/4", "4/4", "5/4", "7/4", "9/4"};
    choiceBoxes.setValue("4/4");
    choiceBoxes.getItems().addAll(signatures);
  }

  private void printCheckBoxArea() {
    //signature will never be more than 10
    String amountCheckBoxes = choiceBoxes.getValue().substring(0, 1);
    System.out.println(amountCheckBoxes);
  }

}