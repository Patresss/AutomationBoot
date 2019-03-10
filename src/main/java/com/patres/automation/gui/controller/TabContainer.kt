package com.patres.automation.gui.controller

import com.patres.automation.Main
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.util.LoaderFile
import javafx.scene.control.Tab
import java.io.File

class TabContainer(
        val tab: Tab,
        val rootSchema: RootSchemaGroupModel
) {

    companion object {
        const val MAX_NUMBER_OF_CHARACTERS = 24
        val DEFAULT_NAME = Main.bundle.getString("rootSchema.defaultFileName") ?: "New file"
    }

    var file: File? = null
        set(value) {
            field = value
            setTabName(value)
        }

    private fun setTabName(file: File?) {
        var newName = file?.name?.removeSuffix(LoaderFile.AUTOMATION_BOOT_EXTENSION) ?: DEFAULT_NAME
        newName = if (newName.length > MAX_NUMBER_OF_CHARACTERS) newName.substring(0, MAX_NUMBER_OF_CHARACTERS) + "…" else newName
        tab.text = newName
    }

}