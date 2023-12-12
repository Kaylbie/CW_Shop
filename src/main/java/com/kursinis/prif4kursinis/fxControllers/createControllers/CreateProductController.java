package com.kursinis.prif4kursinis.fxControllers.createControllers;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.fxControllers.windowControllers.ProductUpdateCallback;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.hibernateControllers.UserHib;
import com.kursinis.prif4kursinis.model.SimpleProduct;
import com.kursinis.prif4kursinis.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class CreateProductController implements Initializable {

    @FXML
    public Label imagePathLabel;
    @FXML
    public TextField productNameField;
    @FXML
    public TextField productCodeField;
    @FXML
    public TextField productPriceField;
    @FXML
    public TextArea productDescriptionField;

    private ProductUpdateCallback productUpdateCallback;
    @FXML
    private String selectedImagePath;
    private EntityManagerFactory entityManagerFactory;
    private CustomHib customHib;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = StartGui.getEntityManagerFactory();
        customHib = new CustomHib(entityManagerFactory);
    }
    public void setMainController(ProductUpdateCallback productUpdateCallback) {
        this.productUpdateCallback = productUpdateCallback;
    }
    public void chooseImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();
            imagePathLabel.setText(selectedFile.getName());

        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void createProduct(ActionEvent actionEvent) {
        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            try {
                String destPath = "src/main/resources/com/kursinis/prif4kursinis/images/" + new File(selectedImagePath).getName();
                Files.copy(Paths.get(selectedImagePath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
                customHib.create(new SimpleProduct(productNameField.getText(), productCodeField.getText(), productPriceField.getText(), productDescriptionField.getText(), new File(selectedImagePath).getName()));

                // Now, create your product here using the destPath for the image
                // For example: Product newProduct = new Product(..., destPath);
                showAlert("Success", "Product created successfully.");
                // Save the newProduct to your database or wherever it needs to be stored
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to save the image.");
            }
        } else {
            // Handle the case where no image was selected
        }
    }


}
