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
        refreshCartNodes(null, null);
        setStatistics();
    }
    private void setStatistics(){
        GenericHib cartHib = new GenericHib(entityManagerFactory);
        List<Cart> cartList = cartHib.getAllRecords(Cart.class);
        CartStatistics statistics = new CartStatistics(cartList);
        pendingCountLabel.setText(String.valueOf(statistics.getPendingCount()));
        openCountLabel.setText(String.valueOf(statistics.getOpenCount()));
        closedCountLabel.setText(String.valueOf(statistics.getClosedCount()));
    }
    public void refreshCartNodes(String query, String status) {
        ordersVbox.getChildren().clear();
        GenericHib cartHib = new GenericHib(entityManagerFactory);
        List<Cart> cartList = cartHib.getAllRecords(Cart.class);

        for (Cart cart : cartList) {
            boolean matchesQuery = (query == null || isCartMatchingQuery(cart, query));
            boolean matchesStatus = (status == null || isCartMatchingStatus(cart, status));

            if (matchesQuery && matchesStatus) {
                try {
                    FXMLLoader loader = new FXMLLoader(StartGui.class.getResource("nodes/orderTabs.fxml"));
                    Node node = loader.load();
                    OrderTabsController controller = loader.getController();
                    controller.setCartData(cart, currentUser);
                    ordersVbox.getChildren().add(node);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isCartMatchingQuery(Cart cart, String query) {
        return String.valueOf(cart.getId()).startsWith(query);
    }

    private boolean isCartMatchingStatus(Cart cart, String status) {
        if ("All".equalsIgnoreCase(status)) {
            return true; // Ignore status filter if "All" is selected
        }
        return cart.getStatus().equalsIgnoreCase(status);
    }

    public void onSearchQueryChanged(String query) {
        refreshCartNodes(query, null);
    }

    public void updateOrderTabs(MouseEvent mouseEvent) {
        refreshCartNodes(null, null);
        setStatistics();
    }
    public void onStatusChanged(String query, String status) {
        refreshCartNodes(query, status);

    }
}
