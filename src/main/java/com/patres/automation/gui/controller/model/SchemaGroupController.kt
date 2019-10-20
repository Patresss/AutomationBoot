package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXTextField
import com.patres.automation.mapper.SchemaGroupMapper
import com.patres.automation.mapper.model.SchemaGroupSerialized
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.type.ActionBootSchema
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox


class SchemaGroupController : AutomationController<ActionBootSchema>("SchemaGroup.fxml", ActionBootSchema.ADD_GROUP) { // TODO do I need menuItem?

    @FXML
    lateinit var innerBox: VBox

    @FXML
    lateinit var groupNameTextField: JFXTextField

    @FXML
    lateinit var iterationsTextField: JFXTextField

    @FXML
    override fun initialize() {
        super.initialize()
        groupNameTextField.onMouseClicked = EventHandler { selectAction() }
    }

    fun getMainInsideNode(): Pane = innerBox

    override fun getNodesToSelect(): List<Node> = super.getNodesToSelect() + listOf(groupNameTextField, iterationsTextField)


    val actionBlocks: List<AutomationController<*>>
        get() = innerBox.children.filterIsInstance<AutomationController<*>>()

    private val schemaGroups
        get() = actionBlocks.filterIsInstance<SchemaGroupController>()

    val allChildrenActionBlocks: List<AutomationController<*>>
        get() = actionBlocks + schemaGroups.flatMap { it.allChildrenActionBlocks }

    fun addActionBlockToList(actionController: AutomationController<*>, index: Int) {
        getMainInsideNode().children.add(index, actionController)
    }

    fun leaveGroupToUp(actionController: AutomationController<*>) {
        actionController.schemaGroupParent?.removeNode(actionController)
        val index = schemaGroupParent?.getMainInsideNode()?.children?.indexOf(this) ?: 0
        schemaGroupParent?.addActionBlockToList(actionController, index)
    }

    fun leaveGroupToDown(actionController: AutomationController<*>) {
        actionController.schemaGroupParent?.removeNode(actionController)
        val index = (schemaGroupParent?.getMainInsideNode()?.children?.indexOf(this) ?: 0) + 1
        schemaGroupParent?.addActionBlockToList(actionController, index)
    }

    fun moveActionBlockToTop(actionController: AutomationController<*>) {
        actionController.schemaGroupParent?.removeNode(actionController)
        getMainInsideNode().children.add(0, actionController)
    }

    fun moveActionBlockToBottom(actionController: AutomationController<*>) {
        actionController.schemaGroupParent?.removeNode(actionController)
        getMainInsideNode().children.add(actionController)
    }

    fun addActionBlockToBottom(actionController: AutomationController<*>) {
        getMainInsideNode().children.add(actionController)
    }

    fun removeNode(actionController: AutomationController<*>) {
        getMainInsideNode().children.remove(actionController)
    }

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

    override fun toModel(): SchemaGroupModel {
        return SchemaGroupMapper.controllerToModel(this)
    }

    override fun toSerialized(): SchemaGroupSerialized {
        return SchemaGroupMapper.controllerToSerialized(this)
    }

}