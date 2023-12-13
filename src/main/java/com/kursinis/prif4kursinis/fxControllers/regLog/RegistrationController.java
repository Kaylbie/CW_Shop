package com.kursinis.prif4kursinis.fxControllers.regLog;

import com.kursinis.prif4kursinis.hibernateControllers.UserHib;
import com.kursinis.prif4kursinis.model.Customer;
import com.kursinis.prif4kursinis.model.Manager;
import com.kursinis.prif4kursinis.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public RadioButton customerCheckbox;
    @FXML
    public ToggleGroup userType;
    @FXML
    public RadioButton managerCheckbox;
    @FXML
    public TextField addressField;
    @FXML
    public TextField cardNoField;
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField employeeIdField;
    @FXML
    public TextField medCertificateField;
    @FXML
    public DatePicker employmentDateField;
    @FXML
    public CheckBox isAdminCheck;

    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;

    public void setData(EntityManagerFactory entityManagerFactory, boolean showManagerFields) {
        this.entityManagerFactory = entityManagerFactory;
        disableFields(showManagerFields);
    }

    public void setData(EntityManagerFactory entityManagerFactory, Manager manager) {
        this.entityManagerFactory = entityManagerFactory;
        toggleFields(customerCheckbox.isSelected(), manager);
    }

    private void disableFields(boolean showManagerFields) {
        if (!showManagerFields) {
//            employeeIdField.setVisible(false);
//            medCertificateField.setVisible(false);
//            employmentDateField.setVisible(false);
//            isAdminCheck.setVisible(false);
//            managerCheckbox.setVisible(false);
        }
    }

    private void toggleFields(boolean isManager, Manager manager) {
//        if (isManager) {
//            addressField.setDisable(true);
//            cardNoField.setDisable(true);
//            employeeIdField.setDisable(false);
//            medCertificateField.setDisable(false);
//            employmentDateField.setDisable(false);
//            if (manager.isAdmin()) isAdminCheck.setDisable(false);
//        } else {
//            addressField.setDisable(false);
//            cardNoField.setDisable(false);
//            employeeIdField.setDisable(true);
//            medCertificateField.setDisable(true);
//            employmentDateField.setDisable(true);
//            isAdminCheck.setDisable(true);
//        }
    }


    public void createUser() {
        userHib = new UserHib(entityManagerFactory);
        User user = new Customer(loginField.getText(), passwordField.getText(), null, nameField.getText(), surnameField.getText(), null);
        userHib.createUser(user);
//        if (customerCheckbox.isSelected()) {
//            //User user = new Customer(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), addressField.getText(), cardNoField.getText());
//            User user = new Customer(loginField.getText(), passwordField.getText(), null, nameField.getText(), surnameField.getText(), null, null);
//            userHib.createUser(user);
//        } else {
//
//        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Arba cia kazka su laukais darau
    }
}
