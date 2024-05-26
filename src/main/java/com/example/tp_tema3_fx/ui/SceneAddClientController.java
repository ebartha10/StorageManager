package com.example.tp_tema3_fx.ui;

import com.example.tp_tema3_fx.backend.model.Client;
import com.example.tp_tema3_fx.backend.model.Product;
import com.example.tp_tema3_fx.backend.service.ClientDaoService;
import com.example.tp_tema3_fx.backend.singleton.ClientDaoServiceSingleton;
import com.example.tp_tema3_fx.recycler.ClientRecycler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SceneAddClientController extends AddEntityController{
    @FXML
     TextField textFieldEmail;
    @FXML
     TextField textFieldGender;
    @FXML
    TextField textFieldName;
    @FXML
    Button buttonConfirm;
    @FXML
    Button buttonClose;
    private ClientDaoService clientDaoService = ClientDaoServiceSingleton.getClientDaoService();

    @FXML
    protected void onAddButtonClick(MouseEvent mouseEvent){
        //TODO ADD CHECKS FOR STRINGS!!
        if(textFieldName.getText().isEmpty() || textFieldGender.getText().isEmpty() || textFieldEmail.getText().isEmpty()) {
            shorError("Please fill all the fields.");
            return;
        }
        if(!textFieldEmail.getText().matches("\\S*@\\S*.com")){
            shorError("Enter a valid email!");
            return;
        }
        if(!textFieldGender.getText().equals("Male") && !textFieldGender.getText().equals("Female")){
            shorError("Enter a valid gender: Male, Female!");
            return;
        }
        String name = textFieldName.getText();
        String email = textFieldEmail.getText();
        int gender = textFieldGender.getText().equals("Male") ? 0 : (textFieldGender.getText().equals("Female") ? 1 : -1);

        Client newClient = new Client(entityId, name, email, gender);
        if(workingMode == AddEntityController.MODE_ADD) {
            int returnId = clientDaoService.insert(newClient);
            System.out.println("Entry added with id: " + returnId);
        }
        else if(workingMode == AddEntityController.MODE_UPDATE){
            int returnId = clientDaoService.update(newClient);
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

    public TextField getTextFieldEmail() {
        return textFieldEmail;
    }

    public void setTextFieldEmail(TextField textFieldEmail) {
        this.textFieldEmail = textFieldEmail;
    }

    public TextField getTextFieldGender() {
        return textFieldGender;
    }

    public void setTextFieldGender(TextField textFieldGender) {
        this.textFieldGender = textFieldGender;
    }

    public TextField getTextFieldName() {
        return textFieldName;
    }

    public void setTextFieldName(TextField textFieldName) {
        this.textFieldName = textFieldName;
    }
}
