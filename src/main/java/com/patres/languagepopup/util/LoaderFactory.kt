package com.patres.languagepopup.util

import com.patres.languagepopup.Main
import com.patres.languagepopup.action.mouse.MouseAction
import com.patres.languagepopup.gui.controller.model.LabelActionController
import com.patres.languagepopup.gui.controller.model.RootSchemaGroupController
import com.patres.languagepopup.gui.controller.model.SchemaGroupController
import com.patres.languagepopup.gui.controller.model.TextActionController
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.patres.languagepopup.model.ActionNodeModel
import javafx.fxml.FXMLLoader
import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object LoaderFactory {

    fun createLabelActionController(model: MouseAction<LabelActionController>): LabelActionController {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/LabelAction.fxml")
        loader.resources = Main.bundle
        loader.load<LabelActionController>()
        return loader.getController<LabelActionController>().also {
            it.model = model
        }
    }


    fun createTextActionController(model: ActionNodeModel<TextActionController>): TextActionController {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/TextAction.fxml")
        loader.resources = Main.bundle
        loader.load<TextActionController>()
        return loader.getController<TextActionController>().also {
            it.model = model
        }
    }

    fun createSchemaGroupController(model: SchemaGroupModel): SchemaGroupController {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/SchemaGroup.fxml")
        loader.resources = Main.bundle
        loader.load<SchemaGroupController>()
        return loader.getController<SchemaGroupController>().also {
            it.model = model
        }
    }

    fun createRootSchemaGroupController(model: RootSchemaGroupModel): RootSchemaGroupController {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/RootSchemaGroup.fxml")
        loader.resources = Main.bundle
        loader.load<RootSchemaGroupController>()
        return loader.getController<RootSchemaGroupController>().also {
            it.model = model
        }
    }

    fun createRootSchemaGroup(tabPane: TabPane?): RootSchemaGroupModel {
        val rootSchemaGroup = RootSchemaGroupModel().apply {
            controller.insidePane.content = schemaGroup.controller.getMainNode()
        }

        val newTab = Tab("New Tab", rootSchemaGroup.controller.rootStackPane)
        tabPane?.apply {
            tabs?.add(newTab)
            selectionModel?.select(newTab)
        }

        return rootSchemaGroup
    }
}
