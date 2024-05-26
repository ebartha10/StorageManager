package com.example.tp_tema3_fx.ui;

import com.example.tp_tema3_fx.recycler.ProductRecycler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public abstract class AddEntityController {
    public final static int  MODE_ADD = 0;
    public final static int  MODE_UPDATE = 1;
    public final static int MODE_SHOW = 2;
    protected int workingMode;
    protected int entityId;

    public int isWorkingMode() {
        return workingMode;
    }

    public void setWorkingMode(int workingMode) {
        this.workingMode = workingMode;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }
    protected void shorError(String givenString){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("WARNING");
        alert.setHeaderText(givenString);
        alert.showAndWait();
    }
}
