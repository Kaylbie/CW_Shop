package com.kursinis.cwshop.fxControllers.createControllers;

import com.kursinis.cwshop.fxControllers.JavaFxCustomUtils;
import com.kursinis.cwshop.hibernateControllers.CustomHib;
import com.kursinis.cwshop.hibernateControllers.UserHib;
import com.kursinis.cwshop.model.Customer;
import com.kursinis.cwshop.model.Manager;
import com.kursinis.cwshop.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditUserWindowController implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private ChoiceBox<String> userTypeChoiceBox;
    @FXML private ChoiceBox<String> isAdminChoiceBox;
    private User editableUser;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //entityManagerFactory = StartGui.getEntityManagerFactory();
        //customHib = new CustomHib(entityManagerFactory);
    }
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void initializeWithUser(User user) {
        this.editableUser = entityManager.find(User.class, user.getId());
        if (editableUser != null) {
            usernameField.setText(editableUser.getLogin());
            passwordField.setText(editableUser.getPassword());
            nameField.setText(editableUser.getName());
            surnameField.setText(editableUser.getSurname());
            if (user instanceof Customer) {
                userTypeChoiceBox.setValue("Customer");
                isAdminChoiceBox.setVisible(false);
            } else if (user instanceof Manager) {
                userTypeChoiceBox.setValue("Manager");
                isAdminChoiceBox.setVisible(true);
                if(((Manager) editableUser).isAdmin()){
                    isAdminChoiceBox.setValue("True");
                }
                else{
                    isAdminChoiceBox.setValue("False");
                }
            }
        }
    }

    @FXML
    private void saveUser(ActionEvent event) {
        entityManager.getTransaction().begin();

        String selectedType = userTypeChoiceBox.getValue();
        boolean isAdmin = "True".equals(isAdminChoiceBox.getValue());
        boolean typeChanged = !(editableUser.getClass().getSimpleName().equals(selectedType));

        if (typeChanged) {
            entityManager.remove(editableUser);

            User newUser;
            if (selectedType.equals("Customer")) {
                newUser = new Customer();
            } else {
                newUser = new Manager();
            }
            newUser.setLogin(usernameField.getText());
            newUser.setPassword(passwordField.getText());
            newUser.setName(nameField.getText());
            newUser.setSurname(surnameField.getText());
            if(editableUser instanceof Manager) {
                ((Manager) editableUser).setAdmin(isAdmin);
            }

            entityManager.persist(newUser);
        } else {
            editableUser.setLogin(usernameField.getText());
            editableUser.setPassword(passwordField.getText());
            editableUser.setName(nameField.getText());
            editableUser.setSurname(surnameField.getText());
            if(editableUser instanceof Manager) {
                ((Manager) editableUser).setAdmin(isAdmin);
            }

            entityManager.merge(editableUser);
        }

        entityManager.getTransaction().commit();
        JavaFxCustomUtils customUtils = new JavaFxCustomUtils();
        customUtils.generateAlert(Alert.AlertType.INFORMATION, "User saved", "User saved", "User saved successfully");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


}
