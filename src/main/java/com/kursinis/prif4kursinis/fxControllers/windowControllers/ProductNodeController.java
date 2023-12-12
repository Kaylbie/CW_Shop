package com.kursinis.prif4kursinis.fxControllers.windowControllers;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
public class ProductNodeController {
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
    private ProductUpdateCallback updateCallback;
    private Product product;

    public void setUpdateCallback(ProductUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }
    public void setProductData(Product product) {
        this.product=product;
        productNameLabel.setText(product.getTitle());
        productCodeLabel.setText(product.getCode()); // Replace getCode() with your actual method
        productPriceLabel.setText("Price: $" + product.getPrice()); // Replace getPrice() with your actual method
        // Set the image for productImageView if needed
        String imagePath = "/com/kursinis/prif4kursinis/images/" + product.getPhotoName(); // Replace getImageName() with your actual method
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            productImageView.setImage(image);
        }
    }

    public void handleDeleteAction(ActionEvent actionEvent) {
        CustomHib customHib = new CustomHib(StartGui.getEntityManagerFactory());
        customHib.delete(Product.class, product.getId());
        if (updateCallback != null) {
            updateCallback.onProductUpdated();
        }
    }

    public void handleVisibilityAction(ActionEvent actionEvent) {
    }

    public void handleEditAction(ActionEvent actionEvent) {
    }
}
