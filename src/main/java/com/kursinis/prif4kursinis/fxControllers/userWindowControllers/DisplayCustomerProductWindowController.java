package com.kursinis.prif4kursinis.fxControllers.userWindowControllers;
import java.util.List;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.fxControllers.windowControllers.ProductUpdateCallback;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Comment;
import com.kursinis.prif4kursinis.model.Product;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DisplayCustomerProductWindowController implements Initializable {

    @FXML private Label productNameLabel, productCodeLabel, productPriceLabel;
    @FXML private ImageView productImageView;
    @FXML private TextField commentTitleField;
    @FXML private TextArea commentBodyField;
    @FXML private Button deleteButton, visibilityButton;
    @FXML private TreeView<Comment> commentTreeView; // Replace ListView with TreeView

    private ProductUpdateCallback updateCallback;
    private Product product;
    private EntityManagerFactory entityManagerFactory;
    private Product editableProduct;
    private CustomHib customHib;
    @FXML private Pane adminDashboardPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = StartGui.getEntityManagerFactory();
        customHib = new CustomHib(entityManagerFactory);
    }
    public void setProductData(Product product) {
        this.product=product;
        productNameLabel.setText(product.getTitle());
        productCodeLabel.setText(product.getCode()); // Replace getCode() with your actual method
        productPriceLabel.setText("Price: $" + product.getPrice()); // Replace getPrice() with your actual method
        String imagePath = "/com/kursinis/prif4kursinis/images/" + product.getPhotoName(); // Replace getImageName() with your actual method
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            productImageView.setImage(image);
        }
        loadCommentTree(product.getId());
    }

    public void addToCart(ActionEvent actionEvent) {
    }

    private void loadCommentTree(int productId) {
        TreeItem<Comment> rootItem = new TreeItem<>(new Comment("Root", "", null));
        rootItem.setExpanded(true);

        List<Comment> topLevelComments = customHib.getTopLevelComments(productId);
        for (Comment comment : topLevelComments) {
            TreeItem<Comment> commentItem = createTreeItem(comment);
            rootItem.getChildren().add(commentItem);
        }

        commentTreeView.setRoot(rootItem);
        commentTreeView.setShowRoot(false);
    }

    private TreeItem<Comment> createTreeItem(Comment comment) {
        TreeItem<Comment> item = new TreeItem<>(comment);
        for (Comment reply : comment.getReplies()) {
            item.getChildren().add(createTreeItem(reply));
        }
        return item;
    }

    public void createComment(ActionEvent actionEvent) {
        Comment newComment = new Comment(commentTitleField.getText(), commentBodyField.getText(), product);
        TreeItem<Comment> selectedTreeItem = commentTreeView.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Comment parentComment = selectedTreeItem.getValue();
            newComment.setParentComment(parentComment);
        }
        customHib.create(newComment);
        loadCommentTree(product.getId());
    }

    public void updateComment(ActionEvent actionEvent) {
        TreeItem<Comment> selectedTreeItem = commentTreeView.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Comment selectedComment = selectedTreeItem.getValue();
            Comment commentFromDb = customHib.getEntityById(Comment.class, selectedComment.getId());
            commentFromDb.setCommentTitle(commentTitleField.getText());
            commentFromDb.setCommentBody(commentBodyField.getText());
            customHib.update(commentFromDb);
            loadCommentTree(product.getId());
        }
    }

    public void deleteComment(ActionEvent actionEvent) {
        TreeItem<Comment> selectedTreeItem = commentTreeView.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Comment selectedComment = selectedTreeItem.getValue();
            customHib.delete(Comment.class, selectedComment.getId());
            loadCommentTree(product.getId());
        }
    }

    public void selectedComment(MouseEvent mouseEvent) {
        TreeItem<Comment> selectedTreeItem = commentTreeView.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Comment selectedComment = selectedTreeItem.getValue();
            commentTitleField.setText(selectedComment.getCommentTitle());
            commentBodyField.setText(selectedComment.getCommentBody());
        }
    }
}
