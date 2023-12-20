package com.kursinis.prif4kursinis.fxControllers.windowControllers;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.fxControllers.MainWindowController;
import com.kursinis.prif4kursinis.hibernateControllers.GenericHib;
import com.kursinis.prif4kursinis.model.Cart;
import com.kursinis.prif4kursinis.model.Product;
import com.kursinis.prif4kursinis.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderWindowController implements Initializable  {



    @FXML
    public VBox ordersVbox;
    @FXML
    public ScrollPane ordersScrollPane;
    @FXML
    private Label pendingCountLabel;
    @FXML
    private Label openCountLabel;
    @FXML
    private Label closedCountLabel;
    private User currentUser;
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = StartGui.getEntityManagerFactory();
    }
    public void setOrdersData(User currentUser) {
        this.currentUser = currentUser;
        refreshCartNodes(null);
    }
    private void refreshCartNodes(String query) {
        ordersVbox.getChildren().clear(); // Assuming ordersVBox is the VBox where you want to display the cart nodes
        GenericHib cartHib = new GenericHib(entityManagerFactory);
        List<Cart> cartList = cartHib.getAllRecords(Cart.class);
        CartStatistics statistics = new CartStatistics(cartList);
        pendingCountLabel.setText(String.valueOf(statistics.getPendingCount()));
        openCountLabel.setText(String.valueOf(statistics.getOpenCount()));
        closedCountLabel.setText(String.valueOf(statistics.getClosedCount()));
        for (Cart cart : cartList) {
            if (query == null || isCartMatchingQuery(cart, query)) { // Define your query logic in isCartMatchingQuery
                try {
                    FXMLLoader loader = new FXMLLoader(StartGui.class.getResource("nodes/orderTabs.fxml"));
                    Node node = loader.load();
                    OrderTabsController controller = loader.getController(); // Assuming the controller class name is OrderTabsController
                    controller.setCartData(cart, currentUser); // Assuming setCartData is a method in your OrderTabsController
                    //controller.setUpdateCallback(this); // If you have an update callback
                    ordersVbox.getChildren().add(node);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isCartMatchingQuery(Cart cart, String query) {
        // Implement the logic to check if the cart should be included based on the query
        // For example, you might want to check the status or total of the cart
        return cart.getStatus().toLowerCase().contains(query.toLowerCase());
    }


    public void updateOrderTabs(MouseEvent mouseEvent) {
        refreshCartNodes(null);
    }
    private void changeCount(){

    }
}
