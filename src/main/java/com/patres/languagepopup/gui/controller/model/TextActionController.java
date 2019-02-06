package com.patres.languagepopup.gui.controller.model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.patres.languagepopup.Main;
import com.patres.languagepopup.gui.controller.Controllable;
import com.patres.languagepopup.gui.controller.PointerController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TextActionController extends AutomationController {

    @FXML
    private JFXTextField valueTextField;
    @FXML
    private Label actionLabel;
    @FXML
    private Label validLabel;
    @FXML
    private JFXButton pointButton;
    @FXML
    private JFXButton removeButton;

    @FXML
    public void initialize() {
        setHandler();
    }

    private void setHandler() {
        pointButton.setOnAction((event) -> {
            Main.Companion.getMainStage().setIconified(true);
            showPointerStage();
        });
    }

    private void showPointerStage() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Set point");
        stage.setScene(loadPointScene(stage));
        stage.setMaximized(true);
        stage.show();
    }

    private Scene loadPointScene(Stage stage) {
        PointerController pointController = new PointerController(stage, this);
        return pointController.getScene();
    }

    public void showButton(boolean show) {
        pointButton.setVisible(show);
    }

    public String getValue() {
        return valueTextField.getText();
    }

    public Label getActionLabel() {
        return actionLabel;
    }

    public JFXTextField getValueTextField() {
        return valueTextField;
    }

    public Label getValidLabel() {
        return validLabel;
    }
}
