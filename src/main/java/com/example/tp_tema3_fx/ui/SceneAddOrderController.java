package com.example.tp_tema3_fx.ui;

import com.example.tp_tema3_fx.Main;
import com.example.tp_tema3_fx.backend.model.Client;
import com.example.tp_tema3_fx.backend.model.Order;
import com.example.tp_tema3_fx.backend.model.Product;
import com.example.tp_tema3_fx.backend.service.ClientDaoService;
import com.example.tp_tema3_fx.backend.service.OrderDaoService;
import com.example.tp_tema3_fx.backend.service.ProductDaoService;
import com.example.tp_tema3_fx.backend.singleton.ClientDaoServiceSingleton;
import com.example.tp_tema3_fx.backend.singleton.OrderDaoServiceSingleton;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class SceneAddOrderController extends AddEntityController implements Initializable {
    private final ProductDaoService productDaoService = ProductDaoServiceSingleton.getProductDaoService();
    private final ClientDaoService clientDaoService = ClientDaoServiceSingleton.getClientDaoService();
    private final OrderDaoService orderDaoService = OrderDaoServiceSingleton.getOrderDaoService();
    @FXML
    Button buttonRemove;
    @FXML
    Button buttonAdd;
    @FXML
    Button buttonConfirm;
    @FXML
    Button titleText;
    @FXML
    Button buttonClose;
    private Client selectedClient;
    private List<Client> clientList;
    private List<Product> productList;
    @FXML
    SplitMenuButton spinnerClients;
    @FXML
    ScrollPane scrollPane;

    public void onAddButtonClick(MouseEvent mouseEvent) {
        Stage addProductStage = new UpdatedStage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneChooseProduct.fxml"));

        try {
            Parent root = fxmlLoader.load();
            SceneChooseProductController controller = fxmlLoader.getController();
            addProductStage.setOnCloseRequest(closeRequest -> {
                Product newProduct = controller.getSelectedProduct();
                if (newProduct != null) {
                for(Product product : productList){
                    if(product.getId() == newProduct.getId()){
                        shorError("Product already selected!");
                        return;
                    }
                }
                productList.add(newProduct);
                System.out.println("Product on request " + newProduct);
                    ProductRecycler productRecycler = new ProductRecycler(newProduct);
                    addRecyclerToScrollPane(productRecycler);
                }

            });
            Scene scene = new Scene(root, 370, 240);
            addProductStage.setTitle("Choose product");
            addProductStage.setScene(scene);
            addProductStage.initOwner((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow());
            addProductStage.initModality(Modality.WINDOW_MODAL);
            addProductStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onRemoveButtonClicked(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to delete selected items?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.out.println("Confirmed!");
                Iterator<Node> iterator = ((VBox) scrollPane.getContent()).getChildren().iterator();
                while (iterator.hasNext()) {
                    Node obj = iterator.next();
                    if (obj instanceof ProductRecycler && ((ProductRecycler) obj).isSelected()) {
                        productList.remove(((ProductRecycler) obj).getProduct());
                        iterator.remove(); // Use iterator's remove() method to safely remove the element
                    }
                }

            } else {
                System.out.println("Cancelled");
            }
        });
        System.out.println(productList);
    }

    public void onCofirmButtonClick(MouseEvent mouseEvent) {
        if(selectedClient == null){
            shorError("Please choose a client!");
            return;
        }
        if(productList.isEmpty()){
            shorError("Please add products!");
            return;
        }
        Order order = new Order(-1, this.selectedClient);
        for(Product product : productList){
            order.addProduct(product, product.getQuantity());
        }
        orderDaoService.insert(order);
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onCloseButtonClick(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(workingMode == AddEntityController.MODE_ADD || workingMode == AddEntityController.MODE_UPDATE) {
            productList = new ArrayList<>();
            clientList = clientDaoService.findAll();
            for (Client client : clientList) {
                MenuItem menuItem = new MenuItem(client.getName());
                menuItem.setOnAction(actionEvent -> {
                    this.selectedClient = client;
                    spinnerClients.setText(client.getName());
                    System.out.println("Selected client:" + selectedClient.getName());
                });
                spinnerClients.getItems().add(menuItem);
            }
        }
        else{
            spinnerClients.setText(selectedClient.getName());
            for(Product product : productList){
                ProductRecycler productRecycler = new ProductRecycler(product);
                addRecyclerToScrollPane(productRecycler);
            }
            buttonRemove.setVisible(false);
            buttonAdd.setVisible(false);
            buttonClose.setVisible(false);
            buttonConfirm.setVisible(false);
        }
    }
    public void spinnerOnMouseExit(MouseEvent mouseEvent) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), spinnerClients);

        scaleTransition.setFromX(1.02);
        scaleTransition.setFromY(1.02);

        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        scaleTransition.play();
    }

    public void spinnerOnMouseEntered(MouseEvent mouseEvent) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), spinnerClients);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        scaleTransition.setToX(1.02);
        scaleTransition.setToY(1.02);


        scaleTransition.play();
    }
    private void addRecyclerToScrollPane(ProductRecycler productRecycler){
        VBox.setMargin(productRecycler, new Insets(5, 20, 5, 20));
        Parent currentContent = (Parent) this.scrollPane.getContent();
        VBox newContent = new VBox();
        newContent.getChildren().add(0, productRecycler);
        newContent.getChildren().add(currentContent);
        this.scrollPane.setContent(newContent);
        for(Node obj : ((VBox)scrollPane.getContent()).getChildren()){
            if(obj instanceof ProductRecycler){
                System.out.println(productRecycler.getProduct());
            }
        }
        System.out.println(productList);
    }
    private static class UpdatedStage extends Stage{
        @Override
        public void close(){
            fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST));
            super.close();
        }
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Button getTitleText() {
        return titleText;
    }
}
