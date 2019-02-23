package com.patres.automation.action.mouse

import com.patres.automation.gui.controller.model.LabelActionController
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.util.LoaderFactory
import javafx.scene.Node

abstract class LabelAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : MouseAction<LabelActionController>(root, parent) {

    override val controller: LabelActionController = LoaderFactory.createLabelActionController(this)

    override fun runAction() {
        runMouseAction()
    }

    override fun getMainNode(): Node = controller.getMainNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()
}
