package com.example.tp_tema3_fx.recycler;

import com.example.tp_tema3_fx.Main;
import com.example.tp_tema3_fx.backend.model.Order;
import com.example.tp_tema3_fx.ui.AddEntityController;
import com.example.tp_tema3_fx.ui.SceneAddOrderController;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * UI recycler used for the display of orders.
 */
public class OrderRecycler extends HBox {
    private Order order;
    private Label nameLabel;
    private Label orderNumberLabel;
    private Label priceLabel;
    private Separator separator;

    public OrderRecycler(Order order) {
        this.order = order;
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefSize(550,40);
        this.setStyle("-fx-background-color: #91D7E0; -fx-background-radius: 50; -fx-padding: 5 20 5 20;");
        this.setSpacing(20.0);

        nameLabel = new Label(order.getClient().getName());
        nameLabel.setStyle("-fx-font-size: 18; -fx-font-family: Monserrat Bold; -fx-font-weight: bold; -fx-text-fill: #F4F7FA;");

        priceLabel = new Label("");
        priceLabel.setStyle("-fx-font-size: 18; -fx-font-family: Monserrat Bold; -fx-font-weight: Bold; -fx-text-fill: #F4F7FA;");

        orderNumberLabel = new Label(order.getId() + "");
        orderNumberLabel.setStyle("-fx-font-size: 18; -fx-font-family: Monserrat Bold; -fx-font-weight: Bold; -fx-text-fill: #F4F7FA;");

        addDropShadowEffect(nameLabel);
        addDropShadowEffect(priceLabel);
        addDropShadowEffect(orderNumberLabel);

        separator = new Separator();
        separator.setStyle("-fx-background-color: transparent;");
        setHgrow(separator, Priority.ALWAYS);
        separator.setOpacity(0.0);

        this.getChildren().addAll(orderNumberLabel, nameLabel, separator, priceLabel);

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
            Stage addProductStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneAddOrder.fxml"));
            try {
                SceneAddOrderController controller = new SceneAddOrderController();
                controller.setEntityId(this.order.getId());
                controller.setWorkingMode(AddEntityController.MODE_SHOW);
                controller.setSelectedClient(order.getClient());
                controller.setProductList(order.getProducts().keySet().stream().toList());
                fxmlLoader.setController(controller);
                Parent root = fxmlLoader.load();
                controller.getTitleText().setText("Order No." + order.getId());

                Scene scene = new Scene(root, 640, 480);
                addProductStage.setTitle("Order Details...");
                addProductStage.setScene(scene);
                addProductStage.initOwner((Stage) this.getScene().getWindow());
                addProductStage.initModality(Modality.WINDOW_MODAL);
                addProductStage.show();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.rgb(0,0,0, 0.57), 9.5, 0, 5, 5));
    }
    private void addDropShadowEffect(Label label) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        //dropShadow.setWidth(10);
        //dropShadow.setWidth(10);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.75));
        //dropShadow.setOffsetX(0);
        //dropShadow.setOffsetY(2);
        //dropShadow.setRadius(4.5);
        label.setEffect(dropShadow);
    }
}
