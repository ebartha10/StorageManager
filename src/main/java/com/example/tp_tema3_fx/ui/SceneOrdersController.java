package com.example.tp_tema3_fx.ui;

import com.example.tp_tema3_fx.Main;
import com.example.tp_tema3_fx.backend.model.Order;
import com.example.tp_tema3_fx.backend.service.OrderDaoService;
import com.example.tp_tema3_fx.backend.singleton.OrderDaoServiceSingleton;
import com.example.tp_tema3_fx.recycler.OrderRecycler;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SceneOrdersController implements Initializable {
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
    private final OrderDaoService orderDaoService = OrderDaoServiceSingleton.getOrderDaoService();
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
        Stage addProductStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneAddOrder.fxml"));

        try {
            SceneAddOrderController controller = new SceneAddOrderController();
            controller.setEntityId(-1);
            controller.setWorkingMode(AddEntityController.MODE_ADD);
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 640, 480);
            addProductStage.setTitle("Add order");
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
    protected void onProductButtonClick(MouseEvent mouseEvent){
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneProduct.fxml"));

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
        List<Order> orderList = orderDaoService.findAll();
        updatePanel(orderList);
    }

    public void onKeyPressedSearchBox(KeyEvent keyEvent) {
        List<Order> orderList = new ArrayList<>();
        if(keyEvent.getCode() == KeyCode.ENTER){
            String name = textField.getText();
            if(textField.getText().isEmpty()){
                orderList = orderDaoService.findAll();
            }
            else {
                orderList = orderDaoService.findByName(name);
            }
            updatePanel(orderList);
        }
    }

    private void updatePanel(List<Order> orderList) {
        VBox newContent = new VBox();
        for(Order order : orderList){
            OrderRecycler orderRecycler = new OrderRecycler(order);
            VBox.setMargin(orderRecycler, new Insets(5, 20, 5, 20));
            newContent.getChildren().add(orderRecycler);
        }
        this.scrollPane.setContent(newContent);
    }
}
