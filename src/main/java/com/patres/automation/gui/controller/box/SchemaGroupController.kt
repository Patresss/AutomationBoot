package com.patres.automation.gui.controller.box

import com.jfoenix.controls.JFXTextField
import com.patres.automation.action.AbstractAction
import com.patres.automation.action.SchemaGroupModel
import com.patres.automation.mapper.SchemaGroupMapper
import com.patres.automation.mapper.model.AutomationActionSerialized
import com.patres.automation.mapper.model.SchemaGroupSerialized
import com.patres.automation.type.ActionBootSchema
import javafx.fxml.FXML
import javafx.scene.layout.VBox


class SchemaGroupController : AbstractBox<ActionBootSchema>("SchemaGroup.fxml") {

    @FXML
    lateinit var innerBox: VBox

    @FXML
    lateinit var groupNameTextField: JFXTextField

    @FXML
    lateinit var iterationsTextField: JFXTextField

    fun getNumberOfIteration(): Int {
        val text = iterationsTextField.text
        return if (text == "INF") {
            Integer.MAX_VALUE
        } else {
            try {
                Integer.parseInt(iterationsTextField.text)
            } catch (nfe: NumberFormatException) {
                1
            }
        }
    }

    val abstractBlocks: List<AbstractBox<*>>
        get() = innerBox.children.filterIsInstance<AbstractBox<*>>()

    private val schemaGroups
        get() = abstractBlocks.filterIsInstance<SchemaGroupController>()

    val allChildrenActionBlocks: List<AbstractBox<*>>
        get() = abstractBlocks + schemaGroups.flatMap { it.allChildrenActionBlocks }

    override fun addNewAction(abstractBox: AbstractBox<*>) {
        innerBox.children.add(abstractBox)
    }

    override fun toSerialized(): SchemaGroupSerialized {
        return SchemaGroupMapper.controllerToSerialized(this)
    }

    override fun toModel(): SchemaGroupModel {
        return SchemaGroupMapper.controllerToModel(this)
    }

    fun addActionBlockToList(abstractController: AbstractBox<*>, index: Int) {
        innerBox.children.add(index, abstractController)
    }

    fun removeNode(actionController: AbstractBox<*>) {
        innerBox.children.remove(actionController)
    }


    fun leaveGroupToUp(abstractController: AbstractBox<*>) {
        abstractController.schemaGroupParent?.removeNode(abstractController)
        val index = schemaGroupParent?.innerBox?.children?.indexOf(this) ?: 0
        schemaGroupParent?.addActionBlockToList(abstractController, index)
    }

    fun leaveGroupToDown(abstractController: AbstractBox<*>) {
        abstractController.schemaGroupParent?.removeNode(abstractController)
        val index = (schemaGroupParent?.innerBox?.children?.indexOf(this) ?: 0) + 1
        schemaGroupParent?.addActionBlockToList(abstractController, index)
    }

    fun moveActionBlockToTop(abstractController: AbstractBox<*>) {
        abstractController.schemaGroupParent?.removeNode(abstractController)
        innerBox.children.add(0, abstractController)
    }

    fun moveActionBlockToBottom(abstractController: AbstractBox<*>) {
        abstractController.schemaGroupParent?.removeNode(abstractController)
        innerBox.children.add(abstractController)
    }


}