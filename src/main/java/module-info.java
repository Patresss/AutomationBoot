module automationBoot {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires slf4j.api;
    requires java.desktop;
    requires kotlin.stdlib;
    requires opencv;
    requires fontawesomefx.fontawesome;
    requires de.jensd.fx.glyphs.commons;
    requires jnativehook;
    requires java.logging;
    requires jackson.module.kotlin;
    requires com.fasterxml.jackson.databind;
//    requires ktor.http.jvm;
//    requires ktor.server.netty;
//    requires ktor.server.host.common;
//    requires ktor.server.core;
    requires commons.lang3;

    opens com.patres.automation to javafx.fxml, javafx.graphics;
    opens com.patres.automation.gui.controller to javafx.fxml;
    opens com.patres.automation.gui.controller.settings to javafx.fxml;
    opens com.patres.automation.gui.controller.model to javafx.fxml;
    opens com.patres.automation.gui.controller.box to javafx.fxml;
    opens com.patres.automation.gui.custom to javafx.fxml;
    opens com.patres.automation.gui.dialog to javafx.fxml;

    opens com.patres.automation.mapper.model to com.fasterxml.jackson.databind;
    exports com.patres.automation.mapper.model to  com.fasterxml.jackson.databind;
    exports com.patres.automation.settings to  com.fasterxml.jackson.databind;
    exports com.patres.automation.keyboard to  com.fasterxml.jackson.databind;
    exports com.patres.automation.type to  com.fasterxml.jackson.databind;

    exports com.patres.automation;
}