import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class UserInterfaceI extends Application {

    private TextArea textArea;
    private VBox root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Interface I");

        textArea = new TextArea();
        textArea.setPrefRowCount(10);
        textArea.setPrefColumnCount(40);
        textArea.setWrapText(true);
        textArea.setEditable(true);  // ✅ allow user input

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options");

        MenuItem printDateTime = new MenuItem("Print Date/Time");
        MenuItem saveToFile = new MenuItem("Save to log.txt");
        MenuItem changeColor = new MenuItem("Change Background to Green Hue");
        MenuItem exit = new MenuItem("Exit");

        menu.getItems().addAll(printDateTime, saveToFile, changeColor, exit);
        menuBar.getMenus().add(menu);

        root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);
        root.getChildren().addAll(menuBar, textArea);
        root.setBackground(new Background(
            new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)
        ));

        printDateTime.setOnAction(_ -> {
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            textArea.appendText("Current Date and Time: " + now + "\n");
        });

        saveToFile.setOnAction(_ -> {
            try (FileWriter writer = new FileWriter("log.txt", true)) {
                writer.write(textArea.getText());
                textArea.appendText("Saved to log.txt\n");
            } catch (IOException ex) {
                textArea.appendText("Error writing to log.txt\n");
            }
        });

        changeColor.setOnAction(_ -> {
            Color randomGreen = generateRandomGreen();
            BackgroundFill fill = new BackgroundFill(
                randomGreen, CornerRadii.EMPTY, Insets.EMPTY
            );
            root.setBackground(new Background(fill));
            String hex = colorToHex(randomGreen);
            changeColor.setText("Green Hue: " + hex);
        });

        exit.setOnAction(_ -> {
            primaryStage.close();
            System.exit(0);  // ✅ ensure full exit
        });

        Scene scene = new Scene(root, 500, 300);
        scene.setFill(Color.TRANSPARENT); // ✅ enable background updates

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Color generateRandomGreen() {
        Random rand = new Random();
        int g = 150 + rand.nextInt(106); // 150–255 green
        int r = rand.nextInt(100);       // 0–99 red
        int b = rand.nextInt(100);       // 0–99 blue
        return Color.rgb(r, g, b);
    }

    private String colorToHex(Color color) {
        return String.format("#%02X%02X%02X",
            (int)(color.getRed() * 255),
            (int)(color.getGreen() * 255),
            (int)(color.getBlue() * 255));
    }
}
