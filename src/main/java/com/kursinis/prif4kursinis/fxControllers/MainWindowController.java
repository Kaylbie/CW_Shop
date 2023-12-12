package com.kursinis.prif4kursinis.fxControllers;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.fxControllers.createControllers.CreateProductController;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    public Pane adminDashboardPane;
    @FXML
    public Pane orderPageButtons;
    @FXML
    public VBox mainWindowButtons;
    @FXML
    public VBox productsPageButtons;
    @FXML
    public Label uidLabel;
    @FXML
    public Label nameSurnameLabel;
    private User currentUser;
    private EntityManagerFactory entityManagerFactory;
    private CustomHib customHib;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = StartGui.getEntityManagerFactory();
        loadDashboardPane();
    }
    private void loadPane(String paneName){
        adminDashboardPane.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("window/" + paneName + ".fxml"));

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
        loadPane("dashboardWindow");
        mainWindowButtons.setVisible(true);
        orderPageButtons.setVisible(false);
        productsPageButtons.setVisible(false);

    }
    private void loadDashboardPane(){
        loadPane("dashboardWindow");
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

    public void loadMessagesPane(ActionEvent actionEvent) {
        loadPane("messagesWindow");
    }

    public void loadSettingsPane(ActionEvent actionEvent) {
        loadPane("settingsWindow");
    }

    public void logOut(ActionEvent actionEvent) {
    }

    public void createNewProduct(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("create/createProduct.fxml"));
            Parent root = fxmlLoader.load();
            //CreateProductController createProductController = fxmlLoader.getController();
            //createProductController.setData(entityManagerFactory);
            // Create a new stage for the popup
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Set modality to block interaction with the main window
            stage.setTitle("Create New Product");

            // Create and set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Show the new stage
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace(); // Or handle the exception as you see fit
        }
    }

    public void setData(User currentUser) {
        this.currentUser = currentUser;
        //limitAccess();
        loadData();
    }
    private void loadData(){
        nameSurnameLabel.setText(currentUser.getName()+" "+currentUser.getSurname());
        uidLabel.setText("UID: "+currentUser.getId());
        setWindowData();
    }
    private EntityManagerFactory setWindowData(){
        return entityManagerFactory;
    }
}
