package com.kursinis.prif4kursinis.fxControllers.windowControllers;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Manager;
import com.kursinis.prif4kursinis.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.util.Callback;

public class UsersWindowController implements Initializable {


    @FXML private TableView<User> usersTableView;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> loginColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> surnameColumn;
    @FXML private TableColumn<User, String> typeColumn;
    @FXML
    private TableColumn<User, Void> editColumn;
    private EntityManagerFactory entityManagerFactory;
    private CustomHib customHib;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = StartGui.getEntityManagerFactory();
        customHib = new CustomHib(entityManagerFactory);
        initializeTable();
        loadUsers();

    }
    private void initializeTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        typeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue() instanceof Manager ? "Manager" : "Customer"
        ));
        setupEditColumn();
        // Set up other columns as needed
    }
    private void setupEditColumn() {
        editColumn.setCellFactory(new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {
                    private final Button editBtn = new Button("Edit");

                    {
                        editBtn.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            // Handle the edit action
                            editUser(user);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editBtn);
                        }
                    }
                };
            }
        });
    }
    private void editUser(User user) {
        // Logic to edit the user
        System.out.println("Editing user: " + user.getId()); // Replace with actual editing logic
    }
    private void loadUsers() {
        List<User> users = customHib.getAllRecords(User.class);
        usersTableView.getItems().setAll(users);
    }
    @FXML
    public ScrollPane productsScrollPane;
    @FXML
    public VBox productsVBox;



}
