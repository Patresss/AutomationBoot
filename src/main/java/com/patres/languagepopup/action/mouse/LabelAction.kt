package com.patres.languagepopup.action.mouse

import com.patres.languagepopup.gui.controller.model.LabelActionController
import com.patres.languagepopup.model.RootSchemaGroupModel
import com.patres.languagepopup.model.SchemaGroupModel
import com.patres.languagepopup.util.LoaderFactory
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
