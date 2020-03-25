module automationBoot {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.jfoenix;
    requires slf4j.api;
    requires java.desktop;
    requires opencv;
    requires fontawesomefx.fontawesome;
    requires de.jensd.fx.glyphs.commons;
    requires jnativehook;
    requires java.logging;
    requires com.fasterxml.jackson.databind;
    requires commons.lang3;
    requires kotlin.stdlib;
    requires jackson.module.kotlin;
    requires kotlin.reflect;
    requires jdk.httpserver;

    opens com.patres.automation to javafx.fxml, javafx.graphics;
    opens com.patres.automation.gui.controller to javafx.fxml;
    opens com.patres.automation.gui.controller.saveBackScreen to javafx.fxml;
    opens com.patres.automation.gui.controller.saveBackScreen.activeSchema to javafx.fxml;
    opens com.patres.automation.gui.controller.saveBackScreen.settings to javafx.fxml;
    opens com.patres.automation.gui.controller.model to javafx.fxml;
    opens com.patres.automation.gui.controller.box to javafx.fxml;
    opens com.patres.automation.gui.custom to javafx.fxml;
    opens com.patres.automation.gui.dialog to javafx.fxml;

    opens com.patres.automation.mapper.model to com.fasterxml.jackson.databind;
    opens com.patres.automation.settings to com.fasterxml.jackson.databind;
    exports com.patres.automation.mapper.model to com.fasterxml.jackson.databind, kotlin.reflect;
    exports com.patres.automation.settings to com.fasterxml.jackson.databind, kotlin.reflect;
    exports com.patres.automation.keyboard to com.fasterxml.jackson.databind;
    exports com.patres.automation.type to com.fasterxml.jackson.databind;
    exports com.patres.automation.action.delay to com.fasterxml.jackson.databind;

    exports com.patres.automation;

}