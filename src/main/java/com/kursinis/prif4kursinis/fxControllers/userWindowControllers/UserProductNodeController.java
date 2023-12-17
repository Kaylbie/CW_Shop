package com.kursinis.prif4kursinis.fxControllers.userWindowControllers;


import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.fxControllers.createControllers.CreateProductController;
import com.kursinis.prif4kursinis.fxControllers.windowControllers.ProductUpdateCallback;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Product;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserProductNodeController implements Initializable {
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productCodeLabel;
    @FXML
    private Label productPriceLabel;
    @FXML
    private ImageView productImageView;
    @FXML
    private Button deleteButton;
    @FXML
    private Button visibilityButton;
    private ProductUpdateCallback updateCallback;
    private Product product;
    private EntityManagerFactory entityManagerFactory;
    private Product editableProduct;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = StartGui.getEntityManagerFactory();

    }
    public void setUpdateCallback(ProductUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }
    public void setProductData(Product product) {

        this.product=product;

        productNameLabel.setText(product.getTitle());
        productCodeLabel.setText(product.getCode()); // Replace getCode() with your actual method
        productPriceLabel.setText("Price: $" + product.getPrice()); // Replace getPrice() with your actual method
        String imagePath = "/com/kursinis/prif4kursinis/images/" + product.getPhotoName(); // Replace getImageName() with your actual method
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            productImageView.setImage(image);
        }
    }

    public void addToCart(ActionEvent actionEvent) {
    }
}
