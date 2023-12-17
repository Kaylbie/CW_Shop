package com.kursinis.prif4kursinis.fxControllers.createControllers;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.hibernateControllers.UserHib;
import com.kursinis.prif4kursinis.model.Customer;
import com.kursinis.prif4kursinis.model.Manager;
import com.kursinis.prif4kursinis.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    private UserHib userHib;
    private User editableUser;
    private CustomHib customHib;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private String userType;
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
            // Delete the old user
            entityManager.remove(editableUser);

            // Create a new user of the selected type
            User newUser;
            if (selectedType.equals("Customer")) {
                newUser = new Customer();
            } else { // Assuming the other type is Manager
                newUser = new Manager();
            }
            // Copy common fields from editableUser to newUser
            newUser.setLogin(usernameField.getText());
            newUser.setPassword(passwordField.getText()); // Handle with care
            newUser.setName(nameField.getText());
            newUser.setSurname(surnameField.getText());
            if(editableUser instanceof Manager) {
                ((Manager) editableUser).setAdmin(isAdmin);
            }

            // Persist the new user
            entityManager.persist(newUser);
        } else {
            // If the type hasn't changed, just update the existing user
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

        // Close the window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
