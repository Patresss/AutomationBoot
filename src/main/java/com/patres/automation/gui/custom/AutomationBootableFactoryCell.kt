package com.patres.automation.gui.custom

import com.patres.automation.gui.menuItem.MenuItem
import com.patres.automation.settings.LanguageManager
import com.patres.automation.util.extension.getIcon
import javafx.scene.control.ListCell
import javafx.scene.text.Text


class AutomationBootableFactoryCell {

    fun createCell(): ListCell<MenuItem> {
        return object : ListCell<MenuItem>() {
            init {
                prefHeight = 30.0
            }

            override fun updateItem(item: MenuItem?, empty: Boolean) {
                super.updateItem(item, empty)
                if (item != null) {
                    textProperty().bind(LanguageManager.createStringBinding(item.actionBoot?.bundleName() ?: ""))
                    graphic = item.actionGraphic.getIcon("1em")
                }
            }
        }
    }


    fun createButtonCell(): ListCell<MenuItem> {
        return object : ListCell<MenuItem>() {
            override fun updateItem(item: MenuItem?, empty: Boolean) {
                super.updateItem(item, empty)
                if (empty || item == null) {
                    text = null
                } else {
                    val wrappedText = Text().apply {
                        wrappingWidthProperty().bind(widthProperty().subtract(10))
                        textProperty().bind(LanguageManager.createStringBinding(item.actionBoot?.bundleName() ?: ""))
                    }
                    wrappedText.styleClass.add("text-combobox")
                    graphic = wrappedText
                }
            }
        }
    }

}