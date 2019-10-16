package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXTextField
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.model.SchemaGroupModel
import com.patres.automation.type.ActionBootSchema
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox


class SchemaGroupController(root: RootSchemaGroupModel,
                            parent: SchemaGroupController? = null
) : AutomationController<ActionBootSchema>("SchemaGroup.fxml", root, parent, ActionBootSchema.ADD_GROUP) { // TODO do I need menuItem?

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
        actionController.parent = this
        getMainInsideNode().children.add(index, actionController)
    }

    fun leaveGroupToUp(actionController: AutomationController<*>) {
        actionController.parent?.removeNode(actionController)
        val index = parent?.getMainInsideNode()?.children?.indexOf(this) ?: 0
        parent?.addActionBlockToList(actionController, index)
    }

    fun leaveGroupToDown(actionController: AutomationController<*>) {
        actionController.parent?.removeNode(actionController)
        val index = (parent?.getMainInsideNode()?.children?.indexOf(this) ?: 0) + 1
        parent?.addActionBlockToList(actionController, index)
    }

    fun moveActionBlockToTop(actionController: AutomationController<*>) {
        actionController.parent?.removeNode(actionController)
        actionController.parent = this
        getMainInsideNode().children.add(0, actionController)
    }

    fun moveActionBlockToBottom(actionController: AutomationController<*>) {
        actionController.parent?.removeNode(actionController)
        actionController.parent = this
        getMainInsideNode().children.add(actionController)
    }

    fun addActionBlockToBottom(actionController: AutomationController<*>) {
        actionController.parent = this
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

    fun getGroupName(): String = groupNameTextField.text

    fun setGroupName(text: String) {
        groupNameTextField.text = text
    }

    fun setNumberOfIterations(text: String) {
        iterationsTextField.text = text
    }

    override fun toModel(): SchemaGroupModel {
        val actionBlockModels = actionBlocks.map { it.toModel() }
        val iteration = getNumberOfIteration()
        val automationRunningProperty = root.automationRunningProperty
        return SchemaGroupModel(actionBlockModels, iteration, automationRunningProperty)
    }

}