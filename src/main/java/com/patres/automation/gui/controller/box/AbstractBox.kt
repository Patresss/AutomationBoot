package com.patres.automation.gui.controller.box

import com.patres.automation.Main
import com.patres.automation.action.AbstractAction
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.controller.model.RootSchemaGroupController
import com.patres.automation.mapper.model.AutomationActionSerialized
import com.patres.automation.type.ActionBootable
import com.patres.automation.util.calculateTypedParent
import com.patres.automation.util.getAllNodes
import com.patres.automation.util.swap
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane


abstract class AbstractBox<ActionBootType : ActionBootable>(
        fxmlFile: String
) : StackPane() {

    @FXML
    lateinit var rootPane: StackPane

    @FXML
    lateinit var borderPane: BorderPane

    @FXML
    lateinit var actionPlaceholder: StackPane

    @FXML
    lateinit var selectStackPane: StackPane

    @FXML
    lateinit var selectActionButton: Button

    val root: RootSchemaGroupModel?
        get() = calculateTypedParent(RootSchemaGroupController::class)?.model

    val schemaGroupParent: SchemaGroupController?
        get() = calculateTypedParent(SchemaGroupController::class)


    init {
        val fxmlLoader = FXMLLoader(javaClass.getResource("/fxml/$fxmlFile"))
        fxmlLoader.setRoot(this)
        fxmlLoader.setController(this)
        fxmlLoader.resources = Main.getBundle()

        fxmlLoader.load<AbstractBox<*>>()
        selectStackPane.addEventHandler(MouseEvent.MOUSE_PRESSED) { selectAction() }
    }

    @FXML
    fun selectAction() {
        root?.controller?.unselectAllButton()
        selectActionButton.styleClass.add("select-action-button-selected")
        root?.controller?.selectedModel = this
    }


    fun unselectSelectActionButton() {
        selectActionButton.styleClass.remove("select-action-button-selected")
    }

    abstract fun addNewAction(abstractBox: AbstractBox<*>)


    private fun isLast(): Boolean = schemaGroupParent?.abstractBlocks?.last() == this

    private fun isFirst(): Boolean = schemaGroupParent?.abstractBlocks?.first() == this

    private fun swap(abstractBlockToSwap: AbstractBox<*>) {
        schemaGroupParent?.innerBox?.swap(this, abstractBlockToSwap)
    }


    fun downActionBlock() {
        val bottomNode = findNodeOnTheBottomFromGroup()
        when {
            bottomNode == null && hasTheSameParentAsRoot() -> schemaGroupParent?.leaveGroupToDown(this)
            bottomNode is SchemaGroupController -> bottomNode.moveActionBlockToTop(this)
            bottomNode is ActionBox<*> -> swap(bottomNode)
        }
    }

    fun upActionBlock() {
        val topNode = findNodeOnTheTopFromGroup()
        when {
            topNode == null && hasTheSameParentAsRoot() -> schemaGroupParent?.leaveGroupToUp(this)
            topNode is SchemaGroupController -> topNode.moveActionBlockToBottom(this)
            topNode is ActionBox<*> -> swap(topNode)
        }
    }

    private fun hasTheSameParentAsRoot() = root?.controller?.schemaGroupController != schemaGroupParent

    fun findNodeOnTheTop(): AbstractBox<*>? {
        val topNodeInGroup = findNodeOnTheTopFromGroup()
        if (topNodeInGroup != null) {
            return topNodeInGroup
        }
        return schemaGroupParent
    }

    private fun findNodeOnTheBottomFromGroup(): AbstractBox<*>? {
        val parentVal = schemaGroupParent
        return if (!isLast() && parentVal != null) {
            val currentActionIndex = parentVal.abstractBlocks.indexOf(this)
            parentVal.abstractBlocks[currentActionIndex + 1]
        } else {
            null
        }
    }

    private fun findNodeOnTheTopFromGroup(): AbstractBox<*>? {
        val parentVal = schemaGroupParent
        return if (!isFirst() && parentVal != null) {
            val currentActionIndex = parentVal.abstractBlocks.indexOf(this)
            parentVal.abstractBlocks[currentActionIndex - 1]
        } else {
            null
        }
    }

    abstract fun toModel(): AbstractAction?
    abstract fun toSerialized(): AutomationActionSerialized

}

