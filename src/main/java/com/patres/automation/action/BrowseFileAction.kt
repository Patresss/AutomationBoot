package com.patres.automation.action

import com.patres.automation.gui.controller.model.BrowseFileActionController
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
) : TextFieldActionModel<BrowseFileActionController>(root, parent) {

    override val controller: BrowseFileActionController = BrowseFileActionController(this)

    private var validation = FileValidation(controller).also { it.activateControlListener() }

    init {
        validation.activateControlListener()
    }


    override fun getMainNode(): Node = controller.getMainNode()

    override fun getMainInsideNode(): Node = controller.getMainInsideNode()

}
