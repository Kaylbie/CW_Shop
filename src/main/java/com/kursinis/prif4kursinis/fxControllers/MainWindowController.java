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

    }
    @FXML
    public Pane adminDashboardPane;
    @FXML
    public Pane orderPageButtons;
    @FXML
    public VBox mainWindowButtons;

    private void loadPane(String paneName){
        adminDashboardPane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("/com/kursinis/prif4kursinis/"+paneName+".fxml"));
        try {
            adminDashboardPane.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadOrderPane(ActionEvent actionEvent){
        loadPane("order-window");
        orderPageButtons.setVisible(true);
        mainWindowButtons.setVisible(false);

    }
    public void loadDashboardPane(ActionEvent actionEvent) {
        loadPane("admin-dashboard-window");
        mainWindowButtons.setVisible(true);
        orderPageButtons.setVisible(false);

    }
    private void loadDashboardPane(){
        loadPane("admin-dashboard-window");
        mainWindowButtons.setVisible(true);
        orderPageButtons.setVisible(false);
    }

    public void loadProductCataloguePane(ActionEvent actionEvent) {
        loadPane("product-catalogue-window");
    }

    public void orderMenuBackButton(MouseEvent mouseEvent) {
        loadDashboardPane();
    }
}
