package com.example.tp_tema3_fx.recycler;

import com.example.tp_tema3_fx.Main;
import com.example.tp_tema3_fx.backend.model.Client;
import com.example.tp_tema3_fx.ui.AddEntityController;
import com.example.tp_tema3_fx.ui.SceneAddClientController;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * UI recycler used for the display of clients.
 */
public class ClientRecycler extends VBox {
    private Client client;
    private boolean isSelected;
    private Label labelName;
    private ImageView imageView;

    public ClientRecycler(Client client) {
        this.client = client;
        this.setAlignment(Pos.CENTER);
        this.setPrefSize(160.0, 160.0);
        this.setStyle("-fx-background-color: #91D7E0; -fx-background-radius: 20;");
        imageView = new ImageView();
        if(client.getGender() == 0) {
            imageView.setImage(new Image(getClass().getResource("/com/example/tp_tema3_fx/drawable/iconMale.png").toExternalForm()));
        }
        else{
            imageView.setImage(new Image(getClass().getResource("/com/example/tp_tema3_fx/drawable/iconFemale.png").toExternalForm()));

        }
        imageView.setFitWidth(100.0);
        imageView.setFitHeight(100.0);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);

        labelName = new Label(client.getName());
        labelName.setStyle("-fx-font-family: Montserrat Bold; -fx-font-weight: bold; -fx-text-fill: #F4F7FA; -fx-font-size: 18;");
        addDropShadowEffect(labelName);

        this.getChildren().addAll(imageView, labelName);

        this.setOnMouseEntered(mouseEvent -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), (VBox) this);

            scaleTransition.setToX(1.05);
            scaleTransition.setToY(1.05);

            scaleTransition.play();
        });
        this.setOnMouseExited(mouseEvent -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), (VBox) this);

            scaleTransition.setToX(1);
            scaleTransition.setToY(1);

            scaleTransition.play();
        });

        this.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.isSelected = !isSelected;
                if (isSelected) {
                    this.setStyle("-fx-background-color: #285d64; -fx-background-radius: 20; -fx-padding: 5 20 5 20;");
                } else {
                    this.setStyle("-fx-background-color: #91D7E0; -fx-background-radius: 20; -fx-padding: 5 20 5 20;");
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
    private void addDropShadowEffect(Label label) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setWidth(30);
        dropShadow.setHeight(30);
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.75));
        dropShadow.setRadius(14.5);
        label.setEffect(dropShadow);
    }
    private MenuItem initMenuItem() {
        MenuItem editMenuItem = new MenuItem("Edit Client");
        editMenuItem.setOnAction(event -> {
            Stage addProductStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SceneAddClient.fxml"));


            try {
                Parent root = fxmlLoader.load();
                SceneAddClientController controller = fxmlLoader.getController();
                controller.setEntityId(this.client.getId());
                controller.setWorkingMode(AddEntityController.MODE_UPDATE);
                controller.getTextFieldName().setText(this.client.getName());
                controller.getTextFieldEmail().setText(this.client.getEmail());
                controller.getTextFieldGender().setText(this.client.getGender() == 0 ? "Male" : "Female");

                Scene scene = new Scene(root, 370, 240);
                addProductStage.setTitle("Update client");
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
    public int getClientId() {
        return client.getId();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Label getLabelName() {
        return labelName;
    }

    public void setLabelName(Label labelName) {
        this.labelName = labelName;
    }
}
