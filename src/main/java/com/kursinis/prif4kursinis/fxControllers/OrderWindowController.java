package com.kursinis.prif4kursinis.fxControllers;

import com.kursinis.prif4kursinis.StartGui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderWindowController implements Initializable {



    @FXML
    public VBox ordersVbox;
    @FXML
    public ScrollPane ordersScrollPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshNodes();
    }
    private void refreshNodes() {
        ordersVbox.getChildren().clear();
        Node[] nodes = new Node[10];
        for(int i = 0; i < nodes.length; i++) {
            try {
                nodes[i] = FXMLLoader.load(StartGui.class.getResource("/com/kursinis/prif4kursinis/orderTabs.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ordersVbox.getChildren().add(nodes[i]);
        }

    }

    public void updateOrderTabs(MouseEvent mouseEvent) {
        refreshNodes();
    }
}
