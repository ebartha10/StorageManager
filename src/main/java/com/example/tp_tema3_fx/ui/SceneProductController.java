package com.example.tp_tema3_fx.ui;

import com.example.tp_tema3_fx.Main;
import com.example.tp_tema3_fx.backend.model.Product;
import com.example.tp_tema3_fx.backend.service.ProductDaoService;
import com.example.tp_tema3_fx.backend.singleton.ProductDaoServiceSingleton;
import com.example.tp_tema3_fx.recycler.ProductRecycler;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SceneProductController implements Initializable {
    @FXML
    AnchorPane productPane;
    @FXML
    Button buttonProducts;
    @FXML
    Button buttonClients;
    @FXML
    Button buttonOrders;
    @FXML
    Button buttonAdd;
    @FXML
    Button buttonRemove;
    @FXML
    ScrollPane scrollPane;
    @FXML
    TextField textField;
    private ProductDaoService productDaoService = ProductDaoServiceSingleton.getProductDaoService();

    @FXML
    protected void onProductButtonEnter(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), buttonProducts);

        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);

        scaleTransition.play();
    }
    @FXML
    protected void onProductButtonExit(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), buttonProducts);
        scaleTransition.setFromX(1.05);
        scaleTransition.setFromY(1.05);

        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        scaleTransition.play();
   }
    @FXML
    protected void onClientsButtonEnter(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), buttonClients);

        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);

        scaleTransition.play();
    }
    @FXML
    protected void onClientsButtonExit(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), buttonClients);

        scaleTransition.setFromX(1.05);
        scaleTransition.setFromY(1.05);

        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        scaleTransition.play();
    }
    @FXML
    protected void onOrdersButtonEnter(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), buttonOrders);

        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);

        scaleTransition.play();
    }
    @FXML
    protected void onOrdersButtonExit(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), buttonOrders);

        scaleTransition.setFromX(1.05);
        scaleTransition.setFromY(1.05);

        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        scaleTransition.play();
    }
    @FXML
    protected void onAddButtonClick(MouseEvent mouseEvent){
//        ProductRecycler productRecycler = new ProductRecycler("John Smith", "$300", "5");
//        VBox.setMargin(productRecycler, new Insets(5,20,5,20));
//        Parent currentContent = (Parent) scrollPane.getContent();
//        VBox newContent = new VBox();
//        newContent.getChildren().add(0,productRecycler);
//        if(currentContent != null){
//            newContent.getChildren().add(currentContent);
//        }
//        scrollPane.setContent(newContent);

        Stage addProductStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneAddProduct.fxml"));

        try {
            Parent root = fxmlLoader.load();
            SceneAddProductController controller = fxmlLoader.getController();
            controller.setEntityId(-1);
            controller.setWorkingMode(AddEntityController.MODE_ADD);

            Scene scene = new Scene(root, 370, 240);
            addProductStage.setTitle("Add product");
            addProductStage.setScene(scene);
            addProductStage.initOwner((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
            addProductStage.initModality(Modality.WINDOW_MODAL);
            addProductStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    protected void onSearchBoxEnter(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), textField);

        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);

        scaleTransition.play();
    }
    @FXML
    protected void onSearchBoxExit(){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), textField);
        scaleTransition.setFromX(1.05);
        scaleTransition.setFromY(1.05);

        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        scaleTransition.play();
    }

    public void onClientsButtonClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneClients.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onOrdersButtonClick(MouseEvent mouseEvent){
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneOrders.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Product> productList = productDaoService.findAll();
        updateProductList(productList);
    }

    public void onKeyPressedSearchBox(KeyEvent keyEvent) {
        List<Product> productList;
        if(keyEvent.getCode() == KeyCode.ENTER){
            String name = textField.getText();
            //TODO ADD CHECKS FOR THE NAME!!!
            if(textField.getText().isEmpty()){
                productList = productDaoService.findAll();
            }
            else {
                productList = productDaoService.findByName(name);
            }
            updateProductList(productList);
        }
    }
    private void updateProductList(List<Product> productList){
        VBox newContent = new VBox();
        int index = 0;
        for (Product product : productList){
            ProductRecycler productRecycler = new ProductRecycler(product);
            VBox.setMargin(productRecycler, new Insets(5,20,5,20));
            newContent.getChildren().add(index++,productRecycler);
        }
        scrollPane.setContent(newContent);
    }

    public void onRemoveButtonClick(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to delete selected items?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("Confirmed!");
                for(Node obj : ((VBox)scrollPane.getContent()).getChildren()){
                    if(obj instanceof ProductRecycler && ((ProductRecycler) obj).isSelected()){
                        productDaoService.delete(((ProductRecycler) obj).getProductId());
                    }
                }
                updateProductList(productDaoService.findAll());
            } else {
                System.out.println("Cancelled");
            }
        });
    }
}
