package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXListCell
import com.patres.automation.action.delay.TimeType
import com.patres.automation.settings.LanguageManager
import com.patres.automation.type.ActionBootTime
import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.text.Text
import javafx.util.Callback

class TimeActionController(
        action: ActionBootTime
) : TextActionController<ActionBootTime>("DelayAction.fxml", action) {

    @FXML
    lateinit var comboBox: ComboBox<TimeType>

    @FXML
    override fun initialize() {
        super.initialize()
        comboBox.cellFactory = createCellFactory()
        comboBox.buttonCell = createButtonCell()
        comboBox.items.addAll(TimeType.values())
        if (comboBox.value == null) {
            comboBox.value = TimeType.MILLISECONDS
        }
    }

    private fun createCellFactory(): Callback<ListView<TimeType>, ListCell<TimeType>> {
        return Callback {
            object : JFXListCell<TimeType>() {
                override fun updateItem(item: TimeType?, empty: Boolean) {
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

    private fun createButtonCell(): ListCell<TimeType> {
        return object : ListCell<TimeType>() {
            override fun updateItem(item: TimeType?, empty: Boolean) {
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

    fun selectedDelayTime(): TimeType = comboBox.value ?: TimeType.MILLISECONDS

}