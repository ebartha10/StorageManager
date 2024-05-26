package com.example.tp_tema3_fx.recycler;

import com.example.tp_tema3_fx.Main;
import com.example.tp_tema3_fx.backend.model.Product;
import com.example.tp_tema3_fx.ui.AddEntityController;
import com.example.tp_tema3_fx.ui.SceneAddProductController;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * UI recycler used for the display of products.
 */
public class ProductRecycler extends HBox {
    private Product product;
    private boolean isSelected;
    private Label nameLabel;
    private Label priceLabel;
    private Label countLabel;
    private Separator separator;

    public ProductRecycler(Product product) {
        this.product = product;
        this.isSelected = false;
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefSize(550,40);
        this.setStyle("-fx-background-color: #91D7E0; -fx-background-radius: 50; -fx-padding: 5 20 5 20;");
        this.setSpacing(20.0);

        nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-size: 18; -fx-font-family: Monserrat Bold; -fx-font-weight: bold; -fx-text-fill: #F4F7FA;");

        priceLabel = new Label("$" + product.getPrice());
        priceLabel.setStyle("-fx-font-size: 18; -fx-font-family: Monserrat Bold; -fx-font-weight: Bold; -fx-text-fill: #F4F7FA;");

        countLabel = new Label("x" + product.getQuantity());
        countLabel.setStyle("-fx-font-size: 18; -fx-font-family: Monserrat Bold; -fx-font-weight: Bold; -fx-text-fill: #F4F7FA;");

        addDropShadowEffect(nameLabel);
        addDropShadowEffect(priceLabel);
        addDropShadowEffect(countLabel);

        separator = new Separator();
        separator.setStyle("-fx-background-color: transparent;");
        setHgrow(separator, Priority.ALWAYS);
        separator.setOpacity(0.0);

        this.getChildren().addAll(nameLabel, separator, priceLabel, countLabel);

        this.setOnMouseEntered(mouseEvent -> {
            this.setOpacity(0.9);
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), (HBox) this);

            scaleTransition.setToX(1.02);
            scaleTransition.setToY(1.02);

            scaleTransition.play();
        });

        this.setOnMouseExited(mouseEvent -> {
            this.setOpacity(1);
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), (HBox) this);

            scaleTransition.setToX(1);
            scaleTransition.setToY(1);

            scaleTransition.play();
        });

        this.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.isSelected = !isSelected;
                if (isSelected) {
                    this.setStyle("-fx-background-color: #285d64; -fx-background-radius: 50; -fx-padding: 5 20 5 20;");
                } else {
                    this.setStyle("-fx-background-color: #91D7E0; -fx-background-radius: 50; -fx-padding: 5 20 5 20;");
                }
            }
        });
        this.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.rgb(0,0,0, 0.57), 9.5, 0, 5, 5));

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setStyle("-fx-background-color: #23E9B4; -fx-text-fill: #F4F7FA; -fx-font-family: \"Montserrat Bold\";");
        MenuItem editMenuItem = initMenuItem();

        contextMenu.getItems().addAll(editMenuItem);

        this.setOnContextMenuRequested(event -> {
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
            event.consume();
        });
    }

    private MenuItem initMenuItem() {
        MenuItem editMenuItem = new MenuItem("Edit Field");
        editMenuItem.setOnAction(event -> {
            Stage addProductStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneAddProduct.fxml"));


            try {
                Parent root = fxmlLoader.load();
                SceneAddProductController controller = fxmlLoader.getController();
                controller.setEntityId(this.product.getId());
                controller.setWorkingMode(AddEntityController.MODE_UPDATE);
                controller.getTextFieldName().setText(this.product.getName());
                controller.getTextFieldQuantity().setText(this.product.getQuantity() + "");
                controller.getTextFieldPrice().setText(this.product.getPrice() + "");

                Scene scene = new Scene(root, 370, 240);
                addProductStage.setTitle("Update product");
                addProductStage.setScene(scene);
                addProductStage.initOwner((Stage) this.getScene().getWindow());
                addProductStage.initModality(Modality.WINDOW_MODAL);
                addProductStage.show();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return editMenuItem;
    }

    private void addDropShadowEffect(Label label) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.75));
        label.setEffect(dropShadow);
    }

    public int getProductId() {
        return product.getId();
    }

    public Product getProduct(){
        return this.product;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
