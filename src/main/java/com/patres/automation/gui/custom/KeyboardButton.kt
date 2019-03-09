package com.patres.automation.gui.custom

import com.jfoenix.controls.JFXButton
import com.patres.automation.util.KeyboardKey
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.geometry.Insets
import javafx.scene.layout.GridPane


class KeyboardButton(
        val key: KeyboardKey,
        val keyboardField: KeyboardField
) : GridPane() {

    companion object {
        private const val DEFAULT_PADDING = 3.0
        val DEFAULT_INSETS = Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING)
    }

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/KeyboardButton.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<KeyboardButton>()
    }

    @FXML
    lateinit var removeButton: JFXButton

    @FXML
    lateinit var keyboardButton: JFXButton

    @FXML
    fun initialize() {
        keyboardButton.text = key.keyName
        keyboardButton.padding = DEFAULT_INSETS
    }

    @FXML
    fun removeButton() {
        keyboardField.removeKeyboardButton(this)
    }

}
