package com.kursinis.cwshop.fxControllers.userWindowControllers;

import com.kursinis.cwshop.StartGui;
import com.kursinis.cwshop.fxControllers.windowControllers.ProductUpdateCallback;
import com.kursinis.cwshop.hibernateControllers.GenericHib;
import com.kursinis.cwshop.model.Product;
import com.kursinis.cwshop.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserCatalogueWindowController implements Initializable, ProductUpdateCallback {

    private EntityManagerFactory entityManagerFactory;
    @FXML
    private VBox productsVBox;
    private User currentUser;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = StartGui.getEntityManagerFactory();


    }
    @Override
    public void onProductUpdated() {
        refreshNodes(null);
    }
    public void updateProductTabs(MouseEvent mouseEvent) {
        refreshNodes(null);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        refreshNodes(null);
    }
    private void refreshNodes(String query) {
        System.out.println(currentUser.getId());
        productsVBox.getChildren().clear();
        GenericHib productHib = new GenericHib(entityManagerFactory);
        List<Product> productList = productHib.getAllRecords(Product.class);

        for (Product product : productList) {
            if (query == null || product.getTitle().toLowerCase().contains(query.toLowerCase())) {
                try {
                    if(product.isVisible()){
                        FXMLLoader loader = new FXMLLoader(StartGui.class.getResource("nodes/customerProductNode.fxml"));
                        Node node = loader.load();
                        UserProductNodeController controller = loader.getController();
                        controller.setProductData(product);
                        controller.setUpdateCallback(this);
                        productsVBox.getChildren().add(node);
                        UserProductNodeController productNodeController = loader.getController();
                        productNodeController.setCurrentUser(this.currentUser);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
