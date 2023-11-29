package com.kursinis.prif4kursinis.fxControllers;

import com.kursinis.prif4kursinis.StartGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public Pane adminDashboardPane;

    public void loadOrderPane(ActionEvent actionEvent){
        adminDashboardPane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("/com/kursinis/prif4kursinis/order-window.fxml"));
        try {
            adminDashboardPane.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateOrderBox(ActionEvent actionEvent) {



    }

    public void ordersButton(ActionEvent actionEvent) {
        //ordersScrollPane.setVisible(true);
        //refreshNodes();
    }

    public void dashboardButton(ActionEvent actionEvent) {
        //ordersScrollPane.setVisible(false);
    }
}
