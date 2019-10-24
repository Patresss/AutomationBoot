package com.patres.automation.gui.custom

import com.patres.automation.Main
import com.patres.automation.gui.menuItem.MenuItem
import com.patres.automation.util.getIcon
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
                    text = item.actionBoot?.bundleName()?.let { Main.getLanguageString(it) }//  TODO i18n?
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
                        text = item.actionBoot?.bundleName()?.let { Main.getLanguageString(it) }
                    }
                    wrappedText.styleClass.add("text-combobox")
                    graphic = wrappedText
                }
            }
        }
    }

}