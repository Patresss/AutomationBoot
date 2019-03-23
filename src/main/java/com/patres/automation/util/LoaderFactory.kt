package com.patres.automation.util

import com.patres.automation.gui.controller.TabContainer
import com.patres.automation.model.RootSchemaGroupModel
import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object LoaderFactory {

    fun loadRootSchemaGroup(tabPane: TabPane?, rootSchemaGroup: RootSchemaGroupModel): TabContainer {
        val fileName = rootSchemaGroup.file?.name ?: rootSchemaGroup.tmpFile
        val newTab = Tab("New Tab", rootSchemaGroup.controller)
        tabPane?.apply {
            tabs?.add(newTab)
            selectionModel?.select(newTab)
        }
        return TabContainer(tab = newTab, rootSchema = rootSchemaGroup)
    }

}
