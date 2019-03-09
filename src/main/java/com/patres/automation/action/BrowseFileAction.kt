package com.patres.automation.action

import com.patres.automation.gui.controller.model.TextFieldActionController
import com.patres.automation.menuItem.MenuItem
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.util.LoaderFactory
import com.patres.automation.validation.FileValidation
import com.patres.automation.validation.IntegerValidation
import javafx.scene.Node
import org.slf4j.LoggerFactory

abstract class BrowseFileAction(
        root: RootSchemaGroupModel,
        parent: SchemaGroupModel
) : TextFieldActionModel(root, parent) {

    override val controller: TextFieldActionController = LoaderFactory.createBrowseFileActionController(this)

    private var validation = FileValidation(controller.validLabel, controller.valueText)

    init {
        validation.activateControlListener()
    }


    override fun getMainNode(): Node = controller.getMainNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}
