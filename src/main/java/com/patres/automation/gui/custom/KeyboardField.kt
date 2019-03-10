package com.patres.automation.gui.custom

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXListCell
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXPopup
import com.patres.automation.keyboard.KeyboardKey
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.util.Callback


class KeyboardField : StackPane() {

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/KeyboardField.fxml"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.load<KeyboardField>()
    }

    @FXML
    lateinit var addButton: JFXButton

    @FXML
    lateinit var keyboardButtonHBox: HBox

    val keys = ArrayList<KeyboardKey>()

    @FXML
    fun initialize() {
        createPopup()
    }

    fun addKeyboardButton(key: KeyboardKey) {
        if (!keys.contains(key)) {
            keys.add(key)
            keyboardButtonHBox.children.add(KeyboardButton(key, this))
        }
    }

    fun removeKeyboardButton(keyboardButton: KeyboardButton) {
        keys.remove(keyboardButton.key)
        keyboardButtonHBox.children.remove(keyboardButton)
    }

    private fun createPopup() {
        val keyboardKeys = FXCollections.observableArrayList<KeyboardKey>(KeyboardKey.values().asList())
        val listView = JFXListView<KeyboardKey>().apply { items = keyboardKeys }
        listView.cellFactory = Callback {
            object : JFXListCell<KeyboardKey>() {
                override fun updateItem(item: KeyboardKey?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item != null) {
                        text = item.keyName
                    }
                }
            }
        }
        val popup = JFXPopup(listView)
        addButton.setOnMouseClicked { popup.show(addButton, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 0.0, 40.0) }

        listView.onMouseClicked = EventHandler { addKeyboardButton(listView.selectionModel.selectedItem) }
    }

}
