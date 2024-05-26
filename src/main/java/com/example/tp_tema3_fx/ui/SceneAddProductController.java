package com.example.tp_tema3_fx.ui;

import com.example.tp_tema3_fx.backend.model.Product;
import com.example.tp_tema3_fx.backend.service.ProductDaoService;
import com.example.tp_tema3_fx.backend.singleton.ProductDaoServiceSingleton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SceneAddProductController extends AddEntityController{
    @FXML
    Button buttonConfirm;
    @FXML
    Button buttonClose;
    @FXML
    TextField textFieldName;
    @FXML
    TextField textFieldPrice;
    @FXML
    TextField textFieldQuantity;
    private final ProductDaoService productDaoService = ProductDaoServiceSingleton.getProductDaoService();

    @FXML
    protected void onAddButtonClick(MouseEvent mouseEvent){
        if(textFieldName.getText().isEmpty() || textFieldPrice.getText().isEmpty() || textFieldQuantity.getText().isEmpty()) {
            shorError("Please fill all the fields.");
            return;
        }
        if(!textFieldPrice.getText().matches("\\d*") || !textFieldQuantity.getText().matches("\\d*")){
            shorError("Enter a valid number!");
            return;
        }
        String name = textFieldName.getText();
        int price = Integer.parseInt(textFieldPrice.getText());
        int quantity = Integer.parseInt(textFieldQuantity.getText());
        Product newProduct = new Product(entityId, name, price, quantity);

        if(workingMode == AddEntityController.MODE_ADD) {
            int returnId = productDaoService.insert(newProduct);
            System.out.println("Entry added with id: " + returnId);
        }
        else if(workingMode == AddEntityController.MODE_UPDATE){
            int returnId = productDaoService.update(newProduct);
            System.out.println("Entry updated with id: " + returnId);

        }
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    protected void onCloseButtonClick(MouseEvent mouseEvent){
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public TextField getTextFieldName() {
        return textFieldName;
    }

    public void setTextFieldName(TextField textFieldName) {
        this.textFieldName = textFieldName;
    }

    public TextField getTextFieldPrice() {
        return textFieldPrice;
    }

    public void setTextFieldPrice(TextField textFieldPrice) {
        this.textFieldPrice = textFieldPrice;
    }

    public TextField getTextFieldQuantity() {
        return textFieldQuantity;
    }

    public void setTextFieldQuantity(TextField textFieldQuantity) {
        this.textFieldQuantity = textFieldQuantity;
    }
}
