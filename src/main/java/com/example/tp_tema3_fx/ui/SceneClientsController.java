package com.example.tp_tema3_fx.ui;

import com.example.tp_tema3_fx.Main;
import com.example.tp_tema3_fx.backend.model.Client;
import com.example.tp_tema3_fx.backend.service.ClientDaoService;
import com.example.tp_tema3_fx.backend.singleton.ClientDaoServiceSingleton;
import com.example.tp_tema3_fx.recycler.ClientRecycler;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SceneClientsController implements Initializable {
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
    private final ClientDaoService clientDaoService = ClientDaoServiceSingleton.getClientDaoService();
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
        Stage addClientStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneAddClient.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 370, 240);
            addClientStage.setTitle("Add client");
            addClientStage.setScene(scene);
            addClientStage.initOwner((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
            addClientStage.initModality(Modality.WINDOW_MODAL);
            addClientStage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
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
        List<Client> clients = clientDaoService.findAll();
        updateClientPanels(clients);
    }

    private void updateClientPanels(List<Client> clients) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        gridPane.setHgap(10.0);
        gridPane.setVgap(10.0);
        gridPane.setPadding(new Insets(10, 20, 10, 20));
        int numRows = clients.size() / 3 + 1;
        int numCols = 3;
        for (int col = 0; col < numCols; col++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(javafx.scene.layout.Priority.ALWAYS);
            column.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(column);
        }
        for (int row = 0; row < numRows; row++) {
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setVgrow(javafx.scene.layout.Priority.ALWAYS);
            rowConstraint.setValignment(javafx.geometry.VPos.CENTER);
            gridPane.getRowConstraints().add(rowConstraint);
        }
        int indexOfClient = 0;
        for(Client client : clients){
            ClientRecycler clientRecycler = new ClientRecycler(client);
            gridPane.add(clientRecycler, indexOfClient % 3, indexOfClient / 3);
            indexOfClient++;
        }
        scrollPane.setContent(gridPane);
    }

    public void onRemoveButtonClicked(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to delete selected items?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("Confirmed!");
                for(Node obj : ((GridPane)scrollPane.getContent()).getChildren()){
                    if(obj instanceof ClientRecycler && ((ClientRecycler) obj).isSelected()){
                        clientDaoService.delete(((ClientRecycler) obj).getClientId());
                    }
                }
                updateClientPanels(clientDaoService.findAll());
            } else {
                System.out.println("Cancelled");
            }
        });
    }
    public void onKeyPressedSearchBox(KeyEvent keyEvent) {
        List<Client> clientList;
        if(keyEvent.getCode() == KeyCode.ENTER){
            String name = textField.getText();
            //TODO ADD CHECKS FOR THE NAME!!!
            if(textField.getText().isEmpty()){
                clientList = clientDaoService.findAll();
            }
            else {
                clientList = clientDaoService.findByName(name);
            }
            updateClientPanels(clientList);
        }
    }
}
