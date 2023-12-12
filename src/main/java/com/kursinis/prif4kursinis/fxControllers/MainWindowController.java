package com.kursinis.prif4kursinis.fxControllers;

import com.kursinis.prif4kursinis.StartGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainWindowButtons.setVisible(true);
        orderPageButtons.setVisible(false);
        productsPageButtons.setVisible(false);
    }
    @FXML
    public Pane adminDashboardPane;
    @FXML
    public Pane orderPageButtons;
    @FXML
    public VBox mainWindowButtons;
    @FXML
    public VBox productsPageButtons;

    private void loadPane(String paneName){
        adminDashboardPane.getChildren().clear();
        //FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("com/kursinis/prif4kursinis/" + paneName + ".fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("src/main/resources/com/kursinis/prif4kursinis/usersWindow.fxml"));
        try {
            adminDashboardPane.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadOrderPane(ActionEvent actionEvent){
        loadPane("orderWindow");
        orderPageButtons.setVisible(true);
        mainWindowButtons.setVisible(false);
        productsPageButtons.setVisible(false);

    }
    public void loadDashboardPane(ActionEvent actionEvent) {
        //loadPane("admin-dashboard-window");
        mainWindowButtons.setVisible(true);
        orderPageButtons.setVisible(false);
        productsPageButtons.setVisible(false);

    }
    private void loadDashboardPane(){
        loadPane("admin-dashboard-window");
        mainWindowButtons.setVisible(true);
        orderPageButtons.setVisible(false);
        productsPageButtons.setVisible(false);
    }

    public void loadProductCataloguePane(ActionEvent actionEvent) {
        loadPane("productsWindow");
        productsPageButtons.setVisible(true);
        mainWindowButtons.setVisible(false);
        orderPageButtons.setVisible(false);
    }
    public void loadUsersPane(ActionEvent actionEvent) {
        loadPane("usersWindow");
        productsPageButtons.setVisible(true);
        mainWindowButtons.setVisible(false);
        orderPageButtons.setVisible(false);
    }

    public void orderMenuBackButton(MouseEvent mouseEvent) {
        loadDashboardPane();
    }
}
