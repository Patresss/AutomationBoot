package com.patres.languagepopup.gui.controller;

import com.patres.languagepopup.Main;
import com.patres.languagepopup.Point;
import com.patres.languagepopup.gui.controller.model.TextActionController;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PointerController {

    private final static double CIRCLE_OPACITY = 0.6;

    private Scene scene;
    private Pane pane;
    private Stage stage;
    private TextActionController pointPane;
    private Circle circlePoint;


    public PointerController(Stage stage, TextActionController pointPane) {
        this.pane = new Pane();
        this.pointPane = pointPane;
        this.stage = stage;
        setScene();
        setStyle();
        loadCirclePoint();
        addMouseListener();
    }

    private void setStyle() {
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(Main.Companion.getStylesheet());
        pane.getStyleClass().add("stackpane-background");
        pane.setCursor(Cursor.CROSSHAIR);
    }

    private void setScene() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double width = primaryScreenBounds.getWidth();
        double height = primaryScreenBounds.getHeight();
        this.scene = new Scene(pane, width, height);
    }

    private void loadCirclePoint() {
        this.circlePoint = new Circle(12.0);
        circlePoint.setFill(Color.YELLOW);
        circlePoint.setOpacity(0.0);
        pane.getChildren().add(circlePoint);
    }

    private void addMouseListener() {
        pane.setOnMousePressed((pressedEvent) -> {
            loadPressedHandler(pressedEvent);

            pane.setOnMouseDragged((draggedEvent) ->
                    loadDraggedHandler(draggedEvent)
            );

            pane.setOnMouseReleased((relesedEvent) -> {
                loadRelesedHandler(relesedEvent);
            });
        });
    }

    private void loadPressedHandler(MouseEvent pressedEvent) {
        FadeTransition fadeStart = new FadeTransition(Duration.millis(200), circlePoint);
        fadeStart.setFromValue(0.);
        fadeStart.setToValue(CIRCLE_OPACITY);
        fadeStart.play();

        circlePoint.setCenterX(pressedEvent.getScreenX() - stage.getX());
        circlePoint.setCenterY(pressedEvent.getScreenY() - stage.getY());
    }

    private void loadDraggedHandler(MouseEvent draggedEvent) {
        circlePoint.setCenterX(draggedEvent.getScreenX() - stage.getX());
        circlePoint.setCenterY(draggedEvent.getScreenY() - stage.getY());
    }

    private void loadRelesedHandler(MouseEvent relesedEvent) {
        ScaleTransition scaleEnd = new ScaleTransition(Duration.millis(200), circlePoint);
        scaleEnd.setToX(0.0);
        scaleEnd.setToY(0.0);
        scaleEnd.setOnFinished((finishEvent) -> {
            int x = (int) (relesedEvent.getScreenX() - stage.getX());
            int y = (int) (relesedEvent.getScreenY() - stage.getY());

            Point point = new Point(x, y);
            pointPane.getValueTextField().setText(point.toString());
            stage.close();
            Main.Companion.getMainStage().setIconified(false);
        });

        scaleEnd.play();
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }
}
