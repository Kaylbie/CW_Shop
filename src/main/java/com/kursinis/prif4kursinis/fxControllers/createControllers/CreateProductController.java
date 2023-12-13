package com.kursinis.prif4kursinis.fxControllers.createControllers;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.fxControllers.windowControllers.ProductUpdateCallback;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.hibernateControllers.UserHib;
import com.kursinis.prif4kursinis.model.Product;
import com.kursinis.prif4kursinis.model.SimpleProduct;
import com.kursinis.prif4kursinis.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    @FXML private Label titleLabel;
    @FXML private Button createProductButton;

    private Product productToEdit;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = StartGui.getEntityManagerFactory();
        customHib = new CustomHib(entityManagerFactory);
    }
    public void setData(EntityManagerFactory entityManagerFactory, Product productToEdit) {
        this.entityManagerFactory = entityManagerFactory;
        this.productToEdit = productToEdit;
        if (productToEdit != null) {
            loadProductData(productToEdit);
        }
    }
    private void loadProductData(Product product) {
        titleLabel.setText("Edit Product");
        createProductButton.setText("Save Product");
        productNameField.setText(product.getTitle());
        productCodeField.setText(product.getCode()); // Adjust these getters to your Product class
        productPriceField.setText(String.valueOf(product.getPrice()));
        productDescriptionField.setText(product.getDescription());
        imagePathLabel.setText(product.getPhotoName());
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
        try {
            String imagePath = selectedImagePath != null && !selectedImagePath.isEmpty() ? new File(selectedImagePath).getName() : null;

            if (productToEdit == null) {
                // Creating a new product
                String destPath = "src/main/resources/com/kursinis/prif4kursinis/images/" + imagePath;
                if (imagePath != null) {
                    Files.copy(Paths.get(selectedImagePath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
                }
                SimpleProduct newProduct = new SimpleProduct(productNameField.getText(), productCodeField.getText(), productPriceField.getText(), productDescriptionField.getText(), imagePath);
                customHib.create(newProduct);
                showAlert("Success", "Product created successfully.");
            } else {
                // Editing an existing product
                if (imagePath != null) {
                    String destPath = "src/main/resources/com/kursinis/prif4kursinis/images/" + imagePath;
                    Files.copy(Paths.get(selectedImagePath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
                    productToEdit.setPhotoName(imagePath);
                }
                productToEdit.setTitle(productNameField.getText());
                productToEdit.setCode(productCodeField.getText());
                productToEdit.setPrice(productPriceField.getText());
                productToEdit.setDescription(productDescriptionField.getText());
                customHib.update(productToEdit);
                showAlert("Success", "Product updated successfully.");
            }

            // Close the stage
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save the image.");
        }
    }


}
