import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.nio.file.Paths;

public class Game extends Application {
    private MediaPlayer backgroundMusicPlayer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        showHomeScreen(primaryStage);
    }

    private void showHomeScreen(Stage primaryStage) {
        StackPane homeRoot = new StackPane();
        homeRoot.setStyle("-fx-background-color: #f0f0f0;");
        Label welcomeLabel = new Label("Welcome to MurderbobADVANCED!");
        welcomeLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #333;");
        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
        startButton.setOnAction(e -> showCutscene(primaryStage));
        VBox homeLayout = new VBox(20, welcomeLabel, startButton);
        homeLayout.setAlignment(javafx.geometry.Pos.CENTER);
        homeLayout.setStyle("-fx-padding: 20px;");
        homeRoot.getChildren().add(homeLayout);
        Scene homeScene = new Scene(homeRoot, 800, 600);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("MurderbobADVANCED - Home");
        primaryStage.show();
    }

    private void showCutscene(Stage primaryStage) {
        String videoPath = "/cutscenes/edited/cutscene1.mp4";
        Media media = new Media(Paths.get(videoPath).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(1.0);
        MediaView mediaView = new MediaView(mediaPlayer);
        Button skipButton = new Button("Skip Cutscene");
        skipButton.setOnAction(e -> {
            mediaPlayer.stop();
            System.out.println("Cutscene skipped!");
            fadeOutAndShowGameScene(primaryStage);
        });
        StackPane cutsceneRoot = new StackPane();
        cutsceneRoot.getChildren().addAll(mediaView, skipButton);
        StackPane.setAlignment(skipButton, javafx.geometry.Pos.BOTTOM_RIGHT);
        Scene cutsceneScene = new Scene(cutsceneRoot, 800, 600);
        primaryStage.setScene(cutsceneScene);
        primaryStage.setTitle("MurderbobADVANCED - Cutscene");
        mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("Cutscene ended!");
            fadeOutAndShowGameScene(primaryStage);
        });
    }

    private void fadeOutAndShowGameScene(Stage primaryStage) {
        StackPane root = (StackPane) primaryStage.getScene().getRoot();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(e -> {
            stopBackgroundMusic();
            showGameScene(primaryStage);
        });
        fadeTransition.play();
    }

    private void showGameScene(Stage primaryStage) {
        Image backgroundImage = new Image(Paths.get("/Assets/Room.png").toUri().toString());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(800);
        backgroundImageView.setFitHeight(600);
        backgroundImageView.setPreserveRatio(false);
        Image characterImage = new Image(Paths.get("/Assets/Sad_Spongebob_sprite.png").toUri().toString());
        ImageView characterImageView = new ImageView(characterImage);
        characterImageView.setFitWidth(300);
        characterImageView.setFitHeight(200);
        characterImageView.setPreserveRatio(true);
        characterImageView.setOnMouseClicked(event -> {
            System.out.println("Character clicked!");
        });
        Button lookOutsideButton = new Button("Look Outside");
        lookOutsideButton.setOnAction(e -> showLookOutsideScene(primaryStage));
        Button goToLivingRoomButton = new Button("Go to Living room");
        goToLivingRoomButton.setOnAction(e -> showLivingRoomScene(primaryStage));
        VBox bottomButtonBox = new VBox(10);
        bottomButtonBox.getChildren().addAll(lookOutsideButton, goToLivingRoomButton);
        bottomButtonBox.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        StackPane.setAlignment(bottomButtonBox, javafx.geometry.Pos.BOTTOM_CENTER);
        StackPane.setMargin(bottomButtonBox, new javafx.geometry.Insets(10));
        characterImageView.setTranslateX(10);
        characterImageView.setTranslateY((primaryStage.getHeight() - characterImageView.getFitHeight()) / 2 - 280);
        StackPane gameRoot = new StackPane();
        gameRoot.getChildren().addAll(backgroundImageView, characterImageView, bottomButtonBox);
        Scene gameScene = new Scene(gameRoot, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("MurderbobADVANCED - Game");
        playBackgroundMusic();
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImageView.setFitWidth(newVal.doubleValue());
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImageView.setFitHeight(newVal.doubleValue());
            characterImageView.setTranslateY((newVal.doubleValue() - characterImageView.getFitHeight()) / 2 - 280);
        });
    }

    private void showLivingRoomScene(Stage primaryStage) {
        StackPane livingRoomRoot = new StackPane();
        Image livingRoomImage = new Image(Paths.get("/Assets/Livingroom.png").toUri().toString());
        ImageView livingRoomImageView = new ImageView(livingRoomImage);
        livingRoomImageView.setFitWidth(primaryStage.getWidth());
        livingRoomImageView.setFitHeight(primaryStage.getHeight());
        livingRoomImageView.setPreserveRatio(false);
        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> showGameScene(primaryStage));
        Button readNoteButton = new Button("Read Note");
        Rectangle noteRectangle = new Rectangle(400, 200, Color.WHITE);
        noteRectangle.setVisible(false);
        Label noteLabel = new Label("Help, I don't like java anymore - 5quirre1");
        noteLabel.setStyle("-fx-font-size: 20px;");
        noteLabel.setVisible(false);
        readNoteButton.setOnAction(e -> {
            System.out.println("Reading the note...");
            noteRectangle.setVisible(true);
            noteLabel.setVisible(true);
        });
        VBox readNoteBox = new VBox(10);
        readNoteBox.getChildren().add(readNoteButton);
        readNoteBox.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(readNoteBox, javafx.geometry.Pos.BOTTOM_RIGHT);
        StackPane.setMargin(readNoteBox, new javafx.geometry.Insets(0, 90, 220, 0));
        VBox goBackBox = new VBox(10);
        goBackBox.getChildren().add(goBackButton);
        goBackBox.setAlignment(javafx.geometry.Pos.BOTTOM_LEFT);
        StackPane.setAlignment(goBackBox, javafx.geometry.Pos.BOTTOM_LEFT);
        StackPane.setMargin(goBackBox, new javafx.geometry.Insets(0, 0, 0, 320));
        noteRectangle.setOnMouseClicked(event -> {
            noteRectangle.setVisible(false);
            noteLabel.setVisible(false);
        });
        livingRoomRoot.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE:
                    if (noteRectangle.isVisible()) {
                        noteRectangle.setVisible(false);
                        noteLabel.setVisible(false);
                        System.out.println("Note closed with Escape key!");
                    }
                    break;
                default:
                    break;
            }
        });
        livingRoomRoot.getChildren().addAll(livingRoomImageView, noteRectangle, noteLabel, readNoteBox, goBackBox);
        Scene livingRoomScene = new Scene(livingRoomRoot, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(livingRoomScene);
        primaryStage.setTitle("MurderbobADVANCED - Living Room");
        playBackgroundMusic();
    }

    private void playBackgroundMusic() {
        if (backgroundMusicPlayer == null) {
            Media music = new Media(Paths.get("/Assets/sadmusic1.wav").toUri().toString());
            backgroundMusicPlayer = new MediaPlayer(music);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusicPlayer.setVolume(0.5);
            backgroundMusicPlayer.play();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.dispose();
            backgroundMusicPlayer = null;
        }
    }

    private void showLookOutsideScene(Stage primaryStage) {
        StackPane lookOutsideRoot = new StackPane();
        Image scaryImage = new Image(Paths.get("/Assets/Scary.png").toUri().toString());
        ImageView scaryImageView = new ImageView(scaryImage);
        scaryImageView.setFitWidth(primaryStage.getWidth());
        scaryImageView.setFitHeight(primaryStage.getHeight());
        scaryImageView.setPreserveRatio(false);
        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> showGameScene(primaryStage));
        VBox goBackBox = new VBox(10);
        goBackBox.getChildren().add(goBackButton);
        goBackBox.setAlignment(javafx.geometry.Pos.BOTTOM_LEFT);
        StackPane.setAlignment(goBackBox, javafx.geometry.Pos.BOTTOM_LEFT);
        StackPane.setMargin(goBackBox, new javafx.geometry.Insets(0, 0, 0, 320));
        lookOutsideRoot.getChildren().addAll(scaryImageView, goBackBox);
        Scene lookOutsideScene = new Scene(lookOutsideRoot, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(lookOutsideScene);
        primaryStage.setTitle("MurderbobADVANCED - Look Outside");
    }

    public static void main(String[] args) {
        launch(args);
    }
}


