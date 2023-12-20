package com.kursinis.prif4kursinis.fxControllers.userWindowControllers;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Cart;
import com.kursinis.prif4kursinis.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UserOrdersWindowController implements Initializable {
    @FXML
    private ListView<Cart> ordersListView;
    List<String> statuses = Arrays.asList("Pending", "Open", "Closed", "Cancelled");
    private CustomHib customHib;
    private User currentUser;
    @FXML
    ComboBox statusFilterComboBox;
    private EntityManagerFactory entityManagerFactory;
    @FXML
    private TableView<OrderViewModel> ordersTableView;
    @FXML
    private TableColumn<OrderViewModel, String> totalPriceColumn;
    @FXML
    private TableColumn<OrderViewModel, Number> quantityColumn;
    @FXML
    private TableColumn<OrderViewModel, String> managerNameColumn;
    @FXML
    private TableColumn<OrderViewModel, String> statusColumn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = StartGui.getEntityManagerFactory();
        customHib = new CustomHib(entityManagerFactory);
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        managerNameColumn.setCellValueFactory(new PropertyValueFactory<>("managerName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }
    private void loadOrders() {
        List<Cart> orders = customHib.findCartsByUserIdAndStatuses(currentUser.getId(), statuses); // Implement this method in CustomHib
        ObservableList<OrderViewModel> orderViewModels = FXCollections.observableArrayList();

        for (Cart cart : orders) {
            String managerName = cart.getResponsibleManager() != null ? cart.getResponsibleManager().getName() : "N/A";
            orderViewModels.add(new OrderViewModel(cart.getTotal(), cart.getItemCount(), managerName, cart.getStatus()));
        }

        // Set the ObservableList of OrderViewModels to the TableView
        ordersTableView.setItems(orderViewModels);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser=currentUser;
        loadOrders();
    }

    public void onFilter(ActionEvent actionEvent) {
        Object selectedStatusObject = statusFilterComboBox.getValue();
        //System.out.println(selectedStatusObject);
        String selectedStatus = null;

        if (selectedStatusObject instanceof String) {
            selectedStatus = (String) selectedStatusObject;

        }

        if (selectedStatus != null && !selectedStatus.isEmpty()) {
            List<Cart> filteredOrders = customHib.findCartsByUserIdAndStatus(currentUser.getId(), selectedStatus);
            ObservableList<OrderViewModel> orderViewModels = FXCollections.observableArrayList();
            for (Cart cart : filteredOrders) {
                String managerName = cart.getResponsibleManager() != null ? cart.getResponsibleManager().getName() : "N/A";
                orderViewModels.add(new OrderViewModel(cart.getTotal(), cart.getItemCount(), managerName, cart.getStatus()));
            }
            ordersTableView.getItems().clear();
            ordersTableView.setItems(orderViewModels);
        } else {
            // Handle the case where no status is selected or the selected status is invalid
            //showAlert("Filter Error", "Please select a valid status to filter.");
        }
    }

    public void onReset(ActionEvent actionEvent) {
        loadOrders();
    }
}
