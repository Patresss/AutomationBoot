package com.patres.languagepopup.model

import com.patres.languagepopup.gui.controller.model.TextActionController
import javafx.scene.Node

class TextActionModel(
        controller: TextActionController,
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel?
) : AutomationModel<TextActionController>(controller, root, parent) {

    override fun getMainNode(): Node {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMainInsideNode(): Node {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}