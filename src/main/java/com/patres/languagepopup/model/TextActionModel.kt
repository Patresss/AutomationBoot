package com.patres.languagepopup.model

import com.patres.languagepopup.gui.controller.model.TextActionController
import javafx.scene.Node

class TextActionModel(
        controller: TextActionController,
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel?
) : AutomationModel<TextActionController>(controller, root, parent) {

    override fun getMainNode(): Node = controller.getMainOutsideNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}