package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import com.patres.languagepopup.model.AutomationModel
import com.patres.languagepopup.model.SchemaGroup
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.*


class SchemaGroupController : ActionBlockController() {

    @FXML
    lateinit var mainSchemaBox: BorderPane

    @FXML
    lateinit var outsideBox: StackPane

    @FXML
    lateinit var innerBox: VBox

    @FXML
    lateinit var groupNameTextField: JFXTextField

    @FXML
    lateinit var iterationsTextField: JFXTextField

    @FXML
    lateinit var selectActionButton: JFXButton


    fun initialize() {
    }

    @FXML
    fun selectAction() {
        actionBlock?.rootParent?.unselectAllButton()
        selectActionButton.styleClass.add("select-action-button-selected")
    }

    fun unselectAction() {
        selectActionButton.styleClass.remove("select-action-button-selected")
    }

    @FXML
    fun downActionBlock() {
        actionBlock?.let { currentActionBlock ->
            val bottomNode = findNodeOnTheBottom()
            if (bottomNode is SchemaGroup) {
                if (currentActionBlock.hasTheSameParent(bottomNode)) {
                    bottomNode.addActionBlockToTop(currentActionBlock)
                } else {
                    bottomNode.moveActionBlockUnder(currentActionBlock)
                }
            }
        }
    }

    @FXML
    fun upActionBlock() {
        actionBlock?.let { currentActionBlock ->
            val topNode = findNodeOnTheTop()
            if (topNode is SchemaGroup) {
                if (currentActionBlock.hasTheSameParent(topNode)) {
                    topNode.addActionBlockToBottom(currentActionBlock)
                } else {
                    topNode.moveActionBlockAbove(currentActionBlock)
                }
            }
        }
    }

    fun findNodeOnTheBottom(): AutomationModel? {
        actionBlock?.let { currentActionBlock ->
            val endPositionY = currentActionBlock.endPositionY
            return currentActionBlock.rootParent.allChildrenActionBlocks
                    .filter { it !== actionBlock }
                    .filter { endPositionY <= it.startPositionY || (currentActionBlock.parent == it && currentActionBlock.isLast()) }
                    .minBy { it.startPositionY }
        }
        return null
    }

    fun findNodeOnTheTop(): AutomationModel? {
        actionBlock?.let { currentActionBlock ->
            val startPositionY = currentActionBlock.startPositionY
            return currentActionBlock.rootParent.allChildrenActionBlocks
                    .filter { it !== actionBlock }
                    .filter { startPositionY >= it.endPositionY || (currentActionBlock.parent == it && currentActionBlock.isFirst()) }
                    .maxBy { it.endPositionY }
        }
        return null
    }

    override fun getMainOutsideNode(): Node = mainSchemaBox

    override fun getMainInsideNode(): Pane = innerBox

}