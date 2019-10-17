package com.patres.automation.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.patres.automation.Main
import com.patres.automation.action.AbstractAction
import com.patres.automation.gui.custom.KeyboardButton
import com.patres.automation.mapper.model.AutomationActionSerialized
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.type.ActionBootable
import com.patres.automation.util.swap
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane


abstract class AutomationController<ActionBootType : ActionBootable>(
        fxmlFile: String,
        val root: RootSchemaGroupModel,
        var parent: SchemaGroupController? = null,
        val action: ActionBootType
) : StackPane() {

    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/$fxmlFile"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.getBundle()
        fxmlLoader.load<KeyboardButton>()
    }

    @FXML
    lateinit var selectActionButton: JFXButton

    @FXML
    lateinit var selectStackPane: StackPane

    @FXML
    lateinit var gridPane: GridPane

    @FXML
    open fun initialize() {
        getNodesToSelect().forEach { it.onMouseClicked = EventHandler { selectAction() } }
    }

    fun selectAction() {
        root.controller.unselectAllButton()
        selectActionButton.styleClass.add("select-action-button-selected")
        root.controller.selectedModel = this
    }

    open fun getNodesToSelect(): List<Node> = listOf(selectStackPane)

    fun unselectSelectActionButton() {
        selectActionButton.styleClass.remove("select-action-button-selected")
    }

    private fun isLast(): Boolean = parent?.actionBlocks?.last() == this

    private fun isFirst(): Boolean = parent?.actionBlocks?.first() == this

    private fun swap(actionBlockToSwap: AutomationController<*>) {
        parent?.getMainInsideNode()?.swap(this, actionBlockToSwap)
    }

    fun addActionBlockUnder(actionBlock: AutomationController<*>) {
        val index = parent?.actionBlocks?.indexOf(this) ?: 0
        parent?.addActionBlockToList(actionBlock, index + 1)
    }

    fun downActionBlock() {
        val bottomNode = findNodeOnTheBottomFromGroup()
        when {
            bottomNode == null && root.controller.schemaGroupController != parent -> parent?.leaveGroupToDown(this)
            bottomNode is SchemaGroupController -> bottomNode.moveActionBlockToTop(this)
            bottomNode is AutomationController<*> -> swap(bottomNode)
        }
    }

    fun upActionBlock() {
        val topNode = findNodeOnTheTopFromGroup()
        when {
            topNode == null && root.controller.schemaGroupController != parent -> parent?.leaveGroupToUp(this)
            topNode is SchemaGroupController -> topNode.moveActionBlockToBottom(this)
            topNode is AutomationController<*> -> swap(topNode)
        }
    }

    fun findNodeOnTheTop(): AutomationController<*>? {
        val topNodeInGroup = findNodeOnTheTopFromGroup()
        if (topNodeInGroup != null) {
            return topNodeInGroup
        }
        return parent
    }

    private fun findNodeOnTheBottomFromGroup(): AutomationController<*>? {
        val parentVal = parent
        return if (!isLast() && parentVal != null) {
            val currentActionIndex = parentVal.actionBlocks.indexOf(this)
            parentVal.actionBlocks[currentActionIndex + 1]
        } else {
            null
        }
    }

    private fun findNodeOnTheTopFromGroup(): AutomationController<*>? {
        val parentVal = parent
        return if (!isFirst() && parentVal != null) {
            val currentActionIndex = parentVal.actionBlocks.indexOf(this)
            parentVal.actionBlocks[currentActionIndex - 1]
        } else {
            null
        }
    }

    abstract fun toModel(): AbstractAction
    abstract fun toSerialized(): AutomationActionSerialized

}