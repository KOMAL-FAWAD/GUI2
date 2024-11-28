package com.example.assignment4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;

public class LoginInterface extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Interface");

        // Banner Image
        Image bannerImage = new Image("file:C:/Users/world/Downloads/communicate.jpg");
        ImageView imageView = new ImageView(bannerImage);
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label titleLabel = new Label("LOGIN HERE");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("User Name:");
        TextField nameField = new TextField();
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);

        Button loginButton = new Button("Login");
        gridPane.add(loginButton, 1, 3);
        GridPane.setMargin(loginButton, new Insets(10, 0, 0, 0));

        Button exitButton = new Button("Exit");
        gridPane.add(exitButton, 2, 3);
        GridPane.setMargin(exitButton, new Insets(10, 0, 0, 0));

        VBox root = new VBox(20);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(imageView, gridPane);

        loginButton.setOnAction(event -> {
            String name = nameField.getText();
            String password = passwordField.getText();
            if (name.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all the fields!");
            } else {
                try {
                    // Check if username already exists
                    if (doesUsernameExist(name)) {
                        showAlert(Alert.AlertType.INFORMATION, "User", "Welcome! Hiba.");
                    } else {
                        saveToFile(name);
                        showAlert(Alert.AlertType.INFORMATION, "Login Successful",
                                "Data submitted successfully!\n\nUser name: " + name);
                    }
                } catch (IOException e) {
                    showAlert(Alert.AlertType.ERROR, "File Error", "Could not process the file!");
                }
            }
        });

        exitButton.setOnAction(event -> primaryStage.close()); 

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(headerText);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void saveToFile(String name) throws IOException {
        String filePath = "login_data.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("User Name: " + name + "\n");
            writer.write("-----------\n");
        }
    }

    private boolean doesUsernameExist(String username) throws IOException {
        String filePath = "login_data.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("User Name: ")) {
                    String existingUsername = line.substring(11);
                    if (existingUsername.equals(username)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        launch();
    }
}
