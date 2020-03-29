package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXListCell
import com.patres.automation.action.delay.DelayType
import com.patres.automation.settings.LanguageManager
import com.patres.automation.type.ActionBootDelay
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.text.Text
import javafx.util.Callback

class DelayActionController(
        action: ActionBootDelay,
        fxmlFile: String = "DelayAction.fxml"
) : TextActionController<ActionBootDelay>(fxmlFile, action) {

    @FXML
    lateinit var comboBox: ComboBox<DelayType>

    @FXML
    override fun initialize() {
        super.initialize()
        comboBox.cellFactory = createCellFactory()
        comboBox.buttonCell = createButtonCell()
        comboBox.items.addAll(DelayType.values())
        if (comboBox.value == null) {
            comboBox.value = DelayType.MILLISECONDS
        }
    }

    private fun createCellFactory(): Callback<ListView<DelayType>, ListCell<DelayType>> {
        return Callback {
            object : JFXListCell<DelayType>() {
                override fun updateItem(item: DelayType?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (item != null) {
                        val wrappedText = Text().apply {
                            textProperty().bind(LanguageManager.createStringBinding(item.bundleName()))
                        }
                        wrappedText.styleClass.add("text-combobox")
                        graphic = wrappedText
                        text = null
                    }
                }
            }
        }
    }

    private fun createButtonCell(): ListCell<DelayType> {
        return object : ListCell<DelayType>() {
            override fun updateItem(item: DelayType?, empty: Boolean) {
                super.updateItem(item, empty)
                if (empty || item == null) {
                    text = null
                } else {
                    val wrappedText = Text().apply {
                        wrappingWidthProperty().bind(widthProperty().subtract(10))
                        textProperty().bind(LanguageManager.createStringBinding(item.bundleShortName()))
                    }
                    wrappedText.styleClass.add("text-combobox")
                    graphic = wrappedText
                }
            }
        }
    }

    fun selectedDelayTime(): DelayType = comboBox.value ?: DelayType.MILLISECONDS

}