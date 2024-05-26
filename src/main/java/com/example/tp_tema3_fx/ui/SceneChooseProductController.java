package com.example.tp_tema3_fx.ui;

import com.example.tp_tema3_fx.backend.model.Client;
import com.example.tp_tema3_fx.backend.model.Product;
import com.example.tp_tema3_fx.backend.service.ProductDaoService;
import com.example.tp_tema3_fx.backend.singleton.ProductDaoServiceSingleton;
import com.example.tp_tema3_fx.recycler.ProductRecycler;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SceneChooseProductController implements Initializable {
    @FXML
    SplitMenuButton splitMenu;
    @FXML
    TextField textField;
    private Product selectedProduct;
    private final ProductDaoService productDaoService = ProductDaoServiceSingleton.getProductDaoService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Product> productList = productDaoService.findAll();
        for(Product product : productList){
            MenuItem menuItem = new MenuItem(product.getName());
            menuItem.setOnAction(actionEvent -> {
                this.selectedProduct = product;
                textField.setText(product.getQuantity() + "");
                splitMenu.setText(product.getName());
                System.out.println("Selected product: " + selectedProduct.getName());
            });
            splitMenu.getItems().add(menuItem);
        }
    }

    public void onCofirmButtonClick(MouseEvent mouseEvent) {
        if(textField.getText().isEmpty() || !textField.getText().matches("\\d*")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WARNING");
            alert.setHeaderText("Enter a valid number!");
            alert.showAndWait();
            return;
        }
        int productQty = Integer.parseInt(textField.getText());
        if(productQty > selectedProduct.getQuantity()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("WARNING");
            alert.setHeaderText("Select a valid product quantity.");
            alert.showAndWait();
        }
        else{
            selectedProduct.setQuantity(productQty);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void onCloseButtonClick(MouseEvent mouseEvent) {
        selectedProduct = null;
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void spinnerOnMouseExit(MouseEvent mouseEvent) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), splitMenu);

        scaleTransition.setFromX(1.02);
        scaleTransition.setFromY(1.02);

        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        scaleTransition.play();
    }

    public void spinnerOnMouseEntered(MouseEvent mouseEvent) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), splitMenu);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        scaleTransition.setToX(1.02);
        scaleTransition.setToY(1.02);


        scaleTransition.play();
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }
}
