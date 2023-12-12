package com.kursinis.prif4kursinis.fxControllers.windowControllers;
import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.hibernateControllers.GenericHib;
import com.kursinis.prif4kursinis.model.Product;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsWindowController implements Initializable, ProductUpdateCallback {

    @FXML
    public ScrollPane productsScrollPane;
    @FXML
    public VBox productsVBox;

    private EntityManagerFactory entityManagerFactory;
    private CustomHib customHib;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = StartGui.getEntityManagerFactory();
        refreshNodes();
    }
    @Override
    public void onProductUpdated() {
        refreshNodes();
    }
    private void refreshNodes() {
        productsVBox.getChildren().clear();

        // Use GenericHib to get product data
        GenericHib productHib = new GenericHib(entityManagerFactory);
        List<Product> productList = productHib.getAllRecords(Product.class);

        for (Product product : productList) {
            try {
                FXMLLoader loader = new FXMLLoader(StartGui.class.getResource("nodes/productNode.fxml"));
                Node node = loader.load();
                ProductNodeController controller = loader.getController();
                controller.setProductData(product);
                controller.setUpdateCallback(this);
                productsVBox.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        }
    }


    public void updateProductTabs(MouseEvent mouseEvent) {
        refreshNodes();
    }
}
