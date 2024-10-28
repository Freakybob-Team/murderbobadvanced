import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.nio.file.Paths;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) {
       
        String videoPath = "cutscenes/edited/cutscene1.mp4";
        Media media = new Media(Paths.get(videoPath).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(1.0);
        
        
        MediaView mediaView = new MediaView(mediaPlayer);
        
    
        Button skipButton = new Button("Skip Cutscene");
        skipButton.setOnAction(e -> {
            mediaPlayer.stop();
           
            System.out.println("Cutscene skipped!");
            primaryStage.close(); 
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(mediaView, skipButton);
        StackPane.setAlignment(skipButton, javafx.geometry.Pos.BOTTOM_RIGHT);
        
        Scene scene = new Scene(root, 800, 600); 
        primaryStage.setScene(scene);
        primaryStage.setTitle("Murderbob - Cutscene");
        primaryStage.show();
        
        
        mediaPlayer.setOnEndOfMedia(() -> primaryStage.close());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setVisible(boolean b) {
    }
}
