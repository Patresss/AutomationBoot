package com.patres.languagepopup.util

import com.patres.languagepopup.Main
import com.patres.languagepopup.action.Action
import com.patres.languagepopup.action.mouse.click.LeftMouseClickAction
import com.patres.languagepopup.gui.controller.model.RootSchemaGroupController
import com.patres.languagepopup.gui.controller.model.SchemaGroupController
import com.patres.languagepopup.gui.controller.model.TextActionController
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.patres.languagepopup.model.TextActionModel
import javafx.fxml.FXMLLoader
import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object LoaderFactory {

    fun createTextActionModel(root: RootSchemaGroupModel, parent: SchemaGroupModel, action: Action): TextActionModel {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/TextAction.fxml")
        loader.resources = Main.bundle
        loader.load<TextActionController>()
        val controller = loader.getController<TextActionController>().apply {
            actionLabel.text = action.actionName
        }
        val model = getActionModel(controller, root, parent, action)
        controller.model = model
        return model
    }

    private fun getActionModel(controller: TextActionController, root: RootSchemaGroupModel, parent: SchemaGroupModel, action: Action): TextActionModel {
        return when (action) {
            Action.CLICK_LEFT_MOUSE_BUTTON -> LeftMouseClickAction(controller, root, parent)
            else -> LeftMouseClickAction(controller, root, parent) // TODO
        }
    }

    fun createSchemaGroupModel(root: RootSchemaGroupModel, parent: SchemaGroupModel?): SchemaGroupModel {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/SchemaGroup.fxml")
        loader.resources = Main.bundle
        loader.load<SchemaGroupController>()
        val controller = loader.getController<SchemaGroupController>()
        val model = SchemaGroupModel(controller, root, parent)
        controller.model = model
        return model
    }

    fun loadRootSchemaGroupModel(): RootSchemaGroupModel {
        val loader = FXMLLoader()
        loader.location = javaClass.getResource("/fxml/RootSchemaGroup.fxml")
        loader.resources = Main.bundle
        loader.load<RootSchemaGroupController>()
        val controller = loader.getController<RootSchemaGroupController>()
        val model = RootSchemaGroupModel(controller)
        controller.model = model
        model.initAfterSetController()
        return model
    }

    fun createRootSchemaGroup(tabPane: TabPane?): RootSchemaGroupModel {
        val rootSchemaGroup = loadRootSchemaGroupModel().apply {
            controller.insidePane.content = schemaGroup.controller.getMainOutsideNode()
        }

        val newTab = Tab("New Tab", rootSchemaGroup.controller.rootStackPane)
        tabPane?.apply {
            tabs?.add(newTab)
            selectionModel?.select(newTab)
        }

        return rootSchemaGroup
    }
}
